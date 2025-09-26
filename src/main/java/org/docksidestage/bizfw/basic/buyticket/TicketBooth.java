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
    // salesProceedsの初期値0にして、余計なnullチェック無くしたい。。。
//    private Integer change;

    // done fujisawa ここのswitchに関しては、ちょっと頑張れば解消すると思います by jflute (2025/09/08)
    // done jflute TicketTypeに価格と最大使用可能日数をぶら下げることにしたので、ここは不要になりました
//    public static int getPrice(TicketType ticketType) {
//        switch (ticketType) {
//            case ONE_DAY:
//                return ONE_DAY_PRICE;
//            case TWO_DAY:
//                return TWO_DAY_PRICE;
//            case FOUR_DAY:
//                return FOUR_DAY_PRICE;
//            case NIGHT_ONLY_TWO_DAY:
//                return NIGHT_ONLY_TWO_DAY_PRICE;
//            default:
//                return 0;
//        }
//    }

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
        //if (hasTicket(ticketType) && hasSufficientMoney(handedMoney, ticketType)) {
        //    return sellTicket(handedMoney, ticketType);
        //}
        // done fujisawa ここに来ること絶対にない？ (hasメソッドが例外 or trueなので) by jflute (2025/08/26)
        // #1on1: 超少なくとも、一言「ここには来ないのでダミー」みたいなコメント欲しい。
        // #1on1: 一方で、hasの違和感を修正したら、自動的にここも解決するかも
        //return null;
        // TODOが解決したのを確認するために、コメントアウトでコード残してます
    }

    // done fujisawa hasメソッドで、true or 例外は一般的ではないので... by jflute (2025/08/26)
    // hasなら、true or false に限定して、呼び出し側で例外をthrow
    // もしくは、assertTicketExists() みたいなメソッドにしてダメなとき例外
    // のどっちか。
    private boolean hasTicket(TicketType ticketType) {
        // TODO fujisawa 修行++: ここのswitchに関しては、ちょいムズかもなので by jflute (2025/09/08)
        // <del>↑ こっちは、良い方法かどうかは置いておいて、そんなに難しくなく解決はできるかな。</del>
        // いやいや、public のbuyOneDayPassport()たちがもういないスタイルなのでダメだった。
        // e.g.
        //  public TicketBuyResult buyOneDayPassport(int handedMoney) {
        //      return buyPassport(handedMoney, TicketType.ONE_DAY, oneDayQuantity);
        //  }
        int quantity = 0;
        switch (ticketType) {
            case ONE_DAY:
                quantity = oneDayQuantity;
                break;
            case TWO_DAY:
                quantity = twoDayQuantity;
                break;
            case FOUR_DAY:
                quantity = fourDayQuantity;
                break;
            case  NIGHT_ONLY_TWO_DAY:
                quantity = nightOnlyTwoDayQuantity;
                break;
        }
        return quantity > 0;
    }

    private boolean hasSufficientMoney(int handedMoney, TicketType ticketType) {
        int price = ticketType.getPrice();
        return handedMoney >= price;
    }

    private TicketBuyResult sellTicket(int handedMoney, TicketType ticketType) {
        int price = ticketType.getPrice();

        // TODO fujisawa 修行++: ここのswitchに関しては、ちょいムズかもなので by jflute (2025/09/08)
        switch (ticketType) {
            case ONE_DAY:
                --oneDayQuantity;
            case  TWO_DAY:
                --twoDayQuantity;
            case FOUR_DAY:
                --fourDayQuantity;
        }
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
