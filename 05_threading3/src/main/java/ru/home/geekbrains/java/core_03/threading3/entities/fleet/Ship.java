package ru.home.geekbrains.java.core_03.threading3.entities.fleet;

import ru.home.geekbrains.java.core_03.threading3.entities.cruise.Cruise;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.Segment;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.Task;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Channel;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Harbor;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Geo;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.Terminal;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.TerminalList;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.ProductType;

import java.util.*;
import java.util.function.Consumer;

import static ru.home.geekbrains.java.core_03.threading3.App.TICK_TIME;

public class Ship implements Runnable {

    private String name;           // Имя корабля
    private double maxSpeed;       // макс скорость пустого
    private int maxTonnage;        // макс перевозимый груз
    private int currentTonnage;    // текущий груз
    private ProductType cargoType; // тип груза, перевозимого кораблем (перманентен, зависит от типа корабля)

    private Cruise cruise;         // segment list
    private boolean onAssignment;

    // segment handler list
    private Map<Segment, Consumer<Segment>> router = new HashMap<>();


    public Ship(String name, int maxTonnage, ProductType cargoType,  Cruise cruise) {

        this.name = name;
        this.maxTonnage = maxTonnage;
        this.cruise = cruise;
        this.cargoType = cargoType;
        this.onAssignment = true;

        // от ~3 для легких кораблей до 1 для тяжелых
        maxSpeed = (3 - 2*maxTonnage/1000.0);

        // setup navigation
        setupNavigation();
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxTonnage() {
        return maxTonnage;
    }

    public int getCurrentTonnage() {
        return currentTonnage;
    }
    public void setCurrentTonnage(int currentTonnage) {
        this.currentTonnage = currentTonnage;
    }

    public ProductType getCargoType() {return cargoType;}

    public double speed() {
        return maxSpeed * (1 - 0.5 * currentTonnage / maxTonnage);
    }



    @Override
    public void run() {

        while(onAssignment) {

            for (Segment segment : cruise.list) {
                router.get(segment).accept(segment);
                if(!onAssignment) break;
            }
        }

        System.out.println("Ship '" + name + "' DISMISSED");
    }


    /**
     * Navigation router
     */
    private void setupNavigation() {

        for(Segment seg : cruise.list) {

            // just sail
            if (seg.getGeo() instanceof Channel) {
                router.put(seg, this::passChannel);
                continue;
            }

            // currentTonnage cargo
            if (seg.getTask() == Task.LOAD) {
                router.put(seg, this::loadCargo);
                continue;
            }

            // unload cargo
            if (seg.getTask() == Task.UNLOAD) {
                router.put(seg, this::unloadCargo);
            }
        }
    }



    private void passChannel(Segment segment) {

        Geo geo = segment.getGeo();
        try {

            geo.enter(name); // Входим в пролив

            // moving
            Thread.sleep((long) (TICK_TIME * geo.getLength() / speed()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            geo.leave(name); // Выходим их пролив
        }
    }





    private void loadCargo(Segment segment) {

        Geo geo = segment.getGeo();

        try {

            geo.enter(name); // Входим в гавань

            // moving to terminal
            Thread.sleep((long) (TICK_TIME * geo.getLength() / 2 / speed()));

            // check if requested material is available in port
            if (((Harbor) geo).getPort().getGoods().get(cargoType) > 0) {

                // get dock list
                TerminalList terminalList = ((Harbor) geo).getPort().getTerminalList();

                // find appropriate dock
                NavigableMap<Integer, Terminal> supportedDockList = new TreeMap<>();

                for (Terminal d : terminalList) {

                    // Если терминал отгружает данный тип товара и он есть в наличии в порту
                    // (пока ждали могли растащить)
                    if (d.getAmount(cargoType) > 0) {
                        supportedDockList.put(d.getQueueLength(), d);
                    }
                }

                if (supportedDockList.size() > 0) {

                    // Get the first (maybe) free terminal
                    Terminal terminal = supportedDockList.firstEntry().getValue();

                    // Load cargo
                    int received = terminal.get(cargoType, maxTonnage - currentTonnage, name);
                    currentTonnage += received;
                }
                else {
                    onAssignment = false;

                }

            }
            else {
                onAssignment = false;
            }

            // logging no material
            if (!onAssignment) {
                String materialNotAvailable =
                        "Ship '" + name + "' can't find appropriate terminal to load cargo " +
                        cargoType + ". Sailing home.";
                System.out.println(materialNotAvailable);
            }

            // moving out harbor
            Thread.sleep((long) (TICK_TIME * geo.getLength() / 2 / speed()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            geo.leave(name); // Выходим из гавани
        }
    }





    private void unloadCargo(Segment segment) {

        Geo geo = segment.getGeo();

        try {

            geo.enter(name); // Входим в гавань

            // moving to terminal
            Thread.sleep((long) (TICK_TIME * geo.getLength() / 2 / speed()));

            // get dock list
            TerminalList terminalList = ((Harbor) geo).getPort().getTerminalList();

            // find appropriate dock
            NavigableMap<Integer, Terminal> supportedDockList = new TreeMap<>();

            for (Terminal d : terminalList) {

                // Если терминал загружает данный тип товара
                if (d.getProductTypeSet().contains(cargoType)) {
                    supportedDockList.put(d.getQueueLength(), d);
                }
            }

            if (supportedDockList.size() > 0) {

                // Get the first (maybe) free terminal
                Terminal terminal = supportedDockList.firstEntry().getValue();

                // unload cargo
                terminal.put(cargoType, currentTonnage, name);
                currentTonnage = 0;
                //System.out.println("Ship " + this.name +  " have now " + cargoType + ": " +  currentTonnage);
            }
            else {
                onAssignment = false;
                System.out.println("Can't find appropriate dock terminal to unload cargo. Sailing home.");
            }


            // moving out of harbor
            Thread.sleep((long) (TICK_TIME * geo.getLength() / 2 / speed()));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            geo.leave(name); // Выходим из гавани
        }
    }
}
