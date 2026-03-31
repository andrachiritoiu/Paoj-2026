package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica{
    private boolean bonus;

    public CIMColaborator(){
        super();
        this.tip=TipColaborator.CIM;
        this.bonus=false;
    }

    public CIMColaborator(String nume, String prenume, double venit, boolean bonus) {
        super(nume, prenume, venit, TipColaborator.CIM);
        this.bonus=bonus;
    }

    @Override
    public void citeste(Scanner in){
        super.citeste(in);

        if (in.hasNext()) {
            String bonusText=in.next();
            this.bonus=bonusText.equalsIgnoreCase("DA");
        } else {
            this.bonus = false;
        }
    }

    @Override
    public String tipContract(){
        return "CIM";
    }


    @Override
    public double calculeazaVenitNetAnual(){
       double v=venit*12*0.55;

       if(bonus){
           v*=1.10;
       }
       return v;
    }
}
