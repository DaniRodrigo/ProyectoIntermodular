package org.movieProyect.view;

import org.movieProyect.ModelDAO.ActorDAO;
import org.movieProyect.ModelDAO.MovieDAO;
import org.movieProyect.Repository.MySqlImpl.MySqlActorImpl;
import org.movieProyect.Repository.MySqlImpl.MySqlMovieCastImpl;
import org.movieProyect.Repository.MySqlImpl.MySqlMovieImpl;
import org.movieProyect.Repository.interfaces.IActorDao;
import org.movieProyect.Repository.interfaces.IMovieCastDao;
import org.movieProyect.Repository.interfaces.IMovieDao;
import org.movieProyect.utils.DateUtils;

import static org.movieProyect.mappers.ActorEntityToDAO.actorStringBuilder;
import static org.movieProyect.mappers.MovieEntityToDAO.movieStringBuilder;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private static Scanner sc = new Scanner(System.in);
    private boolean trueFalse_principal = true;
    private boolean trueFalse_actores = true;
    private boolean trueFalse_peliculas = true;
    private IActorDao actorDaoImp = new MySqlActorImpl();
    private IMovieDao movieDaoImp = new MySqlMovieImpl();


    public Menu() {
    }

    public void menuPrincipal() {
        int opcion = 0;

        do {
            //mostar menu
            plantillaMenuPrincipal();

            //almacena la opcion elegida
            try {
                opcion = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Unicamente puedes ingresar numeros enteros");
            }

            switch (opcion) {
                case 1:
                    menuActores();
                    break;
                case 2:
                    menuPeliculas();
                    break;
                case 3:
                    System.out.println("\t Haz finalizado el programa");
                    trueFalse_principal = false;
                    break;
                default:
                    System.out.println("\t ¡ATENCION! ingresa unicamente numeros enteros entre (1-3)");
                    break;
            }

        } while (trueFalse_principal);


    }

    private void menuPeliculas() {

        int opcion = 0;

        do {
            //mostar menu
            plantillaMenuPeliculas();

            //almacena la opcion elegida
            try {
                opcion = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Unicamente puedes ingresar numeros enteros");
            }

            switch (opcion) {
                case 1:
                    findAllmovies();
                    break;
                case 2:
                    findMovieByID();
                    break;
                 case 3:
                    findMovieByName();
                    break;
                case 4:
                    createMovie();
                    break;
                case 5:
                    deleteMovie();
                    break;
                case 6:
                    updateMovie();
                    break;
                case 7:
                    findAllActorscast();
                    break;
                case 8:
                    System.out.println("\t Has regresado al menu principal");
                    trueFalse_peliculas = false;
                    break;
                default:
                    System.out.println("\t ¡ATENCION! ingresa unicamente numeros enteros entre (1-2)");
                    break;
            }

        } while (trueFalse_peliculas);
    }

    private void findAllActorscast() {
        IMovieCastDao movieCastImpl = new MySqlMovieCastImpl();

        List<MovieDAO> movieList = movieDaoImp.getMovieList();

        movieList.forEach(movie -> {
            System.out.println("\n\t Los actores que aparecen en " + movie.getTitle() + " son: ");
            movieCastImpl.getActorsFromMovie(movie.getIdMovie()).forEach(actor -> {
                System.out.println("\t " + actor.getName() + " " + actor.getLastName());
            });
        });
    }

    private void deleteMovie() {
        int movieID;

        try {

            System.out.print("Ingresar ID de la pelicula: ");
            //almacena el ID introducido
            movieID = Integer.parseInt(sc.next());

            //buscar en la BBDD el ID que coincidan
            MovieDAO movieFound = movieDaoImp.getMovieList()
                    .stream()
                    .filter(movieDAO -> movieDAO.getIdMovie() == movieID)
                    .findFirst()
                    .orElseThrow(()
                            -> new InputMismatchException("\t La pelicula con el ID: " + movieID + " no existe en la BBDD \n"));

            movieDaoImp.deleteMovie(movieFound.getIdMovie());

            System.out.println("\t La pelicula de nombre : " + movieFound.getTitle() + " ha sido eliminada de la BBDD con exito \n");


        } catch (NumberFormatException e) {
            System.out.println("Unicamente puedes ingresar numeros enteros");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateMovie() {
        String movieTitle;
        int movieRunningTime;
        String movieResume;
        int movieID;

        try {

            System.out.print("Ingresar ID pelicula: ");
            //almacena el ID introducido
            movieID = Integer.parseInt(sc.next());

            //buscar en la BBDD el ID que coincidan
            MovieDAO movieFound = movieDaoImp.getMovieList()
                    .stream()
                    .filter(movieDAO -> movieDAO.getIdMovie() == movieID)
                    .findFirst()
                    .orElseThrow(()
                            -> new InputMismatchException("\t La pelicula con el ID: " + movieID + " no existe en la BBDD \n"));


            System.out.println("\t Ingresa los datos de la pelicula");
            System.out.print("\t titulo: ");
            //almacena el nombre introducido
            movieTitle = sc.next();
            System.out.print("\t duracion (min): ");
            //almacena el apellido introducido
            movieRunningTime = Integer.parseInt(sc.next());
            System.out.print("\t sinopsis: ");
            //almacena el pais introducido
            movieResume = sc.next();

            //actualizamos la pelicula
            movieFound.setTitle(movieTitle);
            movieFound.setRunningTime(movieRunningTime);
            movieFound.setResume(movieResume);
            movieDaoImp.updateMovie(movieFound);

            System.out.println("\t La pelicula con nombre : " + movieFound.getTitle() + " actualizada de la BBDD con exito \n");


        } catch (NumberFormatException e) {
            System.out.println("Unicamente puedes ingresar numeros enteros");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createMovie() {
        String movieTitle;
        String movieRelease;
        int movieRunningTime;
        String movieResume;

        try {
            System.out.println("\t Ingresa los datos de la pelicula");
            System.out.print("\t titulo: ");
            //almacena el titulo introducido
            movieTitle = sc.next();
            System.out.print("\t fecha estreno: ");

            movieRelease = sc.next();
            System.out.print("\t duracion (min): ");

            movieRunningTime = Integer.parseInt(sc.next());
            System.out.print("\t sinopsis: ");
            //almacena sinopsis introducido
            movieResume = sc.next();

            //creamos el nuevo actor
            MovieDAO newMovie = new MovieDAO(
                    movieTitle,
                    DateUtils.stringToDate(movieRelease),
                    movieRunningTime,
                    movieResume);

            //guardar el actor en la BBDD
            movieDaoImp.createMovie(newMovie);

            System.out.println("\t Pelicula " + movieTitle + " creado en la BBDD con exito \n");

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findMovieByName() {
        String movieName;

        try {
            System.out.print("\t Ingresar nombre de la pelicula: ");

            boolean esNumerico = sc.hasNextInt();

            if (!esNumerico) {
                //almacena el nombre introducido
                movieName = sc.next();
                //buscar en la BBDD el ID que coincidan
                MovieDAO movieFound = Optional.ofNullable(movieDaoImp.getMovieByTitle(movieName))
                        .orElseThrow(()
                                -> new InputMismatchException("\t La pelicula con el nombre: " + movieName + " no existe en la BBDD \n"));
                //mostrar actor
                System.out.println(movieStringBuilder(movieFound));

            } else {
                throw new Exception("Debes ingresar un tipo de nombre valido");
            }

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void findMovieByID() {
        int movieID;


        try {
            System.out.print("Ingresar movie ID: ");
            //almacena el ID introducido
            movieID = Integer.parseInt(sc.next());

            //buscar en la BBDD el ID que coincidan
            MovieDAO movieFound = Optional.ofNullable(movieDaoImp.getMovie(movieID))
                    .orElseThrow(()-> new InputMismatchException("\t La movie con el ID: " + movieID + " no existe en la BBDD \n"));

            System.out.println(movieStringBuilder(movieFound));

        } catch (NumberFormatException e) {
            System.out.println("Unicamente puedes ingresar numeros enteros");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void plantillaMenuPeliculas() {
        System.out.println("\n\t MENU PELICULAS");
        System.out.println("\t   1. Listar peliculas");
        System.out.println("\t   2. Buscar pelicula por su ID");
        System.out.println("\t   3. Buscar pelicula por su titulo");
        System.out.println("\t   4. Crear pelicula");
        System.out.println("\t   5. Eliminar pelicula");
        System.out.println("\t   6. Actualizar pelicula");
        System.out.println("\t   7. Listado de peliculas y actores");
        System.out.println("\t   8. Salir");
        System.out.print("Elige una opcion:");
    }

    private void findAllmovies() {
        List<String> peliculas = movieDaoImp.getMovieList()
                .stream()
                .map(movieDAO -> movieStringBuilder(movieDAO))
                .collect(Collectors.toList());

        System.out.println(peliculas);
    }

    public void plantillaMenuPrincipal() {
        System.out.println("\n\t MENU APP CINE");
        System.out.println("\t   1. Menu actores");
        System.out.println("\t   2. Menu peliculas");
        System.out.println("\t   3. Salir");
        System.out.print("Elige una opcion:");

    }

    public void menuActores() {

        int opcion = 0;

        do {
            //mostar menu
            plantillaMenuActores();

            //almacena la opcion elegida
            try {
                opcion = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Unicamente puedes ingresar numeros enteros");
            }

            switch (opcion) {
                case 1:
                    findAllActors();
                    break;
                case 2:
                    findActorByID();
                    break;
                case 3:
                    findActorByName();
                    break;
                case 4:
                    createActor();
                    break;
                case 5:
                    deleteActor();
                    break;
                case 6:
                    updateActor();
                    break;
                case 7:
                    findAllMovicast();
                    break;
                case 8:
                    System.out.println("\t Has regresado al menu principal");
                    trueFalse_actores = false;
                    break;
                default:
                    System.out.println("\t ¡ATENCION! ingresa unicamente numeros enteros entre (1-8)");
                    break;
            }

        } while (trueFalse_actores);


    }

    private void findAllMovicast() {
        IMovieCastDao movieCastImpl = new MySqlMovieCastImpl();

        List<ActorDAO> actorList = actorDaoImp.getActorList();

        actorList.forEach(actor -> {
            System.out.println("\n\t Las peliculas de " + actor.getName() + " " + actor.getLastName() + " son: ");
            movieCastImpl.getMoviesFromActor(actor.getIdActor()).forEach(movie -> {
                System.out.println("\t " + movie.getTitle());
            });
        });
    }

    private void updateActor() {
        String actorName;
        String actorLastName;
        String actorDOB;
        String actorCountry;
        int actorID;

        try {

            System.out.print("Ingresar actor ID: ");
            //almacena el ID introducido
            actorID = Integer.parseInt(sc.next());

            //buscar en la BBDD el ID que coincidan
            ActorDAO actorFound = actorDaoImp.getActorList()
                    .stream()
                    .filter(actorDAO -> actorDAO.getIdActor() == actorID)
                    .findFirst()
                    .orElseThrow(()
                            -> new InputMismatchException("\t El actor con el ID: " + actorID + " no existe en la BBDD \n"));


            System.out.println("\t Ingresa los datos de actor");
            System.out.print("\t nombre: ");
            //almacena el nombre introducido
            actorName = sc.next();
            System.out.print("\t apellido: ");
            //almacena el apellido introducido
            actorLastName = sc.next();
            System.out.print("\t pais: ");
            //almacena el pais introducido
            actorCountry = sc.next();

            //actualizamos el  actor
            actorFound.setName(actorName);
            actorFound.setLastName(actorLastName);
            actorFound.setCountry(actorCountry);
            actorDaoImp.updateActor(actorFound);

            System.out.println("\t Actor con nombre : " + actorFound.getName() + " actualizado de la BBDD con exito \n");


        } catch (NumberFormatException e) {
            System.out.println("Unicamente puedes ingresar numeros enteros");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteActor() {
        int actorID;

        try {

            System.out.print("Ingresar actor ID: ");
            //almacena el ID introducido
            actorID = Integer.parseInt(sc.next());

            //buscar en la BBDD el ID que coincidan
            ActorDAO actorFound = actorDaoImp.getActorList()
                    .stream()
                    .filter(actorDAO -> actorDAO.getIdActor() == actorID)
                    .findFirst()
                    .orElseThrow(()
                            -> new InputMismatchException("\t El actor con el ID: " + actorID + " no existe en la BBDD \n"));

            actorDaoImp.deleteActor(actorFound.getIdActor());

            System.out.println("\t Actor con nombre : " + actorFound.getName() + " elminado de la BBDD con exito \n");


        } catch (NumberFormatException e) {
            System.out.println("Unicamente puedes ingresar numeros enteros");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createActor() {
        String actorName;
        String actorLastName;
        String actorDOB;
        String actorCountry;

        try {
            System.out.println("\t Ingresa los datos de actor");
            System.out.print("\t nombre: ");
            //almacena el nombre introducido
            actorName = sc.next();
            System.out.print("\t apellido: ");
            //almacena el apellido introducido
            actorLastName = sc.next();
            System.out.print("\t fecha nacimiento: ");
            //almacena el fecha nacimiento introducido
            actorDOB = sc.next();
            System.out.print("\t pais: ");
            //almacena el pais introducido
            actorCountry = sc.next();

            //creamos el nuevo actor
            ActorDAO newActor = new ActorDAO(
                    actorName,
                    actorLastName,
                    DateUtils.stringToDate(actorDOB),
                    actorCountry);

            //guardar el actor en la BBDD
            actorDaoImp.createActor(newActor);

            System.out.println("\t Actor " + actorName + " creado en la BBDD con exito \n");

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findActorByName() {
        String actorName;

        try {
            System.out.print("\t Ingresar nombre de actor: ");

            boolean esNumerico = sc.hasNextInt();

            if (!esNumerico) {
                //almacena el nombre introducido
                actorName = sc.next();
                //buscar en la BBDD el ID que coincidan
                ActorDAO actorFound = Optional.ofNullable(actorDaoImp.getActorByName(actorName))
                        .orElseThrow(()
                                -> new InputMismatchException("\t El actor con el nombre: " + actorName + " no existe en la BBDD \n"));
                //mostrar actor
                System.out.println(actorStringBuilder(actorFound));

            } else {
                throw new Exception("Debes ingresar un tipo de nombre valido");
            }

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findActorByID() {
        int actorID;


        try {
            System.out.print("Ingresar actor ID: ");
            //almacena el ID introducido
            actorID = Integer.parseInt(sc.next());

            //buscar en la BBDD el ID que coincidan
            ActorDAO actorFound = actorDaoImp.getActorList()
                    .stream()
                    .filter(actorDAO -> actorDAO.getIdActor() == actorID)
                    .findFirst()
                    .orElseThrow(()
                            -> new InputMismatchException("\t El actor con el ID: " + actorID + " no existe en la BBDD \n"));

            System.out.println(actorStringBuilder(actorFound));

        } catch (NumberFormatException e) {
            System.out.println("Unicamente puedes ingresar numeros enteros");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }

    }

    private void findAllActors() {
        List<String> actores = actorDaoImp.getActorList()
                .stream()
                .map(actorDAO -> actorStringBuilder(actorDAO))
                .collect(Collectors.toList());
        System.out.println(actores);

    }


    public void plantillaMenuActores()  {
        System.out.println("\n\t MENU ACTORES");
        System.out.println("\t   1. Listar actores");
        System.out.println("\t   2. Buscar actor por ID");
        System.out.println("\t   3. Buscar actor por su nombre");
        System.out.println("\t   4. Crear actor");
        System.out.println("\t   5. Eliminar actor");
        System.out.println("\t   6. Actualizar actor");
        System.out.println("\t   7. Listado de actores y peliculas");
        System.out.println("\t   8. Salir");
        System.out.print("Elige una opcion:");
    }
}
