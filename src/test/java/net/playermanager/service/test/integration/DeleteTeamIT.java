/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.playermanager.service.test.integration;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.util.Collection;
import net.playermanager.data.Team;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import net.playermanager.service.util.TeamTestHelper;
import static org.junit.Assert.*;

/**
 *
 * @author u530619
 */
public class DeleteTeamIT {
    
    public DeleteTeamIT() {
    }
   
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        TeamTestHelper.createAllTestTeams();
    }
    
    @After
    public void tearDown() {
        TeamTestHelper.removeAllTestTeams();
    }

    /**
     * Test of create method, of class TeamRESTFacade.
     */
    @Test
    public void testDeleteForbidden() {
        System.out.println("delete forbidden");

        TeamTestHelper teamTestHelper = new TeamTestHelper();
        teamTestHelper.setUser(TeamTestHelper.User.PLAYER);

        try {
            teamTestHelper.remove("1");
        } catch (UniformInterfaceException e) {
            ClientResponse result = e.getResponse();
            assertEquals(ClientResponse.Status.FORBIDDEN, result.getClientResponseStatus());
        }
        
        String expResult = "1";
        String result = teamTestHelper.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of create method, of class TeamRESTFacade.
     */
    @Test
    public void testDeleteAllowed() {
        System.out.println("delete allowed");
        
        TeamTestHelper teamTestHelper = new TeamTestHelper();
        teamTestHelper.setUser(TeamTestHelper.User.MANAGER);

        GenericType<Collection<Team>> genericType = new GenericType<Collection<Team>>(){};
        Collection<Team> teams = teamTestHelper.findAll(genericType);
        Team team = teams.toArray(new Team[0])[0];
        
        teamTestHelper.remove(team.getId().toString());
        
        String expResult = "0";
        String result = teamTestHelper.count();
        assertEquals(expResult, result);
    }

}
