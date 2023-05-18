package org.movieProyect.ModelDAO;

import java.util.Date;

/**
 * Clase que representa un objeto de Actor.
 */

public class ActorDAO {

    //Lista de atributos

    int idActor;  //id del actor.
    String name;   //Nombre del actor.
    String lastName;   //Apellido del actor.
    Date born;   //Fecha de nacimiento del actor.
    String country;   //Pais del actor.

    public ActorDAO() {
    }

    /**
     * Constructores de la clase ActorDAO.
     * El de arriba es un constructor vacio por defecto.
     *
     * @param name       El nombre del objeto ActorDAO.
     * @param lastName   El apellido del objeto ActorDAO.
     * @param born       La fecha de nacimiento del objeto ActorDAO, usando DateUtils.stringToDate("dd-MM-yyyy")
     * @param country    El pais del objeto ActorDAO.
     */

    public ActorDAO(String name, String lastName, Date born, String country) {
        this.name = name;
        this.lastName = lastName;
        this.born = born;
        this.country = country;
    }

    /**
     * Getter y Setter de los atributos de la clase.
     */


    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
