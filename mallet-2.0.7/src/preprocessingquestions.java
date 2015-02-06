import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class preprocessingquestions {


	public static void main(String[] args) {
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
			ResultSet res = st.executeQuery("SELECT DISTINCT OwnerUserId FROM Posts WHERE PostTypeId= 1 OR PostTypeId= 2"); 
			Statement st4 = conn.createStatement();
			ResultSet res4 = st4.executeQuery("SELECT Count(*) AS Count FROM Posts");
			res4.next();
			double total = res4.getDouble("Count");
			int counter=0;
			System.out.println("Starting the Pre-Processing!");
			while(res.next()) 
			{ 
				int OwnerUserId;
				OwnerUserId= res.getInt("OwnerUserId");
				//System.out.println("Owner="+OwnerUserId);
				String Body="", Title="", Tags="";
				
								
				
				Statement st2 = conn.createStatement();
				String Query1="SELECT PostTypeId , Body , Title , Tags FROM Posts WHERE OwnerUserId='"+OwnerUserId+"' AND PostTypeId=1";
				//System.out.println(Query1);//break;
				ResultSet res2 = st2.executeQuery("SELECT PostTypeId , Body , Title , Tags FROM Posts WHERE OwnerUserId='"+OwnerUserId+"' AND PostTypeId=1 AND Body IS NOT NULL AND Title IS NOT NULL AND Tags IS NOT NULL");
				while(res2.next())
				{
					Body+=res2.getString("Body"); 
					String result = Body.replaceAll("[^\\dA-Za-z ]", " ").replaceAll("\\s+", " ");
					Body=result;  

					Title=Title+res2.getString("Title");
					String result1 = Title.replaceAll("[^\\dA-Za-z ]", " ").replaceAll("\\s+", " ");
					Title=result1;  

					Tags=Tags+res2.getString("Tags");
					String result2 = Tags.replaceAll("[^\\dA-Za-z ]", " ").replaceAll("\\s+", " ");
					Tags=result2;  


				}
				++counter;
				System.out.println("Processed="+counter+"/"+(int)total);

				if((Body.equals(""))==true || (Title.equals(""))==true || (Tags.equals(""))==true) 
					continue;
				else
				{
					Statement st3 = conn.createStatement();
					st3.executeUpdate("INSERT into ModifiedUsers(OwnerUserId, PostTypeId, Body, Title, Tags) VALUES('"+OwnerUserId+"',1, '"+Body+"','"+Title+"','"+Tags+"')");
				}
			}

			System.out.println("Ran Successfully! Developed by Aditya, Sanjeev & Udit under the guidance of Prof. Tirath Sahu");
			conn.close(); 
		}
		catch (Exception e) 
		{ 
			//e.getMessage();
			e.printStackTrace(); 
		} 
	}
}