import java.io.File
import java.util.*

val input = File("input.txt").readLines().map { row -> row.toList().map { it.digitToInt() } }
println("Part 1: ${dijkstra(input)}")
println("Part 2: ${dijkstra(computeAllRisks(input))}")

fun computeAllRisks(tile: List<List<Int>>): List<List<Int>> {
    val n = tile.size
    val m = tile[0].size
    return List(n * 5) { i ->
        List(m * 5) { j ->
            1 + ((tile[i % n][j % m] + i / n + j / m - 1) % 9)
        }
    }
}

fun dijkstra(risks: List<List<Int>>): Int {
    val n = risks.size
    val m = risks[0].size
    val dist = List(n) { MutableList(m) { Int.MAX_VALUE } }
    dist[0][0] = 0
    val q = PriorityQueue<Pair<Int, Int>>(compareBy { dist[it.first][it.second] })
    q.addAll(risks.flatMapIndexed { i, row -> row.indices.map { j -> i to j } })

    while(q.isNotEmpty()){
        val u = q.remove()
        val (i, j) = u
        val adj = listOf(i - 1 to j, i + 1 to j, i to j - 1, i to j + 1).filter { (k, l) ->
            k in 0 until n && l in 0 until m
        }
        for (v in adj) {
            val (k, l) = v
            if (dist[i][j] < dist[k][l] - risks[k][l]) {
                dist[k][l] = dist[i][j] + risks[k][l]
                q.add(k to l)
            }
        }
    }
    return dist[n-1][m-1]
}