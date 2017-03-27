package com.rorkien.opsanta.Graphics;

public class Font  {
	public static String fontChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			"abcdefghijklmnopqrstuvwxyz" +
			"0123456789.,:;!?+-*/=()[]\\" +
			"\'\"@#$%&|░_<>изг ";

	public static String digitsOnly(String text) {
		int length = text.length();
		StringBuffer buffer = new StringBuffer(length);
		for(int i = 0; i < length; i++) {
			char ch = text.charAt(i);
			if (Character.isDigit(ch)) {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	
	public static void write(int i, int xpos, int ypos, Image[][] font) {
		if (i >= 0) {
			int xx = i % 26;
			int yy = i / 26;
			font[xx][yy].draw(xpos, ypos);
		}
	}
		
	public static void write(char c, int xpos, int ypos, Image[][] font) {
			int ch = fontChars.indexOf(c);
			if (ch >= 0) {
				int xx = ch % 26;
				int yy = ch / 26;
				font[xx][yy].draw(xpos, ypos);
			}
	}
	
	public static void subWrite(char c, int xpos, int ypos, Image[][] font, int srccolor, int subcolor) {
		int src[] = { srccolor };
		int sub[] = { subcolor };
		int ch = fontChars.indexOf(c);
		if (ch >= 0) {
			int xx = ch % 26;
			int yy = ch / 26;
			font[xx][yy].subDraw(xpos, ypos, src, sub);
		}
}
	
	public static void write(String s, int xpos, int ypos, Image[][] font) {
		int currentXPos = 0;
		int currentYPos = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\n') {
				currentXPos = 0;
				currentYPos += font[0][0].height * 1.4;
			}
			write(s.charAt(i), xpos + currentXPos * font[0][0].width, ypos + currentYPos, font);
			currentXPos++;
		}
	}
	
	public static void subWrite(String s, int xpos, int ypos, Image[][] font, int srccolor, int subcolor) {
		int currentXPos = 0;
		int currentYPos = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\n') {
				currentXPos = 0;
				currentYPos += font[0][0].height * 1.4;
			}
			subWrite(s.charAt(i), xpos + currentXPos * font[0][0].width, ypos + currentYPos, font, srccolor, subcolor);
			currentXPos++;
		}
	}
	
	public static void outlineWrite(String s, int xpos, int ypos, Image[][] font, int outline, int color, int outcolor) {		
		subWrite(s, xpos - outline, ypos, font, 0xFFFFFF, outcolor);		
		subWrite(s, xpos + outline, ypos, font, 0xFFFFFF, outcolor);		
		subWrite(s, xpos, ypos - outline, font, 0xFFFFFF, outcolor);		
		subWrite(s, xpos, ypos + outline, font, 0xFFFFFF, outcolor);		
		
		subWrite(s, xpos, ypos, font, 0xFFFFFF, color);	
	}
}
