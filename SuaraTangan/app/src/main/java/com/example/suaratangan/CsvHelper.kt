package com.example.suaratangan.ui.screen

import android.content.Context
import java.io.File
import java.io.FileWriter

object CsvHelper {

    fun saveLandmarks(
        context: Context,
        landmarks: List<Float>,
        label: String
    ) {

        val file = File(
            context.filesDir,
            "hand_data.csv"
        )

        val writer = FileWriter(file, true)

        val row =
            landmarks.joinToString(",") +
                    ",$label\n"

        writer.append(row)

        writer.flush()
        writer.close()

        println("CSV SAVED")
        println(file.exists())
        println(file.length())
        println(file.absolutePath)
    }
}