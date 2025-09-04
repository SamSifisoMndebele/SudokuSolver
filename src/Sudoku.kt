import java.util.stream.IntStream
import kotlin.math.sqrt

/**
 * A map to convert characters 'A' through 'Z' to their corresponding integer values 10 through 36.
 * This is used for Sudoku grids that have a size greater than 9x9.
 */
private val numbers = mapOf('A' to 10, 'B' to 11, 'C' to 12, 'D' to 13, 'E' to 14, 'F' to 15, 'G' to 16, 'H' to 17, 'I' to 18, 'J' to 19, 'K' to 20,
    'L' to 21, 'M' to 22, 'N' to 23, 'O' to 24, 'P' to 25, 'Q' to 26, 'R' to 27, 'S' to 28, 'T' to 29, 'U' to 30, 'V' to 31, 'W' to 32, 'X' to 34, 'Y' to 35, 'Z' to 36)

/**
 * Converts a string representation of a Sudoku grid into a 2D integer array.
 * The string can contain spaces, tabs, and newlines, which will be removed.
 * Characters 'A'-'Z' are converted to integers 10-36.
 * @return A 2D integer array representing the Sudoku grid.
 */
private fun String.toIntGrid(): Array<out IntArray> = replace(Regex("[ \t\r]"), "")
    .replace(Regex("\n+"), "\n").trim()
    .split('\n')
    .map { it.toCharArray().map { char -> if (char.isDigit()) char.digitToInt() else numbers[char]!! }.toIntArray() }
    .toTypedArray()

/**
 * Converts a 2D integer array representing a Sudoku grid into a formatted string.
 * Numbers less than 10 are represented as digits, while numbers 10 and greater are represented by characters 'A' onwards.
 * @return A string representation of the Sudoku grid.
 */
private fun Array<out IntArray>.toStringGrid(): String {
    val buffer = StringBuilder()
    for ((i, rows) in this.withIndex()) {
        if (i > 0) buffer.append('\n')
        for ((j, element) in rows.withIndex()) {
            if (j > 0) buffer.append("\t")
            if (element < 10) buffer.append(element) else buffer.append('A' + element - 10)
        }
    }
    return buffer.toString()
}

/**
 * Checks if a given value is valid for a specific cell in the Sudoku grid.
 * A value is valid if it does not already exist in the same row, column, or subgrid.
 * @param grid The Sudoku grid.
 * @param r The row index of the cell.
 * @param c The column index of the cell.
 * @param value The value to check.
 * @return `true` if the value is valid, `false` otherwise.
 */
private fun isValid(grid: Array<out IntArray>, r: Int, c: Int, value: Int): Boolean {
    // Check the row, column and subgrid
    val subSize = sqrt(grid.size.toDouble()).toInt()
    val subRow = (r / subSize) * subSize
    val subCol = (c / subSize) * subSize
    for (i in grid.indices) {
        val subR = subRow + i / subSize
        val subC = subCol + i % subSize
        if (grid[r][i] == value || grid[i][c] == value || grid[subR][subC] == value)
            return false
    }
    return true
}

/**
 * Gets the set of possible candidate numbers for a given empty cell in the Sudoku grid.
 * @param grid The Sudoku grid.
 * @param r The row index of the cell.
 * @param c The column index of the cell.
 * @return A sorted set of candidate numbers.
 */
private fun getCandidates(grid: Array<out IntArray>, r: Int, c: Int): Set<Int> {
    val candidates = LinkedHashSet<Int>()
    for (num in 1 .. grid.size) {
        if (isValid(grid, r, c, num)) {
            candidates.add(num)
        }
    }
    return candidates.toSortedSet()
}

/**
 * Gets the set of possible candidate numbers for a given empty cell in the Sudoku grid in parallel.
 * @param grid The Sudoku grid.
 * @param r The row index of the cell.
 * @param c The column index of the cell.
 * @return A sorted set of candidate numbers.
 */
private fun getCandidatesParallel(grid: Array<out IntArray>, r: Int, c: Int): Set<Int> {
    val candidates = LinkedHashSet<Int>()
    IntStream.range(1, grid.size + 1).parallel()
        .forEach { num ->
            if (isValid(grid, r, c, num)) {
                candidates.add(num)
            }
        }
    return candidates.toSortedSet()
}

/**
 * Represents a position in the Sudoku grid, including its row, column, and the set of possible candidate numbers.
 * @property row The row index of the position. Defaults to -1.
 * @property col The column index of the position. Defaults to -1.
 * @property candidates The set of candidate numbers for this position. Defaults to an empty set.
 */
data class Position(
    val row: Int = -1,
    val col: Int = -1,
    val candidates: Set<Int> = emptySet()
)

/**
 * Finds the next empty cell to fill in the Sudoku grid.
 * It uses a heuristic to select the cell with the minimum number of candidates to improve performance.
 * @param grid The Sudoku grid.
 * @return A [Position] object representing the next cell to fill, or a default [Position] object if all cells are filled.
 */
private fun nextPosition(grid: Array<out IntArray>): Position {
    var position = Position()
    var minCandidates = 100
    for (r in grid.indices) {
        for (c in grid.indices) {
            if (grid[r][c] == 0) {
                val candidates = getCandidates(grid, r, c)
                if (candidates.size < minCandidates) {
                    minCandidates = candidates.size
                    position = Position(r, c, candidates)
                    if (minCandidates == 1) return position
                }
            }
        }
    }
    return position
}

/**
 * Finds the next empty cell to fill in the Sudoku grid in parallel.
 * It uses a heuristic to select the cell with the minimum number of candidates to improve performance.
 * @param grid The Sudoku grid.
 * @return A [Position] object representing the next cell to fill, or a default [Position] object if all cells are filled.
 */
private fun nextPositionParallel(grid: Array<out IntArray>): Position {
    var position = Position()
    var minCandidates = 100
    IntStream.range(0, grid.size).parallel()
        .forEach { r ->
            IntStream.range(0, grid[r].size).parallel()
                .forEach { c ->
                    if (grid[r][c] == 0) {
                        val candidates = getCandidatesParallel(grid, r, c)
                        if (candidates.size < minCandidates) {
                            minCandidates = candidates.size
                            position = Position(r, c, candidates)
                        }
                    }
                }
        }
    return position
}

/**
 * Solves the Sudoku puzzle using a backtracking algorithm.
 * @param grid The Sudoku grid to solve.
 * @return The solved Sudoku grid as a 2D integer array, or `null` if no solution is found.
 */
private fun solve(grid: Array<out IntArray>): Array<out IntArray>? {
    val pos = nextPosition(grid)
    if (pos == Position()) { return grid }  // All cells filled

    for (candidate in pos.candidates) {
        grid[pos.row][pos.col] = candidate
        val solution = solve(grid)
        if (solution != null) return solution
        grid[pos.row][pos.col] = 0 // Backtrack if the candidate doesn't lead to a solution
    }
    return null // No solution found for this configuration
}

/**
 * Solves the Sudoku puzzle using a backtracking algorithm in parallel.
 * @param grid The Sudoku grid to solve.
 * @return The solved Sudoku grid as a 2D integer array, or `null` if no solution is found.
 */
private fun solveParallel(grid: Array<out IntArray>): Array<out IntArray>? {
    val pos = nextPositionParallel(grid)
    if (pos == Position()) { return grid }  // All cells filled

    for (candidate in pos.candidates) {
        grid[pos.row][pos.col] = candidate
//        println()
//        println(candidate)
//        println(grid.toStringGrid())
        val solution = solveParallel(grid)
        if (solution != null) return solution
        grid[pos.row][pos.col] = 0 // Backtrack if the candidate doesn't lead to a solution
    }
    return null // No solution found for this configuration
}

/**
 * Extension function on [String] to solve a Sudoku puzzle.
 * @param parallel If `true`, the solver will use a parallel algorithm. Defaults to `true`.
 * @return The solved Sudoku grid as a 2D integer array, or `null` if no solution is found.
 */
fun String.sudokuSolve(parallel: Boolean = true): Array<out IntArray>? {
    val grid = toIntGrid()
    println("Problem:")
    println(grid.toStringGrid())

    val solution = if (parallel) solveParallel(grid) else solve(grid)
    if (solution != null) {
        println("Solution:")
        println(solution.toStringGrid())
    }
    else println("No solution")
    println("------------------------------------------------------")
    return solution
}