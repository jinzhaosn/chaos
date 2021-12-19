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

import java.util.List;

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
    public <T> T getItem(List<T> items, int pos) {
        if (null == items || pos < 0 || pos >= items.size()) {
            return null;
        }
        return items.get(pos);
    }
}
