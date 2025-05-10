// Stwórz klasę abstrakcyjną Vehicle z polem name oraz abstrakcyjną metodą start().
// Następnie stwórz dwie klasy dziedziczące: Car i Bicycle, implementujące metodę start().
// Napisz program, który tworzy obiekty różnych klas i uruchamia je.

public class Main {
    public static void main(String[] args) {
        Vehicle car = new Car("Volvo");
        Vehicle bicycle = new Bicycle("Romecik");

        car.start();
        bicycle.start();
    }
}


