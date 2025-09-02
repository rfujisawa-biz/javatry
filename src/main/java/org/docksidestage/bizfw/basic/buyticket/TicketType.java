package org.docksidestage.bizfw.basic.buyticket;

import java.time.LocalDateTime;

/**
 * @author rfujisawa-biz
 */

public enum TicketType {
    ONE_DAY,
    TWO_DAY,
    FOUR_DAY,
    NIGHT_ONLY_TWO_DAY;

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
}