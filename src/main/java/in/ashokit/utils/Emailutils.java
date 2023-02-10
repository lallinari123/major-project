package in.ashokit.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Emailutils {
	
@Autowired
private JavaMailSender mailSender;

public boolean sendMail(String to,String subject,String body)
{
	boolean isMailSent=false;
	try
	{
		MimeMessage mimemessage=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimemessage);
		
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body,true);
		mailSender.send(mimemessage);
		isMailSent=true;
	}catch(Exception e)

	{
		e.printStackTrace();
	}
	return isMailSent;
}
}
