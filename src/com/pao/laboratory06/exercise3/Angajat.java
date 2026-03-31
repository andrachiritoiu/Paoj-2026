package com.pao.laboratory06.exercise3;

public abstract class Angajat extends Persoana {
    private double salariu;

    public Angajat() {
        super();
    }

    public Angajat(String nume, String prenume, String telefon, double salariu) {
        super(nume, prenume, telefon);
        this.salariu = salariu;
    }

    public double getSalariu() {
        return salariu;
    }

    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", telefon='" + getTelefon() + '\'' +
                ", salariu=" + salariu +
                '}';
    }
}