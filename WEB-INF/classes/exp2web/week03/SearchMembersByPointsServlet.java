package exp2web.week03;

import java.io.IOException;
import java.sql.Connection;
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
 * ポイント数で検索する
 */
// このサーブレットを呼び出すURLを指定する
@WebServlet("/SearchMembersByPoints")
public class SearchMembersByPointsServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 検索条件を取得する
    String minPointsStr = request.getParameter("minPoints");
    String maxPointsStr = request.getParameter("maxPoints");

    if ((minPointsStr != null) || (maxPointsStr != null)) {
      // 検索条件の指定があれば，検索処理を行なう．なければJSPにそのまま転送する

      // 検索条件設定エラー用のメッセージを用意しておく
      List<String> errorMessages = new ArrayList<String>();
      
      // 下限の値
      // デフォルトの値
      int minPoints = Integer.MIN_VALUE;
      if (minPointsStr != null) {
        minPointsStr = minPointsStr.trim();
        // 検索条件が指定されていれば，設定
        if (minPointsStr.length() > 0) {
          try {
            minPoints = Integer.parseInt(minPointsStr.trim());
          } catch (NumberFormatException e) {
            // 書式エラーであればエラーメッセージに追加する
            errorMessages.add("ポイント数下限の書式エラー");
          }
        }
      }

      // 上限の値
      // デフォルトの値
      int maxPoints = Integer.MAX_VALUE;
      if (maxPointsStr != null) {
        maxPointsStr = maxPointsStr.trim();
        // 検索条件が指定されていれば，設定
        if (maxPointsStr.length() > 0) {
          try {
            maxPoints = Integer.parseInt(maxPointsStr.trim());
          } catch (NumberFormatException e) {
            // 書式エラーであればエラーメッセージに追加する
            errorMessages.add("ポイント数上限の書式エラー");
          }
        }
      }

      if (!errorMessages.isEmpty()) {
        // エラーメッセージがあればJSPに渡す
        request.setAttribute("errorMessages", errorMessages);
      } else {
        // 検索を行う
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
	  String sql = "SELECT * FROM members WHERE ポイント数　>= ? AND ポイント数 <= ?";
	  PreparedStatement ps = conn.prepareStatement(sql);
	  ps.setInt(1, minPoints);
	  ps.setInt(2, maxPoints);
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
          // エラーの時はExceptionを投げる
          throw new ServletException(e);
        }
      }
    }
    // JSPに転送する
    /* ここから */
    RequestDispatcher dispatcher =
        request.getRequestDispatcher("/WEB-INF/jsp/SearchMembersByPoints.jsp");
    dispatcher.forward(request, response);


    /* ここまで */
  }
}