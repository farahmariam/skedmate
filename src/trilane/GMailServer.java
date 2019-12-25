package trilane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class GMailServer extends javax.mail.Authenticator {
	
	
	private String mailhost ="smtp.gmail.com";//"mail.cisco.com";//"smtp.gmail.com";//"https://mail.cisco.com/ews/exchange.asmx" ;//"smtp.gmail.com"; //"smtp.mail.yahoo.com";//"smtp.gmail.com"; ; //"smtp.mail.yahoo.com"; //"smtp.gmail.com";
	private String user;
	private String password;
	private Session session;
	
	public GMailServer(String user, String password)
	{
		this.user = user;
		this.password = password;
		Properties props = new Properties();
		
	
		props.setProperty("mail.transport.protocol", "smtp");
		
		props.setProperty("mail.smtp.host", mailhost);
		props.put("mail.smtp.auth", "true");
		
		
	    props.put("mail.smtp.port", "587");
		//props.put("mail.smtp.port", "465");
		
		
		props.put("mail.smtp.socketFactory.port", "465");
		
		
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		//props.put("mail.smtps.ssl.protocols","TLSv1.2");
		java.security.Security.setProperty("jdk.tls.disabledAlgorithms","");
		
		
		
		props.put("mail.smtp.starttls.enable","true");
		
		//added for gcp
		props.put("mail.smtp.ssl.trust", mailhost);
		
		
	
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtps.quitwait", "false"); 
		
		session = Session.getInstance(props,this);
		
	}
	
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(user, password);
	}
	
	public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception
	{
		MimeMessage message = new MimeMessage(session);
		DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/html"));
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);
		message.setDataHandler(handler);
		
		if (recipients.indexOf(',') > 0)
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
		Transport.send(message);
		
		
		
	}
	
	
	
		public class ByteArrayDataSource implements DataSource 
		{
			private byte[] data;
			private String type;
			
			public ByteArrayDataSource(byte[] data, String type)
			{
				super();
				this.data = data;
				this.type = type;
			}
			public ByteArrayDataSource(byte[] data)
			{
				super();
				this.data = data;
			}
			
			public void setType(String type) 
			{
				this.type = type;
			}
			
			public String getContentType() 
			{
				
				if (type == null)
					return "application/octet-stream";
				else
				 return type;
			}
				
			public InputStream getInputStream() throws IOException
			{
					return new ByteArrayInputStream(data);
			}
			
			public String getName()
			{
				return "ByteArrayDataSource";
			}
			
			public OutputStream getOutputStream() throws IOException 
			{
				throw new IOException("Not Supported");
			}
		}

}
