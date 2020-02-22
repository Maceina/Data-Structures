package edu.ktu.ds.lab3.maceina;

import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;

import java.util.Locale;

public class ManualTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() {
        Computer c1 = new Computer("Gam", "d5555", 1997, 1700);
        Computer c2 = new Computer.Builder().buildRandom();
        Computer c3 = new Computer("fam x500 2001 700");
        Computer c4 = new Computer("ibm x1 1969 9500");
        Computer c5 = new Computer("zap z55 2001 80.3");
        Computer c6 = new Computer.Builder().buildRandom();
        Computer c7 = new Computer.Builder().buildRandom();
        
        // Raktų masyvas
        String[] computersIds = {"TA156", "TA102", "TA178", "TA171", "TA105", "TA106", "TA107", "TA108"};
        int id = 0;
        ParsableMap<String, Computer> computersMap
                = new ParsableHashMap<>(String::new, Computer::new, HashType.DIVISION);

        // Reikšmių masyvas
        Computer[] computers = {c1, c2, c3, c4, c5, c6, c7};
        for (Computer c : computers) {
            computersMap.put(computersIds[id++], c);
        }
        computersMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Ar egzistuoja pora atvaizdyje?");
        Ks.oun(computersMap.contains(computersIds[6]));
        Ks.oun(computersMap.contains(computersIds[7]));
        Ks.oun("Pašalinamos poros iš atvaizdžio:");
        Ks.oun(computersMap.remove(computersIds[1]));
        Ks.oun(computersMap.remove(computersIds[7]));
        computersMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paiešką atvaizdyje:");
        Ks.oun(computersMap.get(computersIds[2]));
        Ks.oun(computersMap.get(computersIds[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(computersMap);
    }
}
