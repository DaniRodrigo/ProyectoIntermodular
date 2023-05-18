package org.movieProyect.Repository.interfaces;

import org.movieProyect.ModelDAO.ActorDAO;

import java.util.List;

/**
 * Esta interfaz define los métodos para gestionar los actores en la clase que los implementa.
 */

public interface IActorDao {

    /**
     * Definicion de métodos para poder interactuar con la base de datos.
     * Serán usados en el método main del proyecto.
     */

    public List<ActorDAO> getActorList();   //Lista de actores contenidos en la BBDD.

    public ActorDAO getActor(int id);   //Obtener un actor concreto de la BBDD.

    public ActorDAO getActorByName(String name);   //Obtener un actor concreto de la BBDD por nombre (primera ocurrencia).

    public ActorDAO updateActor(ActorDAO actor);   //Modificar valores de un actor en la BBDD.

    public boolean deleteActor(int id);   //Eliminar un actor de la BBDD.

    public int createActor(ActorDAO actor);   //Crear un actor en la BBDD.
}
