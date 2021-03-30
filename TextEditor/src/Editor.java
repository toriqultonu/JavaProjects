import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Editor extends JPanel implements ActionListener {
    File file;
    JButton savebtn = new JButton("Save");
    JButton saveclosebtn = new JButton("Save & Close");
    JTextArea text = new JTextArea(20,40);

    public Editor(String s){
        file = new File(s);
        savebtn.addActionListener(this);
        saveclosebtn.addActionListener(this);
        if(file.exists()){
            try {
                BufferedReader input = new BufferedReader(new FileReader(file));
                String line = input.readLine();
                while(line != null){
                    text.append(line+"\n");
                    line = input.readLine();
                }
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        add(savebtn);
        add(saveclosebtn);
        add(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileWriter out = new FileWriter(file);
            out.write(text.getText());
            out.close();
            if(e.getSource() == saveclosebtn){
                Login login = (Login) getParent();
                login.cl.show(login,"fs");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
