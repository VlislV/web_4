import FooterStyles from './Footer.module.css'

export default function Footer(){
    return(
        <footer className={FooterStyles.footer}>
            <div className={FooterStyles.container}>
                <span>СИМПЛ ТЕКСТ</span>
            </div>
        </footer>
    )
}