package trilane;

public class Resource 
{
	String loginname;
    int id;
    String fullname="";

	public Resource()
	{
		
	}
	
	public Resource(String loginName, int id, String fullName )
	{
		setResourceLoginName(loginName);
		setResourceId(id);
		setResourceFullName(fullName);
		
	}
	public String getResourceLoginName()
	{
		return this.loginname;
	}
	
	public void setResourceLoginName(String loginname)
	{
		this.loginname = loginname;
	}
	
	
	public String getResourceFullName()
	{
		return this.fullname;
	}
	
	public void setResourceFullName(String fullname)
	{
		this.fullname = fullname;
	}
	

	public int getResourceId()
	{
		return this.id;
	}
	
	public void setResourceId(int id)
	{
		this.id = 	id;
	}

}

