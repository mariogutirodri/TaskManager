import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class TaskManagerApp extends Application {
    private ObservableList<Task> taskList; 
    private ObservableList<String> categories;
    private ListView<Task> taskListView;
    private ComboBox<String> categoryComboBox;
    private boolean isDarkMode = false; // Track state for activation or deactivation of dark mode
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Manager");

        // Creation of Arrays
        taskList = FXCollections.observableArrayList();
        categories = FXCollections.observableArrayList("General"); // Default category
        taskListView = new ListView<>(taskList);

        // Add Task Button - SetOnAction
        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> showTaskDialog(null));

        // Edit Task Button - SetOnAction
        Button editButton = new Button("Edit Task");
        editButton.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                showTaskDialog(selectedTask);
            }
        });

        // Delete Task Button - SetOnAction
        Button deleteButton = new Button("Delete Task");
        deleteButton.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                taskList.remove(selectedTask);
            }
        });

        // Sort Tasks Button - SetOnAction
        Button sortButton = new Button("Sort Tasks");
        sortButton.setOnAction(e -> {
            FXCollections.sort(taskList, (t1, t2) -> {
                int priorityComparison = Integer.compare(t2.getPriorityLevel(), t1.getPriorityLevel());
                if (priorityComparison != 0) {
                    return priorityComparison;
                }
                return t1.getDueDate().compareTo(t2.getDueDate());
            });
            taskListView.refresh();
        });

        // Filter Tasks Button - SetOnAction
        Button filterButton = new Button("Filter Tasks");
        filterButton.setOnAction(e -> showFilterDialog());

        // Dark Mode Toggle Button
        Button darkModeButton = new Button("Dark Mode");
        darkModeButton.setOnAction(e -> toggleDarkMode());

        // Category Management
        categoryComboBox = new ComboBox<>(categories);
        categoryComboBox.setValue("General");
        categoryComboBox.setOnAction(e -> updateTaskListView());

        // Add Category Button - SetOnAction
        Button addCategoryButton = new Button("Add Category");
        addCategoryButton.setOnAction(e -> showAddCategoryDialog());

        // Creation of display for category options
        HBox categoryBox = new HBox(10, new Label("Category:"), categoryComboBox, addCategoryButton);

        // Layout
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton, sortButton, filterButton, darkModeButton);
        VBox layout = new VBox(10, categoryBox, taskListView, buttonBox);
        layout.setPadding(new Insets(10));

        // Primary scene creation - main window
        scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This function will display the neccessary window to add or edit a task; Title, Description, Due date, Priority and Category options are included. In case of editing a
    // task, the input will be the selected task to edit. There will not be any input if a new task is being created. The output of the function will be the insertion of the
    // task in the ArrayList, and therefore will be visible.
    private void showTaskDialog(Task task) {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle(task == null ? "Add Task" : "Edit Task");

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter task title");

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Enter task description");

        Label dueDateLabel = new Label("Due Date:");
        DatePicker dueDatePicker = new DatePicker();

        Label priorityLabel = new Label("Priority:");
        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low (!)", "Medium (!!)", "High (!!!)");

        Label categoryLabel = new Label("Category:");
        ComboBox<String> categoryBox = new ComboBox<>(categories);

        if (task != null) { //Setters for task creation 
            titleField.setText(task.getTitle());
            descriptionArea.setText(task.getDescription());
            dueDatePicker.setValue(task.getDueDate());
            priorityBox.setValue(task.getPriority());
            categoryBox.setValue(task.getCategory());
        }

        //Creation of content for TaskDialog window
        VBox dialogContent = new VBox(10, titleLabel, titleField, descriptionLabel, descriptionArea, 
                dueDateLabel, dueDatePicker, priorityLabel, priorityBox, categoryLabel, categoryBox);
        dialogContent.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(dialogContent);

        //Save button for TaskDialog Window
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        //Save button - Action
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String title = titleField.getText();
                String description = descriptionArea.getText();
                LocalDate dueDate = dueDatePicker.getValue();
                String priority = priorityBox.getValue();
                String category = categoryBox.getValue();
                return new Task(title, description, dueDate, priority, category);
            }
            return null;
        });

        // This function scans for input on the category section; Prevents users to not input any data once the "showAddCategoryDialog" function is toggled.
        dialog.showAndWait().ifPresent(result -> {
            if (task == null) {
                taskList.add(result);
            } else {
                task.setTitle(result.getTitle());
                task.setDescription(result.getDescription());
                task.setDueDate(result.getDueDate());
                task.setPriority(result.getPriority());
                task.setCategory(result.getCategory());
                taskListView.refresh();
            }
            updateTaskListView();
        });
    }
    // This function enables the category dialog; Creates a new category and adds it to the list of "existing categories" inside the task manager.
    private void showAddCategoryDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Create a new category");
        dialog.setContentText("Category name:");

        dialog.showAndWait().ifPresent(name -> {
            if (!name.isBlank() && !categories.contains(name)) {
                categories.add(name);
            }
        });
    }

    private void updateTaskListView() {
        String selectedCategory = categoryComboBox.getValue();
        ObservableList<Task> filteredTasks = FXCollections.observableArrayList();

        for (Task task : taskList) {
            if (task.getCategory().equals(selectedCategory)) {
                filteredTasks.add(task);
            }
        }

        taskListView.setItems(filteredTasks);
    }
    // Toggles the dark mode in all windows
    private void toggleDarkMode() {
        if (isDarkMode) {
            scene.getStylesheets().clear();
        } else {
            scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());
        }
        isDarkMode = !isDarkMode;
    }
    // Applies filtering options inside the task manager. Input will be the activation of the button and the selection of a priority by the user.
    // Output will be the display of a menu in order to filter by a selected priority, and the following implementation of the filtering based on the input of the user.
    private void showFilterDialog() {
        Stage filterStage = new Stage();
        filterStage.setTitle("Filter Tasks");

        Label priorityLabel = new Label("Filter by Priority:");
        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low", "Medium", "High", "None");

        Button applyButton = new Button("Apply Filter");
        Button clearButton = new Button("Clear Filter");

        VBox layout = new VBox(10, priorityLabel, priorityBox, applyButton, clearButton);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Scene filterScene = new Scene(layout, 300, 200);
        filterStage.setScene(filterScene);

        applyButton.setOnAction(e -> {
            String selectedPriority = priorityBox.getValue();
            if (selectedPriority != null) {
                filterTasksByPriority(selectedPriority);
            }
            filterStage.close();
        });

        clearButton.setOnAction(e -> {
            taskListView.setItems(taskList);
            filterStage.close();
        });

        filterStage.show();
    }
    // This function enables the filtering by priority. Input will be the selected priority by the user. Output will be 
    // the display the implementation of the filtering based on the input of the user.
    private void filterTasksByPriority(String priority) {
        ObservableList<Task> filteredTasks = FXCollections.observableArrayList();

        for (Task task : taskList) {
            String taskPriority = task.getPriority().split(" ")[0];

            if (priority.equals("None") || taskPriority.equalsIgnoreCase(priority)) {
                filteredTasks.add(task);
            }
        }

        taskListView.setItems(filteredTasks);
    }
}
