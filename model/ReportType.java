package a2_2001040230.model;

public enum ReportType {

    ALL("All Transaction"),
    CHECKOUT("All Checked-out books"),
    OVERDUE("Overdue books");

    private final String title;

    // private enum constructor
    private ReportType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
