package logic;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.ItemDao;
import dao.UserDao;

@Service // @Componet + Service 기능(Controller + dao 중간역활) 보통 업무단으로 나눌때 사용,
public class ShopService {
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private UserDao userDao;

	public List<Item> getItemList() {
		// TODO Auto-generated method stub
		return itemDao.list();
	}

	public Item getItem(Integer id) {
		// TODO Auto-generated method stub
		return itemDao.selectOne(id);
	}

	public void itemCreate(Item item, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (item.getPicture() != null && !item.getPicture().isEmpty()) {
			uploadFileCreate(item.getPicture(), request, "img/");
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemDao.insert(item);
	}

	private void uploadFileCreate(MultipartFile picture, HttpServletRequest request, String path) {
		// TODO Auto-generated method stub
		String orgFile = picture.getOriginalFilename();
		String uploadPath = request.getServletContext().getRealPath("/") + path;
		File fpath = new File(uploadPath);
		if (!fpath.exists()) {
			fpath.mkdirs();// 업로드 폴도가 없는 경우는 폴더 생성
		}
		try {
			picture.transferTo(new File(uploadPath + orgFile));// transferTo : 업로드된 파일의 내용을 File 로 저장
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void itemUpdate(@Valid Item item, HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (item.getPicture() != null && !item.getPicture().isEmpty()) {
			uploadFileCreate(item.getPicture(), request, "img/");
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemDao.update(item);
	}

	public void itemDelete(Integer id) {
		// TODO Auto-generated method stub
		itemDao.delete(id);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void insertUser(User user) {
		// TODO Auto-generated method stub
		userDao.insert(user);
	}

	public User getUserById(String id) {
		System.out.println("ShopService - getUserById" + id);
		return userDao.selectOne(id);
	}
}
