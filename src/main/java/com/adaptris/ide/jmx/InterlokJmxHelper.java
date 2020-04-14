package com.adaptris.ide.jmx;

import com.adaptris.core.*;
import com.adaptris.ide.node.ExternalConnection;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashSet;
import java.util.Set;

public class InterlokJmxHelper {
  
  private static final String ADAPTER_OBJECT_NAME = "com.adaptris:type=Adapter,*";
  private static final String ADAPTER_CONFIG_ATTRIBUTE = "Configuration";
    
  private MBeanServerConnection mBeanServerConnection;
  
  private Adapter adapter;
  
  public InterlokJmxHelper() {
  }

  public InterlokJmxHelper withMBeanServer(String address) throws Exception {
    JMXServiceURL url = new JMXServiceURL(address);
    JMXConnector jmxc = JMXConnectorFactory.connect(url);
    mBeanServerConnection = jmxc.getMBeanServerConnection();
    
    return this;
  }
  
  public InterlokJmxHelper getInterlokConfig() throws Exception {
    Set<ObjectInstance> queryMBeans = mBeanServerConnection.queryMBeans(new ObjectName(ADAPTER_OBJECT_NAME), null);
    
    if(queryMBeans.size() == 1) {
      ObjectInstance objectInstance = (ObjectInstance) queryMBeans.toArray()[0];
      
      String config = (String) mBeanServerConnection.getAttribute(objectInstance.getObjectName(), ADAPTER_CONFIG_ATTRIBUTE);
      
      XStreamMarshaller marshaller = new XStreamMarshaller();
      adapter = (Adapter) marshaller.unmarshal(config);
      
      return this;
    } else
      return null;
  }

  public Set<ExternalConnection> getExternalConnections () {
    Set<ExternalConnection> connections = new HashSet<>();

    for (Channel channel : adapter.getChannelList())
    {
      AdaptrisConnection consumeConnection = channel.getConsumeConnection();
      AdaptrisConnection produceConnection = channel.getProduceConnection();

      for (Workflow workflow : channel.getWorkflowList())
      {
        ExternalConnection connection = new ExternalConnection(ExternalConnection.ConnectionDirection.CONSUME);

        AdaptrisMessageConsumer consumer = workflow.getConsumer();
        ConsumeDestination consumeDestination = consumer.getDestination();
        connection.setConnectionClassName(consumeConnection.getClass().getName());
        connection.setTechnology(guessTechnology(consumeConnection));
        connection.setConnectionUrl(consumeDestination != null ? consumeDestination.getDestination() : "NULL");
        connection.setEndpoint("TODO");

        connections.add(connection);

        connection = new ExternalConnection(ExternalConnection.ConnectionDirection.PRODUCE);

        AdaptrisMessageProducer producer = workflow.getProducer();
        ProduceDestination produceDestination = producer.getDestination();
        connection.setConnectionClassName(produceConnection.getClass().getName());
        connection.setTechnology(guessTechnology(produceConnection));
        try
        {
          connection.setConnectionUrl(produceDestination.getDestination(DefaultMessageFactory.getDefaultInstance().newMessage()));
        }
        catch (Exception e)
        {
          connection.setConnectionUrl("NULL");
        }
        connection.setEndpoint("TODO");

        connections.add(connection);
      }
    }

    /*
    ExternalConnection consumeConnection = new ExternalConnection();
    consumeConnection.setConnectionClassName("com.adaptris.core.http.jetty.EmbeddedConnection");
    consumeConnection.setConnectionUrl("http://192.168.0.1/");
    consumeConnection.setEndpoint("/endpoint1");
    consumeConnection.setTechnology(ExternalConnection.ConnectionTechnology.HTTP);
    
    ExternalConnection produceConnection = new ExternalConnection();
    produceConnection.setConnectionClassName("com.adaptris.core.JmsConnection");
    produceConnection.setConnectionUrl("tcp://localhost:55555");
    produceConnection.setEndpoint("jms:queue:Sample.Q1");
    produceConnection.setTechnology(ExternalConnection.ConnectionTechnology.SOLACE);

    connections.add(consumeConnection);
    connections.add(produceConnection);
    */

    return connections;
  }

  private ExternalConnection.ConnectionTechnology guessTechnology(AdaptrisConnection connection)
  {
    for (ExternalConnection.ConnectionTechnology tech : ExternalConnection.ConnectionTechnology.values())
    {
      if (tech.isConnectionTechnology(connection.getClass().getName()))
      {
        return tech;
      }
    }
    return ExternalConnection.ConnectionTechnology.NULL;
  }
}
