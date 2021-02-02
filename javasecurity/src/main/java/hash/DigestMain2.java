package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. change database configuration useraccount password => hash algorithm =>
 * usersecurity create table usersecurity as select * from useraccount select *
 * from usersecurity alter table usersecurity modify password varchar(250) not
 * null desc usersecurity => password change type to varchar(250)
 * 
 * 2. useraccount - read -> usersecurity - update with sha256 -> password
 * encryption
 * 
 * @author dhtmd
 *
 */

public class DigestMain2 {
	public static void main(String[] args) {

		String encryptingType = "SHA-256";

		List<String> DBIdList = getDBIdList();

		for (String encryptionTargetId : DBIdList) {
			if (encrptionAlgorithm(encryptionTargetId, encryptingType)) {
				System.out.println(encryptionTargetId + " -> success");
			} else {
				System.out.println(encryptionTargetId + " -> fail");
			}
		}

	}

	private static List<String> getDBIdList() {
		Connection conn = DbConnection.getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<String> list = new ArrayList<String>();
		String sql = "select userid from useraccount";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("userid"));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, pstmt, rs);
		}
		return list;
	}

	public static boolean encrptionAlgorithm(String encryptionTargetId, String encryptingType) {
		String curPass = getPassword(encryptionTargetId);
		String encryptPassword = encryptPassword(encryptingType, curPass);
		return updatePassword(encryptionTargetId, encryptPassword);
	}

	public static String getPassword(String userid) {
		Connection conn = DbConnection.getConnection();
		String sql = "select password from useraccount where userid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, pstmt, null);
		}
		return null;
	}

	public static String encryptPassword(String encryptingType, String password) {
		System.out.println("encrpying target password : " + password);
		byte[] plain = null;
		byte[] hash = null;
		MessageDigest md = null;
		plain = password.getBytes();
		try {
			md = MessageDigest.getInstance(encryptingType);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		hash = md.digest(plain);
		StringBuilder sb = new StringBuilder();
		for (byte b : hash) {
			sb.append(String.format("%02X", b));
		}
		System.out.println("enc -> " + sb.toString());
		return sb.toString();
	}

	public static boolean updatePassword(String userid, String password) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update usersecurity set password = ? where userid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, userid);
			return pstmt.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, pstmt, null);
		}
		return false;
	}

}
