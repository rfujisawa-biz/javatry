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

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketShortMoneyException;
import org.docksidestage.bizfw.basic.buyticket.TicketBuyResult;
import org.docksidestage.bizfw.basic.buyticket.TicketType;
import org.docksidestage.unit.PlainTestCase;

// done fujisawa authorお願いします by jflute (2025/08/26)
/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう) <br>
 * 
 * If ambiguous requirement exists, you can determine specification that seems appropriate. <br>
 * (要件が曖昧なところがあれば、適切だと思われる仕様を決めても良いです)
 * 
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is oneDayQuantity variable at the method end? <br>
     * (メソッド終了時の変数 oneDayQuantity の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = new TicketBooth();
        TicketType ticketType = TicketType.ONE_DAY;
        booth.buyPassport(7400, ticketType);
        int oneDayQuantity = booth.getOneDayQuantity(); // return quantity
        log(oneDayQuantity); // your answer? => 9 正解
        assertEquals(9, oneDayQuantity);
        // [考えたこと] これ、支払いする前にチケット渡してる。。。お釣りも返してない？
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = new TicketBooth();
        TicketType ticketType = TicketType.ONE_DAY;
        booth.buyPassport(10000, ticketType);
        Integer salesProceeds = booth.getSalesProceeds();
        log(salesProceeds); // your answer? => 10000 正解
        assertEquals(7400, salesProceeds);
        // プログラムを読んで回答しただけ
        // 修正後 answer => 7400 正解
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = new TicketBooth();
        Integer salesProceeds = booth.getSalesProceeds();
        log(salesProceeds); // your answer? => null 正解
        assertNull(salesProceeds);
        // プログラムを読んで回答しただけ
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer quantity = doTest_class_ticket_wrongQuantity();
        log(quantity); // your answer? => 9 正解
        // [考えたこと] 最初に実装を見た時に思ったように、代金支払い前にチケットは渡してしまっている
        // 修正後 answer => 10 正解
        assertEquals(10, quantity);
    }

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = new TicketBooth();
        int handedMoney = 7399;
        TicketType ticketType = TicketType.ONE_DAY;
        try {
            booth.buyPassport(handedMoney, ticketType);
        } catch (TicketShortMoneyException continued) {
            log("Failed to buy one-day passport: money=" + handedMoney, continued);
        }
        return booth.getOneDayQuantity();
    }

    // ===================================================================================
    //                                                                           Let's fix
    //                                                                           =========
    /**
     * Fix the problem of ticket quantity reduction when short money. (Don't forget to fix also previous exercise answers) <br>
     * (お金不足でもチケットが減る問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_ticketQuantityReduction() {
        Integer quantity = doTest_class_ticket_wrongQuantity();
        log(quantity); // should be max quantity, visual check here
        // MAX_QUANTITYになっているので問題なし
        assertEquals(10, quantity);
    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        TicketBooth booth = new TicketBooth();
        TicketType ticketType = TicketType.ONE_DAY;
        booth.buyPassport(10000, ticketType);
        Integer salesProceeds = booth.getSalesProceeds();
        log(salesProceeds); // should be same as one-day price, visual check here
        // ONE_DAY_PRICEになっているので問題なし
        assertEquals(7400, salesProceeds);
    }

    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        // uncomment after making the method
        TicketBooth booth = new TicketBooth();
        int money = 14000;
        TicketType ticketType = TicketType.TWO_DAY;
        TicketBuyResult buyResult = booth.buyPassport(money, ticketType);
        int change = buyResult.getChange();
        Integer totalAmount = booth.getSalesProceeds() + change;
        log(totalAmount); // should be same as money
        assertEquals(money, totalAmount);

        // and show two-day passport quantity here
        int twoDayQuantity = booth.getTwoDayQuantity();
        log(twoDayQuantity);
        // output => 9 OK
        assertEquals(9, twoDayQuantity);
    }

    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = new TicketBooth();
        TicketType ticketType = TicketType.ONE_DAY;
        booth.buyPassport(10000, ticketType);
        log(booth.getOneDayQuantity(), booth.getSalesProceeds()); // should be same as before-fix
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Now you cannot get ticket if you buy one-day passport, so return Ticket class and do in-park. <br>
     * (OneDayPassportを買ってもチケットをもらえませんでした。戻り値でTicketクラスを戻すようにしてインしましょう)
     */
    public void test_class_moreFix_return_ticket() {
        // uncomment out after modifying the method
        TicketBooth booth = new TicketBooth();
        TicketType ticketType = TicketType.ONE_DAY;
        int handedMoney = 10000;
        TicketBuyResult buyResult = booth.buyPassport(handedMoney, ticketType);
        Ticket oneDayPassport = buyResult.getTicket();
        log(oneDayPassport.getDisplayPrice()); // should be same as one-day price
        log(oneDayPassport.isAlreadyIn()); // should be false
        assertFalse(oneDayPassport.isAlreadyIn());
        oneDayPassport.doInPark();
        log(oneDayPassport.isAlreadyIn()); // should be true
        assertTrue(oneDayPassport.isAlreadyIn());
        assertEquals(0, oneDayPassport.getAvailableDays());
    }

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
//         uncomment after modifying the method
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        TicketType ticketType = TicketType.TWO_DAY;
        TicketBuyResult buyResult = booth.buyPassport(handedMoney, ticketType);
        Ticket twoDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(twoDayPassport.getDisplayPrice() + change); // should be same as money
        assertEquals(handedMoney, twoDayPassport.getDisplayPrice() + change);
    }

    /**
     * Now you can use only one in spite of two-day passport, so fix Ticket to be able to handle plural days. <br>
     * (TwoDayPassportなのに一回しか利用できません。複数日数に対応できるようにTicketを修正しましょう)
     */
    public void test_class_moreFix_usePluralDays() {
        // your confirmation code here
        int oneDayPrice = 7400;
        int twoDayPrice = 13200;
        Ticket oneDayPassport = new Ticket(oneDayPrice, TicketType.ONE_DAY);
        Ticket twoDayPassport = new Ticket(twoDayPrice, TicketType.TWO_DAY);
        log("oneDayPassport: " + oneDayPassport.getAvailableDays());
        log("twoDayPassport: " + twoDayPassport.getAvailableDays());
        assertEquals(1, oneDayPassport.getAvailableDays());
        assertEquals(2, twoDayPassport.getAvailableDays());
    }

    /**
     * Accurately determine whether type of bought ticket is two-day passport or not by if-statemet. (fix Ticket classes if needed) <br>
     * (買ったチケットの種別がTwoDayPassportなのかどうかをif文で正確に判定してみましょう。(必要ならTicketクラスたちを修正))
     */
    public void test_class_moreFix_whetherTicketType() {
        // uncomment when you implement this exercise
        TicketBooth booth = new TicketBooth();
        TicketBuyResult buyResultOneDay = booth.buyPassport(10000, TicketType.ONE_DAY);
        Ticket oneDayPassport = buyResultOneDay.getTicket();
        showTicketIfNeeds(oneDayPassport);
        TicketBuyResult buyResultTwoDay = booth.buyPassport(20000, TicketType.TWO_DAY);
        Ticket twoDayPassport = buyResultTwoDay.getTicket();
        showTicketIfNeeds(twoDayPassport);
        assertEquals(TicketType.ONE_DAY, oneDayPassport.getTicketType());
        assertEquals(TicketType.TWO_DAY, twoDayPassport.getTicketType());
    }

    // uncomment when you implement this exercise
    private void showTicketIfNeeds(Ticket ticket) {
        // #1on1: 思考エクササイズ、MaxDaysだとダメな理由
        //if (ticket.getMaxDays() == 2) {
        // done jflute もうちょい進んだらもっかい聞きます (2025/08/26)
        
        // done fujisawa java的なれびゅー、文字列の == がダメ by jflute (2025/08/26)
        // (まあここは普段Java書いてなければ忘れやすいところなのでしょうがない)
        // done fujisawa ここに "TwoDay" と文字列をハードコードしているのをどうにかしたい by jflute (2025/08/26)
        // スペルミスもそうですし、表現が変わった時に自動で追従されるように
        // done jflute enumを使ってリファクタリングしました by fujisawa (2025/08/29)
        if (ticket.getTicketType() == TicketType.TWO_DAY) { // write determination for two-day passport
            log("two-day passport");
        } else {
            log("other");
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Fix it to be able to buy four-day passport (price is 22400). <br>
     * (FourDayPassport (金額は22400) のチケットも買えるようにしましょう)
     */
    public void test_class_moreFix_wonder_four() {
        // your confirmation code here
        // とりあえず、8/28現在の実装で進めていく
        // と思ったけど、Ticketのリファクタリングがめんどくさくなりそうなので、一回1on1のコメントを受けてのリファクタリングをしてから進めることを決意
        // enumを使ってTicketTypeを定義した
        // チケットの料金を、Ticketが決めるべきか、TicketBoothが決めるべきかを悩んだが、
        // 「TicketBoothが決めた料金をチケットが持つ」の方がチケット料金の変更に柔軟な気がしたので、そのままにした
        // 例: チケットの料金がある時点で変更になった時に、TicketBoothで料金を決めていれば、基準日前の料金を持ったチケットと、基準日後の料金を持ったチケットが作れる

        TicketBooth booth = new TicketBooth();
        int handedMoney = 30000;
        TicketType ticketType = TicketType.FOUR_DAY;
        TicketBuyResult buyResult = booth.buyPassport(handedMoney, ticketType);
        Ticket fourDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(fourDayPassport.getDisplayPrice() + change); // should be same as money
        assertEquals(handedMoney, fourDayPassport.getDisplayPrice() + change);
    }

    /**
     * Fix it to be able to buy night-only two-day passport (price is 7400), which can be used at only night. <br>
     * (NightOnlyTwoDayPassport (金額は7400) のチケットも買えるようにしましょう。夜しか使えないようにしましょう)
     */
    public void test_class_moreFix_wonder_night() {
        // your confirmation code here
        // 間違えて夜にしか買えないチケットを作るとこでした笑

        TicketBooth booth = new TicketBooth();
        int handedMoney = 22400;
        TicketType ticketType = TicketType.NIGHT_ONLY_TWO_DAY;
        TicketBuyResult buyResult = booth.buyPassport(handedMoney, ticketType);
        Ticket nightOnlyTwoDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(nightOnlyTwoDayPassport.getDisplayPrice() + change); // should be same as money
        assertEquals(handedMoney, nightOnlyTwoDayPassport.getDisplayPrice() + change);
        log(nightOnlyTwoDayPassport.getTicketType());
//        nightOnlyTwoDayPassport.doInPark(); // ここは18-22時以外に実行すれば例外が投げられるはず
        // テストするのが難しい設計になりましたね
    }

    /**
     * Refactor the code to the best readable code you can think of. <br>
     * (自分の中で思う最高に可読性の高いコードにリファクタリングしてみましょう)
     */
    public void test_class_moreFix_yourRefactoring() {
        // your confirmation code here
        // リファクタリングどこまでやるか問題
        // 自作のクラスはリファクタリングされてるつもりなので、testクラスのリファクタリングもします
        // assert文を書いて、変数の名前を変更した（問題文の書き換えは流石にめんどくさかったので許してください！）
        // いらないコメントアウトも削除
        // リファクタリングとは関係ないけど、退園処理の実装があったらいいですね
    }

    /**
     * Write intelligent JavaDoc comments seriously on the public classes/constructors/methods of the Ticket class. <br>
     * (Ticketクラスのpublicなクラス/コンストラクター/メソッドに、気の利いたJavaDocコメントを本気で書いてみましょう)
     */
    public void test_class_moreFix_yourSuperJavaDoc() {
        // your confirmation code here
        // done!
    }

    // ===================================================================================
    //                                                                         Devil Stage
    //                                                                         ===========
    /**
     * If your specification is to share inventory (quantity) between OneDay/TwoDay/...,
     * change the specification to separate inventory for each OneDay/TwoDay/.... <br>
     * (もし、OneDay/TwoDay/...で在庫(quantity)を共有する仕様になってたら、
     * OneDay/TwoDay/...ごとに在庫を分ける仕様に変えてみましょう)
     */
    public void test_class_moreFix_zonedQuantity() {
        // your confirmation code here
        // rfujisawa-biz already implemented
    }
}
