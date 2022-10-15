package com.application.warehouse;

import java.util.ArrayList;
import java.util.List;

public class Supervisor implements Runnable {

    int id;
    long startTime;
    List<Integer> threshold = new ArrayList<>();
    int totalDuration;
    Warehouse warehouse;
    boolean stopProcess = false;
    int prevInc = 0;


    public Supervisor(Warehouse warehouse, int id, List<Integer> threshold, int totalDuration)
    {
        this.id = id;
        this.startTime = -1;
        System.out.println(threshold);
        this.threshold = threshold;
        this.totalDuration = totalDuration * 1000 * 60;
        this.warehouse = warehouse;
    }

    public void start()
    {
        startTime = System.currentTimeMillis();
        new Thread(this).start();
    }

    public int checkThreshold(int stockListSize)
    {
        for (int idx = threshold.size() - 1; idx >= 0; idx--)
        {
            if (threshold.get(idx) < stockListSize)
            {
                Double returnVal = Math.pow(2, Double.valueOf(String.valueOf(idx)));
                int incCnt = Integer.valueOf(returnVal.toString().split("\\.")[0]);
                if (incCnt <= prevInc) return -1;
                else {
                    prevInc = incCnt;
                }
                return  incCnt;
            }

        }

        return -1;
    }


    @Override
    public void run() {

        while(true){

            if (stopProcess)
                break;

            try {
                System.out.println("Total remaning time:" + (totalDuration - (System.currentTimeMillis() - startTime)));
                if (System.currentTimeMillis() - startTime >= totalDuration) {
                    warehouse.stopLoader();
                    stopProcess = true;
                }

                Thread.sleep(5000); //check for every 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
