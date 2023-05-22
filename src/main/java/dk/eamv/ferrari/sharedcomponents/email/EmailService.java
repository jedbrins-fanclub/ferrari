package dk.eamv.ferrari.sharedcomponents.email;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class EmailService {

    public static void sendEmail() {
        Email email = EmailBuilder.startingBlank()
                .from("Sælgeren", "ferrari.herning.seller@gmail.com")
                .to("Salgschefen", "ferrari.herning.salesman@gmail.com")
                .withSubject("Lån overskrider min grænse")
                .withPlainText("""
                        Hej salgschef
                        
                        Jeg har oprettet et lånetilbud som overskrider min tilladte beløbsgrænse.
                        Vil du godkende aftalen?
                        
                        Mvh
                        Sælger
                        """)
                .buildEmail();


        new Thread(() -> MailerBuilder
                .withSMTPServer("Smtp.gmail.com", 587, "ferrari.herning.seller@gmail.com", "jeetyoldfpfisysb")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email)).start();
    }
}
