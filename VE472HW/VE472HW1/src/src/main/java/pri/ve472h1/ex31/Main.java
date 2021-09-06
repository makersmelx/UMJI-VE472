package pri.ve472h1.ex31;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream textFile = new FileInputStream("cases/shili.txt");
        Scanner scanner = new Scanner(textFile);
        List<People> group = new ArrayList<People>();
        while (scanner.hasNextLine()) {
            String row = scanner.nextLine();
            group.add(new People(row));
        }
        Collections.shuffle(group);
        for (People people : group) {
            System.out.println(people.toFileString());
        }
    }
}
