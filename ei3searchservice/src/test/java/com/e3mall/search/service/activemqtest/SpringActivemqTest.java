package com.e3mall.search.service.activemqtest;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;


public class SpringActivemqTest {

//	@Test
//	public void testSpringActiveMq()throws Exception{
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//				"classpath:spring/applicationContext-activemq.xml");
//		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
//		Destination destination = (Destination)applicationContext.getBean("queueDestination");
//		jmsTemplate.send(destination, new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				TextMessage textMessage = session.createTextMessage("spring activemq queue message");
//				return textMessage;
//			}
//		});
//	}

//	@Test
//	public void testQueueConsumer()throws Exception{
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//				"classpath:spring/applicationContext-activemq.xml");
//		System.in.read();
//	}

}
