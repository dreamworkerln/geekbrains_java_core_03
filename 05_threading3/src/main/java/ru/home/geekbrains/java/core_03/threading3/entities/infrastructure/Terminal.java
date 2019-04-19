package ru.home.geekbrains.java.core_03.threading3.entities.infrastructure;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import static ru.home.geekbrains.java.core_03.threading3.App.TICK_TIME;

/**
 * Причал с грузовым терминалом
 */
public class Terminal {

    private Set<ProductType> typeSet = new HashSet<>();
    private Semaphore semaphore = new Semaphore(1);
    private Port owner;


    /**
     * Create dock with specified single goods type terminal
     * @param type ProductType
     */
    public Terminal(ProductType type) {
        typeSet.add(type);
    }


    /**
     * Create dock with all/none goods type terminal
     * @param all boolean
     */
    public Terminal(boolean all) {

        if (all) {
            typeSet.addAll(Arrays.asList(ProductType.values()));
        }
    }


    public Port getOwner() {
        return owner;
    }

    public void setOwner(Port owner) {
        this.owner = owner;
    }

    /**
     * Get set of goods type terminals available in this dock
     * @return Set<ProductType>
     */
    public Set<ProductType> getProductTypeSet() {
        return typeSet;
    }

    public boolean contains(ProductType productType) {

        return typeSet.contains(productType);
    }

//    @Override
//    public void enter() {
//
//        try {
//            semaphore.acquire();
//            System.out.println("Entering: " + this.getClass().getSimpleName());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    @Override
//    public void leave() {
//
//        System.out.println("Leaving: " + this.getClass().getSimpleName());
//    }



    public int getApproximateQueueLength() {
        return semaphore.getQueueLength();
    }


    public int getAmount(ProductType productType) {

        int result = 0;

        if (typeSet.contains(productType)) {

            result = owner.getGoods().get(productType);
        }
        return result;
    }


    /**
     * Load cargo from dock terminal to board
     * @param productType ProductType
     * @param amount int
     * @return
     */
    public int get(ProductType productType, int amount, String shipName) {

        if (amount < 0 )
            throw new InvalidParameterException("amount < 0 " + amount);

        try {

            System.out.println(
                    "Ship '" + shipName + "' awaiting at terminal '" +
                    owner.getName() + "' "+  typeSet.toString());

            semaphore.acquire();

            int available = owner.getGoods().get(productType);
            amount = Math.min(amount, available);
            available -= amount;
            owner.getGoods().put(productType, available);
            System.out.println(
                    "Terminal '" + owner.getName() + "' " +  typeSet.toString() +
                    " serving ship '" + shipName + "'");
            Thread.sleep((long) (TICK_TIME * amount/100));
            System.out.println(
                    "Terminal '" + owner.getName() + "' " +  typeSet.toString() +
                    " send " + productType.toString() + ": " +
                    amount + " to '" + shipName + "' Current: " + available);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            semaphore.release();
        }

        return amount;
    }




    /**
     * Upload cargo from board to dock terminal
     * @param productType ProductType
     * @param amount int
     * Терминал не переполняется
     */
    public void put(ProductType productType, int amount ,String shipName) {

        if (amount < 0 )
            throw new InvalidParameterException("requested < 0 " + amount);

        try {

            System.out.println(
                    "Ship '" + shipName + "' awaiting at terminal '" +
                    owner.getName() + "' "+  typeSet.toString());

            semaphore.acquire();

            int available = owner.getGoods().get(productType);
            available += amount;
            owner.getGoods().put(productType, available);
            System.out.println(
                    "Terminal '" + owner.getName() + "' " +  typeSet.toString() +
                    " serving ship '" + shipName + "'");
            Thread.sleep((long) (TICK_TIME * amount / 100));
            System.out.println(
                    "Terminal '" + owner.getName()+ "' " +  typeSet.toString() +
                    " received " + productType.toString() + ": "
                    + amount + " from '" + shipName + "' Current: " + available);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            semaphore.release();
        }
    }






}
