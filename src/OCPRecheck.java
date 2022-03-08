import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OCPRecheck {
    public static final String url1 = "jdbc:mariadb:thin:@10.255.216.33/multi_tenant";
    public static final String user1 = "root";
    public static final String pass1 = "root";
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
    public abstract class Operator {
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
    }

    public static void main(String[] args) {
//        Engine carEngine = new Engine();
//        carEngine.operate();
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
        Connection con = DriverManager.getConnection(OCPRecheck.url1, OCPRecheck.user1, OCPRecheck.pass1);
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

