import domain.*
import domain.graphs.Graph
import domain.graphs.takeFrom
import java.io.File

fun main(args: Array<String>) {

    val config = GraphParser.Config(
        graphFile = GraphFile(File(args[0])),
        graphType = Graph.Type.ADJ_MAP
    )
    val parser = GraphParser(config)
    val trianglesCounter = TrianglesCounter(TrianglesCounterAlgorithm.NodeIterator)
    val fakeTrianglesCompletion = object : Callback {
        override fun onResponse(result: TrianglesResult) {
            println("Total triangles: ${result.numOfTriangles}")
        }
        override fun onError(e: Exception) {
        }
    }

    parser.parse { g ->
        g.generateMetrics().forEach { println(it.toString()) }
        println("Graph loaded in memory. Computing triangles...")
        trianglesCounter.compute(g, fakeTrianglesCompletion)
    }
}