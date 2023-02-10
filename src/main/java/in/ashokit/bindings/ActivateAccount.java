package in.ashokit.bindings;

import lombok.Data;

@Data
public class ActivateAccount {
	private String emailId;
	private String newPwd;
	private String tempPwd;
	private String confirmPwd;
	

}
