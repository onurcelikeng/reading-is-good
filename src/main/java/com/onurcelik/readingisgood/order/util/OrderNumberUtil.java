package com.onurcelik.readingisgood.order.util;

import java.util.concurrent.ThreadLocalRandom;

public class OrderNumberUtil {

    private OrderNumberUtil() {

    }

    public static String generate() {
        return String.valueOf(ThreadLocalRandom.current().nextInt());
    }
}
