import org.junit.Test;

import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateTest {
    //LocalDate LocalTime LocalDateTime
    //分别表示日期，时间，日期and时间
    //实例化
    @Test
    public void test01() {
        //三者的方法基本类似，以LocalDateTime为例子
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        LocalDateTime ldt1 = LocalDateTime.of(2020, 11, 11, 11, 11, 11);
        System.out.println(ldt1);
    }

    //日期和时间的基本运算
    @Test
    public void test02() {
        LocalDateTime ldt = LocalDateTime.now();
        //基本运算不会影响参数，而是返回一个新创建的对象
        //plus, minus
        final LocalDateTime ldt1 = ldt.plusDays(10);
        System.out.println(ldt);
        System.out.println(ldt1);
        //获取日期或时间信息
        DayOfWeek dayOfWeek = ldt.getDayOfWeek();
        System.out.println(dayOfWeek);
    }

    //时间戳，程序运行过程中所用的时间对象
    //Instant 1970-01-01 00:00:00 到某个时间之间的毫秒值
    @Test
    public void test03() throws InterruptedException {
        //获取信息
        Instant ins1 = Instant.now();
        Thread.sleep(1000);
        Instant ins2 = Instant.now();
        System.out.println(ins1.toEpochMilli());
        System.out.println(ins2.toEpochMilli());

        //构建
        Instant ins3 = Instant.ofEpochMilli(0);
        System.out.println(ins3);

        //带偏移量的时间日期 (如：UTC + 8)
        OffsetDateTime odt1 = ins1.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt1);
    }

    //计算时间日期之间的差
    //Duration, Period
    @Test
    public void test04() throws InterruptedException {
        //Duration
        Instant ins1 = Instant.now();
        Thread.sleep(1000);
        Instant ins2 = Instant.now();
        final Duration between = Duration.between(ins1, ins2);
        System.out.println(between.getSeconds());

        //Period
        LocalDate localDate1 = LocalDate.now();
        final LocalDate localDate2 = localDate1.plusDays(10);
        final Period betweenDate = Period.between(localDate1, localDate2);
        System.out.println(betweenDate.getDays());
    }

    //时间校正器
    @Test
    public void test05() {
        //指定时间
        final LocalDateTime localDateTime = LocalDateTime.now().withSecond(0);
        System.out.println(localDateTime);

        //通过校正器修改时间
        final LocalDateTime localDateTime1 = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(localDateTime1);

        //获取下一个工作日
        final LocalDateTime localDateTime2 = LocalDateTime.now().minusDays(2).with(temporal -> {
            LocalDateTime ldt = (LocalDateTime) temporal;
            if (ldt.getDayOfWeek().equals(DayOfWeek.FRIDAY))
                return ldt.plusDays(3);
            else if (ldt.getDayOfWeek().equals(DayOfWeek.SATURDAY))
                return ldt.plusDays(2);
            else return ldt.plusDays(1);
        });
        System.out.println(localDateTime2);
    }

    //格式化时间
    //DateTimeFormatter
    @Test
    public void test06() {
        //实例化 DateTimeFormatter
        final DateTimeFormatter isoLocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //格式化时间
        final LocalDateTime now = LocalDateTime.now();
        final String formatStr1 = now.format(isoLocalDateTime);
        final String formatStr2 = dateTimeFormatter.format(now);
        System.out.println(formatStr1);
        System.out.println(formatStr2);

        //将字符串 按照格式化方式进行解析
        final LocalDateTime parse = LocalDateTime.parse("2020-11-11 11:11:11", dateTimeFormatter);
        System.out.println(parse.toString());
    }

    //时区
    @Test
    public void test07() {
        //获取其他时区的时间
        final LocalDateTime now = LocalDateTime.now(ZoneId.of("Australia/Darwin"));
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println(now);
        final String format = now.format(dateTimeFormatter);
        System.out.println(format);

        //在已构建好的日期时间上指定时区
        final LocalDateTime now1 = LocalDateTime.now();
        final ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);
    }

    //Date, LocalDateTime, Instant的相互转换
    @Test
    public void test08() {
        //Instant --->   LocalDateTime
        Instant ins = Instant.now();
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDateTime localDateTime = ins.atZone(zoneId).toLocalDateTime();

        //LocalDateTime ---> Date
        final ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(zoneId);
        final Date from = Date.from(zonedDateTime.toInstant());
        System.out.println(zonedDateTime.toInstant());
        System.out.println(from);

        //Date ---> LocalDateTime
        Date date =new Date();

        final LocalDateTime localDateTime1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), zoneId);
        System.out.println(localDateTime1);
    }
}
