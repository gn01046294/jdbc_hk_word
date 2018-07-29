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

/*�bres/emp.txt��r�ɤ��]�w����employee����ơA�N���妸�s�W�ܸ�Ʈw���C
���G�@����Ƥ@�C�A�C�Ӹ���檺��ƥH�r��(,)�j�}
*/
public class Homework4 {

	public static void main(String[] args) {
		
		//�H�U���s�u��Ʈw
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connUrl = "jdbc:sqlserver://localhost:1433;databaseName=jdbc";
			conn = DriverManager.getConnection(connUrl, "sa", "passw0rd");
        //�H�W���s�u��Ʈw
			
		//�H�U..���F�d�ߦ��X�����
            String qrystmt = "select * from employee_t1";			
			PreparedStatement prestmt0 = conn.prepareStatement(qrystmt);
			ResultSet rs = prestmt0.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();//���o���� �n��DatabaseMetaData ����������k!!
			System.out.println("�ت����employee_t1�����@�� :"+count+"��");   //��6�����
		//�s�W���O	
			String inserts = "insert into employee_t1 values (?, ?, ?, ?, ?, ?)";
			PreparedStatement prestmt = conn.prepareStatement(inserts);
        //�ɮצ�l
			File fin = new File("C:\\JDBC\\workspace\\jdbc_hk\\res", "emp.txt");     //�ɮצ�l
			BufferedReader bfr = new BufferedReader(new FileReader(fin));        //Ū���ɮ�
        //�N�ɮ�Ū���X�ӫ�,insert���Ʈw�� ��k-1
			
			String s = null;               //�NŪ�쪺��Ʃ�bs
			while ((s = bfr.readLine()) != null)  //��while�j��Ū���ɮ� �ñNŪ���쪺��Ʃ��s��
			{         //readLine()�|�@��Ū�@��� 

				String rds[] = s.split(",");      //�p���h��"," ����N�L�k�s�W!!
				
				for(int i =1;i<=count;i++)        //�Q�Ψ��o������ �Τ@�Ӱj��N�}�Crds[i-1]������ �s�W�� ��i����� (rds[0] set���i�����)
				{
					prestmt.setString(i, rds[i-1]);
					if(i==count)                  //����
					{
						prestmt.addBatch();     //�N�H�W�h��SQL���O�[�J��@�����t��List����..
					}
			    }
		//�N�ɮ�Ū���X�ӫ�,insert���Ʈw�� ��k-2		
//				prestmt.setString(1, rds[0]);
//				prestmt.setDouble(1, Double.parseDouble(rds[0]));
//				prestmt.setString(2, rds[1]);
//				prestmt.setString(3, rds[2]);//�p���N,�R������ �|���X�q�r���r���ഫ�������/�ήɶ��ɡA�ഫ���ѡC
//				prestmt.setString(4, rds[3]);
//				prestmt.setDouble(4, Double.parseDouble(rds[3]));
//				prestmt.setString(5, rds[4]);
//				prestmt.setDouble(5, Double.parseDouble(rds[4]));
//				prestmt.setString(6, rds[5]);
//				
//				prestmt.addBatch();

				prestmt.executeBatch(); //�N��Ƽg�X
			}
			//��ܵ��G�bconsole
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
			//�����귽
			rs1.close();
			prestmt2.close();
			bfr.close();
			prestmt.close();
			rs.close();
			prestmt0.close();
		} catch (ClassNotFoundException ce) {
			System.out.println("�X�ʵ{�����U���~:" + ce);
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
