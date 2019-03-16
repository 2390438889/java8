package chapter1;
import  org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * @author Hearts
 * @date 2019/3/15
 * @desc
 */

public class ChapterTest{

    @Test
    public void chapter1_3_6(){
        //1
        Function<String, Integer> stringToInteger =
                (String s) -> Integer.parseInt(s);

        System.out.println(stringToInteger.apply("11"));

        stringToInteger = Integer::valueOf;

        System.out.println(stringToInteger.apply("11"));


        //2

        BiPredicate<List<String>, String> contains =
                (list, element) -> list.contains(element);

        System.out.println(contains.test(Arrays.asList("1","2","3"),"1"));

        contains = List::contains;

        System.out.println(contains.test(Arrays.asList("1","2","3"),"1"));
    }

    @Test
    public void chapter1_3_7(){

        Supplier<String> supplier = String::new;

        System.out.println(supplier.get());


        Function<String,String> function = String::new;
        System.out.println(function.apply("11"));
        List<String> list = Arrays.asList("31", "22");
        Collections.sort(list,
                Comparator.comparing(String::hashCode));
        list.forEach(s -> System.out.println(s.hashCode()));


    }

}
