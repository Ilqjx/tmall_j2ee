package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	static String ip = "127.0.0.1"; // ip
	static int port = 3306; // �˿ں�
	static String database = "tmall"; // ���ݿ�
	static String encoding = "UTF-8"; // ���뷽ʽ
	static String loginName = "root"; // �˺�
	static String password = "admin"; // ����
	
	// �� JVM �������ļ���ʱ���ִ�����еľ�̬�����
	static {
		try {
			// �� mysql ����ע�ᵽ DriverManager ��
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// ������ݿ�����
	public static Connection getConnection() throws SQLException {
		String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
		return DriverManager.getConnection(url, loginName, password);
	}
	
}
