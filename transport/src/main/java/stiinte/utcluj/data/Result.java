package stiinte.utcluj.data;

public class Result {
    
    private String instanceName;
    private int cost;
    private int numberOfIterations;
    private long executionTimeInSeconds;
    private ResultStatus status;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public long getExecutionTimeInSeconds() {
        return executionTimeInSeconds;
    }

    public void setExecutionTimeInSeconds(long executionTimeInSeconds) {
        this.executionTimeInSeconds = executionTimeInSeconds;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.join(",", instanceName, String.valueOf(cost), String.valueOf(numberOfIterations),
                String.valueOf(executionTimeInSeconds), status.name()) + System.lineSeparator();
    }
}
