package com.my.rpc.testMessage;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;
import java.net.InetAddress;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.serializer.SerializeFactory;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.runner.RPCCallBackBase;
import com.my.rpc.runtime.serializer.ISerializer;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;
import com.my.rpc.testMessage.MessageBeerArray.BeerArray;
import com.my.rpc.runtime.runner.RPCClientStub;

public class IBeerQueryClientStub extends RPCClientStub implements IBeerQueryC {
  public IBeerQueryClientStub(InetAddress host, int port) {
    super(IBeerQuery.class, host, port);
  }

  public BeerArray queryBeers(
      RPCFloat param1,
      RPCString param2,
      RPCCommonMessage.SeqString param3,
      RPCCommonMessage.MapStrInt param4,
      RPCBinary param5,
      RPCCommonMessage.SeqInt param6,
      RPCCallBackBase callBack)
      throws RPCExceptionBase {
    RPCRequest request = new RPCRequest();
    request.setRequestTime(System.currentTimeMillis());
    ISerializer ser = SerializeFactory.getSerializer();
    param1.serialize(ser);
    param2.serialize(ser);
    param3.serialize(ser);
    param4.serialize(ser);
    param5.serialize(ser);
    param6.serialize(ser);
    request.setInterfaceId(IBeerQuery.class.getName());
    request.setMethodId(1);
    request.setBodyData(ser.toByteArray());
    call(request, callBack);
    return null;
  }

  public void voidReturnTest(RPCInt param1, RPCCallBackBase callBack) throws RPCExceptionBase {
    RPCRequest request = new RPCRequest();
    request.setRequestTime(System.currentTimeMillis());
    ISerializer ser = SerializeFactory.getSerializer();
    param1.serialize(ser);
    request.setInterfaceId(IBeerQuery.class.getName());
    request.setMethodId(2);
    request.setBodyData(ser.toByteArray());
    call(request, callBack);
  }

  public void exceptionTest(RPCCallBackBase callBack) throws RPCExceptionBase {
    RPCRequest request = new RPCRequest();
    request.setRequestTime(System.currentTimeMillis());
    ISerializer ser = SerializeFactory.getSerializer();
    request.setInterfaceId(IBeerQuery.class.getName());
    request.setMethodId(3);
    request.setBodyData(ser.toByteArray());
    call(request, callBack);
  }
}
