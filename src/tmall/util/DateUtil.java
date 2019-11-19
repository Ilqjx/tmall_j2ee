package tmall.util;

public class DateUtil {

	// java.util.Date 与 java.sql.Timestamp 的相互转换
	public static java.sql.Timestamp d2t(java.util.Date date) {
		if (null == date) {
			return null;
		}
		return new java.sql.Timestamp(date.getTime());
	}
	
	public static java.util.Date t2d(java.sql.Timestamp t) {
		if (null == t) {
			return null;
		}
		return new java.util.Date(t.getTime());
	}
	
}
