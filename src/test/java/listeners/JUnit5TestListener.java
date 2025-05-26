package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import utils.ExtentManager;

import java.util.Optional;

public class JUnit5TestListener implements TestWatcher {
    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentTest test = extent.createTest(context.getDisplayName());
        extentTest.set(test);
        test.log(Status.PASS, "Test passed");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentTest test = extent.createTest(context.getDisplayName());
        extentTest.set(test);
        test.log(Status.FAIL, "Test failed: " + cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        ExtentTest test = extent.createTest(context.getDisplayName());
        extentTest.set(test);
        test.log(Status.SKIP, "Test aborted");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        ExtentTest test = extent.createTest(context.getDisplayName());
        extentTest.set(test);
        test.log(Status.SKIP, "Test disabled: " + reason.orElse("No reason"));
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }
}