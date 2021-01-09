package chap2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmartPhoneViewer implements Viewer {
	@Autowired
	private DisplayMode displayMode;
	public void add(Camera camera) {
		System.out.println("monitor added -> " + camera);
	}
	public void draw() {
		System.out.println(displayMode.getType() + " mode camera image printing");
	}
}
