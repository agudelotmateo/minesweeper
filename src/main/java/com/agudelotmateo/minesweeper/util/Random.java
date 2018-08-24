package com.agudelotmateo.minesweeper.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * pending!
 */
public final class Random {

    /**
     * pending!
     */
    private Random() {
    }

    /**
     * Generates an array of n pseudorandom {@code int} values between the specified
     * origin (inclusive) and the specified bound (exclusive).
     * 
     * pending!
     */
    public static int[] nUniqueIntsInRange(int n, int origin, int bound) {
        // check for input validity
        if (origin >= bound)
            throw new IllegalArgumentException("bound must be greater than origin");

        // create a list of the whole range
        int[] range = new int[bound - origin];
        for (int i = origin; i < bound; ++i)
            range[i - origin] = i;

        // shuffle them randomly
        shuffle(range);

        // take the first n of them only
        int[] unique = new int[n];
        for (int i = 0; i < n; ++i)
            unique[i] = range[i];
        return unique;
    }

    /**
     * Fisher-Yates shuffle algorithm to shuffle a list of integers. Taken from
     * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
     * 
     * pending!
     */
    public static void shuffle(int[] arr) {
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException("array must contain at least one element");
        int j, tmp;
        for (int i = arr.length - 1; i > 0; --i) {
            j = ThreadLocalRandom.current().nextInt(i + 1);
            tmp = arr[j];
            arr[j] = arr[i];
            arr[i] = tmp;
        }
    }
}
