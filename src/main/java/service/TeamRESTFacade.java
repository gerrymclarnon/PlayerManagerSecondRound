/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.sun.jersey.api.spring.Autowire;
import com.sun.jersey.spi.resource.Singleton;
import entities.Player;
import javax.persistence.EntityManager;
import entities.Team;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author u530619
 */
@Path("/team")
@Singleton
@Autowire
public class TeamRESTFacade {
    @PersistenceContext(unitName = "TeamPlayerPU")
    protected EntityManager entityManager;

    public TeamRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    @Transactional
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Response create(Team entity) {
        entityManager.persist(entity);
        return Response.created(URI.create(entity.getId().toString())).build();
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Transactional
    public void edit(Team entity) {
        entityManager.merge(entity);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void remove(@PathParam("id") Long id) {
        Team entity = entityManager.getReference(Team.class, id);
        entityManager.remove(entity);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public Team find(@PathParam("id") Long id) {
        return entityManager.find(Team.class, id);
    }

    @GET
    @Path("{id}/players")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public Collection<Player> findAllPlayers(@PathParam("id") Long id) {
        Team team = entityManager.find(Team.class, id);
        return team.getPlayers();
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Transactional
    public List<Team> findAll() {
        return find(true, -1, -1);
    }

    @GET
    @Path("{max}/{first}")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public List<Team> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return find(false, max, first);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    @Transactional
    public String count() {
        try {
            Query query = entityManager.createQuery("SELECT count(o) FROM Team AS o");
            return query.getSingleResult().toString();
        } finally {
            entityManager.close();
        }
    }

    private List<Team> find(boolean all, int maxResults, int firstResult) {
        try {
            Query query = entityManager.createQuery("SELECT object(o) FROM Team AS o");
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
