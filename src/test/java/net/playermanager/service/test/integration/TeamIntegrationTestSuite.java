/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.playermanager.service.test.integration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author gerrymclarnon
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({net.playermanager.service.test.integration.CreateTeamIT.class, 
                    net.playermanager.service.test.integration.ReadTeamIT.class, 
                    net.playermanager.service.test.integration.DeleteTeamIT.class})
public class TeamIntegrationTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
