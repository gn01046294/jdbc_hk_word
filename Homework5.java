package homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*�ϥνҰ�employee���[�H��g�A�s�W�@�����i�H�s����u���Ӥ��C
�Q��BlobDemo.java�d�ұN�Ҧ����ɥH�妸�覡�s�W�ܸ�Ʈw���C
���G�Ҧ��Ӥ��m��res��Ƨ����A���F��K���g�{���A��ĳ�Ҧ��Ӥ������ɦW�n�@�P�A���ɦW�٥H���u�s���R�W
*/
public class Homework5 {

	public static void main(String[] args) {
		Connection conn = null;
		try { // �s�u��Ʈw
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=jdbc";
			conn = DriverManager.getConnection(connUrl, "sa", "passw0rd");

			// �d�߭��u�s���s��
			String qrystmt = "select empno from employee_p";
			PreparedStatement pstmt = conn.prepareStatement(qrystmt);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
				// �]�wŪ���ɮת���l
				File inFile = new File("C:\\JDBC\\workspace\\jdbc_hk\\res", rs.getInt(1) + ".gif");
				FileInputStream fis = new FileInputStream(inFile);

				String sin = "update employee_p set photo =? where empno =? ";// SQL���O 
				PreparedStatement pstmt1 = conn.prepareStatement(sin); // ���� SQL���O
				
				pstmt1.setBinaryStream(1, fis);//�s�W  fis(�ɮ׸��) �g�J  �Ĥ@��? (���)
				pstmt1.setInt(2, rs.getInt(1));

				pstmt1.addBatch(); // �a������List����

				pstmt1.executeBatch(); // ����妸�@�~
				
				
				System.out.println("UPDATA employee_p is successful!");
				pstmt1.close();
			}
			
           //�d�ߵ��G��ܩ�console
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
