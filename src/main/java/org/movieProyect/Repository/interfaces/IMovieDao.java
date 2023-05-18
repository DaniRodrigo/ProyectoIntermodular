package org.movieProyect.Repository.interfaces;

import org.movieProyect.ModelDAO.MovieDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Esta interfaz define los métodos para gestionar las peliculas en la clase que las implementa.
 */
public interface IMovieDao {

    /**
     * Definicion de métodos para poder interactuar con la base de datos.
     * Serán usados en el método main del proyecto.
     */

    public List<MovieDAO> getMovieList();   //Lista de peliculas contenidas en la BBDD.

    public MovieDAO getMovie(int id) throws SQLException;   //Obtener una pelicula concreta de la BBDD.

    public MovieDAO getMovieByTitle(String title);   //Obtener una pelicula concreta de la BBDD por su titulo (primera ocurrencia).

    public MovieDAO updateMovie(MovieDAO movie) throws SQLException;   //Modificar valores de una pelicula en la BBDD.

    public boolean deleteMovie(int id);   //Eliminar una pelicula de la BBDD.

    public int createMovie(MovieDAO movie);   //Crear una pelicula en la BBDD.
}
