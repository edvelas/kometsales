package co.com.kometsales.appapi.utils.sendemail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import co.com.kometsales.appapi.utils.sendemail.dto.Mail;

/**
 * Clase encargada de enviar los correos electronicos del sistema
 * 
 * @author ED
 *
 */
@Component
public class SendEmail {

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * Metodo para el envio de correo
	 * 
	 * @param mail
	 */
	public void sendEmailApp(Mail mail) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setSubject(mail.getSubject());
		message.setText(mail.getContent());
		message.setTo(mail.getTo());
		message.setFrom(mail.getFrom());

		javaMailSender.send(message);
	}
}
