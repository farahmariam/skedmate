package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.Calendar;

/**
 * Servlet implementation class EmailAdminLoginCredentials
 */
@WebServlet("/EmailAdminLoginCredentials")
public class EmailAdminLoginCredentials extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailAdminLoginCredentials() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		emailLoginDetails(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		emailLoginDetails(request,response);
	}
	
	protected void emailLoginDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//get the email id
		
		String emailId  =  request.getParameter("email");
		
		//query global_admin  table to get the company id.
		
		 ServletContext context = request.getServletContext();
			
			
		Connection connection = null;
		Statement stGetCompanyId = null;
		ResultSet rsGetCompanyId = null;
		Statement stGetCompanyLogin = null;
		ResultSet rsGetCompanyLogin = null;
		
		String admin_login_id="";
		String admin_login_password="";
		String company_login_id="";
		int companyId=-1;
		
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				//String databaseUrl = "jdbc:mysql://localhost:3306/Company?useSSL=false";
				
				String databasePath = context.getInitParameter("data-url");
				String databaseUrl = databasePath + "Company?useSSL=false";
				
				
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				String globalQuery = "select * from global_admin where admin_email like '%" + emailId  + "%'";
				stGetCompanyId = connection.createStatement();
				rsGetCompanyId = stGetCompanyId.executeQuery(globalQuery);
				if(rsGetCompanyId.next())
				{
					admin_login_id = rsGetCompanyId.getString("uname");
					admin_login_password = rsGetCompanyId.getString("pwd"); 
					companyId = rsGetCompanyId.getInt("company_id");
				}
				
				//query company_details to get the company login id
				stGetCompanyLogin = connection.createStatement();
				String companyQuery="select company_loginid from company_details where company_id= " +  companyId ;
				rsGetCompanyLogin = stGetCompanyLogin.executeQuery(companyQuery);
				if(rsGetCompanyLogin.next())
				{
					company_login_id = rsGetCompanyLogin.getString(1);
				}
				
				//now send email with the details
				//user name and password of email account from which mail has to be sent.
				
				//String fromEmail = "resourcemailer@gmail.com";
				//String fromPassword = "resource_mailer_2016";
				
				String fromEmail = "selectivetestsmail@gmail.com";
				String fromPassword = "testSelective123";
				
				String toEmail = emailId;
				
				GMailServer sender = new GMailServer(fromEmail, fromPassword);
				
				StringBuilder mailbody = new StringBuilder();
				
				String subject = "Skedmate login details!";
				
				mailbody.append("<html><body>");
				mailbody.append("<p> Login Credentials for Skedmate</p>"  );
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
				mailbody.append(admin_login_id);
				mailbody.append("</th>");
				
				mailbody.append("</tr>");
				
				
				mailbody.append("<tr >");
				
				mailbody.append("<th>");
				mailbody.append("Administrator Login Password:");
				mailbody.append("</th>");
				
				mailbody.append("<th>");
				mailbody.append(admin_login_password);
				mailbody.append("</th>");
				
				mailbody.append("</tr>");
				
				mailbody.append("<tr >");
				
				mailbody.append("<th>");
				mailbody.append("Company Login Id:");
				mailbody.append("</th>");
				
				mailbody.append("<th>");
				mailbody.append(company_login_id);
				mailbody.append("</th>");
				
				
				mailbody.append("<tr>");
				mailbody.append("<th colspan='2'>");
				//for windows
				//mailbody.append("Please click on this link to login:  <a href='http://localhost:8080/skedmate/login' title='Click here to login'> Login </a>");
				
				// for gcp
				//mailbody.append("Please click on this link to Login:  <a href='http://34.87.223.156:8080/skedmate/login' title='Click here to login'> Login </a>");
				mailbody.append("Please click on this link to Login:  <a href='http://trilanetech.tk/skedmate/login' title='Click here to login'> Login </a>");
				
				mailbody.append("</th>");
				
				mailbody.append("</tr>");
				
				
				
				mailbody.append("</table></body></html>");
				
				
				sender.sendMail(subject,mailbody.toString(),fromEmail,toEmail);
				System.out.println("Email Sent Succesfully...at: " + Calendar.getInstance().getTime().toString() );
				
				
				response.sendRedirect(getServletContext().getContextPath() +  "/jsp/AdminLoginDetailsSent.jsp");
				
				
				
				
		}
		catch (SQLException e)
		{
			
			System.out.println(e.toString()); 
			
		}
		catch (Exception e)
		{
			
			System.out.println(e.toString()); 
			
		}
		finally
		{
			
			if (stGetCompanyId != null) try { stGetCompanyId.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsGetCompanyId != null) try { rsGetCompanyId.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stGetCompanyLogin != null) try { stGetCompanyLogin.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsGetCompanyLogin != null) try { rsGetCompanyLogin.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
		}


	}

}
