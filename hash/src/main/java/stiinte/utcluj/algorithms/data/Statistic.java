package stiinte.utcluj.algorithms.data;

import java.util.List;

public class Statistic {

    private String county;
    private List<AgeGroup> ageGroups;
    private Gender gender;

    public Statistic(String county, List<AgeGroup> ageGroups, Gender gender) {
        this.county = county;
        this.ageGroups = ageGroups;
        this.gender = gender;
    }

    public String getCounty() {
        return county;
    }

    public List<AgeGroup> getAgeGroups() {
        return ageGroups;
    }

    public Gender getGender() {
        return gender;
    }
}
