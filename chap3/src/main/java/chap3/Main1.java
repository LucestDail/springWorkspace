package chap3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 * DI 와 관련된 어노테이션
 * 
 * 1. 객체 생성 : @Component
 * 		XML : <bean id = "소문자 시작, 영문 클래스 이름" class = "패키지명.클래스명" />
 * 	=> <context:component-scan base-package="chap2" /> 설정 필요
 * 		@ComponentScan(basePackages = {"chap2","chap3"})
 * 
 * 2. 객체 주입 : @Autowired, @Resource, @Required
 * 		@Autowired : 자료형이 맞는 객체를 주입, 변수/메소드에 사용이 가능합니당...
 * 		* 컨테이너 내부 같은 자료형을 가지고 있는 객체가 여러개면 오류 뿜!
 * 		* 컨테이너 내부 같은 자료형을 가지고 있는 객체가 없어도 오류 뿜!
 * 		(required-false) => 객체가 없는 경우 null 주입
 * 		@Resource(이름) : 객체 중 이름 기준으로 객체를 주입
 * 		@Required : 자료형 기준으로 객체를 주입. 단, 무조건 주입되어야만 합니다!!!
 * 					주입되는 객체가 없는 경우 오류 발생 => 따라서 거의 사용 안함!!!
 * 
 * 3. 기능
 * 		@PostConstruct : 객체 생성 후 호출되는 메소드 위에 정의, 객체 생성 후에 실행되는 메소드...
 * 							*주입되는 객체가 완료 된 경우를 객체 생성이라고 본다...
 * 		@Qualifier : 객체의 id 이외에 다른 이름 지정이 가능!!!
 * 					* @Bean 과 같이 사용할 경우에는 이름을 지정
 * 					* @Autowired 와 같이 사용할 경우에는 객체를 주입
 * 		@Bean : 객체화 시킴. 이름은 메소드명이 객체의 이름이 됩니다...
 * 				* @Configuration 어노테이션 가진 클래스에서 사용되어야만 합니다...(객체화를 시키기 위한 환경설정 부분이기 때문!!!)
 * 		@Configuration : 자바 환경 설정 파일 지정시 사용되는 어노테이션
 * 		@ComponentScan : 스캔(한번 쫙 훑어서 패키지를 성정, Configuration - ComponentScan 은 같이 사용됩니당...
 * @author dhtmd
 *
 */
public class Main1 {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		//xml 없이 클래스 파일 AppCtx 로 설정, XML 은 단순한 도큐먼트이기 때문에 자바 환경 설정 파일을 이용하자!!!
		Executor exec = ctx.getBean("executor",Executor.class);
		exec.addUnit(new WorkUnit());
		exec.addUnit(new WorkUnit());
		
		HomeController home = ctx.getBean("homeController",HomeController.class);
		home.checkSensorAndAlarm();
		//Window intrusion
		System.out.println("==========Window Intrusion==========");
		InfraredRaySensor sensor = ctx.getBean("windowSensor",InfraredRaySensor.class);
		sensor.foundObject();
		home.checkSensorAndAlarm();
		System.out.println("==========Door Intrusion==========");
		sensor = ctx.getBean("doorSensor",InfraredRaySensor.class);
		sensor.foundObject();
		home.checkSensorAndAlarm();
		System.out.println("==========Lamp Intrusion==========");
		sensor = new InfraredRaySensor("전등센서");
		sensor.foundObject();
		home.checkSensorAndAlarm();
		home = new HomeController();
		home.checkSensorAndAlarm();
	}

}
