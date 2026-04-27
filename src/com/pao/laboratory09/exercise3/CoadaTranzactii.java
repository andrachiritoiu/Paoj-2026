package com.pao.laboratory09.exercise3;

import java.util.LinkedList;
import java.util.Queue;

public class CoadaTranzactii {
    private static final int CAPACITATE = 5;
    private final Queue<Tranzactie> coada = new LinkedList<>();

    public synchronized void adauga(Tranzactie tranzactie, int atmId) throws InterruptedException{
        while(coada.size() == CAPACITATE){
            System.out.println("[ATM-" + atmId + "] astept loc...");
            wait();
        }

        coada.add(tranzactie);
        notifyAll();    //trezeste toate threadurile care sunt in wait() pe acelasi obiect
    }

    public synchronized Tranzactie extrage() throws InterruptedException{
        while(coada.isEmpty()){
            wait();
        }

        Tranzactie tranzactie = coada.remove();
        notifyAll();

        return tranzactie;
    }

    public synchronized boolean esteGoala() {
        return coada.isEmpty();
    }
}
