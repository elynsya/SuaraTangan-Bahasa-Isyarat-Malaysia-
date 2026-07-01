package com.example.suaratangan.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://xziqjogolezqffakvqdw.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inh6aXFqb2dvbGV6cWZmYWt2cWR3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg2Njg2MDQsImV4cCI6MjA5NDI0NDYwNH0.IZat0IZ2XjVUkzcHux7W1VXO17sDOtXul3MU-hQe5gE"
    ) {
        install(Postgrest)
        install(Storage)
    }
}