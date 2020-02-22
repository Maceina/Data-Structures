/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Maceina;

import edu.ktu.ds.lab2.gui.ValidationException;
import edu.ktu.ds.lab2.utils.*;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
/**
 *
 * @author maceina
 */
public class SimpleBenchmark {

    public static final String FINISH_COMMAND = "                       ";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab2.gui.messages");

    private static final String[] BENCHMARK_NAMES = {"addBst", "addAvl", "addTree", "addHash", "contAllBst", "contAllAVL", "contAllTree", "contAllHash"};
    private static final int[] COUNTS = {10000, 20000, 40000, 80000};

    private final Timekeeper timeKeeper;
    private final String[] errors;

    private final BstSet<Computer> cSeries = new BstSet<>(Computer.byPrice);
    private final AvlSet<Computer> cSeries2 = new AvlSet<>(Computer.byPrice);
    private final TreeSet<Integer> cSeries3 = new TreeSet<>();
    private final HashSet<Integer> cSeries4 = new HashSet<>();

    /**
     * For console benchmark
     */
    public SimpleBenchmark() {
        timeKeeper = new Timekeeper(COUNTS);
        errors = new String[]{
            MESSAGES.getString("badSetSize"),
            MESSAGES.getString("badInitialData"),
            MESSAGES.getString("badSetSizes"),
            MESSAGES.getString("badShuffleCoef")
        };
    }

    /**
     * For Gui benchmark
     *
     * @param resultsLogger
     * @param semaphore
     */
    public SimpleBenchmark(BlockingQueue<String> resultsLogger, Semaphore semaphore) {
        semaphore.release();
        timeKeeper = new Timekeeper(COUNTS, resultsLogger, semaphore);
        errors = new String[]{
            MESSAGES.getString("badSetSize"),
            MESSAGES.getString("badInitialData"),
            MESSAGES.getString("badSetSizes"),
            MESSAGES.getString("badShuffleCoef")
        };
    }

    public static void main(String[] args) {
        executeTest();
    }

    public static void executeTest() {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Ks.out("Greitaveikos tyrimas:\n");
        new SimpleBenchmark().startBenchmark();
    }

    public void startBenchmark() {
        try {
            benchmark();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void benchmark() throws InterruptedException {
        try {
            for (int k : COUNTS) {
                Computer[] cars = new ComputerGenerator().generateShuffle(k, 1.0);
                final Runtime rt = Runtime.getRuntime();
                int t = 1;

                List<Integer> integersList = new ArrayList<Integer>();
                for (int i = 0; i < k; i++) {
                    integersList.add(i + 1);
                }
                Collections.shuffle(integersList);
                Integer[] integers = integersList.toArray(new Integer[integersList.size()]);

                Computer[] cars1;
                BstSet<Computer> set = new BstSet();
                Arrays.stream(cars).forEach(set::add);

                List<BstSet<Computer>> sets = new ArrayList<BstSet<Computer>>();

                sets.add(set);
                for (int i = 0; i < k / 1000; i++) {
                    sets.add(set);
                    set.pollLast();
                }

                for (int i = 0; i < k / 40; i++) {
                    cars1 = new ComputerGenerator().generateShuffle(20, 1.0);
                    Arrays.stream(cars1).forEach(set::add);
                    sets.add(set);
                    set = new BstSet();
                }
                Collections.shuffle(sets);

                Random rng = new Random(2019);

                
                List<Integer> integersList1 = new ArrayList<Integer>();
                List<TreeSet<Integer>> sets1 = new ArrayList<TreeSet<Integer>>();
                List<HashSet<Integer>> sets2 = new ArrayList<HashSet<Integer>>();
                 TreeSet<Integer> tree1 = new TreeSet<>();
                 HashSet<Integer> tree2 = new HashSet<>();
                

                for (int i = 0; i < k / 40; i++) {
                    for (int s = 0; s < k / 1000; s++) {
                        integersList1.add(rng.nextInt(k + k / 1000));
                    }
                    integersList1.forEach(tree1::add);
                    integersList1.forEach(tree2::add);
                    sets1.add(tree1);
                    sets2.add(tree2);
                }

                cSeries.clear();
                cSeries2.clear();
                cSeries3.clear();
                timeKeeper.startAfterPause();

                timeKeeper.start();

                //---------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                long startSize = rt.totalMemory() - rt.freeMemory();
                Arrays.stream(cars).forEach(cSeries::add);
                timeKeeper.finish(BENCHMARK_NAMES[0]);
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                startSize = rt.totalMemory() - rt.freeMemory();
                Arrays.stream(cars).forEach(cSeries2::add);
                timeKeeper.finish(BENCHMARK_NAMES[1]);
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                startSize = rt.totalMemory() - rt.freeMemory();
                Arrays.stream(integers).forEach(cSeries3::add);
                timeKeeper.finish(BENCHMARK_NAMES[2]);
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                startSize = rt.totalMemory() - rt.freeMemory();
                Arrays.stream(integers).forEach(cSeries4::add);
                timeKeeper.finish(BENCHMARK_NAMES[3]);
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                startSize = rt.totalMemory() - rt.freeMemory();
                sets.forEach((list) -> {
                    cSeries.containsAll(list);
                });
                timeKeeper.finish(BENCHMARK_NAMES[4]);
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                startSize = rt.totalMemory() - rt.freeMemory();
                sets.forEach(cSeries2::containsAll);
                timeKeeper.finish(BENCHMARK_NAMES[5]);
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                startSize = rt.totalMemory() - rt.freeMemory();
                sets1.forEach(cSeries3::containsAll);
                timeKeeper.finish(BENCHMARK_NAMES[6]);
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < 3; i++) {
                    rt.gc();
                }
                startSize = rt.totalMemory() - rt.freeMemory();
                sets2.forEach(cSeries4::containsAll);
                timeKeeper.finish(BENCHMARK_NAMES[7]);
                System.out.println(t + ".     Used memory increased by " + (rt.totalMemory() - rt.freeMemory() - startSize));
                t++;
//-------------------------------------------------------------------------------------------------------------------------------
                System.out.println("");
                timeKeeper.seriesFinish();
            }
            timeKeeper.logResult(FINISH_COMMAND);
        } catch (ValidationException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                timeKeeper.logResult(errors[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                timeKeeper.logResult(MESSAGES.getString("allSetIsPrinted"));
            } else {
                timeKeeper.logResult(e.getMessage());
            }
        }
    }
}