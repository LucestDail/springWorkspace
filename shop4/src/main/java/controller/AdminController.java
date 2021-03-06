package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dao.UserDao;
import exception.LoginException;
import logic.Mail;
import logic.ShopService;
import logic.User;

@Controller
@RequestMapping("admin")
public class AdminController {
	@Autowired
	private ShopService service;
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("list")
	public ModelAndView adminCheckList(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<User> list = userDao.list();
		mav.addObject("list",list);
		return mav;
	}
	
	@PostMapping("mailForm")
	public ModelAndView mailform(String[] idchks, HttpSession session) {
		ModelAndView mav = new ModelAndView("admin/mail");
		if(idchks == null || idchks.length == 0) {
			throw new LoginException("메일을 보낼 대상자를 선택하세요","list.shop");
		}
		List<User> list = service.userList(idchks);
		mav.addObject("list",list);
		return mav;
	}
	
	@RequestMapping("mail")
	public ModelAndView mail(Mail mail, HttpSession session) {
		ModelAndView mav = new ModelAndView("alert");
		mailSend(mail);
		mav.addObject("message","메일 전송이 완료되었습니다.");
		mav.addObject("url","list.shop");
		return mav;
	}
	
	private void mailSend(Mail mail) {
		MyAuthenticator auth = new MyAuthenticator(mail.getNaverid(), mail.getNaverpw());
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("C:/Users/dhtmd/myworkspace/springWorkspace/shop3/src/main/resources/mail.properties");
			prop.load(fis);
			prop.put("mail.smtp.user", mail.getNaverid());
		}catch(IOException e) {
			e.printStackTrace();
		}
		Session session = Session.getInstance(prop, auth);
		MimeMessage mimeMsg = new MimeMessage(session);
		try {
			mimeMsg.setFrom(new InternetAddress(mail.getNaverid() + "@naver.com"));
			List<InternetAddress> addrs = new ArrayList<InternetAddress>();
			String[] emails = mail.getRecipient().split(",");
			for(String email : emails) {
				try {
					addrs.add(new InternetAddress(new String(email.getBytes("utf-8"),"8859_1")));
				}catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			InternetAddress[] arr = new InternetAddress[emails.length];
			for(int i = 0; i<addrs.size(); i++) {
				arr[i] = addrs.get(i);
			}
			mimeMsg.setSentDate(new Date());
			mimeMsg.setRecipients(Message.RecipientType.TO, arr);
			mimeMsg.setSubject(mail.getTitle());
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart message = new MimeBodyPart();
			message.setContent(mail.getContents(),mail.getMtype());
			multipart.addBodyPart(message);
			for(MultipartFile mf : mail.getFile1()) {
				if((mf != null) && (!mf.isEmpty())) {
					multipart.addBodyPart(bodyPart(mf));
				}
			}
			mimeMsg.setContent(multipart);
			Transport.send(mimeMsg);
		}catch(MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private BodyPart bodyPart(MultipartFile mf) {
		
		MimeBodyPart body = new MimeBodyPart();
		String orgFile = mf.getOriginalFilename();
		String path = "C:/Users/dhtmd/myworkspace/springWorkspace/shop3/src/main/resources/mailupload/"; //메일 전송을 위한 업로드 파일 저장 폴더
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		File f1 = new File(path + orgFile);
		try {
			mf.transferTo(f1);
			body.attachFile(f1);
			body.setFileName(new String(orgFile.getBytes("UTF-8"),"8859_1"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	private final class MyAuthenticator extends Authenticator{
		private String id;
		private String pw;
		public MyAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id,pw);
		}
	}
	
}
