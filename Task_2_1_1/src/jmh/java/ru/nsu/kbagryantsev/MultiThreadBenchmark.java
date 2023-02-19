package ru.nsu.kbagryantsev;

import java.io.FileNotFoundException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * See {@link ConcurrencyBenchmark}.
 */
@State(Scope.Benchmark)
public class MultiThreadBenchmark extends ConcurrencyBenchmark {
    /**
     * Amount of threads used in the benchmark.
     */
    @Param({"2", "4", "8", "16"})
    private int threadCount;

    /**
     * Executes a benchmark using {@link MultiThreadedTask}.
     *
     * @param blackhole Blackhole instance
     */
    @Benchmark
    public void benchmark(final Blackhole blackhole) {
        try {
            this.readData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        blackhole.consume(MultiThreadedTask.call(dataset, threadCount));
    }
}
