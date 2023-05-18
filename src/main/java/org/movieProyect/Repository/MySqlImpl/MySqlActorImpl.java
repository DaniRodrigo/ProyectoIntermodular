package org.movieProyect.Repository.MySqlImpl;

import org.movieProyect.Conectors.MySQLConector;
import org.movieProyect.ModelDAO.ActorDAO;
import org.movieProyect.Repository.interfaces.IActorDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la implementación de actor y usa el conector para comunicarse con la BBDD.
 * Obliga al uso de métodos definidos en la interfaz IActorDAO, que serán desarrollados aqui.
 */

public class MySqlActorImpl implements IActorDao {

    //Lista de atributos

    MySQLConector conector;  //Objeto de la clase MySQLConector

    /**
     * Constructor de la clase MySqlActorImpl.
     * Usamos el tipo de la clase MySQLConector
     */

    public MySqlActorImpl() {
        conector = new MySQLConector();
    }

    /**
     * Metodo para obtener el listado de actores:
     * <p>
     * Se crea una lista de actores vacia, se abre la conexión,
     * introducimos la consulta SQL, va mapeando el resultado en la lista creada o arroja una excepcion en caso de fallo,
     * para finalmente cerrar la conexion. Nos devuelve la lista de actores.
     */

    @Override
    public List<ActorDAO> getActorList() {

        List<ActorDAO> actorList = new ArrayList<>();

        this.conector.openConection();

        ResultSet rs = this.conector.executeQuery("SELECT * FROM actor");

        while (true) {
            try {
                if (!rs.next()) break;
                actorList.add(mapper(rs));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        this.conector.closeConexion();

        return actorList;
    }

    /**
     * Metodo para obtener un actor según su id:
     * <p>
     * Se crea un objeto de la clase ActorDAO inicializada nula para arrojar el resultado
     * de la consulta SQL, si no devuelve una excepción la conexion.
     */

    @Override
    public ActorDAO getActor(int id) {

        ActorDAO actor = null;

        conector.openConection();

        ResultSet rs = conector.executeQuery("SELECT * FROM actor WHERE idActor = " + id);
        try {
            if (rs.next()) {
                actor = mapper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        conector.closeConexion();

        return actor;
    }

    @Override
    public ActorDAO getActorByName(String name) {

        ActorDAO actor = null;

        conector.openConection();

        ResultSet rs = conector.executeQuery("SELECT * FROM actor WHERE name like '%"+ name +"%'");
        try {
            if (rs.next()) {
                actor = mapper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        conector.closeConexion();

        return actor;
    }

    /**
     * Metodo para modificar un actor previamente creado a partir de su objeto.
     */

    @Override
    public ActorDAO updateActor(ActorDAO actor) {

        ActorDAO updatedActor;
        conector.openConection();

        conector.executeUpdate("UPDATE actor SET " +
                "name = " + actor.getName() +
                "lastName = " + actor.getLastName() +
                "born = " + actor.getBorn() +
                "country = " + actor.getCountry()
                + "WHERE idActor = " + actor.getIdActor());

        updatedActor = getActor(actor.getIdActor());

        conector.closeConexion();

        return updatedActor;
    }

    /**
     * Metodo para eliminar un actor según su id:
     * <p>
     * Se abre la conexion, se crea un mecanismo de control booleano que introduce la
     * consulta SQL y devuelve true/false según se haya podido ejecutar.
     */

    @Override
    public boolean deleteActor(int id) {
        conector.openConection();

        boolean result = conector.executeUpdate("DELETE FROM actor where idActor = " + id);

        conector.closeConexion();

        return result;

    }

    /**
     * Metodo para crear un actor:
     * <p>
     * Es necesario un objeto de la clase ActorDAO creado previamente para usar este metodo.
     * <p>
     * También se usa un mecanismo de control booleano para verificar que se ha ejecutado correctamente.
     */

    @Override
    public int createActor(ActorDAO actor) {
        int id = -1;
        conector.openConection();

        String sentence = "INSERT INTO actor (name, lastName, born, country) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement newActor = conector.getCon().prepareStatement(sentence, Statement.RETURN_GENERATED_KEYS);
            newActor.setString(1, actor.getName());
            newActor.setString(2, actor.getLastName());
            newActor.setDate(3, new Date(actor.getBorn().getTime()));
            newActor.setString(4, actor.getCountry());

            int result = newActor.executeUpdate();

            if(result > 0) {
                try (ResultSet rs = newActor.getGeneratedKeys()) {
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

    /**
     * Metodo que nos devuelve visualmente el resultado de las consultas SQL:
     */

    private ActorDAO mapper(ResultSet rs) {

        ActorDAO actor = new ActorDAO();
        try {
            actor.setIdActor(rs.getInt("idActor"));
            actor.setName(rs.getString("name"));
            actor.setLastName(rs.getString("lastName"));
            actor.setBorn(rs.getDate("born"));
            actor.setCountry(rs.getString("country"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actor;
    }
}
