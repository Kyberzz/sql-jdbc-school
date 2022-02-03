package ua.com.foxminded.sql_jdbc_school.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ua.com.foxminded.sql_jdbc_school.services.dto.StudentDTO;

public class Generator {
    private static final String HYPHEN = "-";
    private static final int MIN_STUDENTS = 10; 
    private static final int MAX_STUDENTS = 30; 
    private static final int AMPLITUDE = 20; 
    private static final double ONE_HALF = 0.5; 
    private static final double DOUBLE_PROBABILITY_VALUE = 4.0;
    private static final int INT_PROBABILITY_VALUE = 3;
    private static final int ONE_STUDENT = 1;
    private static final int ZERO_OF_STUDENTS = 0;
    private static final double DOUBLE_AMPL_PROBABILITY = 10.0;
    private static final int INT_AMPL_PROBABILITY = 10;
    private static final int MAX_NO_GROUP_STUDENTS = 10;
            
    public List<Integer> generateStudentNumber(int studentsNumber, int groupsNumber) {
        List<Integer> result = new ArrayList<>();
        int noGroupStudents = new Random().nextInt(MAX_NO_GROUP_STUDENTS); 
        int remainder = studentsNumber - noGroupStudents;

        for (int i = 0; i < groupsNumber; i++) {
            double probability = new Random().nextInt(INT_PROBABILITY_VALUE) / DOUBLE_PROBABILITY_VALUE;
            int zeroProbability = (int) ((probability + ONE_HALF) - (probability - ONE_HALF));
            double amplitudeProbability = (new Random().nextInt(INT_AMPL_PROBABILITY)) / DOUBLE_AMPL_PROBABILITY;
            int studentsInGroup = (int) (zeroProbability * (MIN_STUDENTS + AMPLITUDE * amplitudeProbability));

            if (studentsInGroup <= remainder || studentsInGroup == 0 && remainder != 0) {
                result.add(studentsInGroup);
                remainder -= studentsInGroup;
            } else if (studentsInGroup > remainder && remainder > MIN_STUDENTS) {
                result.add(remainder);
                remainder = 0;
            } else if (remainder != 0) {
                for (int j = 0; j < result.size(); j++) {
                    if (result.get(j) < MAX_STUDENTS
                        && result.get(j) >= MIN_STUDENTS 
                        && remainder != 0) {
                        result.set(j, result.get(j) + ONE_STUDENT);
                        remainder -= ONE_STUDENT;

                        if (j == (result.size() - 1) && remainder > 0) {
                            j = 0;
                        } else if (remainder == 0) {
                            result.add(ZERO_OF_STUDENTS);
                            break;
                        }
                    } 
                }
            } else {
                result.add(ZERO_OF_STUDENTS);
            }
        }
        return result;
    }

    public List<StudentDTO> generateStudents(List<String> firstNames, List<String> lastNames) {
        return Stream.generate(() -> new StudentDTO(firstNames.get(new Random().nextInt(firstNames.size())),
                                                    lastNames.get(new Random().nextInt(lastNames.size()))))
                     .limit(200).collect(Collectors.toList());
    }

    public List<String> generateGroups() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        return Stream.generate(() -> new StringBuilder()
                .append(alphabet.charAt(new Random().nextInt(alphabet.length())))
                .append(alphabet.charAt(new Random().nextInt(alphabet.length()))).append(HYPHEN)
                .append(new Random().nextInt(9)).append(new Random().nextInt(9)).toString())
            .limit(10).collect(Collectors.toList());
    }
}
