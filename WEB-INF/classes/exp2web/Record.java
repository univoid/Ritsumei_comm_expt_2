package exp2web;

public class Record {
    private int recordId;
    private String userName;
    private int planeId;
    private String planeCo;
    private String from;
    private String to;
    private java.sql.Timestamp boardTime;
    private java.sql.Timestamp buyTime;

    public Record() {
    }

    public Record(int recordId, String userName, int planeId, String planeCo,
                  String from, String to, java.sql.Timestamp boardtime,
                  java.sql.Timestamp buytime) {
        this.recordId = recordId;
        this.userName = userName;
        this.planeId = planeId;
        this.planeCo = planeCo;
        this.from = from;
        this.to = to;
        this.boardTime = boardTime;
        this.buyTime = buyTime;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int id) {
        this.recordId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
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

    public void setBoardTime(java.sql.Timestamp boardTime) {
        this.boardTime = boardTime;
    }

    public java.sql.Timestamp getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(java.sql.Timestamp buyTime) {
        this.buyTime = buyTime;
    }

    @Override
    public String toString() {
        return String.format("[Record: %d, %s, %d, %s, %s, %s, %tF, %tF]",
                             recordId, userName, planeId, planeCo, from, to, boardTime, buyTime);
    }
}
