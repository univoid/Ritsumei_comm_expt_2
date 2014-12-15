package exp2web.week04;

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
// オプション課題で使用
import javax.servlet.http.HttpSession;

import exp2web.Member;

/**
 * 会員の削除
 */
@WebServlet("/Members/delete/*")
public class MembersDeleteServlet extends HttpServlet {
  
  /**
   * 削除確認画面の表示する
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // URLから削除対象のidを求める
    // 例えば，URL: "/Members/delete/1"--> pathInfo: "/1"
    int id = 0;
    try {
      id = Integer.parseInt(request.getPathInfo().substring(1));
    } catch (Exception e) {
      // 何らかの例外が起きたら ServletExceptionとして扱う
      throw new ServletException(e);
    }
    
    System.out.println("MembersDeleteServlet (GET) " + id);
    // idの会員の情報を取得する
    // Webアプリのリロード時にドライバがunloadされた場合に備えて改めてロードする
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new ServletException(e);
    }
    // データベースファイルの実際の場所を求める
    String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
    try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
          + ";ifexists=true", "sa", "")) {
      // 削除対象の会員情報を取得する
      String sql = "SELECT * FROM members WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      
      // データベースから検索結果を取得
      if (rs.next()) {
        // 検索結果をMemberクラスのインスタンスとして渡す
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("氏名"));
        member.setPoints(rs.getInt("ポイント数"));
        member.setJoinDate(rs.getDate("加入日"));
        request.setAttribute("memberToDelete", member);
      }
      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new ServletException(e);
    }
    // 削除確認画面のJSPに転送する．
    RequestDispatcher dispatcher = 
        request.getRequestDispatcher("/WEB-INF/jsp/MembersDelete.jsp");
    dispatcher.forward(request, response);
  }
  
  /**
   * 削除の実行
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // URLから削除対象のidを求める
    // 例えば，URL: "/Members/delete/1"--> pathInfo: "/1"
    int id = 0;
    try {
      id = Integer.parseInt(request.getPathInfo().substring(1));
    } catch (Exception e) {
      // 何らかの例外が起きたら ServletExceptionとして扱う
      throw new ServletException(e);
    }
    System.out.println("MembersDeleteServlet (POST) " + id);
    // idの会員を削除する
    // 結果のメッセージを初期化する
    String resultMessage = "";
    // Webアプリのリロード時にドライバがunloadされた場合に備えて改めてロードする
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new ServletException(e);
    }
    // データベースファイルの実際の場所を求める
    String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
    try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
          + ";ifexists=true", "sa", "")) {
      /* ここから */
      // 実際に削除処理を行う
	    String sql = "DELETE FROM members WHERE id = ?";
	
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1,id);
	    int result = ps.executeUpdate();
    	ps.close();

    	resultMessage = "会員削除：データベース更新件数　" + result;



      /* ここまで */
    } catch (SQLException e) {
      throw new ServletException(e);
    }
    // 提出課題でリダイレクトする場合は，この部分はコメントアウトする
    // 更新結果表示画面のJSPに転送する
    request.setAttribute("message", resultMessage); 
    RequestDispatcher dispatcher = 
        request.getRequestDispatcher("/WEB-INF/jsp/MembersList.jsp");
    dispatcher.forward(request, response);
    // 提出課題用：リダイレクトする
    // redirect先でも値を引き継ぐようにセッションスコープに値を入れておく
    /* ここから */
    HttpSession session = request.getSession();
    session.setAttribute("message", resultMessage);

    String redirectUrl = request.getContextPath() + "/Members/list";
    response.sendRedirect(redirectUrl);

    /* ここまで */
  }
}
