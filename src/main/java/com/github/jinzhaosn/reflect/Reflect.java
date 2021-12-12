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
package com.github.jinzhaosn.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 映射工具类
 *
 * @auther 961374431@qq.com
 * @date 2021年12月12日
 */
public class Reflect {
    private Reflect() {

    }

    /**
     * 在类的继承树中寻找指定名称和参数的方法（除去编译器生成的方法）
     *
     * @param clazz          被查找的类
     * @param methodName     方法名称
     * @param parameterTypes 方法参数
     * @return 目标方法
     */
    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        if (clazz == null) {
            return null;
        }

        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.isBridge() || declaredMethod.isSynthetic()) {
                continue;
            }
            if (!declaredMethod.getName().equals(methodName)) {
                continue;
            }

            Class<?>[] declaredMethodParamTypes = declaredMethod.getParameterTypes();
            if ((parameterTypes == null && declaredMethodParamTypes.length > 0)
                    || (parameterTypes != null && !Arrays.equals(parameterTypes, declaredMethodParamTypes))) {
                continue;
            }

            return declaredMethod;
        }

        // 在接口中寻找
        for (Class<?> clazzInterface : clazz.getInterfaces()) {
            Method method = findMethod(clazzInterface, methodName, parameterTypes);
            if (method != null) {
                return method;
            }
        }

        // 在父类中寻找
        return findMethod(clazz.getSuperclass(), methodName, parameterTypes);
    }

    /**
     * 寻找类的属性
     *
     * @param clazz     被寻找的类
     * @param fieldName 属性名称
     * @return 目标属性
     */
    public static Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.getName().equals(fieldName)) {
                    return declaredField;
                }
            }
            clazz = clazz.getSuperclass();
        }

        return null;
    }
}
