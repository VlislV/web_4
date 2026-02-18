import { useGetPointsQuery } from '../redux/api/mainApi';

import styles from './Table.module.css'


export default function Table() {
  const { data: points = [], isLoading, isError } = useGetPointsQuery();

  if (isLoading) {
    return (
      <div className={styles.pointTable}>
        <p className="empty-message">Загрузка...</p>
      </div>
    );
  }
  if (isError) {
    return (
      <div className={styles.pointTable}>
        <p className="error-message">Ошибка загрузки точек</p>
      </div>
    );
  }
  if (!points || points.length === 0) {
    return (
      <div className={styles.pointTable}>
        <p className="empty-message">Нет данных для отображения</p>
      </div>
    );
  }
  return (
      <div className={styles.pointTable}>
        <table>
          <thead>
            <tr>
              <th>X</th>
              <th>Y</th>
              <th>R</th>
              <th>RESULT</th>
            </tr>
          </thead>
          <tbody>
            {points.map((point, index) => (
              <tr key={point.id ?? index}>
                <td>{point.x ?? point.X}</td>
                <td>{point.y ?? point.Y}</td>
                <td>{point.r ?? point.R}</td>
                <td className={`result ${(point.result || '').toLowerCase()}`}>
                  {point.result || '—'}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
}