package exp2web.week04;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * 会員情報の更新（編集）
 */
@WebServlet("/Members/update/*")
public class MembersUpdateServlet extends HttpServlet {
  /**
   * 会員情報の編集画面を表示する
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    // URLから編集対象のidを求める
    // 例えば，URL: "/Members/update/1"--> pathInfo: "/1"
    int id = 0;
    try {
      id = Integer.parseInt(request.getPathInfo().substring(1));
    } catch (Exception e) {
      // 何らかの例外が起きたら ServletExceptionとして扱う
      throw new ServletException(e);
    }
    
    // idの会員の情報を取得する
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
      /* ここから */
	String sql = "SELECT * FROM members WHERE id = ?";
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setInt(1, id);
	ResultSet rs = ps.executeQuery();
	while (rs.next()) {
	  request.setAttribute("id", id);
          // 氏名の初期値の設定
          request.setAttribute("nameToEdit", rs.getString("氏名"));
          // ポイント数の初期値の設定
          request.setAttribute("pointsToEdit", rs.getInt("ポイント数"));
          // 加入日の初期値として設定する
          request.setAttribute("joinDateToEdit", rs.getDate("加入日"));
	}
	rs.close();
	ps.close();

      /* ここまで */
    } catch (SQLException e) {
      throw new ServletException(e);
    }
    // 編集画面のJSPに転送する．
    RequestDispatcher dispatcher = 
        request.getRequestDispatcher("/WEB-INF/jsp/MembersUpdate.jsp");
    dispatcher.forward(request, response);
  }
  
  /*
   * 会員情報の更新
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    // リクエストのパラメータの文字コードの設定
    request.setCharacterEncoding("UTF-8");

    // URLから削除対象のidを求める
    int id = 0;
    try {
      id = Integer.parseInt(request.getPathInfo().substring(1));
    } catch (Exception e) {
      // 何らかの例外が起きたら ServletExceptionとして扱う
      throw new ServletException(e);
    }

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
      // 前後の空白を取り除く
      name = nameStr.trim();
      if (name.equals("")) {
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
    
    if (!errorMessages.isEmpty()) {
      // 入力エラー．入力した値をもう一度渡し，再入力を促す．
      request.setAttribute("id", id);
      request.setAttribute("nameToEdit", nameStr);
      request.setAttribute("pointsToEdit", pointsStr);
      request.setAttribute("joinDateToEdit", joinDateStr);
      request.setAttribute("errorMessages", errorMessages);
      // 入力画面のJSPに転送する
      RequestDispatcher dispatcher = 
          request.getRequestDispatcher("/WEB-INF/jsp/MembersUpdate.jsp");
      dispatcher.forward(request, response);
    } else {
      // エラーがなければ，データベースを更新する
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
        /* ここから */
        // 実際に更新処理を行う
	String sql = "UPDATE members SET 氏名 = ?, ポイント数 = ?, 加入日 = ? WHERE id = ?";
	
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, name);
	ps.setInt(2, points);
	ps.setDate(3, joinDate);
	ps.setInt(4, id);
	int result = ps.executeUpdate();
	ps.close();

	resultMessage = "会員更新：データベース更新件数　" + result;

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
}