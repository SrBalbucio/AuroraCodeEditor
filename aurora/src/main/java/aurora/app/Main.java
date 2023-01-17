package aurora.app;

import java.awt.Color;

import javax.swing.UIManager;

import aurora.app.frames.CodeWindow;
import aurora.app.frames.MainWindow;
import com.formdev.flatlaf.FlatDarculaLaf;
public class Main {

	public static void main(String[] args) {
		
		try {
			//	FlatDarculaLaf.setup();
			// 	UIManager.setLookAndFeel(new FlatDarculaLaf());
			//	FlatDarculaLaf.setup()
			// FlatDarkFlatIJTheme.setup();
			FlatDarculaLaf.setup();
			UIManager.put("TabbedPane.showTabSeparators", false);
			//UIManager.put("TabbedPane.tabSeparatorsFullHeight", true);
			UIManager.put("TabbedPane.selectedBackground", new Color(0, 0, 0, 13));
			UIManager.put("Component.focusWidth", 0);
			UIManager.put("Component.innerFocusWidth", 0);
		}catch(Exception ex) {
			System.err.println("Failed to load theme");
		}
		
		//CodeWindow window = new CodeWindow();
		MainWindow main = new MainWindow();
	}

}
