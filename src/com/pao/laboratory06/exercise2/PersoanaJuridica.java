package com.pao.laboratory06.exercise2;

public abstract class PersoanaJuridica extends Colaborator{
    public PersoanaJuridica(){
        super();
    }

    public PersoanaJuridica(String nume, String prenume, double venit, TipColaborator tip) {
        super(nume, prenume, venit, tip);
    }

    public abstract double calculeazaVenitNetAnual();
}
