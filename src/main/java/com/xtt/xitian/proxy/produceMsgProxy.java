package com.xtt.xitian.proxy;

import com.alibaba.fastjson.JSONObject;
import com.xtt.xitian.model.Light;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class produceMsgProxy {
    public void execute(HttpServletRequest request, HttpServletResponse response, String param1, String param2) {
        Cookie cookie0 = null;
        for(Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])){
            if("kafkaMsg".equals(cookie.getName())){
                cookie0 = cookie;
                break;
            }
        }
        cookie0 = Optional.ofNullable(cookie0).orElse(new Cookie("kafkaMsg", "0"));
        Light light = new Light(param1, param2);
        String kafkaMsg = JSONObject.toJSONString(light);

        cookie0.setValue(cookie0.getValue() + "-" + param1 + param2);
        response.addCookie(cookie0);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        produceKafkaMsg(kafkaMsg);
    }

    private void produceKafkaMsg(String kafkaMsg) {
        Properties prop = new Properties();
        //kafka连接地址
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.17.0.1"+":9092");
        //用于实现Serializer接口的密钥的串行器类。
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //生产者数据安全
        prop.put(ProducerConfig.ACKS_CONFIG,"-1");

        //创建生产者对象
        KafkaProducer<String,String> producer=new KafkaProducer<String, String>(prop);
        //生成10条数据
        //创建消息对象
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test2151", kafkaMsg);
        //调用生产者消息发送方法
        producer.send(producerRecord);
    }
}
