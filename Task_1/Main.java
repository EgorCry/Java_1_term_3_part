package Task_1;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static int MIN_VAL_TEMPERATURE = 15;
    private static int MAX_VAL_TEMPERATURE = 30;
    private static int MIN_VAL_CO2 = 30;
    private static int MAX_VAL_CO2 = 100;
    private static int NORM_TEMP = 25;
    private static int NORM_CO2_LEVEL = 70;

    public static void main(String[] args) {
        Sensor sensorTemp = new Sensor(MIN_VAL_TEMPERATURE, MAX_VAL_TEMPERATURE);
        Sensor sensorCarbon = new Sensor(MIN_VAL_CO2, MAX_VAL_CO2);

        Observable<Integer> obsCarbon = Observable.create(sensorCarbon);
        Observable<Integer> obsTemp = Observable.create(sensorTemp);

        Timer timer = new Timer();

        obsCarbon.zipWith(
                obsTemp,
                i -> createTimerObservable(100, timer),
                i -> createTimerObservable(100, timer),
                (carbon, temperature) -> new DataSensor(temperature, carbon)
        ).subscribe(new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull DataSensor data) {
                int temp = data.getTemperature();
                int carbon = data.getCo2();

                String warn = "";

                if (temp > NORM_TEMP && carbon > NORM_CO2_LEVEL) {
                    warn = "ALERT!!!";
                } else if (temp > NORM_TEMP || carbon > NORM_CO2_LEVEL) {
                    warn = "Warning!";
                }

                System.out.println("Temp: " + temp + ", CO2: " + carbon + " " + warn);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
                timer.cancel();
            }
        });
    }

    private static Observable<Long> createTimerObservable(int delay, Timer timer) {
        return Observable.create(emitter -> {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    emitter.onNext(System.currentTimeMillis());
                    emitter.onComplete();
                }
            };
            timer.schedule(task, delay);
        });
    }
}
