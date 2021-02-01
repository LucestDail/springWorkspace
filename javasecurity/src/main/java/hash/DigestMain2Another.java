package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DigestMain2Another {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:59753/classdb", "root", "159753");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			rs = c.prepareStatement("select userid, password from useraccount").executeQuery();
			while (rs.next()) {
				byte[] h = MessageDigest.getInstance("SHA-256").digest(rs.getString("password").getBytes());
				StringBuffer sb = new StringBuffer();
				for (byte b : h) {
					sb.append(String.format("%02X", b));
				}
				ps = c.prepareStatement("update usersecurity set password = ? where userid = ?");
				ps.setString(1, sb.toString());
				ps.setString(2, rs.getString("userid"));
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(c, ps, rs);
		}
	}
}
