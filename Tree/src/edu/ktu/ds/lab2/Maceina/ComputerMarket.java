/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Maceina;


import edu.ktu.ds.lab2.utils.BstSet;
import edu.ktu.ds.lab2.utils.Set;
/**
 *
 * @author maceina
 */
public class ComputerMarket {
    
    public static Set<String> duplicateComputerBrands(Computer[] computers) {
        
        Set<Computer> uni = new BstSet<>(Computer.byBrand);
        Set<String> duplicates = new BstSet<>();
        
        for (Computer computer : computers) {
            int sizeBefore = uni.size();
            uni.add(computer);

            if (sizeBefore == uni.size()) {
                duplicates.add(computer.getBrand());
            }
        }
        return duplicates;
    }

    public static Set<String> uniqueComputerModels(Computer[] computers) {
        Set<String> uniqueModels = new BstSet<>();
        for (Computer computer : computers) {
            uniqueModels.add(computer.getModel());
        }
        return uniqueModels;
    }
}
