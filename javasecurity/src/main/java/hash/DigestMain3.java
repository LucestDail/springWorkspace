package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 1. id, password input 2. not in usersecurity table -> check id 3. password is
 * correct -> hello, id 4. password is not correct -> check password
 * 
 * @author dhtmd
 *
 */
public class DigestMain3 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:59753/classdb", "root", "159753");
		
		//아이디 입력
		System.out.println("아이디를 입력하세요 => ");
		Scanner scan = new Scanner(System.in);
		String id = scan.nextLine();

		//비밀번호 입력 -> SHA-256변환
		System.out.println("비밀번호를 입력하세요 => ");
		scan = new Scanner(System.in);
		String password = scan.nextLine();
		byte[] h = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
		StringBuffer inputPass = new StringBuffer();
		for (byte b : h) {
			inputPass.append(String.format("%02X", b));
		}
		
		//데이터베이스 조회
		String curPass = null;
		String curName = null;
		PreparedStatement ps = c.prepareStatement("select username, password from usersecurity where userid = ?");
		ResultSet rs = null;
		ps.setString(1, id);
		rs = ps.executeQuery();
		if (rs.next()) {
			curName = rs.getString("username");
			curPass = rs.getString("password");
		}
		
		//아이디 검증
		if (curPass == null) {
			System.out.println("아이디 확인");
			return; //아이디 없음, 탈출
		}
		
		//비밀번호 검증
		if (curPass.toString().equals(inputPass.toString())) { //비밀번호 일치
			System.out.println("반갑습니다! " + curName + "님");
		} else { //비밀번호 불일치
			System.out.println("비밀번호 확인");
		}

	}

}
