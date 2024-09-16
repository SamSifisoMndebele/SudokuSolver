fun main() {

    val easiest = """
        1 0 0 0
        0 0 2 0
        0 3 0 0
        0 0 0 4
        """
    /*Solution:
    1	2	4	3
    3	4	2	1
    4	3	1	2
    2	1	3	4*/


    val easy1 = """
        0 0 5 3 0 0 0 0 0
        8 0 0 0 0 0 0 2 0
        0 7 0 0 1 0 5 0 0 
        4 0 0 0 0 5 3 0 0
        0 1 0 0 7 0 0 0 6
        0 0 3 2 0 0 0 8 0
        0 6 0 5 0 0 0 0 9
        0 0 4 0 0 0 0 3 0
        0 0 0 0 0 9 7 0 0
        """
    /*Solution:
    1	4	5	3	2	7	6	9	8
    8	3	9	6	5	4	1	2	7
    6	7	2	9	1	8	5	4	3
    4	9	6	1	8	5	3	7	2
    2	1	8	4	7	3	9	5	6
    7	5	3	2	9	6	4	8	1
    3	6	7	5	4	2	8	1	9
    9	8	4	7	6	1	2	3	5
    5	2	1	8	3	9	7	6	4*/

    val easy2 = """
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
    /*Solution:
    1	3	9	C	6	E	A	2	8	D	F	B	7	4	5	G
    5	B	F	D	G	3	C	4	9	A	E	7	2	1	6	8
    G	6	7	8	9	1	5	D	3	2	4	C	A	F	B	E
    2	E	4	A	B	F	7	8	5	6	1	G	C	3	9	D
    C	F	1	B	3	4	9	A	D	8	5	2	E	6	G	7
    D	8	6	2	E	5	1	G	7	C	B	9	4	A	3	F
    E	A	G	5	7	D	6	B	4	1	3	F	8	2	C	9
    7	4	3	9	F	2	8	C	A	E	G	6	5	B	D	1
    9	D	A	1	C	7	B	6	2	G	8	E	3	5	F	4
    3	7	B	4	A	G	F	5	C	9	6	1	D	8	E	2
    8	G	5	6	1	9	2	E	F	3	D	4	B	7	A	C
    F	2	C	E	4	8	D	3	B	5	7	A	9	G	1	6
    B	1	2	3	8	A	E	7	6	F	9	D	G	C	4	5
    4	9	D	7	2	6	3	1	G	B	C	5	F	E	8	A
    6	5	8	G	D	C	4	F	E	7	A	3	1	9	2	B
    A	C	E	F	5	B	G	9	1	4	2	8	6	D	7	3*/


    val medium1 = """
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
    /*Solution:
    8	5	9	6	1	2	4	3	7
    7	2	3	8	5	4	1	6	9
    1	6	4	3	7	9	5	2	8
    9	8	6	1	4	7	3	5	2
    3	7	5	2	6	8	9	1	4
    2	4	1	5	9	3	7	8	6
    4	3	2	9	8	1	6	7	5
    6	1	7	4	2	5	8	9	3
    5	9	8	7	3	6	2	4	1*/

    val medium2 = """
        8 0 0 0 0 0 0 0 0 
        0 0 3 6 0 0 0 0 0 
        0 7 0 0 9 0 2 0 0
        0 5 0 0 0 7 0 0 0 
        0 0 0 0 4 5 7 0 0 
        0 0 0 1 0 0 0 3 0 
        0 0 1 0 0 0 0 6 8
        0 0 8 5 0 0 0 1 0 
        0 9 0 0 0 0 4 0 0 
        """
    /*Solution:
    8	1	2	7	5	3	6	4	9
    9	4	3	6	8	2	1	7	5
    6	7	5	4	9	1	2	8	3
    1	5	4	2	3	7	8	9	6
    3	6	9	8	4	5	7	2	1
    2	8	7	1	6	9	5	3	4
    5	2	1	9	7	4	3	6	8
    4	3	8	5	2	6	9	1	7
    7	9	6	3	1	8	4	5	2*/

    val medium3 = """
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
    /*Solution:
    8	5	9	6	1	2	4	3	7
    7	2	3	8	5	4	1	6	9
    1	6	4	3	7	9	5	2	8
    9	8	6	1	4	7	3	5	2
    3	7	5	2	6	8	9	1	4
    2	4	1	5	9	3	7	8	6
    4	3	2	9	8	1	6	7	5
    6	1	7	4	2	5	8	9	3
    5	9	8	7	3	6	2	4	1*/


    val hardest = """
        0 0 0 0 0 6 0 0 0 
        0 5 9 0 0 0 0 0 8 
        2 0 0 0 0 8 0 0 0 
        0 4 5 0 0 0 0 0 0 
        0 0 3 0 0 0 0 0 0 
        0 0 6 0 0 3 0 5 4 
        0 0 0 3 2 5 0 0 6 
        0 0 0 0 0 0 0 0 0 
        0 0 0 0 0 0 0 0 0 
        """
    /*Solution:
    4	3	8	7	9	6	2	1	5
    6	5	9	1	3	2	4	7	8
    2	7	1	4	5	8	6	9	3
    8	4	5	2	1	9	3	6	7
    7	1	3	5	6	4	8	2	9
    9	2	6	8	7	3	1	5	4
    1	9	4	3	2	5	7	8	6
    3	6	2	9	8	7	5	4	1
    5	8	7	6	4	1	9	3	2*/

    executionTime {
        solveSudoku(easiest)
    }
}