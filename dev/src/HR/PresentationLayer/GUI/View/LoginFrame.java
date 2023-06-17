package HR.PresentationLayer.GUI.View;

import HR.PresentationLayer.GUI.Model.LoginModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends AbstractFrame {
    protected final JTextField usernameField;
    protected final JPasswordField passwordField;

    public LoginFrame() {
        super(1, new LoginModel());
        setTitle("Login");
        setResizable(false);

        // Create username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        add(usernamePanel);

        // Create password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        add(passwordPanel);

        // Create login button
        addButton("Login");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
