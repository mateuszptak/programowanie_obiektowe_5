// Stwórz klasę abstrakcyjną Vehicle z polem name oraz abstrakcyjną metodą start().
// Następnie stwórz dwie klasy dziedziczące: Car i Bicycle, implementujące metodę start().
// Napisz program, który tworzy obiekty różnych klas i uruchamia je.


public class zadanie1 {
    public static void main(String[] args) {
        Vehicle car = new Car("Volvo");
        Vehicle bicycle = new Bicycle("Romecik");

        car.start();
        bicycle.start();
    }

}


// tworzenie klasy Vehicle z polem name i metoda start();
abstract class Vehicle {
    String name;

    public Vehicle(String name) {
        this.name = name;
    }

    public abstract void start();
}

// stworzenie klasy Car dziedziczacej z Vehicle
class Car extends Vehicle {
    public Car(String name) {
        super(name);
    }

    @Override
    public void start() {
        System.out.println(name + " przekręca kluczyk i odpala silnik.");
    }
}

// stworzenie klasy Bicycle dziedziczacej z Vehicle
class Bicycle extends Vehicle {
    public Bicycle(String name) {
        super(name);
    }

    @Override
    public void start() {
        System.out.println(name + " zaczyna jechać.");
    }
}

