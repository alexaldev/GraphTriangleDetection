package domain

import domain.graphs.Graph
import domain.graphs.GraphWithMap
import java.io.File
import java.lang.IllegalArgumentException
import java.util.stream.Collectors

class GraphParser(private val config: Config) {

    private val graphFile = File(config.filename)
    private val reader = graphFile.bufferedReader()

    var numOfVertices: Int = 0
        private set

    // Public API

    fun parse(callback: (Graph<Int>) -> Unit) {

        // Graph file conditions check
        if (reader.readLine().split(' ').size != 2) {
            throw IllegalArgumentException("The graph file must contain lines in the following format: v1 v2")
        }

        var result: Graph<Int>

        when (config.graphType) {
            Graph.Type.ADJ_MATRIX -> {
                TODO()
            }
            Graph.Type.ADJ_MAP -> {
                result = GraphWithMap()
                reader.lines()
                    .parallel()
                    .map { it.split(' ')}
                    .map { Pair(it[0].toInt(), it[1].toInt())}
                     // Somehow take the above result and call Graph:addEdge()
                    .collect(Collectors.toList())
                    .forEach {
                        result.addEdge(it.first, it.second)
                    }
            }
        }

        callback(result)
    }

    class Config(
        val filename: String,
        val numberOfCores: Int = 1,
        val graphType: Graph.Type = Graph.Type.ADJ_MATRIX,
        val parsingProgress: Boolean = false,
        val generateMetricsOnTheFly: Boolean = false
    ) {
        init {
            require(filename.isNotEmpty()) { "Provide a legit filename." }
        }
    }
}
