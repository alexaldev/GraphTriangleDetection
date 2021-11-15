package domain

import domain.graphs.Graph
import domain.graphs.GraphWithAdjMatrix
import domain.graphs.GraphWithMap
import domain.graphs.takeFrom
import java.io.File
import java.lang.IllegalArgumentException
import java.util.stream.Collectors
import kotlin.streams.toList

class GraphParser(private val config: Config) {

    private val reader = config.graphFile.file.bufferedReader()
    private val isVerticesLine: (String) -> Boolean = {
        (it.first().isDigit()) && (it.split(' ').size == 2)
    }

    private fun max(l: List<String>) =
        l.map { lines ->
            lines.split(' ')
                .take(2)
                .map { it.toInt() }
        }
            .flatten()
            .maxByOrNull { it }!!

    // Public API

    fun parse(c: (Graph) -> Unit) {
        parse(object :  Callback {
            override fun onCompleted(graph: Graph) {
                c(graph)
            }
        })
    }

    private fun parse(callback: Callback) {

        val lines = reader.readLines().takeFrom(isVerticesLine)

        val result = when (config.graphType) {
            Graph.Type.ADJ_MATRIX -> GraphWithAdjMatrix(max(lines) + 1)
            Graph.Type.ADJ_MAP -> GraphWithMap()
        }

        lines
            .map { it.split(' ') }
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .forEach { result.addEdge(it.first, it.second) }

        callback.onCompleted(result)
    }

    class Config(
        val graphFile: GraphFile,
        val numberOfCores: Int = 1,
        val graphType: Graph.Type = Graph.Type.ADJ_MATRIX,
        val parsingProgress: Boolean = false
    )

    interface Callback {
        fun onCompleted(graph: Graph)
    }
}
