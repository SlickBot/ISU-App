package com.slicky.decisiontree

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
        val rootElement = document.rootElement
                ?: throw DecisionTreeException { "Could not find root element!" }
        // root element has to be called "tree"
        if (rootElement.name != "tree")
            throw DecisionTreeException { "Root element is not named \"tree\"!" }

        // select decisions element
        val decisionsElement = rootElement.element("decisions")
                ?: throw DecisionTreeException { "Could not find \"decisions\" element!" }
        // select actions element
        val actionsElement = rootElement.element("actions")
                ?: throw DecisionTreeException { "Could not find \"actions\" element!" }
        // select ends element
        val endsElement = rootElement.element("ends")
                ?: throw DecisionTreeException { "Could not find \"ends\" element!" }

        // select decision elements
        val decisionElements = decisionsElement.elements("decision").map { it as Element }
        if (decisionElements.isEmpty())
            throw DecisionTreeException { "Could not find any \"decision\" elements!" }
        // select action elements
        val actionElements = actionsElement.elements("action").map { it as Element }
        if (actionElements.isEmpty())
            throw DecisionTreeException { "Could not find any \"action\" elements!" }
        // select end elements
        val endElements = endsElement.elements("end").map { it as Element }
        if (endElements.isEmpty())
            throw DecisionTreeException { "Could not find any \"end\" elements!" }

        // extract decisions from decision elements
        val decisions = decisionElements.map { decisionElement ->
            // select id attribute for decision
            val decisionID = decisionElement.attribute("id")?.value
                    ?: throw DecisionTreeException { "Decision has no \"id\" attribute!" }
            // extract flag elements
            val flagElements = decisionElement.elements("flag") ?: emptyList<Element>()
            // extract text from flag elements
            val flags = flagElements.map { ((it as Element).data as String).trim() }
            // select text and trim it
            val decisionText = (decisionElement.data as String).trim()
            // extract answers from answer elements
            val answers = decisionElement.elements("answer")
                    // cast as Element
                    .map { it as Element }
                    .map { answerElement ->
                        // select text and trim it
                        val answerText = (answerElement.data as String).trim()
                        // select action id from action attribute
                        val actionID = answerElement.attribute("action").value
                        // return Action
                        Answer(answerText, actionID)
                    }
            // there should be at least one answer
            if (answers.isEmpty())
                throw DecisionTreeException { "Decision has no \"answer\" elements!" }
            // return Decision
            Decision(decisionID, flags, decisionText, answers)
        }

        // extract actions from action elements
        val actions = actionElements.map { actionElement ->
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

        // extract ends from end elements
        val ends = endElements.map { endElement ->
            // select id from end elements
            val endID = endElement.attribute("id")?.value
                    ?: throw DecisionTreeException { "End has no \"id\" attribute!" }
            // select text and trim it
            val endText = (endElement.data as String).trim()
            // return End
            End(endID, endText)
        }

        // return TreeData
        return TreeData(decisions, actions, ends)
    }
}
