package edu.ktu.ds.lab3.maceina;

import edu.ktu.ds.lab3.gui.ValidationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class ComputerGenerator {

    private static final String ID_CODE = "TA";      //  ***** Nauja
    private static int serNr = 10000;               //  ***** Nauja

    private Computer[] computers;
    private String[] keys;

    private int currentComputerIndex = 0, currentComputerIdIndex = 0;

    public static Computer[] generateShuffleComputers(int size) {
        Computer[] computers = IntStream.range(0, size)
                .mapToObj(i -> new Computer.Builder().buildRandom())
                .toArray(Computer[]::new);
        Collections.shuffle(Arrays.asList(computers));
        return computers;
    }

    public static String[] generateShuffleIds(int size) {
        String[] keys = IntStream.range(0, size)
                .mapToObj(i -> ID_CODE + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(keys));
        return keys;
    }

    public Computer[] generateShuffleComputersAndIds(int setSize,
            int setTakeSize) throws ValidationException {

        if (setTakeSize > setSize) {
            setTakeSize = setSize;
        }
        computers = generateShuffleComputers(setSize);
        keys = generateShuffleIds(setSize);
        this.currentComputerIndex = setTakeSize;
        return Arrays.copyOf(computers, setTakeSize);
    }

    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public Computer getComputer() {
        if (computers == null) {
            throw new ValidationException("computersNotGenerated");
        }
        if (currentComputerIndex < computers.length) {
            return computers[currentComputerIndex++];
        } else {
            throw new ValidationException("allSetStoredToMap");
        }
    }

    public String getComputerId() {
        if (keys == null) {
            throw new ValidationException("computersIdsNotGenerated");
        }
        if (currentComputerIdIndex < keys.length) {
            return keys[currentComputerIdIndex++];
        } else {
            throw new ValidationException("allKeysStoredToMap");
        }
    }
}
