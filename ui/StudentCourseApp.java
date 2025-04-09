package ui;

import models.Course;
import models.Student;
import services.CourseService;
import services.StudentService;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class StudentCourseApp extends JFrame {
    private Font font = new Font("Segoe UI", Font.PLAIN, 14);
    private JTextField courseNameField;
    private JTextArea courseListArea, studentCoursesArea;
    private CourseService courseService;
    private StudentService studentService;
    private Student currentStudent;
    private boolean isAdmin;

    public StudentCourseApp(String userType) {
        this.isAdmin = userType.equalsIgnoreCase("admin");
        courseService = new CourseService();
        studentService = new StudentService();

        if (!isAdmin) {
            currentStudent = new Student(userType);
            studentService.addStudent(currentStudent);
        }

        setTitle("Student Course Management");
        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 30));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(40, 40, 40));
        container.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Welcome, " + (isAdmin ? "Admin" : currentStudent.getName()));
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // Course Input (admin only)
        if (isAdmin) {
            container.add(createLabel("Course Name:"));
            courseNameField = createRoundedField();
            container.add(courseNameField);
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(container.getBackground());

        JButton viewCoursesBtn = createRoundedButton("View Courses");
        JButton addCourseBtn = createRoundedButton("Add Course");
        JButton deleteCourseBtn = createRoundedButton("Delete Course");
        JButton registerCourseBtn = createRoundedButton("Register Course");

        buttonPanel.add(viewCoursesBtn);
        buttonPanel.add(addCourseBtn);
        buttonPanel.add(deleteCourseBtn);
        buttonPanel.add(registerCourseBtn);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(buttonPanel);

        // Course List Area
        courseListArea = createTextArea("Available Courses");
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(new JScrollPane(courseListArea));

        // Student Course Area
        if (!isAdmin) {
            studentCoursesArea = createTextArea("Registered Courses");
            container.add(Box.createRigidArea(new Dimension(0, 15)));
            container.add(new JScrollPane(studentCoursesArea));
        }

        add(container, BorderLayout.CENTER);

        // === Button Actions ===
        viewCoursesBtn.addActionListener(e -> {
            List<Course> courses = courseService.getAllCourses();
            courseListArea.setText("");
            for (Course c : courses) {
                courseListArea.append(String.format("%-5d  %s%n", c.getCourseId(), c.getCourseName()));
            }
        });

        addCourseBtn.addActionListener(e -> {
            if (!isAdmin) {
                showMsg("Only admin can add courses.");
                return;
            }
            String name = courseNameField.getText().trim();
            if (!name.isEmpty()) {
                courseService.addCourse(new Course(name));
                showMsg("Course added.");
                courseNameField.setText("");
            } else {
                showMsg("Course name is empty.");
            }
        });

        deleteCourseBtn.addActionListener(e -> {
            if (!isAdmin) {
                showMsg("Only admin can delete courses.");
                return;
            }
            String input = JOptionPane.showInputDialog("Enter Course ID to delete:");
            try {
                int id = Integer.parseInt(input);
                courseService.deleteCourse(id);
                showMsg("Course deleted.");
            } catch (Exception ex) {
                showMsg("Invalid ID.");
            }
        });

        registerCourseBtn.addActionListener(e -> {
            if (isAdmin) {
                showMsg("Only students can register.");
                return;
            }
            String input = JOptionPane.showInputDialog("Enter Course ID to register:");
            try {
                int id = Integer.parseInt(input);
                Course course = courseService.getCourseById(id);
                if (course != null) {
                    currentStudent.registerCourse(course);
                    studentService.addStudent(currentStudent);
                    showRegisteredCourses();
                    showMsg("Registered successfully.");
                } else {
                    showMsg("Course not found.");
                }
            } catch (Exception ex) {
                showMsg("Invalid input.");
            }
        });

        if (!isAdmin) {
            showRegisteredCourses(); // show on load
        }

        setVisible(true);
    }

    private void showRegisteredCourses() {
        studentCoursesArea.setText("Registered Courses:\n");
        for (Course c : currentStudent.getRegisteredCourses()) {
            studentCoursesArea.append("- " + c.getCourseName() + "\n");
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createRoundedField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(400, 35));
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(100, 100, 100), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(font);
        return field;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(46, 204, 113));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(new LineBorder(new Color(100, 100, 100), 1, true));
        return button;
    }

    private JTextArea createTextArea(String title) {
        JTextArea area = new JTextArea(5, 40);
        area.setEditable(false);
        area.setBackground(new Color(15, 15, 15));
        area.setForeground(new Color(0, 255, 127));
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1, true),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                Color.WHITE
        ));
        return area;
    }

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
