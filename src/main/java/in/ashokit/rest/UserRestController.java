package in.ashokit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.bindings.ActivateAccount;
import in.ashokit.bindings.Login;
import in.ashokit.bindings.User;
import in.ashokit.service.UserMgmtServiceImpl;

@RestController
public class UserRestController {
	@Autowired
	private UserMgmtServiceImpl service;
	@PostMapping("/user")
	public ResponseEntity<String> userReg(@RequestBody User user)
	{
		boolean saveUser=service.saveUser(user);
		if(saveUser)
		{
			return new ResponseEntity<>("Registration success",HttpStatus.CREATED);
			
		}
		else
		{
			return new ResponseEntity<>("Registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/activated")
	public ResponseEntity<String> activateAccount(@RequestBody ActivateAccount acc)
	{
		boolean isActivated=service.activateUserAcc(acc);
		if(isActivated)

	{
			return new ResponseEntity<>("activated",HttpStatus.OK);
			}
		else
		{
			return new ResponseEntity<>("invalid temp password",HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers()
	{
 List<User> allusers=service.getAllUsers();
	return new ResponseEntity<>(allusers,HttpStatus.OK);
	

}
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer useId)
	{
		User user=service.getUserById(useId);
		return new ResponseEntity<>(user,HttpStatus.OK);
		
	}
	@DeleteMapping("/user/{usrId}")
	public ResponseEntity<String> deleteUserById(@PathVariable Integer  userId)
	{
		boolean isDeleted=service.deleteUserById(userId);
		if(isDeleted)
		{
			return new ResponseEntity<>("deleted",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("failed",HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
	}
	@GetMapping("/user/{userId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer userId,@PathVariable String status)
	{
		boolean isChanged=service.changeAccountStatus(userId, status);
		if(isChanged)
		{
			return new ResponseEntity<>("status changed",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("failed",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		}
	}
		@PostMapping("/login")
		public  ResponseEntity<String> login(@RequestBody Login login)
		{
			String status=service.login(login);
		
			return new ResponseEntity<>(status,HttpStatus.OK);
		}
		@GetMapping("/forgotpwd/{email}")
		public ResponseEntity<String> forgotpwd(@PathVariable String email)
		{
			String status=service.forgotPwd(email);
			return new ResponseEntity<>(status,HttpStatus.OK);
		}
	}
