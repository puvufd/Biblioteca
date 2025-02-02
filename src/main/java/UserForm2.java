

import javax.swing.*;
import java.awt.*;

public class UserForm2 extends JFrame {
    public UserForm2() {
        setTitle("User Form 2");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Добро пожаловать, User 2!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
