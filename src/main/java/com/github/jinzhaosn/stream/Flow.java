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

import com.github.jinzhaosn.function.VoidConsumer;
import com.github.jinzhaosn.function.VoidPredicate;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 条件流处理
 * 非线程安全
 *
 *  使用例子：
 *  Optional result = Flow.choose().when(ch > 10).then(() -> 12)
 *      .elseWhen(() -> ch > 3).then(() -> System.out.println("qqq"))
 *      .otherwise(() -> System.out.println("rrr")).getResult();
 *
 * @auther 961374431@qq.com
 * @date 2021年12月10日
 */
public class Flow {
    private Flow() {
    }

    public static Flow choose() {
        return new Flow();
    }

    public Condition when(boolean expression) {
        return new ConditionImpl(expression);
    }

    public Condition when(VoidPredicate predicate) {
        return new ConditionImpl(predicate);
    }

    public interface Condition {
        FollowFlow then(VoidConsumer consumer);

        FollowFlow then(Supplier<?> supplier);
    }

    /**
     * 条件对象
     */
    private static class ConditionImpl implements Condition {
        private final VoidPredicate predicate;

        public ConditionImpl(boolean checkFlag) {
            this.predicate = () -> checkFlag;
        }

        public ConditionImpl(VoidPredicate predicate) {
            this.predicate = predicate;
        }

        public FollowFlow then(VoidConsumer consumer) {
            if (predicate.test()) {
                try {
                    consumer.accept();
                } catch (Exception exp) {
                    throw (RuntimeException) exp;
                }
                return new FollowFlowImpl(Optional.empty());
            }
            return new FollowFlowImpl();
        }

        public FollowFlow then(Supplier<?> supplier) {
            if (predicate.test()) {
                Object result = supplier.get();
                return new FollowFlowImpl(Optional.ofNullable(result));
            }
            return new FollowFlowImpl();
        }
    }

    /**
     * 条件流结束条件节点，针对已经结束的条件流的特殊处理
     */
    private static class FinishedCondition extends ConditionImpl {
        private final FollowFlowImpl flow;

        public FinishedCondition(FollowFlowImpl flow) {
            super(false);
            this.flow = flow;
        }

        @Override
        public FollowFlowImpl then(VoidConsumer consumer) {
            return flow;
        }

        @Override
        public FollowFlowImpl then(Supplier<?> supplier) {
            return flow;
        }
    }

    /**
     * 条件流流程节点
     */
    public interface FollowFlow extends FlowResult {
        Condition elseWhen(boolean expression);

        Condition elseWhen(VoidPredicate predicate);

        FlowResult otherwise(VoidConsumer consumer);

        FlowResult otherwise(Supplier<?> supplier);
    }

    private static class FollowFlowImpl implements FollowFlow {
        private FinishedCondition finishedCondition;
        private Optional result = Optional.empty();

        public <T> FollowFlowImpl(Optional result) {
            finishedCondition = new FinishedCondition(this);
            this.result = result;
        }

        public FollowFlowImpl() {

        }

        public Condition elseWhen(boolean expression) {
            return finishedCondition != null ? finishedCondition : new ConditionImpl(expression);
        }

        public Condition elseWhen(VoidPredicate predicate) {
            return finishedCondition != null ? finishedCondition : new ConditionImpl(predicate);
        }

        public FlowResult otherwise(VoidConsumer consumer) {
            if (finishedCondition != null) {
                return new FlowResultImpl(result);
            }
            try {
                consumer.accept();
            } catch (Exception exp) {
                throw (RuntimeException) exp;
            }
            return new FlowResultImpl(Optional.empty());
        }

        public FlowResult otherwise(Supplier<?> supplier) {
            if (finishedCondition != null) {
                return new FlowResultImpl(result);
            }
            result = Optional.ofNullable(supplier.get());
            return new FlowResultImpl(result);
        }

        public Optional getResult() {
            return result;
        }

        public <T> Optional<T> getResult(Class<T> clazz) {
            return result;
        }
    }

    /**
     * 条件流结果
     */
    public interface FlowResult {
        Optional getResult();

        <T> Optional<T> getResult(Class<T> clazz);
    }

    private static class FlowResultImpl implements FlowResult {
        private final Optional result;

        public FlowResultImpl(Optional result) {
            this.result = result;
        }

        @Override
        public Optional getResult() {
            return result;
        }

        @Override
        public <T> Optional<T> getResult(Class<T> clazz) {
            return result;
        }
    }
}
