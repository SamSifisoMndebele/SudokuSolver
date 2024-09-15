import kotlin.math.sqrt


class SudokuGrid {
    companion object {
        private val chars = mapOf(null to 0, '0' to 0, '1' to 1, '2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9,
            'A' to 10, 'B' to 11, 'C' to 12, 'D' to 14, 'E' to 15, 'F' to 16, 'G' to 17, 'H' to 18, 'I' to 19, 'K' to 20, 'J' to 21,
            'L' to 22, 'M' to 23, 'N' to 24, 'O' to 25)
        private val charsInv = chars.map { it.value to it.key }.toMap()
    }
    private val grid: Array<Array<SudokuCell>>
    private val intGrid: Array<IntArray>
        get() = grid.map { rows -> IntArray(rows.size) { rows[it].value } }.toTypedArray()
//    val subSize: Int
//        get() = sqrt(grid.size.toDouble()).toInt()

    constructor(stringGrid: String) {
        val intGrid = stringGrid.replace(Regex("[ \t\r]"), "")
            .replace(Regex("\n+"), "\n")
            .trim().split('\n')
            .map { it.toCharArray().map { char -> chars[char]!! }.toIntArray() }
            .toTypedArray()

        val grid = Array(intGrid.size) { Array(intGrid[0].size) { SudokuCell() } }
        for (i in intGrid.indices) {
            for (j in intGrid[i].indices) {
                val value = intGrid[i][j]
                grid[i][j] = SudokuCell(value, if (value == 0) getCandidates(intGrid, i, j) else setOf())
            }
        }
        this.grid = grid
    }
    constructor(intGrid: Array<IntArray>) {
        val grid = Array(intGrid.size) { Array(intGrid[0].size) { SudokuCell() } }
        for (i in intGrid.indices) {
            for (j in intGrid[i].indices) {
                val value = intGrid[i][j]
                grid[i][j] = SudokuCell(value, if (value == 0) getCandidates(intGrid, i, j) else setOf())
            }
        }
        this.grid = grid
    }

//    fun getCandidates(r: Int, c: Int): Set<Int> = getCandidates(intGrid, r, c)
    private fun getCandidates(grid: Array<IntArray>, r: Int, c: Int): Set<Int> {
        val candidates = sortedSetOf<Int>()
        for (num in 1 .. grid.size) {
            if (isValid(grid, r, c, num)) {
                candidates.add(num)
            }
        }
        return candidates
    }

    // Function to check if a number is safe to place in the Sudoku grid
//    fun isValid(r: Int, c: Int, value: Int): Boolean = isValid(intGrid, r, c, value)
    private fun isValid(grid: Array<IntArray>, r: Int, c: Int, value: Int): Boolean {
        // Check the row, column and subgrid
        val subSize = sqrt(grid.size.toDouble()).toInt()
        val subRow = (r / subSize) * subSize
        val subCol = (c / subSize) * subSize
        for (i in grid.indices) {
            if (grid[r][i] == value || grid[i][c] == value || grid[subRow + i / subSize][subCol + i % subSize] == value)
                return false
        }
        return true
    }

    private fun toGridString(): String = buildString {
        for (rows in grid) {
            for (cell in rows) {
                append(charsInv[cell.value] ?: '0')
                append('\t')
            }
            append('\n')
        }
    }
    private fun toSubGridString(): String = buildString {
        for (rows in grid) {
            for (cell in rows) {
                val charString = cell.candidates.joinToString("") { charsInv[it]?.toString() ?: "" }
                append(charString.padEnd(grid.size + 2))
            }
            append("\n\n")
        }
    }
    override fun toString(): String {
        return buildString {
            append(toGridString())
            append("-------------------------------------------------------------\n")
            append(toSubGridString())
        }
    }
}