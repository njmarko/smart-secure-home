package kiber.bezbednjaci.service;

import kiber.bezbednjaci.dto.DeviceMessageRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
@RequiredArgsConstructor
public class MessageService {


    private final RabbitTemplate rabbitTemplate;

    public void sendMessageHttps(Integer estateId, DeviceMessageRequest r){
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        assert sslContext != null;
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

       var restTemplate = new RestTemplate(requestFactory);
        restTemplate.postForEntity("https://localhost:8081/api/device-messages/" + estateId, r, Object.class);
    }

    public void sendMessage(DeviceMessageRequest r){
        rabbitTemplate.convertAndSend("DEVICE_MESSAGES", r);
    }
}
