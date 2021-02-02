package aes;

import java.security.Key;
import java.security.MessageDigest;
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
 * usersecurity 테이블을 읽어서 사용자 id, email(복호화) 출력하기
 * @author dhtmd
 *
 */
public class CipherMain4 {
	private final static byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, (byte) 0x5A,
			(byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, (byte) 0x5A };
	static Cipher cipher;
	static {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 암호화 알고리즘 부분 / 블럭암호화 모드 / PKCS5 Padding 패딩
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		List<String> list = getIdList();

		for (String id : list) {
			System.out.println(id + " : " + decrptionAlgorithm(id));
		}
	}
	
	private static String decrptionAlgorithm(String id) throws Exception {
		String key = CipherUtil.makehash(id);
		String encryptEmail = getEmail(id);
		String decryptEmail = decrypt(encryptEmail, key);
		return decryptEmail;
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

	public static String decrypt(String cipherMsg, String key) {
		// TODO Auto-generated method stub
		byte[] plainMsg = new byte[1024];
		try {
			Key genKey = new SecretKeySpec(makeKey(key),"AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, genKey, paramSpec);
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
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
	
	private static byte[] hexToByte(String str) {
		if (str == null || str.length() < 2) {
			return null;
		}
		int len = str.length() / 2;
		byte[] buf = new byte[len];
		for (int i = 0; i < len; i++) {
			buf[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
		}
		return buf;
	}
	
	private static byte[] makeKey(String key) {
		return key.substring(0,16).getBytes();
	}
	
	public static String makehash(String msg) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] plain = msg.getBytes();
		byte[] hash = md.digest(plain);
		return byteToHex(hash);
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

}
