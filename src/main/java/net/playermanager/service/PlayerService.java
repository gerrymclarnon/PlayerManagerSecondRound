/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.playermanager.service;

import com.sun.jersey.api.spring.Autowire;
import com.sun.jersey.spi.resource.Singleton;
import javax.persistence.EntityManager;
import net.playermanager.data.Player;
import net.playermanager.data.Team;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


/**
 *
 * @author u530619
 */
//@Path("/{club}")
//@Path("/resources")
@Path("/{clubId}{teamId:(/[^/]+?)?}/{players}")
@Singleton
@Autowire
public class PlayerService {
    @PersistenceContext(unitName = "TeamPlayerPU")
    protected EntityManager entityManager;

    public PlayerService() {
    }

    @GET
    @Path("{var:.*}/stuff")
    //@Path("{team:.*}/player")
    public String findAll() {
        return "Hello Jersey";
    }
    
    @GET
    //@Path("/user/{id}{format:(/format/[^/]+?)?}{encoding:(/encoding/[^/]+?)?}")
    //@Path("/user/{id}{format:(/[^/]+?)?}{encoding:(/[^/]+?)?}/{players}")
    public Collection<Player> getPlayers(
            @PathParam("clubId") String clubId,
            @PathParam("teamId") String teamId) {

        Collection<Player> players;
        
        if (teamId.equals("")) {
            // Optional parameter "teamId" not specified
            players = find(true, -1, -1);
        } else {
            Team team = entityManager.find(Team.class, Long.decode(teamId.replace("/", "")));
            players = team.getPlayers();
        }

        return players;
    }
    
/*
    @POST
    @Consumes({"application/xml", "application/json"})
    @Transactional
    public Response create(Player entity) {
        entityManager.persist(entity);
        return Response.created(URI.create(entity.getId().toString())).build();
    }
*/
/*    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Transactional
    public Response addPlayer(@PathParam("teamId") Long teamId, Player player) {
        entityManager.persist(player);
        Team team = entityManager.find(Team.class, teamId);
        team.getPlayers().add(player);
        entityManager.merge(team);
        return Response.created(URI.create(player.getId().toString())).build();
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Transactional
    public void edit(Player entity) {
        entityManager.merge(entity);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void remove(@PathParam("id") Long id) {
        Player entity = entityManager.getReference(Player.class, id);
        entityManager.remove(entity);
    }
*/
/*
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public Player find(@PathParam("id") Long id) {
        return entityManager.find(Player.class, id);
    }
*/

/*
    @GET
    @Path("{/team:.*}/id")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public List<Player> findAll() {
        return find(true, -1, -1);
    }

    @GET
    @Path("{max}/{first}")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public List<Player> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return find(false, max, first);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    @Transactional
    public String count() {
        try {
            Query query = entityManager.createQuery("SELECT count(o) FROM Player AS o");
            return query.getSingleResult().toString();
        } finally {
            entityManager.close();
        }
    }
*/
    private List<Player> find(boolean all, int maxResults, int firstResult) {
        try {
            Query query = entityManager.createQuery("SELECT object(o) FROM Player AS o");
            if (!all) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
            }
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    
}
