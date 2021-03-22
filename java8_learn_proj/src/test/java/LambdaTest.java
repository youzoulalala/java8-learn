import org.junit.Test;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.function.*;

public class LambdaTest {
    public Set<Integer> set = new HashSet<>(Arrays.asList(131, 32, 56, 642, 12, 23, 87));

    //Lambda引入
    @Test
    public void test1() {
        //---------------------------------------
        //Lambda 表达式是一种实现匿名内部类的语法糖
        //代码更加紧凑，语言表达能力更强

        //1.8之前的匿名内部类实现,(Comparator为函数式接口，"FunctionalInterface"进行修饰)
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -Integer.compare(o1, o2);
            }
        };

        //1.8之后lambda表达式的方式，去除了冗余的代码，只需要实现核心部门即可
        Comparator<Integer> com2 = (o1, o2) -> -Integer.compare(o1, o2);

        //测试方法
        TreeSet treeSet = new TreeSet<>(com2);
        treeSet.addAll(set);
        System.out.println(treeSet);

    }

    //Lambda语法介绍
    @Test
    public void test2() {
        //Lambda语法特殊符号 "->"
        //左侧为参数列表
        //右侧为执行的代码，Lambda体

        //无参数，无返回值
        //无参数用()表示，右侧只有一句可以省略{}和return
        Runnable runnable = () -> System.out.println("Runnable implements by lambda");
        runnable.run();

        //有一个参数，无返回值
        //只有一个参数可以省略(), x的参数类型可以听过泛型推断
//        Consumer consumer1 = System.out::println;
        Consumer<String> consumer2 = x -> System.out.println(x);
        consumer2.accept("Comsumer implements by lambda");

        //有两个参数，有返回值
        //lambda体有多条语句用{}括起来，
        Comparator<Integer> comparator1 = (o1, o2) -> {
            System.out.println("comparator for Integer");
            return Integer.compare(o1, o2);
        };
        //lambda体只有一条语句，可以省略return, {}
        Comparator<Integer> comparator2 = (o1, o2) -> Integer.compare(o1, o2);
        //实现接口与某一方法的返回值，参数列表，功能一致，则可以使用方法应用
        Comparator<Integer> comparator3 = Integer::compare;
    }

    //自己创建一个函数式接口
    //可以在参数列表中实现lambda表达式
    @Test
    public void test3() {
        Integer res = operator(1, 3, (o1, o2) -> o1 - o2);//策略模式
        System.out.println(res);
    }

    private Integer operator(Integer o1, Integer o2, MyFun<Integer> myFun) {
        return myFun.calculate(o1, o2);
    }

    List<Employee> emps = Arrays.asList(
            new Employee(101, "Z3", 19, 9999.99),
            new Employee(103, "L4", 20, 7777.77),
            new Employee(102, "W5", 35, 6666.66),
            new Employee(104, "Tom", 44, 1111.11),
            new Employee(105, "Jerry", 60, 4444.44)
    );

    //基本案例
    @Test
    public void test4() {
        //方式一，Employee实现Comparable
//        Collections.sort(emps);
        System.out.println(emps);
        //方式二，听过lambda表达式实现接口
        Collections.sort(emps, (o1, o2) -> -(o1.getAge() - o2.getAge()));
        System.out.println(emps);
    }

    //Java内置四大核心函数式接口（另外还有各类接口，可以避免自己写接口）
    //Comsumer 有一个参数，无返回值
    //Supplier 无参数，有返回值
    //Function 有一个参数，有返回值
    //Predicate 有参数，有 boolean 类型的返回值
    @Test
    public void test5() {
        Consumer<Integer> consumer = integer -> System.out.println(integer);
        Supplier<Integer> supplier = () -> 1;
        Function<String, Integer> function = s -> Integer.parseInt(s);
        Predicate<Integer> predicate = integer -> integer > 0 ? true : false;
    }

    //------------------------------------
    //方法引用
    //若 Lambda 表达式体中的内容已有方法实现，则我们可以使用“方法引用”
    //语法格式：
    //对象 :: 实例方法
    //类 :: 静态方法
    //类 :: 实例方法
    @Test
    public void test6() {
        //对象 :: 实例方法
        final PrintStream out = System.out;
        Consumer consumer0 = x -> System.out.println(x);
        Consumer consumer1 = out::println;
        Consumer consumer2 = System.out::println;
        //类::静态方法
        Comparator<Integer> comparator = (o1, o2) -> Integer.compare(o1, o2);
        Comparator<Integer> comparator1 = Integer::compare;
        //类::实例方法(要求第一个参数是方法调用者，第2 3 4 ...个参数是被调用方法的参数)
        BiPredicate<String, String> predicate = (integer, integer2) -> integer.equals(integer2);
        BiPredicate<String, String> predicate1 = String::equals;
        Function<String, String> function = String::toUpperCase;
    }

    //构造器引用,数字引用
    //构造器的参数列表要与函数时接口中抽象方法的参数列表保持一致。（构造器可以认为是静态方法）
    @Test
    public void test7(){
        //---------------1
        Supplier<List<Integer>> supplier = () -> new ArrayList<>();
        Supplier<List<Integer>> supplier1 = ArrayList::new;
        Supplier<Employee> supplier2 = Employee::new;
        //---------------2
        Function<Integer, String[]> function = String[]::new;
    }
}
