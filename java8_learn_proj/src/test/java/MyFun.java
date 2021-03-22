//只起到修饰的作用，没有该注解仍然可以当作函数式接口来使用
@FunctionalInterface
public interface MyFun<T> {
    T calculate (T o1, T o2);
}
