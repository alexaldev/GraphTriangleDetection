#### Να επικεντρωθώ στο performance evaluation ή και σε κάποια βελτίωση;

#### Πώς θα γίνει η σύγκριση με το state of the art που κυκλοφορεί; 

#### Πόσα είδη γράφων περιλαμβάνουν οι αλγόριθμοι υπολογισμού; 
Directed-undirected, weighted ή οχι ; 
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

#### Για τι μεγέθη μιλάμε; 

#### Να συμφωνήσουμε σε ένα test-set από γράφους για να τρέχω tests ? 

#### Δυνατότητες του jar
 * Επιλογή αλγορίθμων
 * Επιλογή γράφου
 * Επιλογή εξόδου αποτελεσμάτων

#### Benchmarking ? 
Μπορώ να κάνω integrate την JMH έπειτα από διάβασμα. 