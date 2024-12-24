package dataProviders;

import annotations.TestDataSource;
import org.testng.annotations.DataProvider;
import utils.FileIO;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class TestDataProvider {

    @DataProvider(name = "excelDataProvider")
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
