package ch.hearc.ig.guideresto.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompleteEvaluation extends Evaluation {

  private String comment;
  private String username;
  private ArrayList<Grade> grades;

  public CompleteEvaluation(Integer id, LocalDate visitDate, Restaurant restaurant, String comment,
      String username) {
    super(id, visitDate, restaurant);
    this.comment = comment;
    this.username = username;
    this.grades = new ArrayList<>();
  }

  @Override
  public Integer getId() {
    return super.getId();
  }

  public String getComment() {
    return comment;
  }

  public String getUsername() {
    return username;
  }

  public ArrayList<Grade> getGrades() {
    return grades;
  }
}