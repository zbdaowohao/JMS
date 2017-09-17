package com.ads.jms.topic;

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
 * ����ģʽ����Ϣ�����߻��ȡ��Ϣ�ṩ�߷��͵�ȫ����Ϣ��������в�����������ߣ�����Ҫ��ǰ���ķ����޷����յ���Ϣ
 * 
 * @author FY470
 * 
 */
public class AppConsumer {

	private static final String URL = "tcp://127.0.0.1:61616";
	private static final String TOPICNAME = "topic-test";

	public static void main(String[] args) throws JMSException {
		// 1.����connectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
		// 2.����connection
		Connection connection = connectionFactory.createConnection();
		// 3.��������
		connection.start();
		// 4.�����Ự ���Ƿ��������д���Ӧ��ģʽ���Զ�Ӧ��
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.����һ��Ŀ��
		Destination destination = session.createTopic(TOPICNAME);
		// 6.����һ��������
		MessageConsumer consumer = session.createConsumer(destination);
		// 7.����һ��������
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("������Ϣ-" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		// 8.�ر�����
		//connection.close();
	}
}
