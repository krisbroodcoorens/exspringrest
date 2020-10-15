package be.abis.exercise.model;

public class Login 
{
	public String emailAddress;
	public String passWord;
	
	public Login(String emailAddress, String passWord) {
		super();
		this.emailAddress = emailAddress;
		this.passWord = passWord;
	}
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}	
}
