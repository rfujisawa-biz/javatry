package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fish extends Animal {
    private static final Logger logger = LoggerFactory.getLogger(Fish.class);

    public Fish() {
    }

    protected String getBarkWord() {
        return "...";
    }
}
