package org.docksidestage.javatry.basic.st6.os;

/**
 * OldWindowsOSのクラス
 * WindowsOsを継承
 * @author rfujisawa-biz
 */

// 以下の2点で迷った
// 1. 現状実装のように、WindowsOsクラスを継承したOldWindowsOsクラスを作る
// 2. WindowsOsAbstractみたいなクラスを作って、それを継承したWindowsOsクラスとOldWindowsOsクラスを作る
// 1の方がシンプルなので採用。共通なのもPathのセパレータくらいなので。
// OSの種類が増える(Windows95, Windows2000, WindowsXP, WindowsVista, Windows7, Windows8, Windows10, Windows11とか)なら2を採用した方が良さそう
// Windows98, Windows98se, WindowsME, WindowsNT

public class St6OldWindowsOs extends St6WindowsOs {
    public St6OldWindowsOs(String loginId) {
        super(loginId);
    }

    @Override
    protected String getUserDirectory() {
        return "/Documents and Settings/" + loginId;
    }
}
