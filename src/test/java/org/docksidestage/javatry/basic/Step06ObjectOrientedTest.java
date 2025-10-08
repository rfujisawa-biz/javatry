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

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketBuyResult;
import org.docksidestage.bizfw.basic.buyticket.TicketType;
import org.docksidestage.bizfw.basic.objanimal.*;
import org.docksidestage.bizfw.basic.objanimal.loud.AlarmClock;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.javatry.basic.st6.dbms.St6MySql;
import org.docksidestage.javatry.basic.st6.dbms.St6PostgreSql;
import org.docksidestage.javatry.basic.st6.dbms.St6Sql;
import org.docksidestage.javatry.basic.st6.os.*;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of object-oriented. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step06ObjectOrientedTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        About Object
    //                                                                        ============
    // -----------------------------------------------------
    //                                        Against Object
    //                                        --------------
    /**
     * Fix several mistakes (except simulation) in buying one-day passport and in-park process. <br>
     * (OneDayPassportを買って InPark する処理の中で、(simulationを除いて)間違いがいくつかあるので修正しましょう)
     */
    public void test_objectOriented_aboutObject_againstObject() {
        //
        // [ticket booth info]
        //
        // simulation: actually these variables should be more wide scope
        int oneDayPrice = 7400;
        int quantity = 10;
        Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // simulation: actually this money should be from customer
        int handedMoney = 10000;
        if (quantity <= 0) {
            throw new IllegalStateException("Sold out");
        }

        if (handedMoney < oneDayPrice) {
            throw new IllegalStateException("Short money: handedMoney=" + handedMoney);
        }
        --quantity; // 料金が支払えることを確認してから枚数を減らす
        salesProceeds = oneDayPrice; // handedMoneyではなくoneDayPriceに変更

        //
        // [ticket info]
        //
        // simulation: actually these variables should be more wide scope
        int displayPrice = oneDayPrice;
        boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        // simulation: actually this process should be called by other trigger
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        }
        alreadyIn = true;

        // TODO done fujisawa あと4箇所間違いがある by jflute (2025/09/26)
        // #1on1: その場で一つ見つけた(変数)、さらに見つけた(例外)、残り2箇所 (2025/09/26)
        //
        // [final process]
        //
//        saveBuyingHistory(quantity, displayPrice, salesProceeds, alreadyIn); // 順番違う
        saveBuyingHistory(quantity, salesProceeds, displayPrice, alreadyIn);
    }

    // メソッドが全ての変数の状態を保持しなきゃいけないから大変（書くのも読むのも）

    private void saveBuyingHistory(int quantity, Integer salesProceeds, int displayPrice, boolean alreadyIn) {
        if (alreadyIn) {
            // simulation: only logging here (normally e.g. DB insert)
            // 引数違う
//            showTicketBooth(displayPrice, salesProceeds);
//            showYourTicket(quantity, alreadyIn);
            showTicketBooth(quantity, salesProceeds);
            showYourTicket(displayPrice, alreadyIn);
        }
    }

    private void showTicketBooth(int quantity, Integer salesProceeds) {
        log("Ticket Booth: quantity={}, salesProceeds={}", quantity, salesProceeds);
    }

    private void showYourTicket(int displayPrice, boolean alreadyIn) {
        log("Ticket: displayPrice={}, alreadyIn={}", displayPrice, alreadyIn);
    }

    // -----------------------------------------------------
    //                                          Using Object
    //                                          ------------
    /**
     * Read (analyze) this code and compare with the previous test method, and think "what is object?". <br>
     * (このコードを読んで(分析して)、一つ前のテストメソッドと比べて、「オブジェクトとは何か？」を考えてみましょう)
     */
    public void test_objectOriented_aboutObject_usingObject() {
        //
        // [ticket booth info]
        //
        TicketBooth booth = new TicketBooth();

        // *booth has these properties:
        //int oneDayPrice = 7400;
        //int quantity = 10;
        //Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // if step05 has been finished, you can use this code by jflute (2019/06/15)
        //Ticket ticket = booth.buyOneDayPassport(10000);
        int handedMoney = 10000;
        TicketType ticketType = TicketType.ONE_DAY;
        TicketBuyResult ticketBuyResult = booth.buyPassport(handedMoney, ticketType);
//        booth.buyOneDayPassport(10000); // as temporary, remove if you finished step05
//        Ticket ticket = new Ticket(7400); // also here
//        Ticket ticket = null;
        Ticket ticket = ticketBuyResult.getTicket();

        // *buyOneDayPassport() has this process:
        //if (quantity <= 0) {
        //    throw new TicketSoldOutException("Sold out");
        //}
        //if (handedMoney < oneDayPrice) {
        //    throw new TicketShortMoneyException("Short money: handedMoney=" + handedMoney);
        //}
        //--quantity;
        //salesProceeds = handedMoney;

        // *ticket has these properties:
        //int displayPrice = oneDayPrice;
        //boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        ticket.doInPark();

        // *doInPark() has this process:
        //if (alreadyIn) {
        //    throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        //}
        //alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(booth, ticket);
    }

    private void saveBuyingHistory(TicketBooth booth, Ticket ticket) {
        if (ticket.isAlreadyIn()) {
            // only logging here (normally e.g. DB insert)
            doShowTicketBooth(booth);
            doShowYourTicket(ticket);
        }
    }

    private void doShowTicketBooth(TicketBooth booth) {
        log("Ticket Booth: quantity={}, salesProceeds={}", booth.getOneDayQuantity(), booth.getSalesProceeds());
    }

    private void doShowYourTicket(Ticket ticket) {
        log("Your Ticket: displayPrice={}, alreadyIn={}", ticket.getDisplayPrice(), ticket.isAlreadyIn());
    }

    // write your memo here:
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // what is object?
    // 処理や属性を、分類ごとにまとめるもの
    // 責務が分離されたクラスごとの振る舞いを追いかければ良いため、プログラムの流れが理解しやすくなる
    // _/_/_/_/_/_/_/_/_/_/
    // #1on1: このエクササイズを作った背景の話、オブジェクトとは？を意識することの大切さ。
    // TODO jflute 間違い探しが全部終わってからさらに深堀り (2025/09/26)

    // ===================================================================================
    //                                                              Polymorphism Beginning
    //                                                              ======================
    /**
     * What string is sea and land variable at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_objectOriented_polymorphism_1st_concreteOnly() {
        Dog dog = new Dog();
        BarkedSound sound = dog.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = dog.getHitPoint();
        log(land); // your answer? => 7 3回downHitPointされる
        // 正解
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_2nd_asAbstract() {
        Animal animal = new Dog();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
        // 正解
        // Dog型はAnimal型を継承しているから、Animal型に代入できると予想
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_3rd_fromMethod() {
        Animal animal = createAnyAnimal();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
        // 正解
        // 結局、animalにはDog型が代入されていると予想
    }

    private Animal createAnyAnimal() {
        return new Dog();
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_4th_toMethod() {
        Dog dog = new Dog();
        doAnimalSeaLand_for_4th(dog);
    }

    private void doAnimalSeaLand_for_4th(Animal animal) {
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
        // 正解
        // Dog型はAnimal型を継承しているので、Animal型の引数に渡せると予想
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_5th_overrideWithSuper() {
        Animal animal = new Cat();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => nya-
        int land = animal.getHitPoint();
        log(land); // your answer? => 5
        // 正解
        // hitPointの推移
        // 10 -> 9 (breatheIn) -> 8 (prepareAbdominalMuscle) -> 7 (downHitPoint()) -> 6 (doBark) -> 5 (downHitPoint())
        // downHitPoint()がCatクラスでオーバーライドされ、hitPointが偶数の時はもう一度downHitPointが呼ばれる
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_6th_overriddenWithoutSuper() {
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => uooo
        int land = animal.getHitPoint();
        log(land); // your answer? => -1
        // 正解
        // ざっくりプログラムを見て、do nothingを見つけられてハッピーでした
    }

    /**
     * What is happy if you can assign Dog or Cat instance to Animal variable? <br>
     * (Animal型の変数に、DogやCatなどのインスタンスを代入できると何が嬉しいのでしょう？)
     */
    public void test_objectOriented_polymorphism_7th_whatishappy() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is happy?
        // 同じ機能を持ったクラスをまとめて扱える
        // 今回の場合では、Animalに該当するものは、鳴き声を発し、体力を消費するという共通の機能を持っている
        // 同じ処理をまとめて扱えることで、コードの再利用性が高まり、保守性も向上する
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: 現実の世界でも似たような抽象化して(便利に)取り扱うってことはよくやっている (2025/09/26)
        // それをプログラミングの中でも使いたいだけ、と考えると自然なことと思えるかも。
    }

    // ===================================================================================
    //                                                              Polymorphism Interface
    //                                                              ======================
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_dispatch() {
        Loudable loudable = new Zombie();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => uooo
        String land = ((Zombie) loudable).bark().getBarkWord();
        log(land); // your answer? => uooo
        // 正解
        // なんじゃんこりゃ、と思ったが抽象クラスのメソッド呼び出し
        // Animalクラスを見に行ったらちゃんとSoundLoudlyがあった
        // return bark().getBarkWord();なので、barkWordが返ってくる
        // 後半もなんじゃこりゃ、と思ったので、barkWordが帰ってくると予想をした
        // キャストしてなんかしてる
        // [調べたこと] TODO done fujisawa あとで調べてください by fujisawa (2025/09/02)
        // https://qiita.com/tukine_T/items/4461cb75adc36fa4c0b6
        // > 各インスタンスにしかない関数を使いたい時にダウンキャストは使用される
        // ここ大事そう
        // 親クラスで定義された変数に、子クラスを代入した場合は、子クラスで特有に定義されているメソッドを呼んだりできない。
        // 親クラスにそんなメソッドは定義されていないから。
        // そのような時に、ダウンキャストして子クラスとしてメソッドを呼べるようにする

        // #1on1: ((Zombie) loudable) ダウンキャストと呼ぶ。
        // e.g. Dog dog = (Dog)animal;
        // ダウンキャストは基本的には危険な行為。ギャンブルに負ければ、ClassCastExceptionになる。
        // なので、実際ダウンキャストするときは、他の要因から「絶対にこれでしょ」といい切れるときだけやる。
        // もしくは、こういう風に、ifで分岐させてダウンキャスト:
        //  if (animal instanceof Dog) {
        //      Dog dog = (Dog)animal;
        //      dog.ote();
        //  }
        /* ダウンキャストの例、step11より
Object content = boxSpace.getContent();
if (content instanceof String) {
    int length = ((String) content).length();
    
}
         */
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_hierarchy() {
        Loudable loudable = new AlarmClock();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => jiri jiri jiri---
        boolean land = loudable instanceof Animal;
        log(land); // your answer? => false
        // 正解
        // AlarmClockクラスは、loudableインターフェースを実装しているが、別にAnimalクラスを継承しているわけではない
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_partImpl() {
        Animal seaAnimal = new Cat();
        Animal landAnimal = new Zombie();
        boolean sea = seaAnimal instanceof FastRunner;
        log(sea); // your answer? => true
        boolean land = landAnimal instanceof FastRunner;
        log(land); // your answer? => false
        // 正解
        // わからん、インターフェースのインスタンスというのか、、、？
        // 1/2で回答
        // TODO done fujisawa あとで調べてください by fujisawa (2025/09/02)
        // geminiへの質問
        // javaで、X instanceOf Yとしたときに、YがクラスでもインターフェースでもXがYのインスタンスのときはtrueになりますね。
        // インターフェースのインスタンスってのが気持ち悪い気もしますが、そういうもん、だと思うしかないのか。

        // geminiの回答の抜粋
        // > 考え方のポイント
        // > instanceof は、そのオブジェクトが特定の「契約」や「振る舞い」を持っているかどうかをチェックする仕組みです。
        // > クラスの場合: neko instanceof Animal
        // >   これは「nekoオブジェクトはAnimalクラスから作られましたか（またはそのサブクラスから作られましたか）？」という問いです。これは直感的ですね。
        // > インターフェースの場合: neko instanceof Runnable
        // >   これは「nekoオブジェクトはRunnableという契約を守っていますか（Runnableインターフェースを実装したクラスから作られましたか）？」という問いになります。
        // >   nekoオブジェクトはRunnableインターフェースが定めるrun()メソッドを持っているため、「Runnableという型（役割）として扱うことができる」わけです。
        // > instanceofは、メモリ上にある実際のオブジェクトが、指定された型（クラスまたはインターフェース）に代入可能かどうかを調べている、と考えると良いでしょう。
    }

    /**
     * Make Dog class implement FastRunner interface. (the method implementation is same as Cat class) <br>
     * (DogもFastRunnerインターフェースをimplementsしてみましょう (メソッドの実装はCatと同じで))
     */
    public void test_objectOriented_polymorphism_interface_runnerImpl() {
        Animal animal = new Dog();
        boolean sea = animal instanceof FastRunner;
        log(sea);
        // Catクラスと同様にDogクラスを実装
    }

    /**
     * What is difference as concept between abstract class and interface? <br>
     * (抽象クラスとインターフェースの概念的な違いはなんでしょう？)
     */
    public void test_objectOriented_polymorphism_interface_whatisdifference() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is difference?
        // TODO fujisawa あとでやる by fujisawa (2025/09/02)
        // 抽象クラスは、継承を前提に抽象的なクラスとして存在している？
        // インターフェースは、クラスに対して汎用的な機能を付加するために存在する？
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: まず、文法の違いは互いに色々と持っているが...
        // ポリモーフィズムで抽象化して取り扱うって面ではめっちゃ似てる。
        // TODO jflute 次回1on1にて (2025/09/26)
    }

    // ===================================================================================
    //                                                                 Polymorphism Making
    //                                                                 ===================
    /**
     * Make concrete class of Animal, which is not FastRunner, in "objanimal" package. (implementation is as you like) <br>
     * (FastRunnerではないAnimalクラスのコンクリートクラスをobjanimalパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeConcrete() {
        // your confirmation code here
        // コンクリートクラス...？というところから。抽象クラスの対義語だろうけど
        // コンクリートクラス: インスタンス化できるクラス
        // Fishクラスを実装
        Animal fish = new Fish();
        BarkedSound sound = fish.bark();
        assertEquals("...(I cannot bark)", sound.getBarkWord());
    }

    /**
     * Make interface implemented by part of Animal concrete class in new package under "objanimal" package. (implementation is as you like) <br>
     * (Animalクラスの一部のコンクリートクラスだけがimplementsするインターフェースをobjanimal配下の新しいパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeInterface() {
        // your confirmation code here
        // swimインターフェースを作成
        Fish fish = new Fish();
        String swimming = fish.doSwim();
        assertEquals("...(I am swimming)", swimming);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Extract St6MySql, St6PostgreSql (basic.st6.dbms)'s process to abstract class (as super class and sub-class) <br>
     * (St6MySql, St6PostgreSql (basic.st6.dbms) から抽象クラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_generalization_extractToAbstract() {
        St6Sql st6MySql = new St6MySql();
        St6Sql st6PostgreSql = new St6PostgreSql();
        int pageSize = 10;
        int pageNumber = 10;
        String actualSt6MySql = "limit 90, 10";
        String actualSt6PostgreSql = "offset 90 limit 10";

        assertEquals(actualSt6MySql, st6MySql.buildPagingQuery(pageSize, pageNumber));
        assertEquals(actualSt6PostgreSql, st6PostgreSql.buildPagingQuery(pageSize, pageNumber));
    }

    /**
     * Extract St6OperationSystem (basic.st6.os)'s process to concrete classes (as super class and sub-class) <br>
     * (St6OperationSystem (basic.st6.os) からコンクリートクラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_specialization_extractToConcrete() {
        String osType = "Mac"; // Mac, Windows, OldWindows, Other
        String loginId = "rfujisawa-biz";
        String relativePath = "/Downloads/sea.jpg";
        St6OperationSystemAbstract os;
        if ("Mac".equals(osType)) {
            os = new St6MacOs(loginId);
       } else if ("Windows".equals(osType)) {
            os = new St6WindowsOs(loginId);
        } else if ("OldWindows".equals(osType)) {
            os = new St6OldWindowsOs(loginId);
        } else {
            os = new St6UnknownOs(osType, loginId);
        }
        String actual = os.buildUserResourcePath(relativePath);
        String expectValue = "/Users/rfujisawa-biz/" + relativePath;
        String expect = expectValue; // Macの場合
//        String expect = expectValue.replace("/", "\\"); // Windowsの場合
//        String expect = expectValue.replace("/Users/", "/Documents and Settings/").replace("/", "\\"); // OldWindowsの場合
        assertEquals(expect, actual);
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Extract Animal's bark() process as BarkingProcess class to also avoid large abstract class. <br>
     * (抽象クラス肥大化を抑制するためにも、Animalのbark()のプロセス(処理)をBarkingProcessクラスとして切り出しましょう)
     */
    public void test_objectOriented_writing_withDelegation() {
        // your confirmation code here
    }

    /**
     * Put barking-related classes, such as BarkingProcess and BarkedSound, into sub-package. <br>
     * (BarkingProcessやBarkedSoundなど、barking関連のクラスをサブパッケージにまとめましょう)
     * <pre>
     * e.g.
     *  objanimal
     *   |-barking
     *   |  |-BarkedSound.java
     *   |  |-BarkingProcess.java
     *   |-loud
     *   |-runner
     *   |-Animal.java
     *   |-Cat.java
     *   |-Dog.java
     *   |-...
     * </pre>
     */
    public void test_objectOriented_writing_withPackageRefactoring() {
        // your confirmation code here
    }

    /**
     * Is Zombie correct as sub-class of Animal? Analyze it in thirty seconds. (thinking only) <br>
     * (ゾンビは動物クラスのサブクラスとして適切でしょうか？30秒だけ考えてみましょう (考えるだけでOK))
     */
    public void test_objectOriented_zoo() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // is it correct?
        //
        // _/_/_/_/_/_/_/_/_/_/
    }
}
