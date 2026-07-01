package com.example.suaratangan.model

import kotlinx.serialization.Serializable

@Serializable
data class Glosari(
    val id: Int,
    val word: String,
    val category: String,
    val image_url: String?,
    val video_url: String? = null
)


