package com.github.jinzhaosn.stream;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.stream.Stream;

/**
 * Flow类测试
 *
 * @auther 961374431@qq.com
 * @date 2021年12月12日
 */
public class FlowTest {

    /**
     * 测试使用IF/ELSE时、使用Flow条件流的性能
     */
    @Test
    public void flowTest() {
        Flow.choose().when(true).then(() -> System.out.println("hello world"));

        SecureRandom random = new SecureRandom();

        long time3 = System.currentTimeMillis();
        Stream.generate(() -> random.nextInt(21)).limit(1000000).forEach(FlowTest::flowChoose);
        long time4 = System.currentTimeMillis();
        System.out.println("flow cost " + (time4 - time3));

        long time1 = System.currentTimeMillis();
        Stream.generate(() -> random.nextInt(21)).limit(1000000).forEach(FlowTest::normalChoose);
        long time2 = System.currentTimeMillis();
        System.out.println("normal cost " + (time2 - time1));
    }

    static long normalSum = 0;
    static long flowSum = 0;

    private static void normalChoose(Integer ch) {
        if (ch > 15) {
            normalSum += ch + 1;
        } else if (ch > 10) {
            normalSum += ch + 2;
        } else if (ch > 5) {
            normalSum += ch + 3;
        } else {
            normalSum += ch + 4;
        }
    }

    private static void flowChoose(Integer ch) {
        Flow.choose().when(ch > 15).then(() -> flowSum += ch + 1)
                .elseWhen(ch > 10).then(() -> flowSum += ch + 2)
                .elseWhen(ch > 5).then(() -> flowSum += ch + 3)
                .otherwise(() -> flowSum += ch + 4);
    }
}
