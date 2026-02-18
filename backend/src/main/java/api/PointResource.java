package api;

import DTOs.PointDTO;
import beans.PointManager;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.Map;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {
    @EJB
    PointManager pm;

    @OPTIONS
    @Path("{path: .*}")
    public Response handleOptions() {
        return addCorsHeaders(Response.ok());
    }

    @POST
    @Path("/check")
    public Response check(PointDTO point) {
        try {
            String result = pm.check(point);
            point.setResult(result);
            PointDTO saved = pm.addPoint(point);
            return addCorsHeaders(Response.ok(saved));
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> body = Collections.singletonMap("message", "Ошибка сервера: " + e.getMessage());
            return addCorsHeaders(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(body));
        }
    }

    @GET
    public Response getPoints() {
        try {
            return addCorsHeaders(Response.ok(pm.getPoints()));
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
}
