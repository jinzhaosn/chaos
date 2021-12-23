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

import com.github.jinzhaosn.function.VoidConsumer;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 对象工具类
 *
 * @auther 961374431@qq.com
 * @date 2021年12月19日
 */
public class ObjectUtil {
    private ObjectUtil() {

    }

    /**
     * 从列表中指定位置获取元素，从0计数
     *
     * @param items 元素列表
     * @param pos   位置
     * @param <T>   类型
     * @return 数据
     */
    public static <T> T getItem(List<T> items, int pos) {
        if (null == items || pos < 0 || pos >= items.size()) {
            return null;
        }
        return items.get(pos);
    }

    /**
     * 忽略执行过程中的异常
     *
     * @param consumer 执行过程
     */
    public static void doSilent(VoidConsumer consumer) {
        try {
            consumer.accept();
        } catch (Throwable exp) {
            // TODO logger
        }
    }

    /**
     * 忽略获取值过程中的任何异常
     *
     * @param supplier 取值函数
     * @param <T>      类型
     * @return 在异常发生时，返回null，否则返回取值函数返回值
     */
    public static <T> T doSilent(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable exp) {
            // TODO logger
        }
        return null;
    }

    /**
     * 忽略转换过程中的任何异常
     *
     * @param source   源数据
     * @param function 转换函数
     * @param <U>      源类型
     * @param <T>      目标类型
     * @return 在异常发生时，返回null，否则返回转换返回值
     */
    public static <U, T> T doSilent(U source, Function<U, T> function) {
        try {
            return function.apply(source);
        } catch (Throwable exp) {
            // TODO logger
        }
        return null;
    }
}
