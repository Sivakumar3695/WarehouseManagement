package com.application.warehouse;

import java.util.Random;
import java.util.UUID;

public class Packer implements Runnable{

    int id ;
    Warehouse warehouse;

    public Packer(Warehouse warehouse, int id)
    {
        this.id = id;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {

        while (true)
        {
            try
            {
                UUID stockId = warehouse.getItemFromStock();

                if (stockId == null)
                {
                    continue;
                }

                System.out.println("Packer Worker ID: " + id + ", Packed Item ID::" + stockId);
                Random random = new Random();
                int randomInt = random.nextInt(10);
                Thread.sleep(randomInt * 1000);
            }
            catch (Exception e)
            {
                System.out.println("Error occurred");
            }
        }
//        System.out.println("Packer Worker Id:" + id + ", Leaving now!");

    }
}
