package homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*使用課堂的employee表格加以改寫，新增一個欄位可以存放員工的照片。
利用BlobDemo.java範例將所有圖檔以批次方式新增至資料庫中。
註：所有照片置於res資料夾中，為了方便撰寫程式，建議所有照片的副檔名要一致，圖檔名稱以員工編號命名
*/
public class Homework5 {

	public static void main(String[] args) {
		Connection conn = null;
		try { // 連線資料庫
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=jdbc";
			conn = DriverManager.getConnection(connUrl, "sa", "passw0rd");

			// 查詢員工編號編號
			String qrystmt = "select empno from employee_p";
			PreparedStatement pstmt = conn.prepareStatement(qrystmt);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
				// 設定讀取檔案的位子
				File inFile = new File("C:\\JDBC\\workspace\\jdbc_hk\\res", rs.getInt(1) + ".gif");
				FileInputStream fis = new FileInputStream(inFile);

				String sin = "update employee_p set photo =? where empno =? ";// SQL指令 
				PreparedStatement pstmt1 = conn.prepareStatement(sin); // 執行 SQL指令
				
				pstmt1.setBinaryStream(1, fis);//新增  fis(檔案資料) 寫入  第一個? (欄位)
				pstmt1.setInt(2, rs.getInt(1));

				pstmt1.addBatch(); // 家到隱藏List物件內

				pstmt1.executeBatch(); // 執行批次作業
				
				
				System.out.println("UPDATA employee_p is successful!");
				pstmt1.close();
			}
			
           //查詢結果顯示於console
			String qrystmt1 = "select * from employee_p";
			PreparedStatement pstmt1 = conn.prepareStatement(qrystmt1);
			ResultSet rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				System.out.print(rs1.getString(1)+"   ");
				System.out.print(rs1.getString(2)+"   ");
				System.out.print(rs1.getString(3)+"   ");
				System.out.print(rs1.getString(4)+"   ");
				System.out.print(rs1.getString(5)+"   ");
				System.out.print(rs1.getString(6)+"   ");
				System.out.println(rs1.getString(7));
			}
			rs1.close();
			pstmt.close();
			rs.close();

		} catch (ClassNotFoundException cfe) {
			cfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (FileNotFoundException e) {
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
