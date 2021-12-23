/**
 * Copyright 2021-2022 jinzhaosn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jinzhaosn.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.jinzhaosn.util.CheckUtil.checkArgument;

/**
 * 工作日历
 * 可应用于要求是在非假期，工作日指定工作时间段内才算有效工作时长的场景，
 * 计算在偏移指定工作时长后的时间点
 * <p>
 * 比如 工作时间是09：00：00，下班时间是17：30：00
 * 时间点是：2021-12-22 12:00:00 偏移 43200秒（12小时）
 * 后时间是：2021-12023 15:30:00
 *
 * @auther 961374431@qq.com
 * @date 2021年12月20日
 */
public class WorkCalendar {
    private WorkCalendar(List<LocalDate> holidays, LocalTime onDutyTime, LocalTime offDutyTime) {
        this.holidayCalendar = new HashSet<>(holidays);
        this.onDutyTime = onDutyTime;
        this.offDutyTime = offDutyTime;
        this.dayWorkSeconds = intervalInSeconds(onDutyTime, offDutyTime);
    }

    private final Set<LocalDate> holidayCalendar; // 假期
    private final LocalTime onDutyTime; // 上班时间
    private final LocalTime offDutyTime; // 下班时间
    private final long dayWorkSeconds; // 一天工作时长，以秒为单位

    /**
     * 构建工作日历对象
     *
     * @param holidays    假期
     * @param onDutyTime  上班时间
     * @param offDutyTime 下班时间
     * @return 日历
     */
    public static WorkCalendar newInstance(List<LocalDate> holidays, LocalTime onDutyTime, LocalTime offDutyTime) {
        checkArgument(holidays != null, "假期不能为null");
        checkArgument(onDutyTime != null, "上班时间不能为null");
        checkArgument(offDutyTime != null, "下班时间不能为null");
        checkArgument(offDutyTime.isAfter(onDutyTime), "下班时间不能提前于上班时间");

        return new WorkCalendar(holidays, onDutyTime, offDutyTime);
    }

    /**
     * 计算在时间点上偏移时间之后的时间点
     *
     * @param localDateTime 时间点
     * @param workSeconds   工作时长偏移 要求为非负数
     * @return 偏移工作时长之后的时间点
     */
    public LocalDateTime addWorkSecondsAt(LocalDateTime localDateTime, long workSeconds) {
        checkArgument(localDateTime != null, "时间不能为null");
        checkArgument(workSeconds >= 0, "偏移工作时长为非负数");

        LocalDate startDate = localDateTime.toLocalDate();
        LocalTime startTime = localDateTime.toLocalTime();

        // 调整开始时间和开始日期，以及偏移秒数
        if (startTime.isBefore(onDutyTime)) {
            startTime = onDutyTime;
        } else if (startTime.isAfter(offDutyTime)) {
            startTime = onDutyTime;
            startDate = startDate.plusDays(1);
        } else if (isHoliday(startDate)) {
            startTime = onDutyTime;
            startDate = startDate.plusDays(1);
        } else if (intervalInSeconds(startTime, offDutyTime) >= workSeconds) {
            return localDateTime.plusSeconds(workSeconds);
        } else {
            startTime = onDutyTime;
            startDate = startDate.plusDays(1);
            workSeconds -= intervalInSeconds(startTime, offDutyTime);
        }

        while (workSeconds > 0) {
            while (isHoliday(startDate)) {
                startDate = startDate.plusDays(1);
            }

            if (workSeconds <= dayWorkSeconds) {
                return LocalDateTime.of(startDate, startTime).plusSeconds(workSeconds);
            } else {
                startTime = onDutyTime;
                startDate = startDate.plusDays(1);
                workSeconds -= dayWorkSeconds;
            }
        }

        // 逻辑上不会到此处
        return localDateTime.plusSeconds(workSeconds);
    }

    /**
     * 判断是否是假期
     *
     * @param date 日期
     * @return 是否假期
     */
    public boolean isHoliday(LocalDate date) {
        return holidayCalendar.contains(date);
    }

    /**
     * 计算时间间隔
     * 时间2 - 时间1
     *
     * @param pre 时间1
     * @param aft 时间2
     * @return 以秒为单位的时间间隔
     */
    public static long intervalInSeconds(LocalTime pre, LocalTime aft) {
        checkArgument(pre != null, "时间不能为null");
        checkArgument(aft != null, "时间不能为null");

        return Duration.between(pre, aft).getSeconds();
    }

    public Set<LocalDate> getHolidayCalendar() {
        return holidayCalendar;
    }

    public LocalTime getOnDutyTime() {
        return onDutyTime;
    }

    public LocalTime getOffDutyTime() {
        return offDutyTime;
    }
}
