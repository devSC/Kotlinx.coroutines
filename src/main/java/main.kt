import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

fun main(args: Array<String>) {
    launch {
        delay(1000L) //sleep 1 second
        println("hello coruntines")
    }
    println("waiting...")
    Thread.sleep(2000L) //sleep 2 second to keep JVM live
    println("YES...")
}