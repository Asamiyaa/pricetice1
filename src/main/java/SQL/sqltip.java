package SQL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import reactor.util.function.Tuple3;

/**
 *   sql写法技巧
 */
public class sqltip {



  /**
   * 1.使用初始化程序将数据load进去  模型：https://sqlflow.gudusoft.com/#/
   * 2. - tableDesign、meta + 表规范(技巧)
   *              0.meta 比如合规规则对字段拆解
   *              1.createtime 、ts 、非功能需求
   *              2.冗余   https://www.zhihu.com/question/50662784
   *              3.原则：https://www.sohu.com/a/333231147_114819     http://runxinzhi.com/youcong-p-14932845.html
   *              4.时间类型   string、datetime\timestamp \   金额类型：bigdecimal / ...精度
   *
   *    - sql ,哪些逻辑写在sql、哪些在代码中、函数(计算、distinct...replace into... )、(子查询)(表连接)、一对多(和返回字段有关 - mybatis映射)、行转列 、 --> 图 、
   *              1.如何编写复杂sql : https://cloud.tencent.com/developer/article/1033405
   *              2.对 null 、空值 处理 ， 分组key为null
   *              3.逻辑与函数： update A replace(A,b) where
   *                            set x = 子句 where exist .....
   *
   *                            sql中除法：https://blog.csdn.net/u011446177/article/details/53456280
   *
   *                            行转列：https://www.toutiao.com/article/7067138670272496166/?log_from=7b78de44a3681_1672629657820
   *
   *                            join大小表傻傻分不清楚～那就看过来吧:https://juejin.cn/post/7034536186614186014  https://blog.csdn.net/CSDN_WYL2016/article/details/120292878
   *
   *                            SQL条件语句(IF, CASE WHEN, IF NULL) : https://www.jianshu.com/p/d5b2f590858f    https://www.cnblogs.com/richardzhu/p/3571670.html
   *
   *                            coalesce函数详解:https://blog.csdn.net/yilulvxing/article/details/86595725
   *
   *                            You can't specify target table for update in FROM clause：https://blog.csdn.net/u012767761/article/details/84997962
   *
   *                            sql去重复操作详解SQL中distinct的用法：https://blog.csdn.net/boss2967/article/details/79019467
   *                            distinct 多列详解:https://blog.csdn.net/bitcarmanlee/article/details/51100279
   *
   *                            插入null,导致查询等操作时索引失效
   *
   *                            多表连接查询和多次单表查询抉择：https://blog.csdn.net/weixin_37545216/article/details/112413597
   *
   *                            replace into : https://blog.csdn.net/risingsun001/article/details/38977797
   *
   *                            SQL左右连接中的on and和on where的区别：https://www.jianshu.com/p/65f4026a93de
   *
   *                            sql中得group by和列：https://zhuanlan.zhihu.com/p/357004104
   *
   *                            exist:https://blog.csdn.net/bengbuguang4321/article/details/108124129
   *
   *                            distinct去重多个字段_sql-distinct怎么对其中某列去重还保留另外的列:https://codeantenna.com/a/f183mweSi0
   *
   *                 4.同时更新，从已知字段
   *                   update billcenter_summary bc,busi_collection c  set bc.ytenant_id = c.corpid
   * where bc.bill_id in (
   * select bid from (
   * select b.bill_id as bid  from billcenter_summary b
   * left join busi_collection c on b.bill_id = c.pk_invoice
   * where b.src_channel = 'email'
   * and b.id > 1660932248294653962
   * and b.ytenant_id = ''
   * ) as tmp
   * )
   *
   * update invoice i,billcenter_summary b  set i.ytenant_id = b.ytenant_id
   * where b.bill_id in (
   * select bid from (
   * select b.bill_id as bid  from billcenter_summary b
   * left join busi_collection c on b.bill_id = c.pk_invoice
   * where b.src_channel = 'email'
   * and b.id > 1660932248294653962
   * and b.ytenant_id = ''
   * ) as tmp)
   *
   * update busi_collection bc set bc.ytenant_id = bc.corpid
   * where bc.pk_invoice in
   * (
   * select b.bill_id
   * from billcenter_summary b
   * where b.src_channel = 'email'
   * and b.id > 1660932248294653962
   * )
   *
   *
   watch com.yonyou.einvoice.inputtax.api.InputUploadPdfClient receiveInvoiceByRecinvemail '{params,returnObj,@com.yonyou.iuap.context.InvocationInfoProxy@getTenantid()}'  -n 5  -x 3



   select * from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   left join invoice i on b.bill_id = i.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962 and b.ytenant_id = '' order by b.id desc;



   select CONCAT(bill_type,bill_code,bill_num)   , count(1) as cc from billcenter_summary b where b.id in (
   1661241314112438278,
   1661378168958222348
   ) group by CONCAT(bill_type,bill_code,bill_num) having cc > 1;



   select CONCAT(bill_code,bill_num) from billcenter_summary where id = 655351 limit 1;
   select * from billcenter_summary limit 1;


   update billcenter_summary b set b.ytenant_id = c.corpid  from billcenter_summary b ,busi_collection c
   where b.bill_id = c.pk_invoice
   and b.src_channel = 'email' and b.id > 1660932248294653962;


   select b.bill_id from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   left join invoice i on b.bill_id = i.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   and b.ytenant_id = ''


   ---
   update billcenter_summary bc,busi_collection c  set bc.ytenant_id = c.corpid
   where bc.bill_id in (
   select bid from (
   select b.bill_id as bid  from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   and b.ytenant_id = ''
   ) as tmp
   )

   update invoice i,billcenter_summary b  set i.ytenant_id = b.ytenant_id
   where b.bill_id in (
   select bid from (
   select b.bill_id as bid  from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   and b.ytenant_id = ''
   ) as tmp)

   update busi_collection bc set bc.ytenant_id = bc.corpid
   where bc.pk_invoice in
   (
   select b.bill_id
   from billcenter_summary b
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   )




   =---

   billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   left join invoice i on b.bill_id = i.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962



   select count(1) from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   left join invoice i on b.bill_id = i.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   and b.ytenant_id = ''



   select count(1) from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   left join invoice i on b.bill_id = i.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   and b.ytenant_id = ''

   select * from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   left join invoice i on b.bill_id = i.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962 order by b.id desc;
   and b.ytenant_id = ''

   ---billcenter_usmmary id
   select b.id from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   and b.ytenant_id = '' and b.bill_type = c.bill_type


   select count(1) from billcenter_summary b
   left join busi_collection c on b.bill_id = c.pk_invoice
   left join invoice i on b.bill_id = i.pk_invoice
   where b.src_channel = 'email'
   and b.id > 1660932248294653962
   and b.ytenant_id = ''



   3393





   ====删除重复数据
   select 1 from busi_collection bc left join

   (select bill_type , bill_code ,bill_num from billcenter_summary where id in (
   1661512481167638542,
   1661495979911151624,
   1661526938035421189,
   1661512481175502859,
   1661511879872217095,
   1661511888470016008,
   1661512051670908941,
   1661511536282697734,
   1661512043088838669,
   1661511991541366794,
   1661511931419688980,
   1661511922821890058,
   1661592350387339266,
   1661592341789540357,
   1661592298839867400,
   1661592307437666312,
   1661592316027600905,
   1661532787773014022,
   1661592333199605768,
   1661532779190943762,
   1661532779190943758,
   1661592316019736586,
   1661532779183079441,
   1661532779183079437,
   1661532770601009160,
   1661532779190943754,
   1661532779183079433,
   1661520392497397768,
   1661520366735458318,
   1661520426865000463,
   1661520495576612871,
   1661520143397158914,
   1661520289426046984,
   1661520126217289738,
   1661519963008532497,
   1661519902878990338,
   1661519859921453067,
   1661519670942892037,
   1661519662360821766,
   1661519619403284491,
   1661519232856227842,
   1661519610821214216,
   1661519104015073295,
   1661526980977229831,
   1661542511578972164,
   1662869922769272840,
   1663545048670666757,
   1663545125987942409,
   1663529277550755857,
   1663550228409090053,
   1663550228401225736,
   1661500695777378319,
   1663550236999024665,
   1663550228401225745,
   1661551264722321423,
   1662831293825548290,
   1662825487037628426,
   1662825461267824651,
   1662825478439829510,
   1662830606638645255,
   1662826122692788230,
   1663481285586190344,
   1663481423025143820,
   1663481380075470861,
   1663481328535863306,
   1661526173531242511,
   1663516933814747149,
   1663481405845274638,
   1663481319945928732,
   1663481423033008140,
   1663481337133662213,
   1663481345723596805,
   1663481388665405459,
   1663481388673269769,
   1663533100079513622,
   1663481388665405446,
   1663481405853138948,
   1663481328543727631,
   1663481337125797900,
   1663481345723596809,
   1663481380083335188,
   1663516942412546052,
   1663481431622942727,
   1663481285594054663,
   1663481345715732489,
   1663557710234255376,
   1663532077869432845,
   1661503393024704523,
   1663503756862947332,
   1663557710234255371,
   1663557710242119691,
   1663533117259382789,
   1663481294176124937,
   1663533100079513609,
   1663525772865306656,
   1661664935334641673,
   1663533108669448202,
   1663481302773923848,
   1663525772857442316,
   1662801014306111490
   ) ) as tmp
   on  bc.bill_type = tmp.bill_type and bc.fp_dm = tmp.bill_code and bc.fp_hm = tmp.bill_num



   select fp_hm ,fp_dm ,count(1) as c from busi_collection where id > 1661241314112438279 group by fp_hm,fp_dm having c > 1;


   select pk_invoice from busi_collection where
   ((fp_hm = '04312023' and fp_dm ='035002200112') or
   (fp_hm = '04312027' and fp_dm ='035002200112') or
   (fp_hm = '12387730' and fp_dm ='042002100104') or
   (fp_hm = '17410468' and fp_dm ='037002200104') or
   (fp_hm = '27931541' and fp_dm ='034002200112') or
   (fp_hm = '27931841' and fp_dm ='034002200112') or
   (fp_hm = '28114883' and fp_dm ='034002200112') or
   (fp_hm = '28115108' and fp_dm ='034002200112') or
   (fp_hm = '33374970' and fp_dm ='012002100511') or
   (fp_hm = '48677167' and fp_dm ='035001900212') or
   (fp_hm = '48677173' and fp_dm ='035001900212') or
   (fp_hm = '48677329' and fp_dm ='035001900212') or
   (fp_hm = '48787381' and fp_dm ='035001900212') or
   (fp_hm = '49446814' and fp_dm ='035001900212') or
   (fp_hm = '49446817' and fp_dm ='035001900212') or
   (fp_hm = '61764802' and fp_dm ='233002300111') or
   fp_hm = '23612000000000523163')
   and ytenant_id is null

   select * from invoice where pk_invoice in(
   1663547479622156293,
   1663547479630020613,
   1663547488219955207,
   1663556919968137246,
   1663547488212090888,
   1663556919960272905,
   1663547488219955202,
   1663547479622156316,
   1663556919968137235,
   1663556919960272909,
   1663547471032221710,
   1663547479630020622
   )


   ========
   delete from busi_collection where id in (
   1663547479622156296,
   1663547479630020616,
   1663547488219955210,
   1663556919968137249,
   1663547488212090891,
   1663556919960272908,
   1663547488219955205,
   1663547479622156319,
   1663556919968137238,
   1663556919960272912,
   1663547471032221713,
   1663547479630020625
   )


   delete from invoice where pk_invoice in (
   1663547479622156293,
   1663547479630020613,
   1663547488219955207,
   1663556919968137246,
   1663547488212090888,
   1663556919960272905,
   1663547488219955202,
   1663547479622156316,
   1663556919968137235,
   1663556919960272909,
   1663547471032221710,
   1663547479630020622
   )


   ======>>>>>>>

   select * from invoice where pk_invoice in(
   1663547479622156293,
   1663547479630020613,
   1663547488219955207,
   1663556919968137246,
   1663547488212090888,
   1663556919960272905,
   1663547488219955202,
   1663547479622156316,
   1663556919968137235,
   1663556919960272909,
   1663547471032221710,
   1663547479630020622
   )

   *
   *
   *
   * 去重复 ： group by xxx    / distinct
   *
   * =======================
   *
   *
   *    - 配合mybatis动态能力 foreach 、or 、..
   *              1.collection标签 自动一对多
   *    - 哪些逻辑应该放到代码  vs  sql中
   *
   *
   *    - 性能
   *          索引  ： https://cloud.tencent.com/developer/article/1666887 、 https://juejin.cn/post/7114987559381860382 、 https://juejin.cn/post/7083520386498822158
   *          实战：https://www.toutiao.com/article/7065649852025389600/?log_from=77de67517406a_1672576212745
   */


  public static void main(String[] args) {

    /****
     * 自定义 collectors.mapping()
     */
    Map<Integer, List<Integer>> collect = Stream.of(1, 3)
        .collect(Collectors.groupingBy(x -> x.intValue()));

    Map<Integer, List<String>> collect1 = Stream.of(1, 3)
        .collect(Collectors.groupingBy(x -> x.intValue(),
            Collectors.mapping(x -> x.toString(), Collectors.toList())));




  }

  /**
   * todo
   *  7.万字归总表设计与 SQL 编写技巧  https://www.infoq.cn/article/DGMlqL9x0maeHGRltOKT
   *  *
   *  *
   *  *
   *  *                      sql编写实战:
   *  *                          1.oracle、db2中插入系统当前时间：https://www.testwo.com/blog/5249
   *  *
   *  *
   *  *
   *  *
   *  *
   *  *
   *  *
   *
       *  /**
   *      性能优化之查询转换 - 子查询类：http://www.10tiao.com/html/188/201612/2650272664/1.html
   *  1.循环赋值修改  https://blog.51cto.com/alun51cto/2050064
   *    DB2 游标  https://blog.csdn.net/feier7501/article/details/25922303
   *  * 1.sql编写技巧 - sql 的编写需要注意优化
   *  *
   *  * 数据库的查询优化技术：https://blog.csdn.net/littledream/article/details/4243491
   *  *                    https://blog.csdn.net/littledream/article/details/4439812
   *  *
   *      1. 使用 limit 对查询结果的记录进行限定
   *      2. 避免 select *，将需要查找的字段列出来
   *      3. 使用连接（join）来代替子查询
   *      4. 拆分大的 delete 或 insert 语句
   *      5. 可通过开启慢查询日志来找出较慢的 SQL
   *      6. 不做列运算：SELECT id WHERE age + 1 = 10，‘ 任何对列的操作都将导致表扫描 ’，它包括数据库教程函数、计算表达式等等，查询时要尽可能将操作移至等号右边
   *      7.sql 语句尽可能简单：一条 sql 只能在一个 cpu 运算；大语句拆小语句，减少锁时间；一条大 sql 可以堵死整个库
   *      8.OR 改写成 IN：OR 的效率是 n 级别，IN 的效率是 log (n) 级别，in 的个数建议控制在 200 以内
   *      9. 不用函数和触发器，在应用程序实现
   *      10. 避免 % xxx 式查询
   *      11. 少用 JOIN
   *      12. 使用同类型进行比较，比如用 '123' 和 '123' 比，123 和 123 比
   *      13. 尽量避免在 WHERE 子句中使用！= 或 <> 操作符，否则将引擎放弃使用索引而进行全表扫描
   *      14. 对于连续数值，使用 BETWEEN 不用 IN：SELECT id FROM t WHERE num BETWEEN 1 AND 5
   *      15. 列表数据不要拿全表，要使用 LIMIT 来分页，每页数量也不要太大
   *  *
   *  * 索引：
   *  *   1.索引
   * *        1.命名：sample 表 member_id 上的索引:sample_mid_ind。
   * *        2.当删除约束的时候，为了确保不影响到 index，最好加上 keep index 参数。
   *          索引并不是越多越好，要根据查询有针对性的创建，考虑在 WHERE 和 ORDER BY 命令上涉及的列建立索引，
   *          可根据 EXPLAIN 来查看是否用了索引还是全表扫描
   *          应尽量避免在 WHERE 子句中对字段进行 NULL 值判断，否则将导致引擎放弃使用索引而进行全表扫描
   *          值分布很稀少的字段不适合建索引，例如 "性别" 这种只有两三个值的字段
   *          字符字段只建前缀索引
   *          字符字段最好不要做主键
   *          不用外键，由程序保证约束
   *          尽量不用 UNIQUE，由程序保证约束
   *          使用多列索引时主意顺序和查询条件保持一致，同时删除不必要的单列索引
   *          查询频繁的列，在 where，group by，order by，on 从句中出现的列
   *          where 条件中 <，<=，=，>，>=，between，in，以及 like 字符串 + 通配符（%）出现的列
   *          长度小的列，索引字段越小越好，因为数据库的存储单位是页，一页中能存下的数据越多越好
   *          离散度大（不同的值多）的列，放在联合索引前面。查看离散度，通过统计不同的列值来实现，count 越大，离散程度越高：
   *          索引失效：
   *          WHERE字句的查询条件里有不等于号（WHERE column!=…），MYSQL将无法使用索引
   *          类似地，如果WHERE字句的查询条件里使用了函数（如：WHERE DAY(column)=…），MYSQL将无法使用索引
   *          在JOIN操作中（需要从多个数据表提取数据时），MYSQL只有在主键和外键的数据类型相同时才能使用索引，否则即使建立了索引也不会使用
   *          如果WHERE子句的查询条件里使用了比较操作符LIKE和REGEXP，MYSQL只有在搜索模板的第一个字符不是通配符的情况下才能使用索引。比如说，如果查询条件是LIKE 'abc%',MYSQL将使用索引；如果条件是LIKE '%abc'，MYSQL将不使用索引。
   *          在ORDER BY操作中，MYSQL只有在排序条件不是一个查询条件表达式的情况下才使用索引。尽管如此，在涉及多个数据表的查询里，即使有索引可用，那些索引在加快ORDER BY操作方面也没什么作用。
   *          如果某个数据列里包含着许多重复的值，就算为它建立了索引也不会有很好的效果。比如说，如果某个数据列里包含了净是些诸如“0/1”或“Y/N”等值，就没有必要为它创建一个索引。
   *          对于多列索引，不是使用的第一部分，则不会使用索引
   *          如果条件中有or(并且其中有or的条件是不带索引的)，即使其中有条件带索引也不会使用(这也是为什么尽量少用or的原因)。注意：要想使用or，又想让索引生效，只能将or条件中的每个列都加上索引
   *          如果列类型是字符串，那一定要在条件中将数据使用引号引用起来,否则不使用索引
   *          如果mysql估计使用全表扫描要比使用索引快,则不使用索引
   *  *
   *  * 2.思路转换编写sql
   *  *
   *  *
   *  *
   *  *
   *  * 3.plsql
   *  *  虽然推崇减少‘数据库端’逻辑处理，但是比如要临时查看数据库数据，比对，批量插入，学会使用plsql编程可以加快速度而无需写一个程序。
   *      每次写完一条sql都需要进行执行计划，查看是否合理。
   *      0.explain   https://www.cnblogs.com/xuanzhi201111/p/4175635.html
   *      1.含义
   *      场景:-未添加索引
   *      查看两个截图
   *      1.视图
   *          CREATE view testVVV AS SELECT ID , 1+2 AS total from draft ;
   *          1.用于多个表连接，这些表在设计上是遵循原则的，但是业务逻辑又常常结合使用
   *          2.对数据进行处理返回 ，过滤...
   *          drop view testv;
   *      2.存储过程
   *          1.错误
   *          create or REPLACE PROCEDURE testProcedur AS
   *          BEGIN
   *          for i in 1..5 loop
   *          insert into draft values("test" , "test")
   *          end loop
   *          end ;
   *      2.正确  -- 语法学习
   *          create  PROCEDURE testProcedure1()
   *          BEGIN
   *          declare i int ;
   *          set i = 0 ;
   *          while i<5 do
   *          insert  into draft values('abc','abcTest');
   *          set i= i+1;
   *          end while ;
   *          end ;
   *          https://www.cnblogs.com/linjiqin/archive/2011/04/16/2018463.html
   *          https://blog.csdn.net/moxigandashu/article/details/64616135#1-%E5%AD%98%E5%82%A8%E8%BF%87%E7%A8%8B
   *          --有参
   *          --有输出的
   *      1.便于直接从客户端插入处理数据 if / for逻辑
   *         游标
   *      3.索引
   *          create index index_a on draft(draftNo)
   *          唯一索引 vs 普通索引
   *      3.函数 + 操作符  -- 哪些逻辑放到数据库哪些放到程序，放到数据库 sql中的算吗？还是只算存储过程... === 相当于代码中的  工具类 。。。当需要减少数据量或者直接从数据库
   *         导出报表时是重要的。
   *          https://www.jianshu.com/p/32bc449a1bf6
   *          数学函数
   *          字符串函数 //mysql中处理字符串时，默认第一个字符下标为1，即参数position必须大于等于1
   *          日期和时间函数
   *          条件判断函数
   *          系统信息函数
   *          加密函数
   *          格式化函数
   *         字段拼接  a||b||c ....
   *          select abs(t.draftDescribe)+100 as absvalue from draft t where t.id = 4;
   *          select FLOOR(RAND()*100)
   *          select ROUND(1.234,2)
   *          select CONCAT_WS('abc','a','b')
   *          select strcmp('a','B')
   *          SELECT reverse('ABC');
   *          load_file(file_name)
   *          select MONTH(now());
   *          select ADDDATE(NOW(),2);
   *          select DATEDIFF(NOW(),(ADDDATE(NOW(),2)))
   *          为输出进行转化
   *          select
   *          CASE
   *          when 1<2
   *          then 's'
   *          when 1>2
   *          then 'e'
   *          end
   *          select if(1 > 0,'正确','错误')
   *          加密
   *          select MD5('ab')
   *          select DECODE('ab','a')
   *          union
   *          minix
   *          子查询 = 临时表( 关联子查询-嵌套子查询 ) vs  join( 连接 )
   *          join与子查询对比:https://blog.csdn.net/wangzhuo14/article/details/51771472
   *          MySQL嵌套查询（子查询）：https://blog.csdn.net/Trisyp/article/details/78460289
   *          为什么MySQL不推荐使用子查询和join：https://blog.csdn.net/weixin_38676357/article/details/81510079
   *      navicat 快捷键
   *      ctrl+shift+r   命令执行
   *      编辑sql时选中一行快捷键：鼠标三击当前行
   */








}
