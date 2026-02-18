import { useDispatch, useSelector } from 'react-redux';
import { useCheckPointMutation } from '../redux/api/mainApi';
import { setX, setY, setR, resetCoordinates } from '../redux/slices/pointSlice';
import React, { useState, useEffect } from 'react';

import styles from './Form.module.css'

export default function Form() {
   const xOptions = ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'];
   const rOptions = ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'];

   const dispatch = useDispatch();

    const x = useSelector(state => state.point.X);
    const y = useSelector(state => state.point.Y);
    const r = useSelector(state => state.point.R);
    const [displayError, setDisplayError] = useState(null);
    const { pointError } = useSelector((state) => state.point);

     useEffect(() => {
         setDisplayError(pointError);
         }, [pointError]);

  const [checkPoint, { isLoading: isChecking }] = useCheckPointMutation();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (pointError) {
      return;
    }
    try {
      await checkPoint({x, y, r}).unwrap();
    } catch (err) {
      console.error('Check point failed:', err);
    }
  };

   const handleYChange = (event) => {
      const value = event.target.value;
      const numValue = parseFloat(value);
      dispatch(setY(value));
    };

    const handleReset = () => {
      dispatch(resetCoordinates());
    };

     const handleRChange = (option) => {
          dispatch(setR(option));
      };

return (
  <form className="form-block" onSubmit={handleSubmit}>

    <div className={styles.formUnit}>

      <div className="input-section">

        <label className="form-label">Выберите X:</label>

        <div className={styles.buttonGroup}>

          {xOptions.map(option => (
            <button
              key={`x-${option}`}
              type="button"
              className={x === option ? styles.buttonActive : styles.button}
              onClick={() => dispatch(setX(option))}
            >
              {option}
            </button>
          ))}
        </div>
      </div>

      <div className={styles.formUnit}>
        <label className="form-label" htmlFor="y-input">
          Введите Y (-3 ... 3):
        </label>
        <input
          id="y-input"
          className={`form-input`}
          type="text"
          maxLength="10"
          value={y}
          onChange={handleYChange}
          placeholder="Например: 1.5 или -2"
        />
      </div>

      <div className={styles.formUnit}>
        <label className="form-label">Выберите R:</label>
        <div className={styles.buttonGroup}>
          {rOptions.map(option => (
            <button
              key={`r-${option}`}
              type="button"
              className={r === option ? styles.buttonActive : styles.button}
              onClick={() => handleRChange(option)}
            >
              {option}
            </button>
          ))}
        </div>
      </div>
      {displayError && (
          <div className={styles.errorMessage}>
            {displayError?.message || displayError || 'Критическая ошибка'}
          </div>
        )}

      <div className={styles.buttonGroup}>
        <button type="submit" className={styles.buttonSubmit} disabled={isChecking}>
          {isChecking ? 'Проверка...' : 'Проверить точку'}
        </button>
        <button
          type="button"
          className={styles.buttonReset}
           onClick={handleReset}
        >
          Сбросить
        </button>
      </div>

    </div>
  </form>
);
}
