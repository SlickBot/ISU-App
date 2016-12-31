package com.slicky.decisiontree

/**
 * Created by slicky on 30.12.2016
 */
data class TreeData(val decisions: List<Decision>, val actions: List<Action>, val ends: List<End>) {
    val rootDecision = decisions.firstOrNull { it.id.toLowerCase() == "start" }
            ?: throw DecisionTreeException { "Can not find root (START) decision!" }

    @Throws(DecisionTreeException::class)
    fun findDecision(id: String) = decisions.firstOrNull { it.id == id }
            ?: throw DecisionTreeException { "Can not find decision with id \"$id\"!" }
    @Throws(DecisionTreeException::class)
    fun findAction(id: String) = actions.firstOrNull { it.id == id }
            ?: throw DecisionTreeException { "Can not find action with id \"$id\"!" }
    @Throws(DecisionTreeException::class)
    fun findEnd(id: String) = ends.firstOrNull { it.id == id }
            ?: throw DecisionTreeException { "Can not find end with id \"$id\"!" }
}