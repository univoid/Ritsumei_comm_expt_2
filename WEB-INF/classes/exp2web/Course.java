package exp2web;

public class Course {
    private int courseId;
    private int planeId;
    private String planeCo;
    private String from;
    private String to;
    private java.sql.Timestamp boardTime;
    private int fare;
    private int remainingTickets;

    public Course() {
    }

    public Course(int courseId, int planeId, String planeCo, String from,
                  String to, java.sql.Timestamp boardTime, int fare, int remainingTickets) {
        this.courseId = courseId;
        this.planeId = planeId;
        this.planeCo = planeCo;
        this.from = from;
        this.to = to;
        this.boardTime = boardTime;
        this.fare = fare;
        this.remainingTickets = remainingTickets;
    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int id) {
        this.courseId = id;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int id) {
        this.planeId = id;
    }

    public String getPlaneCo() {
        return planeCo;
    }

    public void setPlaneCo(String co) {
        this.planeCo = co;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public java.sql.Timestamp getBoardTime() {
        return boardTime;
    }

    public void setBoardTime(java.sql.Timestamp time) {
        this.boardTime = time;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int num) {
        this.remainingTickets = num;
    }

    @Override
    public String toString() {
        return String.format("[Course: %d, %d, %s, %s, %s, %tF, %d, %d]",
                             courseId, planeId, planeCo, from, to, boardTime, fare,
                             remainingTickets);
    }
}
