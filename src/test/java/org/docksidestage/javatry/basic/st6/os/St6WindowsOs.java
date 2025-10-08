package org.docksidestage.javatry.basic.st6.os;

/**
 * WindowsOsのクラス
 * @author rfujisawa-biz
 */
public class St6WindowsOs extends St6OperationSystemAbstract{
    public St6WindowsOs(String loginId) {
        super("Windows", loginId);
    }

    @Override
    protected String getFileSeparator() {
        return "\\";
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
