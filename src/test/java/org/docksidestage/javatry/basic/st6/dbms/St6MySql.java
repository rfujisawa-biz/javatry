/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.basic.st6.dbms;

/**
 * @author jflute
 * @author rfujisawa-biz
 */
//public class St6MySql {
//
//    public String buildPagingQuery(int pageSize, int pageNumber) {
//        int offset = pageSize * (pageNumber - 1);
//        return "limit " + offset + ", " + pageSize;
//    }
//}

public class St6MySql extends St6Sql {
    // TODO fujisawa その1: offsetの導出ロジックは、PostgreSQLと同じなので再利用してみましょう by jflute (2025/10/21)
    // TODO fujisawa その2: (全体的に)offsetの導出とquery文字列の生成の間に追加処理を入れることになったら... by jflute (2025/10/21)
    // e.g.
    //  int offset = pageSize * (pageNumber - 1);
    //  xxxxxxxxxxxxx(); // 追加処理
    //  return "limit " + offset + ", " + pageSize;
    // この場合に、現状だとサブクラスごとに修正を追加しないといけないので、
    // 一箇所直せば(追加すれば)修正が済むようにしてみましょう。
    // (現状、2stepの流れが再利用できない。なので、流れを再利用したい)
    @Override
    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        return "limit " + offset + ", " + pageSize;
    }
}
