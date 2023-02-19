package ru.nsu.kbagryantsev;

import java.io.FileNotFoundException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Single thread benchmark.
 */
@State(Scope.Benchmark)
public class SingleThreadBenchmark extends ConcurrencyBenchmark {
    /**
     * Executes a benchmark using {@link SingleThreadedTask}.
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

        blackhole.consume(SingleThreadedTask.call(dataset));
    }
}
