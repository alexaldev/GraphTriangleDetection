package domain

import domain.graphs.Graph

// Public API

class TrianglesResult(
    val numOfTriangles: Int
)

interface Callback {
    fun onResponse(result: TrianglesResult)
    fun onError(e: Exception)
}

class TrianglesCounter(private val algorithm: TrianglesCounterAlgorithm) {

    fun <T> compute(graph: Graph<T>, callback: Callback) {
        algorithm.compute(graph, callback)
    }
}

// -----------Internals -----------------

sealed class TrianglesCounterAlgorithm {
    abstract fun <T> compute(g: Graph<T>, callback: Callback)

    object Naive : TrianglesCounterAlgorithm() {
        override fun <T> compute(g: Graph<T>, callback: Callback) {
            TODO("Not yet implemented")

        }
    }

    // Add more implementations here.... TODO()

}