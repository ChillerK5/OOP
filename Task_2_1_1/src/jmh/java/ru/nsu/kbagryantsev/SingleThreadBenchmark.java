package ru.nsu.kbagryantsev;

import java.io.FileNotFoundException;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * See {@link ConcurrencyBenchmark}.
 */
@State(Scope.Benchmark)
public final class SingleThreadBenchmark extends ConcurrencyBenchmark {
    /**
     * See {@link ConcurrencyBenchmark#ConcurrencyBenchmark()}.
     *
     * @throws FileNotFoundException JSON file may be missing
     */
    public SingleThreadBenchmark() throws FileNotFoundException { }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Override
    public void benchmark(final Blackhole blackhole) {
        blackhole.consume(SingleThreadedTask.call(dataset));
    }
}
