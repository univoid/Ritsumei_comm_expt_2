package exp2web.auth;

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


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    static String DEFAULT_TARGET = "/";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String username = request.getParameter("username");
        System.out.println("LoginServlet(GET): username = " + username);

        if (username != null) {

            request.setAttribute("username", username);
        };

        RequestDispatcher dispatcher =
            request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {

            System.out.println("LoginServlet(POST): already logged in by " + user.getName());
            session.invalidate();

            session = request.getSession();
            user = null;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("LoginServlet(POST): username = " + username + ", password = " + password);

        if ((username == null) || (password == null)) {

            if (username != null) {
                request.setAttribute("username", username);
            }
            RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
            return;
        }


        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");

        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
                                   + ";ifexists=true", "sa", "")) {
            String sql = "SELECT * FROM users WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                user = new User();
                user.setId(rs.getInt("u_id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAuthority(rs.getInt("authority"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        if (user != null) {

            if (user.checkPassword(password)) {

                session.setAttribute("user", user);

                String target = (String) session.getAttribute("target");
                if (target != null) {
                    session.removeAttribute("target");
                } else {

                    //2014.12.2
                    // new target for Admin or customers
                    RequestDispatcher dispatcher = null;
                    if (user.getAuthority() == 1)
                        target = request.getContextPath() + "/AdminMainPage";
                    else
                        target = request.getContextPath() + "/CustomerMainPage";

                }

                System.out.println("LoginServlet(POST): redirecting to " + target);
                response.sendRedirect(target);
                return;
            }
        }

        request.setAttribute("errorMessage", "username or password is wrong");
        request.setAttribute("username", username);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
        dispatcher.forward(request, response);
        return;
    }
}
