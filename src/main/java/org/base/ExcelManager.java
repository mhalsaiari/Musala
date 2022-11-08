package org.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelManager {

    private static final Logger LOGGER = LogManager.getLogger(ExcelManager.class);
    private static final List<Map<String, String>> excelRow;

    static {
        excelRow = getExcelRow();
    }

    public static synchronized List<Map<String, String>> getControllerRowsList() throws IOException {
        return excelRow;
    }

    private static synchronized List<Map<String, String>> getExcelRow() {
        FileInputStream fileInputStream;
        List<Map<String, String>> rowMapList;
        try {

            Path data = Paths.get(System.getProperty("user.dir") + "/src/main/resources/data.xlsx");
            fileInputStream = new FileInputStream(data.toFile());
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            List<String> headerList = new LinkedList<>();
            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
                headerList.add(sheet.getRow(0).getCell(i).getStringCellValue());
            }
            LOGGER.debug("Headers list [{}]", headerList);
            rowMapList = new LinkedList<>();
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowMap = new LinkedHashMap<>();

                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    Cell cell = row.getCell(j);
                    if (StringUtils.isNotBlank(getCellValue(cell))) {
                        rowMap.put(headerList.get(j), getCellValue(cell));
                        LOGGER.debug("Added Key : [{}] | Value [{}] to row map", headerList.get(j), getCellValue(cell));
                    } else {
                        break;
                    }
                }
                rowMapList.add(rowMap);
            }
            fileInputStream.close();
        } catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }

        return rowMapList.stream().filter(map -> map.size() > 0).collect(Collectors.toList());
    }

    public static synchronized List<Map<String, String>> getExcelRowsAsListOfMap(String excelWorkbookName) {
        FileInputStream fileInputStream;
        List<Map<String, String>> rowMapList = new LinkedList<>();
        try {
            fileInputStream = new FileInputStream(excelWorkbookName);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            List<String> headerList = new LinkedList<>();
            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
                headerList.add(sheet.getRow(0).getCell(i).getStringCellValue());
            }
            LOGGER.debug("Headers list [{}]", headerList);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowMap = new LinkedHashMap<>();
                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    Cell cell = row.getCell(j);
                    if (StringUtils.isNotBlank(getCellValue(cell))) { //&& getCellValue(sheet.getRow(i).getCell(1)).equals(testMethodName)) {
                        rowMap.put(headerList.get(j), getCellValue(cell));
                        LOGGER.debug("Added Key : [{}] | Value [{}] to row map", headerList.get(j), getCellValue(cell));
                    } else if (StringUtils.isEmpty(getCellValue(cell))) {// && getCellValue(sheet.getRow(i).getCell(1)).equals(testMethodName)) {
                        rowMap.put(headerList.get(j), "");
                    } else {
                        break;
                    }
                }
                if (rowMap.size() > 0) {
                    rowMapList.add(rowMap);
                }
            }
            fileInputStream.close();
        } catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        return rowMapList;
    }


    protected static Map<String, String> getControllerRowMapByTestMethodName(String testMethodName) {
        return excelRow.stream().filter(map -> map.get(0).equals(testMethodName)).collect(Collectors.toList()).get(0);
    }

    private static String getCellValue(Cell cell) {
        return cell.getCellType().equals(CellType.NUMERIC) ? String.valueOf(cell.getNumericCellValue()) : cell.getStringCellValue();
    }


}
