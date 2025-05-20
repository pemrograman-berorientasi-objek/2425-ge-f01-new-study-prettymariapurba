package pbo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import pbo.model.Course;
import pbo.model.Enrollment;
import pbo.model.Student;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Student> students = new TreeMap<>();
        Map<String, Course> courses = new TreeMap<>();
        List<Enrollment> enrollments = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("---")) break;

            String[] parts = line.split("#");
            String command = parts[0];

            switch (command) {
                case "student-add":
                    String nim = parts[1];
                    String nama = parts[2];
                    String prodi = parts[3];
                    students.put(nim, new Student(nim, nama, prodi));
                    break;

                case "course-add":
                    String Kode = parts[1];
                    String Nama = parts[2];
                    int Semester = Integer.parseInt(parts[3]);
                    int Kredit = Integer.parseInt(parts[4]);
                    courses.put(Kode, new Course(Kode, Nama, Semester, Kredit));
                    break;

                case "enroll":
                    String enrollNim = parts[1];
                    String enrollKode = parts[2];
                    Student enrollStudent = students.get(enrollNim);
                    Course enrollCourse = courses.get(enrollKode);
                    if (enrollStudent != null && enrollCourse != null) {
                        enrollments.add(new Enrollment(enrollStudent, enrollCourse));
                    }
                    break;

                case "student-show":
                    String targetNim = parts[1];
                    Student student = students.get(targetNim);
                    if (student != null) {
                        System.out.println(student.getNim() + "|" + student.getNama() + "|" + student.getProdi());
                        List<Course> studentCourses = new ArrayList<>();
                        for (Enrollment e : enrollments) {
                            if (e.getStudent().getNim().equals(targetNim)) {
                                Course c = e.getCourse();
                                if (c != null) studentCourses.add(c);
                            }
                        }
                        
                        studentCourses.sort(Comparator.comparing(Course::getKode));
                        for (Course c : studentCourses) {
                            System.out.println(c.getKode() + "|" + c.getNama() + "|" + c.getSemester() + "|" + c.getKredit());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }
}