import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.channels.produce
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun waitingForJob() = runBlocking<Unit> {
    //launch new coroutine and keep a reference to its job
    val job = launch {
        delay(1000L)
        println("world")
    }
    println("hello")
    job.join() //wait until child coroutine completes
}


//
suspend fun doWorld() {
    delay(1000L)
    println("world")
}


suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

fun doSomethingUsefulOneAsync() = async {
    doSomethingUsefulOne()
}

fun doSomethingUsefulTwoAsync() = async {
    doSomethingUsefulTwo()
}

fun producNumbers() = produce<Int> {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}

//And another coroutine or coroutines are consuming that stream, doing some processing, and producing some other results. In the below example the numbers are just squared:
fun square(numbers: ReceiveChannel<Int>) = produce<Int> {
    for (x in numbers) send(x * x)
}


fun produceNumbers() = produce<Int> {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

fun launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    channel.consumeEach {
        println("Processor #$id received $it")
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
//    launch {
//        delay(1000L) //sleep 1 second
//        println("launch-hello coruntines")
//    }
//
//    thread {
////        delay(1001L) //error
//        println("thread ")
//    }
//
//
//    println("waiting...")
//    Thread.sleep(2000L) //sleep 2 second to keep JVM live
//    println("YES...")
//
//    println("runBlocking...")
//
//    //// but this expression blocks the main thread
//    runBlocking {
//        println("blocking") //
//        delay(2000L)
//    }
//    print("run blocking end")
//
//    //Waiting for a job
//    println("waiting for job")
//    waitingForJob()
//    runBlocking {
//        delay(1500L)
//    }
//    println("end for job")

    ////Extract function refactoring
    ////Let's extract the block of code inside launch { ... } into a separate function. When you perform "Extract function" refactoring on this code you get a new function with suspend modifier. That is your first suspending function. Suspending functions can be used inside coroutines just like regular functions, but their additional feature is that they can, in turn, use other suspending functions, like delay in this example, to suspend execution of a coroutine.
    //Waiting for a job
//    println("waiting for job2")
//    val job = launch {
//        doWorld()
//    }
//    print("hello, ")
//    job.join()
//    println("end for job2")


    //Coroutines ARE light-weight
//    val jobs = List(100_000) { // launch a lot of coroutines and list their jobs
//        launch {
//            delay(1000L)
//            print(".")
//        }
//    }
//    jobs.forEach { it.join() } // wait for all jobs to complete


//    launch {
//        repeat(1000) { i ->
//            println("I'm sleeping $i ...")
//            delay(500L)
//        }
//    }
//    delay(1300L) // just quit after delay


    //Cancelling coroutine execution
//    val job = launch {
//        repeat(1000) { i ->
//            println("I'm sleeping $i ...")
//            delay(500L)
//        }
//    }
//    delay(1300L) // delay a bit
//    println("main: I'm tired of waiting!")
////    job.cancel() // cancels the job
//    job.join() // waits for job's completion
//    println("main: Now I can quit.")


    //Cancellation is cooperative
//    val startTime = System.currentTimeMillis()
//    val job = launch {
//        var nextPrintTime = startTime
//        var i = 0
//        while (i < 5) { // computation loop, just wastes CPU
//            // print a message twice a second
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("I'm sleeping ${i++} ...")
//                nextPrintTime += 500L
//            }
//        }
//    }
//    delay(1300L) // delay a bit
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // cancels the job and waits for its completion
//    println("main: Now I can quit.")

    /*
    I'm sleeping 0 ...
    I'm sleeping 1 ...
    I'm sleeping 2 ...
    main: I'm tired of waiting!
    I'm sleeping 3 ...
    I'm sleeping 4 ...
    main: Now I can quit.
    */

    //Making computation code cancellable
//    val startTime = System.currentTimeMillis()
//    val job = launch {
//        var nextPrintTime = startTime
//        var i = 0
//        while (isActive) { // cancellable computation loop
//            // print a message twice a second
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("I'm sleeping ${i++} ...")
//                nextPrintTime += 500L
//            }
//        }
//    }
//    delay(1300L) // delay a bit
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // cancels the job and waits for its completion
//    println("main: Now I can quit.")

    /*
    I'm sleeping 0 ...
    I'm sleeping 1 ...
    I'm sleeping 2 ...
    main: I'm tired of waiting!
    main: Now I can quit.
    * */


    //Closing resources with finally
//    val job = launch {
//        try {
//            repeat(1000) { i ->
//                println("I'm sleeping $i ...")
//                delay(500L)
//            }
//        } finally {
//            println("I'm running finally")
//        }
//    }
//    delay(1300L) // delay a bit
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // cancels the job and waits for its completion
//    println("main: Now I can quit.")


    //There is a laziness option to async using an optional start parameter with a value of CoroutineStart.LAZY. It starts coroutine only when its result is needed by some await or if a start function is invoked. Run the following example that differs from the previous one only by this option:
//    val times = measureTimeMillis {
//        val value = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
//        val value2 = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
//        println("value is: ${value.await() + value2.await()}")
//    }
//    println("times: $times")

    //Async-style functions
    //However, their use always implies asynchronous (here meaning concurrent) execution of their action with the invoking code.

    // we can initiate async actions outside of a coroutine
//    val times = measureTimeMillis {
//        val one = doSomethingUsefulOneAsync()
//        val two = doSomethingUsefulTwoAsync()
//        // but waiting for a result must involve either suspending or blocking.
//        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
//        runBlocking {
//            println("The answer is ${one.await() + two.await()}")
//        }
//    }
//    println("times: $times")


    //Coroutine context and dispatchers

//    val jobs = arrayListOf<Job>()
//    jobs += launch(Unconfined) { // not confined -- will work with main thread
//        println("      'Unconfined': I'm working in thread ${Thread.currentThread().name}")
//    }
//    jobs += launch(coroutineContext) { // context of the parent, runBlocking coroutine
//        println("'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
//    }
//    jobs += launch(CommonPool) { // will get dispatched to ForkJoinPool.commonPool (or equivalent)
//        println("      'CommonPool': I'm working in thread ${Thread.currentThread().name}")
//    }
//    jobs += launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
//        println("          'newSTC': I'm working in thread ${Thread.currentThread().name}")
//    }
//    jobs.forEach { it.join() }


    //Channels
    //A Channel is conceptually very similar to BlockingQueue. One key difference is that instead of a blocking put operation it has a suspending send, and instead of a blocking take operation it has a suspending receive.
//    val channel = Channel<Int>()
//    launch {
//        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
//        for (x in 1..5) channel.send(x * x)
//    }
//    // here we print five received integers:
//    repeat(5) { println(channel.receive()) }
//    println("Done!")


    //Pipelines
//    val numbers = producNumbers()
//    val squares = square(numbers)
//    for (i in 1..5) println(squares.receive()) //print first five
//    println("done")
//    squares.cancel()
//    numbers.cancel()

    val producer = produceNumbers()
    repeat(5) { launchProcessor(it, producer) }
    delay(1950)
    producer.cancel() // cancel producer coroutine and thus kill them all
}