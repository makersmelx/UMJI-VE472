package com.ve472.l1;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Hall {
    public String name;
    public String movie;
    public List<List<Boolean>> seats = new ArrayList<>();

    public Hall(String configFilePath) throws FileNotFoundException {
        initFromConfigFile(configFilePath);
    }

    public Boolean bookTicket(Integer ticketNumber, String customer) {
        if (seats.isEmpty() || ticketNumber > seats.get(0).size()) {
            return false;
        }
        List<int[]> solutions = new ArrayList<>();
        for (int i = 0; i < seats.size(); i++) {
            List<Boolean> row = seats.get(i);
            for (int j = 0; j < row.size() - ticketNumber + 1; j++) {
                boolean canBook = true;
                for (int k = 0; k < ticketNumber; k++) {
                    canBook = row.get(j + k);
                    if (!canBook) {
                        break;
                    }
                }
                if (canBook) {
                    int[] tmp = new int[2];
                    tmp[0] = i;
                    tmp[1] = j;
                    solutions.add(tmp);
                }
            }
        }
        int bestSolution = -1;
        float bestDistance = seats.size() * seats.size() + seats.get(0).size() * seats.get(0).size();
        for (int i = 0; i < solutions.size(); i++) {
            float rowDistance = ((seats.size() - 1) - solutions.get(i)[0]);
            float columnDistance = ((solutions.get(i)[1] + (float) (ticketNumber + 1) / 2) - ((float) (seats.get(0).size() + 1) / 2));
            float distance = rowDistance * rowDistance + columnDistance * columnDistance;
            if (distance < bestDistance) {
                bestSolution = i;
                bestDistance = distance;
            } else if (distance == bestDistance) {
                if (solutions.get(i)[0] > solutions.get(bestSolution)[0]) {
                    bestSolution = i;
                    bestDistance = distance;
                } else if (solutions.get(i)[0] == solutions.get(bestSolution)[0]) {
                    if (solutions.get(i)[1] < solutions.get(bestSolution)[1]) {
                        if (customer.equals("Damien Burcin") && movie.equals("Avengers: Endgame") && name.equals("orange")) {
                            System.out.println("=============");
                            System.out.println(Arrays.toString(solutions.get(i)));
                            System.out.println(Arrays.toString(solutions.get(bestSolution)));
                            System.out.println("=============");
                        }
                        bestSolution = i;
                        bestDistance = distance;
                    }
                }
            }
        }
        if (bestSolution >= 0) {
            StringBuilder outputText = new StringBuilder();
            for (int i = 0; i < ticketNumber; i++) {
                Integer actualRow = i + solutions.get(bestSolution)[1] + 1;
                seats.get(solutions.get(bestSolution)[0]).set(solutions.get(bestSolution)[1] + i, false);
                outputText.append(",").append(actualRow);
            }
            System.out.println(customer + "," + movie + "," + name + "," + (solutions.get(bestSolution)[0] + 1) + outputText);
            return true;
        } else {
            return false;
        }
    }

    private void initFromConfigFile(String configFilePath) throws FileNotFoundException {
        InputStream fileInput = new FileInputStream(configFilePath);
        Scanner scanner = new Scanner(fileInput);
        name = scanner.nextLine();
        movie = scanner.nextLine();
        int rowCount = 0;
        while (scanner.hasNextLine()) {
            seats.add(new ArrayList<>());
            String nextRow = scanner.nextLine();
            String[] nextRowSplit = nextRow.split(" ");
            for (String s : nextRowSplit) {
                seats.get(rowCount).add(s.equals("1"));
            }
            rowCount++;
        }
    }
}
