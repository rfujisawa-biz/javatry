package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkingProcess {
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    private final Animal animal;
    private final Runnable hitPointDowner;

    public BarkingProcess(Animal animal, Runnable hitPointDowner) {
        this.animal = animal;
        this.hitPointDowner = hitPointDowner;
    }

    public BarkedSound bark() {
        breatheIn();
        prepareAbdominalMuscle();
        String barkWord = animal.getBarkWord();
        logger.debug("4: {}", animal.getHitPoint());
        BarkedSound barkedSound = doBark(barkWord);
        logger.debug("5: {}", animal.getHitPoint());
        return barkedSound;
    }

    // done fujisawa downHitPointの処理
    // #1on1: AnimalのdownHitPoint()をここで呼びたくない...けど、
    // その気持ち悪さの感覚は、とても正しいです。(カプセル化が壊れちゃう)
    // done fujisawa 修行++: downHitPoint()のprotectedを崩さず、実現したいところ by jflute (2025/11/07)
    // AIに聞いて提案してもらった実装
    // 処理を渡してそれを実行する、というやり方らしい。
    // downHitPointをラムダ式で渡して、runが呼ばれたらそれが実行される。
    protected void breatheIn() { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        hitPointDowner.run();
    }

    protected void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        hitPointDowner.run();
    }

    protected BarkedSound doBark(String barkWord) {
        hitPointDowner.run();
        return new BarkedSound(barkWord);
    }
}
