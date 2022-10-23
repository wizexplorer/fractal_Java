package Mandelbrot;

// Mandelbrot GUI
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GUI {
	int length = 650;
	// 31px = min ht of toolbar
	// int height = 850;
	// int height = Math.round(length*3/4);
	int height = length;
	// -9 px on both sides of len, they are for resizing
	int startX;
	int startY;
	int endX;
	int endY;
	boolean mouseDown;
	MandelbrotDatabase database = new MandelbrotDatabase(length, height);
	MandelbrotJPanel mbrotPanel = new MandelbrotJPanel(database.DB);

	public int[] getLenHt() {
		int[] lengthHeight = { length, height };
		return lengthHeight;
	}

	private JFrame frmMandelbrotFractal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmMandelbrotFractal.setVisible(true);
					System.out.println("--------------------- INSTRUCTIONS -------------------");
					System.out.println("Use arrow keys to move");
					System.out.println("Press \"Ctrl + =\" to increase max iterations");
					System.out.println("Press \"Ctrl + -\" to decrease max iterations");
					System.out.println("Click and Drag mouse to zoom into a particular section");
					System.out.println("Use \"Esc\" to go back to last frame");
					System.out.println("Press \"=\" to directly return to the first frame");
					System.out.println("          ----- E N J O Y   :)");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		frmMandelbrotFractal.getContentPane().add(mbrotPanel, BorderLayout.CENTER);

		frmMandelbrotFractal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				ArrayList<ArrayList<ComplexNumber>> list;
				switch (keyCode) {
					case KeyEvent.VK_EQUALS:
						if (e.isControlDown())
							break;
						mbrotPanel.translateX = 0;
						mbrotPanel.translateY = 0;
						mbrotPanel.MAX_ITERATIONS = 200;
						database.removeAllLayers();
						list = database.generateHeadDB();
						mbrotPanel.zoom = list.get(0).get(0).sub(list.get(0).get(1)).modulus();
						mbrotPanel.data = list;
						mbrotPanel.repaint();
						break;
					case KeyEvent.VK_UP:
						mbrotPanel.translateY = 0;
						mbrotPanel.translateX = 0;
						mbrotPanel.translateY -= 50 * mbrotPanel.zoom;
						mbrotPanel.repaint();
						break;
					case KeyEvent.VK_DOWN:
						mbrotPanel.translateY = 0;
						mbrotPanel.translateX = 0;
						mbrotPanel.translateY += 50 * mbrotPanel.zoom;
						mbrotPanel.repaint();
						break;
					case KeyEvent.VK_LEFT:
						mbrotPanel.translateX = 0;
						mbrotPanel.translateY = 0;
						mbrotPanel.translateX -= 50 * mbrotPanel.zoom;
						mbrotPanel.repaint();
						break;
					case KeyEvent.VK_RIGHT:
						mbrotPanel.translateX = 0;
						mbrotPanel.translateY = 0;
						mbrotPanel.translateX += 50 * mbrotPanel.zoom;
						mbrotPanel.repaint();
						break;
					case KeyEvent.VK_ESCAPE:
						if (database.layers == 1)
							return;
						database.removeLastLayer();
						list = database.getLastArr();
						mbrotPanel.zoom = list.get(0).get(0).sub(list.get(0).get(1)).modulus();
						mbrotPanel.data = list;
						mbrotPanel.repaint();
						break;
				}
				if (e.isControlDown() && (keyCode == KeyEvent.VK_EQUALS)) {
					mbrotPanel.MAX_ITERATIONS += 100;
					mbrotPanel.translateX = 0;
					mbrotPanel.translateY = 0;
					System.out.println("Max Iterations : " + mbrotPanel.MAX_ITERATIONS);
					mbrotPanel.repaint();
				} else if (e.isControlDown() && (keyCode == KeyEvent.VK_MINUS) && mbrotPanel.MAX_ITERATIONS > 100) {
					mbrotPanel.MAX_ITERATIONS -= 100;
					mbrotPanel.translateX = 0;
					mbrotPanel.translateY = 0;
					System.out.println("Max Iterations : " + mbrotPanel.MAX_ITERATIONS);
					mbrotPanel.repaint();
				}
			}
		});

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMandelbrotFractal = new JFrame();
		frmMandelbrotFractal.setTitle("Mandelbrot Fractal");
		frmMandelbrotFractal.setResizable(false);
		frmMandelbrotFractal.setGlassPane(new JComponent() {
			// Color selectionColor = new Color(180, 213, 254, 150);
			Color selectionColor = new Color(190, 10, 57, 170);

			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(selectionColor);
				g.fillRect(startX - 9, startY - 31, endX - startX, endY - startY);
			}
		});
		Container glassPane = (Container) frmMandelbrotFractal.getGlassPane();
		glassPane.setVisible(false);
		frmMandelbrotFractal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mbrotPanel.translateX = 0;
				mbrotPanel.translateY = 0;
				startX = e.getX();
				startY = e.getY();
				// System.out.println("MousePressed " + startX + " " + startY);
				glassPane.setVisible(true);
				mouseDown = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				glassPane.setVisible(false);
				mouseDown = false;
				// System.out.println(startX + " " + startY + " " + endX + " " + endY);
				// ZOOM
				// ComplexNumber translateX = new ComplexNumber(mbrotPanel.translateX, 0);
				// ComplexNumber translateY = new ComplexNumber(0, mbrotPanel.translateY);
				// ComplexNumber start = mbrotPanel.data.get(startX).get(startY);
				// start.display();
				// mbrotPanel.data.get(endX).get(endY).display();
				// int i = 4;
				int startCenteredZoomY = startY - 31 < 0 ? startY : startY - 31;
				int startCenteredZoomX = startX - 9 < 0 ? startX : startX - 9;
				int endCenteredZoomY = endY - 31;
				int endCenteredZoomX = endX - 9;
				ArrayList<ArrayList<ComplexNumber>> list = database.createNewDatabase(
						mbrotPanel.data.get(startCenteredZoomX).get(startCenteredZoomY),
						mbrotPanel.data.get(endCenteredZoomX).get(endCenteredZoomY));
				// ArrayList<ArrayList<ComplexNumber>> list = database.createNewDatabase(
				// translateX.add(mbrotPanel.data.get(startCenteredZoomX).get(startCenteredZoomY)),
				// translateY.add(mbrotPanel.data.get(endCenteredZoomX).get(endCenteredZoomY)));
				double mod = list.get(0).get(0).sub(list.get(0).get(1)).modulus();
				mbrotPanel.zoom = mod;
				// while ()
				// System.out.println(Double.toString(mod));
				String modStr = Double.toString(mod);
				int modInt = 0;
				for (int idx = modStr.length() - 1; idx >= 0; idx--) {
					if (modStr.charAt(idx) == '-') {
						modInt = Integer.parseInt(modStr.substring(idx + 1));
					}
				}
				if (modInt >= 14) {
					System.out.println("MAX ZOOM REACHED");
					System.out.println("Use \"Esc\" to go back");
					return;
				}
				mbrotPanel.data = list;
				mbrotPanel.repaint();
				startX = 0;
				startY = 0;
				endX = 0;
				endY = 0;
			}

		});
		frmMandelbrotFractal.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				endX = e.getX();
				endY = e.getY();
				// For forcing square (same shape as our window) shape :-
				// int difX = endX - startX;
				// int difY = endY - startY;
				// if (difX > difY) {
				// 	endX = startX + difY;
				// } else if (difY > difX) {
				// 	endY = startY + difX;
				// }
				glassPane.repaint();
			}

		});
		frmMandelbrotFractal.setBounds(280, 10, length, height);
		frmMandelbrotFractal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}