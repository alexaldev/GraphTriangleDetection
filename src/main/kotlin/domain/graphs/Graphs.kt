package domain.graphs

import domain.TrianglesCounter
import domain.TrianglesCounterAlgorithm
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Stream

abstract class Graph {
    abstract fun generateMetrics(): List<GraphMetrics>
    abstract fun addEdge(sourceV: Int, destinationV: Int)
    abstract fun removeEdge(v: Int, u: Int): Boolean
    abstract fun neighbours(v: Int): Set<Int>
    abstract fun isEdge(v1: Int, v2: Int): Boolean
    abstract fun vertices(): Stream<Int>
    abstract fun degree(v: Int): Int
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

class GraphWithAdjMatrix(private val numOfVertices: Int) : Graph() {

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

    override fun neighbours(v: Int): Set<Int> {
        TODO("Not yet implemented.")
    }

    override fun isEdge(v1: Int, v2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun vertices(): Stream<Int> {
        TODO("Not yet implemented")
    }

    override fun degree(v: Int): Int {
        TODO("Not yet implemented")
    }
}

class GraphWithMap(trianglesCounter: TrianglesCounter = TrianglesCounter(TrianglesCounterAlgorithm.Naive)) : Graph() {

    private val adjacencyMap: HashMap<Int, HashSet<Int>> = HashMap()

    private val verticesCount: Int by lazy {
        adjacencyMap.size
    }

    private val edgesCount: Int by lazy {
        adjacencyMap
            .values
            .fold(0) { acc, hashSet -> acc + hashSet.size } / 2
    }

    override fun addEdge(sourceV: Int, destinationV: Int) {
        adjacencyMap
            .computeIfAbsent(sourceV) { HashSet() }
            .add(destinationV)
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

    override fun removeEdge(v: Int, u: Int) = adjacencyMap[v]?.remove(u) ?: false

    override fun neighbours(v: Int): Set<Int> = adjacencyMap[v] ?: emptySet()

    override fun isEdge(v1: Int, v2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun vertices(): Stream<Int> {
        return adjacencyMap.keys.stream()
    }

    override fun degree(v: Int): Int {
        TODO("Not yet implemented")
    }
}