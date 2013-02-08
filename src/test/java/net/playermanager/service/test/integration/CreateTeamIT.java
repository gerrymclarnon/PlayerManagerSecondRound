/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.playermanager.service.test.integration;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
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
public class CreateTeamIT {
    
    public CreateTeamIT() {
    }
   
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        TeamTestHelper.removeAllTestTeams();
    }
    
    @After
    public void tearDown() {
        TeamTestHelper.removeAllTestTeams();
    }

    /**
     * Test of create method, of class TeamRESTFacade.
     */
    @Test
    public void testCreateForbidden() {
        System.out.println("create forbidden");
        Team entity = new Team();
        entity.setName("Dream Team");
        TeamTestHelper teamTestHelper = new TeamTestHelper();
        teamTestHelper.setUser(TeamTestHelper.User.PLAYER);
        ClientResponse result = teamTestHelper.create(entity);
        assertEquals(ClientResponse.Status.FORBIDDEN, result.getClientResponseStatus());
    }

    /**
     * Test of create method, of class TeamRESTFacade.
     */
    @Test
    public void testCreateAllowed() {
        System.out.println("create allowed");
        Team team = new Team();
        team.setName("Dream Team");
        team.setUri("dreamteam");
        
        TeamTestHelper teamTestHelper = new TeamTestHelper();
        teamTestHelper.setUser(TeamTestHelper.User.MANAGER);
        ClientResponse result = teamTestHelper.create(team);
        assertEquals(ClientResponse.Status.CREATED, result.getClientResponseStatus());
    }

}
