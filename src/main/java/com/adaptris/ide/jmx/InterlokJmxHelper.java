package com.adaptris.ide.jmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.adaptris.core.Adapter;
import com.adaptris.core.XStreamMarshaller;
import com.adaptris.ide.node.ExternalConnection;

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

  public List<ExternalConnection> getExternalConnections () {
    List<ExternalConnection> connections = new ArrayList<ExternalConnection>();
    
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
    
    return connections;
  }
}
