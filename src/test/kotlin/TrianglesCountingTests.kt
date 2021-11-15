import domain.*
import domain.graphs.Graph
import org.junit.jupiter.api.Test
import java.io.File

class TrianglesCountingTests {

    @Test
    fun `running nodes-iterator on the german roads graph works as expected`() {

        val config = GraphParser.Config(
            graphFile = GraphFile(File("/home/pesimatik/Thesis/road-germany-osm.mtx")),
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

        parser.parse { g -> trianglesCounter.compute(g, fakeTrianglesCompletion) }
    }

}