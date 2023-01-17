package aurora.editor;

import java.awt.*;
import java.io.File;

import aurora.app.AppConstants;
import aurora.app.frames.CodeWindow;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import aurora.editor.font.FontManager;
import aurora.listeners.LineListener;

public class SyntaxScrollPane extends RTextScrollPane{

	RSyntaxTextArea textArea;
	boolean isSaved = false;
	File file;
	Gutter gutter;
	LineListener lineListener;
	
	public SyntaxScrollPane(RSyntaxTextArea textArea, CodeWindow window) {
		super(textArea);
		this.textArea = textArea;
		lineListener = new LineListener(window);
		Font font = FontManager.getFont(AppConstants.REGULAR);
		gutter = this.getGutter();
		gutter.setSize(gutter.getWidth()+20, gutter.getHeight());
		gutter.setLineNumberFont(font);
		gutter.setBorderColor(getBackground());
		gutter.setFoldBackground(getBackground());
		gutter.setBackground(new Color(49, 51, 53));
		this.setLineNumbersEnabled(true);
		textArea.addKeyListener(lineListener);
		textArea.addMouseListener(lineListener);
	}
	
	public RSyntaxTextArea getSyntaxArea() {
		return this.textArea;
	}
	
	public boolean getIsSaved() {
		return isSaved;
	}
	
	public void setIsSaved(boolean val) {
		isSaved = val;
	}
	
	//set file
	public void setFile(File file) {
		this.file = file;
	}
	
	//get file
	public File getFile() {
		return this.file;
	}
	
	//setting up fonts
	private void setupGutter(Font font) {
		
	}
}
