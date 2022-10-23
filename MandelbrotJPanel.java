package Mandelbrot;

// Mandelbrot JPanel

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class MandelbrotJPanel extends JPanel {

	int MAX_ITERATIONS = 200;
	double translateX = 0;
	double translateY = 0;
	double zoom;
	ArrayList<ArrayList<ComplexNumber>> data;
	Color[] colorUF = {
			new Color(66, 30, 15),
			new Color(25, 7, 26),
			new Color(9, 1, 47),
			new Color(4, 4, 73),
			new Color(0, 7, 100),
			new Color(12, 44, 138),
			new Color(24, 82, 177),
			new Color(57, 125, 209),
			new Color(134, 181, 229),
			new Color(211, 236, 248),
			new Color(241, 233, 191),
			new Color(248, 201, 95),
			new Color(255, 170, 0),
			new Color(204, 128, 0),
			new Color(153, 87, 0),
			new Color(106, 52, 3),
	};

	public MandelbrotJPanel(ArrayList<ArrayList<ComplexNumber>> db) {
		this.data = db;
		this.zoom = db.get(0).get(0).sub(db.get(0).get(1)).modulus();
		// this.translateY -= 15 * this.zoom;
	}

	int inMandelbrotSetRange(ComplexNumber c, ComplexNumber constant, int count) {
		if (count >= MAX_ITERATIONS)
			return count;
		c.squareThisCN();
		c.addTo(constant);
		double mod = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		if (mod > 4) {
			return count;
		}
		count++;
		int result = inMandelbrotSetRange(c, constant, count);
		return result;
	}

	int inMandelbrotSetRange(ComplexNumber constant) {
		ComplexNumber c = new ComplexNumber();
		int result = inMandelbrotSetRange(c, constant, 1);
		return result;
	}

	boolean inMandelbrotSet(ComplexNumber c, ComplexNumber constant, int count) {
		if (count >= MAX_ITERATIONS)
			return true;
		c.squareThisCN();
		c.addTo(constant);
		double mod = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		if (mod > 4) {
			return false;
		}
		count++;
		boolean result = inMandelbrotSet(c, constant, count);
		return result;
	}

	boolean inMandelbrotSet(ComplexNumber constant) {
		ComplexNumber c = new ComplexNumber();
		boolean result = inMandelbrotSet(c, constant, 1);
		return result;
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g;

		GUI gui = new GUI();
		int[] lengthHeight = gui.getLenHt(); // get length and height from GUI class
		int len = lengthHeight[0]; // - 40 to show extra space after the end points
		int ht = lengthHeight[1]; // - 36 is the len of toolbar at the top of the JPanel
		ComplexNumber c = new ComplexNumber(translateX, translateY); // -ve real means fractal moves right, i.e p.o.v
																		// moves left -ve imaginary means fractal moves
																		// up i.e p.o.v moves down
		// ComplexNumber c = new ComplexNumber(0,0);
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < ht; j++) {
				ComplexNumber complexNum = c.sub(data.get(i).get(j));
				data.get(i).set(j, complexNum);

				int count = inMandelbrotSetRange(complexNum);
				// ----- DOMINATING PINK ----
				// if (count >=425) {
				// gg.setPaint(new Color(0, 0, 0));
				// } else {
				// // // dividing makes color brighter
				// gg.setPaint(new Color((count*10)>255?255:count*10, Math.round(count/500),
				// ((count*10) > 255 ? 255: count*10)));
				// }

				// ----- HOPEFUL PINK ----
				// if (count >=425) {
				// gg.setPaint(new Color(255, 255, 255));
				// } else {
				// gg.setPaint(new Color(count*5>255?255:count*5, Math.round(count/1000),
				// ((count*5) > 255 ? 255: count*5)));
				// }

				// ------ BRIGHT RED ---
				// int red = (count * 3) > 255 ? 255 : (count * 3);
				// int blue = count > 255 ? 255 : count;
				// int green = (int) ((count * 0.9) > 255 ? 255 : (count * 0.9));

				//// ------ PINK CYAN AURA ---
				// int green = (count*4) > 255 ? 255 : 255 - (count*4);
				// int blue = (int) ((count*6) > 255 ? 255 : 255 - (count*6));
				// int red = (int) ((count / 3) > 255 ? 255 : 255 - (count / 3));

				// // ------ GREEN AURA ---
				// int blue = (count*3) > 255 ? 255 : (count*3);
				// int green = count > 255 ? 255 : count;
				// int red = (int) ((count * 0.6) > 255 ? 255 : (count * 0.6));

				// // ------ MAJASTIC BLUE ---
				// int green = (count*3) > 255 ? 255 : (count*3);
				// int blue = count > 255 ? 255 : count;
				// int red = (int) ((count * 0.9) > 255 ? 255 : (count * 0.9));

				// gg.setPaint(new Color(red, green, blue));

				if (count < MAX_ITERATIONS && count > 0) {
					int idx = count % 16;
					gg.setColor(colorUF[idx]);
				} else {
					gg.setColor(Color.BLACK);
				}
				gg.fillRect(i, j, 1, 1);

			}
		}
	}
}
