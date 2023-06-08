package sec01.ex01;

import java.sql.Date;

public class MemberVO {

	//필(필드 - field) - 객체의 데이터가 저장되는 곳
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joinDate;
	
	//생(생성자 - constructor) -객체 생성시 객체의 값을 초기화 역할을 담당.
			// 생성자 만드는 법 - 클래스명(){}, 일종의 메서드
			// 생성자는 return하지 않음.
	
	//기본 생성자.
	public MemberVO() {
		System.out.println("Member 생성자 호출됌");
	}
	//메(메서드) - 객체의 동작을 실행하는 블록 {}를 의미

	
	//getter(다른 클래스에서 id값을 가져가게 하는 역할)
	public String getId(){ //낙타기법.
		return id;
	}

	//Setter(다른 클래스에서 id 값을 설정하는 역할.)
	public void setId(String id){
		this.id = id;
	}
	
	//getter
	public String getPwd(){
		return pwd;
	}
	//Setter
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	
	//getter
	public String getName(){
		return name;
	}
	//Setter
	public void setName(String name){
		this.name = name;
	}
	
	//getter
	public String getEmail(){
		return email;
	}
	//Setter
	public void setEmail(String email){
		this.email = email;
	}
	
	//getter
	public Date getJoinDate(){
		return joinDate;
	}
	//Setter
	public void setJoinDate(Date joinDate){
		this.joinDate = joinDate;
	}
	
	
}
