package com.textmatcher
import kotlin.collections.*

fun textMatcher(refStringArray: Array<String>, queriedText: String): Pair<List<String>, List<String>> {

    val collector: MutableMap<Int, MutableMap<String, MutableList<String>>> = mutableMapOf()
    val queriedLines = queriedText.lines()
    for(i in 0 until queriedLines.size) {
        for (j in 0 until refStringArray.size) {
            if (queriedLines[i].contains(refStringArray[j])) {
                if (i in collector) {
                    collector[i]?.get(queriedLines[i])?.add(refStringArray[j])
                }
                else{
                    collector[i] = mutableMapOf(queriedLines[i] to mutableListOf(refStringArray[j]))
                }
            }
        }
    }
    println(collector)

    val matchedLineNumbers = collector.keys
    val matchedData = collector.values

    //println(matchedData)
    val matchedLines = matchedData.map {x -> x.keys.toList()[0] }
    val matchedNames = matchedData.map {x -> x.values.toList()[0].joinToString(separator = ",") }

    return Pair(matchedLines, matchedNames)
}