import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        } else {
            return false;
        }
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + code + "\nTitle: " + title + "\nDescription: " + description +
               "\nCapacity: " + capacity + "\nSchedule: " + schedule + "\nEnrolled Students: " + enrolledStudents;
    }
}

class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            return true;
        } else {
            return false;
        }
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            course.dropStudent();
            registeredCourses.remove(course);
            return true;
        } else {
            return false;
        }
    }
}

class CourseDatabase {
    private List<Course> courses;

    public CourseDatabase() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Course getCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    public List<Course> getCourses() {
        return courses;
    }
}

class StudentDatabase {
    private List<Student> students;

    public StudentDatabase() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getStudents() {
        return students;
    }
}

public class CourseManagementSystem {
    private static CourseDatabase courseDatabase = new CourseDatabase();
    private static StudentDatabase studentDatabase = new StudentDatabase();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        displayMenu();
    }

    private static void initializeData() {
        courseDatabase.addCourse(new Course("CS101", "Introduction to Computer Science", "Learn the basics of computer science", 30, "MWF 9:00-10:00"));
        courseDatabase.addCourse(new Course("MATH101", "Calculus I", "Introduction to Calculus", 25, "TTh 10:00-11:30"));
        courseDatabase.addCourse(new Course("PHYS101", "Physics I", "Introduction to Physics", 20, "MWF 11:00-12:00"));

        studentDatabase.addStudent(new Student("S001", "John Doe"));
        studentDatabase.addStudent(new Student("S002", "Jane Smith"));
    }

    private static void displayMenu() {
        while (true) {
            System.out.println("\nCourse Management System");
            System.out.println("1. List Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. List Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    listRegisteredCourses();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseDatabase.getCourses()) {
            System.out.println(course);
            System.out.println("Available Slots: " + (course.getCapacity() - course.getEnrolledStudents()));
            System.out.println();
        }
    }

    private static void registerCourse() {
        System.out.print("\nEnter your Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentDatabase.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code to register: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("Successfully registered for " + course.getTitle());
        } else {
            System.out.println("Registration failed. Course might be full.");
        }
    }

    private static void dropCourse() {
        System.out.print("\nEnter your Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentDatabase.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code to drop: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Successfully dropped " + course.getTitle());
        } else {
            System.out.println("Drop failed. You might not be registered for this course.");
        }
    }

    private static void listRegisteredCourses() {
        System.out.print("\nEnter your Student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentDatabase.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Registered Courses:");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course);
            System.out.println();
        }
    }
}
