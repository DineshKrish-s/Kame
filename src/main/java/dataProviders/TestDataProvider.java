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
        // Check if parallel execution is enabled via system property
        boolean isParallel = Boolean.parseBoolean(System.getProperty("enableParallelDataProvider", "false"));

        if (method.isAnnotationPresent(TestDataSource.class)) {
            TestDataSource dataSource = method.getAnnotation(TestDataSource.class);

            String filePath = "src/test/resources/testdata/excel/" + dataSource.fileName();
            String sheetName = dataSource.sheetName();

            List<TestData> dataList = FileIO.readExcelAsDynamicObject(filePath, sheetName);

            // Convert List<TestData> to Object[][]
            return dataList.stream()
                    .map(data -> new Object[]{data})
                    .toArray(Object[][]::new);
        } else {
            throw new RuntimeException("Test method is missing @TestDataSource annotation");
        }
    }

}
