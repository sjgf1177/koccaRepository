import java.awt.*;
import java.awt.event.*;

class MenuPanel extends Container implements MouseListener, MouseMotionListener
{
	// Default Font for Menu Bar labels
	static Font MBAR_FONT = new Font("SansSerif", Font.BOLD, 12);

	PopupMenu popList[];			// A PopupMenu for each item in your "menu bar".
	Label     lblList[];			// Labels to hold the names of "menu bar" items.
	Color     clrDefault;			// Default bkgd color to use for menu bar and labels
	Color     clrDefaultText;		// Default text color to use for text labels
	Color     clrChangeText;
	int       idxHighlight;			// The menu item index that has the focus. (-1=none)
	int       idxPressed;			// Menu item idx that user clicked on
	boolean   bMenuUp;


	// Default constructor for the MenuPanel class.
	MenuPanel()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT, 4, 3));
	//	clrDefault = SystemColor.menu;
	//	clrDefault = new Color(228, 208, 184);
	//         clrDefault = new Color(90, 89 , 90);
	//        clrDefault = new Color(3, 72 , 111);
	        clrDefault = new Color(12, 65 , 154);
	//	clrDefaultText = SystemColor.menuText;
	        clrDefaultText = Color.white;
	 
	        clrChangeText = new Color(165, 252, 248);
		setBackground(clrDefault);
		addMouseMotionListener(this);    
		addMouseListener(this);
		idxHighlight = -1;    idxPressed = -1;    bMenuUp = false;

		Canvas cvsLeft = new Canvas();
		Rectangle rect = getBounds();
		cvsLeft.setSize(6, rect.height-3);
		add(cvsLeft);

		lblList = new Label[1];
		lblList[0] = new Label("Loading...", Label.CENTER);
		lblList[0].addMouseMotionListener(this);    lblList[0].addMouseListener(this);
		add(lblList[0]);

		validate();
	}


	// Converts a MenuBar to a set of Label/PopupMenu pairs used by MenuPanel.
	public void setMenuBar(MenuBar mBar)
	{
		clearMouseListeners();
		int  menuCount = mBar.getMenuCount();
		lblList = new Label[menuCount];
		popList = new PopupMenu[menuCount];

		// For each Menu in the MenuBar...
		for (int i = 0;  i < menuCount;  i++)
		{
			Menu m = mBar.getMenu(i);
			String szName = m.getLabel();
			int itemCount = m.getItemCount();

			// Generate the corresponding Label/PopupMenu object pairs...
			lblList[i] = new Label(szName, Label.CENTER);
			if (MBAR_FONT != null)
				lblList[i].setFont(MBAR_FONT);
			lblList[i].addMouseMotionListener(this);
			lblList[i].addMouseListener(this);
			add(lblList[i]);

			// Build this PopupMenu item by item (assignment won't work).
			popList[i] = new PopupMenu(szName);
			
			popList[i].setFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12)); 
			
			for (int j = 0;  j < itemCount;  j++)
			{
				MenuItem mi = m.getItem(0);
				popList[i].add(mi);
			}
			lblList[i].setBackground(clrDefault);
			lblList[i].setForeground(clrDefaultText);
			
			lblList[i].add(popList[i]);
		}
	}


	// Paints any 3D rectangles around the Menu labels.
	public void paint(Graphics g)
	{
		Rectangle rect = getBounds();

		g.setColor(clrDefault);						// Prepares screen wash
		g.fillRect(0, 0, rect.width, rect.height);	// fill with bkg color

		// Paint 3D lines for dual thumb tabs and one below menus...
		g.draw3DRect(rect.x+3,rect.y+3, 2,rect.height-9, true);
		g.draw3DRect(rect.x+6,rect.y+3, 2,rect.height-9, true);
		g.draw3DRect(rect.x+1,rect.y+rect.height-3, rect.width-4,1, false);
System.out.println("aa" + clrChangeText);
		// Paint the Labels and PopupMenus as appropriate...
		for (int i = 0;  i < lblList.length;  i++)
		{
			rect = lblList[i].getBounds();
			lblList[i].setForeground(clrDefaultText);
			if (i == idxHighlight) {
				g.draw3DRect(rect.x-1,rect.y-1, rect.width+2,rect.height+2, false);
				lblList[i].setForeground(clrChangeText);
				System.out.println("aa" + clrChangeText);
			}
		
			if (i == idxPressed)
			{			    
				g.draw3DRect(rect.x-1,rect.y-1, rect.width+2,rect.height+2, false);
			//	lblList[i].setForeground(clrChangeText);
				if (bMenuUp == false)
				{
					bMenuUp = true;
					popList[i].show(this, rect.x, rect.y+rect.height+1);
				}
			}
		}
	}


	// Used to clear any Listeners before re-creating them with setMenuBar()
	protected void clearMouseListeners()
	{
		for (int i = 0;  i < lblList.length;  i++)
		{
			remove(lblList[i]);
			lblList[i].removeMouseListener(this);
			lblList[i].removeMouseMotionListener(this);
		}
	}


	// A simplified interface to install Menus and register them for events.
	// parent - String for Menu name, or other Menu object (i.e. PopupMenus) 
	// items  - Object array with Strings, MenuItems or "null" for Separators.
	// target - The ActionListener who receives the Menu events.
	/* Example 1:
		mbar.add(makeMenu("Help", new Object[] { "Index","About" }, this) );
	   Example 2:
		mbar.add(makeMenu("Edit", new Object[] { "Undo", null,
			new MenuItem("Cut", new MenuShortcut(KeyEvent.VK_LEFT) ),
			makeMenu("Options", new Object[] { new CheckboxMenuItem("Insert Mode"), }, this) }, this) );
	*/
	static Menu makeMenu(Object parent, Object[] items, Object target)
	{
		Menu m = null;

		if (parent instanceof Menu)
			m = (Menu)parent;
		else if (parent instanceof String)
			m = new Menu((String)parent);
		else
			return null;

		for (int i = 0;  i < items.length;  i++)
		{	
		        /*if (items[i] == null) {
				m.addSeparator();
		        }*/
		        if (items[i] instanceof String)
			{	
			    if(items[i].equals("separator")) {
			        m.addSeparator();
			    }
			    else {    
			        MenuItem mi = new MenuItem((String)items[i]);
				if (target instanceof ActionListener)
					mi.addActionListener((ActionListener)target);
				m.add(mi);
		            }
			}
			else if (items[i] instanceof CheckboxMenuItem  &&
				target instanceof ItemListener)
			{	CheckboxMenuItem cmi = (CheckboxMenuItem)items[i];
				cmi.addItemListener((ItemListener)target);
				m.add(cmi);
			}
			else if (items[i] instanceof MenuItem)
			{	MenuItem mi = (MenuItem)items[i];
				if (target instanceof ActionListener)
					mi.addActionListener((ActionListener)target);
				m.add(mi);
			}
			
		}
		return m;
	}


//// Event Handlers for MouseMotionEvents and MouseEvents...
	// Handle the MouseListener.mouseClicked event.
	public void mousePressed(MouseEvent evt)
	{
		int oldIdx = idxPressed;

		if (idxPressed >= 0)
		{
			idxHighlight = idxPressed;    idxPressed = -1;
			repaint();    return;
		}

		// Which Label[] index is the mouse hovering over?
		idxPressed = -1;
		if (evt.getSource() instanceof Label)
		{
			Label lbl = (Label)evt.getSource();
			for (int i = 0;  i < lblList.length;  i++)
			{
				if (lblList[i] == lbl)
				{
					idxPressed = i;    idxHighlight = -1;    bMenuUp = false;
				}
			}
		}

		if (idxPressed != oldIdx)    repaint();
	}

	// Handle the MouseListener.mouseExited event.
	public void mouseExited(MouseEvent evt)
	{
		idxHighlight = -1;    idxPressed = -1;
		repaint();
	}

	// Handle the MouseListener.mouseReleased event.
	public void mouseReleased(MouseEvent evt)
	{
		if (evt.getSource() instanceof Label)    mouseClicked(evt);
	}

	// Handle the MouseMotionListener.mouseMoved event.
	public void mouseMoved(MouseEvent evt)
	{
		int oldIdx = idxHighlight;

		// Which Label[] index is the mouse hovering over?
		idxHighlight = -1;
		if (evt.getSource() instanceof Label)
		{
			Label lbl = (Label)evt.getSource();
			for (int i = 0;  i < lblList.length;  i++)
			{
				if (lblList[i] == lbl  &&  i != idxPressed)
				{
					idxHighlight = i;
					if (idxPressed >= 0)    idxPressed = i;
				}
			}
		}

		if (idxHighlight != oldIdx)    repaint();
	}

	// Handle the MouseMotionListener.mouseDragged event.
	public void mouseDragged(MouseEvent evt)
	{
		mouseMoved(evt);
	}

	// Unused MouseEvents.
	// Not using MouseAdapter & MouseMotionAdapter yields 2 less .class files.
	public void mouseClicked(MouseEvent evt)    { }
	public void mouseEntered(MouseEvent evt)    { }
}
