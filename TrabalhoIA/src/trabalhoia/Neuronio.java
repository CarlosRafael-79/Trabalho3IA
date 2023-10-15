/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoia;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Rafael
 */
public class Neuronio {

    public List<List<Integer>> entradas;
    public List<Integer> classe;
    public List<Double> pesos = new ArrayList<>();
    public double vies = 0;
    public double NET = 0;
    public double n = 0.5;

    public Neuronio(List<List<Integer>> entradas, List<Integer> classe) {
        this.entradas = entradas;
        this.classe = classe;
        for (Integer classe1 : classe) {
            pesos.add(0.0);
        }

    }
    
    public void exbirDataset(){
        for(int i = 0; i < entradas.size(); i++){
            for(var entrada : entradas.get(i)){
                System.out.print(entrada+" ");
            }
            System.out.println(classe.get(i));
        }
    }

    int executar(int linha) {

        // Somatório (NET)
        double somatorio = 0;
        for (int i = 0; i < entradas.get(linha).size(); i++) {
            int x = entradas.get(linha).get(i);
            double w = pesos.get(i);
            somatorio += x * w;
            System.out.println("Entrada "+i+": "+x+" * "+w+" = "+(x*w));
        }
        System.out.println("Vies: (-1) * "+vies+" = "+((-1)*vies));
        NET = somatorio + (-1) * vies;
        System.out.println("Somatório = "+NET);
        
        
        // Função de Ativação
        if (NET >= 0) {
            return 1;
        }
        return 0;
    }

    void corrigirPeso(int linha, int saida) {
        
        System.out.println("Corrigir peso: novoPeso = velhoPeso + passo * erro * entrada");
        int valorReal = classe.get(linha);

        for (int i = 0; i < entradas.get(linha).size(); i++) {
            int x = entradas.get(linha).get(i);
            double w = pesos.get(i);

            double novoPeso = w + (n * (valorReal - saida) * x);
            System.out.println("novoPeso "+i+": "+w+" + "+n+" * "+(valorReal - saida)+" * "+x+" = "+novoPeso);
            pesos.set(i, novoPeso);
        }
        System.out.println("novoVies: "+vies+" + "+n+" * "+(valorReal - saida)+" * (-1) = "+(vies + (n * (valorReal - saida) * (-1))));
        System.out.println("-------------------");
        System.out.println("");
        vies = vies + (n * (valorReal - saida) * (-1));
    }

    public void treinar(int epocas) {

        // varável responsável para receber o valor da saída (y)
        int saida;
        float acertos;
        int total = entradas.size();
        for(int j = 0; j < epocas; j++){
            acertos = 0;
            
            
            System.out.println("Época: "+j);
            // laço usado para fazer todas as entradas
            for (int i = 0; i < total; i++) {
                // A saída recebe o resultado da rede que no caso é 1 ou 0
                saida = executar(i);
                System.out.println("REAL: "+classe.get(i)+" PREVISÃO: "+ saida);
                System.out.println("");
                if (saida != classe.get(i)) {
                    // Caso a saída seja diferente do valor esperado

                    // os pesos sinápticos serão corrigidos
                    corrigirPeso(i, saida);

                }else{
                    acertos++;
                }
            }
            if(acertos == total){
                System.out.println("-------100% de acertos-------");
                break;
            }else{
                System.out.println("-------"+(acertos/total*100)+"% de acertos-------");
            }
        }
        
    }

}
