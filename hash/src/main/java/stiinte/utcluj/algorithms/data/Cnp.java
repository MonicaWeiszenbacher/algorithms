package stiinte.utcluj.algorithms.data;

import java.util.Objects;

public class Cnp {
    
    private String gender;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private String countyCode;
    private String sequenceNumber;
    private String checksum;

    public Cnp(String gender, String birthYear, String birthMonth, String birthDay,
               String countyCode, String sequenceNumber, String checksum) {
        this.gender = gender;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.countyCode = countyCode;
        this.sequenceNumber = sequenceNumber;
        this.checksum = checksum;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public String getChecksum() {
        return checksum;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cnp cnp)) return false;
        return Objects.equals(gender, cnp.gender) && Objects.equals(birthYear, cnp.birthYear)
                && Objects.equals(birthMonth, cnp.birthMonth) && Objects.equals(birthDay, cnp.birthDay)
                && Objects.equals(countyCode, cnp.countyCode) && Objects.equals(sequenceNumber, cnp.sequenceNumber)
                && Objects.equals(checksum, cnp.checksum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, birthYear, birthMonth, birthDay, countyCode, sequenceNumber, checksum);
    }

    @Override
    public String toString() {
        return String.join("", gender, birthYear, birthMonth, birthDay, countyCode, sequenceNumber, checksum);
    }
}
