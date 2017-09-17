package com.ads.jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 队列模式下消息提供者发送的消息会被多个提前订阅的消费者消费
 * 
 * @author FY470
 * 
 */
public class AppProducer {

	private static final String URL = "tcp://127.0.0.1:61616";
	private static final String TOPICNAME = "topic-test";

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
		Destination destination = session.createTopic(TOPICNAME);
		// 6.创建一个生产者
		MessageProducer producer = session.createProducer(destination);

		for (int i = 0; i < 10; i++) {
			// 7.创建消息
			TextMessage textMessage = session.createTextMessage("text - " + i);
			// 8.发布消息
			producer.send(textMessage);
			System.out.println("发送消息成功-" + textMessage.getText());
		}
		// 9.关闭连接
		connection.close();
	}

}
