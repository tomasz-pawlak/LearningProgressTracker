package tracker;

import java.util.*;

public class Engine {
    private List<Student> studentList = new ArrayList<>();
    Map<String, String> activityMap = new HashMap<>
            (Map.of("Java", "0", "DSA", "0", "Databases", "0", "Spring", "0"));
    Set<Student> studentNotification = new HashSet<>();
    Statistics statistics = new Statistics();
    StudentService studentService = new StudentService();


    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();


            if (command.trim().isEmpty()) {
                System.out.println("no input");
            } else {
                switch (command) {
                    case "add students" -> {
                        System.out.println("Enter student credentials or 'back' to return.");
                        studentService.addStudent(scanner, studentList);
                    }
                    case "add points" -> studentService.addCoursePoints(studentList, activityMap, scanner);
                    case "list" -> studentService.showAllStudents(studentList);
                    case "find" -> statistics.showCoursePoints(studentList, scanner);
                    case "statistics" -> statistics.showStatistics(scanner, studentList, activityMap);
                    case "notify" -> studentService.notifyStudent(studentList, studentNotification);
                    case "exit" -> exitProgram();
                    case "back" -> System.out.println("Enter 'exit' to exit the program.");
                    default -> System.out.println("Error: unknown command!");
                }
            }
        }
    }

    private static void exitProgram() {
        System.out.println("Bye!");
        System.exit(0);
    }


}
