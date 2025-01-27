package stiinte.utcluj.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineParser {

    public static int[] parseArray(String array) {
        return Arrays.stream(array.substring(1, array.length() - 2).split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static void fillCosts(String line, List<Integer> currentRow, List<List<Integer>> costs) {
        String[] splitLine = line.split(" ");

        Arrays.stream(splitLine).filter(s -> !s.isBlank()).forEach(element -> {
            if (element.startsWith("[") || element.startsWith("  ")) {
                element = element.replaceAll("\\[", "");
                currentRow.clear();
            }

            if (element.endsWith("]") || element.endsWith(";")) {
                element = element.replaceAll("]", "").replaceAll(";", "");
                currentRow.add(Integer.parseInt(element));
                costs.add(new ArrayList<>(currentRow));
                currentRow.clear();
            }

            currentRow.add(Integer.parseInt(element));
        });
    }

    public static int[][] getCostsArray(List<List<Integer>> costs) {
        int[][] array = new int[costs.size()][];
        for (int i = 0; i < costs.size(); i++) {
            array[i] = costs.get(i).stream().mapToInt(j -> j).toArray();
        }

        return array;
    }
}