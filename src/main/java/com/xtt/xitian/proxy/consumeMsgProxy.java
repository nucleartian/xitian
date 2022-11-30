package com.xtt.xitian.proxy;

import com.alibaba.fastjson.JSONObject;
import com.xtt.xitian.model.Light;
import com.xtt.xitian.proxy.utils.KafkaConsumeTool;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class consumeMsgProxy {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Light light = consumeKafkaMsg();
        if(light != null){
            Cookie kafkaConsumeMsg1 = null;
            Cookie kafkaConsumeMsg2 = null;
            for(Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])){
                if("kafkaConsumeMsg1".equals(cookie.getName())){
                    kafkaConsumeMsg1 = cookie;
                    continue;
                }
                if("kafkaConsumeMsg2".equals(cookie.getName())){
                    kafkaConsumeMsg2 = cookie;
                    continue;
                }
            }
            kafkaConsumeMsg1 = Optional.ofNullable(kafkaConsumeMsg1).orElse(new Cookie("kafkaConsumeMsg1", ""));
            kafkaConsumeMsg2 = Optional.ofNullable(kafkaConsumeMsg2).orElse(new Cookie("kafkaConsumeMsg2", ""));

            kafkaConsumeMsg1.setValue(kafkaConsumeMsg1.getValue() + "-" + light.getParam1());
            kafkaConsumeMsg2.setValue(kafkaConsumeMsg2.getValue() + "-" + light.getParam2());
            response.addCookie(kafkaConsumeMsg1);
            response.addCookie(kafkaConsumeMsg2);
        }

        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Light consumeKafkaMsg() {
        return KafkaConsumeTool.getInstance().getLight();
    }
}
