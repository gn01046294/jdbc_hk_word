package homework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*�]�w�@�Ӿ��batch size�A�ΥH����B�z�妸�@�~�C*/


public class Homework3 {

	public static void main(String[] args) {

		Connection conn = null;
		int batchsize = 3;
		int i = 0;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// ���U�X�ʵ{��

			String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=jdbc";// �إ�url
			conn = DriverManager.getConnection(connUrl, "sa", "passw0rd");// ���o�s�u

			String qrystmt = "select empno, salary from employee"; // SQL���O
			PreparedStatement pstmt = conn.prepareStatement(qrystmt); // ��prepareStatement�ʺA���O����SQL���O
			ResultSet rs = pstmt.executeQuery();// �^�Ǭd�ߪ����G

			String updstmt = "update employee set salary = ? where empno = ?";
			// ��PreparedStatement pstmt1 = conn.prepareStatement(updstmt);���椣�i�o�˼g �n�g�o�ˡ�
			pstmt = conn.prepareStatement(updstmt);
			while (rs.next()) {
				pstmt.setDouble(1, rs.getDouble("salary") + 1);
				pstmt.setInt(2, rs.getInt("empno"));
				pstmt.addBatch();
				i++;
				if (i == batchsize) {  //�����Mbatchsize ����~�|�s�W (�]�N�O���n�@���| �s3�����)
					i = 0;
					pstmt.executeBatch();

				}
			}

			pstmt.close();
			rs.close();
		} catch (ClassNotFoundException ce) {
			System.out.println("�L�k���U��Dirver��Dirver�W�٦��~(�N�O�AKEY���r��! :" + ce);
		} catch (SQLException e) {
			System.out.println("�s�u���~!���ˬdUrl�O�_���T!" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					System.out.println("�s�u���~!�Э��s�ˬd" + e1);
				}
			}
		}

	}

}
