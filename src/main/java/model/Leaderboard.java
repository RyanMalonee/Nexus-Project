package model;

import java.util.ArrayList;

public class Leaderboard {

    private ArrayList<User> topUsers;

    /**
     * Constructs a Leaderboard object
     * 
     * @param teamMembers users to be sorted and implemeneted in the leaderboard
     */
    public Leaderboard(ArrayList<User> teamMembers) {
        topUsers = teamMembers;
        sortUsers(topUsers);
    }

    /**
     * Sorts users in descending order based on number of tasks completed
     * 
     * @param users
     */
    private void sortUsers(ArrayList<User> users) {
        mergeSort(users, 0, users.size() - 1);
    }

    /**
     * Merge
     * 
     * @param users ArrayList of users
     * @param left  left sublist as int
     * @param mid   middle position of list as int
     * @param right right sublist as int
     */
    private void merge(ArrayList<User> users, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<User> leftArray = new ArrayList<>(users.subList(left, left + n1));
        ArrayList<User> rightArray = new ArrayList<>(users.subList(mid + 1, mid + 1 + n2));

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftArray.get(i).getTaskCompletion() >= rightArray.get(j).getTaskCompletion()) {
                users.set(k, leftArray.get(i));
                i++;
            } else {
                users.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            users.set(k, leftArray.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            users.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    /**
     * Merge sort
     * 
     * @param users users as ArrayList
     * @param left  left sublist as int
     * @param right right sublist as int
     */
    private void mergeSort(ArrayList<User> users, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(users, left, mid);
            mergeSort(users, mid + 1, right);
            merge(users, left, mid, right);
        }
    }

    /**
     * String representation of leaderboard
     * 
     * @return Leaderboard as string
     */
    public String toString() {
        return "#1: " + topUsers.get(0).getUserName() + ", Tasks completed: " + topUsers.get(0).getTaskCompletion()
                + "\n"
                + "#2: " + topUsers.get(1).getUserName() + ", Tasks completed: " + topUsers.get(1).getTaskCompletion()
                + "\n"
                + "#3: " + topUsers.get(2).getUserName() + ", Tasks completed: " + topUsers.get(2).getTaskCompletion()
                + "\n"
                + "#4: " + topUsers.get(3).getUserName() + ", Tasks completed: " + topUsers.get(3).getTaskCompletion()
                + "\n"
                + "#5: " + topUsers.get(4).getUserName() + ", Tasks completed: " + topUsers.get(4).getTaskCompletion()
                + "\n";
    }
}