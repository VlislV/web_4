package beans;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "users")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private byte[] password;

    @Column(name = "salt", nullable = false)
    private String salt;
    public User() {}
    User(String username, byte[] password, String salt){
        this.password = password;
        this.username = username;
        this.salt = salt;
    }
}