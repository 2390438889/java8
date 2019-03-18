package chapter2;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Hearts
 * @date 2019/3/16
 * @desc
 */
public class Chapter2  {

    @Test
    public void chapter2_5_2(){

        //1、给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？例如，给定[1, 2, 3, 4,5]，
        // 应该返回[1, 4, 9, 16, 25]

        System.out.println(Arrays.asList(1,2,3,4,5)
                .stream()
                .map(i -> i*i)
                .collect(toList()));

        //2、给定两个数字列表，如何返回所有的数对呢？
        // 例如，给定列表[1, 2, 3]和列表[3, 4]，应该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。
        // 为简单起见，你可以用有两个元素的数组来代表数对

        System.out.println(Arrays.asList(1,2,3).stream()
                .flatMap(i -> Arrays.asList(3,4).stream().map(j -> new int[]{i, j}))
                .collect(toList())
                .stream()
                .map(Arrays::toString).collect(toList()));

        //(3) 如何扩展前一个例子，只返回总和能被3整除的数对呢？例如(2, 4)和(3, 3)是可以的
        //3 1
        System.out.println(Arrays.asList(1, 2, 3).stream()
                .flatMap(i -> Arrays.asList(3, 4).stream().map(j -> new int[]{i, j}))
                .filter(item -> {
                    int sum =0;
                    for (int i : item) {
                        sum +=i;
                    }
                    return sum % 3 == 0;
                }).map(Arrays::toString).collect(toList()));

        // 3 2
        System.out.println(Arrays.asList(1,2,3)
        .stream()
        .flatMap(i -> Arrays.asList(3,4)
        .stream()
        .filter(j -> (i+j)%3==0)
        .map(j -> new int[]{i,j}))
        .map(Arrays::toString)
        .collect(toList()));
    }

    @Test
    /**
     * 测试短路效果
     */
    public void shortOutTest(){
        //1imit
        Arrays.asList(1,2,3,4)
                .stream()
                .filter(i -> {
                    System.out.println(i);
                    return true;
                })
                .limit(2)
                .forEach(System.out::print);
        //anyMatch
        Arrays.asList(1, 2, 3, 4)
                .stream()
                .anyMatch(i -> {
                    System.out.println(i);
                    return i < 2;
                });

    }

    @Test
    /**
     * 测试reduce
     */
    public void chapter2_5_3(){
        //1、map和reduce方法数一数流中有多少个菜呢
        System.out.println(Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .map(d -> 1)        //将元素修改为1,便于统计数量
                .reduce(0, (a, b) -> a + b));


    }

    @Test
    /**
     * 交易员练习 Page 98
     */
    public void chapter2_5_5(){
        //年份 ，交易员姓名，交易金额，地址
        List years =Arrays.asList("2011","2011","2018","2011");
        List names = Arrays.asList("张三","李四","王五","赵六");
        List moneys = Arrays.asList(23429,32039,323489,3322);
        List addrs = Arrays.asList("剑桥","牛津","麻省理工","剑桥");
        List<Map<String,Object>>  data = new ArrayList<>();
        for (int i = 0; i < years.size(); i++) {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("year",years.get(i));
            map.put("name",names.get(i));
            map.put("money", moneys.get(i));
            map.put("address",addrs.get(i));
            data.add(map);
        }

        //data.forEach(System.out::println);


//        (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        data.stream()
        .filter(map -> map.get("year").equals("2011"))
        .sorted((a, b) -> ((Integer) a.get("money")).compareTo((Integer) b.get("money")))
        .forEach(System.out::println);

//        (2) 交易员都在哪些不同的城市工作过？

        data.stream()
                .map(map -> map.get("address").toString())
                .distinct()
                .forEach(System.out::println);
//        (3) 查找所有来自于剑桥的交易员，并按姓名排序。
        data.stream()
                .filter(map -> map.get("address").equals("剑桥"))
                .sorted((a, b) -> ((String) a.get("name")).compareTo((String) b.get("name")))
                .forEach(System.out::println);
//        (4) 返回所有交易员的姓名字符串，按字母顺序排序。
        data.stream()
                .map(map -> map.get("name").toString())
                .sorted((a,b) -> a.compareTo(b))
                .forEach(System.out::println);
//        (5) 有没有交易员是在米兰工作的？
        System.out.println(data.stream().map(map -> map.get("address").toString()).anyMatch(a -> a.equals("米兰")));
//        (6) 打印生活在剑桥的交易员的所有交易额。
        data.stream()
                .filter(map -> map.get("address").equals("剑桥"))
                .map(map -> (Integer)map.get("money"))
                .forEach(System.out::println);
//        (7) 所有交易中，最高的交易额是多少？
        System.out.println(data.stream()
                .filter(map -> map.get("address").equals("剑桥"))
                .map(map -> (Integer) map.get("money"))
                .reduce(Integer::max)
                .get());
//        (8) 找到交易额最小的交易。
        System.out.println(data.stream()
                .filter(map -> map.get("address").equals("剑桥"))
                .map(map -> (Integer)map.get("money"))
                .reduce(Integer::min).get());
    }

    /**
     * 生成勾股数
     */
    @Test
    public void createValue(){

        //1
        IntStream.range(1,100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a,100)
                .filter(b -> Math.sqrt(a*a+b*b)%1==0)
                .mapToObj(b -> new int[]{a,b,(int)Math.sqrt(a*a + b*b)}))
                .limit(10)
                .map(Arrays::toString)
                .forEach(System.out::println);

        //2
        IntStream.rangeClosed(1,100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a,100)
                .mapToObj(b -> new double[]{a,b,Math.sqrt(a*a + b*b)})
                .filter(b -> b[2]%1==0))
                .map(Arrays::toString)
                .limit(10)
                .forEach(System.out::println);


    }

    /**
     * 用函数生成流
     */
    @Test
    public void functionGenerateStream(){

        System.out.println(Stream.iterate(new int[]{0, 1}, i -> {
            int j = i[0];
            i[0] = i[1];
            i[1] = j + i[1];
            return i;
        }).map(i -> i[0] + "")
                .limit(10)
                .collect(new ToListCollector<>()));
    }

    /**
     * 测试生成质数
     */
    @Test
    public void generateStream(){
        List list= new ArrayList<Integer>();
        long start = System.currentTimeMillis();
        System.out.println(
                IntStream.range(2, 1000000).boxed()
                        .collect(new PrimeCollect()).get(true)

        );
        System.out.println(System.currentTimeMillis() -start);
    }




}
class Food{
    private String name;
    private int calorie;
    private FoodType foodType;

    public Food(String name, int calorie, FoodType foodType) {
        this.name = name;
        this.calorie = calorie;
        this.foodType = foodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}

enum FoodType{
    VAGETABLE,MEAT,FUILT
}
