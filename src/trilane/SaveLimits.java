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

import trilane.CommonDatabase;

/**
 * Servlet implementation class SaveLimits
 */
@WebServlet("/SaveLimits")
public class SaveLimits extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveLimits() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveLimits(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveLimits(request,response);
	}
	
	
	
	protected void saveLimits(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    BufferedWriter objWriter=  null;
	    HttpSession session = request.getSession();
	    
	    String adminName =(String)session.getAttribute("loginname");
		
	    
	    ServletContext context = request.getServletContext();
		
		int date = Integer.parseInt(request.getParameter("date"));
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		
		 Gson gson = new Gson();
		 String[] teamLimits = gson.fromJson(request.getParameter("jsonString"), String[].class);

		
		
		
		//System.out.println("team limit array: " + teamLimits.toString());
		
		ArrayList<Integer> limitList = new ArrayList();
		
		for( int i = 0; i < teamLimits.length ; i++)
		{
			limitList.add((Integer.parseInt(teamLimits[i])));
		    
		}
		
		boolean checked = Boolean.parseBoolean(request.getParameter("checked"));
		
		GregorianCalendar cal = new GregorianCalendar (year, month, date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		Connection connection = null;		
		Statement stLimits = null;		 
		
		Statement stLimitsHoliday=null;
		
		
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
		
			
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String databaseUrl = (String) session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			ArrayList<TeamObject> teamList = null;
			teamList = objCommon.getAllTeams(context, session);
			
			
			
			//If it is a public holiday, save limits in public holiday table
			
		
			if(checked)
			{
				stLimitsHoliday = connection.createStatement();
				String holidayLimitsQuery = "update public_holiday set "  ;
				
				String holidaysuccessMessage =dateFormat.format(calendar.getTime()) +  " - " ;
				String holidayerrorMessage =  dateFormat.format(calendar.getTime()) + " - Error occured while trying to change  limits of public holiday on " + date + "/" + objCommon.getMonth(month) + "/" + year  + " to ";
				
				
				//get limit column names
				for(int loop=0; loop<teamList.size(); loop++)
				{
					TeamObject teamObject = teamList.get(loop);
					
					String teamName = teamObject.getTeamName();
					String teamWithoutSpaces = teamName.replaceAll("\\s+","");
					String colName = teamWithoutSpaces.toLowerCase().trim() + "_limit";
					holidayLimitsQuery = holidayLimitsQuery.concat(colName + "=" + limitList.get(loop) );
					
					holidaysuccessMessage = holidaysuccessMessage.concat(teamName + " limit set to " + limitList.get(loop));
					holidayerrorMessage = holidayerrorMessage.concat(limitList.get(loop).toString());
					
					
					
					if(loop!=(teamList.size()-1))
					{
						holidayLimitsQuery = holidayLimitsQuery.concat(" , ");
						holidaysuccessMessage = holidaysuccessMessage.concat(" and ");
						holidayerrorMessage = holidayerrorMessage.concat(" , ");
					}
				}
				
				holidayLimitsQuery = holidayLimitsQuery.concat(" where day = " + date  + " and month = " + month + " and year= " + year);
				holidaysuccessMessage = holidaysuccessMessage.concat(" for public Holiday on:" + date + "/" + objCommon.getMonth(month) + "/" + year + ". Limits saved by admin: " + adminName +".");
				holidayerrorMessage = holidayerrorMessage.concat(". Edit tried by admin:" + adminName + ".");
				
				
				
				
				
				//System.out.println("the query: " + holidayLimitsQuery);
				int i = stLimitsHoliday.executeUpdate(holidayLimitsQuery);
				if (i > 0)
				{
					
					response.getWriter().write("Saved Public Holiday limits!");
					
					objWriter.write(holidaysuccessMessage);
					objWriter.newLine();
					objWriter.newLine();
					
				} 
				else 
				{
					response.getWriter().write("Error");
					
					objWriter.write(holidayerrorMessage);
					objWriter.newLine();
					objWriter.newLine();
				}
				
			}
			else
			{
				//save in table limitcatxr for normal days(not public holiday)
				stLimits = connection.createStatement();
				
				String sqlQuery =  "update team_day_limits set "; // set xr_limit=" + xrLimit + ",cat_limit=" + catLimit + " where day_id=" + dayOfWeek;
				
				String normalsuccessMessage =dateFormat.format(calendar.getTime()) + " - ";
				String normalerrorMessage =  dateFormat.format(calendar.getTime()) + " - Error occured while trying to change  limits to " ;
				
				//get limit column names
				for(int loop=0; loop<teamList.size(); loop++)
				{
					TeamObject teamObject = teamList.get(loop);
					
					String teamName = teamObject.getTeamName();
					String teamWithoutSpaces = teamName.replaceAll("\\s+","");
					String colName = teamWithoutSpaces.toLowerCase().trim() + "_limit";
					sqlQuery = sqlQuery.concat(colName + "=" + limitList.get(loop) );
					
					normalsuccessMessage = normalsuccessMessage.concat(teamName + " limit set to " + limitList.get(loop));
					normalerrorMessage = normalerrorMessage.concat(limitList.get(loop).toString());
					
					
					
					if(loop!=(teamList.size()-1))
					{
						sqlQuery = sqlQuery.concat(" , ");
						normalsuccessMessage = normalsuccessMessage.concat(" and ");
						normalerrorMessage = normalerrorMessage.concat(" , ");
					}
				}
				
				sqlQuery = sqlQuery.concat(" where day_id=" + dayOfWeek);
				normalsuccessMessage = normalsuccessMessage.concat(" for " + date + "/" + objCommon.getMonth(month) + "/" + year + ". Limits saved by admin: " + adminName +".");
				normalerrorMessage = normalerrorMessage.concat(". Edit tried by admin:" + adminName + ".");
				
				
				//System.out.println("the query: " + sqlQuery);
				
				
				
				
				
				
				
				int i = stLimits.executeUpdate(sqlQuery);
				 
				if (i > 0)
				{
					
					response.getWriter().write("Saved limits!");
					
					
					objWriter.write(normalsuccessMessage);
					objWriter.newLine();
					objWriter.newLine();
					
				} 
				else 
				{
					response.getWriter().write("Error");
					
					objWriter.write(normalerrorMessage);
					objWriter.newLine();
					objWriter.newLine();
				}
				
			}
			
			objWriter.flush();
		}
		catch(SQLException se)
		{
	      //Handle errors for JDBC
	      System.out.println(se.toString());
	    }
		catch(Exception e)
		{
	      //Handle errors for Class.forName
			System.out.println(e.toString());
	   	}
		finally
		{
			if (stLimitsHoliday != null) try { stLimitsHoliday.close(); } catch (SQLException e) {e.printStackTrace();}
			
			if (stLimits != null) try { stLimits.close(); } catch (SQLException e) {e.printStackTrace();}
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
			 
			
		}
	}

	

}
