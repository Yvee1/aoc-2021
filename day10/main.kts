import java.io.File
import java.util.*

val input = File("input.txt").readLines()
println("Part 1: ${part1(input)}")
println("Part 2: ${part2(input)}")

fun part1(input: List<String>): Int = input.mapNotNull { firstIllegalCharacter(it) }.sumOf { syntaxScore(it) }

fun part2(input: List<String>): Long =
    input
        .filter { firstIllegalCharacter(it) == null }
        .map {
            complete(it).fold(0L) { acc, c -> acc * 5 + completeScore(c) }
        }.sorted().middle()

fun <E> List<E>.middle(): E = this[this.size / 2]

fun complete(s: String): List<Char> {
    val stack: Stack<Char> = Stack()

    for (c in s){
        if (opening(c)) stack.push(c) else {
            if (!matches(stack.pop(), c)) error("Line is corrupted")
        }
    }

    val completers = mutableListOf<Char>()
    while(stack.isNotEmpty()){
        completers.add(match(stack.pop()))
    }
    return completers
}

fun opening(c: Char) = when(c) {
    '(', '[', '{', '<' -> true
    else -> false
}

fun match(c: Char): Char = when(c) {
    '(' -> ')'
    '[' -> ']'
    '{' -> '}'
    '<' -> '>'
    else -> error("Invalid character: $c")
}

fun matches(c: Char, d: Char) = d == match(c)

fun firstIllegalCharacter(s: String): Char? {
    val stack: Stack<Char> = Stack()

    for (c in s){
        if (opening(c)) stack.push(c) else {
            if (!matches(stack.pop(), c)) return c
        }
    }
    return null
}

fun syntaxScore(c: Char): Int = when(c) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> error("Invalid character: $c")
}

fun completeScore(c: Char): Int = when(c) {
    ')' -> 1
    ']' -> 2
    '}' -> 3
    '>' -> 4
    else -> error("Invalid character: $c")
}