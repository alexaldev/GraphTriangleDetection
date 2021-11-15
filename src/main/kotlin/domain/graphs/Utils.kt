package domain.graphs

/**
 * Similar to [takeWhile] but in reverse order. Searches for the first position
 * in the list that [predicate] is valid and keeps all the elements after that position
 * inclusively.
 */
inline fun List<String>.takeFrom(predicate: (String) -> Boolean): List<String> {
    var indexToStartFrom = 0
    for (index in this.indices) {
        if (!predicate(this[index])) {
            continue
        } else {
            indexToStartFrom = index
            break
        }
    }
    return this.subList(indexToStartFrom, this.size)
}

fun <E> Set<E>.allPairs(): Set<Pair<E, E>> {
    return when {
        this.isEmpty() -> emptySet()
        else -> {
            val result = hashSetOf<Pair<E,E>>()
            val setAsList = this.toList()
            for (i in setAsList.indices) {
                for (j in i until setAsList.size){
                    if (i != j) result.add(Pair(setAsList[i], setAsList[j]))
                }
            }
            result
        }
    }
}

fun <E> List<E>.allPairs(): List<Pair<E, E>> {
    return when {
        this.isEmpty() -> emptyList()
        else -> {
            val result = mutableListOf<Pair<E, E>>()
            for (i in 0 until this.size) {
                for (j in i until this.size){
                    if (i != j) result.add(Pair(this[i], this[j]))
                }
            }
            result
        }
    }
}