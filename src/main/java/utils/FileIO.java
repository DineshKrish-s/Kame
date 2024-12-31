package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import dataProviders.TestData;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

public class FileIO {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    public static void writeToFile(String filePath, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        }
    }

    /**
     * Opens and returns the Workbook object for the specified Excel file.
     *
     * @param filePath The path to the Excel file.
     * @return The Workbook object.
     * @throws IOException If the file cannot be read.
     */
    public static Workbook getWorkbook(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        return WorkbookFactory.create(fis);
    }

    /**
     * Returns the specified Sheet object from the Workbook by name.
     *
     * @param workbook  The Workbook object.
     * @param sheetName The name of the sheet to retrieve.
     * @return The Sheet object.
     * @throws IllegalArgumentException If the sheet is not found.
     */
    public static Sheet getSheetByName(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in the workbook.");
        }
        return sheet;
    }

    /**
     * Returns the Sheet object from the Workbook by index.
     *
     * @param workbook The Workbook object.
     * @param index    The index of the sheet (0-based).
     * @return The Sheet object.
     * @throws IllegalArgumentException If the index is invalid.
     */
    public static Sheet getSheetByIndex(Workbook workbook, int index) {
        Sheet sheet = workbook.getSheetAt(index);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet at index '" + index + "' not found in the workbook.");
        }
        return sheet;
    }

    /**
     * Finds the index of a column by its name in the header row.
     *
     * @param sheet      The sheet to search within.
     * @param columnName The name of the column to find.
     * @return The index of the column, or -1 if not found.
     */
    public static int findColumnIndex(Sheet sheet, String columnName) {
        Row headerRow = sheet.getRow(0); // Assuming the first row is the header row
        if (headerRow == null) return -1;

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retrieves all values from a specified column by its name.
     *
     * @param sheet      The Sheet object to retrieve values from.
     * @param columnName The name of the column to retrieve values from.
     * @return A list of values from the specified column.
     */
    public static List<String> getColumnValuesByName(Sheet sheet, String columnName) {
        List<String> columnValues = new ArrayList<>();
        int columnIndex = findColumnIndex(sheet, columnName);

        if (columnIndex == -1) {
            throw new IllegalArgumentException("Column '" + columnName + "' not found in the sheet.");
        }

        // Retrieve all values from the column
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip the header row
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                columnValues.add(getCellValueAsString(cell));
            }
        }

        return columnValues;
    }

    /**
     * Converts a cell's value to a string.
     *
     * @param cell The cell to convert.
     * @return The cell's value as a string.
     */
    public static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    /**
     * Reads data from an Excel sheet and returns it as a list of maps.
     * Each map represents a row, with column headers as keys.
     *
     * @param filePath The path to the Excel file.
     * @return A list of maps containing the Excel sheet data.
     * @throws IOException If the file cannot be read.
     */
    public static List<Map<String, String>> readExcel(String filePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();

        // Load the Workbook and Sheet
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // Default: Read the first sheet

            // Read header row to get column names
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("The Excel sheet is empty.");
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim());
            }

            // Read each row and map column values to headers
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = getCellValueAsString(cell);
                    rowData.put(headers.get(j), cellValue);
                }
                data.add(rowData);
            }

            workbook.close();
        }

        return data;
    }

    public static List<TestData> readExcelAsDynamicObject(String filePath, String sheetName) throws IOException {
        List<TestData> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next(); // Header row

            // Extract headers
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            // Process each row
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                TestData rowData = new TestData();

                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i);
                    rowData.put(headers.get(i), getCellValueAsString(cell));
                }

                dataList.add(rowData);
            }
        }

        return dataList;
    }

    public static Map<String, List<TestData>> readSpecifiedSheetsAsDynamicObjects(String filePath, String[] sheetNames) throws IOException {
        Map<String, List<TestData>> sheetDataMap = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fis);

            // Iterate over the provided sheet names
            for (String sheetName : sheetNames) {
                Sheet sheet = workbook.getSheet(sheetName);

                if (sheet == null) {
                    throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in the workbook.");
                }

                List<TestData> dataList = new ArrayList<>();
                Iterator<Row> rowIterator = sheet.iterator();

                if (!rowIterator.hasNext()) continue; // Skip empty sheets

                Row headerRow = rowIterator.next(); // Header row

                // Extract headers
                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }

                // Process each row
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    TestData rowData = new TestData();

                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = row.getCell(j);
                        rowData.put(headers.get(j), getCellValueAsString(cell));
                    }

                    dataList.add(rowData);
                }

                // Add the sheet's data to the map
                sheetDataMap.put(sheetName, dataList);
            }
        }

        return sheetDataMap;
    }

    /**
     * Reads a JSON file and maps it to a Java object of the specified type.
     *
     * @param filePath The path to the JSON file.
     * @param valueType The class of the object to map the JSON to.
     * @param <T> The type of the object.
     * @return The Java object mapped from the JSON file.
     * @throws IOException If the file cannot be read or parsed.
     */
    public static <T> T readJsonAsObject(String filePath, Class<T> valueType) throws IOException {
        return objectMapper.readValue(new File(filePath), valueType);
    }

    /**
     * Reads a JSON file and returns its content as a Map.
     *
     * @param filePath The path to the JSON file.
     * @return A Map representing the JSON content.
     * @throws IOException If the file cannot be read or parsed.
     */
    public static Map<String, Object> readJsonAsMap(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), Map.class);
    }

    /**
     * Reads a JSON file and returns its content as a List.
     *
     * @param filePath The path to the JSON file.
     * @return A List representing the JSON content.
     * @throws IOException If the file cannot be read or parsed.
     */
    public static List<Object> readJsonAsList(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), List.class);
    }

    /**
     * Loads a properties file and returns the Properties object.
     *
     * @param filePath The path to the properties file.
     * @return The Properties object containing key-value pairs from the file.
     * @throws IOException If the file cannot be read.
     */
    public static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
        return properties;
    }

    /**
     * Reads a property value by key from a properties file.
     *
     * @param filePath The path to the properties file.
     * @param key      The key whose value needs to be retrieved.
     * @return The value associated with the key.
     * @throws IOException If the file cannot be read.
     */
    public static String getPropertyValue(String filePath, String key) throws IOException {
        Properties properties = loadProperties(filePath);
        return properties.getProperty(key);
    }

    /**
     * Reads a property value by key from a loaded Properties object.
     *
     * @param properties The Properties object.
     * @param key        The key whose value needs to be retrieved.
     * @return The value associated with the key.
     */
    public static String getPropertyValue(Properties properties, String key) {
        return properties.getProperty(key);
    }

    /**
     * Reads the entire CSV file and returns the data as a List of String arrays.
     *
     * @param filePath The path to the CSV file.
     * @return A List of String arrays, where each array represents a row.
     * @throws IOException If the file cannot be read.
     * @throws CsvException If there is an error while parsing the CSV file.
     */
    public static List<String[]> readCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }

    /**
     * Reads the header row of the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return A String array representing the header row.
     * @throws IOException If the file cannot be read.
     * @throws CsvException If there is an error while parsing the CSV file.
     */
    public static String[] readCsvHeader(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readNext(); // Reads the first row (header)
        }
    }

    /**
     * Reads a specific column from the CSV file by its index.
     *
     * @param filePath The path to the CSV file.
     * @param columnIndex The index of the column to read (0-based).
     * @return A List of values from the specified column.
     * @throws IOException If the file cannot be read.
     * @throws CsvException If there is an error while parsing the CSV file.
     */
    public static List<String> readCsvColumn(String filePath, int columnIndex) throws IOException, CsvException {
        List<String> columnData = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            for (int i = 1; i < rows.size(); i++) { // Skip the header row
                String[] row = rows.get(i);
                if (row.length > columnIndex) {
                    columnData.add(row[columnIndex]);
                }
            }
        }
        return columnData;
    }

}
