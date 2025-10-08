package org.docksidestage.javatry.basic.st6.os;

/**
 * 不明なOSのクラス
 * @author rfujisawa-biz
 */
public class St6UnknownOs extends St6OperationSystemAbstract {
    public St6UnknownOs(String osType, String loginId) {
        super(osType, loginId);
    }
    
    @Override
    protected String getFileSeparator() {
        throw new IllegalStateException("Unknown osType: " + osType);
    }

    @Override
    protected String getUserDirectory() {
        throw new IllegalStateException("Unknown osType: " + osType);
    }
}
