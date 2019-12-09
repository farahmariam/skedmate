package trilane;

public class Company 
{
	String companyName;
	String companyAddress;
	int companyPhone;
	String companyLoginId;
	String databaseName;
	int freezePeriod;
	int is_confirmed;
	
	public Company()
	{
		
	}
	
	public Company(String companyName, String companyAddress, int companyPhone, String companyLoginId, String databaseName, int freezePeriod, int is_confirmed)
	{
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyPhone = companyPhone;
		this.companyLoginId = companyLoginId;
		this.databaseName = databaseName;
		this.freezePeriod = freezePeriod;
		this.is_confirmed  = is_confirmed;
		
	}
	
	public String getCompanyName()
	{
		return this.companyName;
	}
	
	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
		
	}
	
	public String getCompanyAddress()
	{
		return this.companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress)
	{
		this.companyAddress = companyAddress;
		
	}
	
	public int getCompanyPhone()
	{
		return this.companyPhone;
	}
	
	public void setCompanyPhone(int companyPhone)
	{
		this.companyPhone = companyPhone;
		
	}
	
	public String getCompanyLogin()
	{
		return this.companyLoginId;
	}
	
	public void setCompanyLogin(String companyLoginId)
	{
		this.companyLoginId= companyLoginId;
		
	}
	public String getCompanyDatabase()
	{
		return this.databaseName;
	}
	
	public void setCompanyDatabase(String databaseName)
	{
		this.databaseName= databaseName;
		
	}
	
	public int getFreezePeriod()
	{
		return this.freezePeriod;
	}
	
	public void setFreezePeriod(int freezePeriod)
	{
		this.freezePeriod = freezePeriod;
		
	}
	public int getIsConfirmed()
	{
		return this.is_confirmed;
	}
	
	public void setIsConfirmed(int is_confirmed)
	{
		this.is_confirmed = is_confirmed;
		
	}
	
}
