import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserController {
    private UserModel model;
    private UserView view;
    private ReaderForm readerForm;
    private WriterForm writerForm;
    private BookModel bookModel;

    public UserController(UserModel model, UserView view) {
        this.model = model;
        this.view = view;
        this.bookModel = new BookModel(); // Создать модель книг
        this.view.setController(this);

        this.view.addLoginListener(new LoginListener());
        this.view.addRegisterListener(new RegisterListener());
        this.view.addClearListener(new ClearListener());
        this.view.addSwitchToRegisterListener(new SwitchToRegisterListener());
        this.view.addSwitchToLoginListener(new SwitchToLoginListener());
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (model.validateCredentials(username, password)) {
                UserModel.User user = model.getUserByUsername(username);
                view.showMessage("Login Successful!");

                String role = user.getRole();
                if ("admin".equals(role)) {
                    openAdminForm();
                } else if ("Reader".equals(role) || "Читатель".equals(role)) {
                    openReaderForm();
                } else if ("Writer".equals(role) || "Писатель".equals(role)) {
                    openWriterForm();
                }
            } else {
                view.showMessage("Invalid Credentials!");
            }
        }
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = view.getRegisterUsername();
            String password = view.getRegisterPassword();
            String role = view.getSelectedRole();

            if (model.register(username, password, role)) {
                view.showMessage("Registration Successful!");
                view.showLoginPanel(); // Переключиться на панель входа после успешной регистрации
            } else {
                view.showMessage("Registration Failed: Username already exists!");
            }
        }
    }

    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.clearLoginFields();
        }
    }

    class SwitchToRegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.showRegisterPanel();
        }
    }

    class SwitchToLoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.showLoginPanel();
        }
    }

    private void openAdminForm() {
        AdminForm adminForm = new AdminForm(model.getUsers());
        adminForm.setVisible(true);

        adminForm.addDeleteListener(e -> {
            String username = adminForm.getSelectedUsername();
            if (username != null) {
                model.removeUser(username);
                adminForm.refreshUserTable(model.getUsers());
            }
        });

        adminForm.addMakeAdminListener(e -> {
            String username = adminForm.getSelectedUsername();
            if (username != null) {
                model.makeAdmin(username);
                adminForm.refreshUserTable(model.getUsers());
            }
        });
    }

    private void openReaderForm() {
        readerForm = new ReaderForm(view.isEnglish(), bookModel);
        readerForm.setVisible(true);
    }

    private void openWriterForm() {
        writerForm = new WriterForm(view.isEnglish(), bookModel);
        writerForm.setVisible(true);
    }

    public void updateFormsLanguage(boolean isEnglish) {
        if (readerForm != null) {
            readerForm.updateLanguage(isEnglish);
        }
        if (writerForm != null) {
            writerForm.updateLanguage(isEnglish);
        }
    }
}