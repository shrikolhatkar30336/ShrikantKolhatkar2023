package study.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="excel_data")
public class ExcelData {
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(name="name")
	String name;
	
	@Column(name="age")
	int age;
	
	@Column(name="city")
	String city;
	
	@Column(name="email_id")
	String email_id;
	
	public ExcelData()
	{
		
	}

	public ExcelData(int id, String name, int age, String city, String emailId) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.city = city;
		this.email_id = emailId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmailId() {
		return email_id;
	}

	public void setEmailId(String emailId) {
		this.email_id = emailId;
	}
	
}
