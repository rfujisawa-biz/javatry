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
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.docksidestage.javatry.basic.st8.St8DbFacade;
import org.docksidestage.javatry.basic.st8.St8Member;
import org.docksidestage.javatry.basic.st8.St8Withdrawal;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Java8 functions. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author your_name_here
 */
public class Step08Java8FunctionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                              Lambda
    //                                                                              ======
    // -----------------------------------------------------
    //                                              Callback
    //                                              --------
    /**
     * Are all the strings by log() methods in callback processes same? (yes or no) <br>
     * (コールバック処理の中で出力しているログの文字列はすべて同じでしょうか？ (yes or no))
     */
    public void test_java8_lambda_callback_basic() {
        String title = "over";

        log("...Executing named class callback(!?)");
        helpCallbackConsumer(new St8BasicConsumer(title));

        log("...Executing anonymous class callback");
        helpCallbackConsumer(new Consumer<String>() {
            public void accept(String stage) {
                log(stage + ": " + title);
            }
        });

        log("...Executing lambda block style callback");
        helpCallbackConsumer(stage -> {
            log(stage + ": " + title);
        });

        log("...Executing lambda expression style callback");
        helpCallbackConsumer(stage -> log(stage + ": " + title));

        // your answer? => yes
        // わからないけど、記法が違うだけのはず

        // cannot reassign because it is used at callback process
        //title = "wave";
        
        // #1on1: コールバックの話 (2025/12/19)
    }

    // done jflute 次回1on1ふぉろーここから (2025/12/19)
    /**
     * What is order of strings by log(). (write answer as comma-separated) <br>
     * (ログに出力される文字列の順番は？ (カンマ区切りで書き出しましょう))
     */
    public void test_java8_lambda_callback_order() {
        log("harbor");
        helpCallbackConsumer(stage -> {
            log(stage);
        });
        log("lost river");
        // your answer? => harbor, broadway, dockside, hanger, lost river 正解
        // 正解したが、stage -> {log(stage);} のところがよくわからない
        // stage -> {log(stage);}で渡した内容が、acceptのところで実行される。引数がdocksideだから、
        // helpCallbackConsumerのところは
        //     private void helpCallbackConsumer() {
        //        log("broadway");
        //        log("dockside");
        //        log("hangar");
        //    }
        // これと同値か
    }

    private class St8BasicConsumer implements Consumer<String> {

        private final String title;

        public St8BasicConsumer(String title) {
            this.title = title;
        }

        @Override
        public void accept(String stage) {
            log(stage + ": " + title);
        }
    }

    private void helpCallbackConsumer(Consumer<String> oneArgLambda) {
        log("broadway");
        oneArgLambda.accept("dockside");
        log("hangar");
    }

    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_lambda_callback_valueRoad() {
        String label = "number";
        String sea = helpCallbackFunction(number -> {
            return label + ": " + number;
        });
        log(sea); // your answer? => number: 7 正解
        // returnしてるし、代入されるでしょと思って足し合わせた文字列で回答
        // 久しぶりだったので、Integerと文字列ってそのまま足せるんだっけ？となりました
    }

    // done fujisawa 次ここから
    private String helpCallbackFunction(Function<Integer, String> oneArgLambda) {
        return oneArgLambda.apply(7);
    }

    // -----------------------------------------------------
    //                                         Convert Style
    //                                         -------------
    /**
     * Change callback style like this:
     * <pre>
     * o sea: to lambda block style
     * o land: to lambda expression style
     * o piari: to lambda block style
     * </pre>
     * (このようにコールバックスタイルを変えてみましょう:)
     * <pre>
     * o sea: BlockのLambda式に
     * o land: ExpressionのLambda式に
     * o piari: BlockのLambda式に
     * </pre>
     */
    public void test_java8_lambda_convertStyle_basic() {
        //helpCallbackSupplier(new Supplier<String>() { // sea
        //    public String get() {
        //        return "broadway";
        //    }
        //});

        helpCallbackSupplier(() -> {
            return "bloadway";
        });

//        helpCallbackSupplier(() -> { // land
//            return "dockside";
//        });

        helpCallbackSupplier(() -> "dockside");

//        helpCallbackSupplier(() -> "hangar"); // piari

        helpCallbackSupplier(() -> {
            return "hangar";
        });
    }

    private void helpCallbackSupplier(Supplier<String> oneArgLambda) {
        String supplied = oneArgLambda.get();
        log(supplied);
    }
    
    // #1on1: コールバックの敷居の高さのヒアリング (2026/01/09)

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_concept() {
        St8Member oldmember = new St8DbFacade().oldselectMember(1);
        if (oldmember != null) {
            log(oldmember.getMemberId(), oldmember.getMemberName()); // 1, broadway
        }
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        // your answer? => yes 正解
        // Option<T>の文法だと予想
        
        // #1on1: Optionalの一番根源的なメリットの話 (2026/01/09)
        // ないかもしれないという概念を型にして、呼び出し側に意識と実装を矯正させる。
        // Javaで入ったのが2015年とだいぶ遅い。
        // そして、Optionalは文法ではなく、ただのクラス。
        // 長年のnullチェックの慣れ、Optionalの方がもっさり(↑の書き方だと)
        // そこでLambda式。
    }

    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_ifPresent() {
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        optMember.ifPresent(member -> {
            log(member.getMemberId(), member.getMemberName());
        });
        // your answer? => yes 正解
        // ifPresentが、コールバックを受け取って、値が存在する場合はコールバック中の処理を行う
        
        // #1on1: kotlinのandroid話 (2026/01/09)
        // #1on1: expression から block のパターン (2026/01/09)
    }

    /**
     * What string is sea, land, piari, bonvo, dstore, amba variables at the method end? <br>
     * (メソッド終了時の変数 sea, land, piari, bonvo, dstore, amba の中身は？)
     */
    public void test_java8_optional_map_flatMap() {
        St8DbFacade facade = new St8DbFacade();

        // traditional style
        St8Member oldmemberFirst = facade.oldselectMember(1);
        String sea;
        if (oldmemberFirst != null) { // ここ
            St8Withdrawal withdrawal = oldmemberFirst.oldgetWithdrawal(); // St8Withdrawal(11, "music")
            if (withdrawal != null) { // ここ
                sea = withdrawal.oldgetPrimaryReason(); // "music"
                if (sea == null) {
                    sea = "*no reason1: the PrimaryReason was null";
                }
            } else {
                sea = "*no reason2: the Withdrawal was null";
            }
        } else {
            sea = "*no reason3: the selected Member was null";
        }

        Optional<St8Member> optMemberFirst = facade.selectMember(1);

        // map style
        String land = optMemberFirst.map(mb -> mb.oldgetWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason()) // ここ music
                .orElse("*no reason: someone was not present");

        // flatMap style
        String piari = optMemberFirst.flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason()) // ここ music
                .orElse("*no reason: someone was not present");

        // flatMap and map style
        String bonvo = optMemberFirst.flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason()) // ここ music
                .orElse("*no reason: someone was not present");

        String dstore = facade.selectMember(2)
                .flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present"); // ここ

        String amba = facade.selectMember(3)
                .flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason())
                .orElse("*no reason: someone was not present"); // ここ

        int defaultWithdrawalId = -1;
        Integer miraco = facade.selectMember(2)
                .flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.getWithdrawalId()) // ID here IDは12
                .orElse(defaultWithdrawalId);

        log(sea); // your answer? => music
        log(land); // your answer? => music
        log(piari); // your answer? => music
        log(bonvo); // your answer? => music
        log(dstore); // your answer? => *no reason: someone was not present
        log(amba); // your answer? => *no reason: someone was not present
        log(miraco); // your answer? => 12
        
        // #1on1: map() and flatMap() (2026/01/09)
        // https://dbflute.seasar.org/ja/manual/topic/programming/java/java8/mapandflat.html
    }

    /**
     * What string is sea variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_optional_orElseThrow() {
        int memberId = 2;
        Optional<St8Member> optMember = new St8DbFacade().selectMember(memberId); // St8Member(memberId, "dockside", new St8Withdrawal(12, null));
        St8Member member = optMember.orElseThrow(() -> new IllegalStateException("会員がありmせんでした！"));
        String sea = "the";
        try {
            String reason = member.getWithdrawal().map(wdl -> wdl.oldgetPrimaryReason()).orElseThrow(() -> {
                return new IllegalStateException("wave"); // 例外になるはず
            });
            sea = reason;
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? => wave 正解
        // reasonはnullなので、orElseThrowで例外を投げるはず
        // catchした先で、seaにe.getMessage()が代入される
        
        // #1on1: 例えば、selectMember(2) が詳細画面の検索だったら？ (2026/01/23)
        // すでの検索一覧画面で存在する2番を指定して詳細画面に来ているので...
        // 万が一なかったら、もう落ちても良い。(戻り値というのは引数によって有無がニュアンスが変わってくる)
        // そこで、orElseThrow() がある。
        // 業務的に必ず存在するという場面での呼び出しだったら、
        // orElseThrow()で自分でデバッグしやすいように throw してね、というコンセプト。
        // 
        // 一方で、ほとんど業務的に存在すると言っていいような場面だと、
        // orElseThrow()だらけでちょっと面倒かも？という考え方も。
        // かといって、例外メッセージを適当にすると...
        // 
        // St8Member member = optMember.orElseThrow(() -> new IllegalStateException());
        // St8Member member = optMember.get();
        //
        // ↑の2行はほぼ等価。エラーが発生した時の情報量は何も変わらない。
        // orElseThrow()で例外throwが貧弱だと、問答無用get()と何も変わらない。
        //
        // A. どんな場面でも、ちゃんとしたorElseThrow()を投げること (メッセージしっかり)
        // B. 本当に業務的に存在する(存在しないことが確率低い)という場面なら問答無用get()でOK (さじ加減)
        //
        // なんにせよ、Java10から、引数なしorElseThrow() (=実質問答無用get()と同じ) が追加された。
        // kotlinでの話、強制アンラップ(!!)という文法がある。
        // 一方で、ふじさわさんの現場ではデフォルトのエルビス演算子を使う場面が多いので、
        // あまりそのジレンマになることがない。(DB周りはまだ見る機会が少ない)
        // やはりDB周りだとジレンマが発生しやすいかも。
        //
        // ちょこっとDBFluteの例、Optionalに例外情報を載せて戻すというやり方。 (2026/01/23)
    }
    
    // #1on1: AIコードの割合、ケースバイケース、半々 (2026/01/23)
    // レビューする機会は増えて、レビューする力は強くなる!?
    // 元々チームでセルフレビューする文化とても良い。
    // ものができるのが楽しい、なのでAIを使おうが使わまいが楽しさは同じ。

    // done jflute 次回1on1: Stream APIのふぉろー (2026/01/23)
    // ===================================================================================
    //                                                                          Stream API
    //                                                                          ==========
    /**
     * What string is sea, land variables at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_java8_stream_concept() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        List<String> oldfilteredNameList = new ArrayList<>();
        for (St8Member member : memberList) {
            if (member.getWithdrawal().isPresent()) {
                oldfilteredNameList.add(member.getMemberName()); // withDrawalがnullの3以外が入る
            }
        }
        String sea = oldfilteredNameList.toString(); // broadwaydockside
        log(sea); // your answer? => broadwaydockside

        // #1on1: JavaでのStreamAPIの導入経緯 (2026/02/06)
        // 抽象度が一つ上がったプログラミング。
        // StreamAPIの学習コストのジレンマ。
        List<String> filteredNameList = memberList.stream() //
                .filter(mb -> mb.getWithdrawal().isPresent()) //
                .map(mb -> mb.getMemberName()) //
                .collect(Collectors.toList()); // ["broadway", "dockside"]
        String land = filteredNameList.toString();
        log(land); // your answer? => broadwaydockside
        // toStringメソッドを知らなかっただけでした。
        // joinみたいなメソッドだと予想してた。。。
    }

    /**
     * What string is sea, variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_stream_map_flatMap() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        int sea = memberList.stream()
                .filter(mb -> mb.getWithdrawal().isPresent())
                .flatMap(mb -> mb.getPurchaseList().stream())
                .filter(pur -> pur.getPurchaseId() > 100)
                .mapToInt(pur -> pur.getPurchasePrice())
                .distinct()
                .sum();
        log(sea); // your answer? => 600
        // 退会情報があるメンバーの、購入リストを抽出する
        // 購入IDが100より大きい111, 112, 133, 114を抽出する
        // Priceをdistinctで合計して、100+200+300 = 600
    }

    // *Stream API will return at Step12 again, it's worth the wait!
}
