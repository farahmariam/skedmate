package trilane;

public class Administrator 
{
	String adminId;
	String adminPassword;
	String adminEmail;
	
	public Administrator()
	{
		
	}
	public Administrator(String adminId, String adminPassword, String adminEmail)
	{
		this.adminId = adminId;
		this.adminPassword = adminPassword;
		this.adminEmail = adminEmail;
		
	}
	
	public String getAdminId()
	{
		return this.adminId;
	}
	
	public void setAdminId(String adminId)
	{
		this.adminId = adminId;
		
	}
	
	public String getAdminPassword()
	{
		return this.adminPassword;
	}
	
	public void setAdminPassword(String adminPassword)
	{
		this.adminPassword = adminPassword;
		
	}
	
	public String getAdminEmail()
	{
		return this.adminEmail;
	}
	
	public void setAdminEmail(String adminEmail)
	{
		this.adminEmail = adminEmail;
		
	}

}
