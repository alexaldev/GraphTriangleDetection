package domain

import domain.graphs.Graph
import domain.graphs.GraphWithAdjMatrix
import domain.graphs.GraphWithMap
import java.io.File
import java.lang.IllegalArgumentException
import java.util.stream.Collectors
import kotlin.streams.toList

class GraphParser(private val config: Config) {

    private val reader = config.graphFile.file.bufferedReader()

    private fun max(l: List<String>) =
        l.map { lines ->
            lines.split(' ')
                .take(2)
                .map { it.toInt() }
        }
            .flatten()
            .maxByOrNull { it }!!

    // Public API

    fun parse(callback: (Graph<Int>) -> Unit) {
        parse {
            object : Callback {
                override fun onProgress(percentageCompleted: Float) {

                }

                override fun onCompleted(graph: Graph<Int>) {
                    callback(graph)
                }
            }
        }
    }

    fun parse(callback: Callback) {

        if (config.graphFile.isWeighted()) {
            println("Graph file contains weights, ignoring them.")
        }

        val lines = reader.readLines()
        var totalLines = lines.size.toFloat()

        val result = when (config.graphType) {
            Graph.Type.ADJ_MATRIX -> GraphWithAdjMatrix(max(lines) + 1)
            Graph.Type.ADJ_MAP -> GraphWithMap()
        }

        lines
            .parallelStream()
            .map { it.split(' ') }
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .toList()
            .forEachIndexed { index, pair ->
                if (((index / totalLines) * 100).toInt()% 10 == 0) {
                    callback.onProgress((index / totalLines) * 100)
                }
                result.addEdge(pair.first, pair.second)
            }

        callback.onCompleted(result)
    }

    class Config(
        val graphFile: GraphFile,
        val numberOfCores: Int = 1,
        val graphType: Graph.Type = Graph.Type.ADJ_MATRIX,
        val parsingProgress: Boolean = false,
        val generateMetricsOnTheFly: Boolean = false
    )

    interface Callback {
        fun onProgress(percentageCompleted: Float)
        fun onCompleted(graph: Graph<Int>)
    }
}
