package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    void showStatistics(Scanner scanner, List<Student> studentList, Map<String, String> activityMap) {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        if (studentList.isEmpty()) {
            noDataStatistics();
            showCourseStatistic(studentList, scanner);
        } else {
            mostLeastPopular(studentList);
            highLowActivity(activityMap);
            easiestHardestCourse(studentList);
            showCourseStatistic(studentList, scanner);
        }
    }

     static void noDataStatistics() {
        Map<String, String> emptyMap = new LinkedHashMap<>(
        );

        emptyMap.put("Most popular:", "n/a");
        emptyMap.put("Least popular:", "n/a");
        emptyMap.put("Highest activity:", "n/a");
        emptyMap.put("Lowest activity:", "n/a");
        emptyMap.put("Easiest course:", "n/a");
        emptyMap.put("Hardest course:", "n/a");

        emptyMap.forEach((key, value) -> System.out.println(key + " " + value));
    }

    void mostLeastPopular(List<Student> list) {
        int javaCounter = (int) list.stream().filter(n -> n.getCourse().getJava() > 0).count();
        int dsaCounter = (int) list.stream().filter(n -> n.getCourse().getDsa() > 0).count();
        int dbCounter = (int) list.stream().filter(n -> n.getCourse().getDatabases() > 0).count();
        int springCounter = (int) list.stream().filter(n -> n.getCourse().getSpring() > 0).count();

        Map<String, String> result = new HashMap<>();
        result.put("Java", String.valueOf(javaCounter));
        result.put("DSA", String.valueOf(dsaCounter));
        result.put("Databases", String.valueOf(dbCounter));
        result.put("Spring", String.valueOf(springCounter));


        Optional<Map.Entry<String, String>> max = result.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
        Optional<Map.Entry<String, String>> min = result.entrySet().stream().min(Comparator.comparing(Map.Entry::getValue));

        Map<String, String> maxEntry = new HashMap<>();

        for (Map.Entry<String, String> entry : result.entrySet()) {
            if (entry.getValue().equals(max.get().getValue())) {
                maxEntry.put(entry.getKey(), entry.getValue());
            }
        }
        Map<String, String> minEntry = new HashMap<>();

        for (Map.Entry<String, String> entry : result.entrySet()) {
            if (entry.getValue().equals(min.get().getValue())) {
                minEntry.put(entry.getKey(), entry.getValue());
            }
        }

        System.out.print("Most popular: ");
        maxEntry.forEach((k, v) -> System.out.print(k + " "));
        System.out.println();
        if (minEntry.size() == result.size()) {
            System.out.println("Least popular: n/a");
        } else {
            System.out.println("Least popular: " + min.get().getKey());
        }
    }

    void highLowActivity(Map<String, String> activityMap) {

        Optional<Map.Entry<String, String>> max = activityMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
        Optional<Map.Entry<String, String>> min = activityMap.entrySet().stream().min(Comparator.comparing(Map.Entry::getValue));

        Map<String, String> maxEntry = new HashMap<>();

        for (Map.Entry<String, String> entry : activityMap.entrySet()) {
            if (entry.getValue().equals(max.get().getValue())) {
                maxEntry.put(entry.getKey(), entry.getValue());
            }
        }

        Map<String, String> minEntry = new HashMap<>();

        for (Map.Entry<String, String> entry : activityMap.entrySet()) {
            if (entry.getValue().equals(min.get().getValue())) {
                minEntry.put(entry.getKey(), entry.getValue());
            }
        }

        System.out.print("Highest activ: ");
        maxEntry.forEach((k, v) -> System.out.print(k + " "));
        System.out.println();
        if (minEntry.size() == activityMap.size()) {
            System.out.println("Lowest activ:: n/a");
        } else {
            System.out.println("Lowest activ:: " + min.get().getKey());
        }

    }

    void easiestHardestCourse(List<Student> list) {

        int javaCounter = (int) list.stream().filter(n -> n.getCourse().getJava() > 0).count();
        int dsaCounter = (int) list.stream().filter(n -> n.getCourse().getDsa() > 0).count();
        int dbCounter = (int) list.stream().filter(n -> n.getCourse().getDatabases() > 0).count();
        int springCounter = (int) list.stream().filter(n -> n.getCourse().getSpring() > 0).count();

        int javaPointsSum = list.stream().map(Student::getCourse).mapToInt(Course::getJava).sum();
        int dsaPointsSum = list.stream().map(Student::getCourse).mapToInt(Course::getDsa).sum();
        int dbPointsSum = list.stream().map(Student::getCourse).mapToInt(Course::getDatabases).sum();
        int springPointsSum = list.stream().map(Student::getCourse).mapToInt(Course::getSpring).sum();

        int avgJava = 0;
        int avgDsa = 0;
        int avgDb = 0;
        int avgSpring = 0;

        try {
            avgJava = javaPointsSum / javaCounter;
        } catch (Exception e) {
        }
        try {
            avgDsa = dsaPointsSum / dsaCounter;
        } catch (Exception e) {
        }
        try {
            avgDb = dbPointsSum / dbCounter;
        } catch (Exception e) {
        }
        try {
            avgSpring = springPointsSum / springCounter;
        } catch (Exception e) {
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("Java", avgJava);
        result.put("Dsa", avgDsa);
        result.put("DB", avgDb);
        result.put("Spring", avgSpring);

        Optional<Map.Entry<String, Integer>> max = result.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
        Optional<Map.Entry<String, Integer>> min = result.entrySet().stream().filter(s -> s.getValue() > 0).min(Comparator.comparing(Map.Entry::getValue));

        System.out.println("Easiest course: " + max.get().getKey());
        System.out.println("Hardest course: " + min.get().getKey());
    }

    void showCourseStatistic(List<Student> list, Scanner scanner) {

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.equals("back")) {
                break;
            }

            switch (input.toLowerCase()) {
                case "java" -> javaStatistics(list);
                case "dsa" -> dsaStatistics(list);
                case "databases" -> dbStatistics(list);
                case "spring" -> springStatistics(list);
                default -> System.out.println("Unknown course.");
            }
        }

    }

    void javaStatistics(List<Student> list) {

        Map<Integer, Integer> map = new HashMap<>();

        for (Student student : list) {
            map.put(student.id, student.getCourse().getJava());
        }

        Map<Integer, Integer> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("Java");
        System.out.println("id     points completed");
        result.forEach((k, v) -> System.out.println((k + "     " + v + "    " + convertPercentage(v, 600) + "%")));
    }


    void dsaStatistics(List<Student> list) {

        Map<Integer, Integer> map = new HashMap<>();

        for (Student student : list) {
            map.put(student.id, student.getCourse().getDsa());
        }

        Map<Integer, Integer> result = map.entrySet().stream()
                .filter(s -> s.getValue() > 0)
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("DSA");
        System.out.println("id     points completed");
        result.forEach((k, v) -> System.out.println((k + "     " + v + "    " + convertPercentage(v, 400) + "%")));
    }


    void dbStatistics(List<Student> list) {
        Map<Integer, Integer> map = new HashMap<>();

        for (Student student : list) {
            map.put(student.id, student.getCourse().getDatabases());
        }

        Map<Integer, Integer> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("Databases");
        System.out.println("id     points completed");
        result.forEach((k, v) -> System.out.println((k + "     " + v + "    " + convertPercentage(v, 480) + "%")));
    }

    void springStatistics(List<Student> list) {

        Map<Integer, Integer> map = new HashMap<>();

        for (Student student : list) {
            map.put(student.id, student.getCourse().getSpring());
        }

        Map<Integer, Integer> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("Spring");
        System.out.println("id     points completed");
        result.forEach((k, v) -> System.out.println((k + "     " + v + "    " + convertPercentage(v, 550) + "%")));
    }

    BigDecimal convertPercentage(int value, int maxPoint) {
        double pattern = ((value * 100.0f) / maxPoint);
        return new BigDecimal(pattern).setScale(1, RoundingMode.HALF_UP);

    }

    void showCoursePoints(List<Student> list, Scanner scanner) {
        System.out.println("Enter an id or 'back' to return:");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.equals("back")) {
                break;
            }

            int id = Integer.parseInt(input);

            if (!isStudentAlreadyExistById(list, id)) {
                System.out.println("No student is found for id=" + id + ".");
            } else {
                Student student = list.get(id - 1);
                System.out.println(student.id + " points: Java=" + student.getCourse().getJava() + "; DSA=" + student.getCourse().getDsa() +
                        "; Databases=" + student.getCourse().getDatabases() + "; Spring=" + student.getCourse().getSpring());
            }
        }

    }

    boolean isStudentAlreadyExistById(List<Student> studentList, int id) {
        return studentList.stream().anyMatch(n -> n.getId() == id);
    }
}
