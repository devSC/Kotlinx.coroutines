import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

fun main(args: Array<String>) {
    launch {
        delay(1)
        println("hello coruntines")
    }
    println("hello coruntines2")
}