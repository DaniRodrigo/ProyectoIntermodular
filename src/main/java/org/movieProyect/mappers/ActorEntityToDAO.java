package org.movieProyect.mappers;

import org.movieProyect.ModelDAO.ActorDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que contiene un metodo mapper.
 * El mapper se encarga de convertir un tipo de dato en otro.
 * En este caso, coge los datos de la BBDD y lo convierte al modelo de dato conveniente.
 */
public class ActorEntityToDAO {
    public static ActorDAO mapper(ResultSet rs) {

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

    /**
     * Metodo StringBuilder, otra forma de hacer un toString
     */
    public static String actorStringBuilder(ActorDAO actor) { // %2d se usa para reemplazar enteros y %s para reemplazar strings
        return String.format("\n id: %2d,\n name: %s,\n last name: %s,\n born: %s,\n country: %s\n",
                actor.getIdActor(),
                actor.getName(),
                actor.getLastName(),
                actor.getBorn(),
                actor.getCountry());
    }
}
