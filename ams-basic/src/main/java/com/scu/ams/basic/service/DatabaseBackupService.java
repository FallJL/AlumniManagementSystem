package com.scu.ams.basic.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.IOException;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
@Service
public class DatabaseBackupService {
    private static final String DB_HOST = "192.168.1.141";
    private static final String DB_PORT = "13306";
    private static final String DB_NAME = "ams_basic";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    private static final String EXPORT_FILE_PATH = "DataBackup";
    public void performBackup() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME,
                DB_USERNAME, DB_PASSWORD
        )) {
            String[] tables = {"alumnus_basic", "audit_detail", "notification"};

            for (String tableName : tables) {
                String exportQuery = "SELECT * FROM " + tableName;

                try (PreparedStatement statement = connection.prepareStatement(exportQuery);
                     ResultSet resultSet = statement.executeQuery()) {

                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet(tableName);

                    // 获取结果集元数据
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // 创建第一行，并写入字段名
                    Row headerRow = sheet.createRow(0);
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Cell cell = headerRow.createCell(i - 1);
                        cell.setCellValue(columnName);
                    }

                    int rowIndex = 1;
                    while (resultSet.next()) {
                        Row dataRow = sheet.createRow(rowIndex);
                        for (int i = 1; i <= columnCount; i++) {
                            String value = resultSet.getString(i);
                            Cell cell = dataRow.createCell(i - 1);
                            cell.setCellValue(value);
                        }
                        rowIndex++;
                    }

                    LocalDateTime currentTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                    String timestamp = currentTime.format(formatter);
                    String fileName = tableName + "_" + timestamp + ".xlsx";
                    String exportFilePath = EXPORT_FILE_PATH + File.separator + fileName;

                    File exportFile = new File(exportFilePath);
                    try (FileOutputStream fos = new FileOutputStream(exportFile)) {
                        workbook.write(fos);
                    }

                    System.out.println("表数据导出成功：" + exportFilePath);
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("表数据导出失败：" + e.getMessage());
        }
    }
}


