package top.easytake.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author lixiaoming
 * @date 2019/7/17 19:28
 */
public class Worker2 {

    private static final  String QUEUE_NAME = "q_test_02";
    private static final String baseMessage = "hello";

    public static void main(String[] args)  throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*2] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (s, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x2] Received '" + message + "'");

            try {
                doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x2] Done");
            }

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag->{});
    }

    private static void doWork(String task) throws InterruptedException {
        Thread.sleep(300);
    }
}
