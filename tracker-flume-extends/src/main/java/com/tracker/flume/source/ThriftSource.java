package com.tracker.flume.source;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.flume.ChannelException;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.FlumeException;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.instrumentation.SourceCounter;
import org.apache.flume.source.AbstractSource;
import org.apache.flume.thrift.Status;
import org.apache.flume.thrift.ThriftFlumeEvent;
import org.apache.flume.thrift.ThriftSourceProtocol;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThriftSource extends AbstractSource implements Configurable,
  EventDrivenSource {

  public static final Logger logger = LoggerFactory.getLogger(ThriftSource
    .class);
  /**
   * Config param for the maximum number of threads this source should use to
   * handle incoming data.
   */
  public static final String CONFIG_THREADS = "threads";
  /**
   * Config param for the hostname to listen on.
   */
  public static final String CONFIG_BIND = "bind";
  /**
   * Config param for the port to listen on.
   */
  public static final String CONFIG_PORT = "port";
  private Integer port;
  private String bindAddress;
  private int maxThreads = 0;
  private SourceCounter sourceCounter;
  private TServer server;
  private TNonblockingServerSocket serverTransport;
  private ExecutorService servingExecutor;

  @Override
  public void configure(Context context) {
    logger.info("Configuring thrift source.");
    port = context.getInteger(CONFIG_PORT);
    Preconditions.checkNotNull(port, "Port must be specified for Thrift " +
      "Source.");
    bindAddress = context.getString(CONFIG_BIND);
    Preconditions.checkNotNull(bindAddress, "Bind address must be specified " +
      "for Thrift Source.");

    try {
      maxThreads = context.getInteger(CONFIG_THREADS, 0);
    } catch (NumberFormatException e) {
      logger.warn("Thrift source\'s \"threads\" property must specify an " +
        "integer value: " + context.getString(CONFIG_THREADS));
    }

    if (sourceCounter == null) {
      sourceCounter = new SourceCounter(getName());
    }
  }

  @Override
  public void start() {
    logger.info("Starting thrift source");
    ExecutorService sourceService;
    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(
      "Flume Thrift IPC Thread %d").build();
    if (maxThreads == 0) {
      sourceService = Executors.newCachedThreadPool(threadFactory);
    } else {
      sourceService = Executors.newFixedThreadPool(maxThreads, threadFactory);
    }
    try {
      serverTransport = new TNonblockingServerSocket(new InetSocketAddress
        (bindAddress, port));
    } catch (TTransportException e) {
      throw new FlumeException("Failed to start Thrift Source.", e);
    }

    THsHaServer.Args thhsArgs = new THsHaServer.Args(serverTransport); 
	thhsArgs.processor(new ThriftSourceProtocol.Processor(new ThriftSourceHandler()));
//	thhsArgs.transportFactory(new TFramedTransport.Factory());
	thhsArgs.protocolFactory(new TBinaryProtocol.Factory());
	thhsArgs.executorService(sourceService);
	server = new THsHaServer(thhsArgs);// 半同步半异步的服务模型
    
    
    servingExecutor = Executors.newSingleThreadExecutor(new
      ThreadFactoryBuilder().setNameFormat("Flume Thrift Source I/O Boss")
      .build());
    /**
     * Start serving.
     */
    servingExecutor.submit(new Runnable() {
      @Override
      public void run() {
        server.serve();
      }
    });

    long timeAfterStart = System.currentTimeMillis();
    while(!server.isServing()) {
      try {
        if(System.currentTimeMillis() - timeAfterStart >=10000) {
          throw new FlumeException("Thrift server failed to start!");
        }
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new FlumeException("Interrupted while waiting for Thrift server" +
          " to start.", e);
      }
    }
    sourceCounter.start();
    logger.info("Started Thrift source.");
    super.start();
  }

  public void stop() {
    if(server != null && server.isServing()) {
      server.stop();
    }
    servingExecutor.shutdown();
    try {
      if(!servingExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
        servingExecutor.shutdownNow();
      }
    } catch (InterruptedException e) {
      throw new FlumeException("Interrupted while waiting for server to be " +
        "shutdown.");
    }
    sourceCounter.stop();
    // Thrift will shutdown the executor passed to it.
    super.stop();
  }

  private class ThriftSourceHandler implements ThriftSourceProtocol.Iface {

    @Override
    public Status append(ThriftFlumeEvent event) throws TException {
      Event flumeEvent = EventBuilder.withBody(event.getBody(),
        event.getHeaders());

      sourceCounter.incrementAppendReceivedCount();
      sourceCounter.incrementEventReceivedCount();

      try {
        getChannelProcessor().processEvent(flumeEvent);
      } catch (ChannelException ex) {
        logger.warn("Thrift source " + getName() + " could not append events " +
          "to the channel.", ex);
        return Status.FAILED;
      }
      sourceCounter.incrementAppendAcceptedCount();
      sourceCounter.incrementEventAcceptedCount();
      return Status.OK;
    }

    @Override
    public Status appendBatch(List<ThriftFlumeEvent> events) throws TException {
      sourceCounter.incrementAppendBatchReceivedCount();
      sourceCounter.addToEventReceivedCount(events.size());

      List<Event> flumeEvents = Lists.newArrayList();
      for(ThriftFlumeEvent event : events) {
        flumeEvents.add(EventBuilder.withBody(event.getBody(),
          event.getHeaders()));
      }

      try {
        getChannelProcessor().processEventBatch(flumeEvents);
      } catch (ChannelException ex) {
        logger.warn("Thrift source %s could not append events to the " +
          "channel.", getName());
        return Status.FAILED;
      }

      sourceCounter.incrementAppendBatchAcceptedCount();
      sourceCounter.addToEventAcceptedCount(events.size());
      return Status.OK;
    }
  }
}