fun main() {

    val grid0 = arrayOf(
        intArrayOf(1,0,  0,0),
        intArrayOf(0,0,  2,0),

        intArrayOf(0,3,  0,0),
        intArrayOf(0,0,  0,4)
    )
    val gridStr0 = """
        1 0 0 0
        0 0 2 0
        0 3 0 0
        0 0 0 4
        """
    val gridStr1 = """
        8 5 0 0 0 2 4 0 0 
        7 2 0 0 0 0 0 0 9 
        0 0 4 0 0 0 0 0 0 
        0 0 0 1 0 7 0 0 2 
        3 0 5 0 0 0 9 0 0 
        0 4 0 0 0 0 0 0 0 
        0 0 0 0 8 0 0 7 0 
        0 1 7 0 0 0 0 0 0 
        0 0 0 0 3 6 0 4 0 
        """
    val gridStr2 = """
        1 3 0 C 0 0 A 0 0 D F B 7 0 0 0
        0 0 F D G 0 0 4 0 A E 7 2 1 0 8
        G 0 0 0 9 0 5 D 3 0 0 0 A F B E
        0 E 4 A B 0 0 0 5 0 1 G 0 3 0 D
        0 F 1 0 3 0 9 0 D 8 5 0 E 0 G 7
        0 8 0 0 E 5 1 0 0 C B 0 0 0 3 F
        E A G 0 0 D 6 0 0 1 0 F 0 0 C 0
        7 0 3 9 F 0 0 0 0 0 G 0 0 B D 0
        0 D 0 1 0 7 B 0 0 G 0 0 3 0 F 4
        0 7 0 0 0 0 0 5 C 0 0 0 D 0 E 0
        0 0 0 6 1 0 2 E F 3 D 0 0 7 0 0
        F 0 0 0 4 8 D 3 0 0 0 A 9 0 0 0
        0 1 2 3 8 A 0 0 6 F 0 0 0 C 0 0
        0 9 0 7 2 6 3 1 0 0 0 0 F E 0 A
        6 5 8 0 D C 0 0 E 7 0 0 0 0 0 0
        A C E 0 0 B G 0 0 4 2 0 0 D 7 3
        """

    val sudokuGrid = SudokuGrid(gridStr0)

    println(sudokuGrid)
}