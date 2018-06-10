package com.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.jms.JmsException;

import javax.jms.*;
import java.util.concurrent.ExecutionException;

public class ActivemqTest {

	@Test
	public void testQueueProducer() throws Exception{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.136:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("test-queue");
		MessageProducer producer = session.createProducer(queue);
		TextMessage textMessage = session.createTextMessage("hello activeMq, this is my first test");
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testQueueConsumer()throws Exception{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.136:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("test-queue");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try{
					TextMessage textMessage = (TextMessage)message;
					String text = null;
					text = textMessage.getText();
					System.out.println(text);
				}catch (JMSException e){
					e.printStackTrace();
				}
			}
		});
		System.in.read();
		connection.close();
		session.close();
		connection.close();
	}

	@Test
	public void testTopicProducer()throws Exception{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.136:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageProducer producer = session.createProducer(topic);
		TextMessage textMessage = session.createTextMessage("hello activemq, this is my topic test");
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testTopicConsumer()throws Exception{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.136:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try{
					TextMessage textMessage = (TextMessage)message;
					String text = null;
					text = textMessage.getText();
					System.out.println(text);
				}catch (JMSException e){
					e.printStackTrace();
				}
			}
		});
		System.out.println("topic 的消费");
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
	}
}
