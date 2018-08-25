package com.agudelotmateo.minesweeper.util;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Contains methods for generating pseudorandom numbers.
 * 
 * @author Mateo Agudelo Toro
 */
public final class Random {
    // IllegalArgumentException messages
    private static final String BAD_ARRAY = "array must contain at least one element";
    private static final String BAD_RANGE = "bound must be greater than origin";

    // Does not make sense to instantiate this class
    private Random() { }

    /**
     * Shuffles an array of integers using Fisher-Yates's algorithm.
     * 
     * @param arr the array to be shuffled
     * @throws IllegalArgumentException if the array does not contain at least one
     *                                  element
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher–Yates
     *      shuffle - Wikipedia</a>
     */
    public static void shuffle(int[] arr) {
        // Check for input validity
        if (arr == null || arr.length <= 0)
            throw new IllegalArgumentException(BAD_ARRAY);

        // Shuffle the array
        for (int i = arr.length - 1; i > 0; --i) {
            int j = ThreadLocalRandom.current().nextInt(i + 1);
            int tmp = arr[j];
            arr[j] = arr[i];
            arr[i] = tmp;
        }
    }

    /**
     * Generates a set of n pseudorandom {@code int} values between the specified
     * origin (inclusive) and the specified bound (exclusive).
     * 
     * @param n      the amount of random unique {@code int} values to generate
     * @param origin the least value returned
     * @param bound  the upper bound (exclusive)
     * @return a set of n pseudorandom {@code int} values between the specified
     *         origin (inclusive) and the specified bound (exclusive)
     * @throws IllegalArgumentException if {@code origin} is greater than or equal
     *                                  to {@code bound}
     */
    public static HashSet<Integer> nUniqueIntsInRange(int n, int origin, int bound) {
        // Check for input validity
        if (origin >= bound)
            throw new IllegalArgumentException(BAD_RANGE);

        // Create a list of the whole range
        int[] range = new int[bound - origin];
        for (int i = origin; i < bound; ++i)
            range[i - origin] = i;

        // Shuffle them randomly
        shuffle(range);

        // Take the first n of them only
        HashSet<Integer> unique = new HashSet<Integer>(n);
        for (int i = 0; i < n; ++i)
            unique.add(range[i]);
        return unique;
    }
}
