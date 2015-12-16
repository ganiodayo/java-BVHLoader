package fr.mimus.render;

import static org.lwjgl.opengl.GL11.*;

public class Font {
	
	public static String chars = "0123456789"
			+"abcdefghij"
			+"klmnopqrst"
			+"uvwxyz    "
			+"ABCDEFGHIJ"
			+"KLMNOPQRST"
			+"UVWXYZ    "
			+".:/\\?!,;[]"
			+"=+-_><()  "
			+"          ";
		
	public static void drawString(String text, int tx, int ty, int size) {
		drawString(text, tx, ty, size, size);
	}
	public static void drawString(String text, int tx, int ty, int sizeX, int sizeY) {
		float stx = 8f/(float)Texture.font.getWidth();
		float sty = 8f/(float)Texture.font.getHeight();
		Texture.font.bind();	
		glBegin(GL_QUADS);
		char[] ctext = text.toCharArray();
		for(int i=0; i<ctext.length; i++) {
			if(ctext[i] != ' ') {
				int index = chars.indexOf(ctext[i]);
				int xt = index % 10;
				int yt = index / 10;
				glTexCoord2f(xt*stx,yt*sty); 		glVertex2f(tx+i*sizeX,ty);
				glTexCoord2f(xt*stx+stx,yt*sty);	glVertex2f(tx+i*sizeX+sizeX,ty);
				glTexCoord2f(xt*stx+stx,yt*sty+sty);glVertex2f(tx+i*sizeX+sizeX,ty+sizeY);
				glTexCoord2f(xt*stx,yt*sty+sty); 	glVertex2f(tx+i*sizeX,ty+sizeY);
				
			}
		}
		glEnd();
		Texture.unbind();
	}
}
