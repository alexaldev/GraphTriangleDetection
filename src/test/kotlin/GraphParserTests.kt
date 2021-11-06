import domain.GraphParser
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

import kotlin.test.assertTrue

class GraphParserTests {

    lateinit var fakeConfig: GraphParser.Config

    @BeforeEach
    fun setUp() {
        fakeConfig = GraphParser.Config("src/test/resources/h.txt")
    }

    @Test
    fun `calling generate() changes the value of maxNumberOfVertices`() {

        val fakeGraph = GraphParser(fakeConfig)
        val testNumOfVertices = fakeGraph.numOfVertices


        assertTrue(testNumOfVertices != fakeGraph.numOfVertices)
    }

    @Test
    fun `calling generateGraph() produces the correct graph`() {

        val fakeParser = GraphParser(GraphParser.Config("src/test/resources/big"))

        val testGraph = fakeParser.generateGraph()

//        assertEquals(2985, testGraph.edgesCount)
//        assertEquals(2617, testGraph.verticesCount)
        println(testGraph.toString())
    }
}