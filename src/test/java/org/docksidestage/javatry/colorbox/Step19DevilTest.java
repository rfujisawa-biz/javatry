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

import java.math.BigDecimal;
import java.util.List;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Devil with color-box, (try if you woke up Devil in StringTest) <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step19DevilTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        Devil Parade
    //                                                                        ============
    /**
     * What is the content in low space of color-box
     * of which lengths of the color is same as first place number of BigDecimal value first found in List in box spaces,
     * that the second decimal place is same as tens place of depth of the color-box
     * of which color name ends with third character of color-box that contains null as content? <br>
     * (nullを含んでいるカラーボックスの色の名前の3文字目の文字で色の名前が終わっているカラーボックスの深さの十の位の数字が小数点第二桁目になっている
     * スペースの中のリストの中で最初に見つかるBigDecimalの一の位の数字と同じ色の長さのカラーボックスの一番下のスペースに入っているものは？) <br>
     * 
     * addition: (same meaning)
     *  1. colorbox(lets call it A) that contains null 
     *  2. colorbox(B) that name ends with third character of colorbox A
     *  3. colorbox(C) that the list in the space contains the first BigDecimal that 2nd decimal place is same as tens place of depth of color-box B
     *  4. colorbox(D) that length of name is same as 1st place number of the target BigDecimal value found in the colorbox C
     *  5. the question is what is in the lowest space of colorbox D?
     */
    public void test_too_long() {
        // 最初にステップごとに整理した。1を書いたらAIが補完してくれた。
        // 特に↓以上の指示はしていないので、各ステップでカラーボックスが複数あることは考えず、最初に見つかったものを対象にしている。

        // 1. カラーボックスの中から、nullを含んでいるカラーボックスを見つける
        // 2. そのカラーボックスの色の名前の3文字目の文字を取得する
        // 3. カラーボックスの中から、色の名前が2.で取得した文字で終わっているカラーボックスを見つける
        // 4. そのカラーボックスの深さの十の位の数字を取得する
        // 5. カラー�ボックスの中から、スペースの中のリストの中で最初に見つかるBigDecimalの2nd decimal placeが4.で取得した数字と同じものを見つける
        // 6. そのカラーボックスの色の長さを取得する
        // 7. カラーボックスの中から、色の長さが6.で取得した数字と同じカラーボックスを見つける
        // 8. そのカラーボックスの一番下のスペースに入っているものを取得する

        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // 1. カラーボックスの中から、nullを含んでいるカラーボックスを見つける
        ColorBox nullContentColorBox = null;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                if (boxSpace.getContent() == null) {
                    nullContentColorBox = colorBox;
                    break;
                }
            }
            if (nullContentColorBox != null) {
                break;
            }
        }
        if (nullContentColorBox == null) {
            log("null content color-box is not found!");
            return;
        }

        // 2. そのカラーボックスの色の名前の3文字目の文字を取得する
        String nullContentColorName = nullContentColorBox.getColor().getColorName();
        char thirdCharacter = nullContentColorName.charAt(2);

        // 3. カラーボックスの中から、色の名前が2.で取得した文字で終わっているカラーボックスを見つける
        ColorBox endsWithThirdCharacterColorBox = null;
        for (ColorBox colorBox : colorBoxList) {
            String colorName = colorBox.getColor().getColorName();
            if (colorName.endsWith(String.valueOf(thirdCharacter))) {
                endsWithThirdCharacterColorBox = colorBox;
                break;
            }
        }
        if (endsWithThirdCharacterColorBox == null) {
            log("ends-with target color-box is not found!");
            return;
        }

        // 4. そのカラーボックスの深さの十の位の数字を取得する
        int depth = endsWithThirdCharacterColorBox.getSize().getDepth();
        int tensPlaceOfDepth = depth / 10 % 10;

        // 5. カラーボックスの中から、スペースの中のリストの中で最初に見つかるBigDecimalの2nd decimal placeが4.で取得した数字と同じものを見つける
        BigDecimal targetDecimal = null;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (!(content instanceof List)) {
                    continue;
                }

                List<?> contentList = (List<?>) content;
                for (Object element : contentList) {
                    if (!(element instanceof BigDecimal)) {
                        continue;
                    }

                    BigDecimal decimal = (BigDecimal) element;
                    int secondDecimalPlace = decimal.abs()
                            .movePointRight(2)
                            .intValue() % 10;
                    if (secondDecimalPlace == tensPlaceOfDepth) {
                        targetDecimal = decimal;
                        break;
                    }
                }
                if (targetDecimal != null) {
                    break;
                }
            }
            if (targetDecimal != null) {
                break;
            }
        }
        if (targetDecimal == null) {
            log("target BigDecimal is not found!");
            return;
        }

        // 6. そのBigDecimalの一の位の数字を取得する
        int onesPlaceOfDecimal = targetDecimal.abs().intValue() % 10;

        // 7. カラーボックスの中から、色の長さが6.で取得した数字と同じカラーボックスを見つける
        ColorBox sameLengthColorBox = null;
        for (ColorBox colorBox : colorBoxList) {
            String colorName = colorBox.getColor().getColorName();
            if (colorName.length() == onesPlaceOfDecimal) {
                sameLengthColorBox = colorBox;
                break;
            }
        }
        if (sameLengthColorBox == null) {
            log("same length color-box is not found!");
            return;
        }

        // 8. そのカラーボックスの一番下のスペースに入っているものを取得する
        List<BoxSpace> spaceList = sameLengthColorBox.getSpaceList();
        Object lowerContent = spaceList.get(spaceList.size() - 1).getContent();
        log(lowerContent);
    }

    // ===================================================================================
    //                                                                      Java Destroyer
    //                                                                      ==============
    /**
     * What string of toString() is BoxSize of red color-box after changing height to 160 (forcedly in this method)? <br>
     * ((このテストメソッドの中だけで無理やり)赤いカラーボックスの高さを160に変更して、BoxSizeをtoString()すると？)
     */
    public void test_looks_like_easy() {
        
    }

    // ===================================================================================
    //                                                                        Meta Journey
    //                                                                        ============
    /**
     * What value is returned from no-parameter functional method of interface that has FunctionalInterface annotation in color-boxes? <br> 
     * (カラーボックスに入っているFunctionalInterfaceアノテーションが付与されているインターフェースの引数なしのFunctionalメソッドの戻り値は？)
     */
    public void test_be_frameworker() {
    }
}
