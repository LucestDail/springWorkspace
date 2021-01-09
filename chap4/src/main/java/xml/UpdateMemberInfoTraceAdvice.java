package xml;

public class UpdateMemberInfoTraceAdvice {
	// 아규먼트 : ret, id, info
	// 파라매터 설정
	// Object result -> ret
	// String memberid -> id
	// UpdateInfo info -> info
	public void traceReturn(Object result, String memberid, UpdateInfo info) {
		System.out.println("[TA] 정보 수정 : 결과 -> " + result + ", 대상회원(" + memberid + ") => 수정정보(" +info+ ")");
	}

}
