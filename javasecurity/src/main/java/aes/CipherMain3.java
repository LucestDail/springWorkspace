package aes;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * useraccount 테이블의 email 값 읽어서 usersecurity 테이블 email 암호화 하기
 * 1. email 컬럼의 크기 300 변경
 * 2. key는 userid (SHA-256) 해쉬값의 문자열 앞 16자리로 설정
 * @author dhtmd
 *
 */
public class CipherMain3 {
	private final static byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, (byte) 0x5A,
			(byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, (byte) 0x5A };
	static Cipher cipher;
	static {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		List<String> list = getIdList();

		for (String id : list) {
			if (encrptionAlgorithm(id)) {
				System.out.println(id + " -> success");
			} else {
				System.out.println(id + " -> fail");
			}
		}
	}
	
	public static boolean encrptionAlgorithm(String id) throws Exception {
		String key = CipherUtil.makehash(id);
		String targetEmail = getEmail(id);
		String encryptEmail = encryptEmail(targetEmail, key);
		return updatePassword(id, encryptEmail);
	}

	private static String getEmail(String id) {
		Connection conn = DbConnection.getConnection();
		String sql = "select email from usersecurity where userid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, pstmt, null);
		}
		return null;
	}

	private static boolean updatePassword(String id, String email) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update usersecurity set email = ? where userid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			return pstmt.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, pstmt, null);
		}
		return false;
	}

	private static String encryptEmail(String plain, String key) {
		byte[] cipherMsg = new byte[1024];
		try {
			Key genKey = new SecretKeySpec(makeKey(key), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); // 초기화 백터 설정
			cipher.init(Cipher.ENCRYPT_MODE, genKey, paramSpec);
			cipherMsg = cipher.doFinal(plain.getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg);
	}

	private static List<String> getIdList() {
		Connection conn = DbConnection.getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<String> list = new ArrayList<String>();
		String sql = "select userid from usersecurity";
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
	
	private static byte[] makeKey(String key) {
		return key.substring(0,16).getBytes();
	}
	
	private static String byteToHex(byte[] cipherMsg) {
		if (cipherMsg == null) {
			return null;
		}
		String str = "";
		for (byte b : cipherMsg) {
			str += String.format("%02X", b);
		}
		return str;
	}
}
