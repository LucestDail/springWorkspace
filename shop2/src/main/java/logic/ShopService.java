package logic;
import dao.UserDao;
public class ShopService {
	private UserDao userDao; 
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		userDao.insert(user);
	}
	
	public User getUserById(String id) {
		return userDao.selectOne(id);
	}
}