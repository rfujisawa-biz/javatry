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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.color.BoxColor;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, not using Stream API. <br>
 * Show answer by log() for question of javadoc. <br>
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step11ClassicStringTest extends PlainTestCase {
    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * How many lengths does color name of first color-boxes have? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() { // example, so begin from the next method
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (!colorBoxList.isEmpty()) {
            ColorBox colorBox = colorBoxList.get(0);
            BoxColor boxColor = colorBox.getColor();
            String colorName = boxColor.getColorName();
            int answer = colorName.length();
            log(answer + " (" + colorName + ")"); // also show name for visual check
        } else {
            log("*not found");
        }
    }

    // 5 green

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMax_colorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // #1on1: GoodGood, 同率首位の配慮までしっかりされているし... (2026/02/20)
        // 実行結果の安定性を配慮して順序を固定化している。
        // (SQLのorder byの固定化のソートキーのお話も)
        Set<String> namesWithMaxLength = new LinkedHashSet<>(); // 重複排除 + 登場順維持
        int maxLength = 0;

        for (ColorBox colorBox : colorBoxList) {
            BoxColor boxColor = colorBox.getColor();
            String colorName = boxColor.getColorName();
            int length = colorName.length();
            if (length > maxLength) {
                maxLength = length;
                namesWithMaxLength.clear();
                namesWithMaxLength.add(colorName);
            } else if (length == maxLength) {
                namesWithMaxLength.add(colorName);
            }
        }
        log(namesWithMaxLength);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax_stringContent() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        Set<String> contentsWithMaxLength = new LinkedHashSet<>();
        int maxLength = 0;
        
        // 一旦愚直に
        // #1on1: 2重ループがちょっと気持ち悪いけど...どうしても処理としては消えない (2026/02/20)
        // なので、見た目をちょっと改善するだけ、例えば、1ループの処理をメソッドやクラスに切り出したりとかの工夫はある。
        // ちなみに、ネステッドループとも言う。(SQLでjoinするときに駆動表となんとか表でネステッドループとか)
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String strContent = (String) content;
                    int length = strContent.length();
                    if (length > maxLength) {
                        maxLength = length;
                        contentsWithMaxLength.clear();
                        contentsWithMaxLength.add(strContent);
                    } else if (length == maxLength) {
                        contentsWithMaxLength.add(strContent);
                    }
                }
            }
        }
        
        log("maxLength: " + maxLength + ", contents: " + contentsWithMaxLength);
    }

    /**
     * Which value (toString() if non-string) has second-max length in color-boxes? (latter if same length) <br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (同じ長さのものがあれば後の方を))
     */
    public void test_length_findSecondMax_contentToString() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        String firstMax = null;
        String secondMax = null;
        int firstMaxLength = -1;
        int secondMaxLength = -1;

        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content == null) {
                    continue;
                }
                // #1on1: StringのときはtoString()を無駄に呼ばないのはGood (2026/02/20)
                String strContent = (content instanceof String) ? (String) content : content.toString();
                int length = strContent.length();

                if (length > firstMaxLength) {
                    secondMax = firstMax;
                    secondMaxLength = firstMaxLength;
                    firstMax = strContent;
                    firstMaxLength = length;
                } else if (length == firstMaxLength) {
                    // 同じ長さの場合は後の方を優先
                    secondMax = firstMax;
                    secondMaxLength = firstMaxLength;
                    firstMax = strContent;
                } else if (length > secondMaxLength) {
                    secondMax = strContent;
                    secondMaxLength = length;
                } else if (length == secondMaxLength) {
                    // 同じ長さの場合は後の方を優先
                    secondMax = strContent;
                }
            }
        }

        log(secondMax);
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // #1on1: もし、0件の状態と、あったけど空文字だった場合の区別を付けるならnullとか使ったり話 (2026/02/20)
        int sum = 0;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    sum += ((String) content).length();
                }
            }
        }

        log(sum);
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
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // #1on1: LinkedHashSetのコンストラクターの闇(dummy) (2026/02/20)
        Set<BoxColor> hitColorSet = new LinkedHashSet<>(); // 重複なし + 登場順維持

        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String strContent = (String) content;
                    if (strContent.startsWith("Water")) {
                        hitColorSet.add(colorBox.getColor());
                        // #1on1: この配慮とても素晴らしい。パフォーマンス的には絶対にやった方が良い (2026/02/20)
                        break; // この箱はヒット確定なので、同じ箱の残りスペースは見ない
                    }
                }
            }
        }

        log(hitColorSet);
    }

    /**
     * What number character is starting with the late "ど" of string containing two or more "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
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
    // these are very difficult exercises so you can skip
    /**
     * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_flat() {
    }

    /**
     * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_nested() {
    }
}
