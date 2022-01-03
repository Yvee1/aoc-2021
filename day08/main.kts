import java.io.File

val input = File("input.txt").readLines().map { it.split(" | ").map { it.split(' ') } }
println("Part 1: ${input.sumOf { line -> line[1].count { it.length in listOf(2, 3, 4, 7) } }}")
println("Part 2: ${part2(input)}")

fun part2(input: List<List<List<String>>>): Int =
    input.sumOf { line ->
        val m = decode(line[0])
        line[1].map { output ->
            output.map { m[it]!! }.parse()
        }.reduce { acc, v -> acc * 10 + v }
    }

fun List<Char>.parse(): Int =
    when(this.sorted().joinToString("")){
        "abcefg" -> 0
        "cf" -> 1
        "acdeg" -> 2
        "acdfg" -> 3
        "bcdf" -> 4
        "abdfg" -> 5
        "abdefg" -> 6
        "acf" -> 7
        "abcdefg" -> 8
        "abcdfg" -> 9
        else -> -1
    }

fun decode(inputPatterns: List<String>): Map<Char, Char>{
    val wireToSegment: MutableMap<Char, Char> = mutableMapOf()
    val one = inputPatterns.find { it.length == 2 }!!.toList()
    val seven = inputPatterns.find { it.length == 3 }!!.toList()
    val four = inputPatterns.find { it.length == 4 }!!.toList()
    val three = inputPatterns.find { seq -> seq.length == 5 && one.all { seq.contains(it) } }!!.toList()
    wireToSegment['a'] = (seven - one).first()
    wireToSegment['g'] = (three - four - wireToSegment['a']!!).first()
    wireToSegment['d'] = (three - seven - wireToSegment['g']!!).first()
    wireToSegment['b'] = (four - three).first()
    val five = inputPatterns.find { seq -> seq.length == 5 && seq.contains(wireToSegment['b']!!) }!!.toList()
    wireToSegment['f'] = (five - wireToSegment.values).first()
    wireToSegment['c'] = (one - wireToSegment['f']!!).first()
    wireToSegment['e'] = ("abcdefg".toList() - wireToSegment.values).first()
    return wireToSegment.entries.associateBy({ it.value }) { it.key }
}