package top.easytake.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lixiaoming
 * @date 2019/7/18 16:52
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message = "hello, log";

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));

        System.out.println("[X] Sent '" + message+ "'");
    }

}
