package org.docksidestage.bizfw.basic.objanimal.swimmer;

// done fujisawa package, objanimal配下の xxx パッケージに移動しましょう (要件の通り) by jflute (2025/10/21)

// done fujisawa 一応、クラス名インターフェース名は先頭大文字が慣習 by jflute (2025/10/21)
// IntelliJのrename機能を使ってリファクタリングしてみましょう。
// done fujisawa 動詞そのままではなく、Swimmer とか Swimable とかJavaの慣習に倣ってもらえればと by jflute (2025/10/21)
// e.g. Throwable, Comparable
// 一方で、メソッドの方は、純粋に swim() でも良いかなと。
/**
 * @author rfujisawa-biz
 */

public interface Swimmer {
    String swim();
}
