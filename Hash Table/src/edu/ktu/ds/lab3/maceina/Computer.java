package edu.ktu.ds.lab3.maceina;

import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.Parsable;

import java.time.LocalDate;
import java.util.*;

/**
 * @author maceina
 */
public final class Computer implements Parsable<Computer> {

    // Bendri duomenys visiems automobiliams (visai klasei)
    private static final int minYear = 1990;
    private static final int currentYear = LocalDate.now().getYear();
    private static final double minPrice = 100.0;
    private static final double maxPrice = 333000.0;

    private String maker = "";
    private String model = "";
    private int year = -1;
    private double price = -1.0;

    public Computer() {
    }

    public Computer(String maker, String model, int year, double price) {
        this.maker = maker;
        this.model = model;
        this.year = year;
        this.price = price;
        validate();
    }

    public Computer(String dataString) {
        this.parse(dataString);
        validate();
    }

    public Computer(Builder builder) {
        this.maker = builder.maker;
        this.model = builder.model;
        this.year = builder.year;
        this.price = builder.price;
        validate();
    }

    private void validate() {
        String errorType = "";
        if (year < minYear || year > currentYear) {
            errorType = "Netinkami gamybos metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            errorType += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }

        if (!errorType.isEmpty()) {
            Ks.ern("Kompiuteris yra blogai sugeneruotas: " + errorType);
        }
    }

    @Override
    public void parse(String dataString) {
        try {   // duomenys, atskirti tarpais
            Scanner scanner = new Scanner(dataString);
            maker = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            price = scanner.nextDouble();
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų -> " + dataString);
        }
    }

    @Override
    public String toString() {
        return maker + "_" + model + ":" + year + " "
                + String.format("%4.1f", price);
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.maker);
        hash = 29 * hash + Objects.hashCode(this.model);
        hash = 29 * hash + this.year;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Computer other = (Computer) obj;
        if (this.year != other.year) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.maker, other.maker)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        return true;
    }

    // Computer klases objektų gamintojas
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] MAKERS = { // galimų gamintoju ir modeliu pavadinimu masyvas
            {"Apple", "Mac", "Pac", "Rac",
                "iMac"},
            {"Dell", "xkf5",
                "ggg5", "ttt5", "x1225", "x2255"},
            {"Asus", "xc5", "xp50"},
            {"Acer", "fff5", "fff2", "ff3"},
            {"Hp", "n555", "f10", "d10",
                "g15"},
            {"Lenovo", "s56", "e55", "r8"}
        };

        private String maker = "";
        private String model = "";
        private int year = -1;
        private double price = -1.0;

        public Computer build() {
            return new Computer(this);
        }

        public Computer buildRandom() {
            int ma = RANDOM.nextInt(MAKERS.length);        // modelio indeksas  0..
            int mo = RANDOM.nextInt(MAKERS[ma].length - 1) + 1;// gamintojo indeksas 1..
            return new Computer(MAKERS[ma][0],
                    MAKERS[ma][mo],
                    1990 + RANDOM.nextInt(20),// metai tarp 1990 ir 2009
                    800 + RANDOM.nextDouble() * 88000);// kaina tarp 800 ir 88800
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder maker(String maker) {
            this.maker = maker;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
