package study.example.entity;

import java.time.LocalDateTime;

public class ExcelFileInfo {
    private String fileName;
    private LocalDateTime uploadDateTime;
    
   public ExcelFileInfo() {
		
	}

	public ExcelFileInfo(String fileName, LocalDateTime uploadDateTime) {
		super();
		this.fileName = fileName;
		this.uploadDateTime = uploadDateTime;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public LocalDateTime getUploadDateTime() {
		return uploadDateTime;
	}
	public void setUploadDateTime(LocalDateTime uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

}
