package com.slicky.decisiontree

import com.slicky.decisiontree.NextType.DECISION
import com.slicky.decisiontree.NextType.END
import java.util.*

/**
 * Created by slicky on 28.12.2016
 */
fun main(args: Array<String>) {
//    val path = "/tree.xml"
//    val path = "/wd40_vs_tape.xml"
    val path = "/example_decision_tree.xml"
    Tester().run(path)
}

class Tester {
    fun run(path: String) {
        // init scanner
        val sc = Scanner(System.`in`)
        // parse file
        val data = DecisionTreeParser.parseFile(javaClass.getResource(path).file)

        with(data) {

            // init decision to root decision
            var decision = rootDecision
            // init answers to root answers
            var answers = decision.answers
            // init should run
            var shouldRun = true

            // loop while should run
            while (shouldRun) {
                // print question
                println(decision.text)
                // print answers
                answers.forEachIndexed { i, answer -> println("$i: ${answer.text}") }

                // prompt answer idx
                var prompt = ""
                while (!prompt.matches("[+]?\\d+".toRegex()) || prompt.toInt() >= answers.size)
                    prompt = sc.nextLine()
                // convert to integer
                val answerIdx = prompt.toInt()

                // get action id
                val actionID = answers[answerIdx].actionID
                // find action
                val action = findAction(actionID)

                // switch type
                when (action.type) {
                    DECISION -> {
                        // set new decision
                        decision = findDecision(action.nextID)
                        // set new answers
                        answers = decision.answers
                    }
                    END -> {
                        // find end
                        val end = findEnd(action.nextID)
                        // print it
                        println("END: ${end.text}")
                        // should not run anymore
                        shouldRun = false
                    }
                }
            }
        }
    }
}