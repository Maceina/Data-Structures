/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Maceina;

import edu.ktu.ds.lab2.gui.ValidationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;
/**
 *
 * @author maceina
 */
public class ComputerGenerator {
    
    private int startIndex = 0, lastIndex = 0;
    private boolean isStart = true;

    private Computer[] computers;

    public Computer[] generateShuffle(int setSize,
                                 double shuffleCoef) throws ValidationException {

        return generateShuffle(setSize, setSize, shuffleCoef);
    }

    /**
     * @param setSize
     * @param setTake
     * @param shuffleCoef
     * @return Gražinamas aibesImtis ilgio masyvas
     * @throws ValidationException
     */
    public Computer[] generateShuffle(int setSize, int setTake, double shuffleCoef) throws ValidationException {

        Computer[] computer = IntStream.range(0, setSize)
                .mapToObj(i -> new Computer.Builder().buildRandom())
                .toArray(Computer[]::new);
        return shuffle(computer, setTake, shuffleCoef);
    }

    public Computer takeCar() throws ValidationException {
        if (lastIndex < startIndex) {
            throw new ValidationException(String.valueOf(lastIndex - startIndex), 4);
        }
        // Vieną kartą Automobilis imamas iš masyvo pradžios, kitą kartą - iš galo.
        isStart = !isStart;
        return computers[isStart ? startIndex++ : lastIndex--];
    }

    private Computer[] shuffle(Computer[] computer, int amountToReturn, double shuffleCoef) throws ValidationException {
        if (computer == null) {
            throw new IllegalArgumentException("Kompiuteriu nėra (null)");
        }
        if (amountToReturn <= 0) {
            throw new ValidationException(String.valueOf(amountToReturn), 1);
        }
        if (computer.length < amountToReturn) {
            throw new ValidationException(computer.length + " >= " + amountToReturn, 2);
        }
        if ((shuffleCoef < 0) || (shuffleCoef > 1)) {
            throw new ValidationException(String.valueOf(shuffleCoef), 3);
        }

        int amountToLeave = computer.length - amountToReturn;
        int startIndex = (int) (amountToLeave * shuffleCoef / 2);

        Computer[] takeToReturn = Arrays.copyOfRange(computer, startIndex, startIndex + amountToReturn);
        Computer[] takeToLeave = Stream
                .concat(Arrays.stream(Arrays.copyOfRange(computer, 0, startIndex)),
                        Arrays.stream(Arrays.copyOfRange(computer, startIndex + amountToReturn, computer.length)))
                .toArray(Computer[]::new);

        Collections.shuffle(Arrays.asList(takeToReturn)
                .subList(0, (int) (takeToReturn.length * shuffleCoef)));
        Collections.shuffle(Arrays.asList(takeToLeave)
                .subList(0, (int) (takeToLeave.length * shuffleCoef)));

        this.startIndex = 0;
        this.lastIndex = takeToLeave.length - 1;
        this.computers = takeToLeave;
        return takeToReturn;
    }
}
