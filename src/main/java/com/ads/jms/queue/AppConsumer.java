package com.ads.jms.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 队列模式下消息消费者会平分消息提供者发送的消息（多次运行产生多个消费者），无需提前订阅
 * 
 * @author FY470
 * 
 */
public class AppConsumer {

	private static final String URL = "tcp://127.0.0.1:61616";
	private static final String QUEUENAME = "queue-test";

	public static void main(String[] args) throws JMSException {
		// 1.创建connectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		// 2.创建connection
		Connection connection = connectionFactory.createConnection();
		// 3.启动连接
		connection.start();
		// 4.创建会话 （是否在事物中处理，应答模式：自动应答）
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.创建一个目标
		Destination destination = session.createQueue(QUEUENAME);
		// 6.创建一个消费者
		MessageConsumer consumer = session.createConsumer(destination);
		// 7.创建一个监听器
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("接受消息-" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		// 8.关闭连接
		//connection.close();
	}
}
