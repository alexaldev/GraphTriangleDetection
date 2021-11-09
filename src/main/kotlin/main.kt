import domain.GraphFile
import domain.GraphParser
import domain.graphs.Graph
import java.io.File

fun main(args: Array<String>) {

    val graphFile = GraphFile(File(args[0]))

    val config = GraphParser.Config(
        graphFile = graphFile,
        graphType = Graph.Type.ADJ_MAP,
        parsingProgress = true
    )

    GraphParser(config).parse(object : GraphParser.Callback {
        override fun onProgress(percentageCompleted: Float) {
            println("Progress: $percentageCompleted")
        }

        override fun onCompleted(graph: Graph<Int>) {
            println("Graph loaded in-memory")
            graph.generateMetrics().forEach { println(it) }
        }
    })
}