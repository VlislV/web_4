package api;

import DTOs.PointDTO;
import api.annotations.ReqJWT;
import beans.PointManager;
import beans.Tokenizer;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.*;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {
    private static final Logger lg = Logger.getLogger(PointResource.class.getName());
    @EJB
    PointManager pm;

    @EJB
    Tokenizer tokenizer;

    @OPTIONS
    @Path("{path: .*}")
    public Response handleOptions() {
        return addCorsHeaders(Response.ok());
    }

    @POST
    @Path("/check")
    @ReqJWT
    public Response check(PointDTO point, @Context HttpHeaders headers,
                          @Context HttpServletRequest request) {
        try {
            String result = pm.check(point);
            point.setResult(result);
            PointDTO saved = pm.addPoint(point, getUsername(headers, request));
            return addCorsHeaders(Response.ok(saved));
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> body = Collections.singletonMap("message", "Ошибка сервера: " + e.getMessage());
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(body));
        }
    }

    @GET
    @ReqJWT
    public Response getPoints( @Context HttpHeaders headers,
                               @Context HttpServletRequest request) {
        try {
            return addCorsHeaders(Response.ok(pm.getPoints(getUsername(headers, request))));
        } catch (Exception e) {
            Map<String, String> body = Collections.singletonMap("message", "Ошибка сервера: " + e.getMessage());
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(body));
        }
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

    private String getUsername(HttpHeaders headers, HttpServletRequest request) {
        Cookie jwtCookie = headers.getCookies().get("jwt_token");
        if (jwtCookie != null) {
            String token = jwtCookie.getValue();
            Claims claims = tokenizer.validateToken(token);
            if (claims != null) return claims.getSubject();
        }
        return null;
    }
}
