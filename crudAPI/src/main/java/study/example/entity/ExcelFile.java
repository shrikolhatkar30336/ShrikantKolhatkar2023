package study.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="file_info")
public class ExcelFile {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name="file_name")
    private String fileName;
	
	@Lob
	@Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

	 @Column(name = "upload_date_time")
	 private LocalDateTime uploadDateTime;
	 
    public ExcelFile() {
		
	}
    public ExcelFile(Long id, String fileName) {
		super();
		this.id = id;
		this.fileName = fileName;
	}

	public ExcelFile(String fileName, LocalDateTime uploadDateTime) {
		super();
		this.fileName = fileName;
		this.uploadDateTime = uploadDateTime;
	}
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public LocalDateTime getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(LocalDateTime uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
