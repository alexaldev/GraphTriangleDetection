package domain.graphs

import domain.TrianglesCounter
import domain.TrianglesCounterAlgorithm

abstract class Graph<T> {
    abstract fun generateMetrics(): List<GraphMetrics>
    abstract fun addEdge(sourceV: T, destinationV: T)
    abstract fun removeEdge(v: T, u: T): Boolean
    abstract fun neighbours(v: T): List<T>
    abstract fun backedDataStructureDescription(): String
    enum class Type {
        ADJ_MAP, ADJ_MATRIX
    }
}

sealed class GraphMetrics(val name: String, val value: Double) {
    data class NumOfVertices(val count: Int) : GraphMetrics("|V|", count.toDouble())
    data class NumOfEdges(val count: Int) : GraphMetrics("|E|", count.toDouble())
    data class NumOfTotalTriangles(val count: Int) : GraphMetrics("Total count of triangles", count.toDouble())
}

// ---------- Implementations -----------------

class GraphWithAdjMatrix(private val numOfVertices: Int) : Graph<Int>() {

    private val matrix = MutableList(numOfVertices) { MutableList(numOfVertices) {0} }

    private val verticesCount: Int by lazy { numOfVertices }
    private val edgesCount: Int by lazy {
        matrix.sumOf { row -> row.count { it == 1 } }
    }

    override fun generateMetrics(): List<GraphMetrics> {
        return listOf(
            GraphMetrics.NumOfVertices(verticesCount),
            GraphMetrics.NumOfEdges(edgesCount)
        )
    }

    override fun addEdge(sourceV: Int, destinationV: Int) {
        matrix[sourceV][destinationV] = 1
    }

    override fun backedDataStructureDescription(): String {
        TODO("Not yet implemented")
    }

    override fun removeEdge(v: Int, u: Int): Boolean {
        val edgeExists = (matrix[v][u] == 1)
        return if (edgeExists) {
            matrix[v][u] = 0
            true
        } else {
            false
        }
    }

    override fun neighbours(v: Int): List<Int> {
        TODO("Not yet implemented.")
    }
}

class GraphWithMap<T>(trianglesCounter: TrianglesCounter = TrianglesCounter(TrianglesCounterAlgorithm.Naive)) : Graph<T>() {

    private val adjacencyMap: HashMap<T, HashSet<T>> = HashMap()

    private val verticesCount: Int by lazy {
        adjacencyMap.size
    }

    private val edgesCount: Int by lazy {
        adjacencyMap
            .values
            .fold(0) { acc, hashSet -> acc + hashSet.size } / 2
    }

    override fun addEdge(sourceV: T, destinationV: T) {
        // Add edge to source vertex / node.
        adjacencyMap
            .computeIfAbsent(sourceV) { HashSet() }
            .add(destinationV)
        // Add edge to destination vertex / node.
        adjacencyMap
            .computeIfAbsent(destinationV) { HashSet() }
            .add(sourceV)
    }

    override fun toString(): String = StringBuffer().apply {
        for (key in adjacencyMap.keys) {
            append("$key -> ")
            append(adjacencyMap[key]?.joinToString(", ", "[", "]\n"))
        }
    }.toString()

    override fun generateMetrics(): List<GraphMetrics> {
        return listOf(
            GraphMetrics.NumOfVertices(verticesCount),
            GraphMetrics.NumOfEdges(edgesCount)
        )
    }

    override fun backedDataStructureDescription() = """
        |This graph is represented by an adjacency hashmap. 
        |It keeps in-memory a hashmap with this structure:
        |[V] -> [neighbour1, neighnour2,...]
        |...
        |...
    """.trimMargin()

    override fun removeEdge(v: T, u: T) = adjacencyMap[v]?.remove(u) ?: false

    override fun neighbours(v: T): List<T> = adjacencyMap[v]?.toList() ?: emptyList()
}