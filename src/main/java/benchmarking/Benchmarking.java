package benchmarking;

import benchmarking.models.Father;
import benchmarking.models.Son;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class Benchmarking {
    Father father;

    @Setup
    public void setup() {
        int few = 10;

        List<Son> sons3 = new ArrayList<>();
        for (int x = 0; x < few; x++) {
            Son s = new Son();
            sons3.add(s);
        }
        father = new Father();
        father.setSons(sons3);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void simpleIf() throws InterruptedException {
        if (father.getSons() != null && !father.getSons().isEmpty()) {
            father.getSons().forEach(s -> s.setUuid(father.getUuid()));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void stream() throws InterruptedException {
        Stream.of(father).map(Father::getSons).flatMap(Collection::stream).forEach(s -> s.setUuid(father.getUuid()));
    }
}
