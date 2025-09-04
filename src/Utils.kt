import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


@OptIn(ExperimentalContracts::class)
inline infix fun <T,R> T.withTimeMillis(block: T.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val start = System.currentTimeMillis()
    val result = block()
    val time = System.currentTimeMillis() - start
    println("Execution time: ${time}ms")
    println("--------------------------------------")
    return result
}

inline fun <R> withTimeMillis(block: () -> R): R = Unit.withTimeMillis {
    block()
}