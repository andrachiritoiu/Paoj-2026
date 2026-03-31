package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica{
    private double cheltuieliLunare;

    public SRLColaborator(){
        super();
        this.tip = TipColaborator.SRL;
    }

    public SRLColaborator(String nume, String prenume, double venit, TipColaborator tip, double cheltuieliLunare) {
        super(nume, prenume, venit, TipColaborator.SRL);
        this.cheltuieliLunare = cheltuieliLunare;
    }

    @Override
    public void citeste(Scanner in){
        super.citeste(in);
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public String tipContract(){
        return "SRL";
    }


    @Override
    public double calculeazaVenitNetAnual(){
        return (venit - cheltuieliLunare) * 12.0 * 0.84;
    }

}
