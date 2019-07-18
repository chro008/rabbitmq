package top.easytake.rabbitmq.work;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @author lixiaoming
 * @date 2019/7/17 19:28
 */
public class NewTask {

    private static final  String QUEUE_NAME = "q_test_02";
    private static final String baseMessage = "hello";

    //设置队列持久化和消息持久化 可以避免rabbitmq重启 队列或消息丢失 猜测持久化到硬盘恢复到内存
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;  //队列持久化
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        AMQP.BasicProperties properties = MessageProperties.PERSISTENT_TEXT_PLAIN; //消息持久化
        for(int i=0;i<100;i++) {
            StringBuilder message = new StringBuilder(baseMessage).append(i);
            channel.basicPublish("", QUEUE_NAME, properties, message.toString().getBytes("UTF-8"));
            System.out.println(" [task] Sent '" + message + "'");
        }



        channel.close();
        connection.close();
    }

}
