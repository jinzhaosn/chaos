/**
 *    Copyright 2021-2022 jinzhaosn
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.jinzhaosn.stream;

import com.github.jinzhaosn.util.WorkCalendar;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * 日期测试
 *
 * @auther 961374431@qq.com
 * @date 2021年12月21日
 */
public class DateTest {

    private final List<LocalDate> holidays = Arrays.asList(
            LocalDate.parse("2021-12-25", DateTimeFormatter.ISO_LOCAL_DATE),
            LocalDate.parse("2021-12-26", DateTimeFormatter.ISO_LOCAL_DATE),
            LocalDate.parse("2022-01-01", DateTimeFormatter.ISO_LOCAL_DATE),
            LocalDate.parse("2022-01-02", DateTimeFormatter.ISO_LOCAL_DATE));

    @Test
    public void workCalendarTest() {
        WorkCalendar workCalendar = WorkCalendar.newInstance(
                holidays, LocalTime.parse("10:00:00"), LocalTime.parse("17:30:00"));
        workCalendar.isHoliday(LocalDate.now());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDateTime = workCalendar.addWorkSecondsAt(now, 10 * 3600);
        System.out.println("now " + now + " new :" + newDateTime);
    }
}
