package ru.home.geekbrains.java.core_03.threading3.entities.fleet;

import ru.home.geekbrains.java.core_03.threading3.entities.cruise.Cruise;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.Segment;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.Task;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Channel;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Harbor;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Nav;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.Terminal;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.DockList;
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
    boolean onAssignment;

    // segment handler list
    private Map<Segment, Consumer<Segment>> segHandle = new HashMap<>();


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
                segHandle.get(segment).accept(segment);
            }
        }
        
        System.out.println("Ship '" + name + "' dismissed");
    }


    /**
     * Navigation router
     */
    private void setupNavigation() {

        for(Segment seg : cruise.list) {

            // just sail
            if (seg.getNav() instanceof Channel) {
                segHandle.put(seg, this::passChannel);
                continue;
            }

            // currentTonnage cargo
            if (seg.getTask() == Task.LOAD) {
                segHandle.put(seg, this::loadCargo);
                continue;
            }

            // unload cargo
            if (seg.getTask() == Task.UNLOAD) {
                segHandle.put(seg, this::unloadCargo);
            }
        }


    }



    private void passChannel(Segment segment) {

        Nav nav = null;
        try {

            nav = segment.getNav();

            nav.enter(name);

            // moving
            Thread.sleep((long) (TICK_TIME * nav.getLength() / speed()));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            nav.leave(name);
        }
    }





    private void loadCargo(Segment segment) {

        Nav nav = null;

        try {

            nav = segment.getNav();

            nav.enter(name);

            // moving to terminal
            Thread.sleep((long) (TICK_TIME * nav.getLength() / 2 / speed()));


            // get dock list
            DockList dockList = ((Harbor) nav).getPort().getDockList();

            // find appropriate dock
            NavigableMap<Integer, Terminal> supportedDockList = new TreeMap<>();

            for (Terminal d : dockList) {

                // Если терминал отгружает данный тип товара и он есть в наличии в порту
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
                System.out.println("Ship '" + name + "' can't find appropriate terminal to load cargo " + cargoType + ". Sailing home.");
            }

            // moving out harbor
            Thread.sleep((long) (TICK_TIME * nav.getLength() / 2 / speed()));


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            nav.leave(name);
        }
    }





    private void unloadCargo(Segment segment) {

        Nav nav = null;

        try {

            nav = segment.getNav();

            nav.enter(name);


            // moving to terminal
            Thread.sleep((long) (TICK_TIME * nav.getLength() / 2 / speed()));

            // get dock list
            DockList dockList = ((Harbor) nav).getPort().getDockList();

            // find appropriate dock
            NavigableMap<Integer, Terminal> supportedDockList = new TreeMap<>();

            for (Terminal d : dockList) {

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


            // moving harbor
            Thread.sleep((long) (TICK_TIME * nav.getLength() / 2 / speed()));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            nav.leave(name);
        }
    }






























}







//        for(Map.Entry<Nav, Consumer<Nav>> entry : segHandle.entrySet()) {
//
//
//        }



