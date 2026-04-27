package com.pao.laboratory09.exercise3;

import java.time.LocalDate;

public class ATMThread extends Thread{
    private final int atmId;
    private final CoadaTranzactii coada;

    public ATMThread(int atmId, CoadaTranzactii coada) {
        this.atmId = atmId;
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {

                // id unic pentru fiecare tranzactie
                int id = (atmId - 1) * 4 + i;

                double suma = 100 + id * 10;

                String data = LocalDate.now().toString();

                Tranzactie t = new Tranzactie(id, suma, data);

                System.out.println("[ATM-" + atmId + "] trimite: Tranzactie #"
                        + id + " " + String.format("%.2f", suma) + " RON");

                coada.adauga(t, atmId);

                // pauza intre tranzactii
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

