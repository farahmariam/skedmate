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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class SaveStatusColours
 */
@WebServlet("/SaveStatusColours")
public class SaveStatusColours extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveStatusColours() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveColours(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveColours(request,response);
	}
	
	
	protected void saveColours(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    BufferedWriter objWriter=  null;
	    HttpSession session = request.getSession();
	    String colourerrorMessage  = "";
	    
	    String adminName =(String)session.getAttribute("loginname");
		
	    
	    ServletContext context = request.getServletContext();
		
		
		 Gson gson = new Gson();
		 String[] statusColours = gson.fromJson(request.getParameter("jsonString"), String[].class);

		//System.out.println("reached savestatuscolours");
		
		
		//System.out.println("team limit array: " + teamLimits.toString());
		
		
		
		Connection connection = null;		
		Statement stColours = null;
		ArrayList<String> colourList = new ArrayList();
		
		
		try
		{
			
			
			
			for( int i = 0; i < statusColours.length ; i++)
			{
				colourList.add(statusColours[i]);
			    
			}
			
			
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
		
			
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String databaseUrl = (String) session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			ArrayList<StatusObject> AllStatusList = null;
			AllStatusList = objCommon.getAllStatus(context, session);
			stColours = connection.createStatement();
			
			String coloursuccessMessage =dateFormat.format(calendar.getTime()) +  " - " ;
			colourerrorMessage =  dateFormat.format(calendar.getTime()) + " - Error occured while trying to change  status colours."  ;
			colourerrorMessage = colourerrorMessage.concat(" Edit tried by admin:" + adminName + ".");
			
			
			for(int i=0;i<AllStatusList.size();i++)
			{
				StatusObject status = AllStatusList.get(i);
				int status_id=status.getStatusId();
				String statusName = status.getStatusName();
				String newColour = colourList.get(i);
				
				String sqlQuery = "update status set status_colour='" + newColour  + "' where status_id=" + status_id;
				int j = stColours.executeUpdate(sqlQuery);
				if(j>0)
				{
					coloursuccessMessage = coloursuccessMessage.concat(statusName + " display colour set to " + newColour);
					
				}
				
				if(i!=(AllStatusList.size()-1))
				{
					
					coloursuccessMessage = coloursuccessMessage.concat(" and ");
					
				}
				
			}
			
			coloursuccessMessage = coloursuccessMessage.concat( ". Colours saved by admin: " + adminName +".");
			
		
				
			response.getWriter().write("Saved status display colours!");
			
			objWriter.write(coloursuccessMessage);
			objWriter.newLine();
			objWriter.newLine();
			
			
			
		}
		catch(SQLException se)
		{
	      //Handle errors for JDBC
			response.getWriter().write("Error");
			
			objWriter.write(colourerrorMessage);
			objWriter.newLine();
			objWriter.newLine();
	      System.out.println(se.toString());
	    }
		catch(Exception e)
		{
			response.getWriter().write("Error");
			
			objWriter.write(colourerrorMessage);
			objWriter.newLine();
			objWriter.newLine();
	      //Handle errors for Class.forName
			System.out.println(e.toString());
	   	}
		finally
		{
			objWriter.flush();
			if (stColours != null) try { stColours.close(); } catch (SQLException e) {e.printStackTrace();}
			
			
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
			 
			
		}
		
	}

}
