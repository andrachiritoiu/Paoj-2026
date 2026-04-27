package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    private static void read(int idx){
        try(RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "r")) {
            //fiecare tranzacie
            byte[] record = new byte[RECORD_SIZE];

            //muta cursorul la inceputul tranzactiei cerute
            raf.seek(idx * RECORD_SIZE);
            raf.readFully(record);

            int id = ByteBuffer.wrap(record, 0, 4)
                    .order(ByteOrder.LITTLE_ENDIAN)
                    .getInt();

            double suma = ByteBuffer.wrap(record, 4, 8)
                    .order(ByteOrder.LITTLE_ENDIAN)
                    .getDouble();

            String data = new String(record, 12, 10).trim();

            TipTranzactie tip = record[22] == 0 ? TipTranzactie.CREDIT : TipTranzactie.DEBIT;

            String status = switch (record[23]) {
                case 0 -> "PENDING";
                case 1 -> "PROCESSED";
                case 2 -> "REJECTED";
                default -> "UNKNOWN";
            };

            System.out.printf(
                    "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                    idx, id, data, tip, suma, status
            );

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    private static void update(int idx, String status){
        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {

            byte statusByte = switch (status) {
                case "PENDING" -> 0;
                case "PROCESSED" -> 1;
                case "REJECTED" -> 2;
                default -> throw new IllegalArgumentException("Status invalid: " + status);
            };

            raf.seek((long) idx * RECORD_SIZE + 23);
            raf.writeByte(statusByte);

            System.out.println("Updated [" + idx + "]: " + status);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void printAll(int n){
        for (int i = 0; i < n; i++) {
            read(i);
        }
    }


    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>



        //1
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        new File("output").mkdirs();

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for(int i=0; i<n; i++){
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next();
                TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                //2
                // 1. id (4 bytes)
                out.write(ByteBuffer.allocate(4)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .putInt(id)
                        .array());   //scoate bytes din buffer

                // 2. suma (8 bytes)
                out.write(ByteBuffer.allocate(8)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .putDouble(suma)
                        .array());

                // 3. data (10 bytes)
                String dataFix = String.format("%-10s", data);
                out.write(dataFix.getBytes());

                // 4. tip (1 byte)
                byte tipByte = tip == TipTranzactie.CREDIT ? (byte) 0 : (byte) 1;
                out.writeByte(tipByte);

                // 5. status (1 byte) → PENDING
                out.writeByte(0);

                // 6. padding (8 bytes)
                out.write(new byte[8]);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());;
        }


        //3
        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "READ" -> {
                    int idx = scanner.nextInt();
                    read(idx);
                }

                case "UPDATE" -> {
                    int idx = scanner.nextInt();
                    String status = scanner.next();
                    update(idx, status);
                }

                case "PRINT_ALL" -> {
                    printAll(n);
                }
            }
        }
    }
}
