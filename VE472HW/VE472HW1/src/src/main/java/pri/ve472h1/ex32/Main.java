package pri.ve472h1.ex32;

public class Main {
    public static void main(String[] args) {
        System.out.println("============================");
        System.out.println("Let's try driving Tesla.");
        tryACar(new Tesla());
        System.out.println("============================");
        System.out.println("Let's try driving Volkswagen.");
        System.out.println("============================");
        System.out.println("Demo");
        tryACar(new Volkswagen());
        Car modelY = new Tesla();
        modelY.brake();
        Tesla model3 = (Tesla) modelY;
        model3.brake();
    }

    public static void tryACar(Car newCar) {
        newCar.run();
        newCar.brake();
    }
}

abstract class Car {
    abstract void run();

    abstract void brake();
}

class Tesla extends Car {
    @Override
    void run() {
        System.out.println("Tesla go!");
    }

    @Override
    void brake() {
        System.out.println("Oops! There is no brake here. Your car wants to drink milk tea.");
    }
}

class Volkswagen extends Car {
    @Override
    void run() {
        System.out.println("Volkswagen go!");
    }

    @Override
    void brake() {
        System.out.println("Stopped. Watch your steps.");
    }
}