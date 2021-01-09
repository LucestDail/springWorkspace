package chap2;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value = "prototype", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Worker {
	public void work(WorkUnit unit){
		System.out.println(this + " : work : " + unit);
	}
}
