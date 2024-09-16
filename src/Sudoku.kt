import kotlin.math.sqrt

class Sudoku {
    private var grid: Array<Array<Cell>>
//    val subSize: Int
//        get() = sqrt(grid.size.toDouble()).toInt()

//    private fun toGridString(): String = buildString {
//        for (rows in grid) {
//            for (cell in rows) {
//                append(charsInv[cell.value] ?: '0')
//                append('\t')
//            }
//            append('\n')
//        }
//    }
//    private fun toSubGridString(): String = buildString {
//        for` (rows in grid) {
//            for (cell in rows) {
//                val charString = cell.candidates.joinToString("") { charsInv[it]?.toString() ?: "" }
//                append(charString.padEnd(grid.size + 2))
//            }
//            append("\n\n")
//        }
//    }
    override fun toString(): String {
        return buildString {
            for (rows in grid) {
                for (cell in rows) {
                    append(charsInv[cell.value] ?: '0')
                    append('\t')
                }
                append('\n')
            }
        }
    }

    constructor(grid: Array<Array<Cell>>) {
        val newGrid = Array(grid.size) { r -> Array(grid[0].size) { c -> Cell(row = r, col = c) } }
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                val value = grid[r][c].value
                val candidates = if (value == 0) getCandidates(grid.toIntGrid(), r, c) else setOf()
                newGrid[r][c] = Cell(value, candidates, r, c)
            }
        }
        this.grid = newGrid
    }
    constructor(intGrid: Array<IntArray>) {
        val newGrid = Array(intGrid.size) { r -> Array(intGrid[0].size) { c -> Cell(row = r, col = c) } }
        for (r in intGrid.indices) {
            for (c in intGrid[r].indices) {
                val value = intGrid[r][c]
                newGrid[r][c] = Cell(value, if (value == 0) getCandidates(intGrid, r, c) else setOf(), r, c)
            }
        }
        this.grid = newGrid
    }
    constructor(stringGrid: String): this(stringGrid.replace(Regex("[ \t\r]"), "")
        .replace(Regex("\n+"), "\n").trim().split('\n')
        .map { it.toCharArray().map { char -> chars[char]!! }.toIntArray() }.toTypedArray())

    companion object {
        private val chars = mapOf(null to 0, '0' to 0, '1' to 1, '2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9,
            'A' to 10, 'B' to 11, 'C' to 12, 'D' to 13, 'E' to 14, 'F' to 15, 'G' to 16, 'H' to 17, 'I' to 18, 'J' to 19, 'K' to 20, 'L' to 21,
            'M' to 22, 'N' to 23, 'O' to 24, 'P' to 25)
        private val charsInv = chars.map { it.value to it.key }.toMap()

        private fun isValid(grid: Array<IntArray>, r: Int, c: Int, value: Int): Boolean {
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
        private fun getCandidates(grid: Array<IntArray>, r: Int, c: Int): Set<Int> {
            val candidates = sortedSetOf<Int>()
            for (num in 1 .. grid.size) {
                if (isValid(grid, r, c, num)) {
                    candidates.add(num)
                }
            }
            return candidates
        }

        data class Cell(
            val value: Int = 0,
            val candidates: Set<Int> = setOf(),
            val row: Int,
            val col: Int
        ) {
            val heuristic get() = candidates.size
        }

        private fun Array<Array<Cell>>.toIntGrid(): Array<IntArray> = map { rows -> IntArray(rows.size) { rows[it].value } }.toTypedArray()

        fun solve(grid: Array<Array<Cell>>): Array<Array<Cell>>? {
            val cell = grid.flatten().filter { it.heuristic != 0 }.minByOrNull { it.heuristic }
            if (cell == null && grid.all { it -> it.all { it.value != 0 } }) return grid // All cells filled
            else if (cell == null) return null

            for (candidate in cell.candidates) {
                val sudoku = Sudoku(grid.apply {
                    this[cell.row][cell.col] = Cell(candidate, cell.candidates.filter { it != candidate }.toSet(), row = cell.row, col = cell.col)
                })
                val solution = solve(sudoku.grid) // Backtracking
                if (solution != null) return solution
            }
            return null // No solution found for this configuration
        }
    }
//    fun getCandidates(r: Int, c: Int): Set<Int> = getCandidates(intGrid, r, c)

    fun solve() {
        println("Problem:")
        println(toString())

        val solution = solve(grid)?.let { s -> Sudoku(s.map { a -> IntArray(a.size) { a[it].value } }.toTypedArray()) }
        if (solution != null) {
            println("Solution:")
            println(solution)
        }
        else println("No solution")
    }
}