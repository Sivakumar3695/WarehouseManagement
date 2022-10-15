package com.application.warehouse;

import java.util.Random;
import java.util.UUID;

public class Packer implements Runnable{

    int id ;
    boolean stopProcessing = false;
    Warehouse warehouse;
    int retryCount = 0;

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
                    retryCount++;
                    if (retryCount > 3) break;
                    return;
                }

                retryCount = 0;

                System.out.println("My Id:" + id + " and I packed the stock with Id:" + stockId);
                Random random = new Random();
                int randomInt = random.nextInt(5);
                Thread.sleep(randomInt * 1000);
            }
            catch (Exception e)
            {
                System.out.println("Error occurred");
            }

        }
    }
}
