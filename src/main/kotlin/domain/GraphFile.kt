package domain

import java.io.File

/**
 * Unfortunately necessary class because graph files from the
 * repository may contain weighted or unweighted graphs and I still
 * do not know know the official format I will work with.
 */
class GraphFile(
    val file: File,
    val format: String = "v v"
) {
    /**
     * Checks if the lines in the file are in the form of [v v w].
     */
    fun isWeighted() = file.bufferedReader().readLine().split(' ').size == 3
}