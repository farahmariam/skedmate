package trilane;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class CommonDatabase 
{
	public CommonDatabase()
	{
		
	}
	
	
	public boolean saveCompanyDetails(Company objCompany,ServletContext context, Administrator objAdmin,HttpSession session)
	{
		Connection connection = null;
		Statement statement = null;
		Statement stAdminEmailExists = null;
		ResultSet rsAdminEmailExists = null;
		Statement stGetCompany = null;
		ResultSet rsGetCompany = null;
		Statement stSaveAdminEmailGlobal = null;
		
		
		
		String companyName = objCompany.getCompanyName();
		String companyAddress = objCompany.getCompanyAddress();
		int companyPhone = objCompany.getCompanyPhone();
		String companyLogin = objCompany.getCompanyLogin();
		String companyDatabase = objCompany.getCompanyDatabase();
		int freezePeriod = objCompany.getFreezePeriod();
		int isConfirmed = objCompany.getIsConfirmed();
		//System.out.println("in save company details: " + companyName + " , " + companyAddress + " , " + companyPhone + ", " + companyLogin + " , " + companyDatabase + " , " + freezePeriod + " , " + isConfirmed );
		
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/Company?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				String adminEmailId = objAdmin.getAdminEmail();
				
				String sqlQuery = "insert into company_details(company_name, company_address,company_phone,company_loginid,company_database, freeze_period, is_confirmed) values ('" + companyName + "' , '" + companyAddress + "'," + companyPhone + ",'" + companyLogin + "' , '" + companyDatabase + "'," + freezePeriod + "," + isConfirmed  + ")";
				int i = statement.executeUpdate(sqlQuery);
				
				int company_id=-1;
				 
				if (i > 0)
				{
					String checkEmailExistsInGlobalTable = "select * from global_admin where admin_email like '%" + adminEmailId  + "%'";
					stAdminEmailExists = connection.createStatement();
					rsAdminEmailExists = stAdminEmailExists.executeQuery(checkEmailExistsInGlobalTable);
					if(rsAdminEmailExists.next())
					{
						session.setAttribute("errormessage", "The Administrator Email Id already exists in relation to a company!");
						deleteCompanyDetails(companyLogin,context);
						return false;
					}
					else
					{
						//get company id
						company_id = getCompanyIdFromLoginId(objCompany.getCompanyLogin(), context);
						
						
						
						//save email in global table
						stSaveAdminEmailGlobal = connection.createStatement();
						String saveAdminEmailInGlobalTable = "insert into global_admin (company_id, admin_email,uname,pwd) values ( " + company_id + ",' " + objAdmin.getAdminEmail() + "','" +  objAdmin.getAdminId() + "','" + objAdmin.getAdminPassword() + "')";
						int j = stSaveAdminEmailGlobal.executeUpdate(saveAdminEmailInGlobalTable);
						if(j>0)
						{
							return true;
						}
						else
						{
							session.setAttribute("errormessage", "Error occured when saving the admin email id in global.Please check if database is running.");
							
							return false;
						}
					}
					
					
					
				
				} 
				else 
				{
					session.setAttribute("errormessage", "Error occured when saving the company details. Please check if database is running.");
					
					return false;
				}
				
				
				
				
		}
		catch (SQLException e)
		{
			
			System.out.println(e.toString()); 
			session.setAttribute("errormessage", "Error occured . Please check if database is running.");
			
			return false;
		}
		catch (Exception e)
		{
			
			System.out.println(e.toString()); 
			session.setAttribute("errormessage", "Error occured . Please check if database is running.");
			
			return false;
		}
		finally
		{
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stAdminEmailExists != null) try { stAdminEmailExists.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsAdminEmailExists != null) try { rsAdminEmailExists.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stGetCompany != null) try { stGetCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsGetCompany != null) try { rsGetCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stSaveAdminEmailGlobal != null) try { stSaveAdminEmailGlobal.close(); } catch (SQLException e) {e.printStackTrace();}

	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
	}
	
	public boolean createCompanyDatabase(Company objCompany,ServletContext context,HttpSession session,Administrator objAdmin)
	{
		Connection connection=null;
		Statement statement = null;
		
		
		String databaseName = objCompany.getCompanyDatabase();
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				String createDatabaseQuery = "create database if not exists " + databaseName;
				
				
				int i = statement.executeUpdate(createDatabaseQuery);
				 
				if (i>0)
				{
					return true;
				
				} 
				else 
				{
					session.setAttribute("errormessage", "Error occured when trying to create the database.. Please check if mysql server is running.");
					
					int companyId = getCompanyIdFromLoginId(objCompany.getCompanyLogin(), context);
					deleteAdminMailFromGlobal(companyId,objAdmin.getAdminEmail() , context);
					deleteCompanyDetails(objCompany.getCompanyLogin(),context);
					
					
					
					return false;
				}
				
				
				
				
		}
		catch (SQLException e)
		{
			
			System.out.println("Error occured while trying to create company database." + e.toString());  
			return false;
		}
		catch (Exception e)
		{
			
			System.out.println("Error occured while trying to create company database." + e.toString());  
			return false;
		}
		finally
		{
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}

	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
		}
		
	}
	
	
	
	public boolean createTables(Company objCompany,ArrayList<Team> teamList,ServletContext context,HttpSession session,Administrator objAdmin )
	{
		
		
		Connection connection=null;
		Statement stTechnology = null;
		Statement stUsers = null;
		Statement stStatus = null;
		Statement stEvents = null;
		Statement stPublicHoliday = null;
		Statement stDayLimits = null;
		
		
		String databaseName = objCompany.getCompanyDatabase();
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/" + databaseName  +"?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				//technology table
				stTechnology = connection.createStatement();
				String createTechnologyTable = "create table technology (tech_id int not null auto_increment, tech_name varchar(50) not null, primary key (tech_id) )";
				stTechnology.executeUpdate(createTechnologyTable);
				
				//users table
				stUsers = connection.createStatement();
				String createUserTableQuery = "create table users (user_id int not null auto_increment, fname varchar(100) ,lname varchar(100), email varchar(50), uname varchar(50) not null, pwd varchar(50) not null, tech_id int, is_admin int, primary key(user_id),foreign key (tech_id) references technology (tech_id))";
				stUsers.executeUpdate(createUserTableQuery);
					
				//status table
				stStatus = connection.createStatement();
				String createStatusTableQuery = "create table status (status_id int not null auto_increment, status_name varchar(100) not null,is_working int not null,status_colour varchar(50) not null, primary key (status_id) )";
				stStatus.executeUpdate(createStatusTableQuery);
				
				//events table
				stEvents = connection.createStatement();
				String createEventsTableQuery = "create table events (event_id int not null auto_increment, user_id int, day int not null, month int not null, year int not null, status_id int, comment varchar(200), event_datetime datetime, primary key (event_id), foreign key (status_id) references status(status_id), foreign key (user_id) references users (user_id))";
				stEvents.executeUpdate(createEventsTableQuery);
				
				
				//get the number of teams and also teamnames
				int numTeams = teamList.size();
				
				String createPublicHolidayTableQuery = "create table public_holiday ( day int not null, month int not null, year int not null, holiday int not null,";
				String createTeamDayLimitsTableQuery = "create table team_day_limits (day_id int not null auto_increment, day_name varchar(50) not null,";
				for( int i=0; i<numTeams ; i++)
				{
					Team objTeam = teamList.get(i);
					String teamName = objTeam.getTeamName();
					String teamWithoutSpaces = teamName.replaceAll("\\s+","");
					
					
					String colName = teamWithoutSpaces.toLowerCase().trim() + "_limit int ";
					//System.out.println("column name in commondatabase:" + colName);
					
					createPublicHolidayTableQuery = createPublicHolidayTableQuery.concat(colName);
					createTeamDayLimitsTableQuery = createTeamDayLimitsTableQuery.concat(colName);
					
					if(i!=(numTeams-1))
					{
						createPublicHolidayTableQuery = createPublicHolidayTableQuery.concat(" , ");
						createTeamDayLimitsTableQuery = createTeamDayLimitsTableQuery.concat(" , ");
					}
					
					
					
					
					
					
				}
				createPublicHolidayTableQuery = createPublicHolidayTableQuery.concat(")");
				createTeamDayLimitsTableQuery = createTeamDayLimitsTableQuery.concat(" , primary key (day_id) )");
				
				
				//public holiday table
				stPublicHoliday = connection.createStatement();
				stPublicHoliday.executeUpdate(createPublicHolidayTableQuery);
				
				//team day limit table
				stDayLimits = connection.createStatement();
				stDayLimits.executeUpdate(createTeamDayLimitsTableQuery); 
				
				
				
				return true;
					
							
		}
		catch (SQLException e)
		{
			session.setAttribute("errormessage", "Error occured when trying to create the database tables.. Please check if mysql server is running.");
			deleteDatabase(objCompany.getCompanyDatabase(), context);
			int companyId = getCompanyIdFromLoginId(objCompany.getCompanyLogin(), context);
			deleteAdminMailFromGlobal(companyId,objAdmin.getAdminEmail() , context);
			deleteCompanyDetails(objCompany.getCompanyLogin(),context);
			System.out.println("Error occured while trying to create company database tables" + e.toString());  
			return false;
		}
		catch (Exception e)
		{
			session.setAttribute("errormessage", "Error occured when trying to create the database tables.. Please check if mysql server is running.");
			deleteDatabase(objCompany.getCompanyDatabase(), context);
			int companyId = getCompanyIdFromLoginId(objCompany.getCompanyLogin(), context);
			deleteAdminMailFromGlobal(companyId,objAdmin.getAdminEmail() , context);
			deleteCompanyDetails(objCompany.getCompanyLogin(),context);
			
			System.out.println("Error occured while trying to create company database tables" + e.toString());  
			return false;
		}
		finally
		{
			if (stTechnology != null) try { stTechnology.close(); } catch (SQLException e) {e.printStackTrace();}

			if (stUsers != null) try { stUsers.close(); } catch (SQLException e) {e.printStackTrace();}
			
			if (stStatus != null) try { stStatus.close(); } catch (SQLException e) {e.printStackTrace();}
			
			if (stEvents != null) try { stEvents.close(); } catch (SQLException e) {e.printStackTrace();}

			if (stPublicHoliday != null) try { stPublicHoliday.close(); } catch (SQLException e) {e.printStackTrace();}

			if (stDayLimits != null) try { stDayLimits.close(); } catch (SQLException e) {e.printStackTrace();}


	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
		}
		
			
	}
	
	
	
	
	public boolean enterDataInTables(Company objCompany,ArrayList<Team> teamList,ArrayList<Status> statusList,Administrator objAdmin,ServletContext context,HttpSession session)
	{
		Connection connection=null;
		Statement stAdminDetails= null;
		Statement stTechnology= null;
		Statement stStatus = null;
		Statement stDay = null;
		Statement stLimitsTeam = null;
		
		
		
		
		try
		{
				String databaseName = objCompany.getCompanyDatabase();
				String adminId = objAdmin.getAdminId();
				String adminPassword = objAdmin.getAdminPassword();
				String adminEmail = objAdmin.getAdminEmail();
				
			
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/" + databaseName  +"?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				int is_admin=1;
				//enter admin details
				stAdminDetails = connection.createStatement();
				String adminQuery = "insert into users(email,uname,pwd,is_admin) values ('" + adminEmail + "' , '" + adminId + "' , '" +  adminPassword + "', " + is_admin + ")";      
				stAdminDetails.executeUpdate(adminQuery);
				 
				//enter teams into technology table
				stTechnology = connection.createStatement();
				for(int i=0;i<teamList.size();i++)
				{
					Team objTeam = teamList.get(i);
					String teamName = objTeam.getTeamName();
					
					String techQuery = "insert into technology (tech_name) values ('" + teamName + "')";
					stTechnology.executeUpdate(techQuery);
				}
				
				//enter status names into status table
				stStatus = connection.createStatement();
				for(int i=0;i<statusList.size();i++)
				{
					
					Status objStatus = statusList.get(i);
					String statusName = objStatus.getStatusName();
					int is_working = objStatus.getStatusWorking();
					
					String status_colour = objStatus.getStatusColor();
					String statusQuery = "insert into status (status_name, is_working,status_colour) values ('" + statusName + "'," + is_working + ",'" + status_colour + "' )";
					stStatus.executeUpdate(statusQuery);
				}
				
				//enter team limits
				stDay = connection.createStatement();
				String sunQuery = "insert into team_day_limits (day_name) values ( 'sunday' )";
				stDay.executeUpdate(sunQuery);
				String monQuery = "insert into team_day_limits (day_name) values ( 'monday' )";
				stDay.executeUpdate(monQuery);
				String tueQuery = "insert into team_day_limits (day_name) values ( 'tuesday' )";
				stDay.executeUpdate(tueQuery);
				String wedQuery = "insert into team_day_limits (day_name) values ( 'wednesday' )";
				stDay.executeUpdate(wedQuery);
				String thurQuery = "insert into team_day_limits (day_name) values ( 'thursday' )";
				stDay.executeUpdate(thurQuery);
				String friQuery = "insert into team_day_limits (day_name) values ( 'friday' )";
				stDay.executeUpdate(friQuery);
				String satQuery = "insert into team_day_limits (day_name) values ( 'saturday' )";
				stDay.executeUpdate(satQuery);
				
				String pubHolQuery = "insert into team_day_limits (day_name) values ( 'public holiday' )";
				stDay.executeUpdate(pubHolQuery);
				
				
				
				stLimitsTeam = connection.createStatement();
				for(int j=0; j<teamList.size(); j++)
				{
					Team objTeam = teamList.get(j);
					String teamName = objTeam.getTeamName();
					
					String teamWithoutSpaces = teamName.replaceAll("\\s+","");
					String colName = teamWithoutSpaces.trim() + "_limit";
					
					int sunLimit = objTeam.getSunLimit();
					int monLimit = objTeam.getMonLimit();
					int tueLimit = objTeam.getTueLimit();
					int wedLimit = objTeam.getWedLimit();
					int thuLimit = objTeam.getThurLimit();
					int friLimit = objTeam.getFriLimit();
					int satLimit = objTeam.getSatLimit();
					int holLimit = objTeam.getPubHolLimit();
					
					
					String sunLimitQuery = "update team_day_limits set " + colName + " = " + sunLimit  + " where day_id =1 ";
					stLimitsTeam.executeUpdate(sunLimitQuery);
					String monLimitQuery = "update team_day_limits set " + colName + " = " + monLimit  + " where day_id =2 ";
					stLimitsTeam.executeUpdate(monLimitQuery);
					String tueLimitQuery = "update team_day_limits set " + colName + " = " + tueLimit  + " where day_id =3 ";
					stLimitsTeam.executeUpdate(tueLimitQuery);
					String wedLimitQuery = "update team_day_limits set " + colName + " = " + wedLimit  + " where day_id =4 ";
					stLimitsTeam.executeUpdate(wedLimitQuery);
					String thurLimitQuery = "update team_day_limits set " + colName + " = " + thuLimit  + " where day_id =5 ";
					stLimitsTeam.executeUpdate(thurLimitQuery);
					String friLimitQuery = "update team_day_limits set " + colName + " = " + friLimit  + " where day_id =6 ";
					stLimitsTeam.executeUpdate(friLimitQuery);
					String satLimitQuery = "update team_day_limits set " + colName + " = " + satLimit  + " where day_id =7 ";
					stLimitsTeam.executeUpdate(satLimitQuery);
					
					String pubHolLimitQuery = "update team_day_limits set " + colName + " = " + holLimit  + " where day_id =8 ";
					stLimitsTeam.executeUpdate(pubHolLimitQuery);
					
					
					
				}
				
				
				
				return true;
		}
		catch (SQLException e)
		{
			session.setAttribute("errormessage", "Error occured when trying to enter data in the database tables.. Please check if mysql server is running.");
			deleteDatabase(objCompany.getCompanyDatabase(), context);
			int companyId = getCompanyIdFromLoginId(objCompany.getCompanyLogin(), context);
			deleteAdminMailFromGlobal(companyId,objAdmin.getAdminEmail() , context);
			deleteCompanyDetails(objCompany.getCompanyLogin(),context);
			System.out.println("Error occured while trying to create company database." + e.toString());  
			return false;
		}
		catch (Exception e)
		{
			session.setAttribute("errormessage", "Error occured when trying to enter data im the database tables.. Please check if mysql server is running.");
			deleteDatabase(objCompany.getCompanyDatabase(), context);
			int companyId = getCompanyIdFromLoginId(objCompany.getCompanyLogin(), context);
			deleteAdminMailFromGlobal(companyId,objAdmin.getAdminEmail() , context);
			deleteCompanyDetails(objCompany.getCompanyLogin(),context);
			
			System.out.println("Error occured while trying to create company database." + e.toString());  
			return false;
		}
		finally
		{
			
			if (stAdminDetails != null) try { stAdminDetails.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stTechnology != null) try { stTechnology.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stStatus != null) try { stStatus.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stDay != null) try { stDay.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stLimitsTeam != null) try { stLimitsTeam.close(); } catch (SQLException e) {e.printStackTrace();}

	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	
			
		}
		
	}
	
	
	public void deleteCompanyDetails(String companyLoginId,ServletContext context)
	{
		Connection connection = null;
		Statement statement = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/Company?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				String sqlQuery = "delete from company_details where company_loginid='" + companyLoginId + "'";
				int i = statement.executeUpdate(sqlQuery);
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		
		
	}
	
	public int getCompanyIdFromLoginId(String companyLoginId, ServletContext context)
	{
		int company_id = -1;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/Company?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				
				String getCompany = "select company_id from company_details where company_loginid = '" + companyLoginId + "'";
				resultset = statement.executeQuery(getCompany);
				if(resultset.next())
				{
					company_id = resultset.getInt(1);
				}
				
				
				
				
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			if (resultset != null) try { resultset.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		return company_id;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void deleteAdminMailFromGlobal(int companyId,String emailId,ServletContext context)
	{
		Connection connection = null;
		Statement statement = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/Company?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				String sqlQuery = "delete from global_admin where company_id=" + companyId + " and admin_email like '%" + emailId +"%'";
				int i = statement.executeUpdate(sqlQuery);
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		
		
	}
	
	
	
	
	public void deleteDatabase(String databaseName,ServletContext context)
	{
		Connection connection = null;
		Statement statement = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = "jdbc:mysql://localhost:3306/" + databaseName  +"?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				String sqlQuery = "drop database '" + databaseName + "'";
				int i = statement.executeUpdate(sqlQuery);
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	public boolean checkPublicHoliday(int day, int month, int year,ServletContext context,HttpSession session)
	{
		boolean isHoliday = false;
		Connection con=null;
		ResultSet res=null;
		PreparedStatement ps = null;
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			//String databaseUrl = context.getInitParameter("data-url");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			
			con =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
			
			
			
			ps = con.prepareStatement("SELECT  * FROM public_holiday WHERE " + "day = ? and month=? and year=?");
			ps.setInt(1,day);
			ps.setInt(2,month);
			ps.setInt(3,year);
			res = ps.executeQuery();
			if(res.first())
			{
				isHoliday = true;
			}
			

		}
		catch(SQLException se)
		{
	   		//Handle errors for JDBC
	  		 se.printStackTrace();
		 }
		catch(Exception e)
		{
	  		 //Handle errors for Class.forName
	   			e.printStackTrace();
		}
		finally
		{
			if (res != null) try { res.close(); } catch (SQLException e) {e.printStackTrace();}
			if (ps != null) try { ps.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			 
		}
			
		return isHoliday;
		
	}
	
	public String getMonth(int month)
	 {
		 String monthString="";
		 switch (month) 
		 {
		     case 0:  monthString = "January";
		              break;
		     case 1:  monthString = "February";
		              break;
		     case 2:  monthString = "March";
		              break;
		     case 3:  monthString = "April";
		              break;
		     case 4:  monthString = "May";
		              break;
		     case 5:  monthString = "June";
		              break;
		     case 6:  monthString = "July";
		              break;
		     case 7:  monthString = "August";
		              break;
		     case 8:  monthString = "September";
		              break;
		     case 9: monthString = "October";
		              break;
		     case 10: monthString = "November";
		              break;
		     case 11: monthString = "December";
		              break;
		     
	 	}

		return monthString; 
	 }
	
	
	public String getDay(int day)
	 {
		 String dayString="";
		 switch (day) 
		 {
		     case 1:  dayString = "Sunday";
		              break;
		     case 2:  dayString = "Monday";
		              break;
		     case 3:  dayString = "Tuesday";
		              break;
		     case 4:  dayString = "Wednesday";
		              break;
		     case 5:  dayString = "Thursday";
		              break;
		     case 6:  dayString = "Friday";
		              break;
		     case 7:  dayString = "Saturday";
		              break;
		    
		     
	 	}

		return dayString; 
	 }
	
	public boolean checkWeekend(int dayId)
	{
		boolean weekend=false;
		
		switch(dayId) 
		{
			case 1:  weekend = true;
					 break;
			case 2:  weekend = false;
					 break;
			case 3:  weekend = false;
					 break;
			case 4:  weekend = false;
	                 break;
			case 5:  weekend = false;
					 break;
			case 6:  weekend = false;
					 break;
			case 7:  weekend = true;
	    			 break;
		}	 
		return weekend;
		
	}
	
	
	
	
	
	
	
	public int getTotalNumberOfTeams(String companyLoginId, ServletContext context,HttpSession session)
	{
		int total_teams = -1;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = (String)session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				
				String getTeamCount = "select count(*) from technology";
				resultset = statement.executeQuery(getTeamCount);
				if(resultset.next())
				{
					total_teams = resultset.getInt(1);
				}
				
				
				
				
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			if (resultset != null) try { resultset.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		return total_teams;
		
		
		
		
	}
	
	
	
	public int getTotalNumberOfStatus(String companyLoginId, ServletContext context,HttpSession session)
	{
		int total_status = -1;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = (String)session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				
				String getStatusCount = "select count(*) from status";
				resultset = statement.executeQuery(getStatusCount);
				if(resultset.next())
				{
					total_status = resultset.getInt(1);
				}
				
				
				
				
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			if (resultset != null) try { resultset.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		return total_status;
		
		
		
		
	}
	
	
	
	public ArrayList<StatusObject> getAllStatus( ServletContext context,HttpSession session)
	{
		ArrayList<StatusObject> statusList = new ArrayList();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = (String)session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				
				String getStatus = "select * from status";
				resultset = statement.executeQuery(getStatus);
				int i=0;
				while(resultset.next())
				{
					int status_id = resultset.getInt("status_id");
					String status_name = resultset.getString("status_name"); 
					String colour = resultset.getString("status_colour");
					int is_working = resultset.getInt("is_working");
					StatusObject obj = new StatusObject(status_id,status_name,is_working,colour);
					statusList.add(i,obj);
					i++;
				}
				
				
				
				
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			if (resultset != null) try { resultset.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		return statusList;
		
		
		
		
	}
	
	
	public ArrayList<TeamObject> getAllTeams( ServletContext context,HttpSession session)
	{
		ArrayList<TeamObject> teamList = new ArrayList();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
	
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				//String databaseUrl = context.getInitParameter("data-url");
				String databaseUrl = (String)session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				statement = connection.createStatement();
				
				
				String getStatus = "select * from technology";
				resultset = statement.executeQuery(getStatus);
				int i=0;
				while(resultset.next())
				{
					int tech_id = resultset.getInt("tech_id");
					String tech_name = resultset.getString("tech_name"); 
					
					TeamObject obj = new TeamObject(tech_id, tech_name);
					teamList.add(i,obj);
					i++;
				}
				
				
				
				
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			if (resultset != null) try { resultset.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
	   	 	
	   	 	
	   	 
			
			
			
		}
		
		return teamList;
		
		
		
		
	}
	
	
	
	
	public String getTeamColumnForTable(String teamName)
	{
		String teamColumnName="";
		String teamWithoutSpaces = teamName.replaceAll("\\s+","");
		teamColumnName = teamWithoutSpaces.toLowerCase().trim() + "_limit";
		
		
		return teamColumnName;
	}
	
	public int insertIntoGlobalAdmin(ServletContext context,HttpSession session, Administrator adminObject)
	{
		Connection conCompany=null;
		Statement stCompany=null;
		Statement stGlobalAdmin=null;
		ResultSet rsCompany=null;
		int company_id=-1;
		int updated=0;
		try
		{
			String companyLoginId = (String)session.getAttribute("companyLoginId");
			Class.forName("com.mysql.jdbc.Driver");
			
			String companyDatabaseUrl= (String)session.getAttribute("data-url-company");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
			
			conCompany = DriverManager.getConnection(companyDatabaseUrl,databaseUser, databasePwd);
			
			//find company id
			company_id = getCompanyIdFromLoginId(companyLoginId, context);
			
			
			
			
			//insert into global_admin
			String adminQuery = "insert into global_admin (company_id,admin_email,uname,pwd) values (" + company_id + ",'" + adminObject.getAdminEmail() + "','" + adminObject.getAdminId() + "','" + adminObject.getAdminPassword() + "')";
			stGlobalAdmin = conCompany.createStatement();
			updated=stGlobalAdmin.executeUpdate(adminQuery);
			
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
			
			if (stCompany != null) try { stCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsCompany != null) try { rsCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (conCompany != null) try { conCompany.close(); } catch (SQLException e) {e.printStackTrace();}
				
		}
		return updated;
		
	}
	
	
	
	public void UpdateEmailGlobalAdmin(ServletContext context,HttpSession session, Administrator adminObject)
	{
		Connection conCompany=null;
		Statement stCompany=null;
		Statement stGlobalAdmin=null;
		ResultSet rsCompany=null;
		int company_id=-1;
		try
		{
			String companyLoginId = (String)session.getAttribute("companyLoginId");
			Class.forName("com.mysql.jdbc.Driver");
			
			String companyDatabaseUrl= (String)session.getAttribute("data-url-company");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
			
			conCompany = DriverManager.getConnection(companyDatabaseUrl,databaseUser, databasePwd);
			//find company id
			
			company_id = getCompanyIdFromLoginId(companyLoginId, context);
			
			
			
			//update global_admin
			String adminQuery = "update global_admin set admin_email='" + adminObject.getAdminEmail() + "' where company_id=" + company_id + " and uname='" + adminObject.getAdminId() + "'";
			stGlobalAdmin = conCompany.createStatement();
			int j=stGlobalAdmin.executeUpdate(adminQuery);
			
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
			
			if (stCompany != null) try { stCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsCompany != null) try { rsCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (conCompany != null) try { conCompany.close(); } catch (SQLException e) {e.printStackTrace();}
				
		}
		
		
	}
	
	public boolean checkEmailExistsInGlobalAdmin(ServletContext context,HttpSession session, String emailId)
	{
		Connection conCompany=null;
		Statement stAdminEmailExists = null;
		ResultSet rsAdminEmailExists=null;
		int company_id=-1;
		boolean exists=false;
		try
		{
			if(emailId.trim()!="")
			{
				
			
				String companyLoginId = (String)session.getAttribute("companyLoginId");
				Class.forName("com.mysql.jdbc.Driver");
				
				String companyDatabaseUrl= (String)session.getAttribute("data-url-company");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				
				conCompany = DriverManager.getConnection(companyDatabaseUrl,databaseUser, databasePwd);
				//find company id
			
				company_id =getCompanyIdFromLoginId(companyLoginId, context);
				
				String checkEmailExistsInGlobalTable = "select * from global_admin where admin_email like '%" + emailId  + "%' and company_id=" + company_id;
				stAdminEmailExists = conCompany.createStatement();
				rsAdminEmailExists = stAdminEmailExists.executeQuery(checkEmailExistsInGlobalTable);
				if(rsAdminEmailExists.next())
				{
					
					exists =  true;
				}
				else
				{
					exists = false;
				}
			}
			else
			{
				exists = false;
				
			}
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
			
			if (stAdminEmailExists != null) try { stAdminEmailExists.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsAdminEmailExists != null) try { rsAdminEmailExists.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (conCompany != null) try { conCompany.close(); } catch (SQLException e) {e.printStackTrace();}
				
		}	
		return exists;
	}
	
	
	
	
	
	public boolean checkIfAdmin(int userId, ServletContext context, HttpSession session)
	{
		Connection con=null;
		ResultSet rs=null;
		Statement st = null;
		int isAdmin = 0;
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			//String databaseUrl = context.getInitParameter("data-url");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			con = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			st = con.createStatement();
			
			rs = st.executeQuery("select * from users where user_id=" + userId);
			if (rs.next()) 
			{
				isAdmin = rs.getInt("is_admin");
				
			}
			
			
			

		}
		catch(SQLException se)
		{
	   		//Handle errors for JDBC
	  		 se.printStackTrace();
		 }
		catch(Exception e)
		{
	  		 //Handle errors for Class.forName
	   			e.printStackTrace();
		}
		finally
		{
			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			 
		}
		
		if(isAdmin==1)
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	
	public boolean checkIfAdmin(String userName, ServletContext context, HttpSession session)
	{
		Connection con=null;
		ResultSet rs=null;
		Statement st = null;
		int isAdmin = 0;
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			//String databaseUrl = context.getInitParameter("data-url");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			con = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			st = con.createStatement();
			
			rs = st.executeQuery("select * from users where uname= '" + userName + "'");
			if (rs.next()) 
			{
				isAdmin = rs.getInt("is_admin");
				
			}
			
			
			

		}
		catch(SQLException se)
		{
	   		//Handle errors for JDBC
	  		 se.printStackTrace();
		 }
		catch(Exception e)
		{
	  		 //Handle errors for Class.forName
	   			e.printStackTrace();
		}
		finally
		{
			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			 
		}
		
		if(isAdmin==1)
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	
	
	
	public String getEmailIdOfResource(String userName,HttpSession session,ServletContext context)
	{
		
		Connection con=null;
		ResultSet rs=null;
		Statement st = null;
		String email="";
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			//String databaseUrl = context.getInitParameter("data-url");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			con = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			st = con.createStatement();
			
			rs = st.executeQuery("select email from users where uname='" + userName + "'");
			if (rs.next()) 
			{
				email = rs.getString(1);
				
			}
			
			
			

		}
		catch(SQLException se)
		{
	   		//Handle errors for JDBC
	  		 se.printStackTrace();
		 }
		catch(Exception e)
		{
	  		 //Handle errors for Class.forName
	   			e.printStackTrace();
		}
		finally
		{
			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			 
		}
		return email;
		
	}
	
	
	
	
	
	public String getPasswordOfResource(String userName,HttpSession session,ServletContext context)
	{
		
		Connection con=null;
		ResultSet rs=null;
		Statement st = null;
		String password="";
		
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			//String databaseUrl = context.getInitParameter("data-url");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			con = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			st = con.createStatement();
			
			rs = st.executeQuery("select pwd from users where uname='" + userName + "'");
			if (rs.next()) 
			{
				password = rs.getString(1);
				
			}
	
		}
		catch(SQLException se)
		{
	   		//Handle errors for JDBC
	  		 se.printStackTrace();
		 }
		catch(Exception e)
		{
	  		 //Handle errors for Class.forName
	   			e.printStackTrace();
		}
		finally
		{
			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			 
		}
		return password;
		
	}
	
	
	
	public void UpdatePasswordGlobalAdmin(ServletContext context,HttpSession session, Administrator adminObject)
	{
		Connection conCompany=null;
		Statement stCompany=null;
		Statement stGlobalAdmin=null;
		ResultSet rsCompany=null;
		int company_id=-1;
		try
		{
			String companyLoginId = (String)session.getAttribute("companyLoginId");
			Class.forName("com.mysql.jdbc.Driver");
			
			String companyDatabaseUrl= (String)session.getAttribute("data-url-company");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
			
			conCompany = DriverManager.getConnection(companyDatabaseUrl,databaseUser, databasePwd);
			//find company id
			
			company_id = getCompanyIdFromLoginId(companyLoginId, context);
			
			
			
			//update global_admin
			String adminQuery = "update global_admin set pwd='" + adminObject.getAdminPassword() + "' where company_id=" + company_id + " and uname='" + adminObject.getAdminId() + "'";
			stGlobalAdmin = conCompany.createStatement();
			int j=stGlobalAdmin.executeUpdate(adminQuery);
			
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
			
			if (stCompany != null) try { stCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsCompany != null) try { rsCompany.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	if (conCompany != null) try { conCompany.close(); } catch (SQLException e) {e.printStackTrace();}
				
		}
		
		
	}
	
	public ArrayList<user> getAllResources(ServletContext context,HttpSession session)
	{
		ArrayList<user> userList = new ArrayList();
		 Connection connection = null;		
		 Statement stGetResource= null;		 
		 ResultSet rsGetResource  = null;
		 
		 try
			{
				
					Class.forName("com.mysql.jdbc.Driver");
					String databaseUrl = (String)session.getAttribute("data-url-schedule");
					String databaseUser = context.getInitParameter("data-user");
					String databasePwd = context.getInitParameter("data-pwd");
					connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);

					String query = "select * from users order by uname"; 
					stGetResource = connection.createStatement();
					rsGetResource = stGetResource.executeQuery(query);
					
					
					int user_id = -1;
					
					String password="";
					
					String fName="";
					String lName="";
					String email="";
					
					int tech_id=-1;
					int is_admin=-1;
					
					user userObj=null;
					String userName="";
					
					while(rsGetResource.next())
					{
						userName = rsGetResource.getString("uname");						
						user_id = rsGetResource.getInt("user_id");
						
						fName = rsGetResource.getString("fname");
						if(fName==null)
						{
							fName="";
						}
						lName = rsGetResource.getString("lname");
						if(lName==null)
						{
							lName="";
						}
						password=rsGetResource.getString("pwd");
						
						email=rsGetResource.getString("email");
						if(email==null)
						{
							email="";
						}
						tech_id=rsGetResource.getInt("tech_id");
						if((Integer)tech_id==null)
						{
							tech_id=1;
						}
						is_admin = rsGetResource.getInt("is_admin");
						if((Integer)is_admin==null)
						{
							is_admin=0;
						}
						
						//System.out.println("login name:" + userName + ",user id :" + user_id + ",password: " + password  + ",email: " + email + ", techid:" + tech_id + ", is_admin: " + is_admin);
						userObj = new user(user_id,userName,password,fName,lName,email, tech_id,is_admin);
						userList.add(userObj);
						
					}
					
					
			}
		 catch(SQLException se)
			{
		      //Handle errors for JDBC
		      se.printStackTrace();
		      System.out.println("error:" + se.getMessage());
		    }
		
			catch(Exception e)
			{
		      //Handle errors for Class.forName
		      e.printStackTrace();
		      System.out.println("error:" + e.getMessage());
		   	}
			

			finally
			{
				if (rsGetResource != null) try { rsGetResource.close(); } catch (SQLException e) {e.printStackTrace();}
				if (stGetResource != null) try { stGetResource.close(); } catch (SQLException e) {e.printStackTrace();}

		   	 	
				if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
				  

				
			}
		 return userList;
	}
	
	
	public String getTechnologyName(int techId, ServletContext context,HttpSession session)
	{
		 Connection connection = null;		
		 Statement stTechnology= null;		 
		 ResultSet rsTechnology  = null;
		 String tech_name="";
		 
		 try
			{
				
					Class.forName("com.mysql.jdbc.Driver");
					String databaseUrl = (String)session.getAttribute("data-url-schedule");
					String databaseUser = context.getInitParameter("data-user");
					String databasePwd = context.getInitParameter("data-pwd");
					connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);

					String query = "select tech_name from technology where tech_id=" +techId ; 
					stTechnology = connection.createStatement();
					rsTechnology = stTechnology.executeQuery(query);
					
					
					
					
					if(rsTechnology.next())
					{
						tech_name = rsTechnology.getString("tech_name");						
						
						
					}
					
					
			}
		 catch(SQLException se)
			{
		      //Handle errors for JDBC
		      se.printStackTrace();
		      System.out.println("error:" + se.getMessage());
		    }
		
			catch(Exception e)
			{
		      //Handle errors for Class.forName
		      e.printStackTrace();
		      System.out.println("error:" + e.getMessage());
		   	}
			

			finally
			{
				if (rsTechnology != null) try { rsTechnology.close(); } catch (SQLException e) {e.printStackTrace();}
				if (stTechnology != null) try { stTechnology.close(); } catch (SQLException e) {e.printStackTrace();}

		   	 	
				if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
				  

				
			}
		 return tech_name;
		
	}
	
	public int getFreezePeriod(ServletContext context,HttpSession session)
	{
		Connection connection=null;
		Statement stFreezePeriod = null;
		ResultSet rsFreezePeriod = null;
		int freezePeriod=-1;
		 try
			{
			 
			 
			 	Class.forName("com.mysql.jdbc.Driver");
				String databaseUrl = (String)session.getAttribute("data-url-company");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);

				
			 	String companyLoginId = (String)session.getAttribute("companyLoginId");
				
				//find company id
				int company_id = getCompanyIdFromLoginId(companyLoginId, context);
				
				
				String query = "select freeze_period from company_details where company_id=" +company_id ; 
				stFreezePeriod = connection.createStatement();
				
				rsFreezePeriod = stFreezePeriod.executeQuery(query);
				if(rsFreezePeriod.next())
				{
					freezePeriod = rsFreezePeriod.getInt(1);
				}
					
					
			}
		 catch(SQLException se)
			{
		      //Handle errors for JDBC
		      se.printStackTrace();
		      System.out.println("error:" + se.getMessage());
		    }
		
			catch(Exception e)
			{
		      //Handle errors for Class.forName
		      e.printStackTrace();
		      System.out.println("error:" + e.getMessage());
		   	}
			

			finally
			{
				if (rsFreezePeriod != null) try { rsFreezePeriod.close(); } catch (SQLException e) {e.printStackTrace();}
				if (stFreezePeriod != null) try { stFreezePeriod.close(); } catch (SQLException e) {e.printStackTrace();}

		   	 	
				if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
				  

				
			}
		 return freezePeriod;
		
	}
	
	
	public boolean SaveFreezePeriod(int freezePeriod,ServletContext context,HttpSession session)
	{
		Connection connection=null;
		Statement stFreezePeriod = null;
		
		boolean returnValue=false;
		
		 try
			{
			 
			 
			 	Class.forName("com.mysql.jdbc.Driver");
				String databaseUrl = (String)session.getAttribute("data-url-company");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);

				
			 	String companyLoginId = (String)session.getAttribute("companyLoginId");
				
				//find company id
				int company_id = getCompanyIdFromLoginId(companyLoginId, context);
				
				
				String query = "update company_details set freeze_period=" + freezePeriod + " where company_id=" + company_id ; 
				stFreezePeriod = connection.createStatement();
				
				int i = stFreezePeriod.executeUpdate(query);
				if(i>0)
				{
					returnValue =  true;
				}
				else
				{
					returnValue =  false;
				}
					
					
			}
		 catch(SQLException se)
			{
		      //Handle errors for JDBC
		      se.printStackTrace();
		      System.out.println("error:" + se.getMessage());
		    }
		
			catch(Exception e)
			{
		      //Handle errors for Class.forName
		      e.printStackTrace();
		      System.out.println("error:" + e.getMessage());
		   	}
			

			finally
			{
				if (stFreezePeriod != null) try { stFreezePeriod.close(); } catch (SQLException e) {e.printStackTrace();}

		   	 	
				if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
				  

				
			}
		 return returnValue;
		
	}
	
	
}
