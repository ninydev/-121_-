package org.itstep;

import java.util.ArrayList;

public class Network
{

    public void eduNetwork() {
        echoNetwork();

        for (int i = 0; i < 50000; i++) {
            setIndikatorOne();
            one.setExpected(1);
            one.correctWeight();
            seven.setExpected(0);
            seven.correctWeight();

            setIndikatorSeven();
            one.setExpected(0);
            one.correctWeight();
            seven.setExpected(1);
            seven.correctWeight();
        }

        echoNetwork();
    }


    public void testNetwork() {
        setIndikatorOne();
        echoIndikator();
        one.calcSum();
        System.out.print(one.getSum() + " ");
        if(one.getResult() == 1) {
            System.out.println("One");
        } else {
            System.out.println("Not One");
        }
        seven.calcSum();
        System.out.print(seven.getSum() + " ");
        if(seven.getResult() == 1) {
            System.out.println("Seven");
        } else {
            System.out.println("Not Seven");
        }


        System.out.println(" + ------------ +");

        setIndikatorSeven();
        echoIndikator();
        one.calcSum();
        System.out.print(one.getSum() + " ");
        if(one.getResult() == 1) {
            System.out.println("One");
        } else {
            System.out.println("Not One");
        }
        seven.calcSum();
        System.out.print(seven.getSum() + " ");
        if(seven.getResult() == 1) {
            System.out.println("Seven");
        } else {
            System.out.println("Not Seven");
        }

    }




    public Network() {
        buildIndikator();
        buildNumeric();
    }

    ArrayList<Neuron> indikator;
    ArrayList<Neuron> numeric;

    /**
     * Строим 7 разрядный индикатор
     */
    public void buildIndikator()
    {
        indikator = new ArrayList<>();
        indikator.add(new Neuron("a", true));
        indikator.add(new Neuron("b", true));
        indikator.add(new Neuron("c", true));
        indikator.add(new Neuron("d", true));
        indikator.add(new Neuron("e", true));
        indikator.add(new Neuron("f", true));
        indikator.add(new Neuron("g", true));

        indikator.add(new Neuron("h", true));
    }

    /**
     * Строим элементы (1 2 3 и тд - которые будут вычислять говорить, что это зачисло
     */
    public void buildNumeric(){
        numeric = new ArrayList<>();

        // 1
        one = new Neuron("One", false);
        one.setNeurons(indikator);
        one.randomWeights();
        numeric.add(one);

        // 7
        seven = new Neuron("Seven", false);
        seven.setNeurons(indikator);
        seven.randomWeights();
        numeric.add(seven);
    }

    Neuron one;
    Neuron seven;

    /**
     * Вывести состояние сети
     */
    public void echoNetwork() {
        for (Neuron neuron : numeric) {
            System.out.println(neuron);
        }
    }


    public void setIndikatorOne(){
        indikator.get(0).setResult(0); // a
        indikator.get(1).setResult(1); // b
        indikator.get(2).setResult(1); // c
        indikator.get(3).setResult(0); // d
        indikator.get(4).setResult(0); // e
        indikator.get(5).setResult(0); // f
        indikator.get(6).setResult(0); // g
        indikator.get(7).setResult(1); // h
    }

    public void setIndikatorSeven(){
        indikator.get(0).setResult(1); // a
        indikator.get(1).setResult(1); // b
        indikator.get(2).setResult(1); // c
        indikator.get(3).setResult(0); // d
        indikator.get(4).setResult(0); // e
        indikator.get(5).setResult(0); // f
        indikator.get(6).setResult(0); // g
        indikator.get(7).setResult(1); // h
    }


    public void echoIndikator() {
        // 1
        if (indikator.get(0).getResult() == 0) {
            System.out.println(" ");
        } else {
            System.out.println(" ---- ");
        }

        // 2
        if (indikator.get(5).getResult() == 1) {
            System.out.print(" |  ");
        } else {
            System.out.print("    ");
        }

        if (indikator.get(1).getResult() == 1) {
            System.out.println(" |");
        } else {
            System.out.println("  ");
        }

        // 3
        if (indikator.get(6).getResult() == 0) {
            System.out.println(" ");
        } else {
            System.out.println(" ---- ");
        }

        // 4
        if (indikator.get(4).getResult() == 1) {
            System.out.print(" |  ");
        } else {
            System.out.print("    ");
        }

        if (indikator.get(2).getResult() == 1) {
            System.out.println(" |");
        } else {
            System.out.println("  ");
        }

        // 3
        if (indikator.get(3).getResult() == 0) {
            System.out.println(" ");
        } else {
            System.out.println(" ---- ");
        }

        System.out.println("\n************************\n");
    }
}
