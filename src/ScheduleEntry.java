/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
/**
 *
 * @author anishsparida
 */
public class ScheduleEntry {
    
    private String Semester;
    private String CourseCode;
    private String StudentID;
    private String Status;
    private Timestamp timestamp;

    public ScheduleEntry(String Semester, String CourseCode, String StudentID, String Status, Timestamp timestamp) {
        this.Semester = Semester;
        this.CourseCode = CourseCode;
        this.StudentID = StudentID;
        this.Status = Status;
        this.timestamp = timestamp;
    }

    public String getSemester() {
        return Semester;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getStatus() {
        return Status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
}
