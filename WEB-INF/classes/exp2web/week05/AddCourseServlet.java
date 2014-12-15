package exp2web.week05;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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



@WebServlet( "/AddCourse" )
public class AddCourseServlet extends HttpServlet {

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )
    throws ServletException, IOException {

        java.sql.Timestamp timeNow = new java.sql.Timestamp( System.currentTimeMillis() );

        RequestDispatcher dispatcher =
            request.getRequestDispatcher( "/WEB-INF/jsp/AddCourse.jsp" );
        dispatcher.forward( request, response );
    }


    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response )
    throws ServletException, IOException {
        request.setCharacterEncoding( "UTF-8" );

        String  planeIdStr  = request.getParameter( "planeId" );
        String  boardTimeStr  = request.getParameter( "boardTime" );
        String  sumOfTicketsStr = request.getParameter( "sumOfTickets" );
        String  resultMessage = "";
        List<String> errorMessages = new ArrayList<String>();


        /* plane ID */
        int planeId = 0;
        if ( planeIdStr == null ) {
            errorMessages.add( "ID of plane null" );
        } else {
            try {
                planeId = Integer.parseInt( planeIdStr.trim() );
            } catch ( NumberFormatException e ) {
                errorMessages.add( "ID of plane Error" );
            }
        }

        /* board time */
        java.sql.Timestamp boardTime = null;
        if ( boardTimeStr == null ) {
            errorMessages.add( "Board Time null" );
        } else {
            try {
                boardTime = java.sql.Timestamp.valueOf( boardTimeStr.trim() );
            } catch ( IllegalArgumentException e ) {
                errorMessages.add( "Board Time Error" );
            }
        }

        /* sum of tickets */
        int sumOfTickets = 0;
        if ( sumOfTicketsStr == null ) {
            errorMessages.add( "sum of tickets null" );
        } else {
            try {
                sumOfTickets = Integer.parseInt( sumOfTicketsStr.trim() );
            } catch ( NumberFormatException e ) {
                errorMessages.add( "sum of tickets Error" );
            }
        }
        /* TODO */
        if ( !errorMessages.isEmpty() ) {
            request.setAttribute( "errorMessages", errorMessages );
            RequestDispatcher dispatcher =
                request.getRequestDispatcher( "/WEB-INF/jsp/AddCourse.jsp" );
            dispatcher.forward( request, response );
        } else {
            try {
                Class.forName( "org.h2.Driver" );
            } catch ( ClassNotFoundException e ) {
                throw new ServletException( e );
            }
            /* link to database */
            String dbfile = getServletContext().getRealPath( "/WEB-INF/db/dbfile" );
            try (Connection conn = DriverManager.getConnection( "jdbc:h2:file:" + dbfile
                                       + ";ifexists=true", "sa", "" ) ) {
                String sql = "INSERT INTO courses(a_id, time, remaining_tickets) VALUES(?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setInt( 1, planeId);
                ps.setTimestamp( 2, boardTime);
                ps.setInt( 3, sumOfTickets);
                int result = ps.executeUpdate();
                ps.close();

                resultMessage = "Add Course success ! ";
            } catch ( SQLException e ) {
                throw new ServletException( e );
            }

            HttpSession session = request.getSession();
            session.setAttribute( "message", resultMessage );

            String redirectUrl = request.getContextPath() + "/AdminCourse";
            response.sendRedirect( redirectUrl );
        }
    }
}
