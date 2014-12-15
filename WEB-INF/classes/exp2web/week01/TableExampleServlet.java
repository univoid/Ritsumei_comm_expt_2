package exp2web.week01;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exp2web.Member;

@WebServlet("/TableExample")
public class TableExampleServlet extends HttpServlet {
  
  private static int GOLD_MEMBER_DAYS = 1000;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    int id = 1;
    List<Member> members = new ArrayList<Member>();
    members.add(new Member(id++, "立命　太郎", 900, "2011-04-01"));
    members.add(new Member(id++, "衣笠　花子", 500, "2011-04-01"));
    members.add(new Member(id++, "草津　一郎", 800, "2011-04-01"));
    members.add(new Member(id++, "瀬田　二郎", 600, "2011-04-01"));
    members.add(new Member(id++, "石山　三郎", 600, "2012-04-01"));
    members.add(new Member(id++, "膳所　四郎", 700, "2012-04-01"));
    members.add(new Member(id++, "大津　京子", 100, "2012-04-01"));
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<link rel=\"stylesheet\" href=\"css/exp2_default.css\">");
    out.println("<title>表の出力</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<table>");
    out.println("<caption>table</caption>");
    out.println("<thead>");
    out.println("<tr>");
    out.println("<th>id</th><th>name</th><th>point</th><th>joinDate</th><th>how long</th>");
    out.println("</tr>");
    out.println("</thead>");
    out.println("<tbody>");
    for (Member m : members) {
	long dateM = m.getJoinDate().getTime();
        long now = System.currentTimeMillis();
        int diff = (int)Math.ceil((double)(now-dateM)/(1000*60*60*24));
        if (diff > 1000) out.print("<tr class = 'goldmember'>");
	    else out.print("<tr>");
	out.print("<td>" + m.getId() + "</td>");
	out.print("<td>" + m.getName() + "</td>");
	out.print("<td>" + m.getPoints() + "</td>");
	out.print("<td>" + m.getJoinDate() + "</td>");
	out.print("<td>" + diff + "</td>");
        out.print("</tr>");
    }
    out.println("</tbody>");
    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
  }
}
