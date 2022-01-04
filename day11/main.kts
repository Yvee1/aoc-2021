import java.io.File

val input = File("input.txt").readLines().map { row -> row.map { c -> c.digitToInt() } }
println(part1(input))
println(part2(input))

fun part1(input: List<List<Int>>): Int {
    var state = input
    var totalFlashes = 0
    repeat(100) {
        val (newState, flashes) = step(state)
        state = newState
        totalFlashes += flashes
    }
    return totalFlashes
}

fun part2(input: List<List<Int>>): Int {
    var state = input
    val n = input.sumOf { it.size }
    var i = 1
    while (true) {
        val (newState, flashes) = step(state)
        state = newState
        if (flashes == n) return i
        i++
    }
}

fun step(state: List<List<Int>>): Pair<List<List<Int>>, Int> {
    val newState: List<MutableList<Int?>> = state.map { row -> row.map { it + 1 }.toMutableList() }
    val flashed = mutableListOf<Pair<Int, Int>>()
    while(true){
        val flashing = newState.withIndex().map { (i, row) -> row.withIndex().filter { (_, v) -> v != null && v > 9 }.map { i to it.index } }.flatten()
        flashing.forEach { (i, j) -> newState[i][j] = null }
        flashed.addAll(flashing)
        if (flashing.isEmpty()) break
        flashing.forEach { (i, j) ->
            for (di in -1..1)
                for (dj in -1..1)
                    if (i + di >= 0 && i + di < newState.size && j + dj >= 0 && j + dj < newState[0].size)
                        newState[i+di][j+dj]?.let { newState[i+di][j+dj] = it+1 }
        }
    }
    flashed.forEach { (i, j) -> newState[i][j] = 0 }
    return newState.map { it.requireNoNulls() } to flashed.size
}