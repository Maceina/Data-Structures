/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Maceina;

import edu.ktu.ds.lab2.utils.Ks;
import edu.ktu.ds.lab2.utils.Parsable;

import java.time.LocalDate;
import java.util.*;
/**
 *
 * @author maceina
 */
public class Computer implements Parsable<Computer> {
    final static private int minYear = 2000;
    private static final int currentYear = LocalDate.now().getYear();

    final static private double minPrice = 10;
    final static private double maxPrice = 210_000;

    private static final String idCode = "TA";
    private static int serNr = 100;

    private final String RegNr; // change

    // kiekvieno Kompiuterio individualūs duomenys
    private String type;
    private String brand;
    private String model;
    private int year;
    private double price;

    public Computer() {
        RegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
    }

    public Computer(String type, String brand, String model, int year, double price) {
        RegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr

        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;

        validate();
    }

    public Computer(String dataString) {
        RegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr

        this.parse(dataString);

        validate();
    }

    public Computer(String dataString, boolean RandomReg) {
        if (RandomReg) {
            RegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        } else {
            Scanner scanner = new Scanner(dataString);
            RegNr = scanner.next();
        }
        this.parseAlternative(dataString, RandomReg);

        validate();
    }

    public Computer(Builder builder) {
        RegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr

        this.type = builder.type;
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
        this.price = builder.price;

        validate();
    }

    public Computer create(String dataString) {
        return new Computer(dataString);
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
    public final void parse(String data) {
        try {   // duomenys, atskirti tarpais
            Scanner scanner = new Scanner(data);
            type = scanner.next();
            brand = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            setPrice(scanner.nextDouble());

        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų -> " + data);
        }
    }

    public final void parseAlternative(String data, boolean random) {
        try {   // duomenys, atskirti tarpais
            Scanner scanner = new Scanner(data);
            if(!random)scanner.next();
            type = scanner.next();
            brand = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            setPrice(scanner.nextDouble());

        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų -> " + data);
        }
    }

    @Override
    public String toString() {  // papildyta su carRegNr
        return RegNr + "=" + type + "_" + brand + ":" + model + " " + year + " " + String.format("%4.1f", price);
    }

    public String ToString_data1() {  // papildyta su carRegNr
        return RegNr + " " + type + " " + brand + " " + model + " " + year + " " + String.format("%4.1f", price);
    }
    public String ToString_data2() {  // papildyta su carRegNr
        return type + " " + brand + " " + model + " " + year + " " + String.format("%4.1f", price);
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
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

    // keisti bus galima tik kainą - kiti parametrai pastovūs
    public void setPrice(double price) {
        this.price = price;
    }

    public String getRegNr() {  //** nauja.
        return RegNr;
    }

    @Override
    public int compareTo(Computer computer) {
        return getRegNr().compareTo(computer.getRegNr());
    }

    // pradžioje pagal markes, o po to pagal modelius
    public static Comparator<Computer> byBrand
            = (Computer c1, Computer c2) -> c1.brand.compareTo(c2.brand);

    public static Comparator<Computer> byPrice = (Computer c1, Computer c2) -> {
        // didėjanti tvarka, pradedant nuo mažiausios
        if (c1.price < c2.price) {
            return -1;
        }
        if (c1.price > c2.price) {
            return +1;
        }
        return 0;
    };

    // metai mažėjančia tvarka, esant vienodiems lyginama kaina
    public static Comparator<Computer> byYearPrice
            = (Computer c1, Computer c2) -> {
                if (c1.year > c2.year) {
                    return +1;
                }
                if (c1.year < c2.year) {
                    return -1;
                }
                if (c1.price > c2.price) {
                    return +1;
                }
                if (c1.price < c2.price) {
                    return -1;
                }
                return 0;
            };

    // Car klases objektų gamintojas (builder'is)
    public static class Builder {

        private final static Random RANDOM = new Random(2017);  // Atsitiktinių generatorius
        private final static String[][] MODELS = { // galimų type/brand/model masyvas
            {"Desktop", "Nvd_555", "gtx_770", "rtx_2070_super", "rtx_2080", "gtx_1080TI"},
            {"Desktop", "AGD_55", "rx570", "rx470", "rx580", "Vega_64"},
            {"Laptop", "Int_211", "i9-9900k", "i7-9700k", "i5-6600k"},
            {"Laptop", "ADB_555", "Ryzen_3900x", "Ryzen_3700x", "ryzen_3600"}
        };

        private String type = "";
        private String brand = "";
        private String model = "";
        private int year = -1;
        private double price = -1.0;

        public Computer build() {
            return new Computer(this);
        }

        public Computer buildRandom() {
            int tIndex = RANDOM.nextInt(MODELS.length);        // markės indeksas  0..
            int mIndex = RANDOM.nextInt(MODELS[tIndex].length - 2) + 2;// modelio indeksas 1..
            return new Computer(
                    MODELS[tIndex][0],
                    MODELS[tIndex][1],
                    MODELS[tIndex][mIndex],
                    2000 + RANDOM.nextInt(20), // metai tarp 1999 ir 2019
                    //                    2019,
                    10 + RANDOM.nextDouble() * 200_0); // kaina tarp 10 ir 201_0
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
