package exp2web.week01;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Hello")
public class HelloServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, 
      HttpServletResponse response) 
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>hello</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<p>Hello!</p>");
    out.println("<p>I am is0291hh!</p>");
    out.println("</body>");
    out.println("</html>");
  }
}
