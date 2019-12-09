package trilane;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EmailExists
 */
@WebServlet("/EmailExists")
public class EmailExists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailExists() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		emailexists(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void emailexists(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    ServletContext context = request.getServletContext();
	    HttpSession session = request.getSession();
	    
	    String email = request.getParameter("email");
	   // System.out.println("email " + email);
	    
	    CommonDatabase objCommon = new CommonDatabase();
	    boolean exists = objCommon.checkEmailExistsInGlobalAdmin(context, session, email);
	    //System.out.println("email exists? " + exists);
	    if(exists)
	    {
	    	response.getWriter().write("email exists");
	    }
	    else
	    {
	    	response.getWriter().write("email does not exist");
	    }
		
		
	}

}
