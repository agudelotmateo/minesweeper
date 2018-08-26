package com.agudelotmateo.minesweeper.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Random utility class.
 */
public class RandomTest {

    /**
     * The shuffle method must not alter any of them.
     */
    @RepeatedTest(10)
    public void shuffleMustNotChangeValues() {
        // Create a structure containing the numbers [0, 100)
        int[] originalNumbers = IntStream.range(0, 100).toArray();
        Set<Integer> originalSet = Arrays.stream(originalNumbers).boxed().collect(Collectors.toSet());

        // Shuffle the numbers and check if any of them were modified
        Random.shuffle(originalNumbers);
        assertEquals(originalSet, Arrays.stream(originalNumbers).boxed().collect(Collectors.toSet()));
    }

    /**
     * The shuffle method must throw an IllegalArgumentExpcetion for a null array.
     */
    @Test
    public void shuffleNull() {
        assertThrows(IllegalArgumentException.class, () -> Random.shuffle(null));
    }

    /**
     * The shuffle method must throw an IllegalArgumentExpcetion for an empty array.
     */
    @Test
    public void shuffleEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Random.shuffle(new int[] {}));
    }

    /**
     * The nUniqueIntsInRange method must return the whole range if the range's size
     * is n.
     */
    @RepeatedTest(10)
    public void nUniqueIntsInRangeWholeRange() {
        // Generate a random range specification
        int origin = ThreadLocalRandom.current().nextInt(-100, 100);
        int n = ThreadLocalRandom.current().nextInt(50, 100);
        int bound = origin + n;

        // Store the actual range in a set
        Set<Integer> rangeNumbers = IntStream.range(origin, bound).boxed().collect(Collectors.toSet());

        // Check if the method fails to reproduce the exact same numbers
        assertEquals(Random.nUniqueIntsInRange(n, origin, bound), rangeNumbers);
    }

    /**
     * The method must return exactly n items
     */
    @RepeatedTest(10)
    public void nUniqueIntsInRangeExactlyN() {
        // Generate a random range specification
        int origin = ThreadLocalRandom.current().nextInt(-100, 100);
        int n = ThreadLocalRandom.current().nextInt(50, 100);
        int bound = origin + n;

        // Check if the method fails to produce exactly n items
        assertEquals(n, Random.nUniqueIntsInRange(n, origin, bound).size());
    }

    /**
     * The produced numbers must be within the range [origin, bound)
     */
    @RepeatedTest(10)
    public void nUniqueIntsInRangeValidNumbers() {
        // Generate a random range specification
        int origin = ThreadLocalRandom.current().nextInt(-100, 100);
        int n = ThreadLocalRandom.current().nextInt(50, 100);
        int bound = origin + n;

        // Check if any produced value lies outside of the range
        assertEquals(false,
                Random.nUniqueIntsInRange(n, origin, bound).stream().anyMatch(x -> x < origin || x >= bound));
    }

    /**
     * The origin must be less than the bound.
     */
    @Test
    public void nUniqueIntsInRangeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Random.nUniqueIntsInRange(5, 10, -10));
    }
}
