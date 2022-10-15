package com.application.warehouse;

import java.util.Queue;
import java.util.Random;
import java.util.UUID;

public class Loader implements Runnable{


    int id ;
    boolean stopLoading = false;
    Warehouse warehouse;

    public Loader(Warehouse warehouse, int id)
    {
        this.id = id;
        this.warehouse = warehouse;
    }

    public void setStopLoading(boolean stopLoading)
    {
        this.stopLoading = stopLoading;
    }

    @Override
    public void run() {

        System.out.println(stopLoading);
        while(true)
        {
            if (stopLoading)
                return;

            Random random = new Random();
            int randomInt = random.nextInt(10);
            try {
                UUID uuid = UUID.randomUUID();
                System.out.println("I have loaded the Item with UUID:" + uuid + " and My ID is: " + id);

                warehouse.pushToQueue(uuid);
                Thread.sleep(randomInt * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
