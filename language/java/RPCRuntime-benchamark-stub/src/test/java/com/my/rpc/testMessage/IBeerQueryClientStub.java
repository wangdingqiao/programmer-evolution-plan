package com.my.rpc.testMessage;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;
import java.net.InetAddress;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.serializer.SerializeFactory;
import com.my.rpc.runtime.protocol.RPCResponse;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.serializer.ISerializer;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;
import com.my.rpc.testMessage.MessageBeerArray.BeerArray;
import com.my.rpc.runtime.runner.RPCClientStub;

public class IBeerQueryClientStub extends RPCClientStub implements IBeerQuery {
  public IBeerQueryClientStub(InetAddress host, int port) {
    super(IBeerQuery.class, host, port);
  }

  public BeerArray queryBeers(
      RPCFloat param1,
      RPCString param2,
      RPCCommonMessage.SeqString param3,
      RPCCommonMessage.MapStrInt param4,
      RPCBinary param5,
      RPCCommonMessage.SeqInt param6)
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
    RPCResponse response = call(request);
    BeerArray _result_ = new BeerArray();
    _result_.deserialize(response.getMessageDeserializer());
    return _result_;
  }

  public BeerArray queryBeers2(RPCFloat param1, RPCString param2, RPCCommonMessage.SeqString param3)
      throws RPCExceptionBase {
    RPCRequest request = new RPCRequest();
    request.setRequestTime(System.currentTimeMillis());
    ISerializer ser = SerializeFactory.getSerializer();
    param1.serialize(ser);
    param2.serialize(ser);
    param3.serialize(ser);
    request.setInterfaceId(IBeerQuery.class.getName());
    request.setMethodId(2);
    request.setBodyData(ser.toByteArray());
    RPCResponse response = call(request);
    BeerArray _result_ = new BeerArray();
    _result_.deserialize(response.getMessageDeserializer());
    return _result_;
  }

  public BeerArray queryBeers3(
      RPCFloat param1,
      RPCString param2,
      RPCCommonMessage.SeqString param3,
      RPCCommonMessage.MapStrInt param4)
      throws RPCExceptionBase {
    RPCRequest request = new RPCRequest();
    request.setRequestTime(System.currentTimeMillis());
    ISerializer ser = SerializeFactory.getSerializer();
    param1.serialize(ser);
    param2.serialize(ser);
    param3.serialize(ser);
    param4.serialize(ser);
    request.setInterfaceId(IBeerQuery.class.getName());
    request.setMethodId(3);
    request.setBodyData(ser.toByteArray());
    RPCResponse response = call(request);
    BeerArray _result_ = new BeerArray();
    _result_.deserialize(response.getMessageDeserializer());
    return _result_;
  }
}
