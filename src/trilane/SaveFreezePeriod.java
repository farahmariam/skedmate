package trilane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class SaveFreezePeriod
 */
@WebServlet("/SaveFreezePeriod")
public class SaveFreezePeriod extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveFreezePeriod() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		savePeriod(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		savePeriod(request,response);
	}
	
	protected void savePeriod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    BufferedWriter objWriter=  null;
	    HttpSession session = request.getSession();
	    
	    String adminName =(String)session.getAttribute("loginname");
		
	    
	    ServletContext context = request.getServletContext();
		
		int freeze_period = Integer.parseInt(request.getParameter("freezeperiod"));
		
		String successMessage="";
		String errorMessage="";
		
		
		try
		{
			String companyLoginId = (String)session.getAttribute("companyLoginId");
			String strLogPath = context.getInitParameter("logfile-path");
			String fileName = companyLoginId + ".txt";
			strLogPath = strLogPath.concat(fileName);
			File strFile = new File(strLogPath);
			if (!strFile.exists())
			{
				strFile.createNewFile();
			}
			
			
			 //File appending
			objWriter = new BufferedWriter(new FileWriter(strFile,true));
			
			
			//get current date time
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			CommonDatabase objCommon = new CommonDatabase();
		
			boolean saveFreezePeriod = objCommon.SaveFreezePeriod(freeze_period, context, session);
			if(saveFreezePeriod)
			{
				successMessage = dateFormat.format(calendar.getTime()) +  " -  Freeze period has been updated to " + freeze_period + " by admin: " + adminName +".";
				response.getWriter().write("Saved freeze period!");
				
				objWriter.write(successMessage);
				objWriter.newLine();
				objWriter.newLine();
			}
			else
			{
				errorMessage = dateFormat.format(calendar.getTime()) + " - Error occured while trying to change freeze period to " + freeze_period + ". Edit tried by admin:" + adminName + ".";
				response.getWriter().write("Error");
				
				objWriter.write(errorMessage);
				objWriter.newLine();
				objWriter.newLine();
			}
			
			
				
			
			objWriter.flush();
		}
		
		catch(Exception e)
		{
	      //Handle errors for Class.forName
			System.out.println(e.toString());
	   	}
		finally
		{
		if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
			 
			
		}
	}

}
