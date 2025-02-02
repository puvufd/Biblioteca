

import javax.swing.*;
import java.awt.*;

public class UserForm3 extends JFrame {
    public UserForm3() {
        setTitle("User Form 3");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Добро пожаловать, User 3!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
