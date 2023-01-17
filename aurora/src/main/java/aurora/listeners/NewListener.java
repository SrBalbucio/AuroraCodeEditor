package aurora.listeners;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import aurora.app.frames.CodeWindow;
import aurora.editor.SyntaxScrollPane;
import aurora.editor.SyntaxTextArea;
import aurora.editor.TabbedPane;
import com.formdev.flatlaf.icons.FlatClearIcon;

public class NewListener implements ActionListener, MouseListener{

	TabbedPane tabPane;
	String title;
	CodeWindow window;
	JButton btnClose;
	SyntaxTextArea textArea;
	SyntaxScrollPane scrollPane;
	JLabel lblTitle;
	
	public NewListener(CodeWindow window, TabbedPane tabPane, String title) {
		this.tabPane = tabPane;
		this.title = title;
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("File")) {
			
			String title = this.title+"-"+window.i;
			textArea = new SyntaxTextArea();
			scrollPane = new SyntaxScrollPane(textArea, window);
			tabPane.addTab(title, scrollPane);
			int index = tabPane.indexOfTab(title);
			JPanel pnlTab = new JPanel(new GridBagLayout());
			pnlTab.setOpaque(false);
			lblTitle = new JLabel(title);
		    btnClose = new JButton(tabPane.getClearIcon());
		    window.list.add(lblTitle);
		    window.fileNameList.add(lblTitle.getText());
		    configureButton(btnClose);
			btnClose.setActionCommand(title);
			btnClose.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String tabTitle = e.getActionCommand();
					
					int tabindex = tabPane.indexOfTab(tabTitle);
					System.out.println(tabindex);
					if(tabindex >= 0) {
						tabPane.removeTabAt(tabindex);
						window.list.remove(tabindex); //temp code	
						window.fileNameList.remove(tabindex);
				    }
				}	
			});

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			pnlTab.add(lblTitle, gbc);

			gbc.gridx++;
			gbc.weightx = 0;
			pnlTab.add(btnClose, gbc);
			tabPane.setTabComponentAt(index, pnlTab);
			window.i++;
			tabPane.setSelectedIndex(tabPane.getTabCount() - 1);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		
		FlatClearIcon icon = (FlatClearIcon) button.getIcon();
		Graphics graphics = button.getGraphics();
		graphics.setColor(Color.orange);
		icon.paintIcon(button, graphics, 0, 0);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		
		FlatClearIcon icon = (FlatClearIcon) button.getIcon();
		Graphics graphics = button.getGraphics();
		graphics.setColor(Color.white);
		icon.paintIcon(button, graphics, 0, 0);
		
	}
	
	private void configureButton(JButton btnClose) {
		btnClose.putClientProperty("JButton.buttonProperty", "roundRect");
		btnClose.setOpaque(false);
		btnClose.setFocusPainted(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setToolTipText("Close");
	}
}
