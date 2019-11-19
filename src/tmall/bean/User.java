package tmall.bean;

public class User {

	private int id;
	private String name; // �˺�
	private String password; // ����
	
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
	
	// ��ȡ������Ϣ
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
