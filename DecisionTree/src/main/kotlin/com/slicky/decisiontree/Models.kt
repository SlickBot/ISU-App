package com.slicky.decisiontree

import java.io.Serializable

/**
 * Created by slicky on 30.12.2016
 */
data class Decision(val id: String, val flags: List<String>, val text: String, val answers: List<Answer>)
data class Answer(val text: String, val actionID: String)
data class Action(val id: String, val flags: List<String>, val type: NextType, val nextID: String)
data class End(val id: String, val text: String) : Serializable
enum class NextType {
    DECISION,
    END
}