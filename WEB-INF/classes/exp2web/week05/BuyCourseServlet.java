package exp2web.week05;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import exp2web.Course;
import exp2web.auth.User;

@WebServlet("/BuyCourse/*")
public class BuyCourseServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        int id = 0;
        try {
            id = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (Exception e) {

            throw new ServletException(e);
        }

        System.out.println("(GET) " + id);

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
                                   + ";ifexists=true", "sa", "")) {

            String sql = "SELECT courses.c_id, airplanes.a_id, airplanes.a_co, airplanes.a_from, airplanes.a_to, courses.time, airplanes.a_fare, courses.remaining_tickets FROM courses INNER JOIN airplanes ON courses.a_id = airplanes.a_id WHERE courses.remaining_tickets > 0 AND courses.c_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("courses.c_id"));
                course.setPlaneId(rs.getInt("airplanes.a_id"));
                course.setPlaneCo(rs.getString("airplanes.a_co"));
                course.setFrom(rs.getString("airplanes.a_from"));
                course.setTo(rs.getString("airplanes.a_to"));
                course.setBoardTime(rs.getTimestamp("courses.time"));
                course.setFare(rs.getInt("airplanes.a_fare"));
                course.setRemainingTickets(rs.getInt("courses.remaining_tickets"));
                request.setAttribute("courseToBuy", course);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher =
            request.getRequestDispatcher("/WEB-INF/jsp/BuyCourse.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * buy
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        int id = 0;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            id = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (Exception e) {

            throw new ServletException(e);
        }
        System.out.println("(POST) " + id);

        String resultMessage = "";

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
                                   + ";ifexists=true", "sa", "")) {

            //buy new course
            String sql = "INSERT INTO records(u_id, c_id, time) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            //get user id from session
            ps.setInt(1, user.getId());
            //get course id
            ps.setInt(2, id);
            //get time now
            //java.util.Date date= new java.util.Date();
            ps.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
            int result = ps.executeUpdate();
            ps.close();
            //dec remaining tickets
            sql = "UPDATE courses SET remaining_tickets = remaining_tickets -1 WHERE c_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
            ps.close();

            resultMessage = "buy new Course success ! " ;

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        session.setAttribute("message", resultMessage);

        String redirectUrl = request.getContextPath() + "/CustomerCourse";
        response.sendRedirect(redirectUrl);

    }
}
