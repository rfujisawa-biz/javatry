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
        // done fujisawa [いいね] 調べてこと書いてあると嬉しいです。 by jflute (2025/07/22)
        // 予期せぬ挙動、まさしく昔のインターネット画面では、「こんにちは null さん」とかよく出てました。
        // 例外(エラー)になるのが一番安全かもですが、それはそれで都度チェックするのも大変かもと。
        // 空文字で出すのか？nullで出すのか？例えばC#だと空文字になります。
        // 一方で、mystic8:mai だと間違いがあった風に見えなくて気付きにくいという面もあります。
        // 逆に、JavaScriptはnullだけじゃなくundefinedとかまで表示されたりもします。
        // 些細な言語の挙動に違いではありますが、こういうところそれぞれ思想があって違いが生まれるのかもですね。
        // #1on1: Pythonだと空文字だったかも!?、C# だと空文字

        // TODO fujisawa [不思議だよね] ちゃんと正直に答えててえらいです！ by shiny (2025/08/13)
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
        // done fujisawa [ふぉろー] そうですね、Stringがimmutableだからってのが大きいかもですね by jflute (2025/07/22)
        // Stringで+したときは、新しいインスタンスが戻されるということで、seaが参照しているインスタンスに変化はないと。
        //
        // Object型 (Stringなど) の変数は、メモリ上のどこかに置いてあるインスタンスへの参照(アドレス)を持っています。
        // sea は、"mystic" というStringインスタンスへの参照(アドレス)を持っていて、
        // land は、"oneman" というStringインスタンスへの参照(アドレス)を持っています。
        // sea = land; をすると、landが持っている参照(アドレス)を、seaにコピーします。
        // その一瞬だけ、sea も land も同じインスタンスを参照することになります。(同じアドレス持っている)
        // また、"mystic" のStringインスタンスは誰からも参照されなくなります。
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

        // #1on1: JavaDocの表示
        // #1on1: IntelliJでメソッド補完時にcontrolJでjavadocの表示
        // #1on1: ソースコードリーディング: 構造を把握して、フォーカス読み

        // done fujisawa [いいね] immutable, そのとおりです。 by jflute (2025/07/22)
        // immutableの場合は変化させるメソッドを呼び出しても戻り値で受け取らなかったら意味がないのでご注意を。

        // done jflute 1on1にて、immutableの話を深堀りする予定 (2025/07/22)
        // (↑このtodoはくぼ用のtodoなのでそのまま置いておいてください)
        
        // immutableのメリットって？ Ans. 勝手に書き換わらない、一部を改ざんされちゃうを防ぐ
        // A. 安全性: read-only想定で値を渡したりもらったりすること多い => まさしくミスを防ぐ
        // B. 可読性: read-only想定で値を... => 読み飛ばしができる (余計な心配をしなくていい)
        // C. 管理が世話ない: 状態が変化するのを追うのは、人間にはきつい
        //  → 人間都合
        //
        // immutableのデメリットって？
        // A. インスタンスを大量に使う → メモリ使う (現代だとインフラが成長してきて気にしなくなってきた)
        // B. sea.add(new BigDecimal(1)); が書けちゃう (mutableと混ざることによるデメリット)
        // C. immutableにしやすいかどうか？が言語仕様に依存する (100% immutableを実現できるか？)
        //    (immutableにしようとすると逆に手間が掛かることもある)
        // (Scalaのimplicitパラメーターについて余談)
        // 個人的に、Javaだと8:2の感覚。
        //
        // 変数のimmutable
        // インスタンスのimmutable
        
        // Rust: 変数側で制御
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
        // done fujisawa [ふぉろー] test_側のinstanceMagiclampは、インスタンス変数 (attribute) なので... by jflute (2025/07/22)
        // やろうと思えばクラス内のインスタンスメソッドであればどこからでも変更することは可能です。
        // ただ、helpメソッド側のinstanceMagiclampは、(たまたま名前が同じだけの)引数としてのローカル変数なので、
        // その変数に対して再代入で参照先を新しく変えても、インスタンス変数のinstanceMagiclampは全く別の変数なので無関係であると。
        // メソッドを呼び出すの引数は、変数が渡ってるというよりかは「インスタンスが渡っている」(厳密にはアドレスだけが渡っている)、
        // というニュアンスですね。
        // done fujisawa IDE上の変数の色にも着目してみてください。 by jflute (2025/07/22)
        // インスタンス変数と、ローカル変数では、フォントカラーが変わっているかと思います。
        // #1on1: Pythonとの比較 (エディターの歴史/背景が違う)
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
        // done fujisawa [いいね] "immutableって問題に書いてあるから" って気付いたこと素晴らしいです by jflute (2025/07/22)
        // また、それにも関わらず自分が思った解答をされたというのも素晴らしいです(^^。
        //
        // "harbor"インスタンスは、helpの中にも渡っていってconcat()は呼ばれますが、
        // BigDecimalと同じでimmutableなので、連結した値を新しく戻すだけで"harbor"インスタンス自身は変化しないです。
        // ちなみに、戻り値で受け取っていれば答えは変化するのか？ってところで言うと、
        // 仮に sea = sea.concat(landStr); と変えたとしても、にしても、seaは引数としてのローカル変数であって、
        // test_メソッド側のsea変数とは(名前がたまたま同じだけの)別変数なので、やはり答えは変わりません。
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
        log(sea);
    }

    // done fujisawa [いいね] こういう風にお試し実装書いてみるのGood by jflute (2025/07/22)
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
        // done fujisawa [ふぉろー] append()がオーバーロードという文法で色々な引数を受け取れるようになっています。 by jflute (2025/07/22)
        // ぜひ、IDE上で append() のところにカーソルを置いてJavaDocを表示してみてください。
        // append(int i) を呼び出してることがわかります。中では数値を文字列に変換してappendしています。
        // (ソースコード追ってみて単純だろうと思ったら、意外にややこしいことしてるぞぅ: Integer.getChars()など)
        log(land);
        // landはどうなっているんだろうと思い実行
        // 引数でとっているlandなので、外の値は変更されていないだろうと予想し、予想通り415
        // StringBuilder sea2 = new StringBuilder("harbor");
        // helpMethodArgumentMethodcall2(sea2, land);
        // 前の問題と同様のことができるかどうか確認
        // が、StringBuilder型にはそもそもconcatというメソッドが用意されていない
        // 確認がてら見てみたが、appendメソッドは割となんでも結合できそうですごい、が予期せぬ動作も起こりやすそうな印象
        // done fujisawa [ふぉろー] land は int ということで、Javaではプリミティブ型というデータになって... by jflute (2025/07/22)
        // インスタンスという概念は存在せず、変数にそのまま415という値が入っていて、変わるときは416という別に値が入るみたいになります。
        // ++land; は、単純に land = land + 1 をやっているだけなので、新しい値に差し替えているだけとなります。
        // そしてここでも、help側のlandは引数としてのローカル変数で、test_側のlandとは別変数なので、全く操作の影響は受けません。
        // done fujisawa [いいね] 確かに、オーバーロードメソッドでどんな型でも入っちゃう作りになっているので... by jflute (2025/07/22)
        // ついついちゃんと業務ルール通りの文字列に変換してからappendしないといけないってときもスッと入っちゃうので怖いですね。
        // オーバーロードは、StringBuilderとか本当に単純で汎用的なクラスで使われる感じで、
        // 個人的には普段はそこまでオーバーロードのメソッドを自分で作ることは少ない印象です。
        // メソッドを補完しててもメソッド名が同じで引数が違うってわかりづらいところあるんですね。
        // (StringBuilderのようなクラスであればよいのですが)
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
        // done fujisawa [いいね] yes, new していれば100%別インスタンスですからね by jflute (2025/07/22)
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
     * メソッド終了時に出力される文字列は？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    @SuppressWarnings("unused") // ちょっとIDE上で警告でちゃってノイズになるので
    private String s1 = "abcde";
    private String s2 = "fghij";

    public void test_variable_yourExercise() {
        // write your code here
        String s1 = "abc";
        log(s1 + "," + s2); // => ?
    }
    // 自分でやってみましたが、メソッド内ではローカル変数が優先されます。
    // done fujisawa [いいね] そうですね、イメージとしては近いほうが優先されるみたいな by jflute (2025/07/22)
    // もうちょい丁寧に言うと、スコープが短いほうが優先されます。変数の隠蔽ですね。
}
