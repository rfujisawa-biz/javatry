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
package org.docksidestage.javatry.colorbox;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author rfujisawq-biz
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() { // example, so begin from the next method
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            String answer = colorBoxList.stream()
                    .findFirst()
                    .map(colorBox -> colorBox.getColor().getColorName())
                    .map(colorName -> colorName.length() + " (" + colorName + ")")
                    .orElse("*not found");
            log(answer);
        } else {
            log("colorBoxList is empty!");
        }
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMax_colorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            int maxLength = colorBoxList.stream()
                    .mapToInt(box -> box.getColor().getColorName().length())
                    .max()
                    .getAsInt();

            // done fujisawa yellowが二つ出てきちゃう by jflute (2026/02/06)
            List<String> colorNamesWithMaxLength = colorBoxList.stream()
                    .map(box -> box.getColor().getColorName())
                    .filter(name -> name.length() == maxLength)
                    .distinct()
                    .collect(Collectors.toList());
            log(colorNamesWithMaxLength);

            // #1on1: 一本のstreamでできるか？思考トレーニング...だけどちょっと厳しそう (2026/02/06)
            // 最初の1件とかであれば、ソートしてfirstを取るでいいけど、同率首位を綺麗に切り取るとなったら...
            // やっぱり事前にmaxLengthが欲しくなるかな!?
            // 超パフォーマンス求められる場面だと、ループ一回で済ませられるfor文if文が活躍することあるかも。
        } else {
            log("colorBoxList is empty!");
        }
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax_stringContent() {
        // #1on1: StringAPI内でのinstanceofやgetContent()呼び出しの重複排除などの話 (2026/02/20)
        // TODO done fujisawa 落ちてるところ直してもらえればと by jflute (2026/02/20)
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            int maxLength = colorBoxList.stream()
                    .flatMap(box -> box.getSpaceList().stream())
                    .filter(space -> space.getContent() instanceof String)
                    .mapToInt(space -> space.getContent().toString().length())
                    .max()
                    .orElse(0);

            List<String> stringsWithMaxLength = colorBoxList.stream()
                    .flatMap(box -> box.getSpaceList().stream())
                    .filter(space -> space.getContent() instanceof String)
                    .filter(space -> space.getContent().toString().length() == maxLength)
                    .map(space -> space.getContent().toString())
                    .collect(Collectors.toList());

            // #1on1: boxごとにspaceのfilterをするのではなく、spaceのflat一覧を作ってfilterの方が... (2026/02/06)
            // streamが1本線になるので、見栄えはスッキリするかなと。
            //List<String> collect = colorBoxList.stream()
            //        .flatMap(box -> box.getSpaceList().stream())
            //        .filter(space -> space.getContent().toString().length() == maxLength)
            //        .map(space -> space.getContent().toString())
            //        .collect(Collectors.toList());
            log("maxLength: " + maxLength + ", stringsWithMaxLength: " + stringsWithMaxLength);
        } else {
            log("colorBoxList is empty!");
        }
    }

    /**
     * Which value (toString() if non-string) has second-max length in color-boxes? (latter if same length) <br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (同じ長さのものがあれば後の方を))
     */
    public void test_length_findSecondMax_contentToString() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            // 初手に出してきた、filterでStringのみを絞っているので間違い
//            int secondMax = colorBoxList.stream()
//                    .flatMap(box -> box.getSpaceList().stream())
//                    .filter(space -> space.getContent() instanceof String)
//                    .mapToInt(space -> space.getContent().toString().length())
//                    .sorted()
//                    .skip(1)
//                    .findFirst()
//                    .orElse(0);
            int secondMax = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .filter(space -> space.getContent() != null)
                .map(space -> space.getContent().toString())
                .mapToInt(str -> str.length())
                .boxed()
                .sorted(Comparator.reverseOrder()) // 降順にソート
                .skip(1) // 先頭をスキップして二番目を取得
                .findFirst()
                .orElse(0);

            String secondMaxString = colorBoxList.stream()
                    .flatMap(box -> box.getSpaceList().stream())
                    .filter(space -> space.getContent() != null)
                    .map(space -> space.getContent().toString())
                    .filter(str -> str.length() == secondMax)
                    .skip(1)
                    .findFirst()
                    .orElseGet(() -> colorBoxList.stream()
                            .flatMap(box -> box.getSpaceList().stream())
                            .filter(space -> space.getContent() != null)
                            .map(space -> space.getContent().toString())
                            .filter(str -> str.length() == secondMax)
                            .findFirst()
                            .orElse(null));
            log("secondMaxLength: " + secondMax + ", secondMaxString: " + secondMaxString);
        } else {
            log("colorBoxList is empty!");
        }
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            int lengthSum = colorBoxList.stream()
                    .flatMap(box -> box.getSpaceList().stream())
                    .filter(space -> space.getContent() instanceof String)
                    .mapToInt(space -> space.getContent().toString().length())
                    .sum();
            log("lengthSum: " + lengthSum);
        } else {
            log("colorBoxList is empty!");
        }
    }

    // ===================================================================================
    //                                                                      Pickup Methods
    //                                                                      ==============
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            String answer = colorBoxList.stream()
                    .filter(box -> box.getSpaceList().stream()
                            .anyMatch(space -> space.getContent() instanceof String && ((String) space.getContent()).startsWith("Water")))
                    .findFirst() // 箱が2つあったらどうしよう
                    .map(box -> box.getColor().getColorName())
                    .orElse("*not found");
            log(answer);
        } else {
            log("colorBoxList is empty!");
        }
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
// TODO 次ここから
        } else {
            log("colorBoxList is empty!");
        }
    }

    // ===================================================================================
    //                                                                 Welcome to Guardian
    //                                                                 ===================
    /**
     * What is total length of text of GuardianBox class in color-boxes? <br>
     * (カラーボックスの中に入っているGuardianBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToGuardian() {
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    // has small #adjustmemts from ClassicStringTest
    // comment out because of too difficult to be stream?
    ///**
    // * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_flat() {
    //}
    //
    ///**
    // * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_nested() {
    //}
}
