package com.app.shared.utils

class StringUtils {

    fun split(sentence: String): List<String> {
        return sentence.split("\\s+".toRegex()).map { word ->
            word.replace("""^[,.]|[,.]$""".toRegex(), "")
        }.flatMap { word ->
            word.split(".")
        }
    }
}