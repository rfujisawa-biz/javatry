package org.docksidestage.javatry.basic.st6.os;

/**
 * OperatingSystemの抽象クラス
 * @author rfujisawa-biz
 */

public abstract class St6OperationSystemAbstract {
    protected final String osType;
    protected final String loginId;

    /**
     * コンストラクタ
     * @param osType OSの種類
     * @param loginId ユーザのログインID
     */
    public St6OperationSystemAbstract(String osType, String loginId) {
        this.osType = osType;
        this.loginId = loginId;
    }

    /**
     * ファイルの絶対パスを構築する
     * @param relativePath ファイルの相対パス
     * @return ファイルの絶対パス
     */
    public String buildUserResourcePath(String relativePath) {
        String fileSeparator = getFileSeparator();
        String userDirectory = getUserDirectory();
        String resourcePath = userDirectory + fileSeparator + relativePath;
        return resourcePath.replace("/", fileSeparator);
    }

    protected String getFileSeparator() {
        return "/";
    }

    protected abstract String getUserDirectory();
}
