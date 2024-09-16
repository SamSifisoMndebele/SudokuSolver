import java.time.Instant

fun executionTime(executable: () -> Unit) {
    val thread = Thread {
        val start = Instant.now()
        // call the method you want to measure the execution time of here
        executable()
        val end = Instant.now()

        val executionTime = end.toEpochMilli() - start.toEpochMilli()
        println("\nExecution time:\t\t$executionTime ms")
        println("--------------------------------------")
    }
    thread.start()
}