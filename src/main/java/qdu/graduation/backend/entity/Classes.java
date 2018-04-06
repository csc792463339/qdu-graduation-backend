package qdu.graduation.backend.entity;

/**
 * Created by Jay on 2018/4/5.
 */
public class Classes {

    private Integer classId;

    private String className;

    private Integer classStudentaccount;

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassStudentaccount(Integer classStudentaccount) {
        this.classStudentaccount = classStudentaccount;
    }

    public Integer getClassStudentaccount() {
        return this.classStudentaccount;
    }

}
