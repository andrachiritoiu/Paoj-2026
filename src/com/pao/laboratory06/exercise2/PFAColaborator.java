package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica{
    private double cheltuieliLunare;
    private final double salariuMinimBrut= 4050.0 * 12;

    public PFAColaborator(){
        super();
        this.tip=TipColaborator.PFA;
    }

    public PFAColaborator(String nume, String prenume, double venit, double cheltuieliLunare) {
        super(nume, prenume, venit, TipColaborator.PFA);
        this.cheltuieliLunare=cheltuieliLunare;
    }


    @Override
    public void citeste(Scanner in){
        super.citeste(in);
        this.cheltuieliLunare=in.nextDouble();
    }

    @Override
    public String tipContract(){
        return "PFA";
    }

    @Override
    public double calculeazaVenitNetAnual(){
        double venitNet = (this.venit - this.cheltuieliLunare) * 12;

        double impozit = 0.10 * venitNet;

        double cass;
        if (venitNet < 6 * salariuMinimBrut) {
            cass = 0.10 * (6 * salariuMinimBrut);
        } else if (venitNet <= 72 * salariuMinimBrut) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * salariuMinimBrut);
        }

        double cas;
        if (venitNet < 12 * salariuMinimBrut) {
            cas = 0;
        } else if (venitNet <= 24 * salariuMinimBrut) {
            cas = 0.25 * (12 * salariuMinimBrut);
        } else {
            cas = 0.25 * (24 * salariuMinimBrut);
        }

        return venitNet - impozit - cass - cas;
    }
}
