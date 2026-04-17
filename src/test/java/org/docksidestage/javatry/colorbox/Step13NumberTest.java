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

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Number with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step13NumberTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * How many integer-type values in color-boxes are between 0 and 54 (includes)? <br>
     * (カラーボックスの中に入っているInteger型で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_IntegerOnly() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        int count = 0;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Integer) {
                    int number = (Integer) content;
                    if (0 <= number && number <= 54) {
                        ++count;
                    }
                }
            }
        }
        log(count);
    }

    public void test_countZeroToFiftyFour_IntegerOnly_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        long count = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent) // ラムダ式ではなくメソッド参照で書いてある
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .filter(number -> 0 <= number && number <= 54)
                .count();
        log(count);
    }

    /**
     * How many number values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っている数値で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_Number() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        int count = 0;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Number) {
                    Number number = (Number) content;
                    double value = number.doubleValue();
                    if (0 <= value && value <= 54) {
                        ++count;
                    }
                }
            }
        }
        log(count);
    }

    public void test_countZeroToFiftyFour_Number_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        long count = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(Number.class::isInstance)
                .map(Number.class::cast)
                .mapToDouble(Number::doubleValue)
                .filter(value -> 0 <= value && value <= 54)
                .count();
        log(count);
    }

    /**
     * What color name is used by color-box that has integer-type content and the biggest width in them? <br>
     * (カラーボックスの中で、Integer型の Content を持っていてBoxSizeの幅が一番大きいカラーボックスの色は？)
     */
    public void test_findColorBigWidthHasInteger() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }
        ColorBox target = null;
        int maxWidth = Integer.MIN_VALUE;
        for (ColorBox colorBox : colorBoxList) {
            boolean hasInteger = colorBox.getSpaceList().stream()
                    .anyMatch(space -> space.getContent() instanceof Integer);
            if (hasInteger) {
                int width = colorBox.getSize().getWidth();
                if (width > maxWidth) {
                    maxWidth = width;
                    target = colorBox;
                }
            }
        }
        String colorName = target != null ? target.getColor().getColorName() : null;
        log(colorName);
    }

    public void test_findColorBigWidthHasInteger_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }
        ColorBox target = colorBoxList.stream()
                .filter(colorBox -> colorBox.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof Integer))
                .max(Comparator.comparingInt(a -> a.getSize().getWidth()))
                .orElse(null);
        String colorName = target != null ? target.getColor().getColorName() : null;
        log(colorName);
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList() {
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     */
    public void test_findMaxMapNumberValue() {
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br> 
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
    }
}
