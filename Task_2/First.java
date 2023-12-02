package Task_2;

import io.reactivex.rxjava3.core.Observable;

public class First {
    public static void main(String[] args) {
        System.out.println("Поток количества чисел");
        Observable.range(1, (int) Math.round(Math.random() * 1000))
                .count()
                .subscribe(System.out::println);
    }
}
