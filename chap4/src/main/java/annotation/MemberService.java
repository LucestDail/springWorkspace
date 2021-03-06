package annotation;

import org.springframework.stereotype.Component;

import xml.Member;
import xml.UpdateInfo;

@Component("memberService")
public class MemberService {
	public void regist(Member member) {
		System.out.println("[MB]annotation.MemberService.regist() 메소드 실행");
	}
	// UpdateMemberInfoTraceAdvice pointcut 대상 메소드
	public boolean update(String memberid, UpdateInfo info) {
		System.out.println("[MB]annotation.MemberService.update() 메소드 실행");
		return true;
	}
	public boolean delete(String id, String str) {
		System.out.println("[MB]annotation.MemberService.delete() 메소드 실행");
		return false;
	}

}
