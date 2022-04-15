/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas1ga;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author dala_
 */
public class Tugas1GA {
    
    //ClearScreen
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    
    static Random rand = new Random();
    //----------------------Inisialisasi Populasi-------------------
    static int[][] CreatePop(int ukuranPopulasi){
        int[][] populasi = new int[ukuranPopulasi][10]; //Panjang gen saya isi 10
        
        for (int i = 0; i < ukuranPopulasi; i++) {
            for (int j = 0; j < 10; j++) {
                populasi[i][j] = rand.nextInt(10);//random 0-9
            }
        }
        return populasi;            
    }
    //--------------------------------------------------------------
    
  
    //--------------------------DEKODE-----------------------------
    static double[] dekode(int[] subGen){
        double fungsi[] = new double[2];
        
        double p1 = (6/(9*(Math.pow(10,-1) + Math.pow(10,-2) + Math.pow(10,-3))));
        fungsi[0] = -3 + p1*(subGen[0]*Math.pow(10,-1) + subGen[1]*Math.pow(10,-2) + subGen[2]*Math.pow(10,-3)) ;
        
        double p2 = (4/(9*(Math.pow(10,-1) + Math.pow(10,-2) + Math.pow(10,-3))));
        fungsi[1] = -2 + p2*(subGen[3]*Math.pow(10,-1) + subGen[4]*Math.pow(10,-2) + subGen[5]*Math.pow(10,-3)) ;
        
        return fungsi;
    }
    //--------------------------------------------------------------
    
    
    //--------------------------------------------------------------
    static double object(double[] xy){
        double x = xy[0];
        double y = xy[1];
        double objektif = Math.pow(x+2*y-7, 2) + Math.pow(1*x+y-5, 2);
        return objektif;
    }
    //--------------------------------------------------------------
    
    
    //--------------------------------------------------------------
    static double fit(double object){
        return 1/(object+0.01);
    }
    //--------------------------------------------------------------
    
    
    //----------------------HITUNG FIT------------------------------ 
    static double[] hitungFitness(int[][] populasi){
        double[] fitness = new double[populasi.length];
        for (int i = 0; i < fitness.length; i++) {
            fitness[i] = fit(object(dekode(populasi[i])));       
        }
        return fitness;
        
    }
    //--------------------------------------------------------------

    
    //-----------------------SELEKSI----------------------------
    static int[][] Selection(int[][] pop, double[] fitness){
        double[] fitness_data = fitness.clone();
        int[][] parent = new int[2][10]; //Karena Jumlah Parentnya 2 dan panjang gen 10
        int index = 0;
        double max1 = 0;
        double max2 = 0;
            //Mencari Parent 1
            for (int i=0; i<fitness.length; i++){
                if(fitness[i]>max1){
                    max1 = fitness[i];
                    index = i;
                    //Memasukan Gen terbaik menjadi parent1
                    for (int y=0; y<10;y++){
                        parent[0][y] = pop[i][y]; 
                    }
                }   
            }
        
            //Mencari Parent 2
            fitness_data[index] = 0;
            for (int i=0; i<fitness.length; i++){
                if(fitness_data[i]>max2){
                    max2 = fitness_data[i];
                    //Memasukan Gen terbaik menjadi parent1
                    for (int y=0; y<10;y++){
                        parent[1][y] = pop[i][y]; 
                    }
                }   
            }
        return parent;
    }
    //--------------------------------------------------------------
    
    
    //-----------------------CROSSOVER-----------------------------
    static int[][] crossover(int[][] parent){
        double jum = 10/2; //Panjang gen = 10
        double crosPoint = Math.floor(jum);
        int[][] child = new int[2][10];
        child = parent.clone();
        
            int p1 = 9; //Panjang gen = 10
            for (int i = 0; i < jum; i++) {
                child[0][i] = parent[1][p1];
                p1 = p1-1;
            }
            
            int p2 = 9; //Panjang gen = 10
            for (int i = 0; i < jum; i++) {
                child[1][i] = parent[0][p2];
                p2 = p2-1;
            }
        return child;
    }
    //--------------------------------------------------------------
    
    
    //-----------------------MUTASI---------------------------------
    static int[][] mutasi (int[][] child, double lm){
        for (int i = 0; i < child.length; i++) {
                double rd = rand.nextDouble();
                if (rd < lm){
                    for (int j = 0; j < 10; j++) {
                        child[i][j] = rand.nextInt(6);
                    }
                    
                }
            }
        return child;
    }
    //--------------------------------------------------------------

    
    //----------------------------REGENERASI------------------------
    static int[][] Regenerasi(int[][] pop, double[] fitness, int ukuranPopulasi){
        
        double[] fitness_data = fitness.clone();
        int[][] popBaru = new int[ukuranPopulasi][10]; //Karena Jumlah Parentnya 2 dan panjang gen 10
        popBaru = pop.clone();
        int index = 0;
        int index2 = 0;
        double min1 = fitness[0];
        double min2 = fitness[0];
            //Mencari min1
            for (int i=0; i<fitness.length; i++){
                if(fitness[i]<min1){
                    min1 = fitness[i];
                    index = i;
                    //Memasukan Gen terbaik menjadi parent1
                    for (int y=0; y<10;y++){
                        popBaru[index][y] = pop[i][y]; 
                    }
                }   
            }
        
            //Mencari min2
            fitness_data[index] = 101;
            for (int i=0; i<fitness.length; i++){
                if(fitness_data[i]<min2){
                    min2 = fitness_data[i];
                    index2 = i;
                    //Memasukan Gen terbaik menjadi parent1
                    for (int y=0; y<10;y++){
                        popBaru[index][y] = pop[i][y]; 
                    }
                }   
            }
        return popBaru;
    }
    //--------------------------------------------------------------
    
    
    
    public static void main(String[] args) throws IOException {
        int[][] populasi;
        int ukuranPopulasi = 10; //Banyak Populasi saya isi 10
        double[] fitness;
        double lj_mutasi = 0.8;
       
        
        //Membuat Populasi & hitung Fitness
        int Generasi = 0;
        boolean isLooping = true;
        
            while (isLooping == true){
                System.out.println("Generasi ke : "+Generasi);
                populasi = CreatePop(ukuranPopulasi);
                fitness = hitungFitness(populasi);
                //Proses Selection
                int[][] selectionn = Selection(populasi,fitness);
                //Proses Crossover
                int[][] crossOver = crossover(selectionn);
                //Proses Mutasi
                int[][] mutann;
                mutann = mutasi(crossOver, lj_mutasi);
                double[] fit_mutan;
                fit_mutan = hitungFitness(mutann);
                //Print PopBaru
                int[][] popBaru ;
                populasi = Regenerasi(populasi, fitness, ukuranPopulasi);
                for (int i = 0; i < ukuranPopulasi; i++) {
                    int no = i+1;
                    System.out.println("    Populasi "+no);
                    System.out.print("      GEN   :");
                        for (int j = 0; j < 10; j++) {
                            System.out.print(populasi[i][j]+" "); 
                        }
                    System.out.println("");
                    System.out.println("        Fitness :"+fitness[i]);
                    System.out.println("");    
                }
                clearScreen();

                for (int i = 0; i< ukuranPopulasi; i++){
                    if (fitness[i] == 0){
                        isLooping = false;
                    }else{
                        isLooping = true;
                    }
                }
                Generasi++;
            }
            
    }
    
}
