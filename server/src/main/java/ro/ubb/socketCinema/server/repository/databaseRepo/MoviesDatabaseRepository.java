package ro.ubb.socketCinema.server.repository.databaseRepo;

import ro.ubb.socketCinema.common.domain.Movie;
import ro.ubb.socketCinema.server.exceptions.DatabaseException;
import ro.ubb.socketCinema.server.repository.inMemoryRepo.InMemoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoviesDatabaseRepository extends InMemoryRepository<Long, Movie> {

    private static final String URL = "jdbc:postgresql://localhost:5432/cinema";
    private static final String USERNAME = System.getProperty("user");
    private static final String PASSWORD = System.getProperty("password");

    public MoviesDatabaseRepository() {

    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement("select * from movies");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                String producer = resultSet.getString("producer");
                int relesedyear = resultSet.getInt("relesedyear");
                double buget = resultSet.getDouble("buget");

                Movie movie = new Movie(id, title, director, producer, relesedyear, buget);

                movies.add(movie);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Finding all movies from database failed!" + e);
        }
        return movies;
    }

    @Override
    public Optional<Movie> save(Movie entity) {
        Optional<Movie> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToDatabase(entity);
        return Optional.empty();
    }

    private void saveToDatabase(Movie movie) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "insert into movies(id, title, director, producer, relesedyear, buget) " +
                             "values (?,?,?,?,?,?)");
        ) {
            statement.setLong(1, movie.getId());
            statement.setString(2, movie.getTitle());
            statement.setString(3, movie.getDirector());
            statement.setString(4, movie.getProducer());
            statement.setInt(5, movie.getRelesedYear());
            statement.setDouble(6, movie.getBuget());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Save movie to database failed!" + e);
        }
    }

    @Override
    public Optional<Movie> update(Movie movie) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "update movies set title=?, director=?, " +
                             "producer=?, relesedyear=?, buget=?  where id= ?")
        ) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirector());
            statement.setString(3, movie.getProducer());
            statement.setInt(4, movie.getRelesedYear());
            statement.setDouble(5, movie.getBuget());
            statement.setLong(6, movie.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Update movie in database failed! " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> delete(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("delete from movies where id = ?")
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Delete movie from database failed! " + e);
        }
        return Optional.empty();
    }


    public Optional<Movie> findOne(Long id) {
        Movie movie = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement("select * from movies where id = ?");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                String producer = resultSet.getString("producer");
                int relesedyear = resultSet.getInt("relesedyear");
                double buget = resultSet.getDouble("buget");

                movie = new Movie(id, title, director, producer, relesedyear, buget);

            }

        } catch (SQLException e) {
            throw new DatabaseException("Find one movie in database failed!" + e);
        }

        return Optional.ofNullable(movie);
    }

}
