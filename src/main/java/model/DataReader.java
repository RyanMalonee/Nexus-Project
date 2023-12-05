package model;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The DataReader class provides methods for reading data from JSON files and
 * converting it into objects.
 */
public class DataReader extends DataConstants {
    /**
     * Retrieves a list of users from the specified user file.
     *
     * @return an ArrayList containing User objects representing the users
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            FileReader userReader = new FileReader(USER_FILE);
            JSONParser parser = new JSONParser();
            JSONArray usersJSON = (JSONArray) new JSONParser().parse(userReader);

            // Collects basic information from JSON file and creates a user object
            for (int i = 0; i < usersJSON.size(); i++) {
                UserType userType;
                JSONObject userJSON = (JSONObject) usersJSON.get(i);
                UUID uuid = UUID.fromString((String) userJSON.get(USER_ID));
                String firstName = (String) userJSON.get(FIRST_NAME);
                String lastName = (String) userJSON.get(LAST_NAME);
                String userName = (String) userJSON.get(USERNAME);
                String email = (String) userJSON.get(EMAIL);
                String password = (String) userJSON.get(PASSWORD);
                int permissionLevel = ((Long) userJSON.get(PERMISSION_LEVEL)).intValue();
                int tasksCompleted = ((Long) userJSON.get(TASKS_COMPLETED)).intValue();

                // Retuns the list of users from the json file
                users.add(new User(uuid, firstName, lastName, userName, email, password, permissionLevel,
                        tasksCompleted));
            }
            users.toString();
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of projects from the project file.
     *
     * @return An ArrayList of Project objects representing the projects.
     */
    public static ArrayList<Project> getProjects() {
        ArrayList<Project> projects = new ArrayList<Project>();

        try {
            FileReader projectReader = new FileReader(PROJECT_FILE);
            JSONParser parser = new JSONParser();
            JSONArray projectsJSON = (JSONArray) new JSONParser().parse(projectReader);

            // Collects basic information from JSON file
            for (int i = 0; i < projectsJSON.size(); i++) {
                JSONObject projectJSON = (JSONObject) projectsJSON.get(i);
                UUID uuid = UUID.fromString((String) projectJSON.get(PROJECT_ID));
                String projectName = (String) projectJSON.get(PROJECT_NAME);
                String projectDescription = (String) projectJSON.get(PROJECT_DESCRIPTION);
                int sprintTime = ((Long) projectJSON.get(SPRINT_TIME)).intValue();
                String sprintUnits = (String) projectJSON.get(SPRINT_UNITS);

                // Parse through team members and adds them to teamMembersList ArrayList
                JSONArray teamMembers = (JSONArray) projectJSON.get(TEAM_MEMBERS);
                ArrayList<User> teamMembersList = new ArrayList<User>();
                for (int j = 0; j < teamMembers.size(); j++) {
                    teamMembersList
                            .add((UserList.getInstance().getUserByUUID(UUID.fromString((String) teamMembers.get(j)))));
                }

                /*
                 * Goes through the columns in the json file and creates a column
                 * object for each one. Then adds each task to the column by its UUID
                 * from the tasks JSON file using the getTask method.
                 */
                JSONArray columnsJSON = (JSONArray) projectJSON.get(PROJECT_COLUMNS);
                ArrayList<Column> columns = new ArrayList<Column>();
                for (int b = 0; b < columnsJSON.size(); b++) {
                    JSONObject columnJSON = (JSONObject) columnsJSON.get(b);
                    String columnID = (String) columnJSON.get(COLUMN_ID);
                    columnID = columnID.toUpperCase();
                    Column column = new Column(columnID);

                    /*
                     * Adds each tasks from the tasks.JSON file to the column by its UUID
                     */
                    JSONArray taskListJSON = (JSONArray) columnJSON.get(TASK_LIST);
                    ArrayList<Task> tasks = getTasks();
                    for (int a = 0; a < taskListJSON.size(); a++) {
                        UUID taskUUID = UUID.fromString((String) taskListJSON.get(a));
                        Task task = null;
                        /*
                         * Finds the task in the tasks.json file that matches that of the
                         * task in the column
                         */
                        for (Task t : tasks) {
                            if (t.getUUID().equals(taskUUID)) {
                                task = t;
                                break;
                            }
                        }
                        if (task != null) {
                            column.addNewTask(task);
                        }
                    }
                    columns.add(column);
                }

                ArrayList<Comment> comments = new ArrayList<Comment>();
                JSONArray commentsJSON = (JSONArray) projectJSON.get(COMMENTS);
                getComments(commentsJSON, comments);

                /*
                 * Creates a user named scrumMaster and will assign it to the user
                 * in the UserList who has the matching UUID to the one found in the
                 * scrum-master property of the json file.
                 */
                User scrumMaster = UserList.getInstance()
                        .getUserByUUID(UUID.fromString((String) projectJSON.get(SCRUM_MASTER)));

                /*
                 * Creates the project object, sets the columns to the columns containing
                 * the tasks from the tasks.JSON file. Then the project is added to the
                 * projects ArrayList to be sent to the ProjectList class.
                 */
                Project projectInsert = new Project(uuid, projectName, projectDescription, sprintTime, sprintUnits,
                        scrumMaster,
                        teamMembersList, comments);
                projectInsert.setColumns(columns);
                projects.add(projectInsert);
            }
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Gets the tasks from the tasks.json file to be used
     * by getProjects when assigning tasks to columns.
     *
     * @return The ArrayList of Task objects.
     */
    private static ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            FileReader taskReader = new FileReader(TASK_FILE);
            JSONParser taskParser = new JSONParser();
            JSONArray tasksJSON = (JSONArray) new JSONParser().parse(taskReader);
            // goes through the tasks JSON file and creates a task object for each one
            for (int l = 0; l < tasksJSON.size(); l++) {
                JSONObject taskJSON = (JSONObject) tasksJSON.get(l);
                UUID taskUUID = UUID.fromString((String) taskJSON.get(TASK_ID));
                String taskName = (String) taskJSON.get(TASK_NAME);
                String taskDescription = (String) taskJSON.get(TASK_DESCRIPTION);
                int taskPriority = ((Long) taskJSON.get(TASK_PRIORITY)).intValue();
                User assignee = UserList.getInstance()
                        .getUserByUUID(UUID.fromString((String) taskJSON.get(TASK_ASSIGNEE)));

                ArrayList<Comment> comments = new ArrayList<Comment>();
                JSONArray commentsJSON = (JSONArray) taskJSON.get(COMMENTS);
                // Calls recurisve method to handle recursive comments/replies
                getComments(commentsJSON, comments);

                if(taskJSON.containsKey(TASK_REQUIREMENTS)) {
                    JSONArray requirementsJSON = (JSONArray) taskJSON.get(TASK_REQUIREMENTS);
                    ArrayList<String> requirements = new ArrayList<String>();
                    for (int i = 0; i < requirementsJSON.size(); i++) {
                        requirements.add((String) requirementsJSON.get(i));
                    }
                    NewFeature NewFeature = new NewFeature(requirements, taskName, taskDescription, taskPriority,
                            assignee);
                    tasks.add(NewFeature);
                    continue;
                }
                if(taskJSON.containsKey(BUG_DESCRIPTION) && taskJSON.containsKey(BUG_REPRODUCTION_STEPS)) {
                    String bugDescription = (String) taskJSON.get(BUG_DESCRIPTION);
                    String bugReproductionSteps = (String) taskJSON.get(BUG_REPRODUCTION_STEPS);
                    BugFix bugFix = new BugFix(taskName, taskDescription, taskPriority, assignee, bugDescription,
                            bugReproductionSteps);
                    tasks.add(bugFix);
                    continue;
                }

                Task task = new Task(taskUUID, taskName, taskDescription, taskPriority,
                        assignee, comments);
                tasks.add(task);
            }
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves comments from a JSON array and populates an ArrayList with Comment
     * objects.
     * Developed with the aid of Caleb Brunson, CSCE-247 TA
     *
     * @param commentsJSON the JSON array containing comments
     * @param comments     the ArrayList to store the Comment objects
     */
    private static void getComments(JSONArray commentsJSON, ArrayList<Comment> comments) {
        try {
            // Go through each comment in the commentsJSON array that is passed in:
            for (Object comment : commentsJSON) {
                // Create new JSONObject for each item/object in commentsJSON array
                JSONObject commentJSON = (JSONObject) comment;
                // Get the information from the JSON object
                User commenter = UserList.getInstance()
                        .getUserByUUID(UUID.fromString((String) commentJSON.get(COMMENTER)));
                String commentDescription = (String) commentJSON.get(COMMENT_DESCRIPTION);
                LocalDateTime datetime = LocalDateTime.parse((String) commentJSON.get(COMMENT_DATETIME));
                int priority = ((Long) commentJSON.get(COMMENT_PRIORITY)).intValue();

                // Creates an arraylist of the replies to the original comment
                ArrayList<Comment> replies = new ArrayList<Comment>();
                JSONArray repliesJSON = (JSONArray) commentJSON.get(COMMENT_COMMENTS);
                // Uses recursion to burrow down into deepest comment and add it to the replies

                // If the repliesJSON is null or empty it means we can't find any more nested
                // replies
                if (repliesJSON != null && !repliesJSON.isEmpty()) {
                    getComments(repliesJSON, replies);
                    comments.add(new Comment(commenter, commentDescription, priority, datetime, replies));
                } else {
                    comments.add(new Comment(commenter, commentDescription, priority, datetime, replies));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
