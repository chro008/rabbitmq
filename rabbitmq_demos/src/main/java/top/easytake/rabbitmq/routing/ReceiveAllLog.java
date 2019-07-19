package top.easytake.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import top.easytake.rabbitmq.util.ConnectionUtil;

/**
 * @author lixiaoming
 * @date 2019/7/18 18:19
 */
public class ReceiveAllLog {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception  {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        String[] logType = {"warn", "info", "error"};
        for (String type: logType) {
            channel.queueBind(queueName, EXCHANGE_NAME, type);
        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [all] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
