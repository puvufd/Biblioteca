import javax.swing.*;
import java.awt.*;

public class UserForm1 extends JFrame {
    public UserForm1() {
        setTitle("User Form 1");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Добро пожаловать, User 1!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
