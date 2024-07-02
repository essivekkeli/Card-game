package com.example.harjoitustyo;

import java.util.Random;

public class Korttipakka extends Graafinen{
    private Kortti[] pakka;
    private int currentKortti;
    private int KORTTIEN_MAARA = 52;
    private Random rnd = new Random(System.currentTimeMillis());

    public Korttipakka(){

        int kuvat[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

        String maat[] = {"Hertta", "Ruutu", "Risti", "Pata"};

        pakka = new Kortti[KORTTIEN_MAARA];

        //kortit pakkaan
        for(int count = 0; count < pakka.length; count ++){
            pakka[count] = new Kortti(kuvat[count % 13], maat[count/13]);
        }
    }

    public Kortti nostaKortti(){
        if(currentKortti < pakka.length){
            return pakka[rnd.nextInt(52)];
        }
        else{
            return null;
        }
    }
}
