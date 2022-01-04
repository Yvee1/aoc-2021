import java.io.File
import java.util.*

val input = File("input.txt").readLines().map { row -> row.map { it.digitToInt() } }
println("Part 1: ${part1(input)}")
println("Part 2: ${part2(input)}")

fun part1(input: List<List<Int>>) = lowPoints(input).sumOf { it.second + 1 }

fun lowPoints(input: List<List<Int>>): List<Pair<Pair<Int, Int>, Int>> {
    val result = mutableListOf<Pair<Pair<Int, Int>,Int>>()
    for (i in input.indices){
        for (j in input[i].indices){
            val (c, adj) = neighbourhood(input, i, j)
            if (adj.all { c.second < it.second }) result.add(c)
        }
    }
    return result
}

fun neighbourhood(input: List<List<Int>>, i: Int, j: Int): Pair<Pair<Pair<Int, Int>, Int>, List<Pair<Pair<Int, Int>, Int>>> {
    val c = (i to j) to input[i][j]
    val l = input.getOrNull(i-1)?.let { (i-1 to j) to it[j] }
    val r = input.getOrNull(i+1)?.let { (i+1 to j) to it[j] }
    val u = input[i].getOrNull(j-1)?.let { (i to j-1) to it }
    val d = input[i].getOrNull(j+1)?.let { (i to j+1) to it }
    return Pair(c, listOfNotNull(l, r, u, d))
}

fun part2(input: List<List<Int>>): Int {
    fun bfs(start: Pair<Int, Int>): Int {
        val q: Queue<Pair<Int, Int>> = LinkedList()
        val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()

        q.add(start)
        seen.add(start)

        while (q.isNotEmpty()){
            val ij = q.remove()
            val (c, adj) = neighbourhood(input, ij.first, ij.second)
            adj.filter { c.second < it.second && it.second < 9 }.forEach { (kl, _) ->
                if (kl !in seen){
                    seen.add(kl)
                    q.add(kl)
                }
            }
        }

        return seen.size
    }
    return lowPoints(input).map { bfs(it.first) }.sortedDescending().take(3).reduce { x, y -> x * y }
}
