package beans;
import DTOs.PointDTO;
import Utils.Checker;
import Utils.Converter;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class PointManager {
    private static final Logger logger = Logger.getLogger(PointManager.class.getName());

    Checker checker = new Checker();

    @PersistenceContext
    private EntityManager em;

    public String check(PointDTO point) throws Exception {
        return checker.check(point);
    }

    public PointDTO addPoint(PointDTO dto) {
        try {
            Point entity = Converter.toEntity(dto);
            em.persist(entity);
            return Converter.toDTO(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка добавления точки в бд: " + e.getMessage());
        }
    }

    public List<PointDTO> getPoints() {
        try {
            return Converter.toDTOList(em.createQuery("SELECT p FROM Point p", Point.class).getResultList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
