package org.docksidestage.javatry.basic.st6.dbms;

// #1on1: MySQL(OSS/Oracle), PostgreSQL(OSS), Oracle(DB), IBM DB2, MS SQLServer
// St6MySql extends St6Sql
// St6PostgreSql extends St6Sql
// Oracle extends St6Sql
// IBM DB2 extends St6Sql
// ...
// is-aの関係: 犬は動物である。Dog is a(n) Animal
// MySQLはSQLである。OracleはSQLである。
// MySQLとかOracleとかって？ => データベース(製品)
//
// #1on1: 抽象クラスの名前を考える時、まだ見ぬサブクラス候補を想像してみる。 (2025/10/21)
// done fujisawa 抽象クラスをデータベース(製品)にしてみましょう by jflute (2025/10/21)
// データベースは、広義、狭義として色々なところで使われる言葉
// なので、データベース製品 (Database Management System: DBMS)
// #1on1; 抽象クラスの名前付け(概念化)の重要性 (将来に渡って辻褄の合うものを付ける)

// #1on1: Template Method パターンの紹介 (2025/11/07)
// 流れを再利用するやり方は、他にもあります。
// やりたいことは、みんな同じ。
// 本質を学ぶための道具としてであれば、応用ができて有益。
/**
 * Sqlの抽象クラス
 * @author rfujisawa-biz
 */
public abstract class St6Dbms {
    /**
     * ページングのクエリを作成する
     * @param pageSize ページ内の要素数
     * @param pageNumber ページ数
     * @return ページングのクエリ
     */
    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = computeOffset(pageSize, pageNumber);
        return doBuildPagingQuery(pageSize, offset);
    }

    protected abstract String doBuildPagingQuery(int pageSize, int offset);

    protected int computeOffset(int pageSize, int pageNumber) {
        return pageSize * (pageNumber - 1);
    }
}
