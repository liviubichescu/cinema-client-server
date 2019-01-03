package ro.ubb.socketCinema.client.ui;

import ro.ubb.socketCinema.client.service.ServiceMovieClientImpl;
import ro.ubb.socketCinema.common.domain.Movie;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * author: liviu
 */
public class ClientConsole {
    private ServiceMovieClientImpl serviceMovieClient;

    public ClientConsole(ServiceMovieClientImpl serviceMovieClient) {
        this.serviceMovieClient = serviceMovieClient;
    }

    private void printMeniu() {
        System.out.println();
        System.out.println("1. Adauga film");
        System.out.println("2. Sterge film");
        System.out.println("3. Update film");
        System.out.println("4. Afiseaza toate filmele");
        System.out.println("0. Exit");
    }

    public void meniu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMeniu();
            int choice = scanner.nextInt();
            if (choice == 0)
                break;
            switch (choice) {
                case 1:
                    adaugaFilm();
                    break;
                case 2:
                    stergeFilm();
                    break;
                case 3:
                    updateFilm();
                    break;
                case 4:
                    getAllMoviesConsole();
                    break;
                default:
                    System.out.println("This option is not yet implemented");
            }
        }
    }

    private void getAllMoviesConsole() {

        Future<List<Movie>> result = serviceMovieClient.getAllMovies();

        try {
            List<Movie> movieList = result.get();
            System.out.println("List of movies: ");
            for (Movie s : movieList) {
                System.out.println(s);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }


    private void adaugaFilm() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti ID-ul filmului: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Introduceti Titlul: ");
        String titlu = scanner.nextLine();

        System.out.print("Introduceti Directorul: ");
        String director = scanner.nextLine();

        System.out.print("Introduceti Producer: ");
        String producer = scanner.nextLine();

        System.out.print("Introduceti anul lansarii: ");
        int an = scanner.nextInt();

        System.out.print("Introduceti bugetul:");
        double buget = scanner.nextDouble();

        Movie movie = new Movie(id, titlu, director, producer, an, buget);

        Future<?> result = serviceMovieClient.addMovie(movie);
        Object res;
        try {
            res = result.get();
            System.out.println(res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void stergeFilm() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti ID-ul filmului: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Future<?> result = serviceMovieClient.removeMovie(id);
        Object res;
        try {
            res = result.get();
            System.out.println(res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void updateFilm() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceti ID-ul filmului pe care doriti sa il modificati: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Introduceti Titlul: ");
        String titlu = scanner.nextLine();

        System.out.print("Introduceti Directorul: ");
        String director = scanner.nextLine();

        System.out.print("Introduceti Producer: ");
        String producer = scanner.nextLine();

        System.out.print("Introduceti anul lansarii: ");
        int an = scanner.nextInt();

        System.out.print("Introduceti bugetul:");
        double buget = scanner.nextDouble();

        Movie movie = new Movie(id, titlu, director, producer, an, buget);

        Future<?> result = serviceMovieClient.updateMovie(movie);
        Object res;
        try {
            res = result.get();
            System.out.println(res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
