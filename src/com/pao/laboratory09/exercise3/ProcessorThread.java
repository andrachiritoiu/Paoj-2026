package com.pao.laboratory09.exercise3;

public class ProcessorThread implements Runnable {
    public volatile boolean activ = true;

    private final CoadaTranzactii coada;
    private int totalProcesate = 0;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            while (activ || !coada.esteGoala()) {

                Tranzactie t = coada.extrage();

                Thread.sleep(80);

                System.out.printf(
                        "[Processor] Factura #%d - %.2f RON | %s%n",
                        t.getId(),
                        t.getSuma(),
                        t.getData()
                );

                totalProcesate++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getTotalProcesate() {
        return totalProcesate;
    }
}