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

import java.time.LocalDateTime;

/**
 * @author jflute
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // TODO fujisawa インスタンス変数の定義順序、コアな固定の属性と、mutableな状態コントロールの変数を分けるといい by jflute (2025/08/26)
    private final int displayPrice; // written on ticket, park guest can watch this
    private boolean alreadyIn; // true means this ticket is unavailable
    private final String ticketType;
    private int availableDays;
    private LocalDateTime lastUsed;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(int displayPrice, String ticketType) {
        this.displayPrice = displayPrice;
        this.ticketType = ticketType;
        if ("OneDay".equals(ticketType)) {
            availableDays = 1;
        } else if ("TwoDay".equals(ticketType)) {
            availableDays = 2;
        }
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        alreadyIn = true;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    public boolean isAlreadyIn() {
        return alreadyIn;
    }

    public int getAvailableDays() { return availableDays; }

    public String getTicketType() { return ticketType; }
}
