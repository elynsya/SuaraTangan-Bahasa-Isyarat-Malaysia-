package com.example.suaratangan.data

import com.example.suaratangan.model.TranslationItem

suspend fun translateSentence(
    sentence: String
): List<TranslationItem> {
    val result = mutableListOf<TranslationItem>()
    val words = sentence
        .trim()
        .split("\\s+".toRegex())
    for (word in words) {
        val foundWord = getGlosariByWord(word)
        if (foundWord != null) {
            result.add(
                TranslationItem(
                    text = foundWord.word,
                    imageUrl = foundWord.image_url,
                    videoUrl = foundWord.video_url
                )
            )
        } else {
            word.forEach { letter ->
                val foundLetter =
                    getGlosariByWord(letter.toString())
                if (foundLetter != null) {

                    result.add(
                        TranslationItem(
                            text = foundLetter.word,
                            imageUrl = foundLetter.image_url,
                            videoUrl = foundLetter.video_url
                        )
                    )
                }
            }
        }
    }

    return result
}