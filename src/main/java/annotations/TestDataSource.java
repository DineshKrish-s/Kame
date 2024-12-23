package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to specify test data source (Excel file and sheet).
 */
@Retention(RetentionPolicy.RUNTIME) // Make it accessible at runtime
public @interface TestDataSource {
    String fileName();  // Excel file name
    String sheetName(); // Sheet name
}
