
package net.playermanager.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/** Example resource class hosted at the URI path "/hello"
 */
@Path("/hello")
public class HelloService {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String helloWorld() {
        return "Hi there!";
    }
}
