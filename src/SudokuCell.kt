data class SudokuCell(
    val value: Int = 0,
    val candidates: Set<Int> = setOf(),
) {
    val heuristic = candidates.size
}
