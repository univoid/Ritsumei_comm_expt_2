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

import exp2web.Record;


@WebServlet("/DeleteRecord/*")
public class DeleteRecordServlet extends HttpServlet {


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

            String sql = "SELECT records.r_id, users.name, airplanes.a_id, airplanes.a_co, airplanes.a_from, airplanes.a_to, courses.time, records.time FROM records INNER JOIN users ON records.u_id = users.u_id INNER JOIN courses ON records.c_id = courses.c_id INNER JOIN airplanes ON courses.a_id = airplanes.a_id WHERE records.r_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();


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
                request.setAttribute("recordToDelete", record);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher =
            request.getRequestDispatcher("/WEB-INF/jsp/DeleteRecord.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {


        int id = 0;
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

            //add remaining Tickets
            String sql = "SELECT c_id FROM records WHERE r_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            int c_id = 0;
            while (rs.next()) {
                c_id = rs.getInt("records.c_id");
            }
            ps.close();
            sql = "UPDATE courses SET remaining_tickets = remaining_tickets +1 WHERE c_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, c_id);
            int result = ps.executeUpdate();
            ps.close();

            //delete record from database
            sql = "DELETE FROM records WHERE r_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
            ps.close();

            resultMessage = "Delete record success ! " ;

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", resultMessage);

        String redirectUrl = request.getContextPath() + "/CustomerRecord";
        response.sendRedirect(redirectUrl);

    }
}
