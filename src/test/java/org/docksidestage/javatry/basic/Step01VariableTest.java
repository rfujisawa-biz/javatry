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

import java.math.BigDecimal;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Ryo Fujisawa
 */
public class Step01VariableTest extends PlainTestCase {

    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic 正解
        // 変数にmystic文字列入されており、それがlogで出力されている
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => mystic8:mai 不正解
        // nullは出力されないと思ったが、文字列としてnullが結合されて出力されるのは驚きです
        // [調べたこと] Javaの文字列連結演算子は、null参照を文字列の"null"に変換して連結する -> 予期せぬ挙動につながる恐れがある
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman 正解
        // 値渡しか参照渡しか、勘で値渡しにして正解
        // [調べたこと] seaはlandの参照を受け取り、seaもlandも"oneman"を示す
        // その後、Stringがimmutableなためlandの参照先が"oneman's dreams"になる
        // seaの参照先は"oneman"のままなため、log(sea)はonemanとなる
        // [考えたこと] 値渡し、参照渡しというより、
        // immutableなクラスでは、変更しようとすると新しいインスタンスが作成され、変数は新しいインスタンスを示すようになる。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415 正解
        // 1問前と同じ、変数は値渡し
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land; // sea 415
        sea = land.add(new BigDecimal(1)); // sea 416 
        sea.add(new BigDecimal(1)); // sea 417
        log(sea); // your answer? => 417 不正解
        // よく見たらseaに代入されてない、、、単純な見落としでした、、、
        // sea.add(new BigDecimal(1))は、417を作るはず
        log(sea.add(new BigDecimal(1)));
        // これは417になる
        // [調べたこと] BidDecimalはimmutable
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => null 正解
        // instanceBroadwayは定義されただけで値が代入されていない->デフォルトはnullだと予想
        // logでnullが出力されるのは前の問題で確認済み
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0 正解
        // intのデフォルトは0だと予想
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => null 正解
        // intとIntegerの違い
        // Integerはクラスっぽいので、nullになると予想
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb";
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => null|0|null|magician 不正解
        // 外部の変数は触らないと思って回答
        // 解答見た後よく考えたら、外部の変数というよりクラスにぶら下がってるattributeだから、変更できるってことな気がする
        // kotlinとかも、this.を省略してかけたから、その辺と同じ挙動か？
        // instanceMagiclampは、関数の引数なのでクラスにぶら下がってる値を変更している訳ではない
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor416 不正解
        // immutableって問題に書いてあるからimmutableなんだろうなとメタ読みしたものの、最初に自分が思った解答を記述
        // Stringがimmutableで関数がreturnしている訳でもないから、関数内での変更は外に反映されない？
        String sea2 = helpMethodArgumentImmutableMethodCall2(sea, land);
        log(sea2);
        // こうすればできそう、と思って実装
        // harbor416が出力された
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
        log(sea);
    }

    private String helpMethodArgumentImmutableMethodCall2(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land);
        String sea2 = sea.concat(landStr);
        return sea2;
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => harbor 不正解
        // こっちはmutableなんだろうなとは思ったものの、appendでintのまま結合できるとは思いませんでした。
        // こっちはStringBuilderが参照を渡すのか？
        log(land);
        // landはどうなっているんだろうと思い実行
        // 引数でとっているlandなので、外の値は変更されていないだろうと予想し、予想通り415
        // StringBuilder sea2 = new StringBuilder("harbor");
        // helpMethodArgumentMethodcall2(sea2, land);
        // 前の問題と同様のことができるかどうか確認
        // が、StringBuilder型にはそもそもconcatというメソッドが用意されていない
        // 確認がてら見てみたが、appendメソッドは割となんでも結合できそうですごい、が予期せぬ動作も起こりやすそうな印象
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }
    /*
    private void helpMethodArgumentMethodcall2(StringBuilder sea, int land) {
        ++land;
        String landStr = String.valueOf(land);
        sea.concat(landStr);
    }
    */

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => harbor 正解
        // ヘルパー内ではseaに新しく代入しており、引数として渡しているseaとは別のインスタンスを指定していると予想
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        // sea.append(land); ここでappendを呼べばsea = harbor416になり、最終的な出力はharbor416になる予想をし、正解
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */
    private int piari;

    public void test_variable_writing() {
        // define variables here
        String sea = "mystic";
        Integer land = null;
        log(sea + "," + land + "," + piari);
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * 
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    private String s1 = "abcde";
    private String s2 = "fghij";

    public void test_variable_yourExercise() {
        // write your code here
        String s1 = "abc";
        log(s1 + "," + s2); // => 
    }
}
