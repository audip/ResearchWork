import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class generatetextfile {

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		System.out.println("Program starting! Hello User!");

		String url = "jdbc:mysql://localhost:3306/"; 
		String dbName = "msr"; 
		String driver = "com.mysql.jdbc.Driver"; 
		String userName = "root";
		String password = ""; 

		try 
		{ 
			Class.forName(driver).newInstance(); 
			Connection conn = DriverManager.getConnection(url+dbName,userName,password); 
			Statement st = conn.createStatement(); 
			ResultSet res = st.executeQuery("SELECT DISTINCT OwnerUserId, Body, Title, Tags FROM ModifiedUsers LIMIT 1"); 
			String Body="", Title="", Tags="";
			while(res.next()) 
			{ 
				int OwnerUserId;
				OwnerUserId = res.getInt("OwnerUserId");
				Body+=res2.getString("Body"); 
				String result = Body.replaceAll("[^\\dA-Za-z ]", " ").replaceAll("\\s+", " ");
				System.out.println(Body);
			}

			System.out.println("Ran Successfully");
			conn.close(); 
		}
		catch (Exception e) 
		{ 
			//e.getMessage();
			e.printStackTrace(); 
		} 
	}
}

