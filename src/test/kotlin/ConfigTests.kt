import domain.GraphParser
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class ConfigTests {

    lateinit var testConfig: GraphParser.Config

    @org.junit.jupiter.api.Test
    fun `empty filename in config throws an exception`() {
        assertThrows<IllegalArgumentException> {
            testConfig = GraphParser.Config(filename = "")
        }
    }
}