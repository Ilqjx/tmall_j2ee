package tmall.bean;

public class User {

	private int id;
	private String name; // 账号
	private String password; // 密码
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	// 获取匿名信息
	public String getAnonymousName() {
		if (null == name) {
			return null;
		}
		if (name.length() <= 1) {
			return "*";
		}
		if (name.length() == 2) {
			return name.substring(0, 1) + "*";
		}
//		if (name.length() > 2) {
//			String anonymousName = name.substring(0, 1);
//			for (int i = 1; i < name.length() - 1; i++) {
//				anonymousName += "*";
//			}
//			anonymousName += name.substring(name.length() - 1, name.length());
//			return anonymousName;
//		}
//		return null;
		char[] cs = name.toCharArray();
		for (int i = 1; i < cs.length - 1; i++) {
			cs[i] = '*';
		}
		return new String(cs);
	}
	
}
