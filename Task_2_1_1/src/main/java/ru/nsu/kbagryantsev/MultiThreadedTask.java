package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

/**
 * Executes benchmark task via creating several new threads.
 */
public final class MultiThreadedTask {
    private MultiThreadedTask() { }

    /**
     * Executes anyComposite via creating given amount of raw threads.
     *
     * @param threadsCount amount of threads to use
     * @param numbers      list of numbers to check
     * @return result of anyComposite
     */
    public static boolean call(@NotNull final List<Integer> numbers,
                               @Positive final int threadsCount) {
        AtomicBoolean result = new AtomicBoolean(false);

        // Numbers buffer size for a single thread
        int threadBuffer = numbers.size() / threadsCount;
        int bufferTrail = numbers.size() % threadsCount;

        // List of threads
        List<Thread> threads = new ArrayList<>();

        // Threads creation and initialization
        for (int i = 0; i < threadsCount; i++) {
            int from = i * threadBuffer;
            int to = from + threadBuffer;
            if (i == threadsCount - 1) {
                to += bufferTrail;
            }
            List<Integer> numbersSubset = numbers.subList(from, to);

            // Single thread task
            Runnable task;
            task = () -> {
                boolean threadResult;
                threadResult = PrimeNumbersUtils.anyComposite(numbersSubset);
                result.compareAndExchange(false, threadResult);
            };

            threads.add(new Thread(task));
        }

        // Starting threads execution
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return result.get();
    }
}
