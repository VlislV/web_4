package api;

import DTOs.UserDTO;
import Utils.CastomException;
import api.annotations.GenJWTinSuccess;
import beans.UserManager;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @EJB
    UserManager um;

    @OPTIONS
    @Path("{path: .*}")
    public Response handleOptions() {
        return addCorsHeaders(Response.ok());
    }

    @POST
    @Path("/register")
    @GenJWTinSuccess
    public Response register(UserDTO userDTO) {
        try {
            um.register(userDTO.getUsername(), userDTO.getPassword());
            Map<String, String> body = Collections.singletonMap("username", userDTO.getUsername());
            return addCorsHeaders(Response.ok().entity(body));
        } catch (CastomException e) {
            return addCorsHeaders(Response.status(Response.Status.valueOf(e.getMessage())));
        } catch (Exception e) {
            Map<String, String> body = Collections.singletonMap("message", "Server error: " + e.getMessage());
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(body));
        }
    }

    @POST
    @Path("/login")
    @GenJWTinSuccess
    public Response login(UserDTO userDTO) {
        try {
            Boolean flag = um.login(userDTO.getUsername(), userDTO.getPassword());
            if (flag) {
                Map<String, String> body = Collections.singletonMap("username", userDTO.getUsername());
                return addCorsHeaders(Response.ok().entity(body));
            } else {
                Map<String, String> body = Collections.singletonMap("message", "User with name '" + userDTO.getUsername() + "' not found");
                return addCorsHeaders(Response.status(Response.Status.NOT_FOUND)
                        .entity(body));
            }
        } catch (CastomException e) {
            return addCorsHeaders(Response.status(Response.Status.valueOf(e.getMessage().toUpperCase(Locale.ROOT))));
        }catch (Exception e) {
            Map<String, String> body = Collections.singletonMap("message", "Server error: " + e.getMessage());
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(body));
        }
    }

    @POST
    @Path("/logout")
    @GenJWTinSuccess
    public Response logout() {
        return Response.ok("{\"message\":\"Logged out\"}")
                .build();
    }

    private Response addCorsHeaders(Response.ResponseBuilder builder) {
        return builder
                .header("Access-Control-Allow-Origin", "http://localhost:5174")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Max-Age", "86400")
                .build();
    }
}