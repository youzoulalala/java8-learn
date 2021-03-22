import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ParallelTest {
    //并行流，将一个大任务拆分（fork）为小任务，最后将小任务的结果进行汇总（join）
    //"工作窃取"模式，有效提高CPU的利用率，从而提升性能
    @Test
    public void test01(){//3991mm
        Instant start = Instant.now();
        final OptionalLong reduce = LongStream.rangeClosed(0, 10000000000L)
                .parallel()
                .reduce(Long::sum);
//        System.out.println(reduce.getAsLong());
        Instant end = Instant.now();
        final Duration between = Duration.between(start, end);
        System.out.println(between.toMillis());
    }

    @Test
    public void test02(){//26295mm
        Instant start = Instant.now();
        long res = 0;
        for (long i = 0; i <=100000000000L ; i++) {
            res += i;
        }
        Instant end = Instant.now();
        final Duration between = Duration.between(start, end);
        System.out.println(between.toMillis());
    }
}
