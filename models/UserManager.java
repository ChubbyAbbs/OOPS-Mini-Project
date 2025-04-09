package auth;

import java.util.*;

public class UserManager {
    private static final Map<String, String> studentMap = new HashMap<>();
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    static {
        // Predefined student users
        studentMap.put("rahul", "123");
        studentMap.put("ayesha", "abc");
    }

    public static boolean isValidUser(String username, String password) {
        if (username.equals(ADMIN_USER)) {
            return password.equals(ADMIN_PASS);
        }
        return studentMap.containsKey(username) && studentMap.get(username).equals(password);
    }

    public static boolean isAdmin(String username) {
        return username.equals(ADMIN_USER);
    }

    public static Set<String> getAllStudents() {
        return studentMap.keySet();
    }

    public static boolean addStudent(String username, String password) {
        if (studentMap.containsKey(username)) return false;
        studentMap.put(username, password);
        return true;
    }
}
