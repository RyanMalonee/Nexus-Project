package model;

import java.util.ArrayList;

/**
 * The NewFeature class represents a task for implementing a new feature with a
 * list of requirements.
 */
public class NewFeature extends Task {
    private ArrayList<String> requirements;

    /**
     * Constructs a NewFeature task with the specified parameters.
     *
     * @param requirements    The list of requirements for the new feature.
     * @param taskName        The name of the task.
     * @param taskDescription The description of the task.
     * @param taskPriority    The priority of the task.
     * @param assignee        The user assigned to the task.
     */
    public NewFeature(ArrayList<String> requirements, String taskName,
            String taskDescription, int taskPriority, User assignee) {
        super(taskName, taskDescription, taskPriority, assignee);
        if (requirements == null) {
            this.requirements = new ArrayList<>();
        } else {
            this.requirements = requirements;
        }
    }

    /**
     * Adds a requirement to the list of requirements.
     *
     * @param requirement the requirement to be added
     * @return void
     */
    public void addRequirement(String requirement) {
        if (requirement == null) {
            return;
        } else {
            this.requirements.add(requirement);
        }
    }

    /**
     * Removes a requirement from the list of requirements.
     *
     * @param requirement the requirement to be removed
     */
    public void removeRequirement(String requirement) {
        this.requirements.remove(requirement);
    }

    public ArrayList<String> getRequirements() {
        return this.requirements;
    }

    public void setRequirements(ArrayList<String> requirements) {
        if (requirements != null) {
            this.requirements = requirements;
        }
    }

    public String toString() {
        String result = "";
        result += super.toString();
        result += "\n  - Requirements: \n";
        for (int i = 0; i < this.requirements.size(); i++) {
            result += "    - " + this.requirements.get(i) + "\n";
        }
        return result;
    }
}
