import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

public class Register extends JPanel implements ActionListener {
    JLabel userLabel = new JLabel("UserName: ");
    JTextField userTF = new JTextField();
    JLabel passwordL = new JLabel("Password: ");
    JPasswordField passwordPF = new JPasswordField();
    JLabel confirmpassLabel = new JLabel("Confirm: ");
    JPasswordField confirmpassPF = new JPasswordField();
    JButton registerbtn = new JButton("Register");
    JButton backbtn = new JButton("Back");

    public Register(){
        JPanel loginP = new JPanel();
        loginP.setLayout(new GridLayout(4,2));
        loginP.add(userLabel);
        loginP.add(userTF);
        loginP.add(passwordL);
        loginP.add(passwordPF);
        loginP.add(confirmpassLabel);
        loginP.add(confirmpassPF);
        loginP.add(registerbtn);
        loginP.add(backbtn);
        registerbtn.addActionListener(this);
        backbtn.addActionListener(this);

        add(loginP);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerbtn && passwordPF.getPassword().length > 0 && userTF.getText().length() > 0){
            String password = new String(passwordPF.getPassword());
            String confirmpassword = new String(confirmpassPF.getPassword());
            if(password.equals(confirmpassword)){
                try {
                    BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
                    String line = input.readLine();
                    while(line != null){
                        StringTokenizer st = new StringTokenizer(line);
                        if(userTF.getText().equals(st.nextToken())) {
                            System.out.println("User Already Exist!!");
                            return;
                        }
                        line = input.readLine();
                    }
                    input.close();
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(password.getBytes());

                    byte[] byteData = md.digest();
                    StringBuffer sb = new StringBuffer();
                    for(int i =0; i<byteData.length; i++){
                        sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
                    }
                    BufferedWriter output = new BufferedWriter(new FileWriter("passwords.txt",true));
                    output.write(userTF.getText()+" "+sb.toString()+"\n");
                    output.close();
                    Login login = (Login) getParent();
                    login.cl.show(login,"login");

                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        }
        if(e.getSource() == backbtn){
            Login login = (Login) getParent();
            login.cl.show(login,"login");
        }
    }
}
