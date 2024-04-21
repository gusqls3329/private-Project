package com.example.beenproject;

import java.util.*;

public class Solution {

    public int[] solution(String[] record) {
        List<Integer> fifoCost = new ArrayList<>();
        List<Integer> lifoCost = new ArrayList<>();

        int fifoTotalCost = 0;
        int lifoTotalCost = 0;

        for (String rec : record) {
            String[] parts = rec.split(" ");
            int price = Integer.parseInt(parts[1]);
            int quantity = Integer.parseInt(parts[2]);

            if (parts[0].equals("P")) {
                // Purchase
                for (int i = 0; i < quantity; i++) {
                    fifoCost.add(price);
                    lifoCost.add(price);
                }
            } else {
                // Sale
                int fifoSaleCost = 0;
                int lifoSaleCost = 0;

                for (int i = 0; i < quantity; i++) {
                    fifoSaleCost += fifoCost.remove(0);
                    lifoSaleCost += lifoCost.remove(lifoCost.size() - 1);
                }

                fifoTotalCost += fifoSaleCost;
                lifoTotalCost += lifoSaleCost;
            }
        }

        return new int[] {fifoTotalCost, lifoTotalCost};
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] record = {"P 300 6", "P 500 3", "'S 1000 4", "P 600 1", "S 1200 2"};
        int[] result = solution.solution(record);
        System.out.println("선입선출법 매출원가: " + result[0]);
        System.out.println("후입선출법 매출원가: " + result[1]);
        System.out.println(result);
    }
}