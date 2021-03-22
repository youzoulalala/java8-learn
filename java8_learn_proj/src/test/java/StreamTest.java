import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    private List<Employee> emps = Arrays.asList(
            new Employee(101, "Z3", 19, 9999.99),
            new Employee(102, "w4", 20, 7777.77),
            new Employee(102, "L5", 35, 6666.66),
            new Employee(104, "Tom", 44, 1111.11),
            new Employee(105, "Jerry", 60, 4444.44)
    );

    public enum Status {
        FREE, BUSY, VOCATION;
    }

    //流式处理（类似流水线式的处理数据）
    //特点：不会存储元素，不会改变源对象，延迟执行（需要结果时才执行）
    //三步流程：1 获取流 2 执行操作 3 生产结果
    @Test
    public void test01() {
        System.out.println("流式处理");
        List<Employee> employees = new ArrayList<>();
        //三步流程
        employees.stream()
                .filter(employee -> employee != null ? true : false)
                .forEach(System.out::println);
    }

    //中间操作
    //filter：过滤元素
    //limit：截断流
    //distinct：去重，根据hashCode和equals方法判断是否为同一对象
    //skip：跳过前面n个元素
    //forEach：遍历流
    @Test
    public void test02() {
        emps.stream()
                .filter(employee -> employee.getAge() > 10)
                .limit(3)
                .distinct()
                .skip(1)
                .forEach(System.out::println);
    }

    //映射
    //map,将元素映射成另外一种形式
    //flatMap,将每一个元素映射为流,扁平化映射
    @Test
    public void test03() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");
        list.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
        emps.stream()
                .limit(3)
                .map(Employee::getAge)
                .forEach(System.out::println);
        StreamTest streamTest = new StreamTest();
        list.stream()
                .flatMap(streamTest::func)
                .forEach(System.out::println);
    }

    private Stream<Character> func(String str) {
        List<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    //排序
    //sort()：要求类实现 java.lang.Comparable（不然报错：java.lang.ClassCastException: Employee cannot be cast to java.lang.Comparable）
    //sort(Comparator c)
    @Test
    public void test04() {
        //sort()
        emps.stream()
                .sorted()
                .forEach(System.out::println);
        //sort(Comparator c)
        emps.stream()
                .sorted((o1, o2) -> o1.getAge().compareTo(o2.getAge()))
                .forEach(System.out::println);
    }

    //查找 匹配（产生结果）
    //allMatch
    //anyMatch
    //noneMatch
    //findFirst
    //findAny
    //count
    //max
    //min
    @Test
    public void test05() {
        //匹配
        List<Status> list = Arrays.asList(Status.FREE, Status.BUSY, Status.BUSY);
        boolean res1 = list.stream()
                .allMatch(status -> status.equals(Status.BUSY));
        System.out.println(res1);
        boolean res2 = list.stream()
                .anyMatch(status -> status.equals(Status.BUSY));
        System.out.println(res2);
        boolean res3 = list.stream()
                .noneMatch(status -> status.equals(Status.BUSY));
        System.out.println(res3);

        //查找
        Optional<Status> res4 = list.stream()//Optinal为返回结果的包装类，用于避免null的情况
                .findFirst();
        //System.out.println(res4.get());//直接通过get()获取结果可能为空
        Status s = res4.orElse(Status.BUSY);
        System.out.println(s);

        Optional<Status> res5 = list.stream()
                .findAny();
        System.out.println(res5.get());

        //计数，最值
        long res6 = list.stream()
                .count();
        System.out.println(res6);

        final Optional<Employee> max = emps.stream()
                .max((o1, o2) -> o1.getAge().compareTo(o2.getAge()));
        final Optional<Employee> min = emps.stream()
                .min((o1, o2) -> o1.getAge().compareTo(o2.getAge()));
        System.out.println(max.orElse(new Employee()));
        System.out.println(min.orElse(new Employee()));
    }

    //收集， 归约
    //reduce:将流中的元素两两运算最终得到一个值
    //collect:收集
    @Test
    public void test06() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Integer res1 = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(res1);
    }

    //收集为集合类
    @Test
    public void test07() {
        //放入 Set, List, 任意的集合类
        final Set<Employee> collect1 = emps.stream()
                .collect(Collectors.toSet());
        final List<String> collect2 = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        final LinkedHashSet<String> collect3 = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    //统计信息（类似sql中的聚合函数）
    @Test
    public void test08() {
        //总数
        Long count = emps.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        //平均值
        Double avg = emps.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        //总和
        Double sum = emps.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        //最大值
        Optional<Employee> max = emps.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());

        //最小值
        Optional<Double> min = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());
    }

    //分组
    @Test
    public void test09() {
        //分组
        final Map<Integer, List<Employee>> collect1 = emps.stream()
                .collect(Collectors.groupingBy(Employee::getId));
        System.out.println(collect1);

        //多级分组
        Map<Integer, Map<String, List<Employee>>> collect2 = emps.stream()
                .collect(Collectors.groupingBy(Employee::getId, Collectors.groupingBy((e) -> {
                    if (e.getAge() > 35) {
                        return "开除";
                    } else {
                        return "继续加班";
                    }
                })));
        System.out.println(collect2);

        //分区
        final Map<Boolean, List<Employee>> collect3 = emps.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getAge() > 35));
        System.out.println(collect3);
    }

    //总结统计
    @Test
    public void test10() {
        final DoubleSummaryStatistics collect = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getAge));
        System.out.println(collect.getAverage());
        System.out.println(collect.getMax());
        System.out.println(collect.getMin());
        System.out.println(collect.getSum());
        System.out.println(collect.getCount());
    }

    //    **案例一：**给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
//    (如：给定【1，2，3，4，5】，返回【1，4，9，16，25】)
    @Test
    public void test11() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        final List<Integer> collect = list.stream()
                .map(x -> x * x)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    //    **案例二：**怎样使用 map 和 reduce 数一数流中有多少个 Employee 呢？
    @Test
    public void test12() {
        final Integer reduce = emps.stream()
                .map(x -> 1)
                .reduce(0, (x, y) -> x + y);
        final Optional<Integer> reduce1 = emps.stream()
                .map(x -> 1)
                .reduce(Integer::sum);
        final Long collect = emps.stream().collect(Collectors.counting());
        final long count = emps.stream().collect(Collectors.summarizingInt(Employee::getId)).getCount();
        System.out.println(reduce);
        System.out.println(collect);
        System.out.println(count);
    }

}
