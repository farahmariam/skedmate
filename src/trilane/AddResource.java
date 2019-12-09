package trilane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trilane.CommonDatabase;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddResource
 */
@WebServlet("/AddResource")
public class AddResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddResource() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); 
		ServletContext context = request.getServletContext();
		
		HttpSession session = request.getSession();
		
		
		int userId = Integer.parseInt(request.getParameter("userid"));
		String userName = request.getParameter("username");
		boolean isSaturday = Boolean.parseBoolean(request.getParameter("sat"));
		boolean isSunday = Boolean.parseBoolean(request.getParameter("sun"));
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("date"));
		BufferedWriter objWriter=  null;
		
		int status_id = -1;
		
		CommonDatabase objCommon = new CommonDatabase();
		boolean isPublicHoliday = objCommon.checkPublicHoliday(day, month, year, context,session);
		
		
		
	
		
		String comment= " ";
		Statement st  = null;
		Connection connection  = null;
		
		Statement stGetDefaultStatus = null;
		ResultSet rsGetDefaultStatus = null;	
		Statement stCheckEventExist = null;
		ResultSet rsCheckEventExist = null;	
		
		
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
			Calendar cal = Calendar.getInstance();
			
			
			
			Class.forName("com.mysql.jdbc.Driver");
			
			//String databaseUrl = context.getInitParameter("data-url");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			//getting the default status id from status table( the first status is the default status)
			stGetDefaultStatus = connection.createStatement();
			String getStatus = "select * from status ";
			rsGetDefaultStatus = stGetDefaultStatus.executeQuery(getStatus);
			if(rsGetDefaultStatus.first())
			{
				status_id = rsGetDefaultStatus.getInt("status_id");
			}
			
			
			
			
			String checkIfUserEventExists = "select * from events where day = " + day + " and month = " + month + " and year= " + year + " and user_id = " + userId; 
			stCheckEventExist = connection.createStatement();
			rsCheckEventExist = stCheckEventExist.executeQuery(checkIfUserEventExists);
			
			if(!rsCheckEventExist.next())
			{
				st= connection.createStatement();
				String sqlQuery = "insert into events (user_id, day, month, year, status_id, comment,event_datetime) values (" + userId + " , " + day + "," + month + "," + year + "," + status_id + ",'" + comment + "',CURRENT_TIMESTAMP())";
				int i = st.executeUpdate(sqlQuery);
				//Common objCommon = new Common();
				
				if (i > 0)
				{
					response.getWriter().write("New User added");
					//String successMessage = "New Resource:" + userName + ", added as 'On Shift' on date: " + day +"/" + objCommon.getMonth(month) + "/" + year + ". Info added on: " + dateFormat.format(cal.getTime()) ;
					
					String successMessage =dateFormat.format(cal.getTime()) + " - Resource :" + userName + ". Status: On Shift. Resource added for date:" + day +"/" + objCommon.getMonth(month) + "/" + year + ". Info added by: " + userName  ;
					
					objWriter.write(successMessage);
					objWriter.newLine();
					objWriter.newLine();
					
				}
				else
				{
					//String errorMessage = "Error occured while trying to add new User:" + userName + " on date: " + day +"/" + objCommon.getMonth(month) + "/" + year + ". Date and time: " + dateFormat.format(cal.getTime()) ;
					String errorMessage = dateFormat.format(cal.getTime()) +  " - Error occured while trying to add  resource:" + userName + " on date: " + day +"/" + objCommon.getMonth(month) + "/" + year + ".Edit tried by:" + userName + "." ;
					objWriter.write(errorMessage);
					objWriter.newLine();
					objWriter.newLine();
					
					response.getWriter().write("Error");
				
				
				}
				objWriter.flush();
				
				
			}
			else
			{
				response.getWriter().write("User event exists!");	
			}
			
			
			
			 
		}
		catch(SQLException se)
		{
	      //Handle errors for JDBC
	      se.printStackTrace();
	    }
		catch(IOException e){
			e.printStackTrace();
			System.out.println("error:" + e.getMessage());
		}
		catch(Exception e)
		{
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   	}
		

		finally
		{
			if (stCheckEventExist != null) try { stCheckEventExist.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsCheckEventExist != null) try { rsCheckEventExist.close(); } catch (SQLException e) {e.printStackTrace();}

			if (stGetDefaultStatus != null) try { stGetDefaultStatus.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsGetDefaultStatus != null) try { rsGetDefaultStatus.close(); } catch (SQLException e) {e.printStackTrace();}

			
	   	 	if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
			if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
			
			
			
			   

			
		}
		
		
	}
	
	

}
