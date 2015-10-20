package fr.alma.middleware.loki.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorGenerator {
	
	public static Color random() {
		Random rand = new Random();
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		return new Color(r,g,b);
	}
	
	public static List<Color> rainbow(int nbColor) {
		List<Color> res = new ArrayList<Color>(nbColor);
		double frequency = (2.0d*3.141592d)/(new Integer(nbColor)).doubleValue();
		for (int i=0 ; i<nbColor ; i++) {
			double red   = Math.sin(frequency*i + 0) * 127 + 128;
			double green = Math.sin(frequency*i + 2) * 127 + 128;
			double blue  = Math.sin(frequency*i + 4) * 127 + 128;
			
			res.add(
				new Color(
					new Double(red).intValue(),
					new Double(green).intValue(),
					new Double(blue).intValue()
				)
			);
		}
		return res;
	}
}
