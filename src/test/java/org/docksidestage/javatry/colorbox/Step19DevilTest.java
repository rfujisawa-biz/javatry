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

import java.lang.reflect.Field;
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
        
        // #1on1: 1,2のステップはインライン補完、実際の実装は普通に実装してもらったのでプロセスが違う (2026/06/12)
        // "6. そのカラーボックスの色の長さを取得する" が間違っているが...
        // 実装のAIの方は、6が間違ってるよってコメントを残して、ただしい実装をした。
        // つまり実装のAIの方は、元の問題文もしっかり読んで実装したと言える。
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
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        ColorBox redColorBox = null;
        for (ColorBox colorBox : colorBoxList) {
            if ("red".equals(colorBox.getColor().getColorName())) {
                redColorBox = colorBox;
                break;
            }
        }

        if (redColorBox == null) {
            log("red color-box is not found!");
            return;
        }

        // Change height to 160 forcibly
        try {
            Field heightField = redColorBox.getSize().getClass().getDeclaredField("height");
            heightField.setAccessible(true); // こんなメソッドがあるのは怖い
            heightField.setInt(redColorBox.getSize(), 160);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to change box height forcibly.", e);
        }
        log(redColorBox.getSize().toString());

        // なぜこのような機能がJavaに導入されたのか、AIに聞いてみた
        // Java は基本的には強いカプセル化を守る言語だが、フレームワーク・ツール・ランタイム基盤には、その壁を越えてメタ的に扱う能力が必要だった。 そのため reflection と、さらに深いアクセスのための setAccessible が入った、
        // ただし、この仕組みは強力すぎるので、後年かなり制限されました。OpenJDK は、反射で JDK 内部要素に触れることがセキュリティと保守性を損なったと明言していて、Java 9 のモジュール導入以降は strong encapsulation を強め、setAccessible(true) でも開けないケースが増えました。
        // JEP 403 では、その引き締めの理由として security と maintainability を挙げています。

        // 今回は特にモジュールが保護されていなかったから、setAccessible(true) が使えた？
    }

    // ===================================================================================
    //                                                                        Meta Journey
    //                                                                        ============
    /**
     * What value is returned from no-parameter functional method of interface that has FunctionalInterface annotation in color-boxes? <br> 
     * (カラーボックスに入っているFunctionalInterfaceアノテーションが付与されているインターフェースの引数なしのFunctionalメソッドの戻り値は？)
     */
    public void test_be_frameworker() {
        /*
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        Object functionalInterfaceInstance = null;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content != null && content.getClass().isAnnotationPresent(FunctionalInterface.class)) {
                    functionalInterfaceInstance = content;
                    break;
                }
            }
            if (functionalInterfaceInstance != null) {
                break;
            }
        }

        if (functionalInterfaceInstance == null) {
            log("FunctionalInterface instance is not found!");
            return;
        }

        try {
            java.lang.reflect.Method functionalMethod = null;
            for (java.lang.reflect.Method method : functionalInterfaceInstance.getClass().getMethods()) {
                if (method.isAnnotationPresent(java.lang.Override.class)) {
                    continue;
                }
                if (java.lang.reflect.Modifier.isAbstract(method.getModifiers())) {
                    functionalMethod = method;
                    break;
                }
            }

            if (functionalMethod == null) {
                log("Functional method is not found!");
                return;
            }

            Object returnValue = functionalMethod.invoke(functionalInterfaceInstance);
            log(returnValue);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to invoke functional method.", e);
        }
        */
        // ↑ 実行してみたが見つからず、間違ってそうなので、AIに聞いてみた
        // いいえ。test_be_frameworker() は現状の実装だと正しくありません。
        // 対象の見つけ方が違います
        // Step19DevilTest.java では content.getClass().isAnnotationPresent(FunctionalInterface.class) を見ていますが、箱に入っているのはラムダ実体です。@FunctionalInterface が付いているのは実装クラスではなく FavoriteProvider インターフェース側です。実データは YourPrivateRoom.java にあり、注釈は YourPrivateRoom.java です。
        // この条件だと functionalInterfaceInstance は見つからず、"FunctionalInterface instance is not found!" になりやすいです。
        //
        // functional method の取り方も違います
        // Step19DevilTest.java で functionalInterfaceInstance.getClass().getMethods() から abstract method を探していますが、ラムダの実装クラス上ではそのメソッドは abstract ではありません。@Override 判定もここでは意味が薄いです。見るべきなのは「そのオブジェクトが実装している interface」です。

    List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        Object functionalInterfaceInstance = null;
        java.lang.reflect.Method functionalMethod = null;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content == null) {
                    continue;
                }

                for (Class<?> interfaceType : content.getClass().getInterfaces()) {
                    if (!interfaceType.isAnnotationPresent(FunctionalInterface.class)) {
                        continue;
                    }
                    for (java.lang.reflect.Method method : interfaceType.getDeclaredMethods()) {
                        if (java.lang.reflect.Modifier.isAbstract(method.getModifiers()) && method.getParameterCount() == 0) {
                            functionalInterfaceInstance = content;
                            functionalMethod = method;
                            break;
                        }
                    }
                    if (functionalInterfaceInstance != null) {
                        break;
                    }
                }
                if (functionalInterfaceInstance != null) {
                    break;
                }
            }
            if (functionalInterfaceInstance != null) {
                break;
            }
        }

        if (functionalInterfaceInstance == null) {
            log("FunctionalInterface instance is not found!");
            return;
        }

        if (functionalMethod == null) {
            log("Functional method is not found!");
            return;
        }

        try {
            Object returnValue = functionalMethod.invoke(functionalInterfaceInstance);
            log(returnValue);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to invoke functional method.", e);
        }
    }
}
