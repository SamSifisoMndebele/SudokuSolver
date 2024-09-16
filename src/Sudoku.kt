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

private class Cell(intGrid: Array<out IntArray>, val row: Int, val col: Int) {
    val value = intGrid[row][col]
    val candidates = getCandidates(intGrid, row, col)
    val heuristic get() = candidates.size

    override fun toString(): String = value.toString()
}

private fun solve(intGrid: Array<out IntArray>): Array<out IntArray>? {
    val grid = Array(intGrid.size) { row -> Array(intGrid[0].size) { col -> Cell(intGrid, row, col) } }
    val cell = grid.flatten().filter { it.heuristic != 0 }.minByOrNull { it.heuristic }

    if (cell == null && grid.all { it -> it.all { it.value != 0 } }) return intGrid // All cells filled
    else if (cell == null) return null

    for (candidate in cell.candidates) {
        intGrid[cell.row][cell.col] = candidate
        val solution = solve(intGrid)
        if (solution != null) return solution
        intGrid[cell.row][cell.col] = 0 // Backtracking
    }
    return null // No solution found for this configuration
}

/**
 * Solves a Sudoku grid
 * @param stringGrid grid of number characters from `1` to `9` then `A` to `P` (for numbers from 10 to 25)
 * @sample `1 0 0 0
 * 0 0 2 0
 * 0 3 0 0
 * 0 0 0 4`
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