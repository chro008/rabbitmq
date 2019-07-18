package top.easytake.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lixiaoming
 * @date 2019/7/17 19:28
 */
public class NewTask {

    private static final  String QUEUE_NAME = "q_test_01";
    private static final String baseMessage = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for(int i=0;i<100;i++) {
            StringBuilder message = new StringBuilder(baseMessage).append(i);
            channel.basicPublish("", QUEUE_NAME, null, message.toString().getBytes("UTF-8"));
            System.out.println(" [task] Sent '" + message + "'");
        }



        channel.close();
        connection.close();
    }

}
