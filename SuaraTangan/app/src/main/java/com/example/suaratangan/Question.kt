package com.example.suaratangan.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(

    val id: Long? = null,
    val media_url: String? = null,
    val media_type: String? = null,
    val question: String? = null,

    val option1: String? = null,
    val option2: String? = null,
    val option3: String? = null,
    val option4: String? = null,

    val answer: String? = null
)