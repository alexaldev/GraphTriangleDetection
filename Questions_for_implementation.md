#### Πόσα είδη γράφων περιλαμβάνουν οι αλγόριθμοι υπολογισμού; 

#### Οι γράφοι στους οποίους θα κάνουμε evaluate είναι της μορφής [v: Int, v: Int] ; 

#### Τι ξερετε απο functional programming ? Αυτό σίγουρα δεν ειναι σωστό:
reader.lines()
.parallel()
.map { it.split(' ')}
.map { Pair(it[0].toInt(), it[1].toInt())}
.collect(Collectors.toList())
.forEach {
result.addEdge(it.first, it.second)
}

#### Να προχωρήσω με Executors framework ?  