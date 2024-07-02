package com.example.harjoitustyo;

public class Kortti extends Graafinen {
    private int kuvakortti;
    private String maa;

    public Kortti(int korttiKuva, String korttiMaa) {
        kuvakortti = korttiKuva;
        maa = korttiMaa;
    }

    public int getKuvakortti() {
        return kuvakortti;
    }

    public void setKuvakortti(int kuvakortti) {
        this.kuvakortti = kuvakortti;
    }

    public String getMaa() {
        return maa;
    }

    public void setMaa(String maa) {
        this.maa = maa;
    }

    public boolean Tarkista(boolean Isompi) {
        if (Isompi == false && this.getKuvakortti() < 6) {
            return true;
        } else if (Isompi == false && this.getKuvakortti() >= 6) {
            return false;
        } else if (Isompi == true && this.getKuvakortti() < 6) {
            return false;
        } else if (Isompi == true && this.getKuvakortti() >= 6) {
            return true;
        }
        return false;
    }

    public String toString(){
        return maa + " " + kuvakortti;
    }
}
