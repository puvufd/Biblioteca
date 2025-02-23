import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserView extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private JButton switchLanguageButton = new JButton("Switch to Russian");
    private boolean isEnglish = true;

    private UserController controller;

    public UserView() {
        setTitle("User Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(switchLanguageButton);
        add(buttonPanel, BorderLayout.SOUTH);

        switchLanguageButton.addActionListener(e -> switchLanguage());

        cardLayout.show(mainPanel, "Login");
    }

    public void setController(UserController controller) {
        this.controller = controller;
    }

    public void showLoginPanel() {
        cardLayout.show(mainPanel, "Login");
    }

    public void showRegisterPanel() {
        cardLayout.show(mainPanel, "Register");
    }

    public String getUsername() {
        return loginPanel.getUsername();
    }

    public String getPassword() {
        return loginPanel.getPassword();
    }

    public String getRegisterUsername() {
        return registerPanel.getUsername();
    }

    public String getRegisterPassword() {
        return registerPanel.getPassword();
    }

    public String getSelectedRole() {
        return registerPanel.getSelectedRole();
    }

    public void addLoginListener(ActionListener listener) {
        loginPanel.addLoginButtonListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerPanel.addRegisterButtonListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        loginPanel.addClearButtonListener(listener);
    }

    public void addSwitchToRegisterListener(ActionListener listener) {
        loginPanel.addSwitchToRegisterListener(listener);
    }

    public void addSwitchToLoginListener(ActionListener listener) {
        registerPanel.addSwitchToLoginListener(listener);
    }

    public void clearLoginFields() {
        loginPanel.clearFields();
    }

    public void clearRegisterFields() {
        registerPanel.clearFields();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void switchLanguage() {
        isEnglish = !isEnglish;
        loginPanel.updateLanguage(isEnglish);
        registerPanel.updateLanguage(isEnglish);
        switchLanguageButton.setText(isEnglish ? "Switch to Russian" : "Переключить на английский");

        if (controller != null) {
            controller.updateFormsLanguage(isEnglish);
        }
    }

    public boolean isEnglish() {
        return isEnglish;
    }
}

class LoginPanel extends JPanel {
private JTextField usernameField = new JTextField(20);
private JPasswordField passwordField = new JPasswordField(20);
private JButton loginButton = new JButton("Login");
private JButton clearButton = new JButton("Clear");
private JButton switchToRegisterButton = new JButton("Switch to Register");

public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createButtonPanel(), gbc);

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

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.add(loginButton);
        panel.add(clearButton);
        panel.add(switchToRegisterButton);
        return panel;
    }

    public void addLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addClearButtonListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void addSwitchToRegisterListener(ActionListener listener) {
        switchToRegisterButton.addActionListener(listener);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void updateLanguage(boolean isEnglish) {
        loginButton.setText(isEnglish ? "Login" : "Войти");
        clearButton.setText(isEnglish ? "Clear" : "Очистить");
        switchToRegisterButton.setText(isEnglish ? "Switch to Register" : "Переключиться на регистрацию");
    }
}

class RegisterPanel extends JPanel {
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JComboBox<String> roleComboBox = new JComboBox<>(new String[] { "Reader", "Writer" });
    private JButton registerButton = new JButton("Register");
    private JButton switchToLoginButton = new JButton("Switch to Login");

    public RegisterPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createButtonPanel(), gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.add(registerButton);
        panel.add(switchToLoginButton);
        return panel;
    }

    public void addRegisterButtonListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addSwitchToLoginListener(ActionListener listener) {
        switchToLoginButton.addActionListener(listener);
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

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
    }

    public void updateLanguage(boolean isEnglish) {
        registerButton.setText(isEnglish ? "Register" : "Регистрация");
        switchToLoginButton.setText(isEnglish ? "Switch to Login" : "Переключиться на вход");
        roleComboBox.removeAllItems();
        if (isEnglish) {
            roleComboBox.addItem("Reader");
            roleComboBox.addItem("Writer");
        } else {
            roleComboBox.addItem("Читатель");
            roleComboBox.addItem("Писатель");
        }
    }
}