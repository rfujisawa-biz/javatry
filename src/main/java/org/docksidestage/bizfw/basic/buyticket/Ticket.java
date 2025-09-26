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

import java.awt.font.FontRenderContext;
import java.time.LocalDate;
import java.time.LocalDateTime; // NightOnlyのために、時間も必要かも

/**
 * @author jflute
 * @author rfujisawa-biz
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // done fujisawa インスタンス変数の定義順序、コアな固定の属性と、mutableな状態コントロールの変数を分けるといい by jflute (2025/08/26)
//    private final int displayPrice; // written on ticket, park guest can watch this
    private final TicketType ticketType;

    private boolean alreadyIn; // true means this ticket is unavailable
    private int availableDays;
    private LocalDate lastUsed;

    // TODO done fujisawa static final はインスタンス変数よりも上に定義する慣習がある by jflute (2025/09/08)
    // TODO jflute TicketTypeが管理するようになったので不要になりました
//    private static final int ONE_DAY_MAX_AVAILABLE_DAYS = 1;
//    private static final int TWO_DAY_MAX_AVAILABLE_DAYS = 2;
//    private static final int FOUR_DAY_MAX_AVAILABLE_DAYS = 4;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // TODO done fujisawa @param に : が入ってると名前が一致しなくなっちゃう by jflute (2025/09/08)
    /**
     * Ticketクラス
     * @param ticketType チケットの種別
     */
    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;

        // TODO fujisawa ここのswitchに関しては、ちょっと頑張れば解消すると思います by jflute (2025/09/08)
        // TODO jflute availabeDaysはticketTypeから取得 by fujisawa (2025/09/11)
        this.availableDays = ticketType.getMaxAvailableDays();
//        switch (ticketType) {
//        case ONE_DAY:
//            availableDays = ONE_DAY_MAX_AVAILABLE_DAYS;
//            break;
//        case TWO_DAY:
//            availableDays = TWO_DAY_MAX_AVAILABLE_DAYS;
//            break;
//        case NIGHT_ONLY_TWO_DAY:
//            availableDays = TWO_DAY_MAX_AVAILABLE_DAYS;
//            break;
//        case FOUR_DAY:
//            availableDays = FOUR_DAY_MAX_AVAILABLE_DAYS;
//            break;
//        }
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    /**
     *　チケットを使って入園する
     * @throws IllegalStateException すでに入園している場合
     * @throws InvalidTicketUsageException 利用可能な日数が0の場合、またはチケットが現在の日時に利用できない場合
     */
    public void doInPark() {
        validateTicketUsage(LocalDateTime.now());
        alreadyIn = true;
        LocalDate today = LocalDate.now();

        if (lastUsed == null) {
            lastUsed = today;
            availableDays--;
        } else if (!today.equals(lastUsed)) {
            availableDays--;
        }
    }

    // #1on1: 利用者がチケットの有効性を判断できても良いだろうということでpublic (2025/09/08)
    // 現時点でpublicとして使ってる人がいないのであれば、コメントでpublicの理由があるといいかも。
    // 2パターン: 概念的に最初からpublicにするパターン、必要になってからpublicにするパターン
    // 業務のコードの話とOSSのコードの話の例。
    /**
     * チケットが利用可能かどうかを検証する
     * @param dateTime チケットを利用する日時
     */
    public void validateTicketUsage(LocalDateTime dateTime) {
        if (alreadyIn) {
            throw new IllegalStateException("This ticket is currently in use.");
        }
        if (availableDays <= 0) {
            throw new InvalidTicketUsageException("This ticket is no longer valid as its usage limit has been reached.");
        }
        if (!ticketType.canBeUsedAt(dateTime)) {
            throw new InvalidTicketUsageException("This ticket is not valid at the current time.");
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    // TODO done fujisawa ここは説明は省略して、@return だけ書くでもOK by jflute (2025/09/08)
    /**
     * @return チケット料金
     */
    public int getDisplayPrice() {
        return ticketType.getPrice();
    }

    /**
     * @return 入園済みかどうか
     */
    public boolean isAlreadyIn() {
        return alreadyIn;
    }

    /**
     * @return 使用可能日数
     */
    public int getAvailableDays() { return availableDays; }

    /**
     * @return チケット種別
     */
    public TicketType getTicketType() { return ticketType; }

    /**
     * チケットの不適切な仕様に対する例外
     */
    public static class InvalidTicketUsageException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InvalidTicketUsageException(String msg) {
            super(msg);
        }
    }
}
