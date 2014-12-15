package exp2web.week02;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exp2web.Member;

/**
 * 会員の一覧を求める
 */
// このサーブレットのURLを指定する
@WebServlet("/ShowAllMembers")
public class ShowAllMembersServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    /** データベースからデータを取得する */
    // Webアプリのリロード時にドライバがunloadされた場合に備えて改めてドライバをロードする
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new ServletException(e);
    }
    // 会員一覧(検索結果)を保持する変数
    List<Member> members = new ArrayList<Member>();
    // データベースファイル(/WEB-INF/db/dbfile)の実際のファイルパスを求める．    
    String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
    // JDK7から導入されたauto closeableを活用している
    // H2データベースでは，ifexists=true のオプションにより，指定されたファイルが存在しない時はエラーになる．
    // このオプションを指定しないと，指定されたファイルが存在しない時は自動的にファイルが生成される．
    try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
        + ";ifexists=true", "sa", "")) {
      Statement stmt = conn.createStatement();
      // SQL文の定義
      String sql = "SELECT * FROM members";
      // SQLの実行
      ResultSet rs = stmt.executeQuery(sql);
      // SQL実行結果ResultSet から，membersに追加していく
      while (rs.next()) {
        // 会員ごとにMemberクラスのインスタンスを作成する
        Member member = new Member();
        // 検索結果から memberのインスタンス変数に設定していく
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("氏名"));
        member.setPoints(rs.getInt("ポイント数"));
        member.setJoinDate(rs.getDate("加入日"));

        // memberを会員一覧に追加する
        members.add(member);
      }
      // ResultSetをcloseする
      rs.close();
      // statementをcloseする
      stmt.close();
    } catch (SQLException e) {
      // エラーの時はExceptionを投げる
      throw new ServletException(e);
    }
    // connは自動的にcloseされる
    
    /** HTMLを出力する */
    // contentTypeを設定する
    response.setContentType("text/html;charset=UTF-8");
    // 出力先となるPrintWriterを取得
    PrintWriter out = response.getWriter();
    /* ここから */
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
    out.println("<th>id</th><th>name</th><th>point</th><th>joinDate</th>");
    out.println("</tr>");
    out.println("</thead>");
    out.println("<tbody>");
    for (Member m : members) {
	long dateM = m.getJoinDate().getTime();
        long now = System.currentTimeMillis();
        out.print("<tr>");
	out.print("<td>" + m.getId() + "</td>");
	out.print("<td>" + m.getName() + "</td>");
	out.print("<td>" + m.getPoints() + "</td>");
	out.print("<td>" + m.getJoinDate() + "</td>");
        out.print("</tr>");
    }
    out.println("</tbody>");
    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
    /* ここまで */
  }
}
