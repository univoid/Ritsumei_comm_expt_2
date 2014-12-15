package exp2web.auth;

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

/*
 * デバッグ用：ユーザデータベースをリセットする（初期状態に戻す）
 * ユーザデータベースはログイン認証において使用
 */
@WebServlet("/UsersDBReset")
public class UsersDBResetServlet extends HttpServlet {

  // POSTメソッドを呼び出すHTMLを返す
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    // ブラウザに結果を表示する．
    // ここでは，JSPを使わずにそのままHTMLを生成する．
    // contentTypeを設定する
    response.setContentType("text/html;charset=UTF-8");
    // 出力先となるPrintWriterを取得
    PrintWriter out = response.getWriter();
    // HTMLの内容をoutに出力していく
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    // HTMLのヘッダ部分
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    // タイトルの設定
    out.println("<title>ユーザデータベースを初期状態に戻す</title>");
    out.println("</head>");
    // HTMLのボディ部分
    out.println("<body>");
    out.println("<form method=\"POST\" action=\"" + request.getContextPath() +
        "/UsersDBReset\">");
    out.println("<input type=\"submit\" value=\"認証機能テスト用のユーザデータベースを初期状態に戻す(" + request.getContextPath() +
        ")\">");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {

    // Webアプリのリロード時にドライバがunloadされた場合に備えて改めてドライバをロードする
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new ServletException(e);
    }
    // データベースファイルの実際の場所を求める
    String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
    try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
          + ";ifexists=true", "sa", "")) {
      Statement stmt = conn.createStatement();
      // テーブルを一旦削除する
      String sql = "DROP TABLE IF EXISTS users";
      stmt.executeUpdate(sql);
      System.out.println("Table users dropped.");
      // テーブルを作成し直す
      sql = "CREATE TABLE users(name VARCHAR PRIMARY KEY, password VARCHAR)";
      stmt.executeUpdate(sql);
      System.out.println("Table users created.");
      stmt.close();
      
      // 初期データの作成
      List<User> users= new ArrayList<User>();
      // name, password
      users.add(new User("user01", "pass01"));
      
      // PreparedStatementを使用して，データを追加していく．
      sql = "INSERT INTO users(name, password) VALUES(?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      for (User u : users) {
        ps.setString(1, u.getName());
        ps.setString(2, u.getPassword());
        ps.executeUpdate();
        // 一旦パラメータをクリアする
        ps.clearParameters();
      }
      ps.close();
    } catch (SQLException e) {
      throw new ServletException(e);
    }
    // ここでは，JSPを使わずにそのままHTMLを生成する．
    // contentTypeを設定する
    response.setContentType("text/html;charset=UTF-8");
    // 出力先となるPrintWriterを取得
    PrintWriter out = response.getWriter();
    // HTMLの内容をoutに出力していく
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    // HTMLのヘッダ部分
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    // タイトルの設定
    out.println("<title>UsersDB Reset</title>");
    out.println("</head>");
    // HTMLのボディ部分
    out.println("<body>");
    out.println("<p>ユーザデータベースは初期状態にリセットされました</p>");
    out.println("</body>");
    out.println("</html>");
  }
}