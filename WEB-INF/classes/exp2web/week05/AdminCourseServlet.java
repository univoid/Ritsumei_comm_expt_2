package exp2web.week05;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exp2web.Course;

@WebServlet("/AdminCourse")
public class AdminCourseServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //get user's information in session
        HttpSession session = request.getSession();
        request.setAttribute("message", session.getAttribute("message"));
        request.setAttribute("user", session.getAttribute("user"));
        session.removeAttribute("message");

        //read information frome database
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile +
                                   ";ifexists=true", "sa", "")) {

            //new in
            String sql = "SELECT courses.c_id, airplanes.a_id, airplanes.a_co, airplanes.a_from, airplanes.a_to, courses.time, airplanes.a_fare, courses.remaining_tickets FROM courses INNER JOIN airplanes ON courses.a_id = airplanes.a_id ORDER BY courses.c_id ASC";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            List<Course> courses = new ArrayList<Course>();
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
                courses.add(course);
            }
            request.setAttribute("result", courses);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        //To JSP
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("/WEB-INF/jsp/AdminCourse.jsp");
        dispatcher.forward(request, response);
    }

}
