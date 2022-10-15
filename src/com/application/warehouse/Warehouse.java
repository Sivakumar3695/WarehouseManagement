package com.application.warehouse;

import java.util.*;

public class Warehouse {

    List<Loader> loaderList = new ArrayList<>();
    List<Packer> packerList = new ArrayList<>();
    Supervisor supervisor;
    Queue<UUID> stock = new LinkedList<>();

    public Warehouse(int nLoader, int nPacker, int totalDuration, List<Integer> threshold)
    {
        supervisor = new Supervisor(this, 1, threshold, totalDuration);

        for (int idx = 0; idx < nLoader; idx++)
        {
            loaderList.add(new Loader(this, idx+1));
        }

        for (int idx = 0; idx < nPacker; idx++)
        {
            packerList.add(new Packer(this, idx+1));
        }
    }

    public void pushToQueue(UUID uuid)
    {
        stock.offer(uuid);
        int nPackersToAdd = supervisor.checkThreshold(stock.size());

        if (nPackersToAdd == -1) return;

        for (int idx = 0 ; idx < nPackersToAdd; idx++)
        {
            Packer packer = new Packer(this, packerList.size()+idx);
            packerList.add(packer);
            new Thread(packer).start();
        }
    }

    public UUID getItemFromStock()
    {
        System.out.println(this.stock.size());
        if (this.stock.isEmpty())
            return null;

        return this.stock.poll();
    }

    public void stopLoader()
    {
        System.out.println("Done for the day.Loading done.");
        for (Loader loader : loaderList)
            loader.setStopLoading(true);
        System.out.println("Remaining Stock size:" + stock.size());
    }

    public void start() throws InterruptedException {
        supervisor.start();
        for (Loader loader : this.loaderList) new Thread(loader).start();
        Thread.sleep(5000);
        for (Packer packer : this.packerList) new Thread(packer).start();
    }
}
