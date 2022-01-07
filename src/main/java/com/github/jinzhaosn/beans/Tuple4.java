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

package com.github.jinzhaosn.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 四元组
 *
 * @auther 961374431@qq.com
 * @date 2022年01月08日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tuple4<U1, U2, U3, U4> {
    private U1 object1;
    private U2 object2;
    private U3 object3;
    private U4 object4;

    /**
     * 四元组
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @param obj3 对象3
     * @param obj4 对象4
     * @param <U1> 对象1类型
     * @param <U2> 对象2类型
     * @param <U3> 对象3类型
     * @param <U4> 对象4类型
     * @return 四元组
     */
    public static <U1, U2, U3, U4> Tuple4<U1, U2, U3, U4> of(U1 obj1, U2 obj2, U3 obj3, U4 obj4) {
        return new Tuple4<>(obj1, obj2, obj3, obj4);
    }
}
