package trilane;

import java.io.IOException;
import java.util.Calendar;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class SendConfirmationEmail
 */
@WebServlet("/SendConfirmationEmail")
public class SendConfirmationEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendConfirmationEmail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		sendEmail(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		sendEmail(request,response);
	}
	
	protected void sendEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		try
		{
			
		
			Gson gson = new Gson();
			
			String strCompany =(String) request.getAttribute("company");
			
			Company objCompany = gson.fromJson(strCompany , Company.class);
			
			String strAdmin =(String) request.getAttribute("admin");
			
			Administrator objAdmin = gson.fromJson(strAdmin , Administrator.class);
			
			System.out.println("company: " + objCompany.getCompanyName());
			
			//user name and password of email account from which mail has to be sent.
			
			String fromEmail = "resourcemailer@gmail.com";
			String fromPassword = "resource_mailer_2016";
			
			String toEmail = objAdmin.getAdminEmail();
			
			GMailServer sender = new GMailServer(fromEmail, fromPassword);
			
			StringBuilder mailbody = new StringBuilder();
			
			String subject = "Welcome to Skedmate!";
			
			mailbody.append("<html><body>");
			mailbody.append("<p> Congratulations! You are almost ready to use the Skedmate Scheduling online application. All you have to do is note down your login id, password and company id! You have to click on the following confirmation link before you can start using the application.</p>"  );
			mailbody.append("<table style='border:2px solid black'>");
			//mailbody.append("<table class='settingstable'>");
		
			mailbody.append("<tr bgcolor=\"#EE485E\" >");
			mailbody.append("<th colspan='2'>");
			mailbody.append("LOGIN DETAILS ");
			mailbody.append("</th>");
			
			mailbody.append("</tr>");
			
			mailbody.append("<tr>");
			
			mailbody.append("<th>");
			mailbody.append("Administrator Login Id:");
			mailbody.append("</th>");
			
			mailbody.append("<th>");
			mailbody.append(objAdmin.getAdminId());
			mailbody.append("</th>");
			
			mailbody.append("</tr>");
			
			
			mailbody.append("<tr >");
			
			mailbody.append("<th>");
			mailbody.append("Administrator Login Password:");
			mailbody.append("</th>");
			
			mailbody.append("<th>");
			mailbody.append(objAdmin.getAdminPassword());
			mailbody.append("</th>");
			
			mailbody.append("</tr>");
			
			mailbody.append("<tr >");
			
			mailbody.append("<th>");
			mailbody.append("Company Login Id:");
			mailbody.append("</th>");
			
			mailbody.append("<th>");
			mailbody.append(objCompany.getCompanyLogin());
			mailbody.append("</th>");
			
			mailbody.append("<tr>");
			mailbody.append("<th colspan='2'>");
			mailbody.append("Please click on this link to confirm and start using the application:  <a href='http://localhost/skedmate/confirm?companyid=");
			
			mailbody.append(objCompany.getCompanyLogin());	
			mailbody.append("' title='Click here to confirm and login'> Confirm and Login </a> ");
			mailbody.append("</th>");
			
			mailbody.append("</tr>");
			
			
			
			
			mailbody.append("</table></body></html>");
			
			
			sender.sendMail(subject,mailbody.toString(),fromEmail,toEmail);
			System.out.println("Email Sent Succesfully...at: " + Calendar.getInstance().getTime().toString() );
			
			response.sendRedirect(getServletContext().getContextPath() +  "/jsp/emailSent.jsp");
			
		
		}
		catch(Exception ex)
		{
			session.setAttribute("errormessage", "Error occured when connecting to smtp host to send confirmation email");
			System.out.println("Error occured while sending confirmation email.." + ex.toString());
		}
		
		
	}

}
