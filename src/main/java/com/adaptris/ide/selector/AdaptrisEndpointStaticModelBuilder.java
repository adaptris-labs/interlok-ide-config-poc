package com.adaptris.ide.selector;

import com.adaptris.ide.node.ExternalConnection;
import com.adaptris.ide.node.ExternalConnection.ConnectionTechnology;

public class AdaptrisEndpointStaticModelBuilder implements SelectorModelBuilder {

  @Override
  public SelectorModel build(SelectorModel rootNode) {
    rootNode.setDepth(1);
    
    // ******************** FS
    SelectorModel fileSystemModel = new SelectorModel();
    fileSystemModel.setDisplayText("File System");
    fileSystemModel.setDepth(2);
    
    SelectorModel fileSystemStandard = new SelectorModel();
    fileSystemStandard.setDisplayText("Standard File System");
    fileSystemStandard.setDepth(3);
    fileSystemStandard.setRepresentedObject(new ExternalConnection(ConnectionTechnology.FILESYSTEM));
    
    SelectorModel fileSystemImmediate = new SelectorModel();
    fileSystemImmediate.setDisplayText("Immediate File System");
    fileSystemImmediate.setDepth(3);
    fileSystemImmediate.setRepresentedObject(new ExternalConnection(ConnectionTechnology.FILESYSTEM));
    
    fileSystemModel.addAll(fileSystemStandard, fileSystemImmediate);
    // ******************** FS
    
    // ******************** Messaging
    SelectorModel messagingModel = new SelectorModel();
    messagingModel.setDisplayText("Java Messaging");
    messagingModel.setDepth(2);
    
    SelectorModel solaceModel = new SelectorModel();
    solaceModel.setDisplayText("Solace Systems");
    solaceModel.setDepth(3);
    
    // ******************** Solace
    SelectorModel jms = new SelectorModel();
    jms.setDisplayText("JMS");
    jms.setDepth(4);
    jms.setRepresentedObject(new ExternalConnection(ConnectionTechnology.SOLACE));
    
    SelectorModel jcsmp = new SelectorModel();
    jcsmp.setDisplayText("JCSMP");
    jcsmp.setDepth(4);
    jcsmp.setRepresentedObject(new ExternalConnection(ConnectionTechnology.SOLACE));
    
    solaceModel.addAll(jms, jcsmp);
    // ******************** Solace
    
    SelectorModel wmqModel = new SelectorModel();
    wmqModel.setDisplayText("IBM Wmq");
    wmqModel.setDepth(3);
    wmqModel.setRepresentedObject(new ExternalConnection(ConnectionTechnology.WMQ));
    
    SelectorModel sonicModel = new SelectorModel();
    sonicModel.setDisplayText("SonicMQ");
    sonicModel.setDepth(3);
    sonicModel.setRepresentedObject(new ExternalConnection(ConnectionTechnology.WMQ));
    
    SelectorModel activeMqModel = new SelectorModel();
    activeMqModel.setDisplayText("ActiveMQ");
    activeMqModel.setDepth(3);
    activeMqModel.setRepresentedObject(new ExternalConnection(ConnectionTechnology.WMQ));
    
    SelectorModel amqpModel = new SelectorModel();
    amqpModel.setDisplayText("AMQP");
    amqpModel.setDepth(3);
    amqpModel.setRepresentedObject(new ExternalConnection(ConnectionTechnology.WMQ));
    
    messagingModel.addAll(solaceModel, wmqModel, sonicModel, activeMqModel, amqpModel);
    // ******************** Messaging
    
 // ******************** Http
    SelectorModel httpModel = new SelectorModel();
    httpModel.setDisplayText("HTTP/s");
    httpModel.setDepth(2);
    
    SelectorModel http = new SelectorModel();
    http.setDisplayText("HTTP");
    http.setDepth(3);
    http.setRepresentedObject(new ExternalConnection(ConnectionTechnology.HTTP));
    
    SelectorModel https = new SelectorModel();
    https.setDisplayText("HTTPS");
    https.setDepth(3);
    https.setRepresentedObject(new ExternalConnection(ConnectionTechnology.HTTP));
    
    httpModel.addAll(http, https);
    // ******************** FS
    
    rootNode.addAll(fileSystemModel, messagingModel, httpModel);
    

    return rootNode;
  }

}
