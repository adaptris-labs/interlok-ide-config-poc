package com.adaptris.ide.cluster;

import com.adaptris.mgmt.cluster.ClusterInstance;
import com.adaptris.mgmt.cluster.jgroups.Broadcaster;

public class DummyBroadcaster implements Broadcaster {

  @Override
  public void setPingData(ClusterInstance data) {
    
  }

  @Override
  public void setSendDelaySeconds(int parseInt) {
    
  }

  @Override
  public void setJGroupsClusterName(String clusterName) {
    
  }

  @Override
  public void setDebug(boolean debug) {
    
  }

}
