package com.example.suaratangan.data

import com.example.suaratangan.model.Glosari
import io.github.jan.supabase.postgrest.from

suspend fun getGlosariByWord(
    searchWord: String
): Glosari? {

    return try {

        SupabaseClient.client
            .from("glosari_bim")
            .select()
            .decodeList<Glosari>()
            .firstOrNull {
                it.word.equals(
                    searchWord,
                    ignoreCase = true
                )
            }

    } catch (e: Exception) {
        null
    }
}