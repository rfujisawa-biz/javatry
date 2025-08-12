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
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of data type. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step03DataTypeTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          Basic Type
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_datatype_basicType() {
        String sea = "mystic";
        Integer land = 416;
        LocalDate piari = LocalDate.of(2001, 9, 4);
        LocalDateTime bonvo = LocalDateTime.of(2001, 9, 4, 12, 34, 56);
        Boolean dstore = true;
        BigDecimal amba = new BigDecimal("9.4");

        piari = piari.plusDays(1); // piari 2001-09-05
        land = piari.getYear(); // land 2001
        bonvo = bonvo.plusMonths(1); // bonvo 2001-10-4_12-34-56
        land = bonvo.getMonthValue(); // land 10
        land--; // landの型が何か、、、mutableなら1引かれた数値
        // [考えたこと] landの定義はIntegerだからimmutableなんじゃないか
        // [下のプログラムで検証] IntegerのTYPEはint, intにwrapされてる？
        // そうしたら、land = 9
        if (dstore) { // ここには入る
            BigDecimal addedDecimal = amba.add(new BigDecimal(land)); // addedDecimal 18.4
            sea = String.valueOf(addedDecimal);
        }
        log(sea); // your answer? => 18.4 一応正解
        // [考えたこと] 正解したけど、いまいちよくわかってない。
        // [geminiに投げてみた結果] どうやらJavaのオートアンボクシング、オートボクシングという機能らしい
        // 1. オートアンボクシング: Integer型の変数landから、プリミティブ型のintを取り出す
        // 2. intを減産する
        // 3. 計算結果のintを使って、新しいIntegerオブジェクトが生成される（Integer.valueOf())
        // 4. 再代入: 新しく生成されたIntegerオブジェクトを変数landに代入する
        // よく考えたら、immutableの挙動そのものっぽい？新しい値を代入しようとすると、新たなインスタンスが作成されてそれが代入される
        // land-- -> land = land - 1なので、そのような挙動になってるのか？
        // #1on1 ↑一緒に踏み込んで考えてみた (2025/08/12)
    }

    public void test_datatype_basicType2() {
        Integer land = 416;
        log(land);
        log(Integer.TYPE); // Integerでもintになってる
        int tmp = 417;
        land = tmp;
        log(land);
        log(Integer.TYPE); // landはint
        Integer tmp2 = 418;
        land = tmp2;
        log(land);
        log(Integer.TYPE);
    }

    // ===================================================================================
    //                                                                           Primitive
    //                                                                           =========
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_primitive() {
        byte sea = 127; // max
        short land = 32767; // max
        int piari = 1;
        long bonvo = 9223372036854775807L; // max
        float dstore = 1.1f;
        double amba = 2.3d;
        char miraco = 'a';
        boolean dohotel = miraco == 'a'; // true
        if (dohotel && dstore >= piari) { // dstore 1.1f > piari 1;
            // floatとintが比較できるかどうか、流石にできるでしょ
            bonvo = sea; // bonvo 127
            log("bonvo1", bonvo);
            land = (short) bonvo; // 別の型にキャストされても最大値内なら問題ない予想 land = 127
            log("land", land);
            bonvo = piari; // bonvo 1
            log("bonvo2", bonvo);
            sea = (byte) land; // sea 127
            log("sea1", sea);
            if (amba == 2.3D) { // true
                sea = (byte) amba; // doubleをbyteにしたら整数部分が取り出される？ sea = 2
            }
        }
        if ((int) dstore > piari) { // キャストしたら流石に1に丸められると思い、ここには入らない予想
            sea = 0;
        }
        log(sea); // your answer? => 2 正解
        // [考えたこと] 正解はしたが、いまいち腑に落ちないというか、正しい考え方かどうかが分からない。
        // とりあえず途中経過を全部出力してみる -> 途中経過は一応問題ない
        // 最大値をバイトサイズがおさまっていない別の型にキャストしたらどうなるかテスト -> 全部-1になった
        // 最大値+1をバイトサイズがおさまっていない別の型にキャストしたらどうなるかテスト -> 最大値+1の-1倍になった。
        // （この辺で自分が何を確認したかったのかよく分からなくなってくる。）
        // [geminiに聞いてみた結果] 2の補数表現、上位ビットが切り捨てられて下位ビットが取り出される挙動
        // そういえばそうだったなと言われて思い出した
        // #1on1: Javaのレアな文法知識が必要になるようなコードを書くと読みづらいという教訓 (2025/08/12)
    }

    public void test_datatype_primitive2() {
        short land = 32767; // max
        long bonvo = 9223372036854775807L; // max
        land = (byte) land;
        log(land);
        short bonvo2 = (short) bonvo;
        log(bonvo2);
        byte bonvo3 = (byte) bonvo;
        log(bonvo3);
        bonvo = 32768;
        bonvo = (short) bonvo;
        log(bonvo);
        bonvo = 128;
        bonvo = (byte) bonvo;
        log(bonvo);
    }

    // ===================================================================================
    //                                                                              Object
    //                                                                              ======
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_object() {
        St3ImmutableStage stage = new St3ImmutableStage("hangar");
        String sea = stage.getStageName();
        log(sea); // your answer? => hangar 正解
        // [考えたこと] hangarがコンストラクタに渡されて、stageクラスのstageName属性の値がhangarになっている
        // getStageNameで、stage.stageNameにアクセスして値を取り出している。
    }

    private static class St3ImmutableStage {

        private final String stageName;

        public St3ImmutableStage(String stageName) {
            this.stageName = stageName;
        }

        public String getStageName() {
            return stageName;
        }
    }
}
