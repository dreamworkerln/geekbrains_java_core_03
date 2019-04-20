package ru.home.geekbrains.java.core_03.threading3.entities.geo;

import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.Terminal;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.Port;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.ProductType;


import java.util.HashMap;
import java.util.Map;


/**
 * Справочник географических объектов
 */
public class GeoMap {

    // Map Название ->
    public static Map<String, Geo> map = new HashMap<>();

    static {

        createMap();
    }


    // initialize geo data
    private static void createMap() {

        Channel channel = new Channel("Пролив", 10, 2);
        map.put(channel.getName(), channel);

        // Порт загрузки
        Port port = new Port("Порт Поставка");
        port.getTerminalList().add(new Terminal(1, ProductType.AMMUNITION));
        port.getTerminalList().add(new Terminal(2, ProductType.FUEL));
        port.getTerminalList().add(new Terminal(3, ProductType.CONSTRUCTION_MATERIALS));
        //
        port.getGoods().put(ProductType.AMMUNITION, 8021);
        port.getGoods().put(ProductType.FUEL, 6580);
        port.getGoods().put(ProductType.CONSTRUCTION_MATERIALS, 4005);
        Harbor harbor =  new Harbor("Гавань Поставка", 8, port);
        map.put(harbor.getName(), harbor);


        // Порт разгрузки
        port = new Port("Порт Прием");
        for (int i = 9; i >0 ; i--) {
            port.getTerminalList().add(new Terminal(i, true));
        }
        port.getGoods().put(ProductType.AMMUNITION, 0);
        port.getGoods().put(ProductType.FUEL, 0);
        port.getGoods().put(ProductType.CONSTRUCTION_MATERIALS, 0);
        harbor =  new Harbor("Гавань Прием", 5, port);
        map.put(harbor.getName(), harbor);
    }
}
