package ru.home.geekbrains.java.core_03.pak03;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/*
3. Большая задача:

Есть классы Fruit -> Apple, Orange (больше фруктов не надо);
Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта,
поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
Для хранения фруктов внутри коробки можно использовать ArrayList;

Сделать метод getWeight(), который высчитывает вес коробки, зная количество фруктов и вес одного фрукта
(вес яблока – 1.0f, апельсина – 1.5f. Не важно, в каких это единицах);

Внутри класса Коробка сделать метод compare, который позволяет сравнить текущую коробку с той,
которую подадут в compare в качестве параметра, true – если она равны по весу,
false – в противном случае (коробки с яблоками мы можем сравнивать с коробками с апельсинами);

Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую (помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами). Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
Не забываем про метод добавления фрукта в коробку.
*/

public class Box <T extends Fruit> implements Iterable<T> {

    private ArrayList<T> list = new ArrayList<>();

    private Float sampleWeight = 0f;

    public void add(@NotNull T t) {

        // Get generic parameter "type"
        if (t != null && sampleWeight == 0f)
            sampleWeight = t.getWeight();


        list.add(t);
    }

    public float getWeight() {

        return list.size() * sampleWeight;
    }


    public <K extends Fruit> boolean compare(Box<K> other) {
        
        return this.getWeight() ==  other.getWeight();
    }


    public void move(Box<T> other) {

        list.forEach(other::add);
        list.clear();
    }


    public int size() {
        return list.size();
    }


    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }




}
