package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.swimmer.Swimmer;

/**
 * The object for fish(魚).
 * @author rfujisawa-biz
 */
public class Fish extends Animal implements Swimmer {
    // done fujisawa unusedの警告が出ています by jflute (2025/10/21)

    public Fish() {
    }

    public String getBarkWord() {
        return "...(I cannot bark)";
    }

    @Override
    public String swim() {
        return "...(I am swimming)";
    }
}
