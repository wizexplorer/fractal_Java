package Mandelbrot;

public class ComplexNumber {
    // the real part of a complex number
    double real;
    // the imaginary part of a complex number
    double imaginary;

    // ------------ Constructors: -------------
    // initializes real and imaginary to zero
    ComplexNumber() {
        this.real = 0;
        this.imaginary = 0;
    }

    // initializes the private variables real and imaginary to r and i respectively
    ComplexNumber(double r, double i) {
        this.real = r;
        this.imaginary = i;
    }

    // ------------- Methods: --------------
    // For all the examples below assume c1 and c2 are complex numbers that have
    // already been declared and initialized

    // Example of how to use this method: c1.addTo (c2) ;
    // The above line (example) updates c1 with the result of adding c1 (the caller)
    // with c2

    void addTo(ComplexNumber c) {
        this.real += c.real;
        this.imaginary += c.imaginary;
    }

    // Example of how to use this method: c1.subFrom (c2) ;
    // The above line (example) updates c1 with the result of subtracting c2 from c1
    void subFrom(ComplexNumber c) {
        this.real = c.real - this.real;
        this.imaginary = c.imaginary - this.imaginary;
    }

    // Example of how to use this method: c1.multBy (c2) ;
    // The above line (example) updates c1 with the result of multiplying c1 with c2
    void multBy(ComplexNumber c) {
        double temp = Double.valueOf(real); // store val of real immutably before it gets changed
        this.real = this.real * c.real - this.imaginary * c.imaginary;
        this.imaginary = temp * c.imaginary + c.real * this.imaginary;
    }

    // Example of how to use this method: c1.squareThisCN() ;
    // The above line (example) updates c1 with the result of squaring c1
    void squareThisCN() {
        ComplexNumber c = new ComplexNumber();
        c.real = this.real;
        c.imaginary = this.imaginary;
        multBy(c);
    }

    // Example of how to use this method: ComplexNumber myCN = c1.add(c2);
    // The above line (example) initializes the complex number myCN with the result
    // of adding c1 with c2
    // Note: this method does not change c1 (the caller) in any way (as opposed to
    // addTo).
    ComplexNumber add(ComplexNumber c) {
        ComplexNumber n = new ComplexNumber();
        n.real = this.real + c.real;
        n.imaginary = this.imaginary + c.imaginary;
        return n;
    }

    // Example of how to use this method: ComplexNumber myCN = c1.sub(c2);
    // The above line (example) initializes the complex number myCN with the result
    // of subtracting c2 from c1
    // Note: this method does not change c1 (the caller) in any way (as opposed to
    // subFrom).
    ComplexNumber sub(ComplexNumber c) {
        ComplexNumber n = new ComplexNumber();
        n.real = c.real - this.real;
        n.imaginary = c.imaginary - this.imaginary;
        return n;
    }

    // Example of how to use this method: ComplexNumber myCN = c1.mult(c2);
    // The above line (example) initializes the complex number myCN with the result
    // of multiplying c1 with c2
    // Note: this method does not change c1 (the caller) in any way (as opposed to
    // multBy).
    ComplexNumber mult(ComplexNumber c) {
        ComplexNumber n = new ComplexNumber();
        n.real = this.real * c.real - this.imaginary * c.imaginary;
        n.imaginary = this.real * c.imaginary + c.real * this.imaginary;
        return n;
    }

    // Example of how to use this method: ComplexNumber myCN = c1.square();
    // The above line (example) initializes the complex number myCN with the result
    // of squaring c1
    // Note: this method does not change c1 (the caller) in any way (as opposed to
    // squareThisCN).
    ComplexNumber square() {
        ComplexNumber c = new ComplexNumber();
        c.real = this.real;
        c.imaginary = this.imaginary;
        // c.squareThisCN();
        // return c;
        return c.mult(c);
    }

    // Example of how to use this method: if ( c1.equals(c2) ) { ... } else ...
    // The above line uses the method equals as a condition for an if statement.
    // This method returns true if c1 equals c2, otherwise this method returns false
    // Note: this method does not change c1 (the caller) in any way.
    boolean equals(ComplexNumber c) {
        return this.real == c.real && this.imaginary == c.imaginary;
    }

    // Example of how to use this method: double absValue = c1.modulus();
    // The above line uses the method modulus to calculate the absolute (or modulus)
    // value of the complex number c1 .
    // Note: this method does not change c1 (the caller) in any way.
    double modulus() {
        double r = Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
        return r;
    }

    // Example of how to use this method: double absValue = c1.modSquare();
    // The above line uses the method modSquare to calculate square of the absolute
    // (or modulus) value of the complex number c1.
    // Note: this method does not change c1 (the caller) in any way.
    double modSquare() {
        double mod = this.modulus();
        return Math.pow(mod, 2);
    }

    // This method prints the complex number to the screen. Below is an example of
    // how you can use this method :
    // ComplexNumber cn = new ComplexNumber (2, 3);
    // cn.display();
    // The above example should display the following to the console:
    // 2 + 3i
    void display() {
        String positiveOrNegative = this.imaginary > 0 ? " +" : " ";
        System.out.println(this.real + positiveOrNegative + this.imaginary + "i");
    }

    private boolean isEven(int n) {
        return n % 2 == 0;
    }

    // This method returns the val of i^n if imaginary number is 1
    void iToThePow(int n) {
        // ComplexNumber c = new ComplexNumber();
        int quotient = n / 2;
        int remainder = n % 2;
        if (isEven(quotient)) {
            if (remainder == 0) {
                System.out.println("i ^ " + n + " : 1");
            } else {
                System.out.println("i ^ " + n + " : i");
            }
        } else {
            if (remainder == 0) {
                System.out.println("i ^ " + n + " : -1");
            } else {
                System.out.println("i ^ " + n + " : -i");
            }
        }
    }

    // Convert mandelbrot X to Java X
    int mandelbrotToJavaCoordX(int javaOriginX, double mandelbrotX, float scale) {
        int javaX = (int) (javaOriginX + mandelbrotX * scale);
        return javaX;
    }

    // Convert mandelbrot Y to Java Y
    int mandelbrotToJavaCoordY(int javaOriginY, double mandelbrotY, float scale) {
        int javaY = (int) (javaOriginY + mandelbrotY * scale);
        return javaY;
    }

    // Convert Java X to mandelbrot X
    void javaCoordToMandelbrotX(int javaOriginX, int javaX, float scale) {
        // ComplexNumber num = new ComplexNumber();
        // num.real = (javaX - javaOriginX) / scale;
        // return num;
        this.real = (javaX - javaOriginX) / scale;
    }

    // Convert Java Y to mandelbrot Y
    void javaCoordToMandelbrotY(int javaOriginY, int javaY, float scale) {
        // ComplexNumber num = new ComplexNumber();
        // num.imaginary = (javaY - javaOriginY) / scale;
        // return num;
        this.imaginary = (javaY - javaOriginY) / scale;
    }

    // int inMandelbrotSetRange(ComplexNumber c, ComplexNumber constant, int count) {
    //     if (count >= 425)
    //         return count;
    //     c.squareThisCN();
    //     c.addTo(constant);
    //     double mod = c.modulus();
    //     if (mod >= 2) {
    //         // System.out.println(count);
    //         // c.display();
    //         return count;
    //     }
    //     count++;
    //     int result = inMandelbrotSetRange(c, constant, count);
    //     return result;
    // }

    // int inMandelbrotSetRange(ComplexNumber constant) {
    //     ComplexNumber c = new ComplexNumber();
    //     int result = inMandelbrotSetRange(c, constant, 1);
    //     return result;
    // }

    // boolean inMandelbrotSet(ComplexNumber c, ComplexNumber constant, int count) {
    //     if (count > 15)
    //         return true;
    //     c.squareThisCN();
    //     c.addTo(constant);
    //     double mod = c.modulus();
    //     if (mod >= 2) {
    //         // System.out.println(count);
    //         // c.display();
    //         return false;
    //     }
    //     count++;
    //     boolean result = inMandelbrotSet(c, constant, count);
    //     return result;
    // }

    // boolean inMandelbrotSet(ComplexNumber constant) {
    //     ComplexNumber c = new ComplexNumber();
    //     boolean result = inMandelbrotSet(c, constant, 1);
    //     return result;
    // }

    public static void main(String[] args) {

        // ComplexNumber a = new ComplexNumber(-2, 0);
        // int jX = a.mandelbrotToJavaCoordX(400, a.real, 200);
        // System.out.println(jX);

        // ComplexNumber c = new ComplexNumber(0, 0.7);
        // boolean r = c.inMandelbrotSet(c);
        // int r = c.inMandelbrotSetRange(c);
        // System.out.println(r);

        // Testing using Worksheet:

        // ComplexNumber a = new ComplexNumber();
        // System.out.println("\n1. ");
        // a.iToThePow(5);

        // System.out.println("\n2. ");
        // a.iToThePow(32);

        // System.out.println("\n3. ");
        // a.iToThePow(102);

        // ComplexNumber d = new ComplexNumber(2, 3);
        // ComplexNumber d2 = new ComplexNumber(-5, 6);
        // ComplexNumber dAns = d.add(d2);
        // d.addTo(d2);
        // System.out.println("\n4. ");
        // dAns.display();
        // d.display();

        // ComplexNumber e = new ComplexNumber(-6, -8);
        // ComplexNumber e2 = new ComplexNumber(9, 4);
        // ComplexNumber eAns = e2.sub(e);
        // e2.subFrom(e);
        // System.out.println("\n5. ");
        // eAns.display();
        // e2.display();

        // ComplexNumber f = new ComplexNumber(4, 2);
        // ComplexNumber f2 = new ComplexNumber(6, -12);
        // ComplexNumber fAns = f.mult(f2);
        // f.multBy(f2);
        // System.out.println("\n6. ");
        // fAns.display();
        // f.display();

        // ComplexNumber g = new ComplexNumber(7, -3);
        // ComplexNumber g2 = new ComplexNumber(5, -10);
        // ComplexNumber gAns = g.mult(g2);
        // g.multBy(g2);
        // System.out.println("\n7. ");
        // gAns.display();
        // g.display();

        // ComplexNumber h = new ComplexNumber(-5, 3);
        // ComplexNumber hAns = h.square();
        // h.squareThisCN();
        // System.out.println("\n8. ");
        // hAns.display();
        // h.display();

        // ComplexNumber i = new ComplexNumber(8, -9);
        // double iAns = i.modulus();
        // System.out.println("\n9. ");
        // System.out.println(iAns);

        // ComplexNumber j = new ComplexNumber(3, 4);
        // double jAns = j.modSquare();
        // System.out.println("\n10. ");
        // System.out.println(jAns);
    }
}
