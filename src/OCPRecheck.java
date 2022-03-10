import javax.smartcardio.Card;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class OCPRecheck {
    public static final String URL = "jdbc:oracle:thin:@//localhost:1521/ORCLCDB.localdomain";
    public static final String USER = "thientv7";
    public static final String PASS = "1";

}

class Q01 {
    public static void main(String[] args) {
        Vehicle v = new Vehicle(100);
        v.increSpeed(60);
    }

    static class Vehicle {
        int distance;

        Vehicle(int x) {
            this.distance = x;
        }

        public void increSpeed(int time) {
            int timeTravel = time; // n1
            class Car {
                int value = 0;

                public void speed() {
                    value = distance / timeTravel; // n2
                    System.out.println("Vlocity with new speed: " + value + " kmph");
                }
            }

//            new Car().speed(); // n3 rs: 1kmph
//            speed(); // n3 // complicate fail n3
        }

    }
}

class Q2 {
    public static void main(String[] args) {
        IntStream stream = IntStream.of(1,2,3);
//        IntFunction<Integer> inFu = x -> y -> x * y; // line n1
        IntFunction<IntUnaryOperator> inFu = x -> y -> x * y; // line n1
        IntStream newStream = stream.map(inFu.apply(10)); // line n2
        newStream.forEach(System.out::print);

    }
}

class Q3 {
    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(1,2,3);
        values.stream()
                .map(n -> n*2) // line n1
                .peek(System.out::print) // line n2
                .count();
    }

}

class Q4 {
    public static void main(String[] args) {
        Map<Integer, String> unsortMap = new HashMap<>();
        unsortMap.put(10,"z");
        unsortMap.put(5,"b");
        unsortMap.put(1,"d");
        unsortMap.put(7,"e");
        unsortMap.put(50,"j");

        Map<Integer, String> treeMap = new TreeMap<>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
//                        return o2.compareTo(o1);
                        return o1.compareTo(o2);
                    }
                }
        );

        treeMap.putAll(unsortMap);

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()){
            System.out.println(entry.getValue() + " ");
        }
    }
}

class Q6 { // assertError
    public static void main(String[] args) {
        int a = 10;
        int b = -1;
        assert (b>=1) : "Invalid Denominator";
//        assert (b>=1); // message chi co tac dung show them msg

        int c = a/b;
        System.out.println(c);
    }

}

class Q7 {
    static class Bird {
        public void fly() {
            System.out.println("Can fly");
        }
    }
    static class Penguin extends Bird {
        @Override
        public void fly() {
            System.out.println("Can not fly");
        }
    }

    static class Birdie {
        public static void main(String[] args) {
            fly(() -> new Bird());
            fly(Penguin::new);
        }
        /** n1 */
        static void fly(Supplier<Bird> bird){
            bird.get().fly();
        }

        /*static void fly(Supplier<? extends Bird> bird){
            bird::fly();
        }*/
    }
}

class Q8 {
    abstract class Shape {
        public Shape() {
            System.out.println("Shape");
        }
        protected void area (){
            System.out.println("Shape");
        }
    }

    class Square extends Shape {
        int side;
        Square(int side) {
            /** insert code here */
            this.side = side;
        }

        public void area(){
            System.out.println("Square");
        }
    }

    class Rectangle extends Square {
        int len, br;

        Rectangle(int x, int y) {
            /** insert code here */
            super(x); // add line 17
            len = x;
            br = y;
        }
        public void area(){ // them public
            System.out.println("Rectangle");
        }
    }
}

class Q9 {
    class Sum extends RecursiveAction { // n1

        static final int THRESHOLD_SIZE = 3;
        int stIndex, lstIndex;
        int[] data;

        public Sum(int[] data,int start, int end) {
            this.stIndex = start;
            this.lstIndex = end;
            this.data = data;
        }

        @Override
        protected void compute() {
            int sum = 0;
            if ((lstIndex - stIndex) <= THRESHOLD_SIZE) {
                for (int i = stIndex; i < lstIndex; i++) {
                    sum += data[i];
                }
                System.out.println(sum);
            } else {
                new Sum(data, stIndex + THRESHOLD_SIZE, lstIndex).fork();
                new Sum (data, stIndex,Math.min (lstIndex, stIndex + THRESHOLD_SIZE)
                ).compute ();
            }
        }

    }

    public static void main(String[] args) {
        ForkJoinPool fjPool = new ForkJoinPool();
        int data[] = {1,2,3,4,5,6,7,8,9,10};
        fjPool.invoke(new Test09.Sum(data, 0, data.length));
    }
}

class Q10 { // chon in ra ON OFF new co abstract, ne khong thi Operator loi
    // Operator.java
   /* public abstract class Operator {
        protected abstract void turnON(); // co abstract
        protected abstract void turnOFF(); // co abstract
    }

    // EngoneOperator.java
    public class EngineOperator extends Operator {
        public final void turnON() {
            System.out.println("ON");
        }
        public final void turnOFF(){
            System.out.println("OFF");
        }
    }

    // Engine.java
    public class Engine {
        Operator m = new EngineOperator();
        public void operate(){
            m.turnON();
            m.turnOFF();
        }
    }*/

    public static void main(String[] args) {
        Engine carEngine = new Engine();
        carEngine.operate();
    }
}

class Q11 {
    public static void main(String[] args) {
        Stream<List<String>> iStr = Stream.of(
                Arrays.asList("1","John"),
                Arrays.asList("2",null)
        );

        Stream<String> nInStr = iStr.flatMap((x) -> x.stream()); // in ra 1John 2null
//        Stream<String> nInStr = iStr.flatMapToInt((x) -> x.stream()); // compile fail
        nInStr.forEach(System.out::print);
    }
}

class Q12 {
    public static void main(String[] args) throws IOException {
        Path file = Paths.get("/home/vtn-thientv7-u/Documents/TestOCP/src/courses.txt");
        // line n1
        Stream<String> fc = Files.lines(file);
//        Stream<String> fc = Files.readAllLines(file); // tra ve List<String>
//        List<String> fc = Files.list(file); // tra ve stream
//        fc.stream().forEach(s -> System.out.println(s));
        fc.forEach(s-> System.out.println(s));

    }
}

class Q13 { // delete all .class files in the project directory and its subdirectory
    public void recDelete(String dirName) throws IOException {
        File[] listOfFiles = new File(dirName).listFiles();
        if(listOfFiles !=null && listOfFiles.length>0){
            for(File aFile: listOfFiles){
                if(aFile.isDirectory()){
                    recDelete(aFile.getAbsolutePath());
                } else {
                    if (aFile.getName().endsWith(".class")){
                        aFile.delete();
                    }
                }
            }
        }
    }
}

class Q14 {
    static void doStuff() throws ArithmeticException, NumberFormatException, Exception {
        if (Math.random() >-1) throw new Exception("Try Again");
    }

    public static void main(String[] args) {
        try {
            doStuff();
        } catch (ArithmeticException|NumberFormatException e){ // sub class Exception
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class Q15 {
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
                new Country("Germany", Country.Continent.EUROPE)
        );

        Map<Country.Continent, List<String>> regionNames = couList.stream()
                .collect(Collectors.groupingBy(Country::getRegion,
                        Collectors.mapping(Country::getName, Collectors.toList())));
        System.out.println(regionNames);
    }
}

class Q16 {
    public static void main(String[] args) {
        Map<Integer, String> books = new TreeMap<>();
        books.put (1007, "A");
        books.put (1002, "C");
        books.put (1001, "B");
        books.put (1003, "B");
        System.out.println (books);
    }
}

class Q17 {

}

class Q18 {
    // co kiem tra in hoa, in thuong trong file properties
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis;
        fis = new FileInputStream("/home/vtn-thientv7-u/Documents/TestOCP/src/Message.properties");
        prop.load(fis);
        System.out.println(prop.getProperty("welcome1"));
        System.out.println(prop.getProperty("welcome2", "Test"));
        System.out.println(prop.getProperty("welcome3"));

        }
}

class Q20 {
    public static void main(String[] args) {
        Path p1 = Paths.get("/Pics/MyPic.jpeg");
        System.out.println(p1.getNameCount() + ":"+p1.getName(1)+":"+p1.getFileName());
    }
}

class Q21 {
    static class MyThread implements Runnable {
        private static AtomicInteger count = new AtomicInteger(0);

        @Override
        public void run() {
            int x = count.incrementAndGet();
            System.out.println(x+" ");
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        Thread thread3 = new Thread(new MyThread());

        Thread[] ta = {thread1, thread2, thread3};

        for (int i = 0; i < 3; i++) {
            ta[i].start();
        }
    }
}

class Q22 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter GDP: ");
        int GDP = Integer.parseInt(br.readLine());
//        int GDP = br.read();
//        int GDP = br.nextInt();
//        int GDP = Integer.parseInt(br.next());


        System.out.println("GDP: "+GDP);
    }
}

class Q23 { // no such file exception
    public static void main(String[] args) throws Exception {
        Path source = Paths.get("/data/december/log.txt");
        Path des = Paths.get("/data");
        Files.copy(source, des);
    }
}

class Q25 { // answer 3
    interface CourseFilter extends Predicate<String> {
        public default boolean test (String str){
            return str.equals("Java");
        }
    }

    public static void main(String[] args) {
        List<String> strs = Arrays.asList("Java", "Java EE", "Java ME");
        Predicate<String> cf1 = s -> s.length() >3;
        Predicate cf2 = new CourseFilter() {  // n1
            @Override
            public boolean test(String str) {
                return str.contains("Java");
            }
        };

        long c = strs.stream()
                .filter(cf1)
                .filter(cf2) // n2
                .count();

        System.out.println(c);
    }
}

class Q26 {
    public static class Emp {
        String fName;
        String lName;

        public Emp(String fn, String ln) {
            this.fName = fn;
            this.lName = ln;
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
                new Emp("John","Smith"),
                new Emp("Peter","Sam"),
                new Emp("Thomas","Wale")
        );

        emp.stream()
                // line1
                .sorted(Comparator.comparing(Emp::getfName).reversed().thenComparing(Emp::getlName))
            .collect(Collectors.toList());
    }
}

class Q27 {
    public enum USCurrency {
        PENNY (1),
        NICKLE(5),
        DIME (10),
        QUARTER(25);
        private int value;
//        public USCurrency(int value) {
        private USCurrency(int value) {
            this.value = value;
        }
        public int getValue() {return value;}
    }

    public static void main(String[] args) {
        USCurrency usCoin = USCurrency.DIME;
        System.out.println(usCoin.value);
    }
}

class Q29 {
    static Connection newConnection = null;
    public static Connection getDbConnection() throws SQLException {
        Connection con = DriverManager.getConnection(OCPRecheck.URL, OCPRecheck.USER, OCPRecheck.PASS);
        newConnection = con;
        return newConnection;
    }

    public static void main(String[] args) throws SQLException {
        getDbConnection();
        Statement st = newConnection.createStatement();
        st.executeUpdate("INSERT INTO Student VALUES (102,'Kelvin')");
    }
}

class Q30 {
     static class Employee {
        Optional<Address> address;

        public Employee(Optional<Address> address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return address;
        }
    }

    class Address {
        String city = "New York";

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "city='" + city + '\'' +
                    '}';
        }
    }
    // chua khoi tao address nen gia tri bi null
    // ham isPresent check xem co gia tri ben trong khong
    //Optional.orEsle in ra else neu khong co gia tri
    public static void main(String[] args) {
        Address address = null;
        Optional<Address> addrs1 = Optional.ofNullable(address);


        Employee e1 = new Employee(addrs1);

        String eAddress = (addrs1.isPresent()) ? addrs1.get().getCity() :"City Not avaiable";

        System.out.println(eAddress);

        Optional<String> optional1 = Optional.of("Thientv7");
        Optional<String> optional2 = Optional.of("Thientv7");
        System.out.println(optional1.orElse("Hello"));
        System.out.println(optional2);
        System.out.println(optional1);

    }
}

class Q31 { // File.walk list all file and directories under folder param
//    Stream<Path> file = Files.walk(Paths.get(System.getProperty("user.homee")));
}

class Q32 { // class cast exception
    static class Vehicle {
        int vno;
        String name;

        public Vehicle(int vno, String name) {
            this.vno = vno;
            this.name = name;
        }



        @Override
        public String toString() {
            return "Vehicle{" +
                    "vno=" + vno +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        Set<Vehicle> vehicles = new TreeSet<>();
        vehicles.add(new Vehicle(10123, "Ford"));
        vehicles.add(new Vehicle(10124, "BMW"));
        System.out.println(vehicles);
    }
}

class Q33 {
    public static void main(String[] args) {
        int i;
        char c;
        try {
            FileInputStream fis = new FileInputStream("/home/vtn-thientv7-u/Documents/TestOCP/src/courses33.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            while (isr.ready()){
                isr.skip(2); // bo 2 phan tu khong doc
                i = isr.read();
                c = (char) i;
                System.out.println(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Q34 {
    static class Test<T> {
        private T t;

        public T get() {
            return t;
        }

        public void set(T t) {
            this.t = t;
        }
    }

    public static void main(String[] args) {
        Test<String>  type = new Test<>();
        Test<Integer> type1 = new Test<>();

        type.set("Java");
        type1.set(100);

        System.out.println(type.get());
        System.out.println(type1.get());

    }
}

class Q36{

}

class Q63{
    class FuelNotAvailException extends Exception {
    }

    class Vehicle {
                void ride() throws FuelNotAvailException { //line n1
//        protected void ride() throws Exception { //line n1
            System.out.println("Happy Journey!");
        }
    }

    class SolarVehicle extends Vehicle {
        public void ride() throws FuelNotAvailException { //line n2
            super.ride();
        }
    }

    public static void main(String[] args) throws FuelNotAvailException, Exception{
//        Vehicle v = new SolarVehicle();
//        v.ride();
    }
}

class Q66 {
    public static void main(String[] args) {
        LocalDate valentinesDay = LocalDate.of(2015, Month.FEBRUARY, 14);
        LocalDate nextYear = valentinesDay.plusYears(1);
        nextYear.plusDays(15); // line n1
        System.out.println(nextYear);
    }
}

class Q67{ // interger + interger
    public static void main(String[] args) {
        BiFunction<Integer, Double, Double> val = (t1,t2) -> t1+t2 ; // line n1
//        BiFunction<Integer, Double, Integer> val = (t1,t2) -> t1+t2 ; // line n1
        System.out.println(val.apply(10, 10.5));
    }

}

class Q68{
    public static void main(String[] args) {
        UnaryOperator<Double> uol = s -> s*2; // line n1
//        UnaryOperator<Integer> uol = s -> s*2; // line n1
        List<Double> loanValues = Arrays.asList(1000.0, 2000.0);

        loanValues.stream()
                .filter(lv -> lv >= 1500)
                .map(lv -> uol.apply(lv))  // line n2
                .forEach(s -> System.out.println(s + " "));
    }
}

class Q73 {
    static class CallerThread implements Callable<String> {
        String str;

        public CallerThread(String str) {
            this.str = str;
        }

        @Override
        public String call() throws Exception {
            return str.concat("Call");
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(4); // line 4
        Future f1 = es.submit (new CallerThread("Call"));
        String str = f1.get().toString();
        System.out.println(str);
    }
}


class Q74 {

}

class Q77{
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("/home/vtn-thientv7-u/Documents/TestOCP/src/green.txt");
        Path target = Paths.get("/home/vtn-thientv7-u/Documents/TestOCP/src/yellow.txt");
        // ham move la move luon file di khong copy
        Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        // nen khi xoa file source se khong con ton tai nua
        Files.delete(source);
    }
}

class Q78{
    interface Doable{
        public void doSomething(String s);
    }

    public abstract class Job implements Doable {
        public void doSomething(Integer i) {
        }
    }

    public class Do implements Doable {
        @Override
        public void doSomething(String s) {

        }

        public void doSomething(Integer i){

        }

        public void doThat(String s){

        }
    }
}

class Q79{
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(10,20);
        List<Integer> list2 = Arrays.asList(15,30);
        // line 1
        Stream.of(list1, list2)
                .flatMap(list -> list.stream())
                .forEach(s -> System.out.println(s+ " "));

    }
}

class Q81{

    public static void main(String[] args) {
        Book b1 = new Book();
//        b1.read("Java Programing");
        Book b2 = new EBook();
//        b2.read("https://");
    }
}

class Q82{
    public static void main(String[] args) {
        ZonedDateTime depart = ZonedDateTime.of(2015, 1, 15, 3, 0, 0, 0, ZoneId.of("UTC-7"));
        ZonedDateTime arrvive = ZonedDateTime.of(2015, 1, 15, 9, 0, 0, 0, ZoneId.of("UTC-5"));

        long hrs = ChronoUnit.HOURS.between(depart, arrvive); // line n1
        System.out.println("Travel time is "+ hrs+" hours");
    }
}

class Q83{
    public static void main(String[] args) {
        Path path1 = Paths.get("/app/./sys/");
        Path res1 = path1.resolve("log"); // khong phai folder thi cong luon vao path
        Path path2 = Paths.get("/server/exe");
        Path res2 = path2.resolve("/readme/"); // la folder thi thay the luon path

        System.out.println(res1);
        System.out.println(res2);
    }
}

class Q84 {
    // allmatch : TRUE tat ca phan tu trong stream thoa man dk vaf nguoc lai FALSE
    // anymath : TRUE bat ky phan tu trong stream thoa man dk va stream rong thi la FALSE hoac khong co bk phan tu nao
    // nonematch: TRUE neu tat ca khong thoa man
    public static void main(String[] args) {
        List<String> colors = Arrays.asList("red","green","yellow");
        Predicate<String> test = n -> {
            System.out.println("Searching...");
            return n.contains("red");
        };

        colors.stream()
                .filter(c -> c.length() > 3)
                .allMatch(test);
    }
}

class Q85{

}

class Q86{

    // normalize:
    // relative:

    /*
    /software/.././sys/readme.txt
    /sys/readme.txt
    ../../software/.././sys/readme.txt
    */
    // 5 2 7
    public static void main(String[] args) {
//        Path path1 = Paths.get("/software/././sys/readme.txt");
//        Path path1 = Paths.get("/software/.././sys/readme.txt");
        Path path1 = Paths.get("/software/.././sys/readme.txt");
        Path path2 = path1.normalize();
        Path path3 = path2.relativize(path1);

        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);

        System.out.println(path1.getNameCount());
        System.out.println(" : "+path2.getNameCount());
        System.out.println(" : "+path3.getNameCount());
    }
}

class Q87{ // chu y
    static class Product {
        String name;
        int qty;

        public Product(String name, int qty) {
            this.name = name;
            this.qty = qty;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    '}';
        }

        static class ProductFilter{
            public static boolean isAvaiable(Product p) { // line n1
                return p.qty >= 10;
            }
        }
    }

    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product("MotherBoard", 5),
                new Product("Speaker", 20)
        );

        products.stream()
                .filter(Product.ProductFilter::isAvaiable)  // line 2
                .forEach(p -> System.out.println(p));
    }
}

class Q88 {
    public static void main(String[] args) {
//        Locale currentLocale = new Locale.Builder().setRegion("DE").setLanguage("de").build();
        // neu region ,language khong co thi lay mac dinh gia tri english
        Locale currentLocale = new Locale.Builder().setRegion("DE").setLanguage("de").build();
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

        // neu set chi tiet ten file thi van lay dc
//        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle_fr_FR", currentLocale);

        Enumeration<String> names = messages.getKeys();

        while (names.hasMoreElements()){
            String key = names.nextElement();
            String name = messages.getString(key);

            System.out.println(key +" = "+name);

        }
    }
}

class Q89{
    public static void doStuff(String s){
        try {
            if (s == null){
                throw new NullPointerException();
            }
        } finally {
            System.out.println("-finally-");
        }

//        System.out.println("-doStuff-");
    }

    public static void main(String[] args) {
        try {
            doStuff(null);
        } catch (NullPointerException npe){
            System.out.println("-catch-");
        }

        System.out.println("-doStuff-");
    }
}

class Q90 {
    public class Foo {
        public void methodB(String s){
            System.out.println("Foo "+s);
        }
    }

    public class Bar extends Foo{
        public void methodB(String s){
            System.out.println("Bar "+s);
        }
    }
    public class Baz extends Bar{
        public void methodB(String s){
            System.out.println("Baz "+s);
        }
    }

    public class Daze extends Baz {
        private Bar bb = new Bar();

        public void methodB(String s) {
            bb.methodB(s);
            super.methodB(s);
        }
    }

    public static void main(String[] args) {
//        Baz d = new Daze();
//        d.methodB("Hello");
    }
}

class Q95{
    // neu khong implemen Autocloseable thi loi o n2
    // neu implement thi loi o n1
    static class DataConverter implements AutoCloseable {
//    class DataConverter {
        public void copyFlatFilesToTables(){

        }
        public void close() throws Exception {
            throw  new RuntimeException(); // line n1 compile fail neu co AutoCloseable
        }
    }

    public static void main(String[] args) throws Exception {
        try (DataConverter dc = new DataConverter()) { // line n2
            dc.copyFlatFilesToTables();
        }
    }
}

class Q97 {
    public static void main(String[] args) {
        ZoneId zone = ZoneId.of("America/New_York");
        ZonedDateTime dt = ZonedDateTime.of(LocalDate.of(2015,3,8), LocalTime.of(1,0), zone);

        ZonedDateTime dt2 = dt.plusHours(2);
        System.out.print(DateTimeFormatter.ofPattern("H:mm - ").format(dt2));
        System.out.println(" diff: "+ChronoUnit.HOURS.between(dt, dt2));
    }

}

class Q98 {

    enum Course {
        JAVA(100),
        J2ME(150);
        private int cost;
        Course(int c) {
            this.cost = c;
        }

        public int getCost() {
            return cost;
        }
    }

    public static void main(String[] args) {
        for (Course a: Course.values()) {
            System.out.println(a + " Feee "+a.getCost());
        }
    }
}

class Q99{
    static class Resource implements AutoCloseable {

        @Override
        public void close() throws Exception {
            System.out.println("Close-");
        }

        public void open(){
            System.out.println("Open-");
        }
    }

    public static void main(String[] args) {
        Resource res1 = new Resource();

        try {
            res1.open();
            res1.close();
        } catch (Exception e) {
            System.out.println("Exception - 1");
        }

        res1.open();
    }
}

class Q100 {
    public static void main(String[] args) {
        List<String>  cs = Arrays.asList("Java", "Java EE", "Java ME");
        // line 1
        boolean b = cs.stream().allMatch(w -> w.equals("Java"));
        System.out.println(b);
    }
}

class Q101{
    public static void main(String[] args) {
        final String str1 = "Java";
        StringBuffer strBuf = new StringBuffer("Course");
        UnaryOperator<String> u = (str2) -> str1.concat(str2); // line n1
        UnaryOperator<String> c = (str3) -> str3.toLowerCase();
        System.out.println(u.apply(c.apply(str1))); // line n2
//        System.out.println(u.apply(c.apply(strBuf))); // line n2
    }
}

class Q102 {
    public static void main(String[] args) {
        double fuelLevel = 0;

//        assert fuelLevel < 0: System.exit(0); // void not allow here
//        assert fuelLevel < 0: System.exit(0); // void not allow here

          assert fuelLevel > 0 : "Imposible fuel";
//          assert (fuelLevel > 0) : System.out.println("Imposible fuel"); // void not allow here
    }
}

class Q103{
    public static void main(String[] args) {
        List<Integer> li = Arrays.asList(10,20,30);
        Function<Integer, Integer> fn = f1 -> f1+f1;
        Consumer<Integer> conVal = s -> System.out.println("Val: "+s+" ");
        li.stream().map(fn).forEach(conVal);
    }
}

class Q104 { // note
    public static Optional<String> getCountry(String loc){
        Optional<String> couName = Optional.empty();
        if ("Paris".equals(loc)){
            couName = Optional.of("France");
        } else if ("Mumbai".equals(loc)){
            couName = Optional.of("India");
        }

        return couName;
    }

    public static void main(String[] args) {
        Optional<String> city1 = getCountry("Paris");
        Optional<String> city2 = getCountry("Las Vegas");
        Optional<String> city3 = getCountry("Mumbai");
        System.out.println(city1.orElse("Not Found"));

        if (city2.isPresent()){
            city2.ifPresent(x -> System.out.println(x));
        } else if (city3.isPresent()){
            // orElse neu khon co gia tri se lay trong else
            System.out.println(city3.orElse("Not Found"));
            // neu chi in city3 thi ra Optional[India]
            System.out.println(city3);
        }
        else {
            System.out.println(city2.orElse("Not Found"));
        }
    }
}

class Q105{
    public static void main(String[] args) {
//        Path ip = new Paths("First.txt");
//        Path ip = Paths.toPath("/First.txt");
//        Path ip = new Path("First.txt");
        Path ip = Paths.get("/","First.txt");

        System.out.println(ip);
    }
}

class Q106{
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(OCPRecheck.URL, OCPRecheck.USER, OCPRecheck.PASS);
            String query = "SELECT * FROM Employee WHERE ID = 110";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Employee: "+rs.getInt("ID"));
        } catch (Exception e){

        }
//        catch (SQLException e) {
//            System.out.println("Error");
//        }
    }
}

class Q107 {

    public static class TestConsole {
        Console console = System.console();
        char[] pass = console.readPassword("Enter pass: "); // line n1

        // dung readLine thi tra ve String
//        String pass = console.readLine("Enter pass: "); // line n1
        String password = new String(pass);
//        System.out.println(password);
    }

    public static void main(String[] args) {
       new Q107.TestConsole();
    }
}

class Q108{
    // thay language, country khac thi lay theo gia tri cua country do, vi du vi,VN la 15Đ
    public static void main(String[] args) {
        double d = 15;
        Locale l =new Locale("en","US");
//        Locale l =new Locale("fr","FR");
//        Locale l =new Locale("fr","FR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(l);
//        NumberFormat formatter = NumberFormat.getNumberInstance();
        System.out.println(formatter.format(d));
    }
}

class Q111{
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

class Q112{
    public static void main(String[] args) {
//        LocalTime now = LocalTime.now();
//        LocalTime now = LocalTime.of(8,30); // -1 HOURS
//        LocalTime now = LocalTime.of(8,0); // 0 HOURS
//        LocalTime now = LocalTime.of(6,30); // 60 minutes
        LocalTime now = LocalTime.of(6,30); // 60 minutes
        long timeToBreakfast = 0;
        LocalTime office_start = LocalTime.of(7, 30);
//        if (office_start.isAfter(now)){
        if (office_start.isAfter(now)){ // compare with now > 0
            timeToBreakfast = now.until(office_start, MINUTES);

        } else {
            timeToBreakfast = now.until(office_start, HOURS);
        }
        System.out.println(timeToBreakfast);
    }
}
class Q113 {
    static class R implements Runnable {

        @Override
        public void run() {
            System.out.println("Run...");
        }
    }

    static class C implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "Call..";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(new R()); // line 1
        Future<String> f1 = es.submit(new C()); // line 2
        System.out.println(f1.get());
        es.shutdown();
    }
}

class Q115{

    // Susan allen David
    // pop, remove deu remove phan tu dau tien
    public static void main(String[] args) {
        Deque<String> queue = new ArrayDeque<>();
        queue.add("Susan");
        queue.add("Allen");
        queue.add("David");

        // pop
//        String popItem = queue.pop();
//        System.out.println("PopItem " +popItem);

        // remove
        String popItem = queue.remove();
        System.out.println("RemoveItem " +popItem);

        System.out.println(queue.pop());
        System.out.println(queue.remove());
        System.out.println(queue);
    }
}

class Q117{
    public static void main(String[] args) {
        List<String> valList = Arrays.asList("","George","","John","Jim");
        // ne co null thi exception n2
//        List<String> valList = Arrays.asList("","George",null,"John","Jim");
        Long newVal = valList.stream() // line n1
//                        .filter(x -> !x.isEmpty())
                        .filter(x -> x.isEmpty())
                        .count(); // line n2
        System.out.println(newVal);
    }
}

class Q118{
    public static void main(String[] args) throws InterruptedException {
        // Login time ;2015-01—12T21:58:817Z
        Instant loginTime = Instant.now();
        Thread.sleep(1000);
//        Thread.sleep(36000);
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

class Q119{
    public static void main(String[] args) {
        List<String> words = Arrays.asList("win","try","best","luck","do");

        Predicate<String> test1 = w -> {
            System.out.println("Checking...");
            return  w.equals("do");   // line n1
        };

        Predicate<String> test2 =  w -> w.length() > 3; // line n2
//        Predicate test2 = (String w) -> w.length() > 3; // line n2

        words.stream()
                .filter(test2)
                .filter(test1)
                .count();
    }

}

class Q125{

    //
    public static void main(String[] args) {
        List<Integer> prices = Arrays.asList(3,4,5);
        prices.stream()
                .filter(e -> e > 4)
                .peek(e -> System.out.println("Prices "+e))
                .map(n -> n-1)
                .forEach(n -> System.out.println("New Prices "+n)); // Prices 5 New Prices 4

//                .peek(n -> System.out.println("new Prices ")+n);
    }
}

class Q127{
    // error ca 2
    /*static class ProductCode<T extends S, S> {
        T c1;
        S c2;
    }*/

    // khong error
    static class ProductCode<T, S> {
        T c1;
        S c2;
    }

    public static void main(String[] args) {
        ProductCode<Number, Integer> c1 = new ProductCode<Number, Integer>();
        ProductCode<Number, String> c2 = new ProductCode<Number, String>();
    }
}

class Q128{
    public static void main(String[] args) {
        List<String> nums = Arrays.asList("EE","SE");
        String ans = nums
//                .parallelStream() // chia stream thanh cac substream  sau no cong voi nhau : Java EE Java SE
                .stream() // neu la stream thi se la lan luot thao tao Java EESE
                .reduce("Java ", (a,b) -> a.concat(b));
        System.out.println(ans);
    }
}

class Q130{
    public static class Product{
        String name;
        Integer price;

        public Product(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public void printVal(){
            System.out.println(name + " Prices: "+price+" ");
        }
    }

    public static void main(String[] args) {
        List<Product> li = Arrays.asList(
                new Product("TV", 1000),
                new Product("Refrigertor", 2000));

        Consumer<Product> raise = e -> e.setPrice(e.getPrice() + 100);
        li.forEach(raise);
        li.stream().forEach(Product::printVal);

    }
}

class Q132{
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

class Q133{
    public static void main(String[] args) throws IOException {


        // A
        Stream<Path> stream = Files.list(Paths.get("/home/vtn-thientv7-u/Documents/TestOCP/src/company"));
        stream.forEach(s -> System.out.println(s));
    }
}

class Q134{
    static class Person {
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

    public static void main(String[] args) {
        List<Person> sts = Arrays.asList(
                new Person("Jack", 30),
                new Person("Mike Hill", 21),
                new Person("Thomas Hill", 24));

        Stream<Person> resList = sts.stream().filter(s -> s.getAge() >= 25); // line n1
        long count = resList.filter(s -> s.getName().contains("Hill")).count();

        System.out.println(count);
    }
}

class Q135 {
    class Computer{
        private Card sCard = new SoundCard();
        private abstract class Card{}
        private class SoundCard extends Computer.Card {}
    }
}

class Q136{
    public static void main(String[] args) {
        Deque<Integer> nums = new ArrayDeque<>();
        nums.add(1000);
        nums.add(3000);
        nums.push(4000);
        nums.push(2000);

        System.out.println(nums);


        Integer i1 = nums.remove();
        Integer i2 = nums.pop();


        System.out.println(i1 + " : "+i2);

    }
}

class Q137{ // note
    public static void main(String[] args) {
        //1234567890
        try (FileInputStream fis = new FileInputStream("/home/vtn-thientv7-u/Documents/TestOCP/src/version.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);) {
            if (br.markSupported()) {
                System.out.print((char) br.read());
                br.mark(2); // pos 2
                System.out.print((char) br.read());
                System.out.print((char) br.read());
                br.reset();
                System.out.print((char) br.read());

                // 1232
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Q138{
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

class Q139{
    // khong co language hay region thi lay gia tri trong file mac dinh
    // lay sai key se bi loi
    // phan biet hoa thuong
    // getobject voi key dung van duoc
    public static void main(String[] args) {
//        Locale currentLocale = new Locale("de","DE");
        Locale currentLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        // line 1
        // ten file phai dung trong khai bao
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundleTest", currentLocale);
//        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundleTest_vi_VN", currentLocale);
//        System.out.println(messages.getObject("inquiry"));
        System.out.println(messages.getString("inquiry"));
    }

}

class Q140 {
    public static void main(String[] args) {
        List<String> qwords = Arrays.asList("why", "what", "when");
        BinaryOperator<String> operator = (s1, s2) -> s1.concat(s2); // line n1
        String sen = qwords.stream()
//                .sorted() // them o day thi se sort truoc khi reduce
                .reduce("Word: ", operator);
//                .sorted();// them sort o day la loi
        System.out.println(sen);
    }
}

class Q141{
    interface Interface1 {
        public default void sayHi(){
            System.out.println("Hi Interface-1");
        }
    }

    interface Interface2{
        public default void sayHi(){
            System.out.println("Hi Interface -2");
        }
    }

}

class MyClass implements Q141.Interface1, Q141.Interface2 {
    public static void main(String[] args) {
        Q141.Interface1 obj = new MyClass();
        obj.sayHi();
    }

    @Override
    public void sayHi() {
        System.out.println("Hi Myclass");
    }
}

class Q142{
    static class Block {
        String color;
        int size;

        public Block(int size, String color) {
            this.size = size;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(10, "Green"));
        blocks.add(new Block(7, "Red"));
        blocks.add(new Block(12, "Blue"));

        Collections.sort(blocks, new ColorSorter());
    }

    static class ColorSorter implements Comparator<Block> {

        @Override
        public int compare(Block o1, Block o2) {
            return o1.color.compareTo(o2.color);
        }
    }
}

class Q143{
    public static void main(String[] args) {
        Stream.of("Java","Unix", "Linux")
                .filter(s -> s.contains("n"))
//                .sorted()
                .peek(s -> System.out.println("PEEK: "+s))
                // line 1
//                .findFirst();
                .findAny();
        // cac truong hop anyMatch, allMatch, noneMatch phai add Predicate.
    }
}

class Q144{
    class Person implements Comparable<Person>{ // line n1
        String name;
        Person(String name){
            this.name = name;
        }

        @Override
        public int compareTo(Person p) {
            return this.name.compareTo(p.name);
        }

        // line 2
    }
}

class Q147{
    public static class ResourcesApp {
        public void loadResourceBundle() {
            ResourceBundle resource = ResourceBundle.getBundle("Greetings", Locale.UK);
//            System.out.println(resource.getString("hello_msg"));
//            System.out.println(resource.getString("HELLO_MSG"));
            System.out.println(resource.getObject("HELLO_MSG"));
//            System.out.println(resource.getString("hello_msg"));
//            System.out.println(resource.getString("hello_msg")); // phan biet hoa thuong
//            System.out.println(resource.getObject(1)); // compile fail
        }

        public static void main(String[] args) {
            new ResourcesApp().loadResourceBundle();
        }
    }
}

class Q148{
    public static class Test {
        List<String> list = null;

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
        // compile fail
    }
}

class Q149 { // note
    public static void main(String[] args) throws SQLException {
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

class Q150{
    public static void main(String[] args) {
        IntConsumer consumer = e -> System.out.println(e);
        Integer value = 90;

//        Function<Integer> funRef = e -> e + 10; // 2 param

//        IntFunction funRef = e -> e +10;
//        Integer result = funRef.apply(10); // phai cast


        ToIntFunction<Integer> funRef = e -> e+10;
        int result = funRef.applyAsInt(value);

        consumer.accept(result);
    }
}

class Q153{
    public static void main(String[] args) {
        IntStream str = IntStream.of(1, 2, 3, 4);
//        Stream str = Stream.of(1, 2, 3, 4);
        Double d = str.average().getAsDouble();
        System.out.println("Average = "+d);
    }

}


class Q155{
    class Video {
        public void play() throws IOException {
            System.out.println("Video play");
        }
    }

    class Game extends Video {
        public void play() throws IOException {
            super.play();
            System.out.println("Video play");

        }
    }
}

class Q176{ // note
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

//   public class Frame extends Canvas implements Drawable {
//        public void resize() {
//        }
//
//        abstract void open();
//    }

    public interface Drawable {
        public abstract void draw();
    }
}

class Q177{
    public static void main(String[] args) {
        UnaryOperator<Double> uo1 = s -> s*2; // line n1
        List<Double> loanValues = Arrays.asList(1000.0, 2000.0);
        loanValues.stream()
                .filter(lv -> lv >= 1500)
                .map(lv ->uo1.apply(lv))  // line n2
                .forEach(s -> System.out.println(s+ " "));

    }
}

class Q179{
    public static void main(String[] args) {
        List<String> nL = Arrays.asList("Jim","John","Jeff");
        Function<String, String> funVal = s -> "Hello : ".concat(s);

        nL.stream()
                .map(funVal)
//                .sorted()
                .forEach(s -> System.out.println(s));

    }
}

class Q180{

    public static void main(String[] args) {
        List<String> colors = Arrays.asList("red", "green", "yellow");
        Predicate<String> test = n -> {
            System.out.println("Searching...");
            return n.contains("red");
        };

        colors.stream()
                .filter(c -> c.length() >= 3)
                .allMatch(test);// tat ca phai true
//                .anyMatch(test);
    }

}

class Q181{
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

class Q182{
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
//            if (this.name.equals(b.name)) {
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


class Q183{
    public static void main(String[] args) {
        LocalDate valentinesDay =LocalDate.of(2015, Month.FEBRUARY, 14);
        LocalDate next15days = valentinesDay.plusDays (15);
        LocalDate nextYear = next15days.plusYears(1); // line n1
        System.out.println(nextYear);
    }
}

class Q186{
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

class Q187{
//    static class Vehicle implements Comparable<Vehicle> {
    static class Vehicle  {
        int vno;
        String name;

        public Vehicle(int vno, String name) {
            this.vno = vno;
            this.name = name;
        }

        public String toString() {
            return vno + ":" + name;
        }

//        public int compareTo(Vehicle o) {
//            return this.name.compareTo(o.name);
//        }
    }

    public static void main(String[] args) {
        Set<Vehicle> vehicles = new TreeSet<>();
        vehicles.add(new Vehicle(10123, "Ford"));
        vehicles.add(new Vehicle(10124, "BMW"));
        System.out.println(vehicles);
    }
}

class Q188{
    public static void main(String[] args) {
        /*int i;
        char c;
        try (FileInputStream fis = new FileInputStream ("/home/vtn-thientv7-u/Documents/TestOCP/src/courses33.txt");
             InputStreamReader isr = new InputStreamReader(fis);) {
            while (!isr.close()) // line n1 close la ham void nen khon them ! duoc
            {
                //line n1
                isr.skip(2);
                i = isr.read ();
                c = (char) i;
                System.out.print(c);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }
}

class Q189 {
    public static void main(String[] args) {
        ZonedDateTime depart = ZonedDateTime.of(2015, 1, 15, 1, 0, 0, 0, ZoneId.of("UTC-7"));
        ZonedDateTime arrive = ZonedDateTime.of(2015, 1, 15, 9, 0, 0, 0, ZoneId.of("UTC-5"));
        long hrs = ChronoUnit.HOURS.between(depart, arrive); //line n1
        System.out.println("Travel time is " + hrs + "hours");
    }
}

class Q190{
    public static void main(String[] args) throws IOException {
        Path file = Paths.get("/home/vtn-thientv7-u/Documents/TestOCP/src/courses.txt");

//        Stream<String> fc = Files.readAllLines(file); // require List

        //
        List<String> fc = Files.readAllLines(file);
        fc.stream().forEach(s -> System.out.println(s));

    }
}

class Q191 {
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

class Q192{
    public static void main(String[] args) {
//        BiFunction<Integer, Double, Integer> val = (t1, t2) -> t1 + t2;
        BiFunction<Integer, Double, Double> val = (t1, t2) -> t1 + t2;
    }
}

class Q193{

}

class Q194{
    static class Employee{
        Optional<Address> address;

        public Employee(Optional<Address> address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return address;
        }
    }

    static class Address {
        String city = "New York";

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "city='" + city + '\'' +
                    '}';
        }

        public String getCity() {
            return city;
        }
    }

    public static void main(String[] args) {
        Address address = new Address();

        Optional<Address> address1 = Optional.ofNullable(address);

        Employee e1 = new Employee(address1);
        String eAddress = (address1.isPresent()) ? address1.get().getCity() : "City Not available";
        System.out.println(eAddress);

    }
}

class Q196{
    public static void main(String[] args) {
        List<String> empDetails = Arrays.asList("100, Robin, HR",
                "200, Mary, AdminServices", "101, Peter, HR");
        empDetails.stream()
                .filter(s -> s.contains("r"))
                .sorted()
                .forEach(System.out::println);//line n1
//                .sorted(); //line n1 // error
    }
}

class Q197{
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(OCPRecheck.URL, OCPRecheck.USER, OCPRecheck.PASS);
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

class Q198{
    public static void main(String[] args) {
        List<String>  codes = Arrays.asList("A","B","C","D");
        codes.parallelStream().forEach(s -> System.out.println(s)); // random ket qua in ra
        System.out.println(" ");
        codes.parallelStream().forEachOrdered(s1 -> System.out.println(s1)); // in ra theo thu tu vi da duoc ordered
    }
}

class Q203{
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
//                .findAny()
                .findFirst()
                .ifPresent(System.out::print);
    }
}

class Q204{
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

class Q205{
    class Vehicle {
        String name;

        Vehicle(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        public Vehicle() {
        }
    }

    public static void main(String[] args) {

    }
}

class Q206{
    public static void main(String[] args) {
        Path path1 = Paths.get("/app/./sys/");
        Path res1 = path1.resolve("/log");

        Path path2 = Paths.get("/server/exe/");
//        Path res2 = path2.resolve("/readme/");
        Path res2 = path2.resolve("/readme");

        System.out.println(res1);
        System.out.println(res2);

    }
}