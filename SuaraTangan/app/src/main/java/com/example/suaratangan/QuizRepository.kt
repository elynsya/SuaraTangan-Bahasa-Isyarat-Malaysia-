package com.example.suaratangan.repository

import android.util.Log
import com.example.suaratangan.data.SupabaseClient
import com.example.suaratangan.model.Question
import io.github.jan.supabase.postgrest.from

class QuizRepository {

    suspend fun getQuizQuestions(): List<Question> {

        return try {

            val result = SupabaseClient.client
                .from("quiz_questions")
                .select()
                .decodeList<Question>()

            Log.d("SUPABASE_TEST", "SUCCESS")
            Log.d("SUPABASE_TEST", result.toString())

            result

        } catch (e: Exception) {

            Log.e("SUPABASE_TEST", "ERROR: ${e.message}")

            emptyList()
        }
    }
}