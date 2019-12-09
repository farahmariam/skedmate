package trilane;

public class user {
	String loginname;
	String loginpassword;
    int id;
    String fname="";
    String lname="";
    String email="";
    int techid;
    int isadmin;

	public user()
	{
		
	}
	
	public user( int user_id,String loginName,String password,String fName,String lName, String email,int techid, int isadmin  )
	{
		setUserId(user_id);
		setUserName(loginName);
		setUserPassword(password);
		setfName(fName);
		setlName(lName);
		setEmail(email);
		setUserTechId(techid);
		setUserIsAdmin(isadmin);
		
	}
	public String getUserName()
	{
		return this.loginname;
	}
	
	public void setUserName(String loginname)
	{
		this.loginname = loginname;
	}
	
	public String getUserPassword()
	{
		return this.loginpassword;
	}
	
	public void setUserPassword(String pass)
	{
		this.loginpassword = pass;
	}
	
	
	public String getfName()
	{
		return this.fname;
	}
	
	public void setfName(String fName)
	{
		this.fname = fName;
	}
	
	public String getlName()
	{
		return this.lname;
	}
	
	public void setlName(String lName)
	{
		this.lname = lName;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	public int getUserId()
	{
		return this.id;
	}
	
	public void setUserId(int id)
	{
		this.id = 	id;
	}
	
	public int getUserTechId()
	{
		return this.techid;
	}
	
	public void setUserTechId(int techid)
	{
		this.techid= 	techid;
	}
	
	public int getUserIsAdmin()
	{
		return this.isadmin;
	}
	
	public void setUserIsAdmin(int isadmin)
	{
		this.isadmin= 	isadmin;
		
	}


}
