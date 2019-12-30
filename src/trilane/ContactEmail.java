package trilane;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ContactEmail
 */
@WebServlet("/ContactEmail")
public class ContactEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactEmail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		emailContact(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		emailContact(request,response);
	}
	
	 private void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "http://trilanetech.tk");
	      resp.setHeader("Access-Control-Allow-Methods", "POST");
	      resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
	      resp.setHeader("Access-Control-Max-Age", "86400");
	  }
	 

	  //for Preflight
	  @Override
	  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException
	  {
	      setAccessControlHeaders(resp);
	      resp.setStatus(HttpServletResponse.SC_OK);
	  }
	
	protected void emailContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		setAccessControlHeaders(response);
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    try
	    {
	    	
	    //System.out.println("reached email sending pgm..." );
	    
		String name  =  request.getParameter("name");
		String email  =  request.getParameter("email");
		String phone  =  request.getParameter("phone");
		String subjectfromclient  =  request.getParameter("subject");
		String message  =  request.getParameter("message");
		
		String fromEmail = "selectivetestsmail@gmail.com";
		String fromPassword = "testSelective123";
		
		String toEmail = "selectivetestsmail@gmail.com";;
		
		GMailServer sender = new GMailServer(fromEmail, fromPassword);
		
		StringBuilder mailbody = new StringBuilder();
		
		String subject = "Enquiry for TrilaneTech : " + subjectfromclient;
		
		mailbody.append("<html><body>");
		
		mailbody.append("<table style='border:2px solid black'>");
	
	
		mailbody.append("<tr bgcolor=\"#EE485E\" >");
		mailbody.append("<th colspan='2'>");
		mailbody.append("Enquiry/Message ");
		mailbody.append("</th>");
		
		mailbody.append("</tr>");
		
		mailbody.append("<tr>");
		
		mailbody.append("<th>");
		mailbody.append("Name:");
		mailbody.append("</th>");
		
		mailbody.append("<th>");
		mailbody.append(name);
		mailbody.append("</th>");
		
		mailbody.append("</tr>");
		
		
		mailbody.append("<tr >");
		
		mailbody.append("<th>");
		mailbody.append("Email:");
		mailbody.append("</th>");
		
		mailbody.append("<th>");
		mailbody.append(email);
		mailbody.append("</th>");
		
		mailbody.append("</tr>");
		
		mailbody.append("<tr >");
		mailbody.append("<th>");
		mailbody.append("Phone:");
		mailbody.append("</th>");
		
		mailbody.append("<th>");
		mailbody.append(phone);
		mailbody.append("</th>");
		mailbody.append("</tr>");
		
		
		mailbody.append("<tr >");
		mailbody.append("<th>");
		mailbody.append("Message:");
		mailbody.append("</th>");
		
		mailbody.append("<th>");
		mailbody.append(message);
		mailbody.append("</th>");
		mailbody.append("</tr>");
		
		
		
		
		
		mailbody.append("</table></body></html>");
		
		
		sender.sendMail(subject,mailbody.toString(),fromEmail,toEmail);
		System.out.println("Email fom contact us at trillane home Sent Succesfully..." );
		response.getWriter().write("email");
		
		
		
		
	    }
	    catch(Exception ex)
	    {
	    	System.out.println(ex.toString() );
	    	//response.getWriter().write("error");
	    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	response.setContentType("text/plain");
	    	response.getWriter().write(buildErrorMessage(ex));
	    	
	    }
	    catch (Throwable e) 
	    {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.setContentType("text/plain");
	        response.getWriter().println(buildErrorMessage(e));
	    }
		
		
		
		
	}
	
	private static String buildErrorMessage(Exception e) {
	    String msg = e.toString() + "\r\n";

	    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
	        msg += "\t" + stackTraceElement.toString() + "\r\n";
	    }

	     return msg;
	}

	private static String buildErrorMessage(Throwable e) {
	    String msg = e.toString() + "\r\n";

	    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
	        msg += "\t" + stackTraceElement.toString() + "\r\n";
	    }

	    return msg;
	}

}
