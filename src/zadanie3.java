import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Klasa główna - Zadanie 3
class Zadanie3 {
    public static void main(String[] args) {
        List<Figura> figury = wczytajFiguryZPliku();
        for (Figura figura : figury) {
            System.out.println(figura);
            System.out.println("Pole: " + figura.obliczPole());
            System.out.println("Obwód: " + figura.obliczObwod());
            System.out.println();
        }
    }

    public static List<Figura> wczytajFiguryZPliku() {
        List<Figura> figury = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/dane.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 0) {
                    String figura = parts[0];
                    double[] parametry = new double[parts.length - 1];
                    for (int i = 1; i < parts.length; i++) {
                        parametry[i - 1] = Double.parseDouble(parts[i]);
                    }

                    Figura utworzonaFigura = FiguraFactory.utworzFigure(figura, parametry);
                    if (utworzonaFigura != null) {
                        figury.add(utworzonaFigura);
                    } else {
                        System.out.println("Nieprawidlowe dane: " + line);
                    }
                }
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

        return figury;
    }
}


// Interfejs wspólny dla wszystkich figur
interface Figura {
    double obliczPole();

    double obliczObwod();
}

// Klasa Trojkat
class Trojkat implements Figura {
    final private double a, b, c;

    public Trojkat(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double obliczPole() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double obliczObwod() {
        return a + b + c;
    }

    @Override
    public String toString() {
        return "Trojkat (" + a + ", " + b + ", " + c + ")";
    }
}

// Klasa Prostokat
class Prostokat implements Figura {
    final private double a, b;

    public Prostokat(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double obliczPole() {
        return a * b;
    }

    @Override
    public double obliczObwod() {
        return 2 * (a + b);
    }

    @Override
    public String toString() {
        return "Prostokat (" + a + ", " + b + ")";
    }
}

// Klasa Kwadrat
class Kwadrat implements Figura {
    final private double bok;

    public Kwadrat(double bok) {
        this.bok = bok;
    }

    @Override
    public double obliczPole() {
        return bok * bok;
    }

    @Override
    public double obliczObwod() {
        return 4 * bok;
    }

    @Override
    public String toString() {
        return "Kwadrat (" + bok + ")";
    }
}

// Klasa Kolo
class Kolo implements Figura {
    final private double r;

    public Kolo(double r) {
        this.r = r;
    }

    @Override
    public double obliczPole() {
        return Math.PI * r * r;
    }

    @Override
    public double obliczObwod() {
        return 2 * Math.PI * r;
    }

    @Override
    public String toString() {
        return "Kolo (" + r + ")";
    }
}

// Klasa fabryczna - tworzy figury
class FiguraFactory {
    public static Figura utworzFigure(String figura, double... parametry) {
        switch (figura) {
            case "Trojkat":
                if (parametry.length == 3) {
                    return new Trojkat(parametry[0], parametry[1], parametry[2]);
                }
                break;
            case "Prostokat":
                if (parametry.length == 2) {
                    return new Prostokat(parametry[0], parametry[1]);
                }
                break;
            case "Kwadrat":
                if (parametry.length == 1) {
                    return new Kwadrat(parametry[0]);
                }
                break;
            case "Kolo":
                if (parametry.length == 1) {
                    return new Kolo(parametry[0]);
                }
                break;
        }
        return null;
    }
}


