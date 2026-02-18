package beans;

import Utils.CastomException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Stateless
public class UserManager {
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    String pepper = "EGOSHIN";

    @PersistenceContext
    private EntityManager em;


    public String register(String username, String password)throws Exception {

            if (em == null) {
                logger.severe("EntityManager is NULL!");
                throw new CastomException(String.valueOf(Response.Status.SERVICE_UNAVAILABLE));
            }

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String salt = RandomStringUtils.random(new Random().nextInt(10, 34), true, true);
            byte[] hash = md.digest(
                    (password + salt + pepper).getBytes(StandardCharsets.UTF_8));

            User user = new User(username, hash, salt);
            em.persist(user);

            return "Success. userID " + user.getId();
    }

    public Boolean login(String username, String password) throws Exception{

            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            List<User> users = query.setParameter("username", username)
                    .getResultList();

            if (users.isEmpty()) {
                return false;
            }

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] nhash;
            for(User el : users){
                nhash = md.digest(
                        (password + el.getSalt() + pepper).getBytes(StandardCharsets.UTF_8));
                if(Arrays.equals(nhash, el.getPassword())){
                    return true;
                }
            }

            throw new CastomException("UNAUTHORIZED");
    }
}