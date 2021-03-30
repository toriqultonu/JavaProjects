import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Login extends JPanel implements ActionListener {
    JLabel userLabel = new JLabel("UserName: ");
    JTextField userTF = new JTextField();
    JLabel passwordLabel = new JLabel("Password: ");
    JPasswordField passwordPF = new JPasswordField();
    JPanel loginP = new JPanel(new GridLayout(3, 2));
    JPanel panel = new JPanel();
    JButton loginbtn = new JButton("Login");
    JButton registerbtn = new JButton("Register");
    CardLayout cl;

    Login() {
        setLayout(new CardLayout());
        loginP.add(userLabel);
        loginP.add(userTF);
        loginP.add(passwordLabel);
        loginP.add(passwordPF);
        loginbtn.addActionListener(this);
        registerbtn.addActionListener(this);
        loginP.add(loginbtn);
        loginP.add(registerbtn);
        panel.add(loginP);
        add(panel, "login");
        cl = (CardLayout) getLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == loginbtn){
            try {
                BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
                String password = null;
                String line = input.readLine();
                while(line != null){
                    StringTokenizer st = new StringTokenizer(line);
                    if(userTF.getText().equals(st.nextToken()))
                        password = st.nextToken();
                    line = input.readLine();
                }
                input.close();
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(new String(passwordPF.getPassword()).getBytes());

                byte[] byteData = md.digest();
                StringBuffer sb = new StringBuffer();
                for(int i =0; i<byteData.length; i++){
                    sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));}
                if(password.equals(sb.toString())) {
                    add(new FileSave(userTF.getText()),"fs");
                    cl.show(this,"fs");
                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                noSuchAlgorithmException.printStackTrace();
            }
        }
        if(e.getSource() == registerbtn) {
            add(new Register(), "register");
            cl.show(this, "register");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        Login login = new Login();
        frame.add(login);
        frame.setVisible(true);
    }
}
