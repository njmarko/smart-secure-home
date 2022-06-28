package kiber.bezbednjaci;

import kiber.bezbednjaci.model.MessageState;
import kiber.bezbednjaci.runnables.DefaultRunnable;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class DevicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevicesApplication.class, args);
    }

    @Bean
    public ApplicationRunner test() {
        return args -> {
            fireDeviceThreads();
        };
    }

    private void fireDeviceThreads() {

        // camera
        int stateid1 = 1;
        var df1 = new DefaultRunnable(
                1,
                Arrays.stream(new MessageState[]{
                        new MessageState(stateid1, 0.0, 1.0, 4, "Camera detected a dog", null),
                        new MessageState(stateid1, 1.0, 2.0, 6, "Camera detected a vehicle", null),
                        new MessageState(stateid1, 2.0, 3.0, 1, "Camera detected a person", null),
                        new MessageState(stateid1, 2.0, 3.0, 1, "This message is filtered because of regex 123456789 ,./!@#$%^&*()_+", null)
                }).collect(Collectors.toList())

        );

        // lights
        int stateid2 = 1;
        var df2 = new DefaultRunnable(
                2,
                Arrays.stream(new MessageState[]{
                        new MessageState(stateid2, 0.0, 1.0, 2, "Light Switch Turned On", null),
                        new MessageState(stateid2, 1.0, 2.0, 3, "Light Switch Turned Off", null),
                }).collect(Collectors.toList())

        );

        // doors
        int stateid3 = 2;
        var df3 = new DefaultRunnable(
                3,
                Arrays.stream(new MessageState[]{
                        new MessageState(stateid3, 0.0, 1.0, 4, "Door opened", null),
                        new MessageState(stateid3, 1.0, 2.0, 6, "Door closed", null),
                        new MessageState(stateid3, 2.0, 3.0, 1, "Door handle pulled while doors were locked", null),
                        new MessageState(stateid3, 3.0, 4.0, 1, "Unsuccessful unlock attempt", null)
                }).collect(Collectors.toList())

        );

        // air humidity meter
        int stateid4 = 3;
        var df4 = new DefaultRunnable(
                4,
                Arrays.stream(new MessageState[]{
                        new MessageState(stateid4, 35.0, 55.0, 5, "Air Humidity Meter Measured Humidity at", "%"),
                        new MessageState(stateid4, 55.0, 100.0, 1, "Air Humidity Meter Measured Humidity at", "%"),
                        new MessageState(stateid4, 0.0, 35.0, 1, "Air Humidity Meter Measured Humidity at", "%")
                }).collect(Collectors.toList())

        );

        // thermometer
        int stateid5 = 3;
        var df5 = new DefaultRunnable(
                5,
                Arrays.stream(new MessageState[]{
                        new MessageState(stateid5, 18.0, 22.0, 4, "Thermometer measured Temperature at", " Degrees Celsius"),
                        new MessageState(stateid5, 10.0, 18.0, 6, "Thermometer measured Temperature at", " Degrees Celsius"),
                        new MessageState(stateid5, 22.0, 35.0, 1, "Thermometer measured Temperature at", " Degrees Celsius")
                }).collect(Collectors.toList())

        );

        // barometer
        int stateid6 = 1;
        var df6 = new DefaultRunnable(
                6,
                Arrays.stream(new MessageState[]{
                        new MessageState(stateid6, 1000.0, 1030.0, 4, "Barometer measured Air Temperature at", "mb"),
                        new MessageState(stateid6, 900.0, 1000.0, 6, "Barometer measured Air Temperature at", "mb"),
                        new MessageState(stateid6, 1030.0, 1100.0, 1, "Barometer measured Air Temperature at", "mb")
                }).collect(Collectors.toList())

        );

        // heart rate monitor
        int stateid7 = 1;
        var df7 = new DefaultRunnable(
                7,
                Arrays.stream(new MessageState[]{
                        new MessageState(stateid7, 70.0, 90.0, 4, "Heart rate monitor measured Heart rate at", "bpm"),
                        new MessageState(stateid7, 90.0, 170.0, 6, "Heart rate monitor measured Heart rate at", " bpm"),
                        new MessageState(stateid7, 170.0, 220.0, 1, "Heart rate monitor measured Heart rate at", " bpm"),
                        new MessageState(stateid7, 20.0, 70.0, 1, "Heart rate monitor measured Heart rate at", " bpm")
                }).collect(Collectors.toList())

        );

        Thread t1 = new Thread(df1);
		Thread t2 = new Thread(df2);
		Thread t3 = new Thread(df3);
		Thread t4 = new Thread(df4);
		Thread t5 = new Thread(df5);
		Thread t6 = new Thread(df6);
		Thread t7 = new Thread(df7);

        t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
    }

}
