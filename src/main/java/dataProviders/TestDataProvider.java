package dataProviders;

import annotations.TestDataSource;
import org.testng.annotations.DataProvider;
import utils.FileIO;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {
    /**
     * DataProvider for single Excel file and sheet.
     */
    @DataProvider(name = "singleExcelDataProvider")
    public static Object[][] singleExcelDataProvider(Method method) throws IOException {
        boolean isParallel = Boolean.parseBoolean(System.getProperty("enableParallelDataProvider", "false"));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String[] fileNames = dataSource.fileName();
            String[] sheetNames = dataSource.sheetName();

            if (fileNames.length != 1 || sheetNames.length != 1) {
                throw new IllegalArgumentException("This DataProvider supports only one file and one sheet.");
            }

            String filePath = "src/test/resources/testdata/excel/" + fileNames[0];
            String sheetName = sheetNames[0];

            // Read data from the single file and sheet
            List<TestData> dataList = FileIO.readExcelAsDynamicObject(filePath, sheetName);

            // Convert List<TestData> to Object[][]
            Object[][] data = dataList.stream()
                    .map(dataItem -> new Object[]{dataItem})
                    .toArray(Object[][]::new);

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                System.out.println("Parallel execution is disabled for singleExcelDataProvider.");
                return makeSequential(data);
            }

            return data;
        } else {
            throw new RuntimeException("Test method is missing @TestDataSource annotation");
        }
    }

    /**
     * DataProvider for multiple Excel files and sheets.
     */
    @DataProvider(name = "multiExcelDataProvider")
    public static Object[][] multiExcelDataProvider(Method method) throws IOException {
        boolean isParallel = Boolean.parseBoolean(System.getProperty("enableParallelDataProvider", "false"));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String[] fileNames = dataSource.fileName();
            String[] sheetNames = dataSource.sheetName();

            if (fileNames.length != sheetNames.length) {
                throw new IllegalArgumentException("Number of fileNames and sheetNames must match.");
            }

            // Read data from all files
            List<List<TestData>> allFileData = new ArrayList<>();
            for (int i = 0; i < fileNames.length; i++) {
                String filePath = "src/test/resources/testdata/excel/" + fileNames[i];
                String sheetName = sheetNames[i];
                List<TestData> fileData = FileIO.readExcelAsDynamicObject(filePath, sheetName);
                allFileData.add(fileData);
            }

            // Ensure all files have the same number of rows
            int rowCount = allFileData.get(0).size();
            for (List<TestData> dataList : allFileData) {
                if (dataList.size() != rowCount) {
                    throw new IllegalArgumentException("Excel files have different row counts.");
                }
            }

            // Combine corresponding rows from all files into a single Object[][]
            Object[][] combinedData = new Object[rowCount][fileNames.length];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < fileNames.length; j++) {
                    combinedData[i][j] = allFileData.get(j).get(i); // Row i from File j
                }
            }

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                System.out.println("Parallel execution is disabled for multiExcelDataProvider.");
                return makeSequential(combinedData);
            }

            return combinedData;
        } else {
            throw new RuntimeException("Test method is missing @TestDataSource annotation");
        }
    }

    @DataProvider(name = "oneExcelMultiSheetDataProvider")
    public static Object[][] oneExcelMultiSheetDataProvider(Method method) throws IOException {
        boolean isParallel = Boolean.parseBoolean(System.getProperty("enableParallelDataProvider", "false"));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String[] sheetNames = dataSource.sheetName();
            String filePath = "src/test/resources/testdata/excel/" + dataSource.fileName()[0];

            // Read data from all sheets
            List<List<TestData>> allSheetData = new ArrayList<>();
            for (String sheetName : sheetNames) {
                List<TestData> sheetData = FileIO.readExcelAsDynamicObject(filePath, sheetName);
                allSheetData.add(sheetData);
            }

            // Ensure all sheets have the same number of rows
            int rowCount = allSheetData.get(0).size();
            for (List<TestData> dataList : allSheetData) {
                if (dataList.size() != rowCount) {
                    throw new IllegalArgumentException("Sheets in the Excel file have different row counts.");
                }
            }

            // Combine corresponding rows from all sheets into a single Object[][]
            Object[][] combinedData = new Object[rowCount][sheetNames.length];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < sheetNames.length; j++) {
                    combinedData[i][j] = allSheetData.get(j).get(i); // Row i from Sheet j
                }
            }

            // If parallel execution is disabled, return sequential data
            if (!isParallel) {
                System.out.println("Parallel execution is disabled for oneExcelMultiSheetDataProvider.");
                return makeSequential(combinedData);
            }

            return combinedData;
        } else {
            throw new RuntimeException("Test method is missing @TestDataSource annotation");
        }
    }

    /**
     * Converts parallel data to sequential data.
     */
    private static Object[][] makeSequential(Object[][] data) {
        // Sequential handling logic, if any special adjustment is needed
        return data;
    }
}