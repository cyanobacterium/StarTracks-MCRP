/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontgen;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;import java.util.Set;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Cybergnome
 */
public class App {

	static final String[] fontList = {"Xolonium", "sans-serif", "Arial", "Liberation Sans", "Microsoft JhengHei", "Microsoft YaHei" };
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		final Charset ascii = Charset.forName("US-ASCII");
		final Charset latin = Charset.forName("ISO-8859-1");
		final Charset utf8 = Charset.forName("UTF-8");
		
		final Set<Character> slightShift = new HashSet();
		slightShift.addAll(Arrays.asList('[','{','(','>','\\','o','m','z','x','S'));
		
		char[] asciiPage = new char[16*16];
		char[] asciiSgaPage = new char[16*16];
		try {
			Reader in = new InputStreamReader(new FileInputStream("ascii.txt"), utf8);
			in.read(asciiPage);
		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Reference file ascii.txt not found", ex);
		}
		try {
			Reader in = new InputStreamReader(new FileInputStream("sga.txt"), utf8);
			int size = in.read(asciiSgaPage);
			int so = size;
			while (size < asciiSgaPage.length){
				asciiSgaPage[size] = asciiSgaPage[size % so];
				size++;
			}
		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Reference file ascii.txt not found", ex);
		}
		//shuffle(asciiSgaPage);
		
		int gridSize = 8*6;
		int fontSize = (int)(gridSize * (72.0/90.0));
		int glyphOffset = (int)(-0.25*gridSize);
		int gridCount = 16;
		int imgSize = gridSize * gridCount;
		
		BufferedImage bimg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D painter = bimg.createGraphics();
		painter.setColor(Color.white);
		
		//painter.drawString(fontName, 0, imgSize + glyphOffset);
		int i = 0;
		for(int y = 0; y < gridCount; y++){
			for(int x = 0; x < gridCount; x++){
				char c = asciiPage[i];
				System.out.print("'");
				Font f = getFont(c,fontSize);
				if(!Character.isWhitespace(c)){
					painter.setFont(f);
					int dx = 2;
					if(slightShift.contains(c)) dx += 1;
					painter.drawString(String.valueOf(c), 
							x * gridSize + dx, 
							(y+1) * gridSize + glyphOffset);
					System.out.print(c);
				} else {
					System.out.print(' ');
				}
				System.out.print("', ");
				//
				i++;
			}
			System.out.print('\n');
		}
		
		showImage(bimg);
		writeImage(bimg, "ascii.png");i = 0;
		
		
		bimg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		painter = bimg.createGraphics();
		painter.setColor(Color.white);
		for(int y = 0; y < gridCount; y++){
			for(int x = 0; x < gridCount; x++){
				char c = asciiSgaPage[i];
				System.out.print("'");
				Font f = getFont(c,fontSize);
				if(!Character.isWhitespace(c)){
					painter.setFont(f);
					int dx = 2;
					if(slightShift.contains(c)) dx += 1;
					painter.drawString(String.valueOf(c), 
							x * gridSize + dx, 
							(y+1) * gridSize + glyphOffset);
					System.out.print(c);
				} else {
					System.out.print(' ');
				}
				System.out.print("', ");
				//
				i++;
			}
			System.out.print('\n');
		}
		
		showImage(bimg);
		writeImage(bimg, "ascii_sga.png");
		
		
		for(int prefixByte = 0; prefixByte <= 0xff; prefixByte += 1){
			bimg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
			painter = bimg.createGraphics();
			painter.setColor(Color.white);
			i = 0;
			for(int y = 0; y < gridCount; y++){
				for(int x = 0; x < gridCount; x++){
					char c = (char)((prefixByte << 8 ) | (y << 4) | (x));
					System.out.print("'");
					Font f = getFont(c,fontSize);
					if(!Character.isWhitespace(c)){
						painter.setFont(f);
						int dx = 2;
						if(slightShift.contains(c)) dx += 1;
						painter.drawString(String.valueOf(c), 
								x * gridSize + dx, 
								(y+1) * gridSize + glyphOffset);
						System.out.print(c);
					} else {
						System.out.print(' ');
					}
					System.out.print("', ");
					//
					i++;
				}
				System.out.print('\n');
			}
			//showImage(bimg);
			String hexStr = Integer.toHexString(prefixByte);
			if(prefixByte < 0x10) hexStr = "0"+hexStr;
			writeImage(bimg, String.format("unicode_page_%s.png",hexStr));
		}
		
		System.exit(0);
	}

	private static void showImage(BufferedImage bimg) {
		JLabel display = new JLabel(new ImageIcon(bimg));
		JPanel displayPane = new JPanel();
		displayPane.setLayout(new FlowLayout());
		displayPane.setBackground(Color.black);
		displayPane.add(display);
		JOptionPane.showMessageDialog(null, displayPane);
	}

	private static void writeImage(BufferedImage bimg, String file) {
		try {
			ImageIO.write(bimg, "png", new File(file));
			System.out.println(file);
		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "DAM!", ex);
		}
	}

	private static final Random prng = new Random(-123456789);
	private static void shuffle(char[] carr) {
		for(int i = carr.length - 1; i > 0; i--){
			int j = prng.nextInt(i+1);
			char temp = carr[i];
			carr[i] = carr[j];
			carr[j] = temp;
		}
	}

	
	private static Font[] fontCache = null;
	
	private static Font getFont(char c, int size) {
		if (fontCache == null) {
			fontCache = new Font[fontList.length];
			for (int i = 0; i < fontList.length; i++) {
				fontCache[i] = new Font(fontList[i], Font.PLAIN, size);
			}
		}
		for (int i = 0; i < fontList.length; i++) {
			Font font = fontCache[i];
			if(font.canDisplay(c)){
				return font;
			}
		}
		return fontCache[0];
	}

}
