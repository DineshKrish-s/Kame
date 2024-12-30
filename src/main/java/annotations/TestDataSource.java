package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to specify test data source (Excel file and sheet).
 */
@Retention(RetentionPolicy.RUNTIME) // Make it accessible at runtime
public @interface TestDataSource {
    String[] fileName(); // Array of file names
    String[] sheetName(); // Array of corresponding sheet names
    int[] rows() default {}; // Optional: Array of specific rows
    String rowRange() default ""; // Optional: Range of rows in "start-end" format
}
