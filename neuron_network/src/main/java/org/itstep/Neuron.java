package org.itstep;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data
public class Neuron {

    /**
     * Вычисляем - насколько нужно скорректировать значение веса
     * сигмоид функция
     */
    private Double sigmoid = 0.0;
    public void calcSigmoid(){
        if(isBegin) return;
        sigmoid = 1 / (1 + Math.exp(-sum));
    }

    // Ожидаемое значение
    private int expected;

    public void correctWeight() {
        if(isBegin) return;

        calcSum();
        calcSigmoid();

        // Насколько я ошибся в процессе вычисления
        double error = sigmoid - expected;

        // Насколько мне нужно изменить значение веса
        double correct = error * sigmoid * (1 - sigmoid);

        // Шаг обучения сети
        double rate = 0.1;

        for (int i = 0; i < size; i++) {
            double w = weights.get(i);
            double n = w - neurons.get(i).getSum() * correct * rate;
            weights.set(i, n);
        }

    }



    // Результат 0 или 1 - то есть да или нет
    private int result;

    public int getResult() {
        if(sum > 0.5) {
            return 1;
        }
        return 0;
    }

    public void setResult(int result) {
        this.result = result;
        this.sum = (double) result;
    }

    // Вычесленная сумма
    private Double sum = 0.0;

    public void calcSum() {
        sum = 0.0;

        for (int i = 0; i < size; i++) {
            sum += neurons.get(i).getResult() * weights.get(i);
        }

    }



    protected static Random random = new Random();

    public Neuron(String name, Boolean isBegin)
    {
        this.name = name;
        this.isBegin = isBegin;

        neurons = new ArrayList<>();
        weights = new ArrayList<>();
    }


    private int size;

    /**
     * Установим случайные показатели
     */
    public void randomWeights()
    {
        size = neurons.size();
        weights = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            weights.add(random.nextDouble());
        }
    }

    /**
     * Вывод данных по нейрону
     * @return
     */
    public String toString()
    {
        String res = "Neuron " + name + "\n [";
        for (int i = 0; i < size; i++) {
            res += neurons.get(i).getName() + " " + weights.get(i).toString() + ", ";
        }
        res += "]\n";
        return res;
    }


    /**
     * Указывает на то, что этот нейрон - начальный
     * У него нет рядя перед ним
     */
    private Boolean isBegin;

    /**
     * Имя нейрона - что бы мы видели - что это
     */
    private String name;

    /**
     * Связь с другими нейронами
     */
    ArrayList<Neuron> neurons;

    /**
     * Вес
     */
    ArrayList<Double> weights;

}
