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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.space.DoorBoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Date with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author rfujisawa-biz
 */
public class Step14DateTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is date in color-boxes formatted as plus-separated (e.g. 2019+04+24)? <br>
     * (カラーボックスに入っている日付をプラス記号区切り (e.g. 2019+04+24) のフォーマットしたら？)
     */
    public void test_formatDate() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // #1on1: AIが次のエクササイズの問題と紛れたのかもしれない (2026/05/15)
//        for (ColorBox colorBox : colorBoxList) {
//            if (colorBox.getColor().getColorName().equals("yellow")) { // なぜか黄色いカラーボックスに限定してきた(次の問題が黄色指定なのですね)
//                Set<String> yellowDateSet = colorBox.getColor().getColorSet(); // getColorSet()という謎のメソッドを出してきた
//                for (String dateStr : yellowDateSet) {
//                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//                    log(date.toString());
//                }
//            }
//        }
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                if (boxSpace.getContent() instanceof LocalDate) {
                    log("content: " + boxSpace.getContent());
                    LocalDate date = (LocalDate) boxSpace.getContent();
                    log("content transformed: " + date.format(DateTimeFormatter.ofPattern("yyyy+MM+dd")));
                }
            }
        }
    }

    public void test_formatDate_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy+MM+dd");
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(LocalDate.class::isInstance)
                .map(LocalDate.class::cast)
                .forEach(date -> {
                    log("content: " + date);
                    log("content transformed: " + date.format(formatter));
                });
    }

    /**
     * How is it going to be if the slash-separated date string in yellow color-box is converted to LocalDate and toString() is used? <br>
     * (yellowのカラーボックスに入っているSetの中のスラッシュ区切り (e.g. 2019/04/24) の日付文字列をLocalDateに変換してtoString()したら？)
     */
    public void test_parseDate() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        for (ColorBox colorBox : colorBoxList) {
            if (!"yellow".equals(colorBox.getColor().getColorName())) {
                continue;
            }
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                  // #1on1: 逆にこっちは上のエクササイズ向け？ (2026/05/15)
//                if (boxSpace.getContent() instanceof LocalDate) {
//                    log("content: " + boxSpace.getContent());
//                    LocalDate date = (LocalDate) boxSpace.getContent();
//                    log("content transformed: " + date.format(DateTimeFormatter.ofPattern("yyyy+MM+dd")));
//                } // 最初はこれが出てきた
//                if (boxSpace.getContent() instanceof Set) {
//                    Set<?> set = (Set<?>) boxSpace.getContent();
//                    for (Object obj : set) {
//                        if (obj instanceof String) {
//                            String dateStr = (String) obj;
//                            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//                            log("parsed date: " + date);
//                        }
//                    }
//                } // 二回目 parseの部分でエラーになった
                if (boxSpace.getContent() instanceof Set) {
                    Set<?> set = (Set<?>) boxSpace.getContent();
                    for (Object obj : set) {
                        if (obj instanceof String) {
                            String dateStr = (String) obj;
                            // #1on1: ここで2O19を弾いている (2026/05/15)
                            // try/catchでもいいかもしれないけど、事前にチェックできるに越したことはない。
                            if (!dateStr.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
                                continue;
                            }
                            // ここでは、"2026/05/15" 形式の文字列だけになっている。
                            // #1on1: まだ、parseで例外で落ちる可能性がある。 (2026/05/15)
                            // 2026/5/15 だと matches() で弾かれる。これはこれで気にしないといけない。
                            // 2026/00/15 とか by ふじさわさん
                            // ってこと考えると、結局 try/catch は必要なのかなと。
                            // ちなみに、HandyDateの紹介も
                            // done fujisawa 事前チェックはありつつ、try/catchもあった方が良い by jflute (2026/05/15)
                            LocalDate date;
                            try {
                                date = LocalDate.parse(dateStr, formatter);
                            } catch (DateTimeParseException e) {
                                continue;
                            }
                            String dateString = date.toString(); // toStringは不要な気がするけど一応やっておく
                            log("parsed date: " + dateString);
                        }
                    }
                } // 2019じゃなくて2O19があります。とAIに教えてもらって修正してもらった。
            }
            // 藤澤: 意地悪な問題だね
            // AI: そうですね。2O19/04/22 の O が数字の 0 ではなく英字なので、見た目では日付っぽいのに DateTimeFormatter では弾かれます。
            // 今回のポイントは、instanceof String だけでは「文字列である」ことしか保証できない、というところです。
            // LocalDate.parse() に渡す前に「日付として読める文字列か」を確認する必要があります。
        }
    }

    public void test_parseDate_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        colorBoxList.stream()
                .filter(colorBox -> "yellow".equals(colorBox.getColor().getColorName()))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Set)
                .map(content -> (Set<?>) content)
                .flatMap(set -> set.stream())
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(dateStr -> dateStr.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}"))
                .map(dateStr -> LocalDate.parse(dateStr, formatter))
                .map(LocalDate::toString)
                .forEach(dateString -> log("parsed date: " + dateString));
    } // なんか長いな。。
    // #1on1: 確かに事務的なfilter/mapで行を消化してしまいがち (2026/05/15)
    // 個人的には、空行開けたりコメント入れたりで処理のまとまりを表現したくなっちゃうけど...
    // あまりそれをやってるひとはいないかも。
    
    // #1on1: ベタ書きとStreamの可読性の違い (2026/05/15)
    // ベタ書きは無駄も多いけど構造で直感的な面もある。
    // Streamはすっきりしてるけど全部フラットで箇条書き文章読んでみるみたいな、読めばわかる。

    /**
     * What is total of month numbers of date in color-boxes? <br>
     * (カラーボックスに入っている日付の月を全て足したら？)
     */
    public void test_sumMonth() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        int total = 0;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                if (boxSpace.getContent() instanceof LocalDate) {
                    LocalDate date = (LocalDate) boxSpace.getContent();
                    total += date.getMonthValue();
                }
            }
        }
        log(total);
    }

    public void test_sumMonth_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        int total = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(LocalDate.class::isInstance)
                .map(LocalDate.class::cast)
                .mapToInt(LocalDate::getMonthValue)
                .sum();
        log(total);
    }

    /**
     * Add 3 days to second-found date in color-boxes, what day of week is it? <br>
     * (カラーボックスに入っている二番目に見つかる日付に3日進めると何曜日？)
     */
    public void test_plusDays_weekOfDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

//        for (ColorBox colorBox : colorBoxList) {
//            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
//                if (boxSpace.getContent() instanceof LocalDate) {
//                    LocalDate date = (LocalDate) boxSpace.getContent();
//                    LocalDate plus3Days = date.plusDays(3);
//                    log(plus3Days.getDayOfWeek());
//                }
//            }
//        } // 最初に出してきたもの。2番目に関しては対応できていないように見える
        // ↑を踏まえた上で修正してもらったのが以下
        // 一度日付を抽出してから2番目を取るようにしている
        // streamにしやすそう

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<LocalDate> dateList = new ArrayList<>();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof LocalDate) {
                    dateList.add((LocalDate) content);
                }
                if (content instanceof Set) {
                    Set<?> set = (Set<?>) content;
                    for (Object obj : set) {
                        if (obj instanceof String) {
                            String dateStr = (String) obj;
                            if (dateStr.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
                                dateList.add(LocalDate.parse(dateStr, formatter));
                            }
                        }
                    }
                }
            }
        }
        if (dateList.size() < 2) {
            log("second date is not found!");
            return;
        }
        LocalDate secondDate = dateList.get(1);
        log(secondDate.plusDays(3).getDayOfWeek());
    }

    public void test_plusDays_weekOfDay_stream() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String answer = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .flatMap(content -> toDateStream(content, formatter))
                .skip(1)
                .map(date -> date.plusDays(3).getDayOfWeek().toString())
                .findFirst()
                .orElse("second date is not found!");
        log(answer);
    }

    // #1on1: Streamの見た目の流れを壊さずにprivateメソッドするのGood (2026/05/15)
    // 一方で、もうちょいうまくスッキリさせられないだろうか？ by ふじさわさん
    // setがあるので、どこかのその分岐を解決してあげないといけないから、その汚れ役がここになるのかなと。
    // 汚れ役が散らばってると全体が汚くなるけど、汚れ役をまとめて、表舞台は綺麗にしているとも言える。
    private Stream<LocalDate> toDateStream(Object content, DateTimeFormatter formatter) {
        if (content instanceof LocalDate) {
            return Stream.of((LocalDate) content);
        }
        if (!(content instanceof Set)) {
            return Stream.empty();
        }
        return ((Set<?>) content).stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(dateStr -> dateStr.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}"))
                .map(dateStr -> LocalDate.parse(dateStr, formatter));
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * How many days (number of day) are between two dates in yellow color-boxes? <br>
     * (yellowのカラーボックスに入っている二つの日付は何日離れている？)
     */
    public void test_diffDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // ↓ 最初に出てきた. 同じ日付になってしまうのでは？
        // #1on1: 上段と上段に関しては、0になって実質的スキップになるはずだが... (2026/05/29)
        // 上段と中段やってから、中段と上段をやったら、abs()で両方プラスになって倍になるのでは？ by ふたり
        // totalがミリ秒になっている？ by くぼ → いや、toEpochDay()は日数っぽい by ふじさわさん
        // あと、二つyellowの絞りができてないのと、Setの対応ができてない。
        int total = 0;
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                // #1on1: LocalDateしか対象にしてなかったので、LocalDateTimeも対象にするようにした (2026/05/29)
                if (content instanceof LocalDate || content instanceof LocalDateTime) {
                    LocalDate date1 = content instanceof LocalDate ? (LocalDate) content : ((LocalDateTime)content).toLocalDate();
                    log("date1: " + date1);
                    for (BoxSpace boxSpace2 : colorBox.getSpaceList()) {
                        Object content2 = boxSpace2.getContent();
                        if (content2 instanceof LocalDate || content2 instanceof LocalDateTime) {
                            LocalDate date2 = content2 instanceof LocalDate ? (LocalDate) content2 : ((LocalDateTime)content2).toLocalDate();
                            log("date2: " + date2);
                            total += (int) Math.abs(date1.toEpochDay() - date2.toEpochDay());
                        }
                    }
                }
            }
        }
        log("total: " + total); // 13434 (ちょうど倍になってる)
        long betaniDashita = ChronoUnit.DAYS.between(LocalDate.of(1983, 4, 15), LocalDate.of(2001, 9, 4));
        log(betaniDashita); // 6717

        // #1on1: yellowが二つあって、別の互いのLocalDateで比較している (2026/05/29)
        // まあここはそもそもデータのトラップがあったりするところではある。
        // (同じ一つのyellowの中のLocalDateとLocalDateTimeの比較にはなってない)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<LocalDate> dateList = new ArrayList<>();
        for (ColorBox colorBox : colorBoxList) {
            if (!"yellow".equals(colorBox.getColor().getColorName())) {
                continue;
            }
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                toDateStream(boxSpace.getContent(), formatter).forEach(dateList::add);
            }
        }
        if (dateList.size() < 2) {
            log("two dates are not found!");
            return;
        }

        LocalDate date1 = dateList.get(0);
        LocalDate date2 = dateList.get(1);
        log("date1: " + date1);
        log("date2: " + date2);

        long diffDay = Math.abs(ChronoUnit.DAYS.between(date1, date2));
        log("> " + diffDay);
    }

    /**
     * Find LocalDate in yellow color-box,
     * and add same color-box's LocalDateTime's seconds as number of months to it,
     * and add red color-box's Long number as days to it,
     * and subtract the first decimal place of BigDecimal that has three(3) as integer in List in color-boxes from it,
     * What date is it? <br>
     * (yellowのカラーボックスに入っているLocalDateに、同じカラーボックスに入っているLocalDateTimeの秒数を月数として足して、
     * redのカラーボックスに入っているLong型を日数として足して、カラーボックスに入っているリストの中のBigDecimalの整数値が3の小数点第一位の数を日数として引いた日付は？)
     */
    public void test_birthdate() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // 1. yellowのカラーボックスに入っているLocalDateを抽出し、targetDate変数に代入する
        // 2. yellowのカラーボックスに入っているLocalDateTimeの秒数をtargetDateの月数に足す
        // 3. redのカラーボックスに入っているLong型を日数としてtargetDateに足す
        // 4. redのカラーボックスに入っているリストの中のBigDecimalの整数値が3の小数点第一位の数をtargetDateの日数から引く
        // 5. 日付を出力する
        // 問題文の読解が難しかったので↑のコメントをつけて、実装させた
        // #1on1: 人間が要件を分解して翻訳してあげたのGood (2026/05/29)

        LocalDate targetDate = null;
        for (ColorBox colorBox : colorBoxList) {
            if (!"yellow".equals(colorBox.getColor().getColorName())) {
                continue;
            }

            LocalDate foundDate = null;
            Integer second = null;
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof LocalDate) {
                    foundDate = (LocalDate) content;
                }
                if (content instanceof LocalDateTime) {
                    second = ((LocalDateTime) content).getSecond();
                }
            }
            if (foundDate != null && second != null) {
                targetDate = foundDate.plusMonths(second);
                break;
            }
        }
        if (targetDate == null) {
            log("yellow LocalDate or LocalDateTime is not found!");
            return;
        }

        Long redLong = null;
        for (ColorBox colorBox : colorBoxList) {
            if (!"red".equals(colorBox.getColor().getColorName())) {
                continue;
            }
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (content instanceof Long) {
                    redLong = (Long) content;
                    break;
                }
            }
            if (redLong != null) {
                break;
            }
        }
        if (redLong == null) {
            log("red Long number is not found!");
            return;
        }
        targetDate = targetDate.plusDays(redLong);

        Integer subtractDay = null;
        outer: for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = boxSpace.getContent();
                if (!(content instanceof List<?>)) {
                    continue;
                }
                for (Object element : (List<?>) content) {
                    if (!(element instanceof BigDecimal)) {
                        continue;
                    }

                    BigDecimal decimal = (BigDecimal) element;
                    if (decimal.intValue() != 3) {
                        continue;
                    }
                    // 3.x の x を日数として使う
                    subtractDay = decimal.abs().remainder(BigDecimal.ONE).movePointRight(1).intValue() % 10;
                    break outer;
                }
            }
        }
        if (subtractDay == null) {
            log("target BigDecimal is not found!");
            return;
        }

        log(targetDate.minusDays(subtractDay));
    }

    /**
     * What second is LocalTime in color-boxes? <br>
     * (カラーボックスに入っているLocalTimeの秒は？)
     */
    public void test_beReader() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxList is empty!");
            return;
        }

        // 最初に出してきたが、ログが出ない
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                if (boxSpace.getContent() instanceof LocalTime) {
                    LocalTime localTime = (LocalTime) boxSpace.getContent();
                    log(localTime.getSecond());
                }
            }
        }
        // AIに聞いてみたら、DoorColorBoxが存在する可能性がある。とのこと
        // #1on1: That's 罠プログラム (2026/05/29)
        // ↓修正してもらった

        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                Object content = readContent(boxSpace);
                if (content instanceof LocalTime) {
                    LocalTime localTime = (LocalTime) content;
                    log(localTime.getSecond());
                    return;
                }
            }
        }

        log("LocalTime is not found!");
    }

    private Object readContent(BoxSpace boxSpace) {
        if (!(boxSpace instanceof DoorBoxSpace)) {
            return boxSpace.getContent();
        }
        DoorBoxSpace doorBoxSpace = (DoorBoxSpace) boxSpace;
        if (doorBoxSpace.isOpen()) {
            return doorBoxSpace.getContent();
        }
        doorBoxSpace.openTheDoor();
        try {
            return doorBoxSpace.getContent();
        } finally {
            doorBoxSpace.closeTheDoor();
        }
    }
}
