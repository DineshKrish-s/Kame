package dataProviders;

import annotations.TestDataSource;
import org.testng.annotations.DataProvider;
import utils.CustomLogger;
import utils.FileIO;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            List<TestData> filteredDataList = filterRowsforSingleSheet(dataList, rows, rowRange);

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
     * DataProvider for one Excel file with multiple sheets, with optional rows and range.
     */
    @DataProvider(name = "multiSheetDynamicDataProvider")
    public static Object[][] multiSheetDynamicDataProvider(Method method) throws IOException {
        boolean isParallel = Boolean.parseBoolean(System.getProperty(parallelIndicator, defaultParallelIndicator));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String[] sheetNames = dataSource.sheetName();
            String filePath = excelLocation + dataSource.fileName()[0];

            if (sheetNames.length == 0) {
                throw new IllegalArgumentException("No sheet names provided in the @TestDataSource annotation.");
            }

            // Read and filter data from all specified sheets
            List<List<TestData>> allSheetData = new ArrayList<>();
            for (String sheetName : sheetNames) {
                List<TestData> sheetData = FileIO.readExcelAsDynamicObject(filePath, sheetName);

                // Filter rows based on rowRange or rows
                int[] rows = dataSource.rows(); // Specific rows
                String rowRange = dataSource.rowRange(); // Row range
                List<TestData> filteredData = filterRows(sheetData, rows, rowRange);

                allSheetData.add(filteredData);
            }

            // Combine rows from all sheets into a single list
            List<TestData> combinedData = mergeRowsAcrossSheets(allSheetData);

            // Convert combined data to Object[][] for TestNG
            Object[][] data = combinedData.stream()
                    .map(testData -> new Object[]{testData})
                    .toArray(Object[][]::new);

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                logger.infoWithoutReport("Parallel execution is disabled for multiSheetDynamicDataProvider.");
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

            if (fileNames.length == 0 || sheetNames.length == 0) {
                throw new IllegalArgumentException("File names and sheet names must be provided in the @TestDataSource annotation.");
            }

            // Ensure fileNames and sheetNames are valid
            if (sheetNames.length != fileNames.length) {
                throw new IllegalArgumentException("The number of file names and sheet names must match.");
            }

            // Read data from all specified files and sheets
            List<List<TestData>> allFileData = new ArrayList<>();
            for (int i = 0; i < fileNames.length; i++) {
                String filePath = excelLocation + fileNames[i];
                String sheetName = sheetNames[i];

                // Read data from the specified file and sheet
                List<TestData> sheetData = FileIO.readExcelAsDynamicObject(filePath, sheetName);

                // Filter rows based on the provided rows or rowRange
                List<TestData> filteredData = filterRows(sheetData, rows, rowRange);

                allFileData.add(filteredData);
            }

            // Combine rows across all files and sheets
            List<TestData> combinedData = mergeRowsAcrossSheets(allFileData);

            // Convert combined data to Object[][] for TestNG
            Object[][] data = combinedData.stream()
                    .map(testData -> new Object[]{testData})
                    .toArray(Object[][]::new);

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                logger.infoWithoutReport("Parallel execution is disabled for multiExcelDataProvider.");
                return makeSequential(data);
            }

            return data;
        } else {
            throw new RuntimeException(defaultErrorMessage);
        }
    }

    /**
     * Filters rows based on specific indices or a range.
     */
    private static List<TestData> filterRowsforSingleSheet(List<TestData> dataList, int[] rows, String rowRange) {
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

    private static List<TestData> filterRows(List<TestData> sheetData, int[] rows, String rowRange) {
        List<TestData> filteredData = new ArrayList<>();

        if (rows != null && rows.length > 0) {
            // Filter by specific row indices
            for (int row : rows) {
                if (row >= 0 && row < sheetData.size()) {
                    filteredData.add(sheetData.get(row));
                }
            }
        } else if (rowRange != null && !rowRange.isEmpty()) {
            // Filter by row range (e.g., "0-2")
            String[] range = rowRange.split("-");
            if (range.length == 2) {
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);

                for (int i = start; i <= end && i < sheetData.size(); i++) {
                    filteredData.add(sheetData.get(i));
                }
            }
        } else {
            // No filtering, return all rows
            filteredData.addAll(sheetData);
        }

        return filteredData;
    }

    private static List<TestData> mergeRowsAcrossSheets(List<List<TestData>> allSheetData) {
        List<TestData> mergedData = new ArrayList<>();

        // Assume all sheets have the same number of rows
        int rowCount = allSheetData.get(0).size();

        for (int i = 0; i < rowCount; i++) {
            TestData mergedRow = new TestData();
            for (List<TestData> sheetData : allSheetData) {
                TestData rowData = sheetData.get(i);
                mergedRow.getAllData().putAll(rowData.getAllData());
            }
            mergedData.add(mergedRow);
        }

        return mergedData;
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
