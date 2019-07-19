package top.easytake.rabbitmq.routing;

import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.easytake.rabbitmq.util.ConnectionUtil;

/**
 * @author lixiaoming
 * @date 2019/7/18 18:05
 */
public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String[] logType = {"warn", "info", "error"};

        for (int i = 0; i < 100; i++) {
            String type = logType[new Random().nextInt(3)];
            String message = "this is a/an " + type + " message";
            System.out.println("Send '" + message + "'");
            channel.basicPublish(EXCHANGE_NAME, type, null, message.getBytes());
        }

        channel.close();
        connection.close();
    }

}
