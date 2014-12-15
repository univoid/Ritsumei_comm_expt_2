package exp2web.week02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exp2web.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * 第２週提出課題
 * 会員の一覧を求める（並べ替え）
 */
// このサーブレットを呼び出すURLを指定する
@WebServlet("/ShowMembers")
public class ShowMembersServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    /** データベースからデータを取得する  */
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
      /* ここから */
	Statement stmt = conn.createStatement();
	String sql = "SELECT * FROM members ";
	//query string
	String orderBy = request.getParameter("orderBy");
	String order = request.getParameter("order");

 	if (orderBy != null) {
	  if ("ポイント数".equals(orderBy) ||
	      "加入日".equals(orderBy) ||
	      "id".equals(orderBy)) {
	    sql = sql + "ORDER BY " + orderBy + 
	     (("ASC".equals(order) || "DESC".equals(order))? " " + order : "");
	  }
	}
	
	String pointsThresholdStr = request.getParameter("pointsThreshold");
	if (pointsThresholdStr != null) {
	  pointsThresholdStr = pointsThresholdStr.trim();
	  if (pointsThresholdStr.length() > 0) {
	    try {
		int point = Integer.parseInt(pointsThresholdStr);
		request.setAttribute("pointsThreshold", point);
	    } catch (NumberFormatException ignore) {
	    }
	  }
	} 
	ResultSet rs = stmt.executeQuery(sql);
	while (rs.next()) {
	    Member member = new Member();

	    member.setId(rs.getInt("id"));
	    member.setName(rs.getString("氏名"));
	    member.setPoints(rs.getInt("ポイント数"));
	    member.setJoinDate(rs.getDate("加入日"));

	    members.add(member);
	}
	rs.close();
  	stmt.close();
      /* ここまで */
    } catch (SQLException e) {
      // エラーの時はExceptionを投げる
      throw new ServletException(e);
    }
    // connは自動的にcloseされる
    
    // 検索結果をリクエストスコープのresult属性に保持し，JSPに渡す
    request.setAttribute("result", members);
    /* JSPへ転送するコードを追加する */
    /* ここから */
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ShowMembers.jsp");
    dispatcher.forward(request, response);


    /* ここまで */
  }
}
