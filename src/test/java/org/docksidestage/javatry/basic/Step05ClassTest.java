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
import org.docksidestage.unit.PlainTestCase;

// TODO fujisawa authorお願いします by jflute (2025/08/26)
/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう) <br>
 * 
 * If ambiguous requirement exists, you can determine specification that seems appropriate. <br>
 * (要件が曖昧なところがあれば、適切だと思われる仕様を決めても良いです)
 * 
 * @author jflute
 * @author your_name_here
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = new TicketBooth();
//        booth.buyOneDayPassport(7400); // quantity = 9
        String ticketType = "OneDay";
        booth.buyPassport(7400, ticketType);
        int sea = booth.getOneDayQuantity(); // return quantity
        log(sea); // your answer? => 9 正解
        // [考えたこと] これ、支払いする前にチケット渡してる。。。お釣りも返してない？
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = new TicketBooth();
//        booth.buyOneDayPassport(10000);
        String ticketType = "OneDay";
        booth.buyPassport(10000, ticketType);
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => 10000 正解
        // プログラムを読んで回答しただけ
        // 修正後 answer => 7400 正解
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = new TicketBooth();
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => null 正解
        // プログラムを読んで回答しただけ
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // your answer? => 9 正解
        // [考えたこと] 最初に実装を見た時に思ったように、代金支払い前にチケットは渡してしまっている
        // 修正後 answer => 10 正解
    }

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = new TicketBooth();
        int handedMoney = 7399;
        String ticketType = "OneDay";
        try {
//            booth.buyOneDayPassport(handedMoney);
            booth.buyPassport(handedMoney, ticketType);
            fail("always exception but none");
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
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // should be max quantity, visual check here
        // MAX_QUANTITYになっているので問題なし
    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        TicketBooth booth = new TicketBooth();
        String ticketType = "OneDay";
//        booth.buyOneDayPassport(10000);
        booth.buyPassport(10000, ticketType);
        Integer sea = booth.getSalesProceeds();
        log(sea); // should be same as one-day price, visual check here
        // ONE_DAY_PRICEになっているので問題なし
    }

    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        // uncomment after making the method
        TicketBooth booth = new TicketBooth();
        int money = 14000;
        String ticketType = "TwoDay";
//        int change = booth.buyTwoDayPassport(money);
//        int change = booth.buyPassport(money, ticketType);
//        Ticket twoDayPassport = booth.buyPassport(money, ticketType);
        TicketBuyResult buyResult = booth.buyPassport(money, ticketType);
//        int change = booth.getChange();
        int change = buyResult.getChange();
        Integer sea = booth.getSalesProceeds() + change;
        log(sea); // should be same as money
        // output => 14000 OK

        // and show two-day passport quantity here
        log(booth.getTwoDayQuantity());
        // output => 9 OK
    }

    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = new TicketBooth();
        String ticketType = "OneDay";
//        booth.buyOneDayPassport(10000);\
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
        String ticketType = "OneDay";
        int handedMoney = 10000;
//        Ticket oneDayPassport = booth.buyOneDayPassport(10000);
//        Ticket oneDayPassport = booth.buyPassport(handedMoney, ticketType);
        TicketBuyResult buyResult = booth.buyPassport(handedMoney, ticketType);
        Ticket oneDayPassport = buyResult.getTicket();
        log(oneDayPassport.getDisplayPrice()); // should be same as one-day price
        log(oneDayPassport.isAlreadyIn()); // should be false
        oneDayPassport.doInPark();
        log(oneDayPassport.isAlreadyIn()); // should be true
    }

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
//         uncomment after modifying the method
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
//        TicketBuyResult buyResult = booth.buyTwoDayPassport(handedMoney);
        TicketBuyResult buyResult = booth.buyPassport(handedMoney, "TwoDay");
        Ticket twoDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(twoDayPassport.getDisplayPrice() + change); // should be same as money
    }

    /**
     * Now you can use only one in spite of two-day passport, so fix Ticket to be able to handle plural days. <br>
     * (TwoDayPassportなのに一回しか利用できません。複数日数に対応できるようにTicketを修正しましょう)
     */
    public void test_class_moreFix_usePluralDays() {
        // your confirmation code here
        int oneDayPrice = 7400;
        int twoDayPrice = 13200;
        Ticket oneDayPassport = new Ticket(oneDayPrice, "OneDay");
        Ticket twoDayPassport = new Ticket(twoDayPrice, "TwoDay");
        log("oneDayPassport: " + oneDayPassport.getAvailableDays());
        log("twoDayPassport: " + twoDayPassport.getAvailableDays());
    }

    /**
     * Accurately determine whether type of bought ticket is two-day passport or not by if-statemet. (fix Ticket classes if needed) <br>
     * (買ったチケットの種別がTwoDayPassportなのかどうかをif文で正確に判定してみましょう。(必要ならTicketクラスたちを修正))
     */
    public void test_class_moreFix_whetherTicketType() {
        // uncomment when you implement this exercise
        TicketBooth booth = new TicketBooth();
//        Ticket oneDayPassport = booth.buyOneDayPassport(10000);
        TicketBuyResult buyResultOneDay = booth.buyPassport(10000, "OneDay");
        Ticket oneDayPassport = buyResultOneDay.getTicket();
        showTicketIfNeeds(oneDayPassport);
//        TicketBuyResult buyResult = booth.buyTwoDayPassport(10000);
        TicketBuyResult buyResultTwoDay = booth.buyPassport(20000, "TwoDay");
        Ticket twoDayPassport = buyResultTwoDay.getTicket();
        showTicketIfNeeds(twoDayPassport);
    }

    // uncomment when you implement this exercise
    private void showTicketIfNeeds(Ticket ticket) {
        // #1on1: 思考エクササイズ、MaxDaysだとダメな理由
        //if (ticket.getMaxDays() == 2) {
        // TODO jflute もうちょい進んだらもっかい聞きます (2025/08/26)
        
        // TODO fujisawa java的なれびゅー、文字列の == がダメ by jflute (2025/08/26)
        // (まあここは普段Java書いてなければ忘れやすいところなのでしょうがない)
        // TODO fujisawa ここに "TwoDay" と文字列をハードコードしているのをどうにかしたい by jflute (2025/08/26)
        // スペルミスもそうですし、表現が変わった時に自動で追従されるように
        if (ticket.getTicketType() == "TwoDay") { // write determination for two-day passport
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
    }

    /**
     * Fix it to be able to buy night-only two-day passport (price is 7400), which can be used at only night. <br>
     * (NightOnlyTwoDayPassport (金額は7400) のチケットも買えるようにしましょう。夜しか使えないようにしましょう)
     */
    public void test_class_moreFix_wonder_night() {
        // your confirmation code here
    }

    /**
     * Refactor the code to the best readable code you can think of. <br>
     * (自分の中で思う最高に可読性の高いコードにリファクタリングしてみましょう)
     */
    public void test_class_moreFix_yourRefactoring() {
        // your confirmation code here
    }

    /**
     * Write intelligent JavaDoc comments seriously on the public classes/constructors/methods of the Ticket class. <br>
     * (Ticketクラスのpublicなクラス/コンストラクター/メソッドに、気の利いたJavaDocコメントを本気で書いてみましょう)
     */
    public void test_class_moreFix_yourSuperJavaDoc() {
        // your confirmation code here
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
    }
}
