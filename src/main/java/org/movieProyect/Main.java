package org.movieProyect;

import org.movieProyect.ModelDAO.ActorDAO;
import org.movieProyect.ModelDAO.MovieDAO;
import org.movieProyect.Repository.MySqlImpl.MySqlMovieCastImpl;
import org.movieProyect.Repository.MySqlImpl.MySqlMovieImpl;
import org.movieProyect.Repository.interfaces.IActorDao;
import org.movieProyect.Repository.MySqlImpl.MySqlActorImpl;
import org.movieProyect.Repository.interfaces.IMovieCastDao;
import org.movieProyect.Repository.interfaces.IMovieDao;
import org.movieProyect.utils.DateUtils;
import org.movieProyect.view.Menu;

import java.sql.SQLException;
import java.util.List;
import static org.movieProyect.mappers.ActorEntityToDAO.actorStringBuilder;
import static org.movieProyect.mappers.MovieEntityToDAO.movieStringBuilder;

public class Main {
    public static void main(String[] args) throws SQLException {

        Menu menu = new Menu();
        menu.menuPrincipal();

      /*  //Creacion de objetos para la realizacion de metodos:

        IActorDao actorDaoImp = new MySqlActorImpl();

        IMovieCastDao movieCastImpl = new MySqlMovieCastImpl();

        IMovieDao movieDaoImp = new MySqlMovieImpl();

        ActorDAO newActor = new ActorDAO("Julia", "Robberts", DateUtils.stringToDate("28-10-1967"), "EE.UU");

        MovieDAO newMovie = new MovieDAO("Goodfellas", DateUtils.stringToDate("19-09-1990"), 146, "La película sigue el ascenso y caída de tres delincuentes durante tres décadas. Es protagonizada por Ray Liotta, Robert De Niro, Joe Pesci, Lorraine Bracco y Paul Sorvino en los papeles principales.");

        List<ActorDAO> actorList = actorDaoImp.getActorList();
        List<MovieDAO> movieList = movieDaoImp.getMovieList();


        //Metodos de actores

        System.out.println("Obtenemos la lista de actores: ");
        for (ActorDAO actor : actorList) {
            System.out.println(actorStringBuilder(actor));
        }

        System.out.println();
        System.out.println("Obtenemos al actor de id 4: " + actorStringBuilder(actorDaoImp.getActor(4)));
        System.out.println();
        //int idActor = actorDaoImp.createActor(newActor);

        //System.out.println("Obtenemos al actor que acabamos de crear: " + actorStringBuilder(actorDaoImp.getActor(idActor))); //metodo dentro de metodo para ahorrar variables (buenas practicas)

        System.out.println();
        System.out.println("Mostramos al nuevo actor en la lista: " + actorDaoImp.getActorList());
        System.out.println();
        System.out.println("Obtenemos actriz Julia por su nombre: " + actorStringBuilder(actorDaoImp.getActorByName("Jul")));
        System.out.println();
        System.out.println();


        //Metodos de peliculas

        System.out.println("Obtenemos la lista de peliculas: ");
        for (MovieDAO movie : movieList) {
            System.out.println(movieStringBuilder(movie));
        }
        System.out.println();
        System.out.println("Obtenemos la pelicula de id 4: " + movieStringBuilder(movieDaoImp.getMovie(4)));
        System.out.println();

        System.out.println("Obtenemos película Hook por su título: " + movieStringBuilder(movieDaoImp.getMovieByTitle("Hook")));
        System.out.println();

        //int idMovie = movieDaoImp.createMovie(newMovie);

        //System.out.println("Obtenemos el id de la pelicula que acabamos de crear: " + movieStringBuilder(movieDaoImp.getMovie(idMovie))); //metodo dentro de metodo para ahorrar variables (buenas practicas)

        System.out.println("Obtenemos la lista de peliculas para verificar que la nueva está incluida: ");
        for (MovieDAO movie : movieList) {
            System.out.println(movieStringBuilder(movie));
        }
        System.out.println();



        //Métodos de MovieCast

        actorList.forEach(actor -> {
            System.out.println("Las pelis de "+ actor.getName() + " "+actor.getLastName()+" son: ");
            movieCastImpl.getMoviesFromActor(actor.getIdActor()).forEach(movie -> {
                System.out.println(movie.getTitle());
            });
            System.out.println();
        });



        movieList.forEach(movie -> {
            System.out.println("Los actores de "+ movie.getTitle() +" son: ");
            movieCastImpl.getActorsFromMovie(movie.getIdMovie()).forEach(actor -> {
                System.out.println(actor.getName()+""+actor.getLastName());
            });
            System.out.println();
        });
*/


    }


}
