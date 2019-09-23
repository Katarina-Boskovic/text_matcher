package com.textmatcher
import kotlin.collections.*

fun textMatcher(refStringArray: Array<String>, queriedText: String): Pair<List<String>, List<String>> {

    /*val collector: MutableMap<String, MutableList<String>> = mutableMapOf()

    val queriedLines = queriedText.lines()
    for(i in 0 until queriedLines.size) {
        for (j in 0 until refStringArray.size) {
            if (queriedLines[i].contains(refStringArray[j])) {
                if (queriedLines[i] in collector) {
                    collector[queriedLines[i]]?.add(refStringArray[j])
                }
                else{
                    collector[queriedLines[i]] = mutableListOf(refStringArray[j])
                }
            }
        }
    }

    val matchedLines: MutableSet<String> = collector.keys
    val matchedNames: MutableCollection<MutableList<String>> = collector.values
    */



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



fun main(){
    val refStringArray = arrayOf("CompanyA", "CompanyB")
    val queriedText = "Sample text contains CompanyA\nhas 3 lines and contains\nCompanyB"
    val (n, l) = textMatcher(refStringArray, queriedText)
    println(n)
    println(l)
}
