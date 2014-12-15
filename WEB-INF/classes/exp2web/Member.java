package exp2web;



public class Member {
    private int id;

    private String name;

    private int points;

    private java.sql.Date joinDate;

    public Member() {
    }

    public Member(String name, int points, java.sql.Date joinDate) {
        this.name = name;
        this.points = points;
        this.joinDate = joinDate;
    }

    public Member(String name, int points, String dateStr) {
        this.name = name;
        this.points = points;
        this.joinDate = java.sql.Date.valueOf(dateStr);
    }

    public Member(int id, String name, int points, String dateStr) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.joinDate = java.sql.Date.valueOf(dateStr);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public java.sql.Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(java.sql.Date joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return String.format("[Member: %d, %s, %d, %tF]", id, name, points,
                             joinDate);
    }
}
