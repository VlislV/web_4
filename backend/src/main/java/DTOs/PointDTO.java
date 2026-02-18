package DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDTO implements Serializable {
    private Integer X;
    private Double Y;
    private Integer R;
    private String result;
    private Date stime;
    private Date rtime;
}