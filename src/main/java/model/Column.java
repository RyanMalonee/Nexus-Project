package model;

import java.util.ArrayList;

/**
 * The Column class represents a column in a task management system.
 */
public class Column {
    private ColumnType columnType;
    private String columnName;
    private ArrayList<Task> taskList;

    /**
     * Constructs a new column with the specified column type.
     *
     * @param columnType The type of the column (NEWTASK, INPROGRESS, COMPLETED,
     *                   ARCHIVE).
     */
    public Column(ColumnType columnType) {
        this.columnType = columnType;
        if (this.columnType == ColumnType.NEWTASK) {
            this.columnName = "New Task";
        } else if (this.columnType == ColumnType.INPROGRESS) {
            this.columnName = "In Progress";
        } else if (this.columnType == ColumnType.COMPLETED) {
            this.columnName = "Completed";
        } else if (this.columnType == ColumnType.ARCHIVE) {
            this.columnName = "Archive";
        }
        this.taskList = new ArrayList<Task>();
    }

    /**
     * Constructs a new column with the specified column name.
     *
     * @param columnName The name of the column.
     */
    public Column(String columnName) {
        this.columnName = columnName;
        if (columnName.equals("New Task")) {
            this.columnType = ColumnType.NEWTASK;
        } else if (columnName.equals("In Progress")) {
            this.columnType = ColumnType.INPROGRESS;
        } else if (columnName.equals("Completed")) {
            this.columnType = ColumnType.COMPLETED;
        } else if (columnName.equals("Archive")) {
            this.columnType = ColumnType.ARCHIVE;
        } else {
            this.columnType = null;
        }
        this.taskList = new ArrayList<Task>();
    }

    /**
     * Adds a new task to the column.
     *
     * @param task The task to be added to the column.
     * @return True if the task is added successfully, false if it already exists in
     *         the column.
     */
    public boolean addNewTask(Task task) {
        if (taskList.contains(task)) {
            return false;
        }
        taskList.add(task);
        return true;
    }

    /**
     * Gets a list of tasks in the column.
     *
     * @return An ArrayList of tasks in the column.
     */
    public ArrayList<Task> getTasks() {
        return this.taskList;
    }

    /**
     * Gets the type of the column.
     *
     * @return The type of the column (e.g., NEWTASK, INPROGRESS, COMPLETED,
     *         ARCHIVE).
     */
    public ColumnType getColumnType() {
        return columnType;
    }

    /**
     * Gets the name of the column.
     *
     * @return The name of the column.
     */
    public String getName() {
        return columnName;
    }

    /**
     * Returns a string representation of the column, including its name and the
     * tasks within it.
     *
     * @return A string representation of the column.
     */
    public String toString() {
        String result = "";
        result += this.columnName + "\n***********************\n";
        for (Task task : taskList) {
            result += "  - " + task.toString() + "\n";
        }
        result += "\n";
        return result;
    }
}
