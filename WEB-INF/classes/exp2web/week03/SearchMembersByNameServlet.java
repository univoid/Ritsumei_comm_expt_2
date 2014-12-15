package exp2web.week03;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
// バージョン2
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// バージョン1
import java.sql.Statement;
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
 * 名前で検索する
 */
// サーブレットを呼び出すURLを指定する
@WebServlet("/SearchMembersByName")
public class SearchMembersByNameServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 検索条件を取得する
    String nameStr = request.getParameter("name");
    
    if (nameStr != null) {
      // 検索条件の指定があれば，検索処理を行なう．なければJSPにそのまま転送する(検索条件のみ表示させる)．
      // 検索語の前後の空白を取り除く
      nameStr = nameStr.trim();

      // 検索を開始する
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
        // バージョン2では，PreparedStatementを使用するように書き換える

        // バージョン2 PreparedStatementを使用する
        // 上記の ここから(バージョン1) ここまで(バージョン1)の部分は削除する
        /* ここから (バージョン2) */
	String sql = "SELECT * FROM members WHERE 氏名 LIKE ?";
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, "%" + nameStr + "%");
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
        /* ここまで (バージョン2) */
      } catch (SQLException e) {
        // エラーの時はExceptionを投げる
        throw new ServletException(e); 
      }
    }
    // JSPに転送する
    RequestDispatcher dispatcher =
        request.getRequestDispatcher("/WEB-INF/jsp/SearchMembersByName.jsp");
    dispatcher.forward(request, response);
  }
}