package com.slicky.decisiontree

import org.dom4j.Document
import org.dom4j.Element
import org.dom4j.io.SAXReader
import java.io.File
import java.io.InputStream

/**
 * Created by slicky on 30.12.2016
 */
object DecisionTreeParser {

    @Throws(DecisionTreeException::class)
    fun parseFile(path: String): TreeData {
        return parse(File(path).inputStream())
    }

    @Throws(DecisionTreeException::class)
    fun parse(inStream: InputStream): TreeData {
        // open SAXReader
        val reader = SAXReader()
        // read document
        val document = reader.read(inStream)

        // find root element
        val rootElement = findRoot(document)
        // find decisions
        val decisions = findDecisions(rootElement)
        // find actions
        val actions = findActions(rootElement)
        // find ends
        val ends = findEnds(rootElement)

        // return TreeData
        return TreeData(decisions, actions, ends)
    }

    private fun findRoot(document: Document?): Element {
        return document?.rootElement?.apply {
            // root element has to be called "tree"
            if (name != "tree")
                throw DecisionTreeException { "Root element is not named \"tree\"!" }
            // if root element is null throw exception
        } ?: throw DecisionTreeException { "Could not find root element!" }
    }

    private fun findDecisions(root: Element): List<Decision> {
        // select decisions element
        val decisionsElement = root.element("decisions")
                ?: throw DecisionTreeException { "Could not find \"decisions\" element!" }

        // select decision elements
        val decisionElements = decisionsElement.elements("decision")
        if (decisionElements.isEmpty())
            throw DecisionTreeException { "Could not find any \"decision\" elements!" }

        // extract and return decisions from decision elements
        return decisionElements.map { decision ->
            // cast to Element
            val decisionElement = decision as Element

            // select id attribute for decision
            val decisionID = decisionElement.attribute("id")?.value
                    ?: throw DecisionTreeException { "Decision has no \"id\" attribute!" }
            // select text and trim it
            val decisionText = (decisionElement.data as String).trim()

            // extract flag elements
            val flagElements = decisionElement.elements("flag") ?: emptyList<Element>()
            // extract text from flag elements
            val flags = flagElements.map { ((it as Element).data as String).trim() }

            // extract answers from answer elements
            val answers = findAnswers(decisionElement)

            // there should be at least one answer
            if (answers.isEmpty())
                throw DecisionTreeException { "Decision has no \"answer\" elements!" }

            // return Decision
            Decision(decisionID, flags, decisionText, answers)
        }
    }

    private fun findAnswers(decisions: Element): List<Answer> {
        return decisions.elements("answer")
                .map { answer ->
                    // cast to Element
                    val answerElement = answer as Element

                    // select text and trim it
                    val answerText = (answerElement.data as String).trim()

                    // extract flag elements
                    val flagElements = answerElement.elements("flag") ?: emptyList<Element>()
                    // extract text from flag elements
                    val flags = flagElements.map { ((it as Element).data as String).trim() }

                    // extract more element
                    val moreElement = answerElement.element("more")
                    // extract text from more element
                    val data = moreElement?.data
                    val moreText = if (data != null) (data as String).trim() else null

                    // select action id from action attribute
                    val actionID = answerElement.attribute("action").value

                    // return Action
                    Answer(answerText, flags, moreText, actionID)
                }
    }

    private fun findActions(root: Element): List<Action> {
        // select actions element
        val actionsElement = root.element("actions")
                ?: throw DecisionTreeException { "Could not find \"actions\" element!" }

        // select action elements
        val actionElements = actionsElement.elements("action")
        if (actionElements.isEmpty())
            throw DecisionTreeException { "Could not find any \"action\" elements!" }

        // extract and return actions from action elements
        return actionElements.map { action ->
            // cast to Element
            val actionElement = action as Element

            // select id from action attribute
            val actionID = actionElement.attribute("id")?.value
                    ?: throw DecisionTreeException { "Action has no \"id\" attribute!" }

            // extract flag elements
            val flagElements = actionElement.elements("flag") ?: emptyList<Element>()
            // extract text from flag elements
            val flags = flagElements.map { ((it as Element).data as String).trim() }

            // select next element from action element
            val nextElement = actionElement.element("next")
                    ?: throw DecisionTreeException { "Action has no \"next\" elements!" }
            // select next attribute as decision
            var nextAttribute = nextElement.attribute("decision")

            if (nextAttribute != null) {
                // next is decision, return decision Action
                Action(actionID, flags, NextType.DECISION, nextAttribute.value)
            } else {
                // next is end, select next attribute as end
                nextAttribute = nextElement.attribute("end")
                        ?: throw DecisionTreeException { "Next has no attribute is named \"decision\" or \"end\"!" }

                // return end Action
                Action(actionID, flags, NextType.END, nextAttribute.value)
            }
        }
    }

    private fun findEnds(root: Element): List<End> {
        // select ends element
        val endsElement = root.element("ends")
                ?: throw DecisionTreeException { "Could not find \"ends\" element!" }

        // select end elements
        val endElements = endsElement.elements("end")
        if (endElements.isEmpty())
            throw DecisionTreeException { "Could not find any \"end\" elements!" }

        // extract and return ends from end elements
        return endElements.map { end ->
            // cast to Element
            val endElement = end as Element

            // select id from end elements
            val endID = endElement.attribute("id")?.value
                    ?: throw DecisionTreeException { "End has no \"id\" attribute!" }
            // select text and trim it
            val endText = (endElement.data as String).trim()

            // return End
            End(endID, endText)
        }
    }
}
