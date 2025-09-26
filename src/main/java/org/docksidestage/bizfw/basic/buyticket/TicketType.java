package org.docksidestage.bizfw.basic.buyticket;

import java.time.LocalDateTime;

// #1on1: enumの仕組み的な本質、実はほぼ単なるクラス。

/**
 * @author rfujisawa-biz
 */
public enum TicketType {
    ONE_DAY(7400, 1),
    TWO_DAY(13200, 2),
    FOUR_DAY(22400, 4),
    NIGHT_ONLY_TWO_DAY(7400, 2);

    private final int price;
    private final int maxAvailableDays;

    TicketType(int price, int availableDays) {
        this.price = price;
        this.maxAvailableDays = availableDays;
    }

    /**
     * チケットが指定された日時に利用可能か判定する
     * @param dateTime 現在の日時
     * @return 利用可能な場合はtrue, そうでない場合はfalse
     */
    public boolean canBeUsedAt(LocalDateTime dateTime) {
        if (this == NIGHT_ONLY_TWO_DAY) {
            int hour = dateTime.getHour();
            return hour >= 18 && hour < 22;
        }
        return true;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxAvailableDays() {
        return maxAvailableDays;
    }
}