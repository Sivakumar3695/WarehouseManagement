package com.application.warehouse;

import java.util.*;
import java.util.stream.Collectors;

public class Client {

    public static void main(String args[]) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        int nLoader = Integer.valueOf(scanner.nextLine());
        int nPacker = Integer.valueOf(scanner.nextLine());
        String threasholds = scanner.nextLine();
        String thresholdStr[] = threasholds.split(",");
        List<Integer> threshold = Arrays.stream(thresholdStr).map(x -> Integer.valueOf(x)).collect(Collectors.toList());
        int totalDuration = Integer.valueOf(scanner.nextLine());

        Warehouse warehouse = new Warehouse(nLoader, nPacker, totalDuration, threshold);
        warehouse.start();

        int retryCount = 0;
        while (true)
        {

            Thread.sleep(10000);
            if (warehouse.stock.isEmpty()) {
                retryCount++;
                if (retryCount >= 4) break;
                System.out.println("Done for the day! Thank you");
                continue;
            }
            retryCount = 0;
        }
    }

}
