package kiber.bezbednjaci.runnables;

import kiber.bezbednjaci.dto.DeviceMessageRequest;
import kiber.bezbednjaci.model.MessageState;
import kiber.bezbednjaci.model.MessageType;
import kiber.bezbednjaci.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultRunnable implements Runnable {
    private Integer deviceId;
    private List<MessageState> states;

    @Override
    public void run() {
        Random r = new Random();
        var i = states.iterator();
        var msgSer = new MessageService();

        while (true) {
            var state = i.next();
            var stream = r.doubles(state.getNumMessages(), state.getMinVal(), state.getMaxVal());

            for (var s : stream.toArray()) {
                var msg = new DeviceMessageRequest();
                msg.setValue(s);
                msg.setDeviceId(deviceId);
                msg.setMessageType(MessageType.INFO);
                if (state.getMeasureUnit() == null)
                    msg.setContent(state.getMessageContent());
                else msg.setContent(state.getMessageContent() + " " + String.format("%.2f", msg.getValue()) + state.getMeasureUnit());

                System.out.println(msg.getContent());
                msgSer.sendMessageHttps(msg);

                try {
                    Thread.sleep(r.nextInt(10000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (!i.hasNext()) {
                i = states.iterator();
            }
        }
    }
}
