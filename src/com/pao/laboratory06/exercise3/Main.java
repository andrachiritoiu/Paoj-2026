package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Inginer ing1 = new Inginer("Popescu", "Andrei", "0711111111", 9000,
                "andrei.popescu", "pass123", 2500);
        Inginer ing2 = new Inginer("Ionescu", "Maria", "0722222222", 12000,
                "maria.ionescu", "abc123", 4000);
        Inginer ing3 = new Inginer("Georgescu", "Vlad", "", 10000,
                "vlad.georgescu", "xyz789", 1500);

        Inginer[] ingineri = {ing1, ing2, ing3};

        System.out.println("=== Array inițial de ingineri ===");
        for (Inginer ing : ingineri) {
            System.out.println(ing);
        }

        System.out.println("\n=== Sortare naturală după nume ===");
        Arrays.sort(ingineri);
        for (Inginer ing : ingineri) {
            System.out.println(ing);
        }

        System.out.println("\n=== Sortare descrescătoare după salariu ===");
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer ing : ingineri) {
            System.out.println(ing);
        }

        System.out.println("\n=== Acces prin referință de tip PlataOnline ===");
        PlataOnline clientOnline = ing1;
        clientOnline.autentificare("andrei.popescu", "pass123");
        System.out.println("Sold curent: " + clientOnline.consultareSold());
        System.out.println("Plată 500: " + clientOnline.efectuarePlata(500));
        System.out.println("Sold după plată: " + clientOnline.consultareSold());

        System.out.println("\n=== Persoana juridică prin referință PlataOnlineSMS ===");
        PlataOnlineSMS firma = new PersoanaJuridica("SC Tech SRL", "Administrator", "0733333333",
                "tech.srl", "firma123", 10000);

        firma.autentificare("tech.srl", "firma123");
        System.out.println("Sold firmă: " + firma.consultareSold());
        System.out.println("SMS valid trimis: " + firma.trimiteSMS("Plata a fost procesată."));
        System.out.println("Plată 2000: " + firma.efectuarePlata(2000));
        System.out.println("Sold după plată: " + firma.consultareSold());

        PersoanaJuridica firmaConcreta = (PersoanaJuridica) firma;
        System.out.println("SMS-uri stocate: " + firmaConcreta.getSmsTrimise());

        System.out.println("\n=== Caz fără telefon ===");
        PlataOnlineSMS firmaFaraTelefon = new PersoanaJuridica("SC NoPhone SRL", "Admin", "",
                "nophone", "1234", 5000);
        System.out.println("SMS trimis fără telefon: " + firmaFaraTelefon.trimiteSMS("Mesaj test"));

        System.out.println("\n=== Caz mesaj invalid ===");
        System.out.println("SMS cu mesaj gol: " + firma.trimiteSMS("   "));
        System.out.println("SMS cu mesaj null: " + firma.trimiteSMS(null));

        System.out.println("\n=== Enum constante financiare ===");
        System.out.println("TVA = " + ConstanteFinanciare.TVA.getValoare());
        System.out.println("SALARIU_MINIM = " + ConstanteFinanciare.SALARIU_MINIM.getValoare());

        System.out.println("\n=== Tratare erori ===");
        try {
            clientOnline.autentificare(null, "parola");
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare autentificare: " + e.getMessage());
        }

        try {
            clientOnline.efectuarePlata(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare plată: " + e.getMessage());
        }

        System.out.println("\n=== UnsupportedOperationException pe entitate fără SMS ===");
        try {
            PlataOnline doarOnline = new Inginer("Marin", "Alex", "0744444444", 8000,
                    "alex.marin", "pass", 3000);

            if (!(doarOnline instanceof PlataOnlineSMS)) {
                throw new UnsupportedOperationException("Entitatea nu are capabilitate SMS.");
            }

            ((PlataOnlineSMS) doarOnline).trimiteSMS("Test SMS");
        } catch (UnsupportedOperationException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
}