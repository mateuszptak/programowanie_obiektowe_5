// Co musialem zmienic w kodzie aby moc wprowadzic funkcjonalnosci:
// w klasie Biblioteka musialem zmienic modyfikator dostepu z private na public aby miec mozliwosc dodania funkcji


package zadanie4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.*;
import java.time.LocalDate;

class Recenzja implements Serializable {
    private final String komentarz;
    private final int ocena; // Ocena w skali od 1 do 10
    private final LocalDate dataDodania;

    public Recenzja(String komentarz, int ocena) {
        this.komentarz = komentarz;
        this.ocena = ocena;
        this.dataDodania = LocalDate.now();
    }

    public String getKomentarz() {
        return komentarz;
    }

    public int getOcena() {
        return ocena;
    }

    public LocalDate getDataDodania() {
        return dataDodania;
    }
}

class Autor implements Serializable {
    private String imie;
    private String nazwisko;

    public Autor(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String pelneImie() {
        return imie + " " + nazwisko;
    }
}


class Ksiazka implements Serializable {
    private String tytul;
    private Autor autor;
    private boolean dostepna;
    private List<Recenzja> recenzje;
    private static int ostatniId = 1;
    private int id;
    private int rokWydania;

    public Ksiazka(String tytul, Autor autor, int rokWydania) {
        this.tytul = tytul;
        this.autor = autor;
        this.dostepna = true;
        this.recenzje = new ArrayList<>();
        this.id = ostatniId++;
        this.rokWydania = rokWydania;

    }

    // metoda do obliczania średniej oceny książki
    public double obliczSredniaOcene() {
        if (recenzje.isEmpty()) {
            return 0;
        }
        int sumaOcen = 0;
        for (Recenzja recenzja : recenzje) {
            sumaOcen += recenzja.getOcena();
        }
        return sumaOcen / (double) recenzje.size();
    }

    // metoda do wyświetlania recenzji książki
    public void wyswietlRecenzje() {
        System.out.println("Recenzje dla książki \"" + tytul + "\":");
        if (recenzje.isEmpty()) {
            System.out.println("Brak recenzji.");
        } else {
            for (Recenzja recenzja : recenzje) {
                System.out.println("Komentarz: " + recenzja.getKomentarz() + " (Ocena: " + recenzja.getOcena() +
                        ", Data: " + recenzja.getDataDodania() + ")");
            }
        }
    }

    public String getTytul() {
        return tytul;
    }

    public Autor getAutor() {
        return autor;
    }

    public boolean isDostepna() {
        return dostepna;
    }

    public List<Recenzja> getRecenzje() {
        return recenzje;
    }

    public void dodajRecenzje(Recenzja recenzja) {
        recenzje.add(recenzja);
    }

    public void wypozycz() {
        dostepna = false;
    }

    public void oddaj() {
        dostepna = true;
    }
// umowzliwia aktualizacje ostatniego ID po wczytaniu pliku
    public static void ustawOstatniId(int id) {
        ostatniId = id;
    }
    public int getId() {
        return id;
    }
    public int getRokWydania() {
        return rokWydania;
    }


}

class Biblioteka implements Serializable {
    public List<Ksiazka> ksiazki;
    public List<Autor> autorzy;
    public List<HistoriaWpisu> historia;

    public Biblioteka() {
        ksiazki = new ArrayList<>();
        autorzy = new ArrayList<>();
        historia = new ArrayList<>();
    }

    public void dodajKsiazke(String tytul, Autor autor, int rokWydania) {
        Ksiazka ksiazka = new Ksiazka(tytul, autor, rokWydania);
        ksiazki.add(ksiazka);
    }

    public void dodajAutora(Autor autor) {
        autorzy.add(autor);
    }

    public void listaAutorow() {
        System.out.println("Lista Autorów:");
        for (int i = 0; i < autorzy.size(); i++) {
            Autor autor = autorzy.get(i);
            System.out.println((i + 1) + ". " + autor.pelneImie());
        }
    }

    public Autor wybierzAutora(int numer) {
        if (numer > 0 && numer <= autorzy.size()) {
            return autorzy.get(numer - 1);
        }
        return null;
    }

    public void listaKsiazek() {
        System.out.println("Dostępne Książki:");
        for (Ksiazka ksiazka : ksiazki) {
            if (ksiazka.isDostepna()) {
                System.out.println("ID: " + ksiazka.getId() + ", Tytuł: " + ksiazka.getTytul() + ", Autor: " + ksiazka.getAutor().pelneImie() + ", Rok wydania: " + ksiazka.getRokWydania());
            }
        }
    }

    public void wypozyczKsiazke(String tytul) {
        for (Ksiazka ksiazka : ksiazki) {
            if (ksiazka.getTytul().equals(tytul) && ksiazka.isDostepna()) {
                ksiazka.wypozycz();
                historia.add(new HistoriaWpisu("Wypożyczono: " + tytul));

                System.out.println("Wypożyczono: " + tytul);
                return;
            }
        }
        System.out.println("Książka niedostępna lub nie istnieje.");
    }

    public void oddajKsiazke(String tytul) {
        for (Ksiazka ksiazka : ksiazki) {
            if (ksiazka.getTytul().equals(tytul) && !ksiazka.isDostepna()) {
                ksiazka.oddaj();
                historia.add(new HistoriaWpisu("Oddano: " + tytul));
                System.out.println("Oddano: " + tytul);
                System.out.print("Podaj komentarz (opcjonalnie): ");
                Scanner skaner = new Scanner(System.in);
                String komentarz = skaner.nextLine();
                int ocena = -1;
                while (ocena < 1 || ocena > 10) {
                    System.out.print("Podaj ocenę od 1 do 10: ");
                    ocena = skaner.nextInt();
                }
                Recenzja recenzja = new Recenzja(komentarz, ocena);
                ksiazka.dodajRecenzje(recenzja);
                System.out.println("Dodano recenzję.");
                return;
            }
        }
        System.out.println("Książka nie znaleziona w Twoich wypożyczonych książkach.");
    }

    public void listaWypozyczonychKsiazek() {
        System.out.println("Wypożyczone Książki:");
        for (Ksiazka ksiazka : ksiazki) {
            if (!ksiazka.isDostepna()) {
                System.out.println("Tytuł: " + ksiazka.getTytul() + ", Autor: " + ksiazka.getAutor().pelneImie());
                List<Recenzja> recenzje = ksiazka.getRecenzje();
                for (Recenzja recenzja : recenzje) {
                    System.out.println("Recenzja: " + recenzja.getKomentarz() + " (Ocena: " + recenzja.getOcena() +
                            ", Data dodania: " + recenzja.getDataDodania());
                }
            }
        }
    }

    public void wyswietlHistorie() {
        System.out.println("Historia:");
        for (HistoriaWpisu wpis : historia) {
            System.out.println(wpis);
        }
    }

    public void filtrujKsiazkiPoAutorze(Autor autor) {
        System.out.println("Dostępne książki autora " + autor.pelneImie() + ":");
        boolean znaleziono = false;
        for (Ksiazka ksiazka : ksiazki) {
            if (ksiazka.getAutor().equals(autor) && ksiazka.isDostepna()) {
                System.out.println("Tytuł: " + ksiazka.getTytul());
                znaleziono = true;
            }
        }
        if (!znaleziono) {
            System.out.println("Brak dostępnych książek tego autora.");
        }
    }

  //  Nowa metoda do wyświetlania średniej oceny książki i recenzji
    public void wyswietlSredniaOceneIRecenzje(String tytul) {
        for (Ksiazka ksiazka : ksiazki) {
            if (ksiazka.getTytul().equals(tytul)) {
                double sredniaOcena = ksiazka.obliczSredniaOcene();
                System.out.println("Średnia ocena książki \"" + tytul + "\": " + sredniaOcena);
                ksiazka.wyswietlRecenzje();
                return;
            }
        }
        System.out.println("Książka o tytule \"" + tytul + "\" nie została znaleziona.");
    }

    // metoda dodajaca recenzje do ksiazki
    public void dodajRecenzjeDoKsiazki(String tytul) {
        for (Ksiazka ksiazka : ksiazki) {
            if (ksiazka.getTytul().equalsIgnoreCase(tytul)) {
                Scanner skaner = new Scanner(System.in);
                System.out.print("Podaj komentarz: ");
                String komentarz = skaner.nextLine();
                int ocena = -1;
                while (ocena < 1 || ocena > 10) {
                    System.out.print("Podaj ocenę (1-10): ");
                    if (skaner.hasNextInt()) {
                        ocena = skaner.nextInt();
                        skaner.nextLine(); // usuń nową linię po liczbie
                    } else {
                        System.out.println("To nie jest liczba. Spróbuj ponownie.");
                        skaner.nextLine();
                    }
                }
                Recenzja recenzja = new Recenzja(komentarz, ocena);
                ksiazka.dodajRecenzje(recenzja);
                historia.add(new HistoriaWpisu("Dodano recenzję do książki: " + tytul));
                System.out.println("Recenzja została dodana.");
                return;
            }
        }
        System.out.println("Nie ma ksiazki z tym tytulem");
    }


}

class HistoriaWpisu implements Serializable {
    private String opis;
    private LocalDateTime czasOperacji;

    public HistoriaWpisu(String opis) {
        this.opis = opis;
        this.czasOperacji = LocalDateTime.now();
    }

    public String getOpis() {
        return opis;
    }

    public LocalDateTime getCzasOperacji() {
        return czasOperacji;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + czasOperacji.format(formatter) + "] " + opis;
    }
}

public class ProgramBiblioteczny {
    public static void main(String[] args) {
        Biblioteka biblioteka = wczytajBiblioteke();

        Scanner skaner = new Scanner(System.in);
        boolean dziala = true;

        while (dziala) {
            System.out.println("\nMenu Biblioteki:");
            System.out.println("1. Dodaj Książkę");
            System.out.println("2. Dodaj Autora");
            System.out.println("3. Lista Dostępnych Książek");
            System.out.println("4. Wypożycz Książkę");
            System.out.println("5. Oddaj Książkę");
            System.out.println("6. Lista Wypożyczonych Książek");
            System.out.println("7. Lista Autorów");
            System.out.println("8. Historia");
            System.out.println("9. Filtrowanie książek wedlug autoa");
            System.out.println("10. Wystaw ocene i recenzje ksiazki");
            System.out.println("11. Wyświetl średnią ocenę i recenzje książki");
            System.out.println("12. Zapisz i Wyjdź");

            int wybor = skaner.nextInt();
            skaner.nextLine(); // Skonsumuj znak nowej linii

            switch (wybor) {
                case 1:
                    System.out.print("Podaj tytuł książki: ");
                    String tytul = skaner.nextLine();
                    biblioteka.listaAutorow();
                    System.out.print("Wybierz numer autora lub dodaj nowego autora wpisując nowy numer: ");
                    int numerAutora = skaner.nextInt();
                    skaner.nextLine();
                    Autor autor;
                    if (numerAutora > 0 && numerAutora <= biblioteka.autorzy.size()) {
                        autor = biblioteka.wybierzAutora(numerAutora);
                    } else {
                        System.out.print("Podaj imię autora: ");
                        String imieAutora = skaner.nextLine();
                        System.out.print("Podaj nazwisko autora: ");
                        String nazwiskoAutora = skaner.nextLine();
                        autor = new Autor(imieAutora, nazwiskoAutora);
                        biblioteka.dodajAutora(autor);
                    }

                    System.out.print("Podaj rok wydania książki: ");
                    int rok = skaner.nextInt();
                    skaner.nextLine();

                    biblioteka.dodajKsiazke(tytul, autor, rok);
                    break;

                case 2:
                    System.out.print("Podaj imię autora: ");
                    String imieAutora = skaner.nextLine();
                    System.out.print("Podaj nazwisko autora: ");
                    String nazwiskoAutora = skaner.nextLine();
                    Autor nowyAutor = new Autor(imieAutora, nazwiskoAutora);
                    biblioteka.dodajAutora(nowyAutor);
                    break;
                case 3:
                    biblioteka.listaKsiazek();
                    break;
                case 4:
                    System.out.print("Podaj tytuł książki do wypożyczenia: ");
                    String wypozyczTytul = skaner.nextLine();
                    biblioteka.wypozyczKsiazke(wypozyczTytul);
                    break;
                case 5:
                    System.out.print("Podaj tytuł książki do zwrotu: ");
                    String oddajTytul = skaner.nextLine();
                    biblioteka.oddajKsiazke(oddajTytul);
                    break;
                case 6:
                    biblioteka.listaWypozyczonychKsiazek();
                    break;
                case 7:
                    biblioteka.listaAutorow();
                    break;
                case 8:
                    biblioteka.wyswietlHistorie();
                    break;

                // Filtrowanie książek według autora
                case 9:
                    System.out.println("Wybierz autora, którego książki chcesz zobaczyć:");
                    biblioteka.listaAutorow();
                    int numerFiltruAutora = skaner.nextInt();
                    skaner.nextLine(); // Skonsumuj znak nowej linii
                    Autor autorFiltru = biblioteka.wybierzAutora(numerFiltruAutora);
                    if (autorFiltru != null) {
                        biblioteka.filtrujKsiazkiPoAutorze(autorFiltru);
                    } else {
                        System.out.println("Nieprawidłowy wybór autora.");
                    }
                    break;
                case 10:
                    System.out.print("Podaj tytuł książki, do której chcesz dodać recenzję: ");
                    String tytulRecenzji = skaner.nextLine();
                    biblioteka.dodajRecenzjeDoKsiazki(tytulRecenzji);
                    break;

                case 11:
                    System.out.println("Podaj tytuł ksiązki aby zobaczyć recenzje oraz srednia ocene:");
                    String tytulKsiazki = skaner.nextLine();
                    biblioteka.wyswietlSredniaOceneIRecenzje(tytulKsiazki);
                    break;
                case 12:
                    dziala = false;
                    break;
            }
        }

        zapiszBiblioteke(biblioteka);
        System.out.println("Dane biblioteki zapisane. Do widzenia!");
    }

    private static Biblioteka wczytajBiblioteke() {
        Biblioteka biblioteka = new Biblioteka();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("biblioteka.dat"))) {
            biblioteka = (Biblioteka) in.readObject();
            System.out.println("Dane biblioteki wczytane pomyślnie.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nie znaleziono zapisanych danych biblioteki. Rozpoczynamy z pustą biblioteką.");
        }
        return biblioteka;
    }

    private static void zapiszBiblioteke(Biblioteka biblioteka) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("biblioteka.dat"))) {
            out.writeObject(biblioteka);
            System.out.println("Dane biblioteki zostały zapisane.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

