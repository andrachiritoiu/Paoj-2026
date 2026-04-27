package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    //A
    private static void serializare(List<Tranzactie> tranzactii) {
        File outputDir = new File("output");
        outputDir.mkdirs();

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            out.writeObject(tranzactii);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Tranzactie> deserializare(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))){
            return (List<Tranzactie>) in.readObject();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void list(List<Tranzactie> tranzactii){
        for(Tranzactie tranzactie: tranzactii){
            System.out.println(tranzactie);
        }
    }

    //B
    private static void filter(List<Tranzactie> tranzactii, String prefix){
        boolean gasit = false;

        for(Tranzactie tranzactie : tranzactii){
            if(tranzactie.getData().startsWith(prefix)){
                gasit=true;
                System.out.println(tranzactie);
            }
        }

        if(gasit==false) {
            System.out.println("Niciun rezultat.");
        }
    }


    //C
    private static void note(List<Tranzactie> tranzactiiDeserializate, int id){
        boolean gasit=false;

        for(Tranzactie tranzactie: tranzactiiDeserializate){
            if(tranzactie.getId() == id){
                gasit=true;
                System.out.println("NOTE[" + id + "]: " + tranzactie.getNote());
                break;
            }
        }

        if(gasit==false){
            System.out.println("NOTE[" + id + "]: not found");
        }
    }

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1


        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Tranzactie> tranzactii = new ArrayList<>();

        for(int i=0; i<n; i++){
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie tranzactie = new Tranzactie(
                    id, suma, data, contSursa, contDestinatie, tip
            );

            tranzactie.setNote("procesat");
            tranzactii.add(tranzactie);
        }

        serializare(tranzactii);

        List<Tranzactie> tranzactiiDeserializate = deserializare();

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "LIST" -> list(tranzactiiDeserializate);

                case "FILTER" -> {
                    String prefix = scanner.next();
                    filter(tranzactiiDeserializate, prefix);
                }

                case "NOTE" -> {
                    int id = scanner.nextInt();
                    note(tranzactiiDeserializate, id);
                }
            }
        }

    }

}
