package stiinte.utcluj;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class GeneticAlgorithm {

    private static final List<Item> ITEMS = List.of(
            new Item(5, 12),
            new Item(3, 5),
            new Item(7, 10),
            new Item(2, 7));

    private static final int KNAPSACK_SIZE = 12;
    private static final int POPULATION_SIZE = 50;
    private static final int MAX_GENERATIONS = 50;
    private static final double CROSSOVER_PROBABILITY = 0.6;
    private static final double MUTATION_PROBABILITY = 0.02;

    private static List<String> candidates = new ArrayList<>();
    private static double bestFitness;

    /**
     * Runs the genetic algorithm until no more offspring is generated.
     */
    public static void main(String[] args) {
        initialisePopulation();

        for (int i = 1; i <= MAX_GENERATIONS && bestFitness <= KNAPSACK_SIZE; i++) {
            String candidate1 = selectBestCandidate();
            candidates.remove(candidate1);
            String candidate2 = selectBestCandidate();

            candidates = new ArrayList<>();
            candidates.add(candidate1);

            IntStream.range(1, POPULATION_SIZE).forEach(_ -> candidates.add(mutate(crossover(candidate1, candidate2))));

            System.out.println("Generation " + i + " has candidate " + candidate1 + " and value " + bestFitness);
        }

    }

    /**
     * Step 1:
     * Generates randomly an initial population of size {@value POPULATION_SIZE}.
     * Values 0 and 1 are set randomly to represent the absence, respectively the presence of an item in the knapsack.
     * The set of all chromosomes represents the population of the current generation.
     */
    private static void initialisePopulation() {
        Random random = new Random();
        
        IntStream.range(0, POPULATION_SIZE).forEach(_ -> {
            StringBuilder chromosome = new StringBuilder();
            IntStream.range(0, ITEMS.size()).forEach(_ -> chromosome.append(random.nextInt(2)));
            candidates.add(chromosome.toString());
        });
    }

    /**
     * Step 2:
     * Determine the ability of each chromosome to compete with others, keeping
     * only the one whose fitness score is better and weight less than the knapsack's.
     *
     * @return the fitness value if the candidate's weight does not exceed the knapsack's weight, and -1 otherwise
     */
    private static double calculateFitness(String chromosome) {
        double fitness = 0;
        double weight = 0;

        for (int i = 0; i < chromosome.length(); i++) {
            if (chromosome.charAt(i) =='1') {
                Item item = ITEMS.get(i);
                weight += item.getWeight();
                fitness += item.getValue();
            }
        }

        return weight <= KNAPSACK_SIZE ? fitness : -1;
    }

    /**
     * Step 3:
     * Choose the fittest for the reproduction of the next generation.
     */
    private static String selectBestCandidate() {
        double candidateBestFitness = -1;
        String bestCandidate = "";

        for (String candidate : candidates) {
            double fitness = calculateFitness(candidate);

            if (fitness != -1 && fitness >= candidateBestFitness) {
                candidateBestFitness = fitness;
                bestCandidate = candidate;
            }
        }

        bestFitness = candidateBestFitness;

        return bestCandidate;
    }

    /**
     * Step 4:
     * Uses the selected parents' chromosomes for mixing the genetic material for the next offspring
     */
    private static String crossover(String candidate1, String candidate2) {
        StringBuilder crossover = new StringBuilder();
        IntStream.range(0, candidate1.length()).forEach(i ->
                crossover.append(Math.random() >= CROSSOVER_PROBABILITY ? candidate1.charAt(i) : candidate2.charAt(i)));

        return crossover.toString();
    }

    /**
     * Step 5: Mutate
     * Introduces diversity in the population to avoid getting stuck at the local maxima
     */
    private static String mutate(String candidate) {
        for (int i = 0; i < candidate.length(); i++) {
            if (Math.random() <= MUTATION_PROBABILITY) {
                StringBuilder mutated = new StringBuilder(candidate);
                mutated.setCharAt(i, switch (candidate.charAt(i)) {
                    case '0' -> '1';
                    case '1' -> '0';
                    default -> candidate.charAt(i);
                });
                candidate = mutated.toString();
            }
        }

        return candidate;
    }

    private static class Item {

        private final double weight;
        private final double value;

        public Item(double weight, double value) {
            this.weight = weight;
            this.value = value;
        }

        public double getWeight() {
            return weight;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Item item)) return false;
            return Double.compare(weight, item.weight) == 0 && Double.compare(value, item.value) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(weight, value);
        }
    }
}