package model;

public abstract class DataConstants {
    // user.json information
    protected static final String USER_FILE = "src/main/data/user.json";
    protected static final String USER = "User";
    protected static final String USER_ID = "id";
    protected static final String USERNAME = "username";
    protected static final String FIRST_NAME = "firstName";
    protected static final String LAST_NAME = "lastName";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String PERMISSION_LEVEL = "permissionLevel";
    protected static final String TASKS_COMPLETED = "tasks-complete";

    // project.json information
    protected static final String PROJECT_FILE = "src/main/data/project.json";
    protected static final String PROJECT = "Project";
    protected static final String PROJECT_ID = "project-id";
    protected static final String PROJECT_NAME = "project-name";
    protected static final String PROJECT_DESCRIPTION = "project-description";
    protected static final String SPRINT_TIME = "sprint-time";
    protected static final String SPRINT_UNITS = "sprint-units";
    protected static final String SCRUM_MASTER = "scrum-master";
    protected static final String TEAM_MEMBERS = "team-members";
    protected static final String PROJECT_COLUMNS = "project-columns";
    protected static final String COLUMN_ID = "column-id";
    protected static final String TASK_LIST = "task-list";

    // Anything related to comments
    protected static final String COMMENTS = "comments";
    protected static final String COMMENTER = "commenter";
    protected static final String COMMENT_DESCRIPTION = "comment-description";
    protected static final String COMMENT_DATETIME = "datetime";
    protected static final String COMMENT_COMMENTS = "comment-comments";
    protected static final String COMMENT_PRIORITY = "priority";

    // tasks.json information
    protected static final String TASK_FILE = "src/main/data/tasks.json";
    protected static final String TASK = "Task";
    protected static final String TASK_ID = "task-id";
    protected static final String TASK_NAME = "task-name";
    protected static final String TASK_DESCRIPTION = "task-description";
    protected static final String TASK_PRIORITY = "task-priority";
    protected static final String TASK_ASSIGNEE = "task-assignee";
}
