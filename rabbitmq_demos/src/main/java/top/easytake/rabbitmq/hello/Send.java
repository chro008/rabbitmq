package top.easytake.rabbitmq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import top.easytake.rabbitmq.util.ConnectionUtil;

/**
 * @author lixiaoming
 * @date 2019/7/17 18:21
 */
public class Send {

    private static final  String QUEUE_NAME = "q_test_01";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "this is a message from send111";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");


        channel.close();
        connection.close();
    }
}
