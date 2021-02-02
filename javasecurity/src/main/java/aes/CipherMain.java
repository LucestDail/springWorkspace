package aes;

public class CipherMain {
	public static void main(String[] args) {
		String plain1 = "안녕하세요. 홍길동 입니다.";
		String cipher1 = CipherUtil.encrypt(plain1);
		System.out.println("암호문 : " + cipher1);
		String cipher2 = CipherUtil.decrypt(cipher1);
		System.out.println("복호문 : " + cipher2);
	}
}
