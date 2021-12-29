import java.io.File

val input = File("input.txt").readLines().map { it.toInt() }
println("Part 1: ${increases(input)}")
println("Part 2: ${increasesWindowed(input, 3)}")

fun increases(input: List<Int>): Int = input.zipWithNext { a, b -> if (a < b) 1 else 0 }.sum()
fun increasesWindowed(input: List<Int>, k: Int): Int =
    input.windowed(k).zipWithNext { a, b -> if (a.first() < b.last()) 1 else 0 }.sum()