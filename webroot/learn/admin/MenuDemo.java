import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import netscape.javascript.JSObject;

// Simple demo applet using the MenuPanel class, and examples of using the
// MenuPanel.makeMenu() method...
public class MenuDemo extends Applet implements ActionListener
{
	TextArea  taStatus;
	MenuPanel mPanel;
	MenuBar   mBar;
	String [] menu;
	int menu_cnt;
	String [][] menuItem;
	String [][] menuPath;
	int max_menuItem_cnt;
	int [] menuItem_cnt;

	// Returns Author, Version and Copyright info for this Applet...
	public String getAppletInfo()
	{
		return "MenuDemo - sample using the MenuPanel class";
	}

        public void init() {
        // Initialize the MenuBar items using MenuPanel.makeMenu()...
            mBar = new MenuBar();
            
            menu_cnt = Integer.parseInt(getParameter("menu_cnt"));        //   메뉴의 수	
            max_menuItem_cnt = Integer.parseInt(getParameter("max_menuItem_cnt"));     //   메뉴에 해당되는 최대메뉴아이템의 수      
            menu = new String [menu_cnt];
            menuItem_cnt = new int [menu_cnt];	
            
            for(int i = 0; i < menu_cnt; i++) {
                menu [i] = getParameter("menu" + (i+1));	//	메뉴 수만큼  menu 의 배열 생성
                
                menuItem_cnt [i] = Integer.parseInt(getParameter("menuItem_cnt" + (i+1)));     //   메뉴에 해당되는 메뉴아이템의 수                   		                             
            }
            
            menuItem = new String [menu_cnt][max_menuItem_cnt];	        //   해당 메뉴의 메뉴아이템 배열수 선언    
            menuPath = new String [menu_cnt][max_menuItem_cnt];	        //   해당 메뉴의 메뉴별 페이지경로 배열수 선언    
            
            for(int i = 0; i < menu_cnt; i++) {
                int item_cnt = menuItem_cnt [i];
                for(int j = 0; j < item_cnt; j++) {      
                    menuItem [i][j] = getParameter("menuItem" + (i+1) + "|" + (j+1));		        //       해당 메뉴의 메뉴아이템 배열생성
                    menuPath [i][j] = getParameter("menuPath" + (i+1) + "|" + (j+1));		        //       해당 메뉴의 메뉴별 페이지경로  배열생성
                 //   System.out.println("menuItem [" +i + "][" + j + "] : " + menuItem [i][j]);
                }
            }
            
            for(int i = 0; i < menu_cnt; i++) {           
                int item_cnt = menuItem_cnt [i];   
                Object [] menuItemObj = new Object [item_cnt];
           //     System.out.println(i + " = " + item_cnt);
                for(int j = 0; j < item_cnt; j++) {      
                 //   System.out.println("menuItem [" +i + "][" + j + "] : " + menuItem [i][j]);
                    menuItemObj [j] = menuItem [i][j];
                }     //  System.out.println("menu : " + menu [i]);         
                mBar.add(MenuPanel.makeMenu(" " + menu [i], menuItemObj, this));
            }
            
  /*          // makeMenu: "null" is used as a separator.
            mBar.add(MenuPanel.makeMenu(" 신청 ",
            new Object[] { "신청승인 처리", "직접입과 처리", "직접취소 처리", "입과 FileToDB", null, "신청명단 조회", "취소명단 조회", "신청인원 조회" },
            this) );
            
            // makeMenu: Using MenuShortcuts and CheckboxMenuItems, and a submenu.
            // Note: I don't think MenuShortcuts work through the applet model.
            // You'll need to use KeyListeners to catch those.
            // CheckboxMenuItems use the ItemListener interface, not ActionListioner.
            mBar.add(MenuPanel.makeMenu(" 입과 ",
            new Object[] { "입과명단 조회", "입과인원 조회", null, "클래스 관리", "클래스등록 FileToDB" },
            this) );
            
            // makeMenu: A short and simple use of makeMenu.  The 3rd parameter
            // "this", is the object responsible for receiving the menu events.
            // It got the makeMenu function from a Java 1.1 book I have.
            mBar.add(MenuPanel.makeMenu(" 학습 ",
            new Object[] { "개인별 학습현황", "클래스별 학습현황", "성적별 학습현황", "회사별 학습현황", "교육그룹별 학습현황", "학습시간 조회" },
            this) );*/
            
            // Initialize the MenuPanel object and give it this MenuBar...
            mPanel = new MenuPanel();
            mPanel.setMenuBar(mBar);
            
            taStatus = new TextArea("Choose a menu to fire a command.");
            taStatus.setFont(new Font("Courier", Font.PLAIN, 11));
            setLayout(new BorderLayout());
            setBackground(SystemColor.menu);
            
            // NOTE: The MenuPanel doesn't paint right (or not at all) if added
            // directly to the Applet's Layout manager. So I create a Panel, put my
            // components in it, then add the Panel to the Applet.  This works.
            Panel appPanel = new Panel();
            appPanel.setLayout(new BorderLayout());
            //	appPanel.add("South", taStatus);
            appPanel.add("Center", mPanel);
            
            add("Center", appPanel);
            validate();
        }


    // Handle the ActionListener.actionPerformed event.
    // Just print out the Menu's Label each time a Menu is chosen.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof MenuItem) {
            MenuItem mi = (MenuItem)e.getSource();
            MenuItem mp = (MenuItem)mi.getParent();
            
            for(int i = 0; i < menu_cnt; i++) {          
                for(int j = 0; j < menuItem_cnt [i]; j++) {      
                    if(mi.getLabel().equals(menuItem [i][j])) {
                        JSObject win = JSObject.getWindow(this);
                        String [] args = new String [1];
                        args [0] = menuPath [i][j];                        
                        win.call("servletCall", args);                        
                    }        
                }
            }
        }
    }

	// NOTE: The applet does not catch the events generated by CheckboxMenuItems
	// in the Edit->Options menu. Implement an "ItemListener" if you need to
	// perform immediate action for those menus. It's not always necessary to
	// catch CheckboxMenuItems as they are commonly used as toggle switches that
	// are checked during other events.
}
