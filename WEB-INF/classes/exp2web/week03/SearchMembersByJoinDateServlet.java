package exp2web.week03;

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

import exp2web.Member;

/*
 * 加入日で検索する
 */
@WebServlet("/SearchMembersByJoinDate")
public class SearchMembersByJoinDateServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 検索条件を取得する
    String fromDateStr = request.getParameter("fromDate");
    String toDateStr = request.getParameter("toDate");

    if ((fromDateStr != null) || (toDateStr != null)) {
      // 検索条件の指定があれば，検索処理を行なう．なければJSPにそのまま転送する．

      // 検索条件設定エラー用のメッセージを用意しておく
      List<String> errorMessages = new ArrayList<String>();

      // 検索条件の処理（下限値）
      // デフォルトの最初の日付
      Date fromDate = new Date(0L); // 1970-01-01
      if (fromDateStr != null) {
        fromDateStr = fromDateStr.trim();
        // 入力があるかチェック
        if (fromDateStr.length() > 0) {
          try {
            // 書式が違えばIllegalArgumentExceptionが発生
            fromDate = Date.valueOf(fromDateStr);
          } catch (IllegalArgumentException e) {
            errorMessages.add("最初の日の書式エラー");
          }
        }
      }

      // 検索条件の処理（上限値）
      // デフォルトの最後の日付（十分先の未来に設定する）
      Date toDate = Date.valueOf("2999-12-31");
      if (toDateStr != null) {
        toDateStr = toDateStr.trim();
        // 入力があるかチェック
        if (toDateStr.length() > 0) {
          try {
            toDate = Date.valueOf(toDateStr);
          } catch (IllegalArgumentException e) {
            errorMessages.add("最後の日の書式エラー");
          }
        }
      }

      if (!errorMessages.isEmpty()) {
        // エラーがあれば，JSPに渡す
        request.setAttribute("errorMessages", errorMessages);
      } else {
        // 検索を開始する
        // データベースファイルの実際の場所を求める
        String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
        // Webアプリのリロード時にドライバがunloadされた場合に備えて改めてドライバをロードする
        try {
          Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
          throw new ServletException(e);
        }
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile
              + ";ifexists=true", "sa", "")) {
          /* ここから */
	  String sql = "SELECT * FROM members WHERE 加入日 >= ? AND 加入日 <= ?";
	  PreparedStatement ps = conn.prepareStatement(sql);
	  ps.setDate(1, fromDate);
	  ps.setDate(2, toDate);
	  ResultSet rs = ps.executeQuery();

	  List<Member> members = new ArrayList<Member>();
          while (rs.next()) {
            // 各会員ごとにMemberクラスのインスタンスを作成
            Member member = new Member();
            member.setId(rs.getInt("id"));
            member.setName(rs.getString("氏名"));
            member.setPoints(rs.getInt("ポイント数"));
            member.setJoinDate(rs.getDate("加入日"));
            // 検索結果一覧に追加する
            members.add(member);
          }
          // 検索結果をresult属性に保持させる
          request.setAttribute("result", members);
          rs.close();
  	  ps.close();

          /* ここまで */
        } catch (SQLException e) {
          // ServletExceptionとしてthrowしなおす
          throw new ServletException(e);
        }
      }
    }
    // 検索結果をJSPに転送する
    /* ここから */
    RequestDispatcher dispatcher =
        request.getRequestDispatcher("/WEB-INF/jsp/SearchMembersByJoinDate.jsp");
    dispatcher.forward(request, response);


    /* ここまで */
  }
}