import kotlin.math.sqrt

private val numbers = mapOf('A' to 10, 'B' to 11, 'C' to 12, 'D' to 13, 'E' to 14, 'F' to 15, 'G' to 16, 'H' to 17, 'I' to 18, 'J' to 19, 'K' to 20,
    'L' to 21, 'M' to 22, 'N' to 23, 'O' to 24, 'P' to 25, 'Q' to 26, 'R' to 27, 'S' to 28, 'T' to 29, 'U' to 30, 'V' to 31, 'W' to 32, 'X' to 34, 'Y' to 35, 'Z' to 36)

private fun String.toIntGrid(): Array<out IntArray> = replace(Regex("[ \t\r]"), "")
    .replace(Regex("\n+"), "\n").trim()
    .split('\n')
    .map { it.toCharArray().map { char -> if (char.isDigit()) char.digitToInt() else numbers[char]!! }.toIntArray() }
    .toTypedArray()
private fun Array<out IntArray>.toStringGrid(): String {
    val letters = numbers.map { it.value to it.key }.toMap()
    val buffer = StringBuilder()
    for ((i, rows) in this.withIndex()) {
        if (i > 0) buffer.append('\n')
        for ((j, element) in rows.withIndex()) {
            if (j > 0) buffer.append("\t")
            if (element < 10) buffer.append(element) else buffer.append(letters[element])
        }
    }
    return buffer.toString()
}

private fun isValid(intGrid: Array<out IntArray>, r: Int, c: Int, value: Int): Boolean {
    // Check the row, column and subgrid
    val subSize = sqrt(intGrid.size.toDouble()).toInt()
    val subRow = (r / subSize) * subSize
    val subCol = (c / subSize) * subSize
    for (i in intGrid.indices) {
        val subR = subRow + i / subSize
        val subC = subCol + i % subSize
        if (intGrid[r][i] == value || intGrid[i][c] == value || intGrid[subR][subC] == value)
            return false
    }
    return true
}
private fun getCandidates(intGrid: Array<out IntArray>, r: Int, c: Int): Set<Int> {
    val value = intGrid[r][c]
    if (value != 0) return emptySet()

    val candidates = sortedSetOf<Int>()
    for (num in 1 .. intGrid.size) {
        if (isValid(intGrid, r, c, num)) {
            candidates.add(num)
        }
    }
    return candidates
}
data class Position(
    val row: Int = -1,
    val col: Int = -1,
    val candidates: Set<Int> = emptySet()
)
private fun nextPosition(grid: Array<out IntArray>): Position {
    var position = Position()
    var minCandidates = 100
    val f = grid.flatMap { it.toList() }.toIntArray()
    java.util.Arrays.parallelSort(f)

    for (r in grid.indices) {
        for (c in grid[r].indices) {
            if (grid[r][c] == 0) {
                val candidates = getCandidates(grid, r, c)
                if (candidates.size < minCandidates) {
                    minCandidates = candidates.size
                    position = Position(r, c, candidates)
                }
            }
        }
    }
    return position
}
private fun solve(intGrid: Array<out IntArray>): Array<out IntArray>? {
    val pos = nextPosition(intGrid)
    if (pos == Position()) { return intGrid }  // All cells filled

    for (candidate in pos.candidates) {
        intGrid[pos.row][pos.col] = candidate
        val solution = solve(intGrid)
        if (solution != null) return solution
        intGrid[pos.row][pos.col] = 0 // Backtrack if the candidate doesn't lead to a solution
    }
    return null // No solution found for this configuration
}

/**
 * Solves a Sudoku grid
 * @param stringGrid grid of number characters from `1` to `9` then `A` to `P` (for numbers from 10 to 25)
 */
fun solveSudoku(stringGrid: String) {
    val grid = stringGrid.toIntGrid()
    println("Problem:")
    println(grid.toStringGrid())

    val solution = solve(grid)
    if (solution != null) {
        println("Solution:")
        println(solution.toStringGrid())
    }
    else println("No solution")
}