package com.slicky.decisiontree

/**
 * Created by slicky on 30.12.2016
 */
class DecisionTreeException(lazy: () -> String) : Exception(lazy.invoke())
