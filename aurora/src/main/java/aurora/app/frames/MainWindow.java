package aurora.app.frames;

import aurora.app.AppConstants;
import aurora.app.Icon;
import aurora.editor.font.FontManager;
import aurora.explorer.ClickListener;
import aurora.explorer.IconManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {

    public MainWindow(){
        super(AppConstants.APP_NAME);
        Dimension preferedSize = new Dimension(600,400);
        this.setSize(preferedSize);
        this.setMinimumSize(preferedSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFont(FontManager.getFont(AppConstants.REGULAR));
        create();
        this.setIconImages(new Icon().getImageList());
        this.setVisible(true);
    }

    private void create(){
        this.setLayout(new BorderLayout());
        GridBagConstraints cts = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel menu = new JPanel();
        BoxLayout layout = new BoxLayout(menu, BoxLayout.Y_AXIS){};
        menu.setLayout(layout);
        JLabel logo = new JLabel(IconManager.getIcon("aurora128"));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel name = new JLabel("AURORA CODE EDITOR");
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font font = FontManager.getFont(AppConstants.BOLD, 16);
        name.setFont(font);
        JButton openFolder = new JButton("Abrir Pasta");
        openFolder.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int response;
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            response = chooser.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION) {
                this.dispose();
                new CodeWindow(chooser.getSelectedFile());
            }
        });
        openFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton openArchive = new JButton("Abrir Arquivo (.zip)");
        openArchive.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton openNetwork = new JButton("Conectar em Servidor");
        openNetwork.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton config = new JButton("Settings");
        config.setAlignmentX(Component.CENTER_ALIGNMENT);
        //menu.add(logo);
        //menu.add(new JPanel());
        menu.add(name);
        menu.add(new JPanel());
        menu.add(new JPanel());
        menu.add(openFolder);
        menu.add(new JPanel());
        menu.add(openArchive);
        menu.add(new JPanel());
        menu.add(openNetwork);
        menu.add(new JPanel());
        menu.add(config);
        menu.add(new JPanel());
        panel.add(menu, cts);
        this.add(panel);
    }
}
