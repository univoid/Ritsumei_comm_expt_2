package exp2web.week04;

import java.sql.Date;

/** 
 * 第４週提出課題オプション用
 * 会員検索の絞り込み・並べ替え条件を表現する
 * ポイント数と加入日についてはユーザが入力した文字列もあわせて保持する
 * （自由に修正して使用して構わない）
 */
public class MembersListConditions {
  // 絞り込み・並べ替え条件のデフォルト
  private static String DEFAULT_NAME = "";
  private static int DEFAULT_MIN_POINTS = Integer.MIN_VALUE;
  private static int DEFAULT_MAX_POINTS = Integer.MAX_VALUE;
  private static Date DEFAULT_FROM_DATE = new Date(0L); // 1970-01-01;
  private static Date DEFAULT_TO_DATE = Date.valueOf("2999-12-31");
  private static String DEFAULT_ORDER_BY = null;
  private static String DEFAULT_ORDER = "";
  
  // 初期値としてデフォルトの値を設定しておく
  private String name = DEFAULT_NAME;
  private int minPoints = DEFAULT_MIN_POINTS;
  private int maxPoints = DEFAULT_MAX_POINTS;
  private Date fromDate = DEFAULT_FROM_DATE;
  private Date toDate = DEFAULT_TO_DATE;
  private String orderBy = DEFAULT_ORDER_BY;
  private String order = DEFAULT_ORDER;
  // ユーザが入力した文字列を保持しておく
  private String minPointsStr = "";
  private String maxPointsStr = "";
  private String fromDateStr = "";
  private String toDateStr = "";
  
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    // 一旦，デフォルト値を設定する
    this.name = DEFAULT_NAME;
    if (name != null) {
      this.name = name;
    }
  }  

  /**
   * @return the minPoints
   */
  public int getMinPoints() {
    return minPoints;
  }

  /**
   * @return the minPointsStr
   */
  public String getMinPointsStr() {
    return minPointsStr;
  }
  
  /**
   * @param minPointsStr the minPointsStr to set
   * @throws NumberFormatException 
   */
  public void setMinPointsStr(String minPointsStr) throws NumberFormatException {
    // 一旦，デフォルト値を設定する
    this.minPoints = DEFAULT_MIN_POINTS;
    this.minPointsStr = minPointsStr;
    if (minPointsStr != null) {
      minPointsStr = minPointsStr.trim();
      if (minPointsStr.length() > 0) {
        this.minPoints = Integer.parseInt(minPointsStr);
      }
    }
  }

  /**
   * @return the maxPoints
   */
  public int getMaxPoints() {
    return maxPoints;
  }

  /**
   * @return the maxPointsStr
   */
  public String getMaxPointsStr() {
    return maxPointsStr;
  }

  /**
   * @param maxPointsStr the maxPointsStr to set
   */
  public void setMaxPointsStr(String maxPointsStr) {
    // 一旦，デフォルト値を設定する
    this.maxPoints = DEFAULT_MAX_POINTS;
    this.maxPointsStr = maxPointsStr;
    if (maxPointsStr != null) {
      maxPointsStr = maxPointsStr.trim();
      if (maxPointsStr.length() > 0) {
        this.maxPoints = Integer.parseInt(maxPointsStr);
      }
    }
  }

  /**
   * @return the fromDate
   */
  public Date getFromDate() {
    return fromDate;
  }

  /**
   * @return the fromDateStr
   */
  public String getFromDateStr() {
    return fromDateStr;
  }

  /**
   * @param fromDateStr the fromDateStr to set
   */
  public void setFromDateStr(String fromDateStr) {
    // 一旦，デフォルト値を設定する
    this.fromDate = DEFAULT_FROM_DATE;
    this.fromDateStr = fromDateStr;
    if (fromDateStr != null) {
      // 日付として扱えない時は，Exceptionが発生するが，Exceptionは呼び出し側で処理する
      if (fromDateStr.length() > 0) {
        this.fromDate = Date.valueOf(fromDateStr);
      }
    }
  }


  /**
   * @return the toDate
   */
  public Date getToDate() {
    return toDate;
  }
  
  /**
   * @return the toDateStr
   */
  public String getToDateStr() {
    return toDateStr;
  }

  /**
   * @param toDateStr the toDateStr to set
   */
  public void setToDateStr(String toDateStr) {
    // 一旦，デフォルト値を設定する
    this.toDate = DEFAULT_TO_DATE;
    this.toDateStr = toDateStr;
    if (toDateStr != null) {
      toDateStr = toDateStr.trim();
      // 日付として扱えない時は，Exceptionが発生するが，Exceptionは呼び出し側で処理する
      if (toDateStr.length() > 0) {
        this.toDate = Date.valueOf(toDateStr);
      }
    }
  }

  /**
   * @return the orderBy
   */
  public String getOrderBy() {
    return orderBy;
  }

  /**
   * @param orderBy the orderBy to set
   */
  public void setOrderBy(String orderBy) {
    this.orderBy = DEFAULT_ORDER_BY;
    if (orderBy != null) {
      if (("id".equals(orderBy)) || 
          ("ポイント数".equals(orderBy)) || ("加入日".equals(orderBy))) {
        this.orderBy = orderBy;
      } else {
        // エラーの場合はexceptionを発生させる
        throw new IllegalArgumentException();
      }
    }
  }

  /**
   * @return the order
   */
  public String getOrder() {
    return order;
  }

  /**
   * @param order the order to set (ASC or DESC)
   */
  public void setOrder(String order) {
    this.order = DEFAULT_ORDER;
    if (order != null) {
      if ("ASC".equals(order) || "DESC".equals(order)) {
        this.order = order;
      } else {
        // エラーの場合は
        throw new IllegalArgumentException();
      }
    }
  }

  @Override
  // デバッグ用にオブジェクトの内容を書き出す
  public String toString() {
    return String.format("[name: %s; minPoints: %s; maxPoints: %s; fromDate: %s; toDate: %s; orderBy: %s; order: %s; " +
        "minPointsStr: %s; maxPointsStr: %s; fromDateStr: %s; toDateStr: %s]",
        this.name, this.minPoints, this.maxPoints, this.fromDate, this.toDate, this.orderBy, this.order,
        this.minPointsStr, this.maxPointsStr, this.fromDateStr, this.toDateStr);
  }
}
