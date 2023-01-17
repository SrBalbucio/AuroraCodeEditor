package aurora.editor;

import java.awt.Component;
import java.io.File;

import javax.swing.JTabbedPane;

import aurora.app.frames.CodeWindow;
import com.formdev.flatlaf.icons.FlatClearIcon;

public class TabbedPane extends JTabbedPane {

    int tabIndex = 0;
    Component component;
    CodeWindow window;
    FlatClearIcon clearIcon;

    public TabbedPane(CodeWindow window) {
        this.window = window;
        clearIcon = new FlatClearIcon();
        addListener();
    }

    private void addListener() {
        this.addChangeListener(e -> {
            int index = this.getSelectedIndex();

            SyntaxScrollPane scrollPane = (SyntaxScrollPane) this.getSelectedComponent();

            if (scrollPane != null) {
                File file = scrollPane.getFile();
                if (file != null && file.exists()) {
                    String label = getLang(file);
                    window.getStatusBar().setLang(label);
                    window.getStatusBar().setPath(file.getAbsolutePath());
                }
            }
        });
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int index) {
        this.tabIndex = index;
    }

    @Override
    public void addTab(String title, Component component) {
        // TODO Auto-generated method stub

        super.addTab(title, component);
    }

    String getLang(File file) {
        String fileName = file.getName();
        String extension = "";
        int lastIndex = fileName.lastIndexOf(".");

        if (lastIndex >= 0) {
            extension = fileName.substring(lastIndex);
        }

        switch (extension) {
            case ".java":
                return "Java";
            case ".py":
                return "Python";
            case ".js":
                return "JavaScript";
            case ".c":
                return "C";
            case ".cpp":
                return "CPP";
            case ".xml":
                return "XML";
            case ".css":
                return "CSS";
            case ".html":
                return "HTML";
            case ".gradle":
                return "Gradle";
            default:
                return "Plain";
        }
    }

    //getter for clear icon
    public FlatClearIcon getClearIcon() {
        return this.clearIcon;
    }
}
