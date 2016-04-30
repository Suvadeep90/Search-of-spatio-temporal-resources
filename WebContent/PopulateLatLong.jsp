<%@ page import ="java.sql.*" %>
<%@ page import ="java.lang.*"%>
<%@ page import = "com.edu.uic.cs581.team3.constants.ConstVariables"%>
<%@ page import = "javax.sql.DataSource"%>
<%@ page import = "javax.naming.InitialContext"%>
<%
		String place=request.getParameter("place");
		System.out.print(place);
		String Query = "select latitude,longitude from sf_park_node where node_name='"+place+"'";
		System.out.println(Query);
		Connection connection = null;
		Statement statement = null;
		try {
			System.out.print(place);
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/sqlserv");
			connection = ds.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(Query);
			if(rs.next())
			{
				System.out.println("inside rs");
				System.out.print(rs.getString("latitude")+","+rs.getString("longitude"));
				out.print(rs.getString(1)+","+rs.getString(2));
			}
			}
			catch(Exception e)
			{
				System.out.print("The error faced is :"+e);
			}
    %>
 