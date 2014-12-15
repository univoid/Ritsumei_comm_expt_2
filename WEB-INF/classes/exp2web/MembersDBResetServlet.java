package exp2web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ¥Ç¥Ð¥Ã¥°ÓÃ£ºÀýî}¤Î»á†T¥Ç©`¥¿¥Ù©`¥¹¤ò³õÆÚ×´‘B¤Ë‘ø¤¹
 * Ò»µ© members ¤Î¥Æ©`¥Ö¥ë¤òÏ÷³ý¤·£¬ÐÂ¤¿¤Ëmembers ¤Î¥Æ©`¥Ö¥ë¤ò¶¨Áx¤¹¤ë
 * ¶¨Áx¤·¤Ê¤ª¤·¤¿¥Æ©`¥Ö¥ë¤Ë³õÆÚ¥Ç©`¥¿¤òµÇåh¤·¤Æ¤¤¤¯
 */
// ¥µ©`¥Ö¥ì¥Ã¥È¤òºô¤Ó³ö¤¹URL¤òÖ¸¶¨¤¹¤ë
@WebServlet("/MembersDBReset")
public class MembersDBResetServlet extends HttpServlet {

    // GET¥á¥½¥Ã¥É¤ÎˆöºÏ¤ÏPOST¤òËÍ¤ëform¤òº¬¤ó¤ÀHTML¤ò·µ¤¹
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // ¥Ö¥é¥¦¥¶¤Ë½Y¹û¤ò±íÊ¾¤¹¤ë£®
        // ¤³¤³¤Ç¤Ï£¬JSP¤òÊ¹¤ï¤º¤Ë¤½¤Î¤Þ¤ÞHTML¤òÉú³É¤¹¤ë£®
        // contentType¤òÔO¶¨¤¹¤ë
        response.setContentType("text/html;charset=UTF-8");
        // ³öÁ¦ÏÈ¤È¤Ê¤ëPrintWriter¤òÈ¡µÃ
        PrintWriter out = response.getWriter();
        // HTML¤ÎÄÚÈÝ¤òout¤Ë³öÁ¦¤·¤Æ¤¤¤¯
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        // HTML¤Î¥Ø¥Ã¥À²¿·Ö
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        // ¥¿¥¤¥È¥ë¤ÎÔO¶¨
        out.println("<title>»á†T¥Ç©`¥¿¥Ù©`¥¹¤ò³õÆÚ×´‘B¤Ë‘ø¤¹</title>");
        out.println("</head>");
        // HTML¤Î¥Ü¥Ç¥£²¿·Ö
        out.println("<body>");
        out.println("<form method=\"POST\" action=\"" + request.getContextPath() +
                    "/MembersDBReset\">");
        out.println("<input type=\"submit\" value=\"»á†T¥Ç©`¥¿¥Ù©`¥¹¤ò³õÆÚ×´‘B¤Ë‘ø¤¹(" + request.getContextPath() +
                    ")\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    // POST¥á¥½¥Ã¥É¤Ç£¬ŒgëH¤Ë¥Ç©`¥¿¥Ù©`¥¹¤ò³õÆÚ×´‘B¤Ë‘ø¤¹
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        // Web¥¢¥×¥ê¤Î¥ê¥í©`¥É•r¤Ë¥É¥é¥¤¥Ð¤¬unload¤µ¤ì¤¿ˆöºÏ¤Ë‚ä¤¨¤Æ¸Ä¤á¤Æ¥É¥é¥¤¥Ð¤ò¥í©`¥É¤¹¤ë
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
        // ¥Ç©`¥¿¥Ù©`¥¹¥Õ¥¡¥¤¥ë¤ÎŒgëH¤ÎˆöËù¤òÇó¤á¤ë
        String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
        System.out.println(dbfile);
        // ½MÞz¤ß¥â©`¥É¤Ç¥ª©`¥×¥ó¤¹¤ë
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
                                   + ";ifexists=false", "sa", "")) {
            Statement stmt = conn.createStatement();
            // ¥Æ©`¥Ö¥ë¤òÒ»µ©Ï÷³ý¤¹¤ë
            String sql = "DROP TABLE IF EXISTS members";
            stmt.executeUpdate(sql);
            // ¥Ç¥Ð¥Ã¥°ÓÃ¤Ë˜ËœÊ³öÁ¦¤Ë•ø¤­³ö¤¹
            System.out.println("Table members dropped.");

            // ¥Æ©`¥Ö¥ë¤ò×÷³É¤·Ö±¤¹
            sql = "CREATE TABLE members(id INT PRIMARY KEY AUTO_INCREMENT, " +
                  "ÊÏÃû VARCHAR, ¥Ý¥¤¥ó¥ÈÊý INT, ¼ÓÈëÈÕ DATE)";
            stmt.executeUpdate(sql);
            // ¥Ç¥Ð¥Ã¥°ÓÃ¤Ë˜ËœÊ³öÁ¦¤Ë•ø¤­³ö¤¹
            System.out.println("Table members created.");
            stmt.close();

            // ³õÆÚ¥Ç©`¥¿¤ò×÷³É¤¹¤ë
            // »á†T¥Ç©`¥¿¤ò±£³Ö¤¹¤ë¤¿¤á¤ËArrayList¤òÊ¹ÓÃ¤¹¤ë
            List<Member> members = new ArrayList<Member>();
            // ¥Æ©`¥Ö¥ë¤ÎÒ»ÐÐ(»á†TÒ»ÈË)¤´¤È¤ËMember¥¯¥é¥¹¤Î¥¤¥ó¥¹¥¿¥ó¥¹¤ò×÷³É¤·£¬members¤Ë×·¼Ó¤¹¤ë
            members.add(new Member("Á¢Ãü¡¡Ì«ÀÉ", 900, "2011-04-01"));
            members.add(new Member("ÒÂóÒ¡¡»¨×Ó", 500, "2011-04-01"));
            members.add(new Member("²Ý½ò¡¡Ò»ÀÉ", 800, "2011-04-01"));
            members.add(new Member("žÌï¡¡¶þÀÉ", 600, "2011-04-01"));
            members.add(new Member("Ê¯É½¡¡ÈýÀÉ", 600, "2012-04-01"));
            members.add(new Member("ÉÅËù¡¡ËÄÀÉ", 700, "2012-04-01"));
            members.add(new Member("´ó½ò¡¡¾©×Ó", 100, "2012-04-01"));

            // PreparedStatement¤òÊ¹ÓÃ¤·¤Æ£¬¥Ç©`¥¿¥Ù©`¥¹¤Ë¥Ç©`¥¿¤ò×·¼Ó¤·¤Æ¤¤¤¯£®
            sql = "INSERT INTO members(ÊÏÃû, ¥Ý¥¤¥ó¥ÈÊý, ¼ÓÈëÈÕ) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (Member member : members) {
                ps.setString(1, member.getName());
                ps.setInt(2, member.getPoints());
                ps.setDate(3, member.getJoinDate());
                ps.executeUpdate();
                // Ò»µ©¥Ñ¥é¥á©`¥¿¤ò¥¯¥ê¥¢¤¹¤ë
                ps.clearParameters();
            }
            ps.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        // ¥Ö¥é¥¦¥¶¤Ë½Y¹û¤ò±íÊ¾¤¹¤ë£®
        // ¤³¤³¤Ç¤Ï£¬JSP¤òÊ¹¤ï¤º¤Ë¤½¤Î¤Þ¤ÞHTML¤òÉú³É¤¹¤ë£®
        // contentType¤òÔO¶¨¤¹¤ë
        response.setContentType("text/html;charset=UTF-8");
        // ³öÁ¦ÏÈ¤È¤Ê¤ëPrintWriter¤òÈ¡µÃ
        PrintWriter out = response.getWriter();
        // HTML¤ÎÄÚÈÝ¤òout¤Ë³öÁ¦¤·¤Æ¤¤¤¯
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        // HTML¤Î¥Ø¥Ã¥À²¿·Ö
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        // ¥¿¥¤¥È¥ë¤ÎÔO¶¨
        out.println("<title>MembersDB Reset</title>");
        out.println("</head>");
        // HTML¤Î¥Ü¥Ç¥£²¿·Ö
        out.println("<body>");
        out.println("<p>»á†T¥Ç©`¥¿¥Ù©`¥¹¤Ï³õÆÚ×´‘B¤Ë¥ê¥»¥Ã¥È¤µ¤ì¤Þ¤·¤¿£®</p>");
        out.println("</body>");
        out.println("</html>");
    }
}