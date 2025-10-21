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

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of method. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step04MethodTest extends PlainTestCase {

    // ===================================================================================
    //                                                                         Method Call
    //                                                                         ===========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_method_call_basic() {
        String sea = supplySomething();
        log(sea); // your answer? => over 正解
        // supplySomethingは、overをreturnしている
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_call_many() {
        String sea = functionSomething("mystic"); // sea: mysmys
        consumeSomething(supplySomething());
        runnableSomething();
        log(sea); // your answer? => mysmys 正解
        // functionSomethingは、ticをmysに置き換えた文字列を返す
        // それ以外は、関数の返した値を代入していないため、seaの値は変わらず
    }

    private String functionSomething(String name) {
        String replaced = name.replace("tic", "mys"); // mystic -> mysmys
        log("in function: {}", replaced);
        return replaced;
    }

    private String supplySomething() {
        String sea = "over";
        log("in supply: {}", sea);
        return sea;
    }

    private void consumeSomething(String sea) {
        log("in consume: {}", sea.replace("over", "mystic"));
    }

    private void runnableSomething() {
        String sea = "outofshadow";
        log("in runnable: {}", sea);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_object() {
        St4MutableStage mutable = new St4MutableStage();
        int sea = 904;
        boolean land = false;
        helloMutable(sea - 4, land, mutable); // mutable.stageName = "mystic"
        if (!land) { // land = falseなので入る
            sea = sea + mutable.getStageName().length(); // sea: 904, mutable.stageName.length()は6 sea = 910
        }
        log(sea); // your answer? => 910 正解
        // [考えたこと] 特に迷うことなく、愚直にプログラムを読み進めました。
    }

    private int helloMutable(int sea, Boolean land, St4MutableStage piari) { // sea, landは引数に触ってるだけなので関数外には関係ない
        sea++;
        land = true;
        piari.setStageName("mystic"); // ここだけは、piariのstageName属性に触っている & stageName属性はfinalがついてないのでmutable
        return sea;

        // done fujisawa 直接的に関係ないけど、finalっていうキーワードでimmutabilityって難しいよねということをふと思ったので共有 by shiny (2025/08/13)
        // 参照先のオブジェクトは変更できないが、要素の変更可能って直感的にちょっとわかりにくかったりする
        // 変更検知ベースの実装とかすると、Mutateしてるけどしてない（伝われ）というhackyなことができなくもないので、どう可読性を保ちつつ実装するかが悩ましい
        
        // #1on1: shinyさんの言葉を元に immutability の難しさ/ジレンマについて話した。(2025/08/26)
        // そして、shinyさんの使う言葉が難しくて勉強になるねという話。
        
        // done shiny Mutateしてるけどしてない（伝わりました） by hal (2025/08/27)
    }

    private static class St4MutableStage {

        private String stageName;

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private int inParkCount;
    private boolean hasAnnualPassport;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_instanceVariable() {
        hasAnnualPassport = true;
        int sea = inParkCount;
        offAnnualPassport(hasAnnualPassport); // 関数内では引数のhasAnnualPassportをいじっているだけなのでtrueのまま
        for (int i = 0; i < 100; i++) {
            goToPark(); // i=0からi=99まで, inParkCount = 100
        }
        ++sea; // seaはseaが定義されたときのinParkCountの値を持っているはずなので、ここではsea = 1
        sea = inParkCount; // inParkCount = 100で上書きされてsea = 100
        log(sea); // your answer? => 100 正解
        // [考えたこと] ここも特に悩むことなく愚直にコードを読み進めました。
    }

    // #1on1: mutableなインスタンス変数を引数で渡したり直接使ったりが混ざってるとわかりにくい(既存コード) (2025/08/26)
    // 徹底してimmutable寄りにするか、逆に状態を変化させるメソッドという統一的な実装をするか？
    private void offAnnualPassport(boolean hasAnnualPassport) {
        hasAnnualPassport = false;
    }

    private void goToPark() {
        if (hasAnnualPassport) {
            ++inParkCount;
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    // write instance variables here
    /**
     * Make private methods as followings, and comment out caller program in test method:
     * <pre>
     * o replaceAwithB(): has one argument as String, returns argument replaced "A" with "B" as String 
     * o replaceCwithB(): has one argument as String, returns argument replaced "C" with "B" as String 
     * o quote(): has two arguments as String, returns first argument quoted by second argument (quotation) 
     * o isAvailableLogging(): no argument, returns private instance variable "availableLogging" initialized as true (also make it separately)  
     * o showSea(): has one argument as String argument, no return, show argument by log()
     * </pre>
     * (privateメソッドを以下のように定義して、テストメソッド内の呼び出しプログラムをコメントアウトしましょう):
     * <pre>
     * o replaceAwithB(): 一つのString引数、引数の "A" を "B" に置き換えたStringを戻す 
     * o replaceCwithB(): 一つのString引数、引数の "C" を "B" に置き換えたStringを戻す 
     * o quote(): 二つのString引数、第一引数を第二引数(引用符)で囲ったものを戻す 
     * o isAvailableLogging(): 引数なし、privateのインスタンス変数 "availableLogging" (初期値:true) を戻す (それも別途作る)  
     * o showSea(): 一つのString引数、戻り値なし、引数をlog()で表示する
     * </pre>
     */

    private boolean availableLogging = true;

    public void test_method_making() {
        // use after making these methods
        String replaced = replaceCwithB(replaceAwithB("ABC"));
        String sea = quote(replaced, "'");
        if (isAvailableLogging()) {
            showSea(sea);
        }
    }

    // write methods here
    // done fujisawa [いいね] privateメソッドの定義順序が呼び出しと一致してて直感的 by jflute (2025/08/26)
    // #1on1: クラスの一番下におじゃまします感でメソッド追加がナンバーワンパターンとして多いが...
    // 途中で修正する人でもクラスの全体バランスを考えて積上げていかないと、将来キメラのようなクラスになる。
    // #1on1: authorの話にもつなげて
    public String replaceAwithB(String str) {
        String replaced = str.replace("A", "B");
        return replaced;
    }

    // done fujisawa [いいね] 第二引数 quote がわかりやすい by jflute (2025/08/26)
    public String quote(String str, String quote) {
        String quoted = quote + str + quote;
        return quoted;
    }

    public boolean isAvailableLogging() {
        return availableLogging;
    }
    
    public String replaceCwithB(String str) {
        String replaced = str.replace("C", "B");
        return replaced;
    }

    public void showSea(String str) {
        log(str);
    }

}
