package Task_1;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Sensor implements ObservableOnSubscribe<Integer> {
    private final int minValue;
    private final int maxValue;
    private final Timer timer;

    public Sensor(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.timer = new Timer();
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {
        Random random = new Random();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int value = random.nextInt((maxValue - minValue) + 1) + minValue;
                emitter.onNext(value);
            }
        }, 0, 1000);
    }
}
