import FooterStyles from './Footer.module.css'
import {useSelector } from 'react-redux';
export default function Footer(){
     const username = useSelector(state => state.user.username);
    return(
        <footer className={FooterStyles.footer}>
            <div className={FooterStyles.container}>
                <span>{(username == null ? "SIMPLE TEXT": "Вы зашли под ником: " + username)}</span>
            </div>
        </footer>
    )
}