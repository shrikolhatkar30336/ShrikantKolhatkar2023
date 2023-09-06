package study.example.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import study.example.entity.ExcelData;
import study.example.repository.ExcelDataRepository;

@Service
public class ExcelDataService {

    @Autowired
    private ExcelDataRepository excelDataRepository;

    //Method go read data from excel file and store it to database
    public void storeDataFromExcel(MultipartFile file) {
        // Read data from the Excel file and store it into the database
        List<ExcelData> excelDataList = readDataFromExcel(file);
        excelDataRepository.saveAll(excelDataList);
    }

    private List<ExcelData> readDataFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
        	  Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet
            List<ExcelData> excelDataList = new ArrayList<>();

            for (Row row : sheet) {
                ExcelData excelData = new ExcelData();
                excelData.setId(getCellValueAsInt(row.getCell(0))); 
                excelData.setName(getCellValueAsString(row.getCell(1))); 
                excelData.setAge(getCellValueAsInt(row.getCell(2))); 
                excelData.setCity(getCellValueAsString(row.getCell(4))); 
                excelData.setEmailId(getCellValueAsString(row.getCell(3))); 

                excelDataList.add(excelData);
            }

            workbook.close();
            return excelDataList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from the Excel file.", e);
        }
    }

    //methods to handle cell values
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    private int getCellValueAsInt(Cell cell) {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return 0;
    }
    
    //Method to write data from database to excel file.
    public Resource writeDataToExcel() {
        List<ExcelData> excelDataList = excelDataRepository.findAll();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Workbook workbook = new XSSFWorkbook(); // Use XSSFWorkbook for XLSX format
            Sheet sheet = workbook.createSheet("Sheet1"); // Create a new sheet

            // Create the header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("name");
            headerRow.createCell(2).setCellValue("age");
            headerRow.createCell(3).setCellValue("city");
            headerRow.createCell(4).setCellValue("email_id");

            // Create data rows
            int rowNum = 1;
            for (ExcelData excelData : excelDataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(excelData.getId());
                row.createCell(1).setCellValue(excelData.getName());
                row.createCell(2).setCellValue(excelData.getAge());
                row.createCell(3).setCellValue(excelData.getCity());
                row.createCell(4).setCellValue(excelData.getEmailId());
            }

            workbook.write(outputStream);
            workbook.close();

            byte[] excelBytes = outputStream.toByteArray();
            return new ByteArrayResource(excelBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to Excel file.", e);
        }
    }
}