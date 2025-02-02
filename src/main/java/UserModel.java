import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private List<User> users;

    public UserModel() {
        users = new ArrayList<>();
        users.add(new User("admin", "adminpass", "admin")); // Администратор
    }

    public boolean validateCredentials(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean register(String username, String password, String role) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty() && role != null) {
            users.add(new User(username, password, role));
            return true;
        }
        return false;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void removeUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
    }

    public void makeAdmin(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setRole("admin");
            }
        }
    }

    class User {
        private String username;
        private String password;
        private String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getLocalizedRole(boolean isEnglish) {
            if (isEnglish) {
                return role.equals("admin") ? "Admin" : role;
            } else {
                switch (role) {
                    case "Reader":
                        return "Читатель";
                    case "Writer":
                        return "Писатель";
                    default:
                        return role;
                }
            }
        }
    }
}