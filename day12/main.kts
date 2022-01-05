import java.io.File
import java.util.*

val input = File("input.txt").readLines().map { line -> line.split("-") }
val adj = constructAdjacencies(input)
println("Part 1: ${part1(adj)}")
println("Part 2: ${part2(adj)}")

fun part1(adj: Map<String, List<String>>): Int {
    val q: Queue<List<String>> = LinkedList()
    var noPaths = 0
    q.add(listOf("start"))
    while(q.isNotEmpty()){
        val u = q.remove()
        for (v in adj[u.last()]!!){
            if (v.first().isUpperCase() || v !in u){
                if (v == "end"){
                    noPaths++
                    continue
                }
                q.add(u + listOf(v))
            }
        }
    }
    return noPaths
}

fun part2(adj: Map<String, List<String>>): Int {
    val q: Queue<Pair<List<String>, Boolean>> = LinkedList()
    var noPaths = 0
    q.add(listOf("start") to true)
    while(q.isNotEmpty()){
        val (path, joker) = q.remove()
        for (v in adj[path.last()]!!){
            if (v.first().isUpperCase()) {
                q.add(path + listOf(v) to joker)
            } else {
                if (v == "end"){
                    noPaths++
                    continue
                }
                if (v !in path) {
                    q.add(path + listOf(v) to joker)
                } else if (joker && v != "start"){
                    q.add(path + listOf(v) to false)
                }
            }
        }
    }
    return noPaths
}

fun constructAdjacencies(input: List<List<String>>): Map<String, List<String>> {
    val adj = mutableMapOf<String, MutableList<String>>()
    adj.putAll(input.flatten().map { it to mutableListOf() })
    input.forEach { (u, v) ->
        adj[u]!!.add(v)
        adj[v]!!.add(u)
    }
    return adj
}