package ru.nsu.kbagryantsev;

import java.io.FileNotFoundException;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * See {@link ConcurrencyBenchmark}.
 */
@State(Scope.Benchmark)
public final class MultiThreadBenchmark extends ConcurrencyBenchmark {
    /**
     * Amount of threads used in the benchmark.
     */
    @Param({"2", "4", "8", "16"})
    private int threadCount;

    /**
     * See {@link ConcurrencyBenchmark#ConcurrencyBenchmark()}.
     *
     * @throws FileNotFoundException JSON file may be missing
     */
    public MultiThreadBenchmark() throws FileNotFoundException { }

    @Override
    public void benchmark(final Blackhole blackhole) {
        blackhole.consume(MultiThreadedTask.call(dataset, threadCount));
    }
}
