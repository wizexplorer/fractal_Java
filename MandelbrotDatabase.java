package Mandelbrot;

// Mandelbrot database

import java.util.ArrayList;

public class MandelbrotDatabase {

	MandelbrotDatabase head;
	MandelbrotDatabase prev;
	MandelbrotDatabase next;
	int layers;
	ArrayList<ArrayList<ComplexNumber>> DB;
	int frameLen;
	int frameHt;

	MandelbrotDatabase(int len, int ht) {
		this.head = this;
		this.next = null;
		this.prev = null;
		layers = 0;
		this.frameLen = len;
		this.frameHt = ht;
		this.DB = generateHeadDB();
	}

	private MandelbrotDatabase(ArrayList<ArrayList<ComplexNumber>> data, int len, int ht) {
		this.head = null;
		this.next = null;
		this.prev = null;
		layers++;
		this.frameLen = len;
		this.frameHt = ht;
		this.DB = data;
	}

	ArrayList<ArrayList<ComplexNumber>> createNewDatabase(ComplexNumber min, ComplexNumber max) {
		// System.out.println(diffX + " " + diffY);
		double incrementFactorX;
		double incrementFactorY;

		// create 2d arraylist containing complexNum
		ArrayList<ArrayList<ComplexNumber>> db = new ArrayList<>();
		// Next, we'll initialize each element of ArrayList with another ArrayList each containing data for its specific row:

		// IF SELECTION SHAPE IS VARIABLE (I.E.) NOT FIXED :-
		double diffX = max.real - min.real;
		double diffY = max.imaginary - min.imaginary;
		double diffMinMax;
		if (diffX > diffY) {
			incrementFactorX = (max.real - min.real) / (frameLen);
			incrementFactorY = incrementFactorX;
			diffMinMax = (diffX - diffY);

			for (double real = min.real; real < max.real; real += incrementFactorX) {
				ArrayList<ComplexNumber> temp = new ArrayList<>();
				for (double imaginary = min.imaginary - diffMinMax; imaginary < max.imaginary
						+ diffMinMax; imaginary += incrementFactorY) {
					temp.add(new ComplexNumber(real, imaginary));
				}
				db.add(temp);
			}
		} else {
			incrementFactorY = (max.imaginary - min.imaginary) / frameHt;
			incrementFactorX = incrementFactorY;
			diffMinMax = (diffY - diffX) / 2;

			for (double real = min.real - diffMinMax; real < max.real + diffMinMax; real += incrementFactorX) {
				ArrayList<ComplexNumber> temp = new ArrayList<>();
				for (double imaginary = min.imaginary; imaginary < max.imaginary; imaginary += incrementFactorY) {
					temp.add(new ComplexNumber(real, imaginary));
				}
				db.add(temp);
			}
		}

		// OR IF YOU ARE FORCING FIXED(SQUARE) SHAPE :-
		// incrementFactorX = (max.real - min.real) / (frameLen);
		// incrementFactorY = incrementFactorX;

		// for (double real = min.real; real < max.real; real += incrementFactorX) {
		// ArrayList<ComplexNumber> temp = new ArrayList<>();
		// for (double imaginary = min.imaginary; imaginary < max.imaginary; imaginary
		// += incrementFactorY) {
		// temp.add(new ComplexNumber(real, imaginary));
		// }
		// db.add(temp);
		// }

		MandelbrotDatabase newDB = new MandelbrotDatabase(db, frameLen, frameHt);
		MandelbrotDatabase lastElem = getDBByLayer(layers);
		lastElem.next = newDB;
		newDB.prev = lastElem;
		newDB.head = lastElem.head;
		lastElem.head.layers++;
		return db;
	}

	ArrayList<ArrayList<ComplexNumber>> generateHeadDB() {
		if (this.layers > 1) {
			System.out.println("Cannot generate head with existing layers");
			return null;
		} else {
			return createNewDatabase(new ComplexNumber(-1.9, -1.2), new ComplexNumber(0.6, 1.3));
		}
	}

	ArrayList<ArrayList<ComplexNumber>> addOrSubFromAll(boolean addOrSub, ComplexNumber c, ArrayList<ArrayList<ComplexNumber>> data ) {
		ArrayList<ArrayList<ComplexNumber>> db = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			ArrayList<ComplexNumber> temp = new ArrayList<>();
			for (int j = 0; j < data.get(i).size(); j++) {
				if (addOrSub) {
					temp.add(c.add(data.get(i).get(j)));
				} else {
					temp.add(c.sub(data.get(i).get(j)));
				}
			}
			db.add(temp);
		}
		return db;
	}

	void display(int level) {
		if (level == 0) {
			System.out.println("level must be greater than 0");
			return;
		}
		MandelbrotDatabase elem = this.head;
		int size = head.layers;
		int idx = 1;
		while (idx <= size) {
			if (idx == layers) {
				ArrayList<ArrayList<ComplexNumber>> db = elem.DB;

				for (int i = 0; i < db.size(); i++) {
					for (int j = 0; j < db.get(i).size(); j++) {
						// db[i][j].display();
						db.get(i).get(j).display();
					}
				}
			}
			elem = elem.next;
			idx++;
		}
	}

	MandelbrotDatabase getDBByLayer(int level) {
		if (level == 0) {
			// System.out.println("level must be greater than 0");
			return this;
		}
		MandelbrotDatabase elem = this.head;
		int size = head.layers;
		int idx = 1;
		while (idx <= size) {
			if (idx == layers) {
				return elem;

			}
			elem = elem.next;
			idx++;
		}
		return null;
	}

	ArrayList<ArrayList<ComplexNumber>> getLastArr() {
		return getDBByLayer(this.layers).DB;
	}

	void removeLastLayer() {
		removeLayer(this.layers);
	}

	void removeAllLayers() {
		while (this.layers > 1) {
			removeLastLayer();
		}
	}

	void removeLayer(int level) {
		if (level == 0) {
			System.out.println("level must be greater than 0");
			return;
		}
		MandelbrotDatabase elem = this.head;
		int size = head.layers;
		int idx = 1;
		while (idx <= size) {
			if (idx == layers) {
				MandelbrotDatabase prev = elem.prev;
				prev.next = null;
				elem.prev = null;
				this.layers--;
			}
			elem = elem.next;
			idx++;
		}
	}

	// public static void main(String[] args) {
	//
	// MandelbrotDatabase db = new MandelbrotDatabase(5, 5);
	//// db.display(1);
	// System.out.println("1 " + db.DB.size());
	// System.out.println("2 " + db.DB.get(0).size());
	// db.createNewDatabase(new ComplexNumber(0, 0), new ComplexNumber(1, 1));
	// ArrayList<ArrayList<ComplexNumber>> arr = db.getLastArr();
	//// System.out.println(db.layers);
	// for (int i = 0; i < arr.size(); i++) {
	// for (int j = 0; j < arr.get(i).size(); j++) {
	// // db[i][j].display();
	// arr.get(i).get(j).display();
	// }
	// }
	// System.out.println("3 " + db.getLastArr().size());
	// System.out.println("4 " + db.getLastArr().get(0).size());
	// db.createNewDatabase(new ComplexNumber(1.2, 1.3), new ComplexNumber(1.3,
	// 1.4));
	// ArrayList<ArrayList<ComplexNumber>> arr2 = db.getLastArr();
	//// System.out.println(db.layers);
	// for (int i = 0; i < arr2.size(); i++) {
	// for (int j = 0; j < arr2.get(i).size(); j++) {
	// // db[i][j].display();
	// arr2.get(i).get(j).display();
	// }
	// }
	// System.out.println("5 " + db.getLastArr().size());
	// System.out.println("6 " + db.getLastArr().get(0).size());
	//// db.createNewDatabase(new ComplexNumber(0, 0), new ComplexNumber(2, 1));
	//// System.out.println(db.getLastArr().get(0).size());
	// }

}
