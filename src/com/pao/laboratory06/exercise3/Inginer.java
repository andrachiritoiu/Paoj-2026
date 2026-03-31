package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private String username;
    private String parola;
    private double sold;

    public Inginer() {
        super();
        this.sold = 0;
    }

    public Inginer(String nume, String prenume, String telefon, double salariu,
                   String username, String parola, double sold) {
        super(nume, prenume, telefon, salariu);
        this.username = username;
        this.parola = parola;
        this.sold = sold;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public double getSoldIntern() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.trim().isEmpty() || parola == null || parola.trim().isEmpty()) {
            throw new IllegalArgumentException("User sau parola nu pot fi null/goale.");
        }

        if (!user.equals(this.username) || !parola.equals(this.parola)) {
            throw new IllegalArgumentException("Credențiale invalide.");
        }

        System.out.println("Autentificare reușită pentru inginerul " + getNume() + " " + getPrenume());
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) {
            throw new IllegalArgumentException("Suma trebuie să fie pozitivă.");
        }

        if (suma > sold) {
            return false;
        }

        sold -= suma;
        return true;
    }

    @Override
    public int compareTo(Inginer other) {
        if (other == null) {
            throw new IllegalArgumentException("Obiectul comparat nu poate fi null.");
        }
        return this.getNume().compareToIgnoreCase(other.getNume());
    }

    @Override
    public String toString() {
        return "Inginer{" +
                "nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", telefon='" + getTelefon() + '\'' +
                ", salariu=" + getSalariu() +
                ", sold=" + sold +
                '}';
    }
}