package aurora.editor.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class FontManager {

	public static Font getFont(String fontType) {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, FontManager.class.getResourceAsStream("/fonts/"+fontType));
			font = font.deriveFont(12f);
		}catch(FontFormatException | IOException e) {
			System.out.println("Failed to load font");
		}
		return font;
	}

	public static Font getFont(String fontName, int size){
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, FontManager.class.getResourceAsStream("/fonts/"+fontName));
			font = font.deriveFont(Float.valueOf(size));
		}catch(FontFormatException | IOException e) {
			System.out.println("Failed to load font");
		}
		return font;
	}
}
