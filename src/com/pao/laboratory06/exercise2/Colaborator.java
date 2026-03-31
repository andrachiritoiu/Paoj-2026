package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public abstract class Colaborator implements IOperatiiCitireScriere, Comparable<Colaborator>{
    protected String nume;
    protected String prenume;
    protected double venit;
    protected TipColaborator tip;

    public Colaborator(){}

    public Colaborator(String nume, String prenume, double venit, TipColaborator tip){
        this.nume=nume;
        this.prenume=prenume;
        this.venit=venit;
        this.tip=tip;
    }

    public String  getNume(){
        return this.nume;
    }

    public String getPrenume(){
        return this.prenume;
    }

    public double getVenit(){
        return this.venit;
    }

    public TipColaborator getTip(){
        return this.tip;
    }

    public abstract double calculeazaVenitNetAnual();

    @Override
    public void citeste(Scanner in){
        this.nume=in.next();
        this.prenume=in.next();
        this.venit=in.nextDouble();
    }

    @Override
    public void afiseaza(){
        System.out.printf("%s: %s %s, venit net anual: %.2f lei%n",
                tipContract(), nume, prenume, calculeazaVenitNetAnual());
    }

    //tipColaborator se implemeteaza in subclase

    @Override
    public int compareTo(Colaborator other) {
        return Double.compare(other.calculeazaVenitNetAnual(), this.calculeazaVenitNetAnual());
    }
}
