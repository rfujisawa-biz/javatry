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
package org.docksidestage.javatry.basic;

import java.util.ArrayList;
import java.util.List;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step02IfForTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        if Statement
    //                                                                        ============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_if_basic() { // example, so begin from the next method
        int sea = 904;
        if (sea >= 904) {
            sea = 2001;
        }
        log(sea); // your answer? => 2001
        // seaは904以上かつintなので、seaに2001が代入される
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_else_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else {
            sea = 7;
        }
        log(sea); // your answer? => 7 正解
        // seaは904より大きいので、else句に入る
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (sea >= 904) {
            sea = 7;
        } else if (sea >= 903) {
            sea = 8;
        } else {
            sea = 9;
        }
        log(sea); // your answer? => 7 正解
        // seaは904より大きく、904以上のため一つ目のelse if句に入り、7が代入される
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_nested() {
        boolean land = false;
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (land && sea >= 904) {
            sea = 7;
        } else if (sea >= 903 || land) { // ここに入る
            sea = 8;
            if (!land) { // ここに入る
                land = true;
            } else if (sea <= 903) {
                sea++;
            }
        } else if (sea == 8) {
            sea++;
            land = false;
        } else {
            sea = 9;
        }
        if (sea >= 9 || (sea > 7 && sea < 9)) { // sea = 8, land = true 入る
            sea--;
        }
        if (land) { // ここも入る
            sea = 10;
        }
        log(sea); // your answer? => 10 正解
        // 命令型を追いかけました。
        // #1on1: 漠然読みして構造を把握して、当てをつけてフォーカス読み (2025/08/12)
        // (BigDecimalのときから2回目)

        // TODO fujisawa 個人的には、業務を初めて約一年経つけど、もはや漠然よみが一番助けになってることのひとつかも！ by shiny (2025/08/13)
        // 結局プログラマーはコードを読んでる時間が一番長いもんね、、新しい実装する時、リファクタする時、コードレビューする時、など全てでいかに構造を早く把握できるかがアウトプットの量と質に直結してるなと感じます！
    }

    // ===================================================================================
    //                                                                       for Statement
    //                                                                       =============
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_inti_basic() {
        List<String> stageList = prepareStageList(); // stageList = ["broadway", "dockside", "hangar", "magiclamp"]
        String sea = null;
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i); // stageListのi番目からstage変数を作る
            if (i == 1) {
                sea = stage; // i = 1の時のstageの参照をseaに代入する
            }
        }
        log(sea); // your answer? => dockside 正解
        // i = 1の時にstageに代入される
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_basic() {
        List<String> stageList = prepareStageList(); // stageList = ["broadway", "dockside", "hangar", "magiclamp"]
        String sea = null;
        for (String stage : stageList) {
            sea = stage;
        }
        log(sea); // your answer? => magiclamp 正解
        // seaにstageList内のstageの参照代入され続け、最後のmagiclampが代入された状態で終了する。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_continueBreak() {
        List<String> stageList = prepareStageList(); // stageList = ["broadway", "dockside", "hangar", "magiclamp"]
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) { // "br"から始まる場合はスキップされる
                continue;
            }
            sea = stage; // stageの参照を渡す
            if (stage.contains("ga")) { // "ga"が含まれる場合は打ち切り
                break;
            }
        }
        log(sea); // your answer? => hangar 正解
        // 愚直に追いかけました
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_listforeach_basic() {
        List<String> stageList = prepareStageList(); // stageList = ["broadway", "dockside", "hangar", "magiclamp"]
        StringBuilder sb = new StringBuilder();
        stageList.forEach(stage -> { // 関数指向的に書いてるだけ？
            if (sb.length() > 0) {
                return;
            }
            if (stage.contains("i")) { // iが入ってたらsbに追加
                sb.append(stage);
            }
        });
        String sea = sb.toString();
        log(sea); // your answer? => dockside 正解
        // 他の言語と同様に、forEachは単純にstageList内の要素に対して処理を行っているだけだと予想
        // 1on1: 拡張for文と比べて、どう便利か？
        // できないが不便なのか？できないが便利なのか？
        // ストレートなループだったら、他の世界に影響を与えない、トリッキーなループができない方が安全/安心。
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Make list containing "a" from list of prepareStageList() and show it as log by loop. (without Stream API) <br>
     * (prepareStageList()のリストから "a" が含まれているものだけのリストを作成して、それをループで回してログに表示しましょう。(Stream APIなしで))
     */
    public void test_iffor_making() {
        // write if-for here
        List<String> stageList = prepareStageList();
        stageList.forEach(stage -> {
            if (stage.contains("a")) {
                log(stage);
            }
        });
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Change foreach statement to List's forEach() (keep result after fix) <br>
     * (foreach文をforEach()メソッドへの置き換えてみましょう (修正前と修正後で実行結果が同じになるように))
     */
    // 解答
    public void test_iffor_refactor_foreach_to_forEach() {
        //        List<String> stageList = prepareStageList();
        List<String> stageList = new ArrayList<>();
        stageList.add("brskla");
        stageList.add("srkejigaklw");
        stageList.add("gal;kjwer");

        StringBuilder sea = new StringBuilder();
        stageList.forEach(stage -> {
            if (!stage.startsWith("br") && stage.contains("ga")) {
                if (sea.length() > 0) {
                    return;
                }
                sea.append(stage);
            }
        });

        log(sea); // should be same as before-fix
        // TODO fujisawa 修行++: もし、gaが含まれてないstageListだったときでも、結果が変わらないように by jflute (2025/08/12)
        // つまり、stageListが動的に値が変わっても、元の実装と結果が変わらないようにしたい。
    }

    // 元の実装
    public void test_iffor_refactor_foreach_to_forEach_test() {
        List<String> stageList = new ArrayList<>();
        stageList.add("brskla");
        stageList.add("srkejigaklw");
        stageList.add("gal;kjwer");
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {
                continue;
            }
            sea = stage;
            if (stage.contains("ga")) {
                break;
            }
        }

        log(sea);
    }

    // stream APIを使ってみた実装
    public void test_iffor_refactor_foreach_to_forEach_with_stream() {
        List<String> stageList = new ArrayList<>();
        stageList.add("brskla");
        stageList.add("srkejigaklw");
        stageList.add("gal;kjwer");

        StringBuilder sea = new StringBuilder();
        stageList.stream().filter(stage -> !stage.startsWith("br") && stage.contains("ga")).findFirst().ifPresent(sea::append);
        log(sea);

        // TODO fujisawa ようこそStream APIの沼へ！笑（Stream APIにハマる時期が果たしてやってくるのか、、笑） by shiny (2025/08/13)
    }

    /**
     * Make your original exercise as question style about if-for statement. <br>
     * (if文for文についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * stageListの各要素に関して、文字の長さが8文字以上の文字列を連結した文字列を出力してください。
     * 文字列はリストに格納されている順に結合されます。
     * forEachを用いて記述してください。
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_iffor_yourExercise() {
        //        List<String> stageList = prepareStageList();
        //        StringBuilder result = new StringBuilder();
        //        stageList.forEach(stage -> {
        //            if (stage.length() >= 8) {
        //                result.append(stage);
        //            }
        //        });
        //        log(result);
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    private List<String> prepareStageList() {
        List<String> stageList = new ArrayList<>();
        stageList.add("broadway");
        stageList.add("dockside");
        stageList.add("hangar");
        stageList.add("magiclamp");
        return stageList;
    }
}
