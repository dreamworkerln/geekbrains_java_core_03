package ru.home.geekbrains.java.core_03.threading3;




/*      1 Есть транспортные корабли, которые подплывают к проливу и далее
        плывут к докам для погрузки разного рода товара.

        2 Они проходят через узкий пролив где одновременно могут находиться
        только 2 корабля.

        3 Вид кораблей и их вместительность могут быть разными в зависимости от типа товаров,
        которые нужно загрузить на корабль. (Представим что корабли везут Боеприпасы, Топливо, Стройматериалы)

        4 Есть 3 вида доков для погрузки кораблей в соотвествие с товарами,
        за одну секунду док загружает на корабль 100 ед. товара, вместимость кораблей
        500.

        5 После загрузки нужно пройти обратно через пролив и перевести товар.

        6 Нужно перевести 2700 ед. боеприпасов, 5900 топлива, 8500 стройматериалов.

        Перевести груз.
        Правильно разбить задачу на параллельность.
        Синхронизировать потоки, сохранить целостность данных.*/


import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.Task;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.TaskEntry;
import ru.home.geekbrains.java.core_03.threading3.entities.fleet.Ship;
import ru.home.geekbrains.java.core_03.threading3.entities.fleet.ShipFactory;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.GeoMap;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Harbor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 1. Едем за товаром - Пролив
 *
 * 2. Загрузка - List<BlockingQueue<Runnable>> список очередей из кораблей(Runnable) на каждый терминал
 *    Если все терминалы заняты, то становимся в очередь где меньше всего кораблей
 *
 * 3. Везем обратно - Пролив
 * 4. Выгружаем товар - все корабли могут разгружатся одновременно
 *
 * длина пролива 10 единиц, semaphore(2).
 * вместимость корабля(тоннаж) от 100 до 1000 ед (кратно 100)
 * 100 ед. товара грузится/выгружается 1 сек
 * Скорость корабля V = V0(1- 0.5*количество_перевозимого_товара/Тоннаж)
 * V0 = (2 - Тоннаж/1000) ед/сек
 *
 */
public class App
{

    public static double TICK_TIME = 100.0;

    public static void main(String[] args) {

        new App();
    }
    
    

    App()  {




        Harbor harbour = (Harbor)GeoMap.map.get("Гавань Поставка");
        System.out.println((harbour.getPort().toString()));
        harbour = (Harbor)GeoMap.map.get("Гавань Прием");
        System.out.println(harbour.getPort().toString());
        System.out.println("------------------------------------------");



        // crutches
        List<TaskEntry> route = new ArrayList<>(); //Arrays.asList("Ferry","Гавань Поставка","Ferry","Гавань Прием");

        // add nav tasks
        route.add(new TaskEntry("Пролив", Task.SAIL));
        route.add(new TaskEntry("Гавань Поставка", Task.LOAD));
        route.add(new TaskEntry("Пролив", Task.SAIL));
        route.add(new TaskEntry("Гавань Прием", Task.UNLOAD));






        final CustomizableThreadFactory threadFactory = new CustomizableThreadFactory();
        threadFactory.setDaemon(true);
        threadFactory.setThreadNamePrefix("MyPool-");//
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool(threadFactory);


        List<Ship> fleet = ShipFactory.createFleet(10, route);
        for(Ship ship : fleet) {
            threadPool.execute(ship);
        }

        threadPool.shutdown();

        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------");
        harbour = (Harbor)GeoMap.map.get("Гавань Поставка");
        System.out.println((harbour.getPort().toString()));
        harbour = (Harbor)GeoMap.map.get("Гавань Прием");
        System.out.println(harbour.getPort().toString());


    }

}