package aurora.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import aurora.app.frames.CodeWindow;
import aurora.editor.SyntaxScrollPane;
import aurora.editor.TabbedPane;

public class EditListener implements ActionListener {

	CodeWindow window;
	TabbedPane tabPane;
	
	public EditListener(CodeWindow window, TabbedPane tabPane) {
		this.window = window;
		this.tabPane = tabPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Cut")) {
			cut();
		}else if(actionCommand.equals("Copy")) {
			copy();
		}else if(actionCommand.equals("Paste")) {
			paste();
		}
	}
	
	//cut routine
	private void cut() {
		int index = tabPane.getSelectedIndex();
		SyntaxScrollPane scrollPane = (SyntaxScrollPane) tabPane.getComponentAt(index);
		scrollPane.getTextArea().cut();
	}
	
	//copy routine
	private void copy() {
		int index = tabPane.getSelectedIndex();
		SyntaxScrollPane scrollPane = (SyntaxScrollPane) tabPane.getComponentAt(index);
		scrollPane.getTextArea().copy();
	}
	
	//paste routine
	private void paste() {
		int index = tabPane.getSelectedIndex();
		SyntaxScrollPane scrollPane = (SyntaxScrollPane) tabPane.getComponentAt(index);
		scrollPane.getTextArea().paste();
	}
}
