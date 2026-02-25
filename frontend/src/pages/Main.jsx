import { Link , useNavigate } from 'react-router-dom';
import Figure from '../components/Figure'
import Form from '../components/Form'
import Table from '../components/Table'
import { useDispatch } from 'react-redux';
import { useLogoutMutation } from '../redux/api/mainApi';

import styles from './Main.module.css'

export default function MainPage() {
      const dispatch = useDispatch();
      const navigate = useNavigate();
      const [logout, { isLoading }] = useLogoutMutation();
      const handleLogout = async () => {
          try {
            await logout().unwrap();
            navigate('/');
          } catch (error) {
            console.error('Logout failed:', error);
          }
        };


  return (
      <div className={styles.fullPage}>
          <div className={styles.wrapper}>
        <Figure/>
        <Form/>
        <button onClick={handleLogout} className={styles.button}>
            Выйти
        </button>
          </div>
        <Table/>
    </div>
  )
}
