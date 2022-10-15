package com.application.warehouse;

import java.util.*;
import java.util.stream.Collectors;

public class Client {

    public static void main(String args[]) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of loaders:");
        int nLoader = Integer.valueOf(scanner.nextLine());

        System.out.print("Enter the number of packers:");
        int nPacker = Integer.valueOf(scanner.nextLine());

        System.out.print("Enter the threshold limit intervals (comma separated):");
        String threasholds = scanner.nextLine();
        String thresholdStr[] = threasholds.split(",");
        List<Integer> threshold = Arrays.stream(thresholdStr).map(x -> Integer.valueOf(x)).collect(Collectors.toList());

        System.out.print("Enter the total duration of the process (in minutes):");
        int totalDuration = Integer.valueOf(scanner.nextLine());

        Warehouse warehouse = new Warehouse(nLoader, nPacker, totalDuration, threshold);
        warehouse.start();

        int retryCount = 0;
        while (true)
        {

            Thread.sleep(1000);
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
