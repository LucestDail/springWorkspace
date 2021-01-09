package chap3;

import org.springframework.stereotype.Component;

@Component
public class SmsAlarmDevice implements AlarmDevice{
	public void alarm(String name) {
		System.out.println(name + " -> intrusion detected");
	}

}
