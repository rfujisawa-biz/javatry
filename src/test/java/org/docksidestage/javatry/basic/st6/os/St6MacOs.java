package org.docksidestage.javatry.basic.st6.os;

/**
 * MacOsのクラス
 * @author rfujisawa-biz
 */

public class St6MacOs extends St6OperationSystemAbstract {

    public St6MacOs(String loginId) {
        super("Mac", loginId);
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
