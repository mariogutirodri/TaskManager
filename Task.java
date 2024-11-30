import java.time.LocalDate;

public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String category;

    public Task(String title, String description, LocalDate dueDate, String priority, String category) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return title + " - Due: " + dueDate + " - Priority: " + priority;
    }

    // Method to determine priority level
    public int getPriorityLevel() {
        // Translate priority string into numeric levels for sorting
        switch (priority) {
            case "High (!!!)":
                return 3;
            case "Medium (!!)":
                return 2;
            case "Low (!)":
                return 1;
            default:
                return 0;
        }
    }
}
