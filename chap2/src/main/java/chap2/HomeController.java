package chap2;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HomeController {
	private AlarmDevice alarmDevice;
	private Viewer viewer; 
	@Resource(name="camera1") //~ 이 정보를 주입함ㄱ
	private Camera camera1;//여기에 정의된 객체에~
	@Resource(name="camera2")
	private Camera camera2;
	@Resource(name="camera3")
	private Camera camera3;
	@Resource(name="camera4")
	private Camera camera4;

	private List<InfraredRaySensor> sensors;
	// 객체 자료형을 이용하여 주입
	@Autowired(required=false) //없으면 객체 주입 안함
	private Recorder recorder;
	
	@Autowired
	public void prepare(AlarmDevice alarmDevice, Viewer viewer) {
		this.alarmDevice = alarmDevice;
		this.viewer = viewer;
	}
	
	@PostConstruct
	public void init() {
		System.out.println("invoke init method");
		viewer.add(camera1);
		viewer.add(camera2);
		viewer.add(camera3);
		viewer.add(camera4);
		viewer.draw();
	}
	
	@Autowired
	@Qualifier("intrusionDetection")
	public void setSensors(List<InfraredRaySensor> sensors) {
		System.out.println("invoke setSensors method");
		this.sensors = sensors;
		for(InfraredRaySensor s : sensors) {
			System.out.println(s.getName());
		}
	}
	
	public void checkSensorAndAlarm() {
		for(InfraredRaySensor s : sensors) {
			if(s.isObjectFounded()) {
				alarmDevice.alarm(s.getName());
			}
		}
	}
}
