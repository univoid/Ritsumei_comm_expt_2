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
import javax.servlet.http.HttpSession;

import exp2web.Member;

/**
 * 課題4-2
 * 第４週提出課題:会員の 一覧表示
 */
@WebServlet("/Members/list")
public class MembersListServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {

    /* オプション課題 */
    /* ここから */
    // 入力パラメータの処理
    HttpSession session = request.getSession();
    request.setAttribute("message", session.getAttribute("message"));
    request.setAttribute("user", session.getAttribute("user"));
    session.removeAttribute("message");
    // 検索条件を取得する

    String nameStr = request.getParameter("name");

    String minPointsStr = request.getParameter("minPoints");
    String maxPointsStr = request.getParameter("maxPoints");
    
    String fromDateStr = request.getParameter("fromDate");
    String toDateStr = request.getParameter("toDate");
    // オプション
    // 並べ替えの条件 
    // 並べ替えの基準となるカラム
    String orderByStr = request.getParameter("orderBy");
    // 昇順(ASC)か降順(DESC)かの指定
    String orderStr = request.getParameter("order");
    
    // 必要に応じてパラメータを追加する
    
    // if ((nameStr != null) || (minPointsStr != null) || (maxPointsStr != null) ||
    //     (fromDateStr != null) || (toDateStr != null) || (orderByStr != null) ||
    //     (orderStr != null)) {
      // 検索条件の指定があれば，検索処理を行なう．なければJSPにそのまま転送する．
      // その場合は検索結果の表示を行わない

      // 検索条件のチェック
      // 検索条件設定エラー用のメッセージを用意しておく

      List<String> errorMessages = new ArrayList<String>();
      /* ここから */
      /*name*/
      if (nameStr != null) {
        nameStr = nameStr.trim();
      }
      /*points*/
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

      /*joinDate*/
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
      /* ここまで*/

      if (!errorMessages.isEmpty()) {
        // エラーがあれば，JSPに渡す
        request.setAttribute("errorMessages", errorMessages);
      } else {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      throw new ServletException(e);
    }
    // データベースファイルの実際の場所を求める
    String dbfile = getServletContext().getRealPath("/WEB-INF/db/dbfile");
    try (Connection conn = DriverManager.getConnection("jdbc:h2:file:" + dbfile +
        ";ifexists=true", "sa", "")) {
      
      /* ここから */
      // 提出課題で絞り込み・並べ替えをする場合は必要に応じて修正する
	  String sql = "SELECT * FROM members" + 
	  " WHERE (氏名 LIKE ?)" + 
	  " AND (ポイント数 >= ?) AND (ポイント数 <= ?)" + 
	  " AND (加入日 >= ?) AND (加入日　<= ?)";
 	  if (orderByStr != null) {
	    if ("ポイント数".equals(orderByStr) ||
	      "加入日".equals(orderByStr) ||
	      "id".equals(orderByStr)) {
	      sql = sql + "ORDER BY " + orderByStr + 
	      (("ASC".equals(orderStr) || "DESC".equals(orderStr))? " " + orderStr : "");
	    }
	  }
	  	PreparedStatement ps = conn.prepareStatement("SELECT * FROM members");
	  	if (nameStr != null) {
	  	  ps = conn.prepareStatement(sql);
	  		ps.setString(1, "%" + nameStr + "%");
	  		ps.setInt(2, minPoints);
	  		ps.setInt(3, maxPoints);
	  		ps.setDate(4, fromDate);
	  		ps.setDate(5, toDate);
	  	}
	  	

 	  ResultSet rs = ps.executeQuery();
	  List<Member> members = new ArrayList<Member>();
      while (rs.next()) {
        // 一つのResultに対してMemberのオブジェクトを作成する
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("氏名"));
        member.setPoints(rs.getInt("ポイント数"));
        member.setJoinDate(rs.getDate("加入日"));
        members.add(member);
      }
      // JSPに値を渡すためresult属性にセットする
      request.setAttribute("result", members);
      rs.close();
      ps.close();
      /* ここまで */
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
// }
    // JSPに転送する
    /* ここから */
    RequestDispatcher dispatcher = 
        request.getRequestDispatcher("/WEB-INF/jsp/MembersList.jsp");
    dispatcher.forward(request, response);
    /* ここまで */
  }
}