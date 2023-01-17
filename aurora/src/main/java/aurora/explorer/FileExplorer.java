package aurora.explorer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import aurora.app.frames.CodeWindow;

public class FileExplorer extends JPanel implements ActionListener {

    private static FileExplorer instance;

    public static FileExplorer getInstance() {
        return instance;
    }

    private String dirPath;
    private File directory;
    private File projectFile;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel model;
    private JTree tree;
    private JButton openFolder;
    private JButton openArchive;
    private JButton openInNetwork;
    private boolean fileOpened = false;
    private ClickListener clickListener;
    private CodeWindow window;

    public FileExplorer(CodeWindow window) {
        instance = this;
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.window = window;
    }

    //
    public void openFolder() {
        JFileChooser chooser = new JFileChooser();
        int response;
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        response = chooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            directory = chooser.getSelectedFile();
            fileOpened = true;
            this.projectFile = new File(directory, "project.aurora");
            clickListener = new ClickListener(directory, tree, window);
            loadFiles();
            //this.remove(openFolder);
            this.setLayout(new BorderLayout());
            this.add(tree, BorderLayout.CENTER);
            this.updateUI();
        }
    }

    public void openFolder(File folder){
        directory = folder;
        fileOpened = true;
        this.projectFile = new File(directory, "project.aurora");
        clickListener = new ClickListener(directory, tree, window);
        loadFiles();
        //this.remove(openFolder);
        this.setLayout(new BorderLayout());
        this.add(tree, BorderLayout.CENTER);
        this.updateUI();
    }

    private void loadFiles() {
        rootNode = new DefaultMutableTreeNode(directory.getName());
        model = new DefaultTreeModel(rootNode, true);
        tree = new JTree();

        tree.addTreeSelectionListener(clickListener);
        tree.addMouseListener(clickListener);
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                                                          Object value, boolean selected, boolean expanded,
                                                          boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, isLeaf, row, focused);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                String str = (String) node.getUserObject();
                // System.out.println(str);
                if (str.endsWith(".java")) {
                    setIcon(IconManager.getIcon("javac"));
                } else if (str.endsWith(".py")) {
                    setIcon(IconManager.getIcon("python1"));
                } else if (str.endsWith(".c")) {
                    setIcon(IconManager.getIcon("c"));
                } else if (str.endsWith(".js")) {
                    setIcon(IconManager.getIcon("javascript"));
                } else if (str.endsWith(".cpp")) {
                    setIcon(IconManager.getIcon("cpp"));
                }
                return c;
            }

        });
        displayFiles(rootNode, directory);
        tree.setModel(model);
    }

    //Method to load files and folders CAUTION : Highly recursive
    private void displayFiles(DefaultMutableTreeNode root, File file) {
        File[] files = file.listFiles();

        for (File f : files) {
            if (f.isDirectory()) {
                DefaultMutableTreeNode sub = new DefaultMutableTreeNode(f.getName());
                sub.setAllowsChildren(true);
                root.add(sub);
                displayFiles(sub, f);
            } else if (f.isFile()) {
                DefaultMutableTreeNode fi = new DefaultMutableTreeNode(f.getName());
                fi.setAllowsChildren(false);
                root.add(fi);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openFolder();
    }

    public JTree getTree() {
        return tree;
    }
}
