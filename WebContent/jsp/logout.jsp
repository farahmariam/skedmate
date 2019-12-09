<%
session.setAttribute("userid", null);
session.setAttribute("username", null);
session.setAttribute("loginname", null);
session.setAttribute("companyLoginId", null);

session.setAttribute("data-url-company", null);
session.setAttribute("data-url-schedule", null);
session.setAttribute("errormessage", null);

if(session.getAttribute("month")!="" && session.getAttribute("month")!=null)
{
	session.setAttribute("month", null);
}
if(session.getAttribute("year")!="" && session.getAttribute("year")!=null)
{
	session.setAttribute("year", null);
}




session.invalidate();

ServletContext context = request.getServletContext();
String loginUrl = context.getInitParameter("login-url");
response.sendRedirect(loginUrl);




%>