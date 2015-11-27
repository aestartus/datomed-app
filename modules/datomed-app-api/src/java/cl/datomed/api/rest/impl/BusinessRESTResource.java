package cl.datomed.api.rest.impl;

import cl.datomed.api.Authenticator;
import cl.datomed.api.rest.BusinessRESTResourceProxy;
import cl.datomed.api.rest.HTTPHeaderNames;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.security.GeneralSecurityException;

/**
 * Created by aestartus on 18-11-15.
 */
@Stateless( name = "DemoBusinessRESTResource", mappedName = "ejb/BusinessRESTResource" )
public class BusinessRESTResource implements BusinessRESTResourceProxy {

    private static final long serialVersionUID = -6663599014192066936L;

    @Override
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam( "username" ) String username,
            @FormParam( "password" ) String password ) {

        Authenticator authenticator = Authenticator.getInstance();
        String serviceKey = httpHeaders.getRequestHeader(HTTPHeaderNames.SERVICE_KEY).get(0);

        try {
            String authToken = authenticator.login( serviceKey, username, password );

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "auth_token", authToken );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();

        } catch ( final LoginException ex ) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Problem matching service key, username and password" );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        }
    }

    @Override
    public Response demoGetMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add( "message", "Executed demoGetMethod" );
        JsonObject jsonObj = jsonObjBuilder.build();

        return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
    }

    @Override
    public Response demoPostMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add( "message", "Executed demoPostMethod" );
        JsonObject jsonObj = jsonObjBuilder.build();

        return getNoCacheResponseBuilder( Response.Status.ACCEPTED ).entity( jsonObj.toString() ).build();
    }

    @Override
    public Response logout(
            @Context HttpHeaders httpHeaders ) {
        try {
            Authenticator authenticator = Authenticator.getInstance();
            String serviceKey = httpHeaders.getRequestHeader( HTTPHeaderNames.SERVICE_KEY ).get(0);
            String authToken = httpHeaders.getRequestHeader( HTTPHeaderNames.AUTH_TOKEN ).get(0);

            authenticator.logout( serviceKey, authToken );

            return getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).build();
        } catch ( final GeneralSecurityException ex ) {
            return getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }
    }

    private Response.ResponseBuilder getNoCacheResponseBuilder( Response.Status status ) {
        CacheControl cc = new CacheControl();
        cc.setNoCache( true );
        cc.setMaxAge( -1 );
        cc.setMustRevalidate( true );

        return Response.status( status ).cacheControl( cc );
    }
}
