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

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int ONE_DAY_MAX_QUANTITY = 10; // OneDayPassport
    private static final int TWO_DAY_MAX_QUANTITY = 10; // TwoDayPassport OneDayPassport同様10
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int oneDayQuantity = ONE_DAY_MAX_QUANTITY;
    private int twoDayQuantity = TWO_DAY_MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase
    // salesProceedsの初期値0にして、余計なnullチェック無くしたい。。。
//    private Integer change;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
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
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public void buyOneDayPassport(Integer handedMoney) {
        if (oneDayQuantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < ONE_DAY_PRICE) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        --oneDayQuantity; // 所持金を確認してからチケットを減らす
        if (salesProceeds != null) { // second or more purchase
            salesProceeds = salesProceeds + ONE_DAY_PRICE; // ONE_DAY_PRICEを加算するように修正
        } else { // first purchase
            salesProceeds = ONE_DAY_PRICE; // ONE_DAY_PRICEを加算するように修正
        }
    }

//     とりあえず、buyOneDayPassportと同様に実装したbuyTwoDayPassportメソッド
    public int buyTwoDayPassport(Integer handedMoney) {
        if (twoDayQuantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < TWO_DAY_PRICE) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        --twoDayQuantity;
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + TWO_DAY_PRICE;
        } else {
            salesProceeds = TWO_DAY_PRICE;
        }
        return handedMoney - TWO_DAY_PRICE;
    }

    public TicketBuyResult buyPassport(int handedMoney, String ticketType) {
        if (hasTicket(ticketType) && hasSufficientMoney(handedMoney, ticketType)) {
            return sellTicket(handedMoney, ticketType);
        }
        return null;
    }

    private boolean hasTicket(String ticketType) {
        int quantity = 0;
        if ("OneDay".equals(ticketType)) {
            quantity = oneDayQuantity;
        } else if ("TwoDay".equals(ticketType)) {
            quantity = twoDayQuantity;
        }
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        } else {
            return true;
        }
    }

    private boolean hasSufficientMoney(int handedMoney, String ticketType) {
        int price = 0;
        if ("OneDay".equals(ticketType)) {
            price = ONE_DAY_PRICE;
        }  else if ("TwoDay".equals(ticketType)) {
            price = TWO_DAY_PRICE;
        }
        if (handedMoney < price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        } else {
            return true;
        }
    }

    private TicketBuyResult sellTicket(int handedMoney, String ticketType) {
        int price = 0;
        if ("OneDay".equals(ticketType)) {
            --oneDayQuantity;
            price = ONE_DAY_PRICE;
        } else if ("TwoDay".equals(ticketType)) {
            --twoDayQuantity;
            price = TWO_DAY_PRICE;
        }
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
//        return handedMoney - price;
        Ticket ticket = new Ticket(price, ticketType);
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
    public int getOneDayQuantity() {
        return oneDayQuantity;
    }

    public int getTwoDayQuantity() {
        return twoDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }

//    public Integer getChange() {
//        return change;
//    }
}
