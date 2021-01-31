package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.BoardDao;
import dao.ItemDao;
import dao.SaleDao;
import dao.SaleItemDao;
import dao.UserDao;

@Service // @Componet + Service 기능(Controller + dao 중간역활) 보통 업무단으로 나눌때 사용,
public class ShopService {
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SaleDao saleDao;
	@Autowired
	private SaleItemDao saleItemDao;
	@Autowired
	private BoardDao boardDao;

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
		return userDao.selectOne(id);
	}

	public List<Sale> salelist(String id) {
		List<Sale> list = saleDao.selectAll(id);
//		return new ArrayList<Sale>();
		return list;
	}

	public List<SaleItem> saleItemList(int saleid) {
		List<SaleItem> list = saleItemDao.selectAll(saleid);
//		return new ArrayList<SaleItem>();
		return list;
	}

	public void updateUser(User user) {
		userDao.update(user);
	}

	public void deleteUser(String userid) {
		userDao.delete(userid);
	}

	public List<User> userList(String[] idchks) {
		List<User> list = new ArrayList<>();
		for (String id : idchks) {
			list.add(userDao.selectOne(id));
		}
		return list;
	}

	public User getUserByEmailTel(String email, String tel) {
		return userDao.selectByEmailTel(email, tel);
	}

	public User getUserByIdEmailTel(String id, String email, String tel) {
		return userDao.selectByIdEmailTel(id, email, tel);
	}

	public void chgpassUser(String sessionUserid, String chgpass) {
		userDao.chgPass(sessionUserid, chgpass);
	}

	public String getSearch(User user) {
		System.out.println(user + " -> activate");
		String result = null;
		if (user.getUserid() != null) {
			result = userDao.selectByIdEmailTel(user.getUserid(), user.getEmail(), user.getPhoneno()).getPassword();
		} else {
			result = userDao.selectByEmailTel(user.getEmail(), user.getPhoneno()).getUserid();
		}
		return result;
	}

	/**
	 * sale 정보, saleitem 정보 db에 저장(dao) 1. sale 테이블의 saleid값 가져오기 => 최대값보다 큰 값 지정 2.
	 * sale 정보 저장(insert) 3. Cart 정보에서 saleitem 내용 저장(insert) 4. sale 객체에 view 에서
	 * 필요한 정보 저장
	 * 
	 * @param loginUser
	 * @param cart
	 * @return
	 */
	public Sale checkend(User loginUser, Cart cart) {
		System.out.println("Sale checkend activated -> " + loginUser + "," + cart);

		// 1. sale 테이블의 saleid값 가져오기 => 최대값보다 큰 값 지정
		int curId = saleDao.maxNumId();
		int inputId = curId + 1;
		// 2. sale 정보 저장(insert)
		Sale sale = new Sale();
		sale.setSaleid(inputId);
		sale.setUser(loginUser);
		sale.setUserid(loginUser.getUserid());
		saleDao.insert(sale);

		// 3. Cart 정보에서 saleitem 내용 저장(insert)
		int i = 0;
		for (ItemSet itemSet : cart.getItemSetList()) {
			int seq = ++i;
			SaleItem saleItem = new SaleItem(sale.getSaleid(), seq, itemSet);
			sale.getItemList().add(saleItem);
			saleItemDao.insert(saleItem);
		}
		System.out.println("[print checker] -> " + saleDao.selectOne(inputId));
		// 4. sale 객체에 view 에서 필요한 정보 저장
		return sale;
	}

	public int boardcount(String searchtype, String searchcontent) {
		return boardDao.count(searchtype, searchcontent);
	}

	public List<Board> boardlist(Integer pageNum, int limit, String searchtype, String searchcontent) {
		return boardDao.list(pageNum, limit, searchtype, searchcontent);
	}

	public Board boardDetail(Integer num) {
		return boardDao.selectOne(num);
	}

	public void readcntAdd(Integer num) {
		boardDao.readcntAdd(num);
	}

	public int boardWrite(Board board, HttpServletRequest request) {
		if (board.getFile1() != null && !board.getFile1().isEmpty()) {
			uploadFileCreate(board.getFile1(), request, "img/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		return boardDao.insert(board);
	}

	public int boardMaxNum() {
		return boardDao.maxNum();
	}

	public Board getBoard(Integer num, boolean b) {
		if (b) {
			boardDao.readcntAdd(num);
		}
		return boardDao.selectOne(num);
	}

	public int boardMaxGrp() {
		return boardDao.maxGrp();
	}

	public boolean boardDelete(Board board) {
		return boardDao.delete(board);
	}

	public int boardUpdate(Board board, HttpServletRequest request) {
		if (board.getFile1() != null && !board.getFile1().isEmpty()) {
			uploadFileCreate(board.getFile1(), request, "img/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		return boardDao.update(board);
	}

	public int boardMaxGrpStep(Board board) {
		return boardDao.maxGrpstep(board);
	}

	public Map<String, Object> graph1() {
		Map<String, Object> map = new HashMap<String, Object>();
		for(Map<String,Object> m : boardDao.graph1()) {
			map.put((String) m.get("name"), m.get("cnt"));
		}
		return map;
	}

	public Map<String, Object> graph2() {
		Map<String, Object> map = new HashMap<String, Object>();
		for(Map<String,Object> m : boardDao.graph2()) {
			map.put((String) m.get("regdate"), m.get("cnt"));
		}
		return map;
	}
}
