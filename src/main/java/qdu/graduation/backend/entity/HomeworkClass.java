package qdu.graduation.backend.entity;

import java.util.Date;

public class HomeworkClass {
    private Integer homeworkId;

    private Integer classId;

    private Date deadline;

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}