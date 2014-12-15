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

@WebServlet("/AdminRecord")
public class AdminRecordServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //get user's information in session
        HttpSession session = request.getSession();
        request.setAttribute("message", session.getAttribute("message"));
        request.setAttribute("user", session.getAttribute("user"));
        session.removeAttribute("message");

        //get check submit information from request
        String nameStr = request.getParameter("name");
        String idStr = request.getParameter("id");
        String companyStr = request.getParameter("company");
        String fromStr = request.getParameter("from");
        String toStr = request.getParameter("to");
        String orderByStr = request.getParameter("orderBy");
        String orderStr = request.getParameter("order");

        //input error Judgment
        List<String> errorMessages = new ArrayList<String>();
        //name
        String name = "";
        if (nameStr != null) {
            name = nameStr.trim();
        }
        //id
        int id = 0;
        if (idStr != null) {
            idStr = idStr.trim();
            if (idStr.length() > 0) {
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    errorMessages.add("id input error");
                }
            }
        }
        //company
        String company = "";
        if (companyStr != null) {
            company = companyStr.trim();
        }
        //from
        String from = "";
        if (fromStr != null) {
            from = fromStr.trim();
        }
        //to
        String to = "";
        if (toStr != null) {
            to = toStr.trim();
        }
        //
        if (!errorMessages.isEmpty()) {
            // send error to JSP and break
            request.setAttribute("errorMessages", errorMessages);
            RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/AdminRecord.jsp");
            dispatcher.forward(request, response);
        } else {
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
                String sql = "SELECT records.r_id, users.name, airplanes.a_id, airplanes.a_co, airplanes.a_from, airplanes.a_to, courses.time, records.time FROM records INNER JOIN users ON records.u_id = users.u_id INNER JOIN courses ON records.c_id = courses.c_id INNER JOIN airplanes ON courses.a_id = airplanes.a_id";
                if ((name != "") || (id != 0) || (company != "") || (from != "") || (to != "")) {
                    sql += " WHERE";
                }
                //count of condition
                int count = 0;
                if (name != "") {
                    sql += " (users.name = '" + name + "')";
                    count++;
                }
                if (id != 0) {
                    if (count > 0) sql += " AND";
                    sql += " (airplanes.a_id = " + idStr + ")";
                    count++;
                }
                if (company != "") {
                    if (count > 0) sql += " AND";
                    sql += " (airplanes.a_co = '" + company + "')";
                    count++;
                }
                if (from != "") {
                    if (count > 0) sql += " AND";
                    sql += " (airplanes.a_from = '" + from + "')";
                    count++;
                }
                if (to != "") {
                    if (count > 0) sql += " AND";
                    sql += " (airplanes.a_to = '" + to + "')";
                    count++;
                }
                //order
                if (orderByStr != "") {
                    System.out.println(orderByStr);
                    sql = sql + "  ORDER BY " + orderByStr +
                          (("ASC".equals(orderStr) || "DESC".equals(orderStr)) ? " " + orderStr : "");
                }

                System.out.println(sql);
                PreparedStatement ps = conn.prepareStatement(sql);

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
                if (records.isEmpty())
                    request.setAttribute("errorMessages", "Sorry for No record you want.");
                else
                    request.setAttribute("result", records);
                rs.close();
                ps.close();
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        //To JSP
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("/WEB-INF/jsp/AdminRecord.jsp");
        dispatcher.forward(request, response);
    }

}
