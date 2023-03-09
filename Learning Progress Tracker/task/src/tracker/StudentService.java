package tracker;

import java.util.*;
import java.util.regex.Pattern;

public class StudentService {

    void addStudent(Scanner scanner, List<Student> studentList) {

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.equals("back")) {
                System.out.printf("Total %d students have been added.", studentList.size());
                break;
            }
            validStudent(input, studentList);
        }
    }

    boolean isStudentAlreadyExistByEmail(List<Student> studentList, String email) {
        return studentList.stream().anyMatch(n -> n.getEmail().equals(email));
    }

    boolean isStudentAlreadyExistById(List<Student> studentList, int id) {
        return studentList.stream().anyMatch(n -> n.getId() == id);
    }

    void validStudent(String input, List<Student> studentList) {
        Map<String, String> convertStudent = convertStudent(input);
        String fistName = convertStudent.get("firstName");
        String lastName = convertStudent.get("lastName");
        String email = convertStudent.get("email");

        if (convertStudent.size() < 3) {
            System.out.println("Incorrect credentials.");
        } else if (isStudentAlreadyExistByEmail(studentList, email)) {
            System.out.println("This email is already taken.");
        } else if (validFirstName(fistName) && validLastName(lastName) && validEmail(email)) {
            Student student = new Student(studentList.size() + 1, fistName, lastName, email);
            studentList.add(student);
            System.out.println("The student has been added.");
        }

    }

    boolean validFirstName(String firstName) {
        Pattern pattern = Pattern.compile("\\w+(\\w|-|')?\\w+");

        if (pattern.matcher(firstName).matches()) {
            return true;
        } else {
            System.out.println("Incorrect first name");
            return false;
        }
    }

    boolean validLastName(String lastName) {
        Pattern pattern = Pattern.compile("(((\\w+('|-)?(\\w-|\\w')*\\w+)\\s?)*)|\\w{2,}");

        if (pattern.matcher(lastName).matches()) {
            return true;
        } else {
            System.out.println("Incorrect last name");
            return false;
        }
    }

    boolean validEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9.-]+$");

        if (pattern.matcher(email).matches()) {
            return true;
        } else {
            System.out.println("Incorrect email");
            return false;
        }
    }

    public Map<String, String> convertStudent(String input) {

        List<String> stringList = List.of(input.split(" "));
        String firstName = stringList.get(0);
        String email = stringList.get(stringList.size() - 1);

        List<String> extraList = new ArrayList<>(stringList);
        extraList.remove(firstName);
        extraList.remove(email);

        String lastName = String.join(" ", extraList);

        Map<String, String> student = new HashMap<>();
        if (!firstName.trim().equals("")) {
            student.put("firstName", firstName);
        }
        if (!lastName.trim().equals("")) {
            student.put("lastName", lastName);
        }
        if (!email.trim().equals("")) {
            student.put("email", email);
        }

        return student;
    }

    void showAllStudents(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            list.forEach(n -> System.out.println(n.getId()));
        }
    }


    void addCoursePoints(List<Student> studentList, Map<String, String> activityMap, Scanner scanner) {
        System.out.println("Enter an id and points or 'back' to return:");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.equals("back")) {
                break;
            }

            List<String> inputs = List.of(input.split(" "));

            if (!validInputEmail(inputs) || !validSize(inputs) || !validFormat(inputs) || !validPoints(inputs)) {
                System.out.println("Incorrect points format");
            } else {

                List<Integer> data = inputs.stream().mapToInt(Integer::parseInt).boxed().toList();

                int id = data.get(0);

                if (!isStudentAlreadyExistById(studentList, id)) {
                    System.out.println("No student is found for id=" + id + ".");
                } else {

                    Student student = studentList.get(id - 1);
                    student.getCourse().setJava(student.getCourse().getJava() + data.get(1));
                    student.getCourse().setDsa(student.getCourse().getDsa() + data.get(2));
                    student.getCourse().setDatabases(student.getCourse().getDatabases() + data.get(3));
                    student.getCourse().setSpring(student.getCourse().getSpring() + data.get(4));
                    updateActivityMap(data, activityMap);

                    studentList.set(id - 1, student);

                    System.out.println("Points updated.");
                }

            }
        }
    }

    boolean validSize(List<String> list) {
        return list.size() == 5;
    }

    boolean validFormat(List<String> list) {
        try {
            list.forEach(Integer::parseInt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean validPoints(List<String> list) {
        List<Integer> data = list.stream().mapToInt(Integer::parseInt).boxed().toList();
        Optional<Integer> answer = data.stream().filter(n -> n < 0).findAny();

        return answer.isEmpty();
    }

    boolean validInputEmail(List<String> input) {
        try {
            Integer.parseInt(input.get(0));
            return true;
        } catch (Exception e) {
            System.out.println("No student is found for id=" + input.get(0));
            return false;
        }

    }

    void updateActivityMap(List<Integer> data, Map<String, String> activityMap) {

        if (data.get(1) > 0) {
            activityMap.put("Java", activityMap.get("Java") + 1);
        }
        if (data.get(2) > 0) {
            activityMap.put("DSA", activityMap.get("DSA") + 1);
        }
        if (data.get(3) > 0) {
            activityMap.put("Databases", activityMap.get("Databases") + 1);
        }
        if (data.get(4) > 0) {
            activityMap.put("Spring", activityMap.get("Spring") + 1);
        }
    }

    void notifyStudent(List<Student> list, Set<Student> studentSet) {
        if (list.size() == 0) {
            System.out.println("Total 0 students have been notified.");

        } else {
            Set<Student> s = new HashSet<>();

            for (Student student : list
            ) {

                if (studentSet.contains(student)) {
                    break;
                }
                if (student.getCourse().getJava() >= 600) {
                    printGrats(student, "! You have accomplished our Java course!", studentSet, s);
                }
                if (student.getCourse().getDsa() >= 400) {
                    printGrats(student, "! You have accomplished our DSA course!", studentSet, s);
                }
                if (student.getCourse().getDatabases() >= 480) {
                    printGrats(student, "! You have accomplished our Databases course!", studentSet, s);
                }
                if (student.getCourse().getSpring() >= 550) {
                    printGrats(student, "! You have accomplished our Spring course!", studentSet, s);
                }

            }
            System.out.println("Total " + s.size() + " students have been notified.");
        }


    }

    private static void printGrats(Student student, String x, Set<Student> studentSet, Set<Student> s) {
        System.out.println("To: " + student.email);
        System.out.println("Re: Your Learning Progress");
        System.out.println("Hello, " + student.getFirstName() + " " + student.getLastName() + x);
        studentSet.add(student);
        s.add(student);
    }
}
