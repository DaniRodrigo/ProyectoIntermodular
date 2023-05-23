package org.movieProyect.Repository.MySqlImpl;

import org.movieProyect.Conectors.MySQLConector;
import org.movieProyect.ModelDAO.MovieDAO;
import org.movieProyect.Repository.interfaces.IMovieDao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.movieProyect.mappers.MovieEntityToDAO.mapper;

/**
 * Clase que representa la implementación de movie y usa el conector para comunicarse con la BBDD.
 * Obliga al uso de métodos definidos en la interfaz IMovieDAO, que serán desarrollados aqui.
 */
public class MySqlMovieImpl implements IMovieDao {

    //Lista de atributos

    MySQLConector conector;  //Objeto de la clase MySQLConector

    /**
     * Constructor de la clase MySqlMovieImpl.
     * Usamos el tipo de la clase MySQLConector
     */
    public MySqlMovieImpl() {
        this.conector = new MySQLConector();
    }

    /**
     * Metodo para obtener el listado de peliculas:
     * <p>
     * Se crea una lista de peliculas vacia, se abre la conexión,
     * introducimos la consulta SQL, va mapeando el resultado en la lista creada o arroja una excepcion en caso de fallo,
     * para finalmente cerrar la conexion. Nos devuelve la lista de peliculas.
     */
    @Override
    public List<MovieDAO> getMovieList() {

        List<MovieDAO> movieList = new ArrayList<>();

        this.conector.openConection();

        ResultSet rs = this.conector.executeQuery("SELECT * FROM movie");

        while (true) {
            try {
                if (!rs.next()) break;
                movieList.add(mapper(rs));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        this.conector.closeConexion();

        return movieList;
    }

    /**
     * Metodo para obtener una pelicula según su id:
     * <p>
     * Se crea un objeto de la clase MovieDAO inicializada nula para arrojar el resultado
     * de la consulta SQL, si no, devuelve una excepción la conexion.
     */

    @Override
    public MovieDAO getMovie(int id) {
        MovieDAO movie = null;

        conector.openConection();

        ResultSet rs = conector.executeQuery("SELECT * FROM movie WHERE idMovie = " + id);

        try {
            if (rs.next()) {
                movie = mapper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        conector.closeConexion();

        return movie;
    }

    @Override
    public MovieDAO getMovieByTitle(String title) {

        MovieDAO movie = null;

        conector.openConection();

        ResultSet rs = conector.executeQuery("SELECT * FROM movie WHERE tittle like '%"+ title +"%'");
        try {
            if (rs.next()) {
                movie = mapper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        conector.closeConexion();

        return movie;
    }

    /**
     * Metodo para modificar una pelicula previamente creado a partir de su objeto.
     */

    @Override
    public MovieDAO updateMovie(MovieDAO movie) {

        MovieDAO updatedMovie;
        conector.openConection();

        String query ="UPDATE movie SET " +
                "tittle = '" + movie.getTitle() + "', " +
                "runningTime = '" + movie.getRunningTime() + "', " +
                "resume = '" + movie.getResume() + "' " +
                "WHERE idMovie = " + movie.getIdMovie();

        conector.executeUpdate(query);

        updatedMovie = getMovie(movie.getIdMovie());

        conector.closeConexion();

        return updatedMovie;
    }

    /**
     * Metodo para eliminar una pelicula según su id:
     * <p>
     * Se abre la conexion, se crea un mecanismo de control booleano que introduce la
     * consulta SQL y devuelve true/false según se haya podido ejecutar.
     */

    @Override
    public boolean deleteMovie(int id) {
        conector.openConection();

        boolean result = conector.executeUpdate("DELETE FROM movie where idMovie = " + id);

        conector.closeConexion();

        return result;
    }

    /**
     * Metodo para crear una pelicula:
     * <p>
     * Es necesario un objeto de la clase MovieDAO creado previamente para usar este metodo.
     * <p>
     * También se usa un mecanismo de control booleano para verificar que se ha ejecutado correctamente.
     */

    @Override
    public int createMovie(MovieDAO movie) {
        int id = -1;
        conector.openConection();

        String sentence = "INSERT INTO movie (tittle, movie.release, runningTime, movie.resume) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement newMovie = conector.getCon().prepareStatement(sentence);

            newMovie.setString(1, movie.getTitle());
            newMovie.setDate(2, new Date(movie.getRelease().getTime()));
            newMovie.setInt(3, movie.getRunningTime());
            newMovie.setString(4, movie.getResume());

            int result = newMovie.executeUpdate();
            if(result > 0) {
                try (ResultSet rs = newMovie.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        conector.closeConexion();
        return id;
    }
}
