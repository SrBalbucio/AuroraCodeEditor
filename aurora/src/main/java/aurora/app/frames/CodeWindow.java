package aurora.app.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

import aurora.app.AppConstants;
import aurora.app.Icon;
import aurora.editor.TabbedPane;
import aurora.explorer.FileExplorer;
import aurora.explorer.IconManager;
import aurora.listeners.EditListener;
import aurora.listeners.FileListener;
import aurora.listeners.NewListener;
import aurora.statusbar.StatusBar;
import aurora.toolbar.ToolBar;

public class CodeWindow extends JFrame{

	private JMenuBar menuBar;
	private JMenu file, edit, newItem, buildMenu, windowMenu, terminal, help;
	private JMenuItem  open, save, saveAs, newFile, newFolder, close;  //for File menu
	private JMenuItem cut, copy, paste;  //for Edit menu
	private JMenuItem build;  //for BuildMenu
	private JMenuItem showHideTerminal;
	private TabbedPane tabPane;
	private FileExplorer explorer;
	private boolean folderOpened = false;
	private NewListener newListener;
	public ArrayList<JLabel> list;
	public ArrayList<String> fileNameList;
	private FileListener fileListener;
	private StatusBar statusBar;
	private EditListener editListener;
	private ToolBar toolBar;
	
	
	//temp
	public int i = 0;
	
	public CodeWindow(boolean visible) {
		//Border layout
		setLayout(new BorderLayout());
		
		list = new ArrayList<>();
		fileNameList = new ArrayList<>();
		menuBar = new JMenuBar();
		tabPane = new TabbedPane(this);
		explorer = new FileExplorer(this);
		newListener = new NewListener(this, tabPane, "untitled");
		fileListener = new FileListener(this, tabPane);
		statusBar = new StatusBar();
		editListener = new EditListener(this, tabPane);
		toolBar = new ToolBar();

		// MENU PRINCIPAL
		file = createMenu(file, "File");
		edit = createMenu(edit, "Edit");
		buildMenu = createMenu(buildMenu, "Build");
		windowMenu = createMenu(windowMenu, "Window");
		terminal = createMenu(terminal, "Terminal");
		help = createMenu(help, "Help");

		createFileItems();
		showHideTerminal = createMenuItem(showHideTerminal, "Show/Hide Terminal");
		cut = createMenuItem(cut, "Cut");
		copy = createMenuItem(copy, "Copy");
		paste = createMenuItem(paste, "Paste");

		build = createMenuItem(build, "Build");
		build.addActionListener(e -> build());

		inflateMenu(edit,  cut, copy, paste);  //Edit menu
		inflateMenu(terminal, showHideTerminal); //Terminal Menu
		inflateMenuBar(menuBar, file, edit, buildMenu, windowMenu, terminal, help);
		inflateMenu(buildMenu, build);

		addListener(editListener, cut, copy, paste);

		//setting up accelerators for save, saveAs, open
		newFile.setAccelerator(KeyStroke.getKeyStroke("control alt N"));
		newFolder.setAccelerator(KeyStroke.getKeyStroke("control alt F"));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveAs.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

		JScrollPane scrollPane = new JScrollPane(explorer);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, tabPane);
		
		splitPane.setDividerLocation(250);
		this.add(splitPane, BorderLayout.CENTER);
		this.setJMenuBar(menuBar);
		this.add(statusBar, BorderLayout.SOUTH);
		this.add(toolBar, BorderLayout.NORTH);
		if(visible) {
			initWindow();
		}
	}

	public CodeWindow(File folder){
		this(false);
		JFrame frame = new JFrame();
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setSize(256, 256);
		frame.setIconImages(new Icon().getImageList());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JLabel label = new JLabel(IconManager.getIcon("aurora128"));
		frame.add(label, BorderLayout.CENTER);
		explorer.openFolder(folder);
		initWindow();
		frame.setVisible(false);
		frame.dispose();
	}

	private void createFileItems(){
		newItem = createMenu(newItem, "Novo");
		open = createMenuItem(open, "Abrir");
		open.addActionListener(e -> explorer.openFolder());
		save = createMenuItem(save, "Salvar");
		saveAs = createMenuItem(saveAs, "Salvar como");
		close = createMenuItem(close, "Fechar");
		close.addActionListener(e -> {
			new MainWindow();
			this.dispose();
		});
		newFile = createMenuItem(newItem, "Arquivo");
		newFile.addActionListener(newListener);
		newFolder = createMenuItem(newFolder, "Pasta");
		newFolder.addActionListener(newListener);

		inflateMenu(file,  newItem, open, save, saveAs, close);
		inflateMenu(newItem,  newFile, newFolder);
	}
	
	//Initialization
	private void initWindow() {
		this.setIconImages(new Icon().getImageList());
		this.setSize(900, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(AppConstants.SIMPLE_APP_NAME);
		this.setVisible(true);
	}

	private void build(){
	}
	
	private JMenu createMenu(JMenu menu, String text) {
		menu = new JMenu(text);
		
		return menu;
	}
	
	private  JMenuItem createMenuItem(JMenuItem menuItem, String text) {
		menuItem = new JMenuItem(text);
		
		return menuItem;
	}
	
	//To inflate Menu
	private void inflateMenu(JMenu menu, JMenuItem ...menuItems) {
		
		for(JMenuItem item : menuItems) {
			menu.add(item);
		}
	}
	
	//To inflate MenuBar with menus
	private void inflateMenuBar(JMenuBar menuBar, JMenu ...menus) {
		
		for(JMenu menu : menus) {
			menuBar.add(menu);
		}
	}
	
	private void addListener(ActionListener listener, JMenuItem ...menuItems) {
		for(JMenuItem menuItem : menuItems) {
			menuItem.addActionListener(listener);
		}
	}
	
	//getter for tabbedpane
	public TabbedPane getTabbedPane() {
		return this.tabPane;
	}
	
	//getter for status bar
	public StatusBar getStatusBar() {
		return this.statusBar;
	}
	
 }
