import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileSave extends JPanel implements ActionListener {
    JLabel label = new JLabel("File List: ");
    JButton filebtn = new JButton("New File");
    JButton openbtn = new JButton("Open");
    JTextField fileTF = new JTextField(10);
    ButtonGroup bg;
    File directory;
    public FileSave(String dir){
        directory = new File(dir);
        directory.mkdir();
        JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+3, 1));
        fileList.add(label);
        bg = new ButtonGroup();
        for(File file: directory.listFiles()){
            JRadioButton radio = new JRadioButton(file.getName());
            radio.setActionCommand(file.getName());
            bg.add(radio);
            fileList.add(radio);
        }
        JPanel jPanel = new JPanel();
        jPanel.add(fileTF);
        jPanel.add(filebtn);
        filebtn.addActionListener(this);
        openbtn.addActionListener(this);
        fileList.add(openbtn);
        fileList.add(jPanel);
        add(fileList);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Login login = (Login) getParent();
        if(e.getSource() == openbtn){
            login.add(new Editor(directory.getName()+"\\"+bg.getSelection().getActionCommand()), "editor");
            login.cl.show(login,"editor");
        }
        if(e.getSource() == filebtn){
            String file = directory.getName()+"\\"+fileTF.getText()+".txt";
            if(fileTF.getText().length() > 0 && (new File(file).exists())){
                login.add(new Editor(file), "editor");
                login.cl.show(login,"editor");
            }
        }
    }
}
