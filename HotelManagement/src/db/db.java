package db;
import java.sql.*;

public class db 
{
	private Connection dbConn;
	private Statement stateMent;
	
	public db()
	{
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=HotelManage";
		String userName= "sa";
		String userPwd = "123456";
		
		try 
		{
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL,userName,userPwd);
			System.out.println("Connection Successful!");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public int executeUpdate(String sql)throws SQLException
	{
		stateMent = dbConn.createStatement();
		return stateMent.executeUpdate(sql);
	}
	public ResultSet executeQuery(String sql)throws SQLException
	{
		stateMent = dbConn.createStatement();
		return stateMent.executeQuery(sql);
	}
	
	public void closeConn()throws SQLException
	{
		stateMent.close();
		dbConn.close();
	}
	
	public PreparedStatement PreparedStatement(String sql) throws
	SQLException
	{
		return dbConn.prepareStatement(sql);
	}
	
	public static void main(String args[])
	{
		new db();
	}
	
	//用来返回CallableStatement对象，以便调用存储过程
	public CallableStatement cstmt( String callProc)  throws 
	SQLException
	{
		return dbConn.prepareCall(callProc);	
	}
}
