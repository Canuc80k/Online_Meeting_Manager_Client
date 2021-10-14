package init;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.io.File;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Font_init {
	public static Font SanFranciscoText_Medium;
	public static Font SanFranciscoDisplay_Medium;
	public static Font SanFranciscoText_Bold;
	
	public static void setUIFont(FontUIResource f) {
		Enumeration<?> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				FontUIResource orig = (FontUIResource) value;
				Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
				UIManager.put(key, new FontUIResource(font));
			}
		}
	}

	public Font_init() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			SanFranciscoText_Medium = Font.createFont(Font.TRUETYPE_FONT, new File("font/San Francisco/SanFranciscoText-Medium.otf")).deriveFont(15f);
			ge.registerFont(SanFranciscoText_Medium);
			
			SanFranciscoDisplay_Medium = Font.createFont(Font.TRUETYPE_FONT, new File("font/San Francisco/SanFranciscoDisplay-Medium.otf")).deriveFont(12f);
			ge.registerFont(SanFranciscoDisplay_Medium);
			
			SanFranciscoText_Bold = Font.createFont(Font.TRUETYPE_FONT, new File("font/San Francisco/SanFranciscoText-Bold.otf")).deriveFont(20f);
			ge.registerFont(SanFranciscoText_Bold);
		} catch (Exception e) {}

		try {
			System.setProperty("flatlaf.menuBarEmbedded", "false");
			UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatDraculaContrastIJTheme");
			UIManager.put("Button.arc", 10);
			UIManager.put("Component.arc", 20);
			UIManager.put("ProgressBar.arc", 20);
			UIManager.put("TextComponent.arc", 20);
			UIManager.put("Button.foreground", Color.white);
			UIManager.put("ScrollBar.thumbArc", 100);
			UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
			UIManager.getFont(SanFranciscoDisplay_Medium);
		} catch (Exception e) {}
	}
}
