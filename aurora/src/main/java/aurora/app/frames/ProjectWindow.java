package aurora.app.frames;

import aurora.app.Icon;
import aurora.explorer.IconManager;
import balbucio.file.BalbFile;

import javax.swing.*;
import java.awt.*;

public class ProjectWindow extends JFrame {

    private BalbFile project;
    private CodeWindow window;

    public ProjectWindow(CodeWindow window, BalbFile project){
        super("Project Settings");
        this.project = project;
        this.window = window;
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImages(new Icon().getImageList());
        this.setLocationRelativeTo(window);
        create();
        this.setVisible(true);
    }

    private void create(){
        this.setLayout(new BorderLayout());
        JTabbedPane table = new JTabbedPane();
        table.addTab("Geral", createGeneralSettings());
        this.add(table);
    }

    private JPanel createGeneralSettings(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTextField field = new JTextField();
        JTextField field1 = new JTextField();
        panel.add(new JLabel("Nome do Projeto:"));
        panel.add(field);
        panel.add(new JPanel());
        panel.add(new JLabel("Desc. do Projeto:"));
        panel.add(field1);
        panel.add(new JPanel());
        JCheckBox box = new JCheckBox("Juntar packages dentro src? (Invés de org/example juntar em org.example)");
        panel.add(box);
        panel.add(new JPanel());
        JButton button = new JButton("Salvar");
        panel.add(button);
        return panel;
    }
}
