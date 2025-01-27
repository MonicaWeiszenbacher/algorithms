package stiinte.utcluj;

import stiinte.utcluj.data.Instance;
import stiinte.utcluj.data.MethodType;
import stiinte.utcluj.data.Result;
import stiinte.utcluj.resolver.NorthWest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static stiinte.utcluj.parser.LineParser.*;

public class TransportProblem {
    
    private static final MethodType METHOD_TYPE = MethodType.NORTH_WEST;
    private static final String INPUT_PATH = "/Users/monica.weiszenbacher/Desktop/Master/Algorithms/TransportProblem/NV/Lab_simple_instances";
    private static final String OUTPUT_PATH = "/Users/monica.weiszenbacher/Desktop/Master/Algorithms/TransportProblem/result.csv";
    
    private static NorthWest northWest = new NorthWest();

    public static void main(String[] args) throws IOException {
        List<Result> results = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(INPUT_PATH))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                Instance instance = new Instance();
                instance.setName(file.getFileName().toString());
                List<List<Integer>> costs = new ArrayList<>();
                List<Integer> currentRow = new ArrayList<>();
                AtomicBoolean foundCosts = new AtomicBoolean(false);

                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    reader.lines().forEach(line -> {
                        if (line.startsWith("SCj = ")) {
                            instance.setSupply(parseArray(line.substring(6)));
                        } else if (line.startsWith("Dk = ")) {
                            instance.setDemand(parseArray(line.substring(5)));
                        } else if (line.contains("Cjk = ")) {
                            foundCosts.set(true);
                            line = line.substring(7);
                        }

                        if (foundCosts.get()) {
                            fillCosts(line, currentRow, costs);
                        }
                    });
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
                
                instance.setCosts(getCostsArray(costs));
                
                switch (METHOD_TYPE) {
                    case NORTH_WEST -> results.add(northWest.getResult(instance));
                    default -> {}
                }
            });
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(OUTPUT_PATH))) {
            writer.write("");
            writer.flush();
            
            for (Result result : results) {
                writer.write(result.toString());
            }
        }
    }
}