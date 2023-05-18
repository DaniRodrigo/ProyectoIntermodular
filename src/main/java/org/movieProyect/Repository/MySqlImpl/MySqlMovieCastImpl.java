package org.movieProyect.Repository.MySqlImpl;

import org.movieProyect.Conectors.MySQLConector;
import org.movieProyect.ModelDAO.ActorDAO;
import org.movieProyect.ModelDAO.MovieDAO;
import org.movieProyect.Repository.interfaces.IActorDao;
import org.movieProyect.Repository.interfaces.IMovieCastDao;
import org.movieProyect.Repository.interfaces.IMovieDao;
import org.movieProyect.mappers.ActorEntityToDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.movieProyect.mappers.MovieEntityToDAO.mapper;

/**
 * Clase que representa la implementación de MovieCast y usa el conector para comunicarse con la BBDD.
 * Obliga al uso de métodos definidos en la interfaz IActorDAO, que serán desarrollados aqui.
 */
public class MySqlMovieCastImpl implements IMovieCastDao {

    //Lista de atributos

    MySQLConector conector;   //Objeto de la clase MySQLConector

    /**
     * Constructor de la clase MySqlMovieCastImpl.
     * Usamos el tipo de la clase MySQLConector
     */


    public MySqlMovieCastImpl(MySQLConector conector) {
        this.conector = conector;
    }

    public MySqlMovieCastImpl() {
        this.conector = new MySQLConector();
    }

    /**
     * Metodo para obtener el listado de peliculas realizadas por un actor a partir de su id:
     * <p>
     * Se crea una lista de peliculas vacia, se abre la conexión,
     * introducimos la consulta SQL, va mapeando el resultado en la lista creada o arroja una excepcion en caso de fallo,
     * para finalmente cerrar la conexion. Nos devuelve la lista de peliculas realizadas por el actor.
     */
    @Override
    public List<MovieDAO> getMoviesFromActor(int actorId) {

        List<MovieDAO> movieList = new ArrayList<>();
        ResultSet rs;

        String sql = "SELECT * FROM new_schema.movie " +
                "WHERE idMovie IN " +
                "(SELECT idMovie from new_schema.moviecast " +
                "where idActor = ? )";

        try {
            conector.openConection();
            PreparedStatement sqlPrepared = conector.getCon().prepareStatement(sql);
            sqlPrepared.setInt(1, actorId);
            rs = sqlPrepared.executeQuery();
            while (true) {
                try {
                    if (!rs.next()) break;
                    movieList.add(mapper(rs));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            conector.openConection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movieList;
    }

    /**
     * Metodo para obtener el listado de actores protaginistas en una pelicula a partir de su id:
     * <p>
     * Se crea una lista de actores vacia, se abre la conexión,
     * introducimos la consulta SQL, va mapeando el resultado en la lista creada o arroja una excepcion en caso de fallo,
     * para finalmente cerrar la conexion. Nos devuelve la lista de actores que protagonizan una pelicula.
     */
    @Override
    public List<ActorDAO> getActorsFromMovie(int movieId) {
        conector.openConection();

        List<ActorDAO> actorList = new ArrayList<>();
        ResultSet rs;

        String sql = "SELECT * FROM new_schema.actor " +
                "WHERE idActor IN " +
                "(SELECT idActor from new_schema.moviecast " +
                "where idMovie = ? )";

        try {
            PreparedStatement sqlPrepared = conector.getCon().prepareStatement(sql);
            sqlPrepared.setInt(1, movieId);
            rs = sqlPrepared.executeQuery();
            while (true) {
                try {
                    if (!rs.next()) break;
                    actorList.add(ActorEntityToDAO.mapper(rs));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.conector.closeConexion();

        return actorList;
    }

    /**
     * Metodo para obtener el listado de peliculas y sus actores:
     */
    public List<String> getMoviesWithActors() {

        List<String> resultList = new ArrayList<>();
        String message = "";

        IMovieDao movieImpl = new MySqlMovieImpl();

        for (MovieDAO movie : movieImpl.getMovieList()) {
            message = "La pelicula " + movie.getTitle() + " tiene los siguientes actores: ";

            List<ActorDAO> actors = getActorsFromMovie(movie.getIdMovie());

            for (ActorDAO actor : actors) {

                message += "" + actor.getName() + " " + actor.getLastName() + ", ";
            }
            resultList.add(message);
        }
        ;

        return resultList;
    }

    /**
     * Metodo para obtener el listado de actores y sus peliculas:
     */
    public List<String> getActorsWithMovies() {

        List<String> resultList = new ArrayList<>();
        String message = "";

        IActorDao actorImpl = new MySqlActorImpl();

        for (ActorDAO actor : actorImpl.getActorList()) {
            message = "El actor " + actor.getName() + " " + actor.getLastName() + " ha participado en las siguientes películas: ";

            List<MovieDAO> movies = getMoviesFromActor(actor.getIdActor());

            for (MovieDAO movie : movies) {

                message += "" + movie.getTitle() + ", ";
            }
            resultList.add(message);
        }
        ;

        return resultList;
    }
}
