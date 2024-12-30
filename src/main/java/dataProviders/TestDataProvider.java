package dataProviders;

import annotations.TestDataSource;
import org.testng.annotations.DataProvider;
import utils.CustomLogger;
import utils.FileIO;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {

    static CustomLogger logger = new CustomLogger(TestDataProvider.class);

    static final String excelLocation = "src/test/resources/testdata/excel/";
    static final String parallelIndicator = "enableParallelDataProvider";
    static final String defaultParallelIndicator = "false";
    static final String defaultErrorMessage = "Test method is missing @TestDataSource annotation";

    TestDataProvider() {}

    /**
     * DataProvider for single Excel file and sheet with optional rows and range.
     */
    @DataProvider(name = "singleExcelDataProvider")
    public static Object[][] singleExcelDataProvider(Method method) throws IOException {
        boolean isParallel = Boolean.parseBoolean(System.getProperty(parallelIndicator, defaultParallelIndicator));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String[] fileNames = dataSource.fileName();
            String[] sheetNames = dataSource.sheetName();
            int[] rows = dataSource.rows(); // Optional rows
            String rowRange = dataSource.rowRange(); // Optional range

            if (fileNames.length != 1 || sheetNames.length != 1) {
                throw new IllegalArgumentException("This DataProvider supports only one file and one sheet.");
            }

            String filePath = excelLocation + fileNames[0];
            String sheetName = sheetNames[0];

            // Read data from the single file and sheet
            List<TestData> dataList = FileIO.readExcelAsDynamicObject(filePath, sheetName);

            // Filter rows and ranges
            List<TestData> filteredDataList = filterRows(dataList, rows, rowRange);

            // Convert List<TestData> to Object[][]
            Object[][] data = filteredDataList.stream()
                    .map(dataItem -> new Object[]{dataItem})
                    .toArray(Object[][]::new);

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                logger.infoWithoutReport("Parallel execution is disabled for singleExcelDataProvider.");
                return makeSequential(data);
            }

            return data;
        } else {
            throw new RuntimeException(defaultErrorMessage);
        }
    }

    /**
     * DataProvider for multiple Excel files and sheets with optional rows and range.
     */
    @DataProvider(name = "multiExcelDataProvider")
    public static Object[][] multiExcelDataProvider(Method method) throws IOException {
        boolean isParallel = Boolean.parseBoolean(System.getProperty(parallelIndicator, defaultParallelIndicator));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String[] fileNames = dataSource.fileName();
            String[] sheetNames = dataSource.sheetName();
            int[] rows = dataSource.rows(); // Optional rows
            String rowRange = dataSource.rowRange(); // Optional range

            if (fileNames.length != sheetNames.length) {
                throw new IllegalArgumentException("Number of fileNames and sheetNames must match.");
            }

            // Read data from all files
            List<List<TestData>> allFileData = new ArrayList<>();
            for (int i = 0; i < fileNames.length; i++) {
                String filePath = excelLocation + fileNames[i];
                String sheetName = sheetNames[i];
                List<TestData> fileData = FileIO.readExcelAsDynamicObject(filePath, sheetName);
                allFileData.add(fileData);
            }

            // Combine and filter rows based on range or rows parameter
            List<Object[]> combinedDataList = combineAndFilterRows(allFileData, rows, rowRange);

            // Convert List<Object[]> to Object[][]
            Object[][] combinedData = combinedDataList.toArray(new Object[0][]);

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                logger.infoWithoutReport("Parallel execution is disabled for multiExcelDataProvider.");
                return makeSequential(combinedData);
            }

            return combinedData;
        } else {
            throw new RuntimeException(defaultErrorMessage);
        }
    }

    /**
     * DataProvider for one Excel file with multiple sheets, with optional rows and range.
     */
    @DataProvider(name = "oneExcelMultiSheetDataProvider")
    public static Object[][] oneExcelMultiSheetDataProvider(Method method) throws IOException {
        boolean isParallel = Boolean.parseBoolean(System.getProperty(parallelIndicator, defaultParallelIndicator));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String[] sheetNames = dataSource.sheetName();
            String filePath = excelLocation + dataSource.fileName()[0];
            int[] rows = dataSource.rows(); // Optional rows
            String rowRange = dataSource.rowRange(); // Optional range

            // Read data from all sheets
            List<List<TestData>> allSheetData = new ArrayList<>();
            for (String sheetName : sheetNames) {
                List<TestData> sheetData = FileIO.readExcelAsDynamicObject(filePath, sheetName);
                allSheetData.add(sheetData);
            }

            // Combine and filter rows based on range or rows parameter
            List<Object[]> combinedDataList = combineAndFilterRows(allSheetData, rows, rowRange);

            // Convert List<Object[]> to Object[][]
            Object[][] combinedData = combinedDataList.toArray(new Object[0][]);

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                logger.infoWithoutReport("Parallel execution is disabled for oneExcelMultiSheetDataProvider.");
                return makeSequential(combinedData);
            }

            return combinedData;
        } else {
            throw new RuntimeException(defaultErrorMessage);
        }
    }

    /**
     * Filters rows based on specific indices or a range.
     */
    private static List<TestData> filterRows(List<TestData> dataList, int[] rows, String rowRange) {
        if (rows != null && rows.length > 0) {
            List<TestData> filteredList = new ArrayList<>();
            for (int rowIndex : rows) {
                if (rowIndex >= 0 && rowIndex < dataList.size()) {
                    filteredList.add(dataList.get(rowIndex));
                } else {
                    throw new IndexOutOfBoundsException("Row " + rowIndex + " is out of bounds for the data source.");
                }
            }
            return filteredList;
        }

        if (!rowRange.isEmpty()) {
            String[] rangeParts = rowRange.split("-");
            if (rangeParts.length == 2) {
                int start = Integer.parseInt(rangeParts[0]);
                int end = Integer.parseInt(rangeParts[1]);
                if (start < 0 || end >= dataList.size() || start > end) {
                    throw new IllegalArgumentException("Invalid row range: " + rowRange);
                }
                return dataList.subList(start, end + 1);
            } else {
                throw new IllegalArgumentException("Invalid row range format. Expected 'start-end', got: " + rowRange);
            }
        }

        return dataList; // Default to all rows
    }

    /**
     * Combines and filters rows for multi-file or multi-sheet data.
     */
    private static List<Object[]> combineAndFilterRows(List<List<TestData>> allData, int[] rows, String rowRange) {
        List<Object[]> combinedDataList = new ArrayList<>();

        for (int i = 0; i < allData.get(0).size(); i++) {
            List<Object> rowData = new ArrayList<>();
            for (List<TestData> sheetOrFileData : allData) {
                rowData.add(sheetOrFileData.get(i));
            }
            combinedDataList.add(rowData.toArray());
        }

        // Filter combined data rows based on rows or range
        if (rows != null && rows.length > 0) {
            List<Object[]> filteredList = new ArrayList<>();
            for (int rowIndex : rows) {
                if (rowIndex >= 0 && rowIndex < combinedDataList.size()) {
                    filteredList.add(combinedDataList.get(rowIndex));
                } else {
                    throw new IndexOutOfBoundsException("Row " + rowIndex + " is out of bounds for the data source.");
                }
            }
            return filteredList;
        }

        if (!rowRange.isEmpty()) {
            String[] rangeParts = rowRange.split("-");
            if (rangeParts.length == 2) {
                int start = Integer.parseInt(rangeParts[0]);
                int end = Integer.parseInt(rangeParts[1]);
                if (start < 0 || end >= combinedDataList.size() || start > end) {
                    throw new IllegalArgumentException("Invalid row range: " + rowRange);
                }
                return combinedDataList.subList(start, end + 1);
            } else {
                throw new IllegalArgumentException("Invalid row range format. Expected 'start-end', got: " + rowRange);
            }
        }

        return combinedDataList; // Default to all rows
    }

    /**
     * Converts parallel data to sequential data.
     */
    private static Object[][] makeSequential(Object[][] data) {
        return data; // No-op in this example
    }
}
