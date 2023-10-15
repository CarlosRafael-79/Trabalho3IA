/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoia;

import java.util.Random;

/**
 *
 * @author Carlos Rafael
 */
public class Perceptron {
    
    public Random rand = new Random();
    
    public Integer[][] entradas = new Integer[16][6];
    public Float[] pesos = new Float[5];
    public float vies = 0;
    public int limite = 1;
    
    
    
    
    public Perceptron(){
        for(int i = 0; i < 5; i++){
            pesos[i] = rand.nextFloat(0.0f,1.0f);
            System.out.println(pesos[i]);
        }
    }
    
    public void initializeData() {
        for(int i = 0; i < 16; i++) {
            Integer val = entradas[i][5] =rand.nextInt(0, 1);
            switch(val) {
                case 0 -> {
                    entradas[i][0] = 0;
                    entradas[i][1] = rand.nextInt(32, 41);
                    entradas[i][2] = rand.nextInt(174, 186);
                    entradas[i][3] = rand.nextInt(108, 119);
                    entradas[i][4] = rand.nextInt(0, 5);
                }

                case 1 -> {
                    entradas[i][0] = 0;
                    entradas[i][1] = rand.nextInt(8, 13);
                    entradas[i][2] = rand.nextInt(82, 98);
                    entradas[i][3] = rand.nextInt(36, 45);
                    entradas[i][4] = rand.nextInt(3, 8);
                }

            }
        }
    }
    
//    public boolean funcao(float valor){
//        boolean resultado = false;
//        if(valor > limite){
//            resultado = true;
//        }
//        return resultado;
//    }
//    
    public float novoPeso(float antigo, float tamanhoPasso, float erro, int entrada){
        float novo = antigo + tamanhoPasso*erro*entrada;
        return novo;
    }
    
    
    public int previsao(int sexo, int idade, int altura, int peso, int cabelo, int classe, int posicao){
        float y = sexo*pesos[0] + idade*pesos[1] + altura*pesos[2] + peso*pesos[3] + cabelo*pesos[4];
        int yResultado = y >limite ? 1 : 0;
        if(yResultado != classe){
            for(int i = 0; i < 5; i++){
                pesos[i] = novoPeso(pesos[i], 0.1f, classe - y, entradas[posicao][i]);
                
            }
            vies = novoPeso(vies, 0.1f, classe - y, -1);
            
        }
        return yResultado;
    }
    
    public void treinar(int epocas){
       for(int j = 0; j < epocas; j++){
           int acertos = 0;
           System.out.println("Época: "+j);
           for(int i = 0; i < 12; i++){
                int previsto = previsao(entradas[i][0],entradas[i][1],entradas[i][2],entradas[i][3],entradas[i][4],entradas[i][5], i);
                System.out.println("Previsto: "+previsto+" Real: "+ entradas[i][5]);
                if(previsto == entradas[i][5]){
                    acertos++;
                }
           }
           System.out.println("Acertos: "+ acertos+ " Total: "+12);
           System.out.println("------------------");
       }
        
    }
    
    
    
    
}
