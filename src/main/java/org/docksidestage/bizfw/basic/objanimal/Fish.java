package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The object for fish(魚).
 * @author rfujisawa-biz
 */
public class Fish extends Animal implements swim {
    // TODO fujisawa unusedの警告が出ています by jflute (2025/10/21)
    private static final Logger logger = LoggerFactory.getLogger(Fish.class);

    public Fish() {
    }

    protected String getBarkWord() {
        return "...(I cannot bark)";
    }

    @Override
    public String doSwim() {
        return "...(I am swimming)";
    }
}
