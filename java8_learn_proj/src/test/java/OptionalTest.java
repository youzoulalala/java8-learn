import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

//Optional容器类，存储可能为空的值
public class OptionalTest {
    //Optional.of(T t)：创建一个 Optional 实例
    //Optional.empty(T t)：创建一个空的 Optional 实例
    //Optional.ofNullable(T t)：若 t 不为 null，创建 Optional 实例，否则空实例
    //isPresent()：判断是否包含某值
    //orElse(T t)：如果调用对象包含值，返回该值，否则返回 t
    //orElseGet(Supplier s)：如果调用对象包含值，返回该值，否则返回 s 获取的值
    //map(Function f)：如果有值对其处理，并返回处理后的 Optional，否则返回 Optional.empty()
    //flatmap(Function mapper)：与 map 相似，要求返回值必须是 Optional
    @Test
    public void test01(){
        Optional<Employee> op1 = Optional.of(new Employee());
        Optional<Employee> op2 = Optional.ofNullable(null);//底层实现为empty()方法
        Optional<Employee> op3 = Optional.empty();
        final boolean    present = op1.isPresent();
        Employee e1 = op1.get();
        Employee e2 = op1.orElse(new Employee());
        Employee e3 = op2.orElseGet(()->new Employee());
        Optional<String> e4 = op1.map(Employee::getName);
        Optional<String> e5 = op1.flatMap(x -> Optional.ofNullable(x.getName()));
    }
}
