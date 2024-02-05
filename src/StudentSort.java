//Imports
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Student Data
class Student implements Comparable<Student> {
    private String name;
    private String address;
    private double GPA;

    //Constructor
    public Student(String name, String address, double GPA) {
        this.name = name;
        this.address = address;
        this.GPA = GPA;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getGPA() {
        return GPA;
    }

    //Sort by Name
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.getName());
    }
}

//
public class StudentSort {
    private static final List<Student> studentList = new LinkedList<>();
    private static final DecimalFormat GPA_FORMAT = new DecimalFormat("#.####");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Student Entry Loop
        while (true) {
            System.out.print("Enter student name (or 'exit' to finish): ");
            String name = scanner.nextLine();

            //Exit Function
            if (name.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter student address: ");
            String address = scanner.nextLine();

            //Get GPA
            double GPA = getValidGPA(scanner);

            // Create a new student object and add it to the list
            Student student = new Student(name, address, GPA);
            studentList.add(student);
        }

        //Sort by Name
        Collections.sort(studentList);

        //Text Output
        writeToFile();

        //Scanner Close
        scanner.close();
    }

    //Validate GPA w/ Regex
    private static double getValidGPA(Scanner scanner) {
        // Regular expression for validating GPA (numeric with optional decimal)
        String regex = "^[0-9]+(\\.[0-9]+)?$";
        Pattern pattern = Pattern.compile(regex);

        while (true) {
            System.out.print("Enter student GPA: ");
            String input = scanner.nextLine();

            Matcher matcher = pattern.matcher(input);

            try {

                double GPA = Double.parseDouble(input);

                if (matcher.matches()) {
                    return GPA;
                } else {
                    System.out.println("Invalid input format. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    private static void writeToFile() {
        // Output to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("student_data.txt"))) {
            writer.write("List of Students:");
            writer.newLine();

            for (Student student : studentList) {
                writer.write("Name: " + student.getName() + ", Address: " + student.getAddress() +
                        ", GPA: " + GPA_FORMAT.format(student.getGPA()));
                writer.newLine();
            }

            System.out.println("Student data written to student_data.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
