import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestOCP {
    //    static final String url = "jdbc:mysql://localhost:3306/test";
//    static final String user = "root";
//    static final String pass = "Itpro0902@!";
    public static final String url = "jdbc:mariadb://10.240.202.51:8306/soc";
    public static final String user = "soc";
    public static final String pass = "AdkUI124##";

    public static final String url2 = "jdbc:oracle:thin:@10.58.3.198:1521/roaming";
    public static final String user2 = "autodt";
    public static final String pass2 = "lsdkTTeAR";

    public static final String url3 = "jdbc:mariadb:thin:@10.255.216.33/multi_tenant";
    public static final String user3 = "root";
    public static final String pass4 = "root";
}

class Test01 {
    class Vehicle {
        int distance;

        Vehicle(int x) {
            this.distance = x;
        }

        public void increSpeed(int time) {
            int timeTravel = time; //line n1
            //line n3
            class Car {
                int value = 0;

                public void speed() {
                    value = distance / timeTravel; //line n2
                    System.out.println("Velocity with new speed" + value + "kmph");
                }
            }
//            speed(); //line n3
        }
    }

    public static void main(String[] args) {
//        Vehicle v = new Vehicle(100);
//        v.increSpeed(60);
    }
}

class Test002 {
    public static void main(String[] args) {
        IntStream stream = IntStream.of(1, 2, 3);
        IntFunction<IntUnaryOperator> inFu = x -> y -> x * y; //line n1
        IntStream newStream = stream.map(inFu.apply(10)); //line n2
        newStream.forEach(System.out::print);
    }
}

class Tess004 {
    public static class Foo {
        public static void main(String[] args) {
            Map<Integer, String> unsortMap = new HashMap<>();
            unsortMap.put(10, "z");
            unsortMap.put(5, "b");
            unsortMap.put(1, "d");
            unsortMap.put(7, "e");
            unsortMap.put(50, "j");
            Map<Integer, String> treeMap = new TreeMap<Integer, String>(
                    new Comparator<Integer>() {
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            return o2.compareTo(o1);
                        }
                    });
            treeMap.putAll(unsortMap);
            for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                System.out.print(entry.getValue() + " ");
            }
        }
    }

}

class Test06 {
    public static class Counter {
        public static void main(String[] args) {
            int a = 10;
            int b = -1;
            assert (b >= 1) : "Invalid Denominator";
            int c = a / b;
            System.out.println(c);
        }
    }
}

class Test7 {
    static class Bird {
        public void fly() {
            System.out.print("Can fly");
        }
    }

    static class Penguin extends Bird {
        public void fly() {
            System.out.print("Cannot fly");
        }
    }

    static class Birdie {
        public static void main(String[] args) {
            fly(() -> new Bird());
            fly(Penguin::new);
        }


        static void fly(Supplier<? extends Bird> bird) {
            bird.get().fly();
        }

    }
}

class Test08 {
    /* 1.*/ abstract class Shape {
        /*2.*/ Shape() {
            System.out.println("Shape");
        }

        /*3.*/
        protected void area() {
            System.out.println("Shape");
        }
        /*4.*/
    }

    /*5.*/
    /*6.*/ class Square extends Shape {
        /*7.*/ int side;

        /*8.*/ Square(int side) {
            /*9.*/ /* insert code here */
            /*10.*/
            this.side = side;
            /*11.*/
        }

        /*12.*/
        public void area() {
            System.out.println("Square");
        }
        /*13.*/
    }

    /*14.*/ class Rectangle extends Square {
        /*15.*/ int len, br;

        /*16.*/ Rectangle(int x, int y) {
            /*17.*/ /* insert code here */
            super(x);
            /*18.*/
            len = x;
            br = y;
            /*19.*/
        }

        /*20.*/
        public void area() {
            System.out.println("Rectangle");
        }
        /*21.*/
    }
}

class Test09 {
    static class Sum extends RecursiveAction { //line n1
        static final int THRESHOLD_SIZE = 3;
        int stIndex, lstIndex;
        int[] data;

        public Sum(int[] data, int start, int end) {
            this.data = data;
            this.stIndex = start;
            this.lstIndex = end;
        }

        protected void compute() {
            int sum = 0;
            if (lstIndex - stIndex <= THRESHOLD_SIZE) {
                for (int i = stIndex; i < lstIndex; i++) {
                    sum += data[i];
                }
                System.out.println(sum);
            } else {
                new Sum(data, stIndex + THRESHOLD_SIZE, lstIndex).fork();
                new Sum(data, stIndex,
                        Math.min(lstIndex, stIndex + THRESHOLD_SIZE)
                ).compute();
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool fjPool = new ForkJoinPool();
        int data[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        fjPool.invoke(new Sum(data, 0, data.length));
    }
}

// cau nay chu y: neu them abstract vao operator thi van error
class Test10 {
    public abstract class Operator {
        protected abstract void turnON();

        protected abstract void turnOFF();
    }

    public class EngineOperator extends Operator{
        public final void turnON() {
            System.out.println("ON");
        }

        public final void turnOFF(){
            System.out.println("OFF");
        }
    }

    public class Engine{
        Operator m = new EngineOperator();
        public void operate(){
            m.turnON();
            m.turnOFF();
        }
    }

    public static void main(String[] args) {
//        Engine carEngine = new Engine();
//        carEngine.operate();
    }
}

class Test11 {
    public static void main(String[] args) {
        Stream<List<String>> iStr = Stream.of(
                Arrays.asList("1", "John"),
                Arrays.asList("2", null));
//        Stream<String> nInSt = iStr.flatMapToInt ((x) -> x.stream ());
//        nInSt.forEach (System.out :: print);
    }
}

class Test12 {
    public static void main(String[] args) throws Exception {
        Path file = Paths.get("/home/vtn-thientv7-u/Documents/TestOCP/src/courses.txt");
//        /*A.*/ List<String> fc = Files.list(file);
//        fc.stream().forEach (s -> System.out.println(s));
//        /*B.*/ Stream<String> fc = Files.readAllLines (file);
//        fc.forEach (s -> System.out.println(s));
//        /*C.*/ List<String> fc = readAllLines(file);
//        fc.stream().forEach (s -> System.out.println(s));
        /*D.*/
        Stream<String> fc = Files.lines(file);
        fc.forEach(s -> System.out.println(s));
    }
}
//
class Test13 {

}

class Test14 {
    static void doStuff() throws ArithmeticException, NumberFormatException, Exception {
        if (Math.random() > -1){
            throw new ArithmeticException("Try again");
        }
    }

    public static void main(String[] args) {
        try {
            doStuff();
        } catch (ArithmeticException|NumberFormatException e){
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


class Test15 {
    public static class Country {
        public enum Continent {ASIA, EUROPE}

        String name;
        Continent region;

        public Country(String na, Continent reg) {
            name = na;
            region = reg;
        }

        public String getName() {
            return name;
        }

        public Continent getRegion() {
            return region;
        }
    }

    public static void main(String[] args) {
        List<Country> couList = Arrays.asList(
                new Country("Japan", Country.Continent.ASIA),
                new Country("Italy", Country.Continent.EUROPE),
                new Country("Germany", Country.Continent.EUROPE));
        Map<Country.Continent, List<String>> regionNames = couList.stream()
                .collect(Collectors.groupingBy(Country::getRegion,
                        Collectors.mapping(Country::getName, Collectors.toList())));
        System.out.println(regionNames);
    }
}

class Test17 {
    static class Book {
        int id;
        String name;

        public Book(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public boolean equals(Object obj) { //line n1
            boolean output = false;
            Book b = (Book) obj;
            if (this.name.equals(b.name)) {
                output = true;
            }
            return output;
        }
    }

    public static void main(String[] args) {
        Book b1 = new Book(101, "Java Programing");
        Book b2 = new Book(102, "Java Programing");
        System.out.println(b1.equals(b2));//line n2
    }
}

class Test20 {
    public static void main(String[] args) {
        Path p1 = Paths.get("/Pics/MyPic.jpeg");
        System.out.println(p1.getNameCount() +
                ":" + p1.getName(1) +
                ":" + p1.getFileName());
    }
}

class Test23 {
    public static void main(String[] args) throws Exception {
        Path source = Paths.get("a.txt");
        Path des = Paths.get("b");
        Files.copy(source, des);
    }
}

class Test24 {
    public static class Student {
        String course, name, city;

        public Student(String name, String course, String city) {
            this.course = course;
            this.name = name;
            this.city = city;
        }

        public String toString() {
            return course + ":" + name + ":" + city;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }
    }

    public static void main(String[] args) {
        List<Student> stds = Arrays.asList(
                new Student("Jessy", "Java ME", "Chicago"),
                new Student("Helen", "Java EE", "Houston"),
                new Student("Mark", "Java ME", "Chicago"));
        stds.stream()
                .collect(Collectors.groupingBy(Student::getCourse))
                .forEach((src, res) -> System.out.println(src))
        ;
    }
}

class Test25 {
    interface CourseFilter extends Predicate<String> {
        public default boolean test(String str) {
            return str.equals("Java");
        }
    }

    public static void main(String[] args) {
        List<String> strs = Arrays.asList("Java", "Java EE", "Java ME");
        Predicate<String> cf1 = s -> s.length() > 3;
        Predicate cf2 = new CourseFilter() { //line n1
            public boolean test(String s) {
                return s.contains("Java");
            }
        };
        long c = strs.stream()
                .filter(cf1)
                .filter(cf2) //line n2
                .count();
        System.out.println(c);
    }
}

class Test26 {
    public static class Emp {
        String fName;
        String lName;

        public Emp(String fn, String ln) {
            fName = fn;
            lName = ln;
        }

        public String getfName() {
            return fName;
        }

        public String getlName() {
            return lName;
        }
    }

    public static void main(String[] args) {
        List<Emp> emp = Arrays.asList(
                new Emp("John", "Smith"),
                new Emp("Peter", "Sam"),
                new Emp("Thomas", "Wale"));
        List<Emp> lst = emp.stream()
                //line n1
                .sorted(Comparator.comparing(Emp::getfName).reversed().thenComparing
                        (Emp::getlName))
                .collect(Collectors.toList());
        System.out.println(lst.size());
    }
}

class Test53 {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(TestOCP.url, TestOCP.user, TestOCP.pass);
        String query = "SELECT APN_ID FROM agg_apn_info";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            stmt.executeQuery("SELECT ft_acc FROM account_ft");
            while (rs.next()) {
                //process the results
                System.out.println("Employee ID: " + rs.getInt("APN_ID"));
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        conn = DriverManager.getConnection("jdbc:oracle:thin:@10.58.3.198:1521/roaming", "autodt", "lsdkTTeAR");
        query = "SELECT ID FROM ABIS_PLAN";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            stmt.executeQuery("SELECT ID FROM BLOCK_WEBSITE_MOP");
            while (rs.next()) {
                //process the results
                System.out.println("Employee ID: " + rs.getInt("ID"));
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}

class Test58 {
    public static void main(String[] args) {
        String str = "Java is a programming language";
        ToIntFunction<String> indexVal = str::indexOf; //line n1
        int x = indexVal.applyAsInt("Java"); //line n2
        System.out.println(x);
    }
}

class Test61 {
    public interface Moveable<Integer> {
        public default void walk(Integer distance) {
            System.out.println("Walking");
        }

        public void run(Integer distance);
    }

    public static void main(String[] args) {
        Moveable<Integer> animal = n -> System.out.println("Running" + n);
        animal.run(100);
        animal.walk(20);
    }
}

class Test63 {
    class FuelNotAvailException extends Exception {
    }

    class Vehicle {
        //        void ride() throws FuelNotAvailException { //line n1
        protected void ride() throws Exception { //line n1
            System.out.println("Happy Journey!");
        }
    }

    class SolarVehicle extends Vehicle {
        public void ride() throws Exception { //line n2
            super.ride();
        }
    }
}

class Test64 {
    public static class Emp {
        private String eName;
        private Integer eAge;

        Emp(String eN, Integer eA) {
            this.eName = eN;
            this.eAge = eA;
        }

        public Integer getEAge() {
            return eAge;
        }

        public String getEName() {
            return eName;
        }
    }

    public static void main(String[] args) {
        List<Emp>li = Arrays.asList(new Emp("Sam", 20), new Emp("John", 60), new Emp
                ("Jim", 51));
        Predicate<Emp> agVal = s -> s.getEAge() > 50; //line n1
        li = li.stream().filter(agVal).collect(Collectors.toList());
        Stream<String> names = li.stream().map(Emp::getEName); //line n2
        names.forEach(n -> System.out.print(n + " "));
    }
}
class Test66{
    public static void main(String[] args) {
        LocalDate valentinesDay =LocalDate.of(2015, Month.FEBRUARY, 14);
        LocalDate nextYear = valentinesDay.plusYears(1);
        nextYear.plusDays(15); //line n1
        System.out.println(nextYear);
    }
}
class Test67{
    public static void main(String[] args) {
        BiFunction<Integer, Double, Integer> val = (t1, t2) -> (int)(t1 + t2); //line n1
        System.out.println(val.apply(10, 10.5));
    }
}

class Test86 {
    public static void main(String[] args) {
//        Path path1 = Paths.get("/software/././sys/readme.txt"); 5 : 3: 6
        Path path1 = Paths.get("/software/.././sys/readme.txt"); // 5: 2: 7
        Path path2 = path1.normalize();
        Path path3 = path2.relativize(path1);

        System.out.println(path1.getNameCount());
        System.out.println(" : "+path2.getNameCount() +" "+path2);
        System.out.println(" : "+path3.getNameCount()+ " "+path3);
    }
}

class Test88 {
    public static void main(String[] args) {
        Locale currentLocale = new Locale.Builder().setRegion("US").setLanguage("en").build();
        ResourceBundle messages = ResourceBundle.getBundle("/home/vtn-thientv7-u/Documents/TestOCP/MessagesBundle", currentLocale);
        Enumeration<String> names = messages.getKeys();

        while (names.hasMoreElements()){
            String key = names.nextElement();
            String name = messages.getString(key);
            System.out.println(key+" = "+name);
        }
    }
}



class Test110 {

    interface LengthValidator {
        public boolean checkLength(String str);
    }

    public static class Txt {
        public static void main(String[] args) {
            boolean res = ((LengthValidator) str -> str.length() > 5 && str.length() < 10).checkLength("Hello");
        }

    }
}

class Test111 {
    public static class Product {
        public double applyDiscount(double price) {
            assert (price > 0); // Line nl
            return price * 50;
        }
    }

    public static void main(String[] args) {
        Product p = new Product();
        double newPrice =
                p.applyDiscount(Double.parseDouble(args[0]));
        System.out.println("New Price:" + newPrice);
    }
}

class Test113 {
    static class R implements Runnable {
        public void run() {
            System.out.println("Run...");
        }
    }

    static class C implements Callable<String> {
        public String call() throws Exception {
            return "Call...";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(new R());    // line nl
        Future<String> fl = es.submit(new C()); // line n.2
        System.out.println(fl.get());
        es.shutdown();

    }
}

class Test115 {
    public static void main(String[] args) {
        Deque<String> queue = new ArrayDeque<>();
        queue.add("Susan");
        queue.add("Allen");
        queue.add("David");
        System.out.println(queue.pop());
        System.out.println(queue.remove());
        System.out.println(queue);

    }
}

class Test117 {
    public static void main(String[] args) {
        List<String> valList = Arrays.asList("", "Georgs", "", "John", "Jim");
        Long newVal = valList.stream()    // line nl
                .filter(x -> !x.isEmpty())
                .count();    // line n2
        System.out.println(newVal);
    }
}

class Test118 {
    public static void main(String[] args) throws InterruptedException {
        // Login time ;2015-01—12T21:58;8ITZ
        Instant loginTime = Instant.now();
        Thread.sleep(1000);
// Logout time:2015-01-12T21:58:B60Z
        Instant logoutTime = Instant.now();
        loginTime = loginTime.truncatedTo(ChronoUnit.MINUTES);    // line nl
        logoutTime = logoutTime.truncatedTo(ChronoUnit.MINUTES);
        if (logoutTime.isAfter(loginTime))
            System.out.println("Logged out at: " + logoutTime);
        else
            System.out.println("Can*t logout");

    }
}

class Test119 {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("win","try","best", "luck", "do");

        Predicate<String> test1 = w -> {
            System.out.println("Checking...");
            return w.equals("do");
        };

        // case nay thi loi o n2
        /*Predicate test2 = (String w) -> w.length() > 3; // n2
        words.stream()
                .filter(test2)
                .filter(test1)
                .count();*/

        // case nay thi dung, in ra Checking... Checking...
        Predicate<String> test2 =  w -> w.length() > 3; // n2
        words.stream()
                .filter(test2)
                .filter(test1)
                .count();


    }
}

class Test120 {
    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("customers.txt"));
        lines.forEach(c -> System.out.println(c));
    }
}

class Test121 {
    static class MyClass implements AutoCloseable {
        int test;

        public void close() {
        }

        public MyClass copyObject() {
            return this;
        }
    }

    public static void main(String[] args) {
        //and the code fragment:
        MyClass obj = null;
        try (MyClass objl = new MyClass()) {
            objl.test = 100;
            obj = objl.copyObject(); // line n1
            System.out.println(obj.test); // line n2
        }
    }
}

class Test124 {
    public static class Foo<K, V> {
        private K key;
        private V value;

        public Foo(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public static <T> Foo<T, T> twice(T value) {
            return new Foo<T, T>(value, value);
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Foo<String, Integer> mark = new Foo<String, Integer>("Steve", 100);
        Foo<String, String> pair = Foo.<String>twice("Hello World!");
//         Foo<Object, Object> percentage = new Foo<String, Integer>("Steve", 100);
        Foo<String, String> grade = new Foo<>("John", "A");
    }
}

class Test128 {
    public static void main(String[] args) {
        Map<Integer, Integer> mVal = new HashMap<>();
        mVal.put(1, 10);
        mVal.put(2, 20);
//line nl
        BiConsumer<Integer, Integer> c = (i, j) -> {
            System.out.print(i + "," + j + ";");
        };

//        BiFunction<Integer, Integer, String> c = (i, j) ->{
//            System.out.print(i + "," + j + "; ");
//        }) ;
//        BiConsumer<Integer, Integer, String> c = (i, j) -> {System.out.print (i + "," +
//                j+ "; ")};
//        BiConsumer<Integer, Integer, Integer> c = (i, j) -> {System.out.print (i + ","
//                + j+ "; ");};
//        c.accept(1, 2);
        mVal.forEach(c);

    }
}

class Test132 {
    public static void main(String[] args) {
        final List<String> list = new CopyOnWriteArrayList<>();
        final AtomicInteger ai = new AtomicInteger();
        final CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println(list);
            }
        });
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * ai.incrementAndGet());
                    list.add("X");
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
    }

}

class Test133 {
    public static void main(String[] args) throws Exception {
        new File("\\company").mkdirs();
        new FileOutputStream("\\company\\emp\\info.txt").close();
        new File("\\company\\emp").mkdirs();
        new File("\\company\\emp\\benefits").mkdirs();
        new FileOutputStream("\\company\\emp\\benefits\\btxt").close();

//        A.
        Stream<Path> stream = Files.list(Paths.get("/company"));
//        B.
        Stream<Path> stream2 = Files.find(
                Paths.get("/company"), 1,
                (p, b) -> b.isDirectory(), FileVisitOption.FOLLOW_LINKS);
//        C.
        Stream<Path> stream3 = Files.walk(Paths.get("/company"));
//        D.
        Stream<Path> stream4 = Files.list(Paths.get("/company/emp"));
        System.out.println("A=======");
        stream.forEach(s -> System.out.println(s));
        System.out.println("B=======");
        stream2.forEach(s -> System.out.println(s));
        System.out.println("C=======");
        stream3.forEach(s -> System.out.println(s));
        System.out.println("D=======");
        stream4.forEach(s -> System.out.println(s));
    }
}

class Test134 {
    class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}

class Test135 {
    //A

    //B
    class Computer {
        private Card card = new SoundCard();

        private abstract class Card {
        }

        private class SoundCard extends Card {
        }
    }
}

class Test136 {
    public static void main(String[] args) {
        Deque<Integer> nums = new ArrayDeque<>();
        nums.add(1000);
        nums.push(2000);
        nums.add(3000);
        nums.push(4000)
        ;
        System.out.println(nums.remove());
        System.out.println(nums.pop());
    }
}

class Test137 {
    public static void main(String[] args) throws Exception {
       /* try (FileInputStream fis = new FileInputStream("/home/vtn-thientv7-u/Documents/TestOCP/src/version.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);) {
            if (br.markSupported()) {
                System.out.print((char) br.read());
                br.mark(2);
                System.out.print((char) br.read());
                br.reset();
                System.out.print((char) br.read());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //1234567890
        try (FileInputStream fis = new FileInputStream("/home/vtn-thientv7-u/Documents/TestOCP/src/version.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);) {
            if (br.markSupported()) {
                System.out.print((char) br.read());
                br.mark(1);
                System.out.print((char) br.read());
                System.out.print((char) br.read());
                br.reset();
                System.out.print((char) br.read());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Test138 {
    public static void main(String[] args) {
        BiPredicate<String, String> bp = (String s1, String s2) -> {
            return s1.contains("SG")
                    && s2.contains("Java");
        };

        BiFunction<String, String, Integer> bf = (String s1, String s2) -> {
            int fee = 0;
            if (bp.test(s1, s2)) {
                fee = 100;
            }
            return fee;
        };

        int feel = bf.apply("D101SG", "Java Programming");
        System.out.println(feel);

    }
}
class Test139{
    public static void main(String[] args) {
        Locale currentLocale;
        currentLocale = new Locale("de","DE");
//        currentLocale = new Locale();
//        currentLocale = Locale.GERMAN;
//        currentLocale = Locale.getInstance(Locale.GERMAN,Locale.GERMANY);
        currentLocale = new Locale ("de", "DE");
        System.out.println(currentLocale);
        currentLocale = new Locale.Builder ().setLanguage ("de").setRegion ("DE").build();
        System.out.println(currentLocale);
//        currentLocale = Locale.GERMAN;
//        System.out.println(currentLocale);
        ResourceBundle message = ResourceBundle.getBundle("mbx",currentLocale);
        System.out.println(message.getString("inquiry"));
    }
}

class Test140 {
    public static void main(String[] args) {
        List<String> qwords = Arrays.asList("why", "what", "when");
        BinaryOperator<String> operator = (s1, s2) -> s1.concat(s2);
        String sen = qwords.stream()
//                .sorted() // theem o day thi se thanh (Word: what when why)
                .reduce("Word: ", operator);
//                .sorted(); // khong them sort o day duoc se bi compile fail

        System.out.println(sen);
    }
}


class Test143 {
    public static void main(String[] args) {
        Stream.of("Java", "Unix", "Linux")
                .filter(s -> s.contains("n"))
                .peek(s -> System.out.println("PEEK: " + s))
//                .findFirst();
//                .sorted()
                .findAny();
    }
}

class Test145 {
    public static void main(String[] args) {
        List<String> strings;
    }
}

class Test147 {

    public static class ResourcesApp {
        public void loadResourceBundle() {
            ResourceBundle resource = ResourceBundle.getBundle("Greetings", Locale.US);
//            System.out.println(resource.getString("hello_msg"));
//            System.out.println(resource.getString("HELLO_MSG"));
            System.out.println(resource.getObject("HELLO_MSG"));
        }

        public static void main(String[] args) {
            new ResourcesApp().loadResourceBundle();
        }
    }

}

class Test148 {
    public static class Test {
        static List<String> list = null;

        public void printValue() {
            System.out.println(getList());
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public static void main(String[] args) {
        List<String> li = Arrays.asList("Dog", "Cat", "Mouse");
        Test test = new Test();
        test.setList(li.stream().collect(Collectors.toList()));
//        test.getList().forEach(Test::printValue);
    }
}

class Test149 {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(TestOCP.url, TestOCP.user, TestOCP.pass);
        Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        st.execute("select * from student");
        ResultSet rs = st.getResultSet();
//        Moves the cursor to the given row number in this <code>ResultSet</code> object
//        The first row is row 1, the second
//                * is row 2, and so on.
        rs.absolute(3);
        rs.moveToInsertRow();
        rs.updateInt(1, 113);
        rs.updateString(2, "Jan");
        rs.updateString(3, "jann@uni.com");
        /**
         * Updates the underlying database with the new contents of the
         * current row of this <code>ResultSet</code> object.
         * This method cannot be called when the cursor is on the insert row.
         *
         * @exception SQLException if a database access error occurs;
         * the result set concurrency is <code>CONCUR_READ_ONLY</code>;
         *  this method is called on a closed result set or
         * if this method is called when the cursor is on the insert row
         * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
         * this method
         * @since 1.2
         */
        rs.updateRow();
        /**
         * Refreshes the current row with its most recent value in
         * the database.  This method cannot be called when
         * the cursor is on the insert row.
         *
         */
        rs.refreshRow();
        System.out.println(rs.getInt(1));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));
    }
}

class Test150 {
    public static void main(String[] args) {
        IntConsumer consumer = e -> System.out.println(e);
        Integer value = 90;
//       A
//        Function<Integer> funRef = e -> e + 10;
//        Integer result = funRef.apply(value);
//        B.
//        IntFunction funRef = e -> e + 10;
//        Integer result = funRef.apply(10);
//        C.
        ToIntFunction<Integer> funRef = e -> e + 10;
        int result = funRef.applyAsInt(value);
//        D.
//        ToIntFunction funRef = e -> e + 10;
//        int result = funRef.apply (value);
        consumer.accept(result);
    }
}

class Test154 {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("");
        Statement statement = conn.createStatement();
//          * @return <code>true</code> if the first result is a <code>ResultSet</code>
//     *         object; <code>false</code> if it is an update count or there are
//     *         no results
        statement.execute("");
    }
}

class Test155 {
//    class Video {
//        public void play() throws IOException {
//            System.out.println("Video play");
//        }
//    }
//
//    class Game extends Video {
//        public void play() throws IOException {
//            super.play();
//            System.out.println("Video play");
//
//        }
//    }

}

class Test157 {
    public static void main(String[] args) throws Exception {
        InputStream in;
        BufferedReader br = new BufferedReader(new FileReader("src/resource/data.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/resource/data.txt"));
        String line = null;
        while ((line = br.readLine()) != null) {
            bw.append(line + "\n");
        }
        bw.flush();
    }
}

class Test159 {
    static class Counter extends Thread {
        int i = 10;

        public synchronized void display(Counter obj) {
            try {
                Thread.sleep(5);
                obj.increment(this);
                System.out.println(i);
            } catch (InterruptedException ex) {
            }
        }

        public synchronized void increment(Counter obj) {
            i++;
        }
    }

    public static class Test {
        public static void main(String[] args) {
            final Counter obj1 = new Counter();
            final Counter obj2 = new Counter();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    obj1.display(obj2);
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    obj2.display(obj1);
                }
            }).start();
        }
    }

}
class Test160{
    static class Employee{
        String dept;
        String name;

        public Employee(String dept, String name) {
            this.dept = dept;
            this.name = name;
        }

        public String getDept() {
            return dept;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getDept()+":"+getName();
        }
    }

    public static void main(String[] args) {
        List<Employee> emps = Arrays.asList(new Employee("sales", "Ada"),
                new Employee("sales","Bod"),
                new Employee("hr","Bod"),
                new Employee("hr","Eva")
        );
        Stream<Employee> s = emps.stream().sorted(Comparator.comparing((Employee e) -> e.getDept()).thenComparing((Employee e) -> e.getName()));
        System.out.println(s.collect(Collectors.toList()));
    }
}

class Test161 {
    static class ThreadRunner implements Runnable {
        public void run() {
            System.out.print("Runnable");
        }
    }

    static class ThreadCaller implements Callable {
        public String call() throws Exception {
            return "Callable";
        }
    }
//https://docs.oracle.com/cd/E13222_01/wls/docs45/classdocs/java.sql.Statement.html
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newCachedThreadPool();
        Runnable r1 = new ThreadRunner();
        Callable c1 = new ThreadCaller();
// line n1

//        Future<String> f1 = (Future<String>) es.submit (r1);
//        es.execute (c1);
//        B.
//                es.execute (r1);
//        Future<String> f1 = es.execute (c1) ;
//        C.
//        Future<String> f1 = (Future<String>) es.execute(r1);
//        Future<String> f2 = (Future<String>) es.execute(c1);
//        D.
        es.submit(r1);
        Future<String> f1 = es.submit(c1);
        es.shutdown();
    }
}

class Test162 {
    public static void main(String[] args) {
        List<Double> doubles = Arrays.asList(100.12, 200.32);
        DoubleFunction funD = d -> d + 100.0;
//        doubles.stream ().forEach (funD); // line n1
        doubles.stream().forEach(e -> System.out.println(e)); // line n
    }
}

class Test163 {
    public static class Product {
        int id;
        int price;

        public Product(int id, int price) {
            this.id = id;
            this.price = price;
        }

        public String toString() {
            return id + ":" + price;
        }
    }

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(Arrays.asList(new Product(1, 10),
                new Product(2, 30),
                new Product(3, 20)));
        Product p = products.stream().reduce(new Product(4, 0), (p1, p2) -> {
            p1.price += p2.price;
            return new Product(p1.id, p1.price);
        });
        products.add(p);
        products.stream().parallel()
                .reduce((p1, p2) -> p1.price > p2.price ? p1 : p2)
                .ifPresent(System.out::println);
    }
}

class Test164 {
    static class Student {
        String course, name, city;

        public Student(String name, String course, String city) {
            this.course = course;
            this.name = name;
            this.city = city;
        }

        public String toString() {
            return course + ":" + name + ":" + city;
        }

        public String getCourse() {
            return course;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }
    }

    public static void main(String[] args) {
        List<Student> stds = Arrays.asList(
                new Student("Jessy", "Java ME", "Chicago"),
                new Student("Helen", "Java EE", "Houston"),
                new Student("Mark", "Java ME", "Chicago"));
        stds.stream()
                .collect(Collectors.groupingBy(Student::getCourse))
                .forEach((src, res) -> System.out.println(src));
    }
}

class Test165 {
    static class MySclass implements Runnable {
        public int value;

        @Override
        public synchronized void run() {
            while (value < 100) {
                value++;
                System.out.println(value);
            }
        }
    }

    public static void main(String[] args) {
        MySclass mc = new MySclass();
        Thread a = new Thread(mc);
        a.start();
        Thread b = new Thread(mc);
        b.start();

    }
}
class Test166{
    static class MyThread implements Runnable {

        private String src[] = {"A","B","C"};
        private int count = 0; //line 1

        public synchronized void run() {  //line2
            while (count< src.length){
                System.out.print(src[count++]);
            }
        }
    }

    public static void main(String[] args) {
        MyThread mt = new MyThread();
        Thread t1 = new Thread(mt);
        Thread t2 = new Thread(mt);
        t1.start();
        t2.start();
    }
}

class Test167 {
    public static void main(String[] args) throws Exception {
        new File("resource\\sports").mkdirs();
        new FileOutputStream("resource\\sports\\info.txt").close();
        new File("resource\\sports\\cricket").mkdirs();
        new FileOutputStream("resource\\sports\\cricket\\players.txt").close();
        new File("resource\\sports\\cricket\\data").mkdirs();
        new FileOutputStream("resource\\sports\\cricket\\data\\ODI.txt").close();
        int maxDepth = 2;
        Stream<Path> paths = Files.find(Paths.get("resource\\sports"),
                maxDepth,
                (p, a) -> p.getFileName().toString().endsWith("txt"),
                FileVisitOption.FOLLOW_LINKS);
        Long fCount = paths.count();
        System.out.println(fCount);
    }
}

class Test169 {
    class FuelNotAvailException extends Exception {
    }

    static class Vehicle {
        void ride() throws FuelNotAvailException { //line n1
            System.out.println("Happy Journey!");
        }
    }

    static class SolarVehicle extends Vehicle {
        public void ride() throws FuelNotAvailException { //line n2
            super.ride();
        }
    }

    public static void main(String[] args) throws Exception {
        Vehicle v = new SolarVehicle();
        v.ride();
    }
}

class Test170 {
    public static class Counter {
        public static void main(String[] args) {
            int a = 10;
            int b = -1;
            assert (b >= 1) : "Invalid Denominator";
            int c = a / b;
            System.out.println(c);
        }
    }
}

class Test171 {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(TestOCP.url, TestOCP.user, TestOCP.pass);
            Statement st = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            st.execute("SELECT * FROM test.Player");
//            Sets the limit for the maximum number of rows that any
            st.setMaxRows(2);
            ResultSet rs = st.getResultSet();
//     * @return <code>true</code> if the cursor is moved to a position in this
//     * <code>ResultSet</code> object;
//     * <code>false</code> if the cursor is before the first row or after the
//     * last row
            rs.absolute(3);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("SQLException is thrown.");
        }
    }
}

class Test172 {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(TestOCP.url, TestOCP.user, TestOCP.pass);
            String query = "Select * FROM Item WHERE ID = 110";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getString(1));
                System.out.println("Description: " + rs.getString(2));
                System.out.println("Price: " + rs.getString(3));
                System.out.println("Quantity: " + rs.getString(4));
            }
        } catch (SQLException se) {
            System.out.println("Error");
        }
    }
}
class Test173{
    interface CourseFilter extends Predicate<String> {
        public default boolean test(String str) {
            return str.contains("Java");
        }
    }

    public static void main(String[] args) {
        List<String> strs = Arrays.asList("Java", "Java EE", "Embedded Java");
        Predicate<String> cf1 = s -> s.length() > 3;
        Predicate cf2 = new CourseFilter() {         //line n1
            public boolean test(String s) {
                return s.startsWith("Java");
            }
        };
        long c = strs.stream().filter(cf1).filter(cf2)                        //line n2
                .count();
        System.out.println(c);
    }
}
class Test174{
    public static class Foo174<K, V> {
        private K key;
        private V value;

        public Foo174(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public static <T> Foo174<T, T> twice(T value) {
            return new Foo174<T, T>(value, value);
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
//        /*A.*/ Foo174<String, Integer> mark = new Foo174<Object, Object> ("Steve", 100);
        /*B.*/ Foo174<String, String> pair = Foo174.<String>twice ("Hello World!");
        /*C.*/ Foo174<Object, Object> percentage = new Foo174<Object, Object>("Steve", 100);
        /*D.*/ Foo174<String, String> grade = new Foo174 <> ("John", "A");
    }
    
}

class Test175{
    public static void main(String[] args) {
        Map<Integer, String> books = new TreeMap<>();
        books.put(1007, "A");
        books.put(1002, "C");
        books.put(1003, "B");
        books.put(1003, "B");
        System.out.println(books);
    }
}
class Test176{
    public class Canvas implements Drawable {
        public void draw() {
        }
    }

    public abstract class Board extends Canvas {
    }

    public class Paper extends Canvas {
        protected void draw(int color) {
        }
    }

   /* public class Frame extends Canvas implements Drawable {
        public void resize() {
        }

        abstract void open();
    }*/

    public interface Drawable {
        public abstract void draw();
    }
}

class Test177{
    public static void main(String[] args) {
        UnaryOperator<Double> uo1 = s -> s * 2;        //line n1
        List<Double> loanValues = Arrays.asList(1000.0, 2000.0);
        loanValues.stream().filter(lv -> lv >= 1500).map(lv -> uo1.apply(lv))            //line n2
                .forEach(s -> System.out.print(s + " "));
    }
}

class Test178 {
    public static void main(String[] args) {
        Path path1 = Paths.get("/app/./sys/");
        Path res1 = path1.resolve("log");
        Path path2 = Paths.get("/server/exe/");
        Path res2 = path2.resolve("/readme/");
        System.out.println(res1);
        System.out.println(res2);
    }
}

class Test179 {
    public static void main(String[] args) {
        List<String> nL = Arrays.asList("Jim", "John", "Jeff");
        Function<String, String> funVal = s -> "Hello : ".concat(s);
        nL.stream()
                .map(funVal)
                .forEach(s -> System.out.print(s));
    }
}

class Test180 {
    public static void main(String[] args) {
        List<String> colors = Arrays.asList("red", "green", "yellow");
        Predicate<String> test = n -> {
            System.out.println("Searching…");
            return n.contains("red");
        };
        colors.stream()
                .filter(c -> c.length() >= 3)
                .allMatch(test);
    }
}

class Test181 {
    public static class Emp {
        private String eName;
        private Integer eAge;

        Emp(String eN, Integer eA) {
            this.eName = eN;
            this.eAge = eA;
        }

        public Integer getEAge() {
            return eAge;
        }

        public String getEName() {
            return eName;
        }
    }

    public static void main(String[] args) {
        List<Emp> li = Arrays.asList(new Emp("Sam", 20),
                new Emp("John", 60),
                new Emp("Jim", 51));
        Predicate<Emp> agVal = s -> s.getEAge() <= 60; //line n1
        li = li.stream().filter(agVal).collect(Collectors.toList());
        Stream<String> names = li.stream().map(Emp::getEName); //line n2
        names.forEach(n -> System.out.print(n + " "));
    }
}

class Test182 {
    static class Book {
        int id;
        String name;

        public Book(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public boolean equals(Object obj) { //line n1
            boolean output = false;
            Book b = (Book) obj;
            if (this.id == b.id) {
                output = true;
            }
            return output;
        }
    }

    public static void main(String[] args) {
        Book b1 = new Book(101, "Java Programing");
        Book b2 = new Book(102, "Java Programing");
        System.out.println(b1.equals(b2));
    }
}

class Test183 {
    public static void main(String[] args) {
        LocalDate valentinesDay = LocalDate.of(2015, Month.FEBRUARY, 14);
        LocalDate next15days = valentinesDay.plusDays(15);
        LocalDate nextYear = next15days.plusYears(1); // line n1
        System.out.println(nextYear);
    }
}

class Test184 {
    static class UserException extends Exception {
    }

    static class AgeOutOfLimitException extends UserException {
    }

    static class App {
        public void doRegister(String name, int age)
                throws UserException, AgeOutOfLimitException {
            if (name.length() <= 60) {
                throw new UserException();
            } else if (age > 60) {
                throw new AgeOutOfLimitException();
            } else {
                System.out.println("User is registered.");
            }
        }

        public static void main(String[] args) throws UserException {
            App t = new App();
            t.doRegister("Mathew", 60);
        }
    }
}

class Test185 {
    public static class Foo {
        public static void main(String[] args) {
            Map<Integer, String> unsortMap = new HashMap<>();
            unsortMap.put(10, "z");
            unsortMap.put(5, "b");
            unsortMap.put(1, "d");
            unsortMap.put(7, "e");
            unsortMap.put(50, "j");
            Map<Integer, String> treeMap = new TreeMap<Integer, String>(
                    new
                            Comparator<Integer>() {
                                @Override
                                public int compare(Integer o1, Integer o2) {
                                    return
                                            o2.compareTo(o1);
                                }
                            });
            treeMap.putAll(unsortMap);
            for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                System.out.print(entry.getValue() + " ");
            }
        }
    }
}

class Test186 {
    static class Caller implements Callable<String> {
        String str;

        public Caller(String s) {
            this.str = s;
        }

        public String call() throws Exception {
            return str.concat("Caller");
        }
    }

    static class Runner implements Runnable {
        String str;

        public Runner(String s) {
            this.str = s;
        }

        public void run() {
            System.out.println(str.concat("Runner"));
        }
    }

    public static void main(String[] args) throws InterruptedException,
            ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future f1 = es.submit(new Caller("Call"));
        Future f2 = es.submit(new Runner("Run"));
        String str1 = (String) f1.get();
        String str2 = (String) f2.get(); //line n1
        System.out.println(str1 + ":" + str2);
        es.shutdown();
    }
}

class Test187 {
    static class Vehicle implements Comparable<Vehicle> {
        int vno;
        String name;

        public Vehicle(int vno, String name) {
            this.vno = vno;
            this.name = name;
        }

        public String toString() {
            return vno + ":" + name;
        }

        public int compareTo(Vehicle o) {
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        Set<Vehicle> vehicles = new TreeSet<>();
        vehicles.add(new Vehicle(10123, "Ford"));
        vehicles.add(new Vehicle(10124, "BMW"));
        System.out.println(vehicles);
    }
}

class Test188 {
    public static void main(String[] args) throws Exception {
        final FileOutputStream fileOutputStream = new FileOutputStream("resource\\course.txt");
        fileOutputStream.write("Course : : Java".getBytes());
        fileOutputStream.close();
        int i;
        char c;
        try (FileInputStream fis = new FileInputStream("course.txt");
             InputStreamReader isr = new InputStreamReader(fis);) {
//            while (!isr.close()) { //line n1
//                isr.skip(2);
//                i = isr.read ();
//                c = (char) i;
//                System.out.print(c);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Test189 {
    public static void main(String[] args) {
        ZonedDateTime depart = ZonedDateTime.of(2015, 1, 15, 1, 0, 0, 0, ZoneId.of("UTC-7"));
        ZonedDateTime arrive = ZonedDateTime.of(2015, 1, 15, 9, 0, 0, 0, ZoneId.of("UTC-5"));
        long hrs = ChronoUnit.HOURS.between(depart, arrive); //line n1
        System.out.println("Travel time is " + hrs + "hours");
    }
}
class Test190{
    public static void main(String[] args) throws Exception{
        Path file = Paths.get ("courses.txt");
        List<String> fc = Files.readAllLines(file);
        fc.stream().forEach (s -> System.out.println(s));
    }
}

class Test191 {
    public static void main(String[] args) throws Exception {
        Stream<Path> files = Files.list(Paths.get(System.getProperty("user.home")));
        files.forEach(fName -> { //line n1
            try {
                Path aPath = fName.toAbsolutePath(); //line n2
                System.out.println(fName + ":"
                        + Files.readAttributes(aPath,
                        BasicFileAttributes.class).creationTime
                        ());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ;
        });
    }
}

class Test192 {
    public static void main(String[] args) {
//        BiFunction<Integer, Double, Integer> val = (t1, t2) -> t1 + t2;
    }
}

class Test193 {
    static class Student {
        String course, name, city;

        public Student(String name, String course, String city) {
            this.course = course;
            this.name = name;
            this.city = city;
        }

        public String toString() {
            return course + ":" + name + ":" + city;
        }

        public String getCourse() {
            return course;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }
    }

    public static void main(String[] args) {
        /*List<Student> stds = Arrays.asList(
                new Student("Jessy1", "Java AME", "Chicago"),
                new Student("Jessy2", "Java XME", "Chicago"),
                new Student("ZHelen", "Java EEE", "Houston"),
                new Student("Huy", "Java XME", "Chicago"),
                new Student("Mark", "Java AME", "Chicago"));
        stds.stream()
                .collect(Collectors.groupingBy(Student::getCourse))
                .forEach((src, res) -> System.out.println(res));*/

        List<Student> stds = Arrays.asList(
                new Student("Jessy", "Java ME", "Chicago"),
                new Student("Helen", "Java EE", "Houston"),
                new Student("Mark", "Java ME", "Chicago"));
        stds.stream().collect(Collectors.groupingBy(Student::getCourse)).forEach((src, res) -> System.out.println(res));
    }
}

class Test194 {
    static class Employee {
        Optional<Address> address;

        Employee(Optional<Address> address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return address;
        }
    }

    static class Address {
        String city = "New York";

        public String getCity() {
            return city;
        }

        public String toString() {
            return city;
        }
    }

    public static void main(String[] args) {
        Address address = new Address();
        Optional<Address> addrs1 = Optional.ofNullable(address);
        Employee e1 = new Employee(addrs1);
        String eAddress = (addrs1.isPresent()) ? addrs1.get().getCity() : "City Not available";
        System.out.println(eAddress);

    }
}

class Test196 {
    public static void main(String[] args) {
        List<String> empDetails = Arrays.asList("100, Robin, HR",
                "200, Mary, AdminServices", "101, Peter, HR");
        empDetails.stream()
                .filter(s -> s.contains("r"))
                .sorted()
                .forEach(System.out::println); //line n1
    }
}

class Test197 {
    public static void main(String[] args) throws Exception {
        try (Connection con = DriverManager.getConnection(TestOCP.url, TestOCP.user, TestOCP.pass);) {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select emp_id, emp_name from employee");
            {
                rs.absolute(-2);    // Linel
                rs.moveToInsertRow();
                rs.updateInt(1, 105);
                rs.updateString(2, "Michael");
                rs.insertRow();
                rs.moveToCurrentRow();
                System.out.println("Employee Id: " + rs.getInt(1) + ", Employee Name: " + rs.getString(2));
            }

        }
    }
}

class Test198 {
    public static void main(String[] args) {
        List<String> codes = Arrays.asList("B", "C", "A", "D");
        codes.parallelStream().forEach(s -> System.out.println(s));
        System.out.println();
        codes.parallelStream().forEachOrdered(s -> System.out.println(s));
    }
}

class Test199 {
    public static void main(String[] args) throws Exception {
        try (Connection con = DriverManager.getConnection(TestOCP.url, TestOCP.user, TestOCP.pass);) {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select emp_id, emp_name from employee");
            /*ResultSetMetaData rsmd = rs.getMetaData();
            int totRows = rsmd.getRowCount();*/
            int t = 0;
            while (rs.next())
                t++;
            /*rs.last();
            int totRows = rs.getRowCount();*/

            /*rs.last();
            int totRows = rs.getRowId(1);*/
        }
    }
}

class Test200 {
    static class Bird {
        String name;

        Bird(String name) {
            this.name = name;
        }

        public void eat() {
            System.out.println(name + "is eating");
        }
    }

    interface BirdInt {
        Bird getBird(String name);
    }

    public static class App {
        public static void main(String[] args) {
            // insert code here
            BirdInt b1 = name -> new Bird(name);
            Bird b = b1.getBird("Peacock");
            b.eat();
//            BirdInt b = new Bird ("Peacock");

//            Bird b = Bird::new("Peacock");
        }
    }

}

class Test201 {
    public static class Emp {
        public void calcLeave() {
            System.out.println("12");
        }
    }

    public static void main(String[] args) {
        Emp e = new Emp() {
            @Override
            public void calcLeave() {
                System.out.println("13");
            }
        };
        e.calcLeave();
    }
}

class Test202 {
    public static void main(String[] args) throws Exception {
        /*Connection conn = DriverManager.getConnection(TestOCP.url, TestOCP.user, TestOCP.pass);
        String query = "SELECT emp_id FROM Employee";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs = stmt.executeQuery("SELECT emp_id FROM Customer");
            while (rs.next()) {
                //process the results
                System.out.println("Employee ID: " + rs.getInt("emp_id"));
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }*/
        Connection conn = DriverManager.getConnection(TestOCP.url2, TestOCP.user2, TestOCP.pass2);
        String query = "SELECT id FROM AUTODT.EMPLOYEE";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs = stmt.executeQuery("SELECT id FROM AUTODT.CUSTOMER");
            while (rs.next()) {
                //process the results
                System.out.println("Employee ID: " + rs.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }

    }
}

class Test203 {
    public static void main(String[] args) {
        List<String> str = Arrays.asList("pen", "is", "not", "a", "pencil");
        Predicate<String> test = s -> {
            int i = 0;
            boolean result = s.contains("pen");
            System.out.print((i++) + ":");
            return result;
        };
        str.stream()
                .filter(test)
                .findFirst()
                .ifPresent(System.out::print);

    }
}

class Test204 {
    static class UserException extends Exception {
    }

    static class AgeOutOfLimitException extends UserException {
    }


    static class App {
        public void doRegister(String name, int age)
                throws UserException, AgeOutOfLimitException {
            if (name.length() < 5) {
                throw new UserException();
            } else if (age > 60) {
                throw new AgeOutOfLimitException();
            } else {
                System.out.println("User is registered.");
            }
        }

        public static void main(String[] args) throws UserException {
            App t = new App();
            t.doRegister("Mathew", 60);
        }
    }

}




