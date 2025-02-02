import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminForm extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton deleteButton = new JButton("Удалить");
    private JButton makeAdminButton = new JButton("Сделать админом");

    public AdminForm(List<UserModel.User> users) {
        setTitle("Admin Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Имя пользователя", "Роль"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(userTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(220, 220, 220));

        deleteButton.setPreferredSize(new Dimension(150, 30));
        makeAdminButton.setPreferredSize(new Dimension(150, 30));
        buttonPanel.add(deleteButton);
        buttonPanel.add(makeAdminButton);

        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadUserData(users);
    }

    private void loadUserData(List<UserModel.User> users) {
        tableModel.setRowCount(0);
        for (UserModel.User user : users) {
            Object[] row = {user.getUsername(), user.getRole()};
            tableModel.addRow(row);
        }
    }

    public String getSelectedUsername() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addMakeAdminListener(ActionListener listener) {
        makeAdminButton.addActionListener(listener);
    }

    public void refreshUserTable(List<UserModel.User> users) {
        loadUserData(users);
    }
}