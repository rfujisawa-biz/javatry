package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkingProcess {
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    public BarkedSound bark(Animal animal) {
        breatheIn(animal);
        prepareAbdominalMuscle(animal);
        String barkWord = animal.getBarkWord();
        BarkedSound barkedSound = doBark(barkWord, animal);
        return barkedSound;
    }

    // TODO fujisawa downHitPointの処理
    // #1on1: AnimalのdownHitPoint()をここで呼びたくない...けど、
    // その気持ち悪さの感覚は、とても正しいです。(カプセル化が壊れちゃう)
    // TODO fujisawa 修行++: downHitPoint()のprotectedを崩さず、実現したいところ by jflute (2025/11/07)
    protected void breatheIn(Animal animal) { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        animal.downHitPoint();
    }

    protected void prepareAbdominalMuscle(Animal animal) { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        animal.downHitPoint();
    }

    protected BarkedSound doBark(String barkWord, Animal animal) {
        animal.downHitPoint();
        return new BarkedSound(barkWord);
    }
}
