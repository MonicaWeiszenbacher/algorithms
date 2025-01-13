package stiinte.utcluj.algorithms.data;

import java.math.BigInteger;
import java.time.LocalDate;

public class AgeGroup {

    private LocalDate birthStartYear;
    private LocalDate birthEndYear;
    private BigInteger count;

    public AgeGroup(LocalDate birthStartYear, LocalDate birthEndYear, String count) {
        this.birthStartYear = birthStartYear;
        this.birthEndYear = birthEndYear;
        this.count = new BigInteger(count);
    }

    public LocalDate getBirthStartYear() {
        return birthStartYear;
    }

    public LocalDate getBirthEndYear() {
        return birthEndYear;
    }

    public BigInteger getCount() {
        return count;
    }
}
