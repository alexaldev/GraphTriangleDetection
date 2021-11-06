package domain.graphs

abstract class Graph<T> {
    abstract fun generateMetrics(): List<GraphMetrics>
    abstract fun addEdge(sourceV: T, destinationV: T)
    abstract fun backedDataStructureDescription(): String
    enum class Type {
        ADJ_MAP, ADJ_MATRIX
    }
}

sealed class GraphMetrics(val name: String, val value: Double) {
    class NumOfVertices(count: Int) : GraphMetrics("|V|", count.toDouble())
    class NumOfEdges(count: Int) : GraphMetrics("|E|", count.toDouble())
}

// ---------- Implementations

class GraphWithAdjMatrix<T> : Graph<T>() {

    override fun generateMetrics(): List<GraphMetrics> {
        TODO("Not yet implemented")
    }

    override fun addEdge(sourceV: T, destinationV: T) {
        TODO("Not yet implemented")
    }

    override fun backedDataStructureDescription(): String {
        TODO("Not yet implemented")
    }
}

class GraphWithMap<T> : Graph<T>() {

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
}