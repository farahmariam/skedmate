package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Servlet implementation class GetMinMaxResource
 */
@WebServlet("/GetMinMaxResource")
public class GetMinMaxResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMinMaxResource() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getMinMax(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getMinMax(request,response);
	}
	
	protected void getMinMax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");

		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		
		int date = Integer.parseInt(request.getParameter("date"));
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		
		GregorianCalendar cal = new GregorianCalendar (year, month, date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		 Connection connection = null;		
		 Statement stGetMinMax = null;		 
		 ResultSet rsGetMinMax  = null;
		 
		 PreparedStatement stCheckHoliday = null;
		 ResultSet rsCheckHoliday = null;
		 
		 Statement stHolidayMinMax=null;
		 ResultSet rsHolidayMinMax = null;
		 
		 
		 ArrayList list = new ArrayList();
		 
		list.add(0, dayOfWeek);
		//System.out.println("day of week: " + dayOfWeek);
		 
		 try
			{
				
					Class.forName("com.mysql.jdbc.Driver");
					String databaseUrl = (String)session.getAttribute("data-url-schedule");
					String databaseUser = context.getInitParameter("data-user");
					String databasePwd = context.getInitParameter("data-pwd");
					connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
					
					//First check if selected day is a public holiday. If so, get the limits from public_holiday table. Else get limits from limitcatxr table.
					
					stCheckHoliday = connection.prepareStatement("SELECT  * FROM public_holiday WHERE " + "day = ? and month=? and year=?");
					stCheckHoliday.setInt(1,date);
					stCheckHoliday.setInt(2,month);
					stCheckHoliday.setInt(3,year);
					rsCheckHoliday = stCheckHoliday.executeQuery();
					
					CommonDatabase obj = new CommonDatabase();
					ArrayList<TeamObject> teamList = obj.getAllTeams(context, session);
					
					int counter=1;
					if(rsCheckHoliday.first())
					{
						stHolidayMinMax = connection.createStatement();
						String holidayQuery = "select * from public_holiday where day = " + date  + " and month = " + month + " and year= " + year;
						rsHolidayMinMax = stHolidayMinMax.executeQuery(holidayQuery);
						if(rsHolidayMinMax.next())
						{
							for(int i=0;i<teamList.size();i++)
							{
								TeamObject objTeam = teamList.get(i);
							
								String teamName = objTeam.getTeamName();
								String teamWithoutSpaces = teamName.replaceAll("\\s+","");
								String colName = teamWithoutSpaces.toLowerCase().trim() + "_limit";
								//System.out.println("col name: " + colName);
								list.add(counter,rsHolidayMinMax.getInt(colName) );
								counter++;
								
							}
							
						}
						
						
						Gson gson = new Gson();
					    String jsonList = gson.toJson(list);
					    
						response.getWriter().write(jsonList);
						
					}
					else
					{
						String query = "select * from team_day_limits where day_id = " + dayOfWeek; 
						stGetMinMax = connection.createStatement();
						rsGetMinMax = stGetMinMax.executeQuery(query);
						if(rsGetMinMax.next())
						{
							for(int i=0;i<teamList.size();i++)
							{
								TeamObject objTeam = teamList.get(i);
							
								String teamName = objTeam.getTeamName();
								//System.out.println("team name: " + teamName);
								String teamWithoutSpaces = teamName.replaceAll("\\s+","");
								String colName = teamWithoutSpaces.toLowerCase().trim() + "_limit";
								//System.out.println("col name: " + colName);
								list.add(counter,rsGetMinMax.getInt(colName) );
								counter++;
								
							}
						}
						
						Gson gson = new Gson();
						
						 String jsonList = gson.toJson(list);
					        

						
						response.getWriter().write(jsonList);
						
					}

					
					
			}
		 catch(SQLException se)
			{
		      //Handle errors for JDBC
		      se.printStackTrace();
		      System.out.println("error:" + se.getMessage());
		    }
			catch(IOException e){
				e.printStackTrace();
				System.out.println("error:" + e.getMessage());
			}
			catch(Exception e)
			{
		      //Handle errors for Class.forName
		      e.printStackTrace();
		      System.out.println("error:" + e.getMessage());
		   	}
			

			finally
			{
				if (stGetMinMax != null) try { stGetMinMax.close(); } catch (SQLException e) {e.printStackTrace();}
				if (rsGetMinMax != null) try { rsGetMinMax.close(); } catch (SQLException e) {e.printStackTrace();}
				
				if (stCheckHoliday != null) try { stCheckHoliday.close(); } catch (SQLException e) {e.printStackTrace();}
				if (rsCheckHoliday != null) try { rsCheckHoliday.close(); } catch (SQLException e) {e.printStackTrace();}

		   	 	
				if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
				  

				
			}
			
		
		
		
		
	}


}
