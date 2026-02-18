package Utils;

import DTOs.PointDTO;
import beans.Point;

import java.util.List;
import java.util.stream.Collectors;
public class Converter {

    public static PointDTO toDTO(Point point) {
        if (point == null) return null;
        return PointDTO.builder()
                .X(point.getX())
                .Y(point.getY())
                .R(point.getR())
                .result(point.getResult())
                .build();
    }

    public static Point toEntity(PointDTO dto) {
        if (dto == null) return null;
        Point point = new Point();
        point.setX(dto.getX() != null ? dto.getX() : 0);
        point.setY(dto.getY() != null ? dto.getY() : 0.0);
        point.setR(dto.getR() != null ? dto.getR() : 0);
        if (dto.getResult() != null) {
            point.setResult(dto.getResult());
        }
        return point;
    }
    public static List<PointDTO> toDTOList(List<Point> points) {
        if (points == null) return null;
        return points.stream()
                .map(Converter::toDTO)
                .collect(Collectors.toList());
    }
}