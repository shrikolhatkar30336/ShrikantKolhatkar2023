package study.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import study.example.service.ExcelDataService;

//4.Create api to read Excel and store it into database (extracted data in tabular format) & api to write that into Excel again from database.

@RestController
@RequestMapping("/exceldata")
public class ExcelDataController {

    @Autowired
    private ExcelDataService excelDataService;

    //API to read Excel and store it into database
    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        excelDataService.storeDataFromExcel(file);
        return ResponseEntity.ok("Data successfully stored from the Excel file.");
    }
    
    //APT to write that into Excel from database
    @GetMapping("/get/excel")
    public ResponseEntity<Resource> WriteExcelFile() {
        Resource resource = excelDataService.writeDataToExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"output.xlsx\"")
                .body(resource);
    }
}