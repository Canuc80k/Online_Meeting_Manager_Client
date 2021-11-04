package user_interface;

import java.awt.*;
import java.awt.event.*;

public class Tray_Icon {
	private static String TRAY_ICON_FILE_PATH = "src/main/resources/img/a.png";
	
	public static void create_tray_icon() {
		if (!SystemTray.isSupported()) {
	        System.out.println("SystemTray is not supported");
	        return;
	    }
	    
	    Image tray_image = null;
	    try {tray_image = Toolkit.getDefaultToolkit().getImage(TRAY_ICON_FILE_PATH);}
	    catch (Exception e) {e.printStackTrace();}

	    final PopupMenu popup = new PopupMenu();
	    final TrayIcon trayIcon = new TrayIcon(tray_image, "MY PROGRAM NAME", popup);
	    final SystemTray tray = SystemTray.getSystemTray();

	    trayIcon.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	try {
					if (Main_interface.frame == null) Main_interface.create_new_window();
					else Main_interface.frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
	    });
	    
	    
	    MenuItem exitItem = new MenuItem("Exit");
	    exitItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            System.exit(1);
	        }
	    });
	    popup.add(exitItem);

	    trayIcon.setPopupMenu(popup);

	    try {
	        tray.add(trayIcon);
	    } catch (Exception e) {
	        System.out.println("TrayIcon could not be added.");
	    }
	}
}
