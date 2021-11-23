package user_interface;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tray_Icon {
	private static String TRAY_ICON_FILE_PATH = "src/main/resources/img/a.png";
	
	public static TrayIcon trayIcon;
	public static void create_tray_icon() {
		if (!SystemTray.isSupported()) {
	        System.out.println("SystemTray is not supported");
	        return;
	    }
	    
	    Image tray_image = null;
	    try {tray_image = Toolkit.getDefaultToolkit().getImage(TRAY_ICON_FILE_PATH);}
	    catch (Exception e) {e.printStackTrace();}

	    final PopupMenu popup = new PopupMenu();
	    final SystemTray tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(tray_image, "Online Meeting Manager", popup);

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
