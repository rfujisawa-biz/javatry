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

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.impl.StandardColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of miscellaneous type with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step15MiscTypeTest extends PlainTestCase {

    // ===================================================================================
    //                                                                           Exception
    //                                                                           =========
    /**
     * What class name is throw-able object in color-boxes? <br>
     * (カラーボックスに入っているthrowできるオブジェクトのクラス名は？)
     */
    public void test_throwable() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Throwable) { // 最初に出てきた時に instanceof Exceptionで確認していたので修正
                    Throwable cause = (Throwable) content;
                    log("content is Throwable: " + cause.getClass().getName());
                }
            }
        }
        // #1on1: Good (2026/05/29)
    }

    /**
     * What message is for exception that is nested by exception in color-boxes? <br>
     * (カラーボックスに入っている例外オブジェクトのネストした例外インスタンスのメッセージは？)
     */
    public void test_nestedException() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // 最初に出てきたのは↑の問題と同じ
//        for (ColorBox colorBox : colorBoxList) {
//            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
//                Object content = boxSpace.getContent();
//                if (content instanceof Exception) {
//                    Exception cause = (Exception) content;
//                    log("content is Exception: " + cause.getMessage());
//                }
//            }
//        }

        // #1on1: forの二つは自分で書いて...AIに実装させた (2026/05/29)
        // ネストがそもそもなんだろう？となってAIに聞いたら出てきた。
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Exception) {
                    Exception cause = (Exception) content;
                    Throwable nested = cause.getCause();
                    if (nested != null) {
                        log("Nested Message: " + nested.getMessage());
                    }
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           Interface
    //                                                                           =========
    /**
     * What value is returned by justHere() of FavoriteProvider in yellow color-box? <br>
     * (カラーボックスに入っているFavoriteProviderインターフェースのjustHere()メソッドの戻り値は？)
     */
    public void test_interfaceCall() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // ↓ なぜか黄色のカラーボックスに絞っている
        // #1on1: ごめんなさい、英語の方にyellowが入ってしまっている。
//        for (ColorBox colorBox : colorBoxList) {
//            if (colorBox.getColor().getColorName().equals("yellow")) {
//                YourPrivateRoom.FavoriteProvider favoriteProvider = (YourPrivateRoom.FavoriteProvider) colorBox.getSpaceList().get(0).getContent();
//                log("favoriteProvider.justHere(): " + favoriteProvider.justHere());
//            }
//        }

        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof YourPrivateRoom.FavoriteProvider) {
                    YourPrivateRoom.FavoriteProvider favoriteProvider = (YourPrivateRoom.FavoriteProvider) content;
                    log("favoriteProvider.justHere(): " + favoriteProvider.justHere());
                }
            }
        }
    }

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * What keyword is in BoxedStage of BoxedResort in List in beige color-box? (show "none" if no value) <br>
     * (beigeのカラーボックスに入っているListの中のBoxedResortのBoxedStageのkeywordは？(値がなければ固定の"none"という値を))
     */
    public void test_optionalMapping() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        for (ColorBox colorBox : colorBoxList) {
            if (!"beige".equals(colorBox.getColor().getColorName())) {
                continue; // beigeのカラーボックスに絞る
            }
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof List) {
                    List<?> listContent = (List<?>) content;
                    for (Object item : listContent) {
                        if (item instanceof YourPrivateRoom.BoxedResort) {
                            YourPrivateRoom.BoxedResort boxedResort = (YourPrivateRoom.BoxedResort) item;
                            String keyword = boxedResort.getPark()
                                    .flatMap(YourPrivateRoom.BoxedPark::getStage)
                                    .map(YourPrivateRoom.BoxedStage::getKeyword)
                                    .orElse("none");
                            log(keyword);
                        }
                    }
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What line number is makeEighthColorBox() call in getColorBoxList()? <br>
     * (getColorBoxList()メソッドの中のmakeEighthColorBox()メソッドを呼び出している箇所の行数は？)
     */
    public void test_lineNumber() {
        final AtomicInteger lineNumber = new AtomicInteger(-1);

        List<ColorBox> colorBoxList = new YourPrivateRoom() {
            @Override
            protected StandardColorBox makeEighthColorBox() {
                for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                    if (YourPrivateRoom.class.getName().equals(element.getClassName())
                            && "getColorBoxList".equals(element.getMethodName())) {
                        lineNumber.set(element.getLineNumber());
                        break;
                    }
                }
                return super.makeEighthColorBox();
            }
        }.getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        log(lineNumber.get());
    }
}
