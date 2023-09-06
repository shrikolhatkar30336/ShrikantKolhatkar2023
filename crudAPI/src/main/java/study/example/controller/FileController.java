package study.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import study.example.entity.ExcelFile;
import study.example.entity.ExcelFileInfo;
import study.example.service.FileService;

//2.Create api for uploading Excel file to data base and api to download same file from database. 
//3.Get api for uploaded file names and date & time when they are uploaded

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileUploadService;
    
    //API for uploading Excel file to database
    @PostMapping("/upload")
    public ResponseEntity<ExcelFile> uploadFile(@RequestParam("file") MultipartFile file) {
        ExcelFile uploadedFile = fileUploadService.uploadFile(file);
        return ResponseEntity.ok(uploadedFile);
    }
    
    //API to download excel file from database
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        ByteArrayResource resource = fileUploadService.downloadFile(id);
        byte[] fileData = resource.getByteArray();
        String fileName = resource.getFilename();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")); // Set the content type to Excel (.xlsx)

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }
    
    //API to get uploaded file names and date & time when they are uploaded
    @GetMapping("/uploadedfiles")
    public ResponseEntity<List<ExcelFileInfo>> getAllUploadedFiles() {
        List<ExcelFileInfo> uploadedFiles = fileUploadService.getAllUploadedFiles();
        return ResponseEntity.ok(uploadedFiles);
    }
}
