package stiinte.utcluj.resolver;

import stiinte.utcluj.data.Instance;
import stiinte.utcluj.data.Result;
import stiinte.utcluj.data.ResultStatus;

import java.time.Duration;
import java.time.Instant;

public interface ProblemResolver {
    
    default Result getResult(Instance instance) {
        Result result = new Result();
        result.setInstanceName(instance.getName());
        Instant start = Instant.now();
        calculate(instance, result);
        Instant end = Instant.now();
        result.setExecutionTimeInSeconds(Duration.between(start, end).toSeconds());
        result.setStatus(result.getCost() > 0 ? ResultStatus.SOLVED : ResultStatus.UNSOLVED);
        return result;
    }
    
    void calculate(Instance instance, Result result);
}