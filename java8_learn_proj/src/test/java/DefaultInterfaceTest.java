import org.junit.Test;

public class DefaultInterfaceTest {
    MyDefault myDefault = new MyDefault();

    //父类与接口默认方法冲突，类优先原则
    @Test
    public void test1(){
        System.out.println(myDefault.getName());
    }

    //接口与接口默认方法冲突，则必须在继承类中实现冲突方法
    @Test
    public void test2(){
        System.out.println(myDefault.getName());
    }
}
