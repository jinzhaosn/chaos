/*
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

package com.github.jinzhaosn.util;

import com.github.jinzhaosn.VerifyException;
import com.github.jinzhaosn.function.VoidPredicate;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 检查工具类
 *
 * @auther 961374431@qq.com
 * @date 2021年12月19日
 */
public class CheckUtil {
    private CheckUtil() {

    }

    /**
     * 检查是否满足条件，不满足返回默认值
     *
     * @param data         被检查的数据
     * @param predicate    检查
     * @param defaultValue 默认值
     * @param <T>          类型
     * @return 在检查满足的条件下返回自身，否则返回默认值
     */
    public static <T> T check(T data, Predicate<T> predicate, T defaultValue) {
        return predicate.test(data) ? data : defaultValue;
    }

    /**
     * 检查是否满足条件，不满足返回默认值
     *
     * @param data            被检查的数据
     * @param predicate       检查条件
     * @param defaultSupplier 默认值提供
     * @param <T>             类型
     * @return 在检查满足的条件下返回自身，否则返回默认值
     */
    public static <T> T check(T data, Predicate<T> predicate, Supplier<T> defaultSupplier) {
        if (predicate.test(data)) {
            return data;
        }
        return defaultSupplier.get();
    }

    /**
     * 检查是否满足条件，在满足条件时返回自身，否则抛出异常
     *
     * @param data      被检查对象
     * @param predicate 检查条件
     * @param message   异常消息
     * @param <T>       类型
     * @return 在条件满足时返回数据
     */
    public static <T> T checkThrow(T data, Predicate<T> predicate, String message) {
        if (!predicate.test(data)) {
            throw new VerifyException(message);
        }
        return data;
    }

    /**
     * 检查是否为真
     *
     * @param checked 是否为真
     * @param message 不为真异常消息
     */
    public static void checkTrue(boolean checked, String message) {
        if (!checked) {
            throw new VerifyException(message);
        }
    }

    /**
     * 检查参数
     *
     * @param predicate 校验条件
     * @param message   异常消息
     */
    public static void checkArgument(VoidPredicate predicate, String message) {
        if (!predicate.test()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检查参数
     *
     * @param checked 检查结果
     * @param message 抛出异常信息
     */
    public static void checkArgument(boolean checked, String message) {
        if (!checked) {
            throw new IllegalArgumentException(message);
        }
    }
}