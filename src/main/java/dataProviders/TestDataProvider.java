package dataProviders;

import annotations.TestDataSource;
import org.testng.annotations.DataProvider;
import utils.FileIO;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"user1", "password1"},
                {"user2", "password2"},
        };
    }

    @DataProvider(name = "excelDataProvider")
//    public static Object[][] excelDataProvider(Method method) throws IOException {
//        // Fetch the TestDataSource annotation for the method
//        if (method.isAnnotationPresent(TestDataSource.class)) {
//            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);
//
//            // Get file path and sheet name from the annotation
//            String filePath = "src/test/resources/testdata/excel/" + dataSource.fileName();
//            String sheetName = dataSource.sheetName();
//
//            // Read data from the Excel file
//            return FileIO.readExcel(filePath, sheetName);
//        } else {
//            throw new RuntimeException("Test method is missing @TestDataSource annotation");
//        }
//    }
    public static Object[][] excelDataProvider(Method method) throws IOException {
        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String filePath = "src/test/resources/testdata/excel/" + dataSource.fileName();
            String sheetName = dataSource.sheetName();

            List<TestData> dataList = FileIO.readExcelAsDynamicObject(filePath, sheetName);

            // Convert List<DynamicTestData> to Object[][]
            return dataList.stream().map(data -> new Object[]{data}).toArray(Object[][]::new);
        } else {
            throw new RuntimeException("Test method is missing @TestDataSource annotation");
        }
    }
}
