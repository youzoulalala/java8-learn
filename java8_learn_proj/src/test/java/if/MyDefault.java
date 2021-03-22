//public class MyDefault extends MySupClass implements Myfun1, Myfun2{
//
//}
public class MyDefault implements Myfun1, Myfun2{
    @Override
    public String getName() {
        //多父类时，需要指明父类
        return Myfun1.super.getName();
    }
}

