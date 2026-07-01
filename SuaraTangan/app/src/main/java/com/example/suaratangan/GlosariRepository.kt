package com.example.suaratangan.data

import com.example.suaratangan.model.Glosari
import io.github.jan.supabase.postgrest.from

suspend fun getGlosari(): List<Glosari> {

    return SupabaseClient.client
        .from("glosari_bim")
        .select()
        .decodeList<Glosari>()
}
