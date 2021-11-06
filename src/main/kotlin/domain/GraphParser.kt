package domain

import java.io.File
import java.lang.Exception

class GraphParser(private val config: Config) {

    private var maxNumOfVertices: Int = 0

    private fun findMaxNumOfVertices() {

        maxNumOfVertices = File(config.filename)
            .bufferedReader(bufferSize = 1024)
            .useLines { sequence ->
                sequence.flatMap { s ->
                    s.split(" ").map { it.toInt() }
                }.maxByOrNull { it }!!
            }
    }

    class Config(
        val filename: String,
        val numberOfCores: Int = 1
    ) {
        init {
            require(filename.isNotEmpty()) { "Provide a legit filename." }
        }
    }

    interface Callback {
        fun onGraphGenerated()
        fun onError(e: Exception)
    }
}