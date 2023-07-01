package cache;


public interface ICommonVO /*extends IPrimaryKey*/ {


  String TS = "ts";

  /**
   * 数据库时间戳字段
   *
   * @return
   */
  default String dbTsField() {
    return TS;
  }

  /**
   * 数据库表明
   *
   * @return 数据库表明
   */
  String dbTableName();


  /**
   * 获取对象中的时间戳
   *
   * @return 时间戳
   */
  String getTs();
}

