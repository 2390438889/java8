package chapter2;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author Hearts
 * @date 2019/3/17
 * @desc
 */
public class PrimeCollect implements Collector<Integer,Map<Boolean,List<Integer>>,Map<Boolean,List<Integer>>>{


    @Override
    /**
     * 初始化
     */
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return ()->{
            Map<Boolean,List<Integer>> map = new HashMap<>();
            map.put(true,new ArrayList<Integer>());
            map.put(false,new ArrayList<Integer>());
            return map;
        };
    }

    @Override
    /**
     * 添加元素规则
     */
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {

        return (Map<Boolean,List<Integer>> map,Integer t) -> {
            boolean flag = map.get(true).stream()
                    .filter(i -> i*i <= t)
                    .noneMatch(i -> t % i == 0);
            map.get(flag).add(t);
        };
    }

    /**
     * 合并元素规则
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (map1,map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
}
