package in.ashokit.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	
private String fullName;
private String email;
private Long Mobile;
private Long Ssn;
private LocalDate Dob;
private String Gender;

}
