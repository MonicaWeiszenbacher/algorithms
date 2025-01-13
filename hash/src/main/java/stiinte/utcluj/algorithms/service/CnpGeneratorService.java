package stiinte.utcluj.algorithms.service;

import stiinte.utcluj.algorithms.data.AgeGroup;
import stiinte.utcluj.algorithms.data.Cnp;
import stiinte.utcluj.algorithms.data.Gender;
import stiinte.utcluj.algorithms.data.Statistic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import static java.util.Map.entry;

/**
 * The population statistics are loaded from the file in the first link below, converted to CSV, with the following manual changes:
 * - Headers and regional entries are removed
 * - Last column changed to gender (M / F), instead of the county name
 * <a href="https://www.recensamantromania.ro/wp-content/uploads/2023/05/P01_Populatia-rezidenta-pe-sexe-grupe-de-varsta-macroregiuni-regiuni-de-dezvoltare-si-judete.xls"></a>
 * <a href="https://ro.wikipedia.org/wiki/Cod_numeric_personal_(Rom%C3%A2nia)"></a>
 */
public class CnpGeneratorService {

    private static final BigInteger TOTAL_POPULATION = BigInteger.valueOf(19_053_815);
    private static final LocalDate YEAR_2000_START_DATE = LocalDate.of(2000, 1, 1);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final Map<String, String> COUNTY_CODES = Map.ofEntries(
            entry("Alba", "01"),
            entry("Arad", "02"),
            entry("Arges", "03"),
            entry("Bacau", "04"),
            entry("Bihor", "05"),
            entry("Bistrita-Nasaud", "06"),
            entry("Botosani", "07"),
            entry("Brasov", "08"),
            entry("Braila", "09"),
            entry("Buzau", "10"),
            entry("Caras-Severin", "11"),
            entry("Cluj", "12"),
            entry("Constanta", "13"),
            entry("Covasna", "14"),
            entry("Dambovita", "15"),
            entry("Dolj", "16"),
            entry("Galati", "17"),
            entry("Gorj", "18"),
            entry("Harghita", "19"),
            entry("Hunedoara", "20"),
            entry("Ialomita", "21"),
            entry("Iasi", "22"),
            entry("Ilfov", "23"),
            entry("Maramures", "24"),
            entry("Mehedinti", "25"),
            entry("Mures", "26"),
            entry("Neamt", "27"),
            entry("Olt", "28"),
            entry("Prahova", "29"),
            entry("Satu Mare", "30"),
            entry("Salaj", "31"),
            entry("Sibiu", "32"),
            entry("Suceava", "33"),
            entry("Teleorman", "34"),
            entry("Timis", "35"),
            entry("Tulcea", "36"),
            entry("Vaslui", "37"),
            entry("Valcea", "38"),
            entry("Vrancea", "39"),
            entry("Bucuresti", "40"),
            entry("Calarasi", "51"),
            entry("Giurgiu", "52")
    );

    public List<Cnp> getIdentityNumbers(long populationSize) throws IOException {
        List<Cnp> identityNumbers = new ArrayList<>();
        BigInteger samplePopulation = BigInteger.valueOf(populationSize);

        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("P01.csv");
             InputStreamReader inputStreamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            List<String> lines = bufferedReader.lines().toList();
            LocalDate now = LocalDate.now();
            List<Statistic> statistics = new ArrayList<>();

            lines.forEach(line -> {
                String[] columns = line.split(";");
                statistics.add(getStatistic(columns, now));
            });

            statistics.forEach(statistic -> statistic.getAgeGroups().forEach(group -> {
                BigInteger maxNumberOfPeopleFromAgeGroup = group.getCount().multiply(samplePopulation).divide(TOTAL_POPULATION);
                LongStream.range(1, maxNumberOfPeopleFromAgeGroup.intValue()).forEach(_ ->
                        identityNumbers.add(getCNP(statistic, group)));
            }));
        }

        return identityNumbers;
    }

    private Statistic getStatistic(String[] columns, LocalDate now) {
        return new Statistic(
                columns[0],
                List.of(
                        new AgeGroup(now.minusYears(4), now, columns[2]),
                        new AgeGroup(now.minusYears(9), now.minusYears(5), columns[3]),
                        new AgeGroup(now.minusYears(14), now.minusYears(10), columns[4]),
                        new AgeGroup(now.minusYears(19), now.minusYears(15), columns[5]),
                        new AgeGroup(now.minusYears(24), now.minusYears(20), columns[6]),
                        new AgeGroup(now.minusYears(29), now.minusYears(25), columns[7]),
                        new AgeGroup(now.minusYears(34), now.minusYears(30), columns[8]),
                        new AgeGroup(now.minusYears(39), now.minusYears(35), columns[9]),
                        new AgeGroup(now.minusYears(39), now.minusYears(40), columns[10]),
                        new AgeGroup(now.minusYears(44), now.minusYears(45), columns[11]),
                        new AgeGroup(now.minusYears(49), now.minusYears(50), columns[12]),
                        new AgeGroup(now.minusYears(54), now.minusYears(55), columns[13]),
                        new AgeGroup(now.minusYears(59), now.minusYears(60), columns[14]),
                        new AgeGroup(now.minusYears(64), now.minusYears(65), columns[15]),
                        new AgeGroup(now.minusYears(69), now.minusYears(70), columns[16]),
                        new AgeGroup(now.minusYears(74), now.minusYears(75), columns[17]),
                        new AgeGroup(now.minusYears(79), now.minusYears(80), columns[18]),
                        new AgeGroup(now.minusYears(120), now.minusYears(85), columns[19])),
                "M".equals(columns[20]) ? Gender.MALE : Gender.FEMALE
        );
    }

    private Cnp getCNP(Statistic statistic, AgeGroup ageGroup) {
        String birthday = getBirthday(ageGroup);
        return new Cnp(
                getGender(statistic, ageGroup),
                birthday.substring(0, 2),
                birthday.substring(2, 4),
                birthday.substring(4, 6),
                COUNTY_CODES.get(statistic.getCounty().substring(0, 1).toUpperCase() + statistic.getCounty().substring(1).toLowerCase()),
                String.format("%03d", ThreadLocalRandom.current().nextInt(1, 1000)),
                String.valueOf(ThreadLocalRandom.current().nextInt(10)));
    }

    private String getGender(Statistic statistic, AgeGroup ageGroup) {
        if (ageGroup.getBirthStartYear().isAfter(YEAR_2000_START_DATE)) {
            return statistic.getGender() == Gender.MALE ? "5" : "6";
        }
        return statistic.getGender() == Gender.MALE ? "1" : "2";
    }

    private String getBirthday(AgeGroup ageGroup) {
        int days = Math.abs((int) ChronoUnit.DAYS.between(ageGroup.getBirthStartYear(), ageGroup.getBirthEndYear()));
        LocalDate randomDate = ageGroup.getBirthStartYear().plusDays(ThreadLocalRandom.current().nextInt(days + 1));
        return DATE_TIME_FORMATTER.format(randomDate);
    }
}
