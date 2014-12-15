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

import exp2web.Record;
import exp2web.auth.User;

@WebServlet("/CustomerRecord")
public class CustomerRecordServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //get user's information in session
        HttpSession session = request.getSession();
        request.setAttribute("message", session.getAttribute("message"));
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
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
            String sql = "SELECT records.r_id, users.name, airplanes.a_id, airplanes.a_co, airplanes.a_from, airplanes.a_to, courses.time, records.time FROM records INNER JOIN users ON records.u_id = users.u_id INNER JOIN courses ON records.c_id = courses.c_id INNER JOIN airplanes ON courses.a_id = airplanes.a_id WHERE users.name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());

            ResultSet rs = ps.executeQuery();
            List<Record> records = new ArrayList<Record>();
            while (rs.next()) {
                Record record = new Record();
                record.setRecordId(rs.getInt("records.r_id"));
                record.setUserName(rs.getString("users.name"));
                record.setPlaneId(rs.getInt("airplanes.a_id"));
                record.setPlaneCo(rs.getString("airplanes.a_co"));
                record.setFrom(rs.getString("airplanes.a_from"));
                record.setTo(rs.getString("airplanes.a_to"));
                record.setBoardTime(rs.getTimestamp("courses.time"));
                record.setBuyTime(rs.getTimestamp("records.time"));
                records.add(record);
            }
            request.setAttribute("result", records);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        //To JSP
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("/WEB-INF/jsp/CustomerRecord.jsp");
        dispatcher.forward(request, response);
    }

}
