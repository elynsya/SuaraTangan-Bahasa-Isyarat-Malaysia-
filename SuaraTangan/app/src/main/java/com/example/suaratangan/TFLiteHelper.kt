package com.example.suaratangan.ui.screen

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TFLiteHelper(context: Context) {

    private var interpreter: Interpreter

    init {
        val assetFile =
            context.assets.open("hand_model.tflite")
        val modelBytes =
            assetFile.readBytes()
        val buffer = ByteBuffer.allocateDirect(modelBytes.size)
        buffer.order(ByteOrder.nativeOrder())
        buffer.put(modelBytes)
        interpreter = Interpreter(buffer)
    }

    fun predict(inputList: List<Float>): Int {
        val inputBuffer =
            ByteBuffer.allocateDirect(63 * 4)
        inputBuffer.order(ByteOrder.nativeOrder())
        for (value in inputList) {
            inputBuffer.putFloat(value)
        }
        val output =
            Array(1) { FloatArray(24)}
        interpreter.run(inputBuffer, output)
        val result = output[0]
        return result.indices.maxByOrNull {
            result[it]
        } ?: 0
    }
}