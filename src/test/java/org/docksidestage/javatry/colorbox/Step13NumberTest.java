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
import java.util.*;
import java.util.stream.Collectors;

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
        // TODO done fujisawa nullを使うのではなく、Optionalを活用しましょう by jflute (2026/04/17)
        Optional<ColorBox> target = colorBoxList.stream()
                .filter(colorBox -> colorBox.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof Integer))
                .max(Comparator.comparingInt(a -> a.getSize().getWidth()));
        String colorName = target.map(colorBox -> colorBox.getColor().getColorName()).orElse(null);
        log(colorName);
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // 最初に出してきた、カラーボックスに直接入っているBigDecimalを足し合わせようとしている
//        BigDecimal total = colorBoxList.stream()
//                .flatMap(colorBox -> colorBox.getSpaceList().stream())
//                .filter(space -> space.getContent() instanceof BigDecimal)
//                .map(space -> (BigDecimal) space.getContent())
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        log("Total BigDecimal value: " + total);
        log(        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(space -> space.getContent() instanceof List)
                .flatMap(space -> ((List<?>) space.getContent()).stream())
                .filter(content -> content instanceof BigDecimal)
                .map(content -> (BigDecimal) content)
                        .collect(Collectors.toList())
                );

        BigDecimal total = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(space -> space.getContent() instanceof List)
                .flatMap(space -> ((List<?>) space.getContent()).stream()) // ここまで書き換えて補完させた
                .filter(content -> content instanceof BigDecimal)
                .map(content -> (BigDecimal) content)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log("Total BigDecimal value: " + total);
    }

    // 先にstreamのパターンを書いてきたので、あとでstreamじゃないパターンを
    public void test_sumBigDecimalInList() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // 深いな... streamの方が見やすい
        BigDecimal total = BigDecimal.ZERO;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof List) {
                    List<?> contentList = (List<?>) content;
                    for (Object element : contentList) {
                        if (element instanceof BigDecimal) {
                            total = total.add((BigDecimal) element);
                        }
                    }
                }
            }
        }

        log("Total BigDecimal value: " + total);
    }

    // ネスト深くない！？って言ったら修正してきたけど、あまり変わらない。
    public void test_sumBigDecimalInList_reduceNest() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
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
                    total = total.add(decimal);
                }
            }
        }

        log("Total BigDecimal value: " + total);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     * 該当するMapが複数ある場合、それぞれの中でvalueが最大値であるkeyを返す
     * 例: mapA = {'id': 1, 'price': 100}, mapB = {'id': 1000, 'price': 150}
     * ↑の場合、mapAのkey 'price', mapBのkey 'id' が返る
     */
    public void test_findMaxMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // AIが全然できなかったので、途中まで手で書いて実装
        // streamを一旦分割して書いてみようとして、Map<String, Number>に詰め直すところをまずやった。
        // その後は、maxKeysを探すところはAIでできた
        List<Map<String, Number>> targetList = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(space -> space.getContent() instanceof Map)
                .map(space -> (Map<?, ?>) space.getContent())
                .filter(map -> map.entrySet().stream()
                        .allMatch(entry -> entry.getKey() instanceof String && entry.getValue() instanceof Number))
                .map(map -> map.entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> (String) entry.getKey(),
                                entry -> (Number) entry.getValue()
                        )))
                .collect(Collectors.toList());
        log(targetList);

        List<String> maxKeys = targetList.stream()
                .map(map -> map.entrySet().stream()
                        .max(Comparator.comparingDouble(entry -> entry.getValue().doubleValue()))
                        .map(Map.Entry::getKey)
                        .orElse(null))
                .map(key -> key != null ? key : "")
                .collect(Collectors.toList());

        log(maxKeys);
    }

    // リファクタリングしてもらったら、↑と結果が変わった
    public void test_findMaxMapNumberValue_refactor() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        List<String> maxKeys = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Map)
                .map(content -> (Map<?, ?>) content)
                .filter(map -> !map.isEmpty())
                .filter(map -> map.entrySet().stream()
                        .allMatch(entry -> entry.getKey() instanceof String && entry.getValue() instanceof Number))
                .map(map -> map.entrySet().stream()
                        .max(Comparator.comparingDouble(entry -> ((Number) entry.getValue()).doubleValue()))
                        .map(entry -> (String) entry.getKey())
                        .orElse(""))
                .collect(Collectors.toList());

        log(maxKeys);
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br> 
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // 一発で書いてもらえた
        double total = colorBoxList.stream()
                .filter(colorBox -> "purple".equals(colorBox.getColor().getColorName()))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Map)
                .map(content -> (Map<?, ?>) content)
                .filter(map -> map.entrySet().stream()
                        .allMatch(entry -> entry.getKey() instanceof String && entry.getValue() != null))
                .flatMap(map -> map.entrySet().stream())
                .map(Map.Entry::getValue)
                .filter(value -> value instanceof Number || value instanceof String)
                .mapToDouble(value -> {
                    if (value instanceof Number) {
                        return ((Number) value).doubleValue();
                    } else {
                        try {
                            return Double.parseDouble((String) value);
                        } catch (NumberFormatException e) {
                            return 0.0;
                        }
                    }
                })
                .sum();

        log("Total sum of number values in purple ColorBox maps: " + total);
    }
}
