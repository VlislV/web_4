import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLoginMutation, useRegisterMutation } from '../redux/api/mainApi';
import { useSelector } from 'react-redux';

import styles from './Login.module.css'

function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [isRegister, setIsRegister] = useState(false);
    const [localError, setLocalError] = useState('');

    const navigate = useNavigate();

    const [login, { error: loginError }] = useLoginMutation();
    const [register, { error: registerError }] = useRegisterMutation();
    const { userError } = useSelector((state) => state.user);

   const handleSubmit = async (e) => {
     e.preventDefault();
     setLocalError('');

     if (!username.trim() || !password.trim()) {
       setLocalError('Заполните все поля');
       return;
     }

     try {
       if (isRegister) {
         await register({ username, password }).unwrap();
       } else {
         await login({ username, password }).unwrap();
       }
       navigate('/main');
     } catch (err) {
         console.error(err);
         setLocalError(err?.data?.message || err?.message || "Ошибка сервера");
     }
   };

    useEffect(() => {
       if(userError) {
           setLocalError(userError);
           }
     }, [userError]);

  const handleToggleMode = () => {
    setIsRegister(!isRegister);
    setLocalError('');
    setUsername('');
    setPassword('');
  };
    const error = loginError || registerError
    const displayError = localError || error?.data?.message || error?.message;

  return (
    <div className={styles.fullPage}>
      <div className={styles.wrapper}>

        <h2>{isRegister ? 'Регистрация' : 'Вход'}</h2>

        <form onSubmit={handleSubmit} className={styles.loginForm}>
          <div className={styles.formUnit}>
            <label htmlFor="username">Логин:</label>
            <input
              type="text"
              maxLength="30"
              minLength="4"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              placeholder="Введите логин"
            />
          </div>

          <div className={styles.formUnit}>
            <label htmlFor="password">Пароль:</label>
            <input
              type="password"
              maxLength="30"
              minLength="4"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Введите пароль"
            />
          </div>

          {displayError && (
            <div className={styles.errorMessage}>
              {displayError?.message || displayError || 'Критическая ошибка'}
            </div>
          )}

          <button
            type="submit"
            className={styles.submit}
          >
            {isRegister ? 'Зарегистрироваться' : 'Войти'}
          </button>

          <button
            type="button"
            onClick={handleToggleMode}
            className={styles.toggleButton}
          >
            {isRegister ? 'Уже есть аккаунт? Войти' : 'Нет аккаунта? Зарегистрироваться'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;