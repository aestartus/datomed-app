package cl.datomed.api.rest.intf;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.Serializable;

/**
 * Created by aestartus on 18-11-15.
 */
@Local
@Path( "business-resource" )
public interface BusinessRESTResourceProxy extends Serializable {

    @POST
    @Path( "login" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam( "username" ) String username,
            @FormParam( "password" ) String password );

    @GET
    @Path( "get-method" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response demoGetMethod();

    @POST
    @Path( "post-method" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response demoPostMethod();

    @POST
    @Path( "logout" )
    public Response logout(
            @Context HttpHeaders httpHeaders
    );
}
