import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class preprocessing {

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		System.out.println("Hello World!");

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
			ResultSet res = st.executeQuery("SELECT DISTINCT OwnerUserId FROM Posts"); 
			//int WordCount;
			//int i=0;
			System.out.println("Hi");
			while(res.next()) 
			{ 
				int OwnerUserId;
				OwnerUserId= res.getInt("OwnerUserId");
				System.out.println("Owner="+OwnerUserId);
				String Body="", Title="", Tags="";
				Statement st2 = conn.createStatement();
				String Query1="SELECT PostTypeId , Body , Title , Tags FROM Posts WHERE OwnerUserId='"+OwnerUserId+"' AND PostTypeId=2";
				System.out.println(Query1);//break;
				ResultSet res2 = st2.executeQuery("SELECT Body FROM Posts WHERE OwnerUserId='"+OwnerUserId+"' AND PostTypeId=2");
				while(res2.next())
				{
					Body+=res2.getString("Body"); 
					String result = Body.replaceAll("[^\\dA-Za-z ]", " ").replaceAll("\\s+", " ");
					Body=result;  
					System.out.println(Body);

				}


				System.out.println(Body.length());
				if((Body.equals(""))==true) 
					continue;
				else
				{
					Statement st3 = conn.createStatement();
					st3.executeUpdate("INSERT into ModifiedUsers(OwnerUserId, PostTypeId, Body) VALUES('"+OwnerUserId+"',2, '"+Body+"')");
				}
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

