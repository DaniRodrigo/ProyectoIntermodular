package org.movieProyect.mappers;

import org.movieProyect.ModelDAO.ActorDAO;
import org.movieProyect.ModelDAO.MovieDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que contiene un metodo mapper.
 * El mapper se encarga de convertir un tipo de dato en otro.
 * En este caso, coge los datos de la BBDD y lo convierte al modelo de dato conveniente.
 */

public class MovieEntityToDAO {
    public static MovieDAO mapper(ResultSet rs) {
        MovieDAO movie = new MovieDAO();
        try {
            movie.setIdMovie(rs.getInt("idMovie"));
            movie.setTitle(rs.getString("tittle"));
            movie.setRelease(rs.getDate("release"));
            movie.setRunningTime(rs.getInt("runningTime"));
            movie.setResume(rs.getString("resume"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }

    /**
     * Metodo StringBuilder, otra forma de hacer un toString
     */

    public static String movieStringBuilder(MovieDAO movie) { // %2d se usa para reemplazar enteros y %s para reemplazar strings
        return String.format("\nid: %2d,\n title: %s,\n release: %s,\n running time: %s,\n resume: %s\n",
                movie.getIdMovie(),
                movie.getTitle(),
                movie.getRelease(),
                movie.getRunningTime(),
                movie.getResume());
    }
}
