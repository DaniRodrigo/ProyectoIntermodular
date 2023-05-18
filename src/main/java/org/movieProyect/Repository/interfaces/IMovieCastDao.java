package org.movieProyect.Repository.interfaces;

import org.movieProyect.ModelDAO.ActorDAO;
import org.movieProyect.ModelDAO.MovieDAO;

import java.util.List;

/**
 * Esta interfaz define los métodos para gestionar IMovieCast en la clase que los implementa.
 */
public interface IMovieCastDao {

    public List<MovieDAO> getMoviesFromActor(int actorId);  //Lista de peliculas según el id de un actor
    public List<ActorDAO> getActorsFromMovie(int movieId);  //Lista de actores según el id de una película
    //public List<String> getMoviesWithActors();  //Lista de peliculas y actores que participan en ellas.
    //public List<String> getActorsWithMovies();  //Lista de actores y peliculas en las que participan.
}
