package org.movieProyect.ModelDAO;

import java.util.Date;

/**
 * Clase que representa un objeto de Actor.
 */
public class MovieDAO {

    //Lista de atributos

    int idMovie;   //id de la pelicula.
    String title;  //Titulo de la pelicula.
    Date release;  //Fecha de estremo de la pelicula.
    int runningTime;  //Duracion en minutos de la pelicula.
    String resume;  //Sinopsis de la pelicula.


    public MovieDAO() {
    }

    /**
     * Constructores de la clase MovieDAO.
     * El de arriba es un constructor vacio por defecto.
     *
     * @param title       El titulo de la pelicula del objeto MovieDAO.
     * @param release     La fecha de estreno de la película objeto MovieDAO, usa DateUtils.stringToDate("dd-MM-yyyy").
     * @param runningTime Duracion en minutos de la pelicula objeto MovieDAO.
     * @param resume      Sinopsis de la pelicula objeto MovieDAO.
     */

    public MovieDAO(String title, Date release, int runningTime, String resume) {
        this.title = title;
        this.release = release;
        this.runningTime = runningTime;
        this.resume = resume;
    }

    /**
     * Getter y Setter de los atributos de la clase.
     */


    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
