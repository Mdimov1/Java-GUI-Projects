package edu.fmi;

public class Ration implements VisualInt{
    private int numerator;
    private int denominator;

    private boolean isValid(int denominatorCandidate) {

        return (denominatorCandidate != 0);
    }

    public Ration(int numerator, int denominator) throws Exception {
        this.numerator = numerator;
        if (isValid(denominator)) {
            this.denominator = denominator;
        } else throw new Exception("Invalid value");
    }

    public Ration() {
        this.numerator = 0;
        this.denominator = 1;
    }

    public Ration(Ration r) {
        this.numerator = r.numerator;
        this.denominator = r.denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    private void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    private void setDenominator(int denominator) {
        if (isValid(denominator)) {
            this.denominator = denominator;
        }
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    //Normal value
    public Ration ordinaryType() throws Exception {
        int c = gcd(numerator, denominator);
        int a = numerator /= c;
        int b = denominator /= c;
        return new Ration(a, b);

    }

    public static Ration sum(Ration r1, Ration r2) throws Exception {
        int sumNumerators;
        int sumDenominators;

        if (r1.getDenominator() == r2.getDenominator()) {
            sumNumerators = r1.numerator + r2.numerator;
            sumDenominators = r1.denominator;
        } else {
            sumNumerators = r1.numerator * r2.denominator + r2.numerator * r1.denominator;
            sumDenominators = r1.denominator * r2.denominator;
        }

        Ration r = new Ration(sumNumerators, sumDenominators);
        return r;
    }

    public static Ration diff(Ration r1, Ration r2) throws Exception {
        int diffNumerators;
        int diffDenominators;

        if (r1.getDenominator() == r2.getDenominator()) {
            diffNumerators = r1.numerator - r2.numerator;
            diffDenominators = r1.getDenominator();
        } else {
            diffNumerators = r1.numerator * r2.denominator - r2.numerator * r1.denominator;
            diffDenominators = r1.denominator * r2.denominator;
        }

        Ration r = new Ration(diffNumerators, diffDenominators);
        return r;

    }

    public static Ration multiply(Ration r1, Ration r2) throws Exception {

        int multiplyNumerators = r1.numerator * r2.numerator;
        int multiplyDenominators = r1.denominator * r2.denominator;
        Ration r = new Ration(multiplyNumerators, multiplyDenominators);
        return r;
    }

    public static Ration division(Ration r1, Ration r2) throws Exception {
        int divNumerators = r1.numerator * r2.denominator;
        int divDenominators = r1.denominator * r2.numerator;
        Ration r = new Ration(divNumerators, divDenominators);
        return r;
    }

    public static Ration recprocally(Ration r) throws Exception {
        return new Ration(r.denominator, r.numerator);
    }

    @Override
    public String toString() {
        return numerator +
                "/" + denominator;
    }

    @Override
    public int printInt() {
        if(getNumerator() == getDenominator()){
            return 1;
        }else if(getDenominator() == 1){
            return getNumerator();
        }else{
            //else numerator = 0
            return 0;
        }
    }
}
