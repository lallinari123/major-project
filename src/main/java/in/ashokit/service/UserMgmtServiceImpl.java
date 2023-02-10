package in.ashokit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.Entity;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.bindings.ActivateAccount;
import in.ashokit.bindings.Login;
import in.ashokit.bindings.User;
import in.ashokit.entity.UserMaster;
import in.ashokit.repo.UserMasterRepo;
import in.ashokit.utils.Emailutils;
@Service
public class UserMgmtServiceImpl implements UserMgmtService {
	@Autowired
	UserMasterRepo userMasterRepo;
	@Autowired 
	private Emailutils emailutils;

	@Override
	public boolean saveUser(User user) 
	{
		UserMaster entity=new UserMaster();
       BeanUtils.copyProperties(user, entity);
       entity.setPassword(generateRandomPwd());
       entity.setAccStatus("IN-ACTIVE");
     
	          UserMaster save=userMasterRepo.save(entity);
	          String subject="your registration sucess";
	          String filename="REG-EMAIL-BODY.txt";
	          String body=readEmailBody(entity.getFullName(),entity.getPassword(),filename);
	          
	          emailutils.sendMail(user.getEmail(),subject,body);
		
		return save.getUserId()!=null;
	}

	@Override
	public boolean activateUserAcc(ActivateAccount activateacc) {
		UserMaster usermaster=new UserMaster();
		usermaster.setEmailId(activateacc.getEmailId());
		usermaster.setPassword(activateacc.getTempPwd());
		//usermaster.setAccStatus("ACTIVE");
		//Example<UserMaster> of=new Example.of(usermaster);
		Example<UserMaster> of=Example.of(usermaster);
		List<UserMaster> findall=userMasterRepo.findAll(of);
		if (findall.isEmpty())
		{
			return false;
		}
		else
		{
			UserMaster usermaster1=findall.get(0);
			usermaster1.setPassword(activateacc.getNewPwd());
			usermaster1.setAccStatus("ACTIVE");
			userMasterRepo.save(usermaster1);
			return true;
			
			
		}
	
	}

	@Override
	public List<User> getAllUsers() {
		
		List<UserMaster> findAll=userMasterRepo.findAll();
		List<User> users=new ArrayList<>();
		for(UserMaster entity:findAll)
		{
			User user=new User();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		Optional<UserMaster> findById=userMasterRepo.findById(id);
		if(findById.isPresent())
		{
			User user=new User();
		BeanUtils.copyProperties(findById, user);
			return user;
		}
		
		else
		
			return null;
		}
	

	@Override
	public boolean deleteUserById(Integer userId) {
try
        {
		
		userMasterRepo.deleteById(userId);
		return true;
        }catch(Exception e)
{
        	e.printStackTrace();
}
		return false;
	}

	@Override
	public boolean changeAccountStatus(Integer userId, String status) {
		Optional<UserMaster> findById=userMasterRepo.findById(userId);
		if(findById.isPresent())
		{
			 UserMaster usermaster = findById.get();
		usermaster.setAccStatus(status);
		return true;
	}
		return false;
	}

	@Override
	public String login(Login login) {
		UserMaster usermaster=new UserMaster();
		usermaster.setEmailId(login.getEmailId());
		Example<UserMaster> of=Example.of(usermaster);
		List<UserMaster> findAll=userMasterRepo.findAll(of);
		if(findAll.isEmpty())
		{
			return "Ïnvalid credentials";
		}
		else
		{
			if(usermaster.getAccStatus().equals("ACTIVE"))
			{
				return "SUCESS";
			}
			else
			{
				return "account not activate";
			}
		
	}
	}

	@Override
	public String forgotPwd(String Email) 
	{
		UserMaster user=userMasterRepo.findByemailId(Email);
		if(user==null)
		{
			return "invalid email";
		}
		String subject="FORGOT PASSWPFD";
		String filename="RECOVER-EMAIL-BODY.txt";
		String body=readEmailBody(user.getFullName(),user.getPassword(),filename);
		boolean sendEmail=emailutils.sendMail(Email,subject,body);
		if(sendEmail)
		{
			return "password sent to yOur mail";
		}
		
	
		
		return null;
		}

private String generateRandomPwd()
{
	String upperAlphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String lowerAlphabet="abcdefghijklmnopqrstuvwxyz";
	String numbers="0123456789";
	String alphaNumeric=upperAlphabet+lowerAlphabet+numbers;
	Random random=new Random();
	StringBuilder sb=new StringBuilder();
	//int length=10;
	int index=random.nextInt(alphaNumeric.length());
	char c=alphaNumeric.charAt(index);
	sb.append(c);
	String alphanumeric=sb.toString();
	System.out.println("random string"+alphanumeric);
	return alphanumeric;
	
}
private String readEmailBody(String fullname,String tempPwd,String filename)
{
	//String filename="REG-EMAIL-BODY.txt";
	String url="";
	String mailBody=null;
	try
	{
		FileReader fr=new FileReader(filename);
		BufferedReader br=new BufferedReader(fr);
		StringBuffer buffer=new StringBuffer();
		String line=br.readLine();
		while(line!=null)
		{
			buffer.append(line);
			line=br.readLine();
		}
		br.close();
		mailBody=buffer.toString();
		mailBody.replace("{FULLNAME}", fullname);
		mailBody.replace("{TEMP-PWD}", tempPwd);
		mailBody.replace("{URL}", url);
		mailBody.replace("{PWD}", tempPwd);
		
		
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	
return mailBody;
}
}
