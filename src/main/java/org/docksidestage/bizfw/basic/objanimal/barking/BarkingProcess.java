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
