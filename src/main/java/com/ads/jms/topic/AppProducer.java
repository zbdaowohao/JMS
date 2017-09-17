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
 * ����ģʽ����Ϣ�ṩ�߷��͵���Ϣ�ᱻ�����ǰ���ĵ�����������
 * 
 * @author FY470
 * 
 */
public class AppProducer {

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
		MessageProducer producer = session.createProducer(destination);

		for (int i = 0; i < 10; i++) {
			// 7.������Ϣ
			TextMessage textMessage = session.createTextMessage("text - " + i);
			// 8.������Ϣ
			producer.send(textMessage);
			System.out.println("������Ϣ�ɹ�-" + textMessage.getText());
		}
		// 9.�ر�����
		connection.close();
	}

}
