package lk.ijse.Model;

public class Test {
    private String testId;
    private String result;
    private String description;

    public Test(String testId, String result, String description) {
        this.testId = testId;
        this.result = result;
        this.description = description;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}