/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabalhoia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Carlos Rafael
 */
public class TrabalhoIA {
    
    static public Random rand = new Random();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<List<Integer>> entradas = new ArrayList<>();
        List<Integer> classes = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            Integer val = rand.nextInt(0, 2);
            classes.add(val);

            switch (val) {
                case 0 -> {
                    entradas.add(List.of(0,
                            rand.nextInt(32, 41), 
                            rand.nextInt(174, 186),
                            rand.nextInt(108, 119),
                            rand.nextInt(0, 5)
                    ));
                }

                case 1 -> {
                    entradas.add(List.of(0,
                            rand.nextInt(8, 13), 
                            rand.nextInt(82, 98),
                            rand.nextInt(36, 45),
                            rand.nextInt(3, 8)
                    ));
            
                }

            }
        }

//        entradas.add(List.of(0, 0));
//        entradas.add(List.of(0, 1));
//        entradas.add(List.of(1, 0));
//        entradas.add(List.of(1, 1));
//        classes.add(0);
//        classes.add(1);
//        classes.add(1);
//        classes.add(1);
        Neuronio perceptron = new Neuronio(entradas, classes);
        perceptron.exbirDataset();
        perceptron.treinar(10);
//        Perceptron algoritmo = new Perceptron();
//        
//        algoritmo.initializeData();
//        algoritmo.treinar(100);

        
    }
    
}
