package top.easytake.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author lixiaoming
 * @date 2019/7/18 17:02
 */
public class ReceiveLog1 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");


        DeliverCallback callback = (s, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("work1 received '" + message + "'");
        };

        channel.basicConsume(queueName, false, callback, consumerTag -> {
        });

    }
}
