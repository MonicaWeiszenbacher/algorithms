package stiinte.utcluj.resolver;

import stiinte.utcluj.data.Instance;
import stiinte.utcluj.data.Result;

public class NorthWest implements ProblemResolver {

    @Override
    public void calculate(Instance instance, Result result) {
        int[] supply = instance.getSupply();
        int[] demand = instance.getDemand();
        int[][] costs = instance.getCosts();
        int row = 0;
        int column = 0;
        int cost = 0;
        int numberOfIterations = 0;

        while (row != costs.length && column != costs[0].length) {
            if (supply[row] <= demand[column]) {
                cost += supply[row] * costs[row][column];
                demand[column] -= supply[row];
                row++;
            } else {
                cost += demand[column] * costs[row][column];
                supply[row] -= demand[column];
                column++;
            }
            numberOfIterations++;
        }
        
        result.setCost(cost);
        result.setNumberOfIterations(numberOfIterations);
    }
}
