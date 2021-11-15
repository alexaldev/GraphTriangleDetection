package domain

import domain.graphs.Graph
import domain.graphs.allPairs

// Public API

class TrianglesResult(
    val triangles: List<Triple<Int, Int, Int>>,
    val numOfTriangles: Int
)

interface Callback {
    fun onResponse(result: TrianglesResult)
    fun onError(e: Exception)
}

class TrianglesCounter(private val algorithm: TrianglesCounterAlgorithm) {

    fun compute(graph: Graph, callback: Callback) {
        algorithm.compute(graph, callback)
    }
}

// -----------Internals -----------------

sealed class TrianglesCounterAlgorithm {
    abstract fun compute(g: Graph, callback: Callback)

    object Naive : TrianglesCounterAlgorithm() {
        override fun compute(g: Graph, callback: Callback) {

        }
    }

    object NodeIterator : TrianglesCounterAlgorithm() {

        override fun compute(g: Graph, callback: Callback) {

            val triangles = mutableListOf<Triple<Int, Int, Int>>()
            g.vertices()
                .forEach { vertex ->
                    g.neighbours(vertex)
                        .allPairs()
                        .filter { pair -> g.neighbours(pair.first).contains(pair.second) }
                        .forEach { triangles.add(Triple(vertex, it.first, it.second)) }
                }

            callback.onResponse(TrianglesResult(triangles, triangles.size))
        }
    }
}