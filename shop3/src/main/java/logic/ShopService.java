package logic;

import java.io.File;
import java.util.ArrayList;
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
		return itemDao.list();
	}

	public Item getItem(Integer id) {
		return itemDao.selectOne(id);
	}

	public void itemCreate(Item item, HttpServletRequest request) {
		if (item.getPicture() != null && !item.getPicture().isEmpty()) {
			uploadFileCreate(item.getPicture(), request, "img/");
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemDao.insert(item);
	}

	private void uploadFileCreate(MultipartFile picture, HttpServletRequest request, String path) {
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
		if (item.getPicture() != null && !item.getPicture().isEmpty()) {
			uploadFileCreate(item.getPicture(), request, "img/");
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemDao.update(item);
	}

	public void itemDelete(Integer id) {
		itemDao.delete(id);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void insertUser(User user) {
		userDao.insert(user);
	}

	public User getUserById(String id) {
		System.out.println("ShopService - getUserById" + id);
		return userDao.selectOne(id);
	}

	public List<Sale> salelist(String id) {
		return new ArrayList<Sale>();
	}

	public List<SaleItem> saleItemList(int saleid) {
		return new ArrayList<SaleItem>();
	}

	public void updateUser(User user) {
		userDao.update(user);
	}

	public void deleteUser(String userid) {
		userDao.delete(userid);
	}

	public List<User> userList(String[] idchks) {
		List<User> list = new ArrayList<>();
		for(String id : idchks) {
			list.add(userDao.selectOne(id));
		}
		return list;
	}

	public User getUserByEmailTel(String email, String tel) {
		return userDao.selectByEmailTel(email,tel);
	}

	public User getUserByIdEmailTel(String id, String email, String tel) {
		return userDao.selectByIdEmailTel(id,email,tel);
	}

	public void chgpassUser(String sessionUserid, String chgpass) {
		// TODO Auto-generated method stub
		userDao.chgPass(sessionUserid,chgpass);
	}
}
