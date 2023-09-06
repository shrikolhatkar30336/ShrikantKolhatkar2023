package study.example.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import study.example.entity.ExcelFile;
import study.example.entity.ExcelFileInfo;
import study.example.repository.FileRepository;

@Service
public class FileService {

	@Autowired
    private FileRepository fileRepository;

	//Method to upload excel file to database.
    public ExcelFile uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            byte[] fileData = file.getBytes();
            
            LocalDateTime uploadDateTime = LocalDateTime.now();

            ExcelFile uploadedFile = new ExcelFile();
            uploadedFile.setFileName(fileName);
            uploadedFile.setData(fileData);
            uploadedFile.setUploadDateTime(uploadDateTime);

            // Save the file data to the database
            uploadedFile = fileRepository.save(uploadedFile);

            return uploadedFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload the file.", e);
        }
    }
    
    //Method to download excel file from database
    public ByteArrayResource downloadFile(Long id) {
        // Retrieve the file data from the database using the ID
        ExcelFile uploadedFile = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));

        return new ByteArrayResource(uploadedFile.getData());
    }
    
    //Method to get uploaded file names and date & time when they are uploaded
    public List<ExcelFileInfo> getAllUploadedFiles() {
        List<ExcelFile> uploadedFiles = fileRepository.findAll();
        return convertToUploadedFileInfoList(uploadedFiles);
    }

    private List<ExcelFileInfo> convertToUploadedFileInfoList(List<ExcelFile> uploadedFiles) {
    	 List<ExcelFileInfo> fileInfoList = new ArrayList<>();

         for (ExcelFile uploadedFile : uploadedFiles) {
             ExcelFileInfo fileInfo = new ExcelFileInfo();
             fileInfo.setFileName(uploadedFile.getFileName());
             fileInfo.setUploadDateTime(uploadedFile.getUploadDateTime());

             fileInfoList.add(fileInfo);
         }

         return fileInfoList;
    }
    
}
