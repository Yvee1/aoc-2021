import java.io.File

val lines = File("input.txt").readLines()
val emptyLine = lines.withIndex().find { (_, s) -> s == "" }!!.index
val inputDots = lines.subList(0, emptyLine) .map { line -> val (x, y) = line.split(",").map { it.toInt() }; x to y }
val inputFolds = lines.subList(emptyLine + 1, lines.size).map { val (x, v) = it.drop("fold along ".length).split("="); Fold(x=="x", v.toInt()) }

println("Part 1: ${fold(inputDots, inputFolds[0]).size}")
val final = inputFolds.fold(inputDots) { acc, f -> fold(acc, f) }
println("Part 2:")
display(final)

data class Fold(val x: Boolean, val v: Int)
fun fold(dots: List<Pair<Int, Int>>, fold: Fold): List<Pair<Int, Int>> =
    dots.map { (x, y) ->
        if (fold.x) {
            (if (x < fold.v) x else 2 * fold.v - x) to y
        } else {
            x to if (y < fold.v) y else 2 * fold.v - y
        }
    }.toSet().toList()

fun display(dots: List<Pair<Int, Int>>){
    val sorted = dots.sortedWith(compareBy ({ it.second }, { it.first }))
    var i = 0
    val mx = sorted.maxOf { it.first }
    val my = sorted.maxOf { it.second }
    var (dx, dy) = sorted[i]
    for (y in 0..my){
        for (x in 0..mx){
            if (x == dx && y == dy){
                print("#")
                i++
                if (i in sorted.indices) {
                    dx = sorted[i].first
                    dy = sorted[i].second
                }
            } else {
                print(" ")
            }
        }
        println()
    }
}