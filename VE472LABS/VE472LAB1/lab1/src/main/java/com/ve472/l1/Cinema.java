package com.ve472.l1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Cinema {
    private HashMap<String, Hall> halls = new HashMap<String, Hall>();
    private HashMap<String, List<Hall>> hallsOfMovie = new HashMap<String, List<Hall>>();

    public Cinema(String configDir) throws FileNotFoundException {
        initHalls(configDir);
    }

    private void initHalls(String configDir) throws FileNotFoundException {
        File dir = new File(configDir);
        if (!dir.isDirectory()) {
            System.exit(2);
        }
        for (String filename : Objects.requireNonNull(dir.list())) {
            Hall newHall = new Hall(configDir + "/" + filename);
            halls.put(newHall.name, newHall);
            List<Hall> hallsOfThisMovie = hallsOfMovie.getOrDefault(newHall.movie, new ArrayList<>());
            hallsOfThisMovie.add(newHall);
            hallsOfThisMovie.sort(Comparator.comparing(o -> (o.name)));
            hallsOfMovie.putIfAbsent(newHall.movie, hallsOfThisMovie);
        }
    }

    public void handleQuery(String queryFile) throws FileNotFoundException {
        InputStream queryFileInput = new FileInputStream(queryFile);
        Scanner scanner = new Scanner(queryFileInput);
        while (scanner.hasNextLine()) {
            String[] info = (scanner.nextLine()).split(", ");
            String customer = info[0];
            String movie = info[1];
            Integer ticketNumber = Integer.parseInt(info[2]);
            // todo: book ticket
            boolean booked = false;
            if (hallsOfMovie.containsKey(movie)) {
                List<Hall> hallOfThisMovie = hallsOfMovie.get(movie);
                for (Hall current : hallOfThisMovie) {
                    if (current.bookTicket(ticketNumber, customer)) {
                        booked = true;
                        break;
                    }
                }
                if (!booked) {
                    System.out.println(customer + "," + movie);
                }
            } else {
                System.out.println(customer + "," + movie);
            }
        }
    }
}
