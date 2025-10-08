package org.docksidestage.javatry.basic.st6.dbms;

/**
 * Sqlの抽象クラス
 * @author rfujisawa-biz
 */
public abstract class St6Sql {
    /**
     * ページングのクエリを作成する
     * @param pageSize ページ内の要素数
     * @param pageNumber ページ数
     * @return ページングのクエリ
     */
    public abstract String buildPagingQuery(int pageSize, int pageNumber);
}
