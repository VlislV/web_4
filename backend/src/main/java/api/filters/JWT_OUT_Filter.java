package api.filters;

import api.annotations.GenJWTinSuccess;
import api.annotations.ReqJWT;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import beans.Tokenizer;
import java.io.IOException;
import java.util.Map;

import jakarta.ejb.EJB;

@Provider
@GenJWTinSuccess
public class JWT_OUT_Filter implements ContainerResponseFilter {

    @EJB
    private Tokenizer tokenizer;

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {

        String path = containerRequestContext.getUriInfo().getPath();

        if (path.contains("/logout") || path.endsWith("logout")) {
            removeCookie(containerResponseContext);
            return;
        }
        if (containerResponseContext.getStatus() == 200) {
            setCookie(containerResponseContext);
        }
    }

    private void removeCookie(ContainerResponseContext RespCtx) {
        try {
            NewCookie deleteCookie = new NewCookie(
                    "jwt_token",
                    "",
                    "/",
                    null,
                    null,
                    0,
                    false,
                    true
            );

            RespCtx.getHeaders().add("Set-Cookie", deleteCookie.toString());
        } catch (Exception e) {
            e.printStackTrace();
            RespCtx.setStatus(500);
        }
    }

    private void setCookie(ContainerResponseContext RespCtx) {
        try {
            Object entity = RespCtx.getEntity();

            if (entity instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) entity;
                Object usernameObj = map.get("username");

                if (usernameObj != null) {
                    String username = usernameObj.toString();
                    String token = tokenizer.generateToken(username);

                    NewCookie cookie = new NewCookie(
                            "jwt_token",
                            token,
                            "/",
                            null,
                            null,
                            3600,
                            false,
                            true
                    );

                    RespCtx.getHeaders().add("Set-Cookie", cookie.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            RespCtx.setStatus(500);
        }
    }
}
