package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a user comment with associated information.
 */
public class Comment {
  private User commenter;
  private String commentDescription;
  private int priority;
  private LocalDateTime datetime;
  private ArrayList<Comment> replies;

  /**
   * Constructor for a Comment with priority.
   *
   * @param commenter   The user who made the comment.
   * @param description The description of the comment.
   * @param priority    The priority level of the comment on a scale from 1-3.
   */
  public Comment(User commenter, String description, int priority) {
    this.commenter = commenter;
    this.commentDescription = description;
    this.datetime = LocalDateTime.now();
    this.replies = new ArrayList<>();

    // Check to make sure priority level provided is valid/correct on a scale from
    // 1-3
    if (priority < 0) {
      this.priority = -1;
    } else if (priority == 0) {
      this.priority = 1;
    } else {
      this.priority = priority;
    }
  }

  /**
   * Constructor for a Comment without priority.
   *
   * @param commenter   The user who made the comment.
   * @param description The description of the comment.
   */
  public Comment(User commenter, String description) {
    this.commenter = commenter;
    this.commentDescription = description;
    this.priority = -1;
    this.datetime = LocalDateTime.now();
    this.replies = new ArrayList<>();
  }

  /**
   * Constructor for a Comment with all attributes.
   *
   * @param commenter   The user who made the comment.
   * @param description The description of the comment.
   * @param priority    The priority level of the comment on a scale from 1-3.
   * @param datetime    The date and time the comment was made.
   * @param replies     A list of replies to the comment.
   */
  public Comment(User commenter, String description, int priority, LocalDateTime datetime, ArrayList<Comment> replies) {
    this.commenter = commenter;
    this.commentDescription = description;
    this.datetime = datetime;
    this.replies = replies;

    // Check to make sure priority level provided is valid/correct on a scale from
    // 1-3
    if (priority < 0) {
      this.priority = -1;
    } else if (priority == 0) {
      this.priority = 1;
    } else {
      this.priority = priority;
    }
  }

  /**
   * Returns a string representation of the comment.
   *
   * @return A string representing the comment, including author, description,
   *         priority, datetime, and replies.
   */
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = datetime.format(formatter);
    String result = "Author: " + commenter.getFirstName() + " " + commenter.getLastName() +
        "\nDescription: " + commentDescription;

    if (priority > 0) {
      result += "\nPriority: " + priority;
    }

    result += "\nDatetime: " + formattedDateTime;

    // Recursive calls on all replies to the comments along with their nested
    // replies.
    if (!replies.isEmpty()) {
      result += "\nReplies:\n";
      for (Comment reply : replies) {
        String replyText = reply.toString();

        // Handles the indentation of the replies (will add two spaces)
        // to each "level" of reply. e.g. a reply will have 2 spaces of
        // indentation, a reply on that reply will have 4, etc.
        String[] replyLines = replyText.split("\n");
        for (String line : replyLines) {
          result += "  " + line + "\n";
        }
        result += "\n";
      }
    }

    return result;
  }

  /**
   * Adds a reply to this comment.
   *
   * @param reply The comment to be added as a reply.
   */
  public void addReply(Comment reply) {
    replies.add(reply);
  }

  /**
   * Returns the commenter of this comment.
   *
   * @return The user who made the comment.
   */
  public User getCommenter() {
    return this.commenter;
  }

  /**
   * Sets the commenter of this comment.
   *
   * @param commenter The user who made the comment.
   */
  public void setCommenter(User commenter) {
    this.commenter = commenter;
  }

  /**
   * Returns the description of this comment.
   *
   * @return The description of the comment.
   */
  public String getCommentDescription() {
    return this.commentDescription;
  }

  /**
   * Sets the description of this comment.
   *
   * @param commentDescription The description of the comment.
   */
  public void setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
  }

  /**
   * Returns the priority level of this comment.
   *
   * @return The priority level of the comment.
   */
  public int getPriority() {
    return this.priority;
  }

  /**
   * Sets the priority level of this comment.
   *
   * @param priority The priority level of the comment.
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /**
   * Returns the date and time this comment was made.
   *
   * @return The date and time the comment was made.
   */
  public LocalDateTime getDateTime() {
    return this.datetime;
  }

  /**
   * Sets the date and time of this comment.
   *
   * @param datetime The date and time the comment was made.
   */
  public void setDatetime(LocalDateTime datetime) {
    this.datetime = datetime;
  }

  /**
   * Returns the list of replies to this comment.
   *
   * @return A list of comments that are replies to this comment.
   */
  public ArrayList<Comment> getReplies() {
    return this.replies;
  }
}
