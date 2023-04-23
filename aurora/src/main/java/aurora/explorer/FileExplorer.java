package aurora.explorer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import aurora.app.frames.CodeWindow;
import aurora.app.frames.ProjectWindow;
import balbucio.file.BalbFile;

public class FileExplorer extends JPanel implements ActionListener {

    private static FileExplorer instance;

    public static FileExplorer getInstance() {
        return instance;
    }

    private String dirPath;
    private File directory;
    private BalbFile projectFile;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel model;
    private JTree tree;
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
            this.projectFile = new BalbFile(new File(directory, "project.aurora"), true);
            clickListener = new ClickListener(directory, tree, window);
            if (projectFile.get("projectName") == null) {
                configureProject();
            } else {
                loadFiles();
            }
            loadFiles();
            this.setLayout(new BorderLayout());
            this.add(tree, BorderLayout.CENTER);
            this.updateUI();
        }
    }

    public void openFolder(File folder) {
        directory = folder;
        fileOpened = true;
        this.projectFile = new BalbFile(new File(directory, "project.aurora"), true);
        clickListener = new ClickListener(directory, tree, window);
        if (projectFile.get("projectName") == null) {
            configureProject();
        } else {
            loadFiles();
        }
        this.setLayout(new BorderLayout());
        this.add(tree, BorderLayout.CENTER);
        this.updateUI();
    }

    public void configureProject() {
        if (projectFile.get("projectName") == null) {
            new ProjectWindow(window, projectFile);
            loadFiles();
        }
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
        displayFiles(rootNode, directory, false, null);
        tree.setModel(model);
    }

    private void displayFiles(DefaultMutableTreeNode root, File file, boolean src, String pack) {

        DefaultMutableTreeNode rootEx = root;
        List<File> fs = new ArrayList<>();
        List<File> dirs = new ArrayList<>();

        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                dirs.add(f);
            } else {
                fs.add(f);
            }
        }

        for (File f : dirs) {
            DefaultMutableTreeNode fi = new DefaultMutableTreeNode(f.getName());
            fi.setAllowsChildren(true);
            rootEx.add(fi);
            displayFiles(fi, f, false, null);
        }
        for (File f : fs) {
            DefaultMutableTreeNode fi = new DefaultMutableTreeNode(f.getName());
            fi.setAllowsChildren(false);
            rootEx.add(fi);
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
