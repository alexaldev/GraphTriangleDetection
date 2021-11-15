import domain.graphs.allPairs
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class UtilsTests {

    @ParameterizedTest
    @MethodSource("providesListsWithPairs")
    fun `allPairs() on a list works as expected`(input: List<Int>, expected: List<Pair<Int, Int>>) {

        assertEquals(expected, input.allPairs())
    }

    companion object {

        @JvmStatic
        fun providesListsWithPairs(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(1, 2, 3), listOf(Pair(1, 2), Pair(1, 3), Pair(2, 3))),
                Arguments.of(
                    emptyList<Int>(), emptyList<Int>()),
                Arguments.of(
                    listOf(0, 2, 3, 4), listOf(Pair(0, 2), Pair(0, 3), Pair(0, 4), Pair(2, 3), Pair(2, 4), Pair(3, 4)))
            )
        }
     }
}