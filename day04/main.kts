import java.io.File

typealias Board = List<List<Int>>

val input = File("input.txt").readLines()
val draws = input.first().split(',').map { it.toInt() }
var boards = input.drop(2).joinToString("\n").split("\n\n").map { board ->
    board.split('\n').map { it.split(' ').filter { it.isNotBlank() }.map { it.toInt() } }
}

println("Part 1: ${part1()}")
println("Part 2: ${part2()}")

fun part1(): Int {
    for (draw in draws) {
        boards = update(boards, draw)
        val winningBoard = boards.find { bingo(it) }
        winningBoard?.let { return finalScore(it, draw) }
    }
    return -1
}

fun part2(): Int {
    for (draw in draws) {
        val updatedBoards = update(boards, draw)
        boards = updatedBoards.filter { !bingo(it) }
        if (boards.size == 0) return finalScore(updatedBoards.first(), draw)
    }
    return -1
}

fun update(boards: List<Board>, draw: Int): List<Board> =
    boards.map { board ->
        board.map { row ->
            row.map {
                if (it == draw) -1 else it
            }
        }
    }

fun bingo(board: Board) = board.any { row -> row.all { it == -1 } }
        || board.indices.any { i -> board.map { it[i] }.all { it == -1 } }

fun finalScore(board: Board, draw: Int) = board.map { it.filter { it != -1 }.sum() }.sum() * draw