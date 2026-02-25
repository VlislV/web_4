package beans;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name="points")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="x")
    private int x;

    @Column(name="y")
    private double y;

    @Column(name="r")
    private int r;

    @Column(name="result")
    private String result;

    @Column(name="username")
    private String username;

}


