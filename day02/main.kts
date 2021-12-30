import java.io.File

val input = File("input.txt").readLines()
val (pos1, depth1) = input.fold(Pair(0, 0)) { (x, d), line ->
    val (word, strn) = line.split(' ')
    val n = strn.toInt()
    when(word){
        "forward" -> Pair(x + n, d)
        "down"    -> Pair(x, d + n)
        "up"      -> Pair(x, d - n)
        else      -> Pair(x, d)
    }
}

println("Part 1: ${pos1 * depth1}")

val (pos2, depth2, aim) = input.fold(Triple(0, 0, 0)) { (x, d, a), line ->
    val (word, strn) = line.split(' ')
    val n = strn.toInt()
    when(word){
        "forward" -> Triple(x + n, d + n * a, a)
        "down"    -> Triple(x, d, a + n)
        "up"      -> Triple(x, d, a - n)
        else      -> Triple(x, d, a)
    }
}

println("Part 2: ${pos2 * depth2}")