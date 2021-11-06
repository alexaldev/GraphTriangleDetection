import domain.GraphParser
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

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
        val testNumOfVertices = fakeGraph.maxNumOfVertices

        fakeGraph.generate()

        assertTrue(testNumOfVertices != fakeGraph.maxNumOfVertices)
    }
}