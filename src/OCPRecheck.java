import java.util.*;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class OCPRecheck {

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

}

