package kiber.bezbednjaci.runnables;

import kiber.bezbednjaci.dto.DeviceMessageRequest;
import kiber.bezbednjaci.model.MessageState;
import kiber.bezbednjaci.model.MessageType;
import kiber.bezbednjaci.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultRunnable implements Runnable {
    private Integer deviceId;
    private List<MessageState> states;

    private MessageService msgSer;

    @Override
    public void run() {
        Random r = new Random();
        var i = states.iterator();

        while (true) {
            var state = i.next();
            var stream = r.doubles(state.getNumMessages(), state.getMinVal(), state.getMaxVal());

            for (var s : stream.toArray()) {
                var msg = new DeviceMessageRequest();
                msg.setValue(s);
                msg.setDeviceId(deviceId);
                msg.setMessageType(MessageType.INFO);
                msg.setObjectId(state.getEstateId());
                if (state.getMeasureUnit() == null)
                    msg.setContent(state.getMessageContent());
                else
                    msg.setContent(state.getMessageContent() + " " + String.format("%.2f", msg.getValue()) + state.getMeasureUnit());

                System.out.println(msg.getContent());
//                msgSer.sendMessageHttps(state.getEstateId(), msg);
                msgSer.sendMessage(msg);

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
