package homework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*設定一個整數batch size，用以分批處理批次作業。*/


public class Homework3 {

	public static void main(String[] args) {

		Connection conn = null;
		int batchsize = 3;
		int i = 0;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 註冊驅動程式

			String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=jdbc";// 建立url
			conn = DriverManager.getConnection(connUrl, "sa", "passw0rd");// 取得連線

			String qrystmt = "select empno, salary from employee"; // SQL指令
			PreparedStatement pstmt = conn.prepareStatement(qrystmt); // 用prepareStatement動態指令執行SQL指令
			ResultSet rs = pstmt.executeQuery();// 回傳查詢的結果

			String updstmt = "update employee set salary = ? where empno = ?";
			// ※PreparedStatement pstmt1 = conn.prepareStatement(updstmt);此行不可這樣寫 要寫這樣↓
			pstmt = conn.prepareStatement(updstmt);
			while (rs.next()) {
				pstmt.setDouble(1, rs.getDouble("salary") + 1);
				pstmt.setInt(2, rs.getInt("empno"));
				pstmt.addBatch();
				i++;
				if (i == batchsize) {  //滿足和batchsize 條件才會新增 (也就是說要一次會 新3筆資料)
					i = 0;
					pstmt.executeBatch();

				}
			}

			pstmt.close();
			rs.close();
		} catch (ClassNotFoundException ce) {
			System.out.println("無法註冊此Dirver或Dirver名稱有誤(就是你KEY錯字啦! :" + ce);
		} catch (SQLException e) {
			System.out.println("連線有誤!請檢查Url是否正確!" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					System.out.println("連線有誤!請重新檢查" + e1);
				}
			}
		}

	}

}
