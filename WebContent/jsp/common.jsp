 <%@ page import ="java.sql.*" %>
<%!  
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

public int nullIntconv(String inv)
{   
	int conv=0;
		
	try
	{
		
		
		conv=Integer.parseInt(inv);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return conv;
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


public String getTechName(int techId,ServletContext context)
{
	String techName="";

	Connection con=null;
	ResultSet rs=null;
	Statement st = null;
	try
	{
		
		Class.forName("com.mysql.jdbc.Driver");
		String databaseUrl = context.getInitParameter("data-url");
		String databaseUser = context.getInitParameter("data-user");
		String databasePwd = context.getInitParameter("data-pwd");
	
		con = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
		st = con.createStatement();
		
		rs = st.executeQuery("select tech_name from technology where tech_id=" + techId);
		if (rs.next()) 
		{
			techName = rs.getString(1);
			
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
		
	return techName;

}

public boolean checkPublicHoliday(int day, int month, int year,ServletContext context, HttpSession session)
{
	boolean isHoliday = false;
	Connection con=null;
	ResultSet res=null;
	PreparedStatement ps = null;
	try
	{
		
		Class.forName("com.mysql.jdbc.Driver");
		//String databaseUrl = context.getInitParameter("data-url");
		String databaseUrl =(String) session.getAttribute("data-url-schedule");
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

 
 
 
 %>