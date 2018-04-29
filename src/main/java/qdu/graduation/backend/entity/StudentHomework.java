package qdu.graduation.backend.entity;

import java.util.Date;

public class StudentHomework {
    private Integer homeworkId;

    private Integer studentId;

    private String studentAnswer;

    private Integer submitRank;

    private Integer score;

    private Date correctTime;

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer == null ? null : studentAnswer.trim();
    }

    public Integer getSubmitRank() {
        return submitRank;
    }

    public void setSubmitRank(Integer submitRank) {
        this.submitRank = submitRank;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCorrectTime() {
        return correctTime;
    }

    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }
}