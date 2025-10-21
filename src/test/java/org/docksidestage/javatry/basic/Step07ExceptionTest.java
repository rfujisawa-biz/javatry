/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.basic;

import org.docksidestage.bizfw.basic.supercar.SupercarClient;
import org.docksidestage.javatry.basic.st7.St7BasicExceptionThrower;
import org.docksidestage.javatry.basic.st7.St7ConstructorChallengeException;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step07ExceptionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_basic_catchfinally() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        StringBuilder sea = new StringBuilder();
        try {
            thrower.land();
            sea.append("dockside");
        } catch (IllegalStateException e) {
            sea.append("hangar");
        } finally {
            sea.append("broadway");
        }
        log(sea); // your answer? => hangarbroadway 正解
        // throwe.land()の中でoneman()がIllegalStateExceptionを呼ぶのでcatchに入る
        // finallyはいずれにせよ最終的に呼ばれるので、hangarbroadwayになる
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_basic_message() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        String sea = null;
        try {
            thrower.land();
            fail("no way here");
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? => oneman at showbase 正解
        // oneman()がIllegalStateExceptionを投げるので、そのメッセージが代入される
    }

    /**
     * What class name and method name and row number cause the exception? (you can execute and watch logs) <br>
     * (例外が発生したクラス名とメソッド名と行番号は？(実行してログを見て良い))
     */
    public void test_exception_basic_stacktrace() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        try {
            thrower.land();
            fail("no way here");
        } catch (IllegalStateException e) {
            log(e);
        }
        // your answer? => St7BasicExceptionThrower.oneman(), line 40
    }

    // ===================================================================================
    //                                                                           Hierarchy
    //                                                                           =========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_hierarchy_Runtime_instanceof_RuntimeException() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof RuntimeException;
        log(sea); // your answer? => true
        // IllegalStateExceptionはRuntimeExceptionを継承しているので
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Exception() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => true
        // IllegalStateExceptionはRuntimeExceptionを継承し、
        // RuntimeExceptionはExceptionを継承しているので
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Error() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Error;
        log(sea); // your answer? => false
        // 継承していないので
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Throwable() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Throwable;
        log(sea); // your answer? => true
        // Throwableを継承しているので
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Throwable_instanceof_Exception() {
        Object exp = new Throwable("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => false
        // ThrowableはExceptionを継承していないので
    }

    // ===================================================================================
    //                                                                         NullPointer
    //                                                                         ===========
    /**
     * What variable (is null) causes the NullPointerException? And what row number? (you can execute and watch logs) <br>
     * (NullPointerが発生する変数(nullだった変数)と、発生する行番号は？(実行してログを見ても良い))
     */
    public void test_exception_nullpointer_basic() {
        try {
            String sea = "mystic";
            String land = sea.equals("mystic") ? null : "oneman";
            String lowerCase = land.toLowerCase();
            log(lowerCase);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? => landがnull, 142行目
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_nullpointer_headache() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman";
            String piari = !!!sea.equals("mystic") ? "plaza" : null;
            int sum = land.length() + piari.length();
            log(sum);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? => piariがnull, 156行目
    }

    /**
     * Refactor to immediately understand what variable (is null) causes the NullPointerException by row number in stack trace. <br>
     * (どの変数がNullPointerを引き起こしたのか(nullだったのか)、スタックトレースの行番号だけでわかるようにリファクタリングしましょう)
     */
    public void test_exception_nullpointer_refactorCode() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman";
            String piari = !!!sea.equals("mystic") ? "plaza" : null;
            int landLength = land.length();
            int piariLength = piari.length();
            int sum = landLength + piariLength;
            log(sum);
        } catch (NullPointerException e) {
            log(e);
        }
    }

    // ===================================================================================
    //                                                                   Checked Exception
    //                                                                   =================
    /**
     * Show canonical path of new java.io.File(".") by log(), and if I/O error, show message and stack-trace instead <br>
     * (new java.io.File(".") の canonical path を取得してログに表示、I/Oエラーの時はメッセージとスタックトレースを代わりに表示)
     */
    // TODO fujisawa あとで
    public void test_exception_checkedException_basic() {
    }

    // ===================================================================================
    //                                                                               Cause
    //                                                                               =====
    /**
     * What string is sea variable in the catch block?
     * And What is exception class name displayed at the last "Caused By:" of stack trace? <br>
     * (catchブロックの変数 sea, land の中身は？また、スタックトレースの最後の "Caused By:" で表示されている例外クラス名は？)
     */
    public void test_exception_cause_basic() {
        String sea = "mystic";
        String land = "oneman";
        try {
            throwCauseFirstLevel();
            fail("always exception but none");
        } catch (IllegalStateException e) {
            Throwable cause = e.getCause();
            sea = cause.getMessage();
            land = cause.getClass().getSimpleName();
            log(sea); // your answer? => ここはわからなかった
            log(land); // your answer? =>
            log(e); // your answer? => 
        }
    }

    private void throwCauseFirstLevel() {
        int symbol = Integer.MAX_VALUE - 0x7ffffffe; // 1
        try {
            throwCauseSecondLevel(symbol);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to call the second help method: symbol=" + symbol, e); // symbol 1
        }
    }

    private void throwCauseSecondLevel(int symbol) {
        try {
            --symbol; // 0
            symbol--; // -1
            if (symbol < 0) {
                throwCauseThirdLevel(symbol);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to call the third help method: symbol=" + symbol, e); // symbol -1のはず
        }
    }

    private void throwCauseThirdLevel(int symbol) {
        if (symbol < 0) {
            Integer.valueOf("piari"); // ここで、NumberFormatException
        }
    }

    // ===================================================================================
    //                                                                         Translation
    //                                                                         ===========
    /**
     * Execute this test and read exception message and write situation and cause on comment for non-programmer. <br>
     * テストを実行して例外メッセージを読んで、プログラマーでない人にもわかるように状況と原因をコメント上に書きましょう。
     */
    public void test_exception_translation_debugChallenge() {
        try {
            new SupercarClient().buySupercar();
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
            // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
            // What happens? Write situation and cause here. (何が起きた？状況と原因をここに書いてみましょう)
            // - - - - - - - - - -
            //
            //
            //
            // _/_/_/_/_/_/_/_/_/_/
        }
    }

    /**
     * Improve exception handling in supercar's classes to understand the situation
     * by only exception information as far as possible. <br>
     * できるだけ例外情報だけでその状況が理解できるように、Supercarのクラスたちの例外ハンドリングを改善しましょう。
     */
    public void test_exception_translation_improveChallenge() {
        try {
            new SupercarClient().buySupercar(); // you can fix the classes
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Fix terrible (YABAI in Japanese) exception handling. (you can modify exception class) <br>
     * (やばい例外ハンドリングがあるので修正しましょう (例外クラスを修正してOK))
     */
    public void test_exception_writing_constructorChallenge() {
        try {
            helpSurprisedYabaiCatch();
        } catch (St7ConstructorChallengeException e) {
            log("Thrown by help method", e); // should show also "Caused-by" information
        }
    }

    private void helpSurprisedYabaiCatch() {
        try {
            helpThrowIllegalState();
        } catch (IllegalStateException e) {
            throw new St7ConstructorChallengeException("Failed to do something.");
        }
    }

    private void helpThrowIllegalState() {
        if (true) { // simulate something illegal
            String importantValue = "dummy"; // important to debug
            throw new IllegalStateException("something illegal: importantValue=" + importantValue);
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What is the concept difference between Exception and Error? Write it on comment. <br>
     * (ExceptionとErrorのコンセプトの違いはなんでしょうか？コメント上に書きましょう)
     */
    public void test_exception_zone_differenceExceptionError() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // Write here. (ここに書いてみましょう)
        // - - - - - - - - - -
        //
        //
        //
        // _/_/_/_/_/_/_/_/_/_/
    }
}
