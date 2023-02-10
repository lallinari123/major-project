package in.ashokit.service;

import java.util.List;

import in.ashokit.bindings.ActivateAccount;
import in.ashokit.bindings.Login;
import in.ashokit.bindings.User;

public interface UserMgmtService {
	public boolean saveUser(User user);
	public boolean activateUserAcc(ActivateAccount activateacc);
	public List<User> getAllUsers();
	public User getUserById(Integer id);
	public boolean deleteUserById(Integer userId);
	public boolean changeAccountStatus(Integer userId,String status);
	public String login(Login login);
	public String forgotPwd(String Email);
	
	

}
