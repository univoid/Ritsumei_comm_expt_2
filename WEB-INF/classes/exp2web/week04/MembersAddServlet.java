package exp2web.week04;

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
// オプション課題で使用
import javax.servlet.http.HttpSession;

/**
 * 会員の追加
 */
@WebServlet("/Members/add")
public class MembersAddServlet extends HttpServlet {
  
  /**
   * 会員追加の画面を表示する
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    // 氏名の初期値の設定
    request.setAttribute("nameToEdit", "");
    // ポイント数の初期値の設定
    request.setAttribute("pointsToEdit", 0);
    // 今日の日付を取得する
    Date date = new Date(System.currentTimeMillis());
    // 加入日の初期値として設定する
    request.setAttribute("joinDateToEdit", date.toString());
    // JSPに転送する
    RequestDispatcher dispatcher = 
        request.getRequestDispatcher("/WEB-INF/jsp/MembersAdd.jsp");
    dispatcher.forward(request, response);
  }
  
  /**
   * 実際に会員を追加する
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // リクエストの文字コードを設定する
    request.setCharacterEncoding("UTF-8");

    // 入力データの取り出し
    String nameStr = request.getParameter("name");
    String pointsStr = request.getParameter("points");
    String joinDateStr = request.getParameter("joinDate");
    // 結果のメッセージ
    String resultMessage = "";
    // エラーメッセージを初期化しておく
    List<String> errorMessages = new ArrayList<String>();

    // 氏名
    String name = null;
    if (nameStr == null) {
      errorMessages.add("氏名がありません");
    } else {
      // 名前：前後の空白を取り除く
      name = nameStr.trim();
      // 名前がなければ，エラーメッセージを加える
      if (name.length() == 0) {
        errorMessages.add("氏名がありません");
      }
    }

    // ポイント数
    int points = 0;
    if (pointsStr == null) {
      errorMessages.add("ポイント数がありません");
    } else {
      try {
        points = Integer.parseInt(pointsStr.trim());
      } catch (NumberFormatException e) {
        // 数字に変換できなければ，エラーメッセージに付け加える
        errorMessages.add("ポイント数の書式エラー");
      }
    }
    
    // 加入日
    Date joinDate = null;
    if (joinDateStr == null) {
      errorMessages.add("加入日がありません"); 
    } else {
      try {
        joinDate = Date.valueOf(joinDateStr.trim());
      } catch (IllegalArgumentException e) {
        errorMessages.add("加入日の書式エラー");
      }
    }

    // エラーメッセージがあるかチェック
    if (!errorMessages.isEmpty()) {
      // エラーがあれば，エラーメッセージをJSPに渡す
      request.setAttribute("errorMessages", errorMessages);
      // 入力された値を入力画面の初期値として渡す
      request.setAttribute("nameToEdit", nameStr);
      request.setAttribute("pointsToEdit", pointsStr);
      request.setAttribute("joinDateToEdit", joinDateStr);
      // 入力画面のJSPに転送することにする
      RequestDispatcher dispatcher = 
          request.getRequestDispatcher("/WEB-INF/jsp/MembersAdd.jsp");
      dispatcher.forward(request, response);
    } else {
      // エラーがなければ，データベースにデータを追加する
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
	String sql = "INSERT INTO members(氏名,ポイント数,加入日) VALUES(?, ?, ?)";
	
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, name);
	ps.setInt(2, points);
	ps.setDate(3, joinDate);
	int result = ps.executeUpdate();
	ps.close();

	resultMessage = "会員追加：データベース更新件数　" + result;

        /* ここまで */
      } catch (SQLException e) {
        throw new ServletException(e);
      }
      // 提出課題でリダイレクトする場合は，この部分はコメントアウトする
      // // 結果表示画面のJSPに転送する
      // request.setAttribute("message", resultMessage);
      // RequestDispatcher dispatcher = 
      //     request.getRequestDispatcher("/WEB-INF/jsp/MembersList.jsp");
      // dispatcher.forward(request, response);
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
}
