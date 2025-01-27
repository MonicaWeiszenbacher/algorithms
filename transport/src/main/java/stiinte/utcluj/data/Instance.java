package stiinte.utcluj.data;

public class Instance {
    
    private String name;
    private int[] supply;
    private int[] demand;
    private int[][] costs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getSupply() {
        return supply;
    }

    public void setSupply(int[] supply) {
        this.supply = supply;
    }

    public int[] getDemand() {
        return demand;
    }

    public void setDemand(int[] demand) {
        this.demand = demand;
    }

    public int[][] getCosts() {
        return costs;
    }

    public void setCosts(int[][] costs) {
        this.costs = costs;
    }
}