package ru.home.geekbrains.java.core_03.threading3.entities.fleet;

import ru.home.geekbrains.java.core_03.threading3.entities.cruise.Cruise;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.TaskEntry;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.ProductType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ShipFactory {


    static class StringGen {


        // class variable
        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
        final java.util.Random rand = new java.util.Random();

        // consider using a Map<String,Boolean> to say whether the identifier is being used or not
        final Set<String> identifiers = new HashSet<>();

        public String randomIdentifier() {
            StringBuilder builder = new StringBuilder();
            while(builder.toString().length() == 0) {
                int length = rand.nextInt(5)+5;
                for(int i = 0; i < length; i++) {
                    builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
                }
                if(identifiers.contains(builder.toString())) {
                    builder = new StringBuilder();
                }
            }
            return builder.toString();
        }



    }




    public static Ship createRandom(ProductType type, List<TaskEntry> route) {

        Ship result;

        // 100 -1000
        int maxTonnage = 100 * ThreadLocalRandom.current().nextInt(1, 10);

        Cruise cruise = Cruise.build(route);
        StringGen sGen = new StringGen();

        // DEBUG
        //maxTonnage = 1000;

        result = new Ship(sGen.randomIdentifier(), maxTonnage, type, cruise);

        return result;
    }



    public static List<Ship> createFleet(int count, List<TaskEntry> route) {

        List<Ship> result = new ArrayList<>();

        ProductType[] cargoTypeList = ProductType.values();

        // возможно не создаст к-то тип корабля и тогда соответствующий товар не будет перевезен
        int j = 0;
        for (int i = 0; i < count; i++) {

            // DEBUG
            int rndI = ThreadLocalRandom.current().nextInt(0, cargoTypeList.length);

            result.add(createRandom(cargoTypeList[j], route));

            // DEBUG
            j = j < cargoTypeList.length -1 ? ++j : 0;
        }

        return result;
    }










}
