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
package org.docksidestage.bizfw.basic.buyticket;

// #1on1: IntelliJのフォーマッターの話。.ideaの話。

import java.util.EnumMap;
import java.util.Map;

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int ONE_DAY_MAX_QUANTITY = 10; // OneDayPassport
    private static final int TWO_DAY_MAX_QUANTITY = 10; // TwoDayPassport OneDayPassport同様10
    private static final int FOUR_DAY_MAX_QUANTITY = 10;
    private static final int NIGHT_ONLY_TWO_DAY_MAX_QUANTITY = 10;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int oneDayQuantity = ONE_DAY_MAX_QUANTITY;
    private int twoDayQuantity = TWO_DAY_MAX_QUANTITY;
    private int fourDayQuantity = FOUR_DAY_MAX_QUANTITY;
    private int nightOnlyTwoDayQuantity = NIGHT_ONLY_TWO_DAY_MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase
    private final Map<TicketType, Integer> ticketQuantityMap = new EnumMap<>(TicketType.class);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
        ticketQuantityMap.put(TicketType.ONE_DAY, ONE_DAY_MAX_QUANTITY);
        ticketQuantityMap.put(TicketType.TWO_DAY, TWO_DAY_MAX_QUANTITY);
        ticketQuantityMap.put(TicketType.FOUR_DAY, FOUR_DAY_MAX_QUANTITY);
        ticketQuantityMap.put(TicketType.NIGHT_ONLY_TWO_DAY, NIGHT_ONLY_TWO_DAY_MAX_QUANTITY);
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    // fujisawa done [事務連絡] 既存コード、問題なければ削除しちゃってOKです by jflute (2025/08/26)

    /**
     * パスポートを買うメソッド
     * @param handedMoney 所持金
     * @param ticketType チケットの種類
     * @throws TicketSoldOutException ブース内のチケットが売り切れ
     * @throws TicketShortMoneyException 買うのに金額が足りない
     * @return TicketBuyResult チケット購入結果(チケットとお釣り)
     */
    public TicketBuyResult buyPassport(int handedMoney, TicketType ticketType) {
        if (!hasTicket(ticketType)) {
            throw new TicketSoldOutException("Sold out");
        }
        if (!hasSufficientMoney(handedMoney, ticketType)) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        return sellTicket(handedMoney, ticketType);
        // done fujisawa ここに来ること絶対にない？ (hasメソッドが例外 or trueなので) by jflute (2025/08/26)
        // #1on1: 超少なくとも、一言「ここには来ないのでダミー」みたいなコメント欲しい。
        // #1on1: 一方で、hasの違和感を修正したら、自動的にここも解決するかも
    }

    // done fujisawa hasメソッドで、true or 例外は一般的ではないので... by jflute (2025/08/26)
    // hasなら、true or false に限定して、呼び出し側で例外をthrow
    // もしくは、assertTicketExists() みたいなメソッドにしてダメなとき例外
    // のどっちか。
    private boolean hasTicket(TicketType ticketType) {
        // done fujisawa 修行++: ここのswitchに関しては、ちょいムズかもなので by jflute (2025/09/08)
        // <del>↑ こっちは、良い方法かどうかは置いておいて、そんなに難しくなく解決はできるかな。</del>
        // いやいや、public のbuyOneDayPassport()たちがもういないスタイルなのでダメだった。
        // e.g.
        //  public TicketBuyResult buyOneDayPassport(int handedMoney) {
        //      return buyPassport(handedMoney, TicketType.ONE_DAY, oneDayQuantity);
        //  }
//        int quantity = 0;
//        switch (ticketType) {
//            case ONE_DAY:
//                quantity = oneDayQuantity;
//                break;
//            case TWO_DAY:
//                quantity = twoDayQuantity;
//                break;
//            case FOUR_DAY:
//                quantity = fourDayQuantity;
//                break;
//            case  NIGHT_ONLY_TWO_DAY:
//                quantity = nightOnlyTwoDayQuantity;
//                break;
//        }
//        return quantity > 0;
        Integer quantity = ticketQuantityMap.get(ticketType);
        return quantity != null && quantity > 0;
    }

    private boolean hasSufficientMoney(int handedMoney, TicketType ticketType) {
        int price = ticketType.getPrice();
        return handedMoney >= price;
    }

    private TicketBuyResult sellTicket(int handedMoney, TicketType ticketType) {
//        int price = ticketType.getPrice();
//
//        // done fujisawa 修行++: ここのswitchに関しては、ちょいムズかもなので by jflute (2025/09/08)
//        switch (ticketType) {
//            case ONE_DAY:
//                --oneDayQuantity;
//                break;
//            case  TWO_DAY:
//                --twoDayQuantity;
//                break;
//            case FOUR_DAY:
//                --fourDayQuantity;
//                break;
//            case NIGHT_ONLY_TWO_DAY:
//                --nightOnlyTwoDayQuantity;
//                break;
//            default:
//                throw new IllegalStateException("Unknown ticket type: " + ticketType);
//        }
//        Ticket ticket = new Ticket(ticketType);
//        if (salesProceeds != null) {
//            salesProceeds = salesProceeds + price;
//        } else {
//            salesProceeds = price;
//        }
//        int change = handedMoney - price;
//        return new TicketBuyResult(ticket, change);
        int price = ticketType.getPrice();

        // #1on1: ConcurrentHashMapのときのcompute()のコード少しだけ読んでみた (2026/02/06)
        // マルチスレッド環境だと、このメソッドが大事になってくる。
        // 下手にget/setしてしまうと、Mapでどんだけ頑張ってもダメなので、アプリでsynchronizedになっちゃう。
        //synchronized (this) {
        //    Integer quantity = ticketQuantityMap.get(ticketType);
        //    if (quantity == null || quantity <= 0) {
        //        throw new TicketSoldOutException("Sold out: " + ticketType);
        //    }
        //    ticketQuantityMap.put(ticketType, quantity - 1);
        //}
        ticketQuantityMap.compute(null, (k, quantity) -> {
            if (quantity == null || quantity <= 0) {
                throw new TicketSoldOutException("Sold out: " + ticketType);
            }
            return quantity - 1;
        });

        Ticket ticket = new Ticket(ticketType);
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
        int change = handedMoney - price;
        return new TicketBuyResult(ticket, change);
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    // TODO fujisawa getするところも、mapから取るようにしないと by jflute (2026/02/06)
    public int getOneDayQuantity() {
        return oneDayQuantity;
    }

    public int getTwoDayQuantity() {
        return twoDayQuantity;
    }

    public int getFourDayQuantity() {
        return fourDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
