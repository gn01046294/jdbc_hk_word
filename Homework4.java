package homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/*在res/emp.txt文字檔中設定五筆employee的資料，將之批次新增至資料庫中。
註：一筆資料一列，每個資料欄的資料以逗號(,)隔開
*/
public class Homework4 {

	public static void main(String[] args) {
		
		//以下為連線資料庫
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=jdbc";
			conn = DriverManager.getConnection(connUrl, "sa", "passw0rd");
        //以上為連線資料庫
			
		//以下..為了查詢有幾個欄位
            String qrystmt = "select * from employee_t1";			
			PreparedStatement prestmt0 = conn.prepareStatement(qrystmt);
			ResultSet rs = prestmt0.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();//取得欄位數 要用DatabaseMetaData 介面內的方法!!
			System.out.println("目的表格employee_t1的欄位共有 :"+count+"欄");   //有6個欄位
		//新增指令	
			String inserts = "insert into employee_t1 values (?, ?, ?, ?, ?, ?)";
			PreparedStatement prestmt = conn.prepareStatement(inserts);
        //檔案位子
			File fin = new File("C:\\JDBC\\workspace\\jdbc_hk\\res", "emp.txt");     //檔案位子
			BufferedReader bfr = new BufferedReader(new FileReader(fin));        //讀取檔案
        //將檔案讀取出來後,insert到資料庫內 方法-1
			
			String s = null;               //將讀到的資料放在s
			while ((s = bfr.readLine()) != null)  //用while迴圈讀取檔案 並將讀取到的資料放到s內
			{         //readLine()會一次讀一整行 

				String rds[] = s.split(",");      //如不去除"," 日期將無法新增!!
				
				for(int i =1;i<=count;i++)        //利用取得的欄位數 用一個迴圈將陣列rds[i-1]的元素 新增到 第i個欄位 (rds[0] set到第i個欄位)
				{
					prestmt.setString(i, rds[i-1]);
					if(i==count)                  //當資料
					{
						prestmt.addBatch();     //將以上多個SQL指令加入到一個隱含的List物件中..
					}
			    }
		//將檔案讀取出來後,insert到資料庫內 方法-2		
//				prestmt.setString(1, rds[0]);
//				prestmt.setDouble(1, Double.parseDouble(rds[0]));
//				prestmt.setString(2, rds[1]);
//				prestmt.setString(3, rds[2]);//如不將,刪除的話 會跳出從字元字串轉換成日期及/或時間時，轉換失敗。
//				prestmt.setString(4, rds[3]);
//				prestmt.setDouble(4, Double.parseDouble(rds[3]));
//				prestmt.setString(5, rds[4]);
//				prestmt.setDouble(5, Double.parseDouble(rds[4]));
//				prestmt.setString(6, rds[5]);
//				
//				prestmt.addBatch();

				prestmt.executeBatch(); //將資料寫出
			}
			//顯示結果在console
			PreparedStatement prestmt2 = conn.prepareStatement(qrystmt);
			ResultSet rs1 = prestmt0.executeQuery();
			while(rs1.next()) {
				System.out.print("empno: "+rs1.getString(1));
				System.out.print("ename: "+rs1.getString(2));
				System.out.print("hiredate: "+rs1.getString(3));
				System.out.print("salary: "+rs1.getString(4));
				System.out.print("deptno: "+rs1.getString(5));
				System.out.println("title: "+rs1.getString(6));
			}
			//關閉資源
			rs1.close();
			prestmt2.close();
			bfr.close();
			prestmt.close();
			rs.close();
			prestmt0.close();
		} catch (ClassNotFoundException ce) {
			System.out.println("驅動程式註冊有誤:" + ce);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
