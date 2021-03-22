import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RepeatAnnoTest {
    //重复注解
    @Test
    @MyAnnotation("Hello")
    @MyAnnotation("World")
    public void test01() throws NoSuchMethodException {
        final Class<RepeatAnnoTest> clazz = RepeatAnnoTest.class;
        final Method test01 = clazz.getMethod("test01");
        final MyAnnotation[] annotations = test01.getAnnotationsByType(MyAnnotation.class);
        Arrays.stream(annotations).forEach(myAnnotation -> System.out.println(myAnnotation.value()));
    }
}
