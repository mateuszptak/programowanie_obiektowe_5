// Wymyśl dowolny przykład interfejsu oraz jego implementacji.

public class zadanie2 {
    public static void main(String[] args) {

        // Tworzymy obiekt typu Gotuj, ale przypisujemy mu konkretną klasę tj.
        // Thermomix, Babcia, Mama
        Gotuj kucharz1 = new Thermomix();
        Gotuj kucharz2 = new Babcia();
        Gotuj kucharz3 = new Mama();

        // wywlouje metode gotujDanie(); dla wszyskitch obiektow
        kucharz1.gotujDanie("rosół");
        kucharz2.gotujDanie("pierogi ruskie");
        kucharz3.gotujDanie("schabowy");
    }
}

// Interfejs Gotuj definiuje metodę gotujDanie
// którą muszą zaimplementować wszystkie klasy, które go implementują
interface Gotuj {
    void gotujDanie(String danie);
}

// Klasa Thrmomix implementuje interfejs Gotuj
// czyli musi mieć metodę gotujDanie()
class Thermomix implements Gotuj {
    @Override
    public void gotujDanie(String danie) {
        System.out.println("Robot kuchenny automatycznie przygotowuje: " + danie);
    }
}

class Babcia implements Gotuj {
    @Override
    public void gotujDanie(String danie) {
        System.out.println("Wera gotuje: " + danie + " i dodaje sekretny składnik.");
    }
}

class Mama implements Gotuj {
    @Override
    public void gotujDanie(String danie) {
        System.out.println("Mamuska robi: " + danie + " i dodaje sekretny sosik.");
    }
}