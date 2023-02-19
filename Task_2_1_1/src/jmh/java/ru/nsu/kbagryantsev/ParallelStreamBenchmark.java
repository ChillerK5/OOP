package ru.nsu.kbagryantsev;

import java.io.FileNotFoundException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * See {@link ConcurrencyBenchmark}.
 */
@State(Scope.Benchmark)
public class ParallelStreamBenchmark extends ConcurrencyBenchmark {
    /**
     * Executes a benchmark using {@link ParallelStreamTask}.
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

        blackhole.consume(ParallelStreamTask.call(dataset));
    }
}
