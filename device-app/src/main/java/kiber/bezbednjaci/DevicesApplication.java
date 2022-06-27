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
        var df1 = new DefaultRunnable(
                1,
                Arrays.stream(new MessageState[]{
                        new MessageState(0.0, 1.0, 4, "Camera detected a dog", null),
                        new MessageState(1.0, 2.0, 6, "Camera detected a vehicle", null),
                        new MessageState(2.0, 3.0, 1, "Camera detected a person", null)
                }).collect(Collectors.toList())

        );

//        // lights
//        var df2 = new DefaultRunnable(
//                111,
//                Arrays.stream(new MessageState[]{
//                        new MessageState(0.0, 1.0, 2, "Light Switch Turned On", null),
//                        new MessageState(1.0, 2.0, 3, "Light Switch Turned Off", null),
//                }).collect(Collectors.toList())
//
//        );
//
//        // doors
//        var df3 = new DefaultRunnable(
//                553,
//                Arrays.stream(new MessageState[]{
//                        new MessageState(0.0, 1.0, 4, "Door opened", null),
//                        new MessageState(1.0, 2.0, 6, "Door closed", null),
//                        new MessageState(2.0, 3.0, 1, "Door handle pulled while doors were locked", null),
//                        new MessageState(3.0, 4.0, 1, "Unsuccessful unlock attempt", null)
//                }).collect(Collectors.toList())
//
//        );
//
//        // air humidity meter
//        var df4 = new DefaultRunnable(
//                134,
//                Arrays.stream(new MessageState[]{
//                        new MessageState(35.0, 55.0, 5, "Air Humidity Meter Measured Humidity at", "%"),
//                        new MessageState(55.0, 100.0, 1, "Air Humidity Meter Measured Humidity at", "%"),
//                        new MessageState(0.0, 35.0, 1, "Air Humidity Meter Measured Humidity at", "%")
//                }).collect(Collectors.toList())
//
//        );
//
//        // thermometer
//        var df5 = new DefaultRunnable(
//                543,
//                Arrays.stream(new MessageState[]{
//                        new MessageState(18.0, 22.0, 4, "Thermometer measured Temperature at", " Degrees Celsius"),
//                        new MessageState(10.0, 18.0, 6, "Thermometer measured Temperature at", " Degrees Celsius"),
//                        new MessageState(22.0, 35.0, 1, "Thermometer measured Temperature at", " Degrees Celsius")
//                }).collect(Collectors.toList())
//
//        );

        Thread t1 = new Thread(df1);
//		Thread t2 = new Thread(df2);
//		Thread t3 = new Thread(df3);
//		Thread t4 = new Thread(df4);
//		Thread t5 = new Thread(df5);

        t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
//		t5.start();
    }

}
