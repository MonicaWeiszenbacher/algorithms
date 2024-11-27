package stiinte.utcluj.algorithms;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * Sorts arrays of random numbers using an algorithm of choice among the {@link SortingAlgorithmType} values.
 */
public class SortingAlgorithmTester {
    
    private static final SortingAlgorithmType ALGORITHM_TYPE = SortingAlgorithmType.BUBBLE;
    private static final boolean DISPLAY_UNSORTED_LIST = false;
    private static final boolean DISPLAY_SORTED_LIST = false;
    private static final int NUMBER_OF_ARRAYS = 1000;
    private static final int ARRAY_MIN_ELEMENT = 10000;
    private static final int ARRAY_MAX_ELEMENT = 100000;

    public static void main(String... args) {
        AtomicLong algorithmTotalDuration = new AtomicLong(0);

        IntStream.rangeClosed(1, NUMBER_OF_ARRAYS).forEach(listNumber -> {
            System.out.println("Generating list nr." + listNumber);
            int limit = new Random().nextInt(ARRAY_MIN_ELEMENT, ARRAY_MAX_ELEMENT);

            int[] randomInts = new Random().ints(ARRAY_MIN_ELEMENT, ARRAY_MAX_ELEMENT)
                    .limit(limit)
                    .peek(i -> {
                        if (DISPLAY_UNSORTED_LIST) {
                            System.out.println(i);
                        }
                    })
                    .toArray();

            System.out.println("Generated " + randomInts.length + " random ints");
            Instant iterationStart = Instant.now();

            switch (ALGORITHM_TYPE) {
                case BUBBLE -> bubbleSort(randomInts);
                case INSERTION -> insertionSort(randomInts);
                case MERGE -> mergeSort(randomInts, new int[randomInts.length], 0, randomInts.length - 1);
                case QUICK -> quickSort(randomInts, 0, randomInts.length - 1);
                case SELECTION -> selectionSort(randomInts);
            }

            Instant iterationEnd = Instant.now();
            long iterationDuration = Duration.between(iterationStart, iterationEnd).toMillis();
            System.out.println("Iteration took: " + iterationDuration + " milliseconds");
            algorithmTotalDuration.addAndGet(iterationDuration);

            if (DISPLAY_SORTED_LIST) {
                Arrays.stream(randomInts).forEach(System.out::println);
            }
        });
        
        System.out.println("Algorithm took: " + algorithmTotalDuration + " milliseconds");
    }

    private static void bubbleSort(int[] numbers) {
        int length = numbers.length;

        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {

                if (numbers[j] > numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
    }

    private static void insertionSort(int[] numbers) {
        int length = numbers.length;

        for (int i = 0; i < length; i++) {
            int key = numbers[i];
            int j = i - 1;

            while (j >= 0 && numbers[j] > key) {
                numbers[j + 1] = numbers[j];
                j = j - 1;
            }
            numbers[j + 1] = key;
        }
    }

    private static void mergeSort(int[] numbers, int[] workArray, int begin, int end) {
        if (begin < end) {
            int middle = begin + (end - begin) / 2;
            mergeSort(numbers, workArray, begin, middle);
            mergeSort(numbers, workArray, middle + 1, end);
            merge(numbers, workArray, begin, middle, end);
        }
    }

    private static void merge(int[] numbers, int[] workArray, int begin, int middle, int end) {
        for (int i = begin; i <= end; i++) {
            workArray[i] = numbers[i];
        }

        int i = begin;
        int j = middle + 1;
        int k = begin;

        while (i <= middle && j <= end) {
            if (workArray[i] <= workArray[j]) {
                numbers[k] = workArray[i];
                i++;
            } else {
                numbers[k] = workArray[j];
                j++;
            }
            k++;
        }

        while (i <= middle) {
            numbers[k] = workArray[i];
            k++;
            i++;
        }
    }

    private static void quickSort(int[] numbers, int begin, int end) {
        int i = begin;
        int j = end;
        int pivot = numbers[begin + (end - begin) / 2];

        while (i <= j) {
            while (numbers[i] < pivot) {
                i++;
            }

            while (numbers[j] > pivot) {
                j--;
            }

            if (i <= j) {
                int temp = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = temp;
                i++;
                j--;
            }
        }

        if (begin < j)
            quickSort(numbers, begin, j);
        if (i < end)
            quickSort(numbers, i, end);
    }

    private static void selectionSort(int[] numbers) {
        int length = numbers.length;
        
        for (int i = 0; i < length - 1; i++) {
            int iMin = i;
            for (int j = i + 1; j < length; j++)
                if (numbers[j] < numbers[iMin])
                    iMin = j;
            if (i != iMin) {
                int temp = numbers[i];
                numbers[i] = numbers[iMin];
                numbers[iMin] = temp;
            }
        }
    }

    private enum SortingAlgorithmType {
        BUBBLE, INSERTION, MERGE, QUICK, SELECTION
    }
}