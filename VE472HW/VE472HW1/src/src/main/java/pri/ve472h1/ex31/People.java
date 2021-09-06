package pri.ve472h1.ex31;

public class People {
    public String name, firstName, email;

    public People(String row) {
        String[] info = row.split(",");
        firstName = info[0];
        name = info[1];
        email = info[2];
    }

    public String toFileString() {
        return name +
                "," +
                firstName +
                "," +
                email;
    }
}
