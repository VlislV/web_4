package api.filters;

import api.annotations.ReqJWT;
import beans.Tokenizer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Map;


@Provider
@ReqJWT
public class JWT_IN_Filter implements ContainerRequestFilter {
    @EJB
    private Tokenizer tokenizer;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String token = null;
        Map<String, Cookie> cookies = containerRequestContext.getCookies();
        Cookie jwtCookie = cookies.get("jwt_token");
        if (jwtCookie != null) {
            token = jwtCookie.getValue();
        }

        if (token == null) {
            abortWithMessage(containerRequestContext, "No token provided");
            return;
        }

        try {
            Claims claims = tokenizer.validateToken(token);
            if (claims == null) {
                abortWithMessage(containerRequestContext, "Invalid token");
                return;
            }
            containerRequestContext.setProperty("username", claims.getSubject());

        } catch (Exception e) {
            abortWithMessage(containerRequestContext, "Token validation failed");
        }
    }

    private void abortWithMessage(ContainerRequestContext ctx, String message) {
        ctx.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"" + message + "\"}")
                        .build()
        );
    }
}
