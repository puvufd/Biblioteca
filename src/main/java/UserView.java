import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserView extends JFrame {
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Reader", "Writer"});
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JButton clearButton = new JButton("Clear");
    private JButton switchLanguageButton = new JButton("Switch to Russian");
    private boolean isEnglish = true;

    private JLabel usernameLabel = new JLabel("Username:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JLabel roleLabel = new JLabel("Role:");

    private UserController controller;

    public UserView() {
        setTitle("User Form");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(roleComboBox, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(switchLanguageButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        switchLanguageButton.addActionListener(e -> switchLanguage());

        usernameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
    }

    public void setController(UserController controller) {
        this.controller = controller;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getSelectedRole() {
        return (String) roleComboBox.getSelectedItem();
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void switchLanguage() {
        if (isEnglish) {
            usernameLabel.setText("Имя пользователя:");
            passwordLabel.setText("Пароль:");
            roleLabel.setText("Роль:");
            roleComboBox.removeAllItems();
            roleComboBox.addItem("Читатель");
            roleComboBox.addItem("Писатель");
            loginButton.setText("Войти");
            registerButton.setText("Регистрация");
            clearButton.setText("Очистить");
            switchLanguageButton.setText("Переключить на английский");
        } else {
            usernameLabel.setText("Username:");
            passwordLabel.setText("Password:");
            roleLabel.setText("Role:");
            roleComboBox.removeAllItems();
            roleComboBox.addItem("Reader");
            roleComboBox.addItem("Writer");
            loginButton.setText("Login");
            registerButton.setText("Register");
            clearButton.setText("Clear");
            switchLanguageButton.setText("Switch to Russian");
        }
        isEnglish = !isEnglish;

        // Опционально: уведомление контроллера для обновления других форм
        if (controller != null) {
            controller.updateFormsLanguage(isEnglish);
        }
    }

    public boolean isEnglish() {
        return isEnglish;
    }
}