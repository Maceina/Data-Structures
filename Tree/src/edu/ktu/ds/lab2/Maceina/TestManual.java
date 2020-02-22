/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Maceina;

import edu.ktu.ds.lab2.utils.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
/**
 *
 * @author maceina
 */
public class TestManual {

    static Computer[] computers;
    static ParsableSortedSet<Computer> cSeries = new ParsableBstSet<>(Computer::new, Computer.byPrice);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() throws CloneNotSupportedException {
        Computer c1 = new Computer("Laptop", "appa", "RTX_2070_super", 2019, 599);
        Computer c2 = new Computer.Builder()
                .type("Desktop")
                .brand("Nvidia")
                .model("RTX_2070")
                .year(2018)
                .price(499)
                .build();
        Computer c3 = new Computer.Builder().buildRandom();
        Computer c4 = new Computer("Desktop", "Nvidia", "RTX_2080_super", 2019, 589);
        Computer c5 = new Computer("Laptop", "Nvidia", "RTX_2080", 2018, 539);
        Computer c6 = new Computer("Laptop", "Nvidia", "GTX_1080TI", 2017, 599);
        Computer c7 = new Computer("Laptop AMD vega_56 2017 449");
        Computer c8 = new Computer("Laptop AMD vega_64 2017 599");
        Computer c9 = new Computer("Desktop AMD rx570 2017 399");

        Computer[] ComputerArray = {c9, c7, c8, c5, c1, c6};

        Ks.oun("Kompiuteriu Aibė:");
        ParsableSortedSet<Computer> ComputerSet = new ParsableBstSet<>(Computer::new);

        for (Computer c : ComputerArray) {
            ComputerSet.add(c);
            Ks.oun("Aibė papildoma: " + c + ". Jos dydis: " + ComputerSet.size());
        }
        Ks.oun("");
        Ks.oun(ComputerSet.toVisualizedString(""));
//-------------------------------------------------------------------------------------------------------------
        BstSet<Computer> beginer;
        beginer = new BstSet<>();
        beginer.add(new Computer("Laptop testing rx570 2017 399"));
        beginer.add(new Computer("TA282 GPU testing vega_64 2017 599",false));
        beginer.add(new Computer("TA242 GPU testing vega_64 2017 589",false));
        beginer.add(new Computer("TA222 GPU testing vega_64 2017 579",false));
        ParsableBstSet<Computer> testing;
        testing = (ParsableBstSet<Computer>) ComputerSet;
        //addAll (1 metodas)
        testing.addAll(beginer);

        Ks.oun("addall");
        Ks.oun(ComputerSet.toVisualizedString(""));
        
        ComputerSet.headSet(new Computer("TA222 GPU testing vega_64 2017 579",false));
        
        Ks.oun("headset TA222");
        Ks.oun(ComputerSet.toVisualizedString(""));
        
//-------------------------------------------------------------------------------------------------------------
        ParsableSortedSet<Computer> ComputerSetCopy = (ParsableSortedSet<Computer>) ComputerSet.clone();

        ComputerSetCopy.add(c2);
        ComputerSetCopy.add(c3);
        ComputerSetCopy.add(c4);
        Ks.oun("Papildyta kompiuteriu kopija:");
        Ks.oun(ComputerSetCopy.toVisualizedString(""));

        c9.setPrice(420);

        Ks.oun("Originalas:");
        Ks.ounn(ComputerSet.toVisualizedString(""));

        Ks.oun("Ar elementai egzistuoja aibėje?");
        for (Computer c : ComputerArray) {
            Ks.oun(c + ": " + ComputerSet.contains(c));
        }
        Ks.oun(c2 + ": " + ComputerSet.contains(c2));
        Ks.oun(c3 + ": " + ComputerSet.contains(c3));
        Ks.oun(c4 + ": " + ComputerSet.contains(c4));
        Ks.oun("");

        Ks.oun("Ar elementai egzistuoja aibės kopijoje?");
        for (Computer c : ComputerArray) {
            Ks.oun(c + ": " + ComputerSetCopy.contains(c));
        }
        Ks.oun(c2 + ": " + ComputerSetCopy.contains(c2));
        Ks.oun(c3 + ": " + ComputerSetCopy.contains(c3));
        Ks.oun(c4 + ": " + ComputerSetCopy.contains(c4));
        Ks.oun("");

        Ks.oun("Elementų šalinimas iš kopijos. Aibės dydis prieš šalinimą:  " + ComputerSetCopy.size());
        for (Computer c : new Computer[]{c2, c1, c9, c8, c5, c3, c4, c2, c7, c6, c7, c9}) {
            ComputerSetCopy.remove(c);
            Ks.oun("Iš autoaibės kopijos pašalinama: " + c + ". Jos dydis: " + ComputerSetCopy.size());
        }
        Ks.oun("");

        Ks.oun("Kompiuteriu aibė su iteratoriumi:");
        Ks.oun("");
        for (Computer c : ComputerSet) {
            Ks.oun(c);
        }
        Ks.oun("");
        Ks.oun("Kompiuteriu aibė AVL-medyje:");
        ParsableSortedSet<Computer> ComputersSetAvl = new ParsableAvlSet<>(Computer::new);
        for (Computer c : ComputerArray) {
            ComputersSetAvl.add(c);
        }
        Ks.ounn(ComputersSetAvl.toVisualizedString(""));

        Ks.oun("Kompiuteriu aibė su iteratoriumi:");
        Ks.oun("");
        for (Computer c : ComputersSetAvl) {
            Ks.oun(c);
        }

        Ks.oun("");
        Ks.oun("Kompiuteriu aibė su atvirkštiniu iteratoriumi:");
        Ks.oun("");
        Iterator iter = ComputersSetAvl.descendingIterator();
        while (iter.hasNext()) {
            Ks.oun(iter.next());
        }

        Ks.oun("");
        Ks.oun("Kompiuteriu aibės toString() metodas:");
        Ks.ounn(ComputersSetAvl);

        // Išvalome ir suformuojame aibes skaitydami iš failo
        ComputerSet.clear();
        ComputersSetAvl.clear();

        Ks.oun("");
        Ks.oun("Kompiuteriu aibė DP-medyje:");
        ComputerSet.load("data\\proc1.txt");
        Ks.ounn(ComputerSet.toVisualizedString(""));
        Ks.oun("Išsiaiškinkite, kodėl medis augo tik į vieną pusę.");

        Ks.oun("");
        Ks.oun("Kompiuteriu aibė AVL-medyje:");
        ComputersSetAvl.load("data\\proc1.txt");
        Ks.ounn(ComputersSetAvl.toVisualizedString(""));

        Set<String> carsSet4 = ComputerMarket.duplicateComputerBrands(ComputerArray);
        Ks.oun("Pasikartojančios KOmpiuteriu markės is avl:\n" + carsSet4.toString());

        Set<String> carsSet5 = ComputerMarket.uniqueComputerModels(ComputerArray);
        Ks.oun("Unikalūs Kompiuteriu modeliai is avl:\n" + carsSet5.toString());
    }

    static ParsableSortedSet generateSet(int kiekis, int generN) {
        computers = new Computer[generN];
        for (int i = 0; i < generN; i++) {
            computers[i] = new Computer.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(computers));

        cSeries.clear();
        Arrays.stream(computers).limit(kiekis).forEach(cSeries::add);
        return cSeries;
    }
}