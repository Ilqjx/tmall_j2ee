package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	static String ip = "127.0.0.1"; // ip
	static int port = 3306; // 端口号
	static String database = "tmall"; // 数据库
	static String encoding = "UTF-8"; // 编码方式
	static String loginName = "root"; // 账号
	static String password = "admin"; // 密码
	
	// 当 JVM 加载类文件的时候会执行其中的静态代码块
	static {
		try {
			// 将 mysql 驱动注册到 DriverManager 中
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// 获得数据库连接
	public static Connection getConnection() throws SQLException {
		String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
		return DriverManager.getConnection(url, loginName, password);
	}
	
}
