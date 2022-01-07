import java.io.File

typealias Rules = Map<Pair<Char, Char>, Char>
val lines = File("input.txt").readLines()
val input = lines[0].toList()
val rules = lines.drop(2)
    .associate { val (pattern, inserted) = it.split(" -> "); (pattern[0] to pattern[1]) to inserted.first() }
println("Part 1: ${part1(input)}")
println("Part 2: ${part2(input)}")

fun part1(template: List<Char>): Int {
    var polymer = template
    repeat(10) { polymer = applyRules(polymer) }
    val occurrences = polymer.groupBy { it }.mapValues { it.value.size }
    return occurrences.maxOf { it.value } - occurrences.minOf { it.value }
}

fun applyRules(polymer: List<Char>) =
    polymer.zipWithNext { a, b ->
        listOf(a, rules[a to b]!!)
    }.flatten() + listOf(polymer.last())

fun part2(template: List<Char>): Long {
    var pairOccurrences = template.zipWithNext().groupBy { it }.mapValues { it.value.size.toLong() }
    repeat(40) {
        pairOccurrences = pairOccurrences.map {
            val m = rules[it.key]!!
            listOf((it.key.first to m) to it.value, (m to it.key.second) to it.value)
        }.flatten().sumToMap()
    }
    val occurrences = pairOccurrences.map { listOf(it.key.first to it.value, it.key.second to it.value) }
        .flatten().sumToMap().mapValues { entry ->
            (entry.value + listOf(template.first(), template.last()).count { it == entry.key })/2
        }
    return occurrences.maxOf { it.value } - occurrences.minOf { it.value }
}

fun <K> List<Pair<K, Long>>.sumToMap() =
    this.groupBy { it.first }.mapValues { entry -> entry.value.sumOf { it.second } }