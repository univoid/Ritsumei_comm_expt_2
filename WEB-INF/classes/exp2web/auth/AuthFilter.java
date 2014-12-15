package exp2web.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * フィルタを使ったカスタム認証の例
 */
// 認証を有効にする場合は以下のアノテーションのコメントを外して，リロードする
// 追加，削除，更新の場合にログインを必要とする場合
//@WebFilter({"/Members/add", "/Members/delete/*", "/Members/update/*"})
// 一覧もログインを必要とする場合
@WebFilter({"/Members/list", "/Members/add", "/Members/delete/*", "/Members/update/*"})
public class AuthFilter implements Filter {

  /**
   * @see Filter#destroy()
   */
  @Override
  public void destroy() {
  }
  
  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
      throws IOException, ServletException {
    try {
      String target = ((HttpServletRequest) request).getRequestURI();
      // queryStringも含めて記録しておく
      String queryString = ((HttpServletRequest) request).getQueryString();
      if (queryString != null) {
        target = target + "?" + queryString;
      }
      String contextPath = ((HttpServletRequest) request).getContextPath();
      String loginURI = contextPath + "/Login";
      String method =  ((HttpServletRequest) request).getMethod();

      HttpSession session = ((HttpServletRequest) request).getSession();
      // ユーザ認証の結果はsession属性に保持されている
      User user = (User) session.getAttribute("user");
      // 動作確認用にユーザ名とリダイレクト先を出力する
      System.out.println("doFilter: user: " + user + "; (" + method + ") " + target);
      if (user == null) {
        // login していない場合は，loginの画面にredirectする
        // この時，もともとのtargetをsession属性に保持しておく
        session.setAttribute("target", target);
        ((HttpServletResponse) response).sendRedirect(loginURI);
        // 動作確認用にリダイレクト先を出力する
        System.out.println("redirecting: " + loginURI);
        // ここで処理を終わりにする
        return;
      }
      

    } catch (Exception e) {
      throw new ServletException(e);
    }
    // pass the request along the filter chain
    chain.doFilter(request, response);
  }

  /**
   * @see Filter#init(FilterConfig)
   */  
  @Override
  public void init(FilterConfig arg) throws ServletException {
  }
}
