package com.my.rpc.testMessage;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.runner.RPCServerStub;
import com.my.rpc.runtime.protocol.RPCCallInformation;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;
import com.my.rpc.testMessage.MessageBeerArray.BeerArray;

public abstract class IBeerQueryServerStub extends RPCServerStub implements IBeerQuery {
  public IBeerQueryServerStub() {
    super(IBeerQuery.class);
  }

  public void dispatchCall(RPCCallInformation callInfo) throws RPCExceptionBase {
    IDeserializer deser = callInfo.getDeserializer();
    int methodIndexer = callInfo.getMethodId();
    switch (methodIndexer) {
      case 1:
        {
          RPCFloat param1 = new RPCFloat();
          param1.deserialize(deser);
          RPCString param2 = new RPCString();
          param2.deserialize(deser);
          RPCCommonMessage.SeqString param3 = new RPCCommonMessage.SeqString();
          param3.deserialize(deser);
          RPCCommonMessage.MapStrInt param4 = new RPCCommonMessage.MapStrInt();
          param4.deserialize(deser);
          RPCBinary param5 = new RPCBinary();
          param5.deserialize(deser);
          RPCCommonMessage.SeqInt param6 = new RPCCommonMessage.SeqInt();
          param6.deserialize(deser);
          BeerArray retValue_ = this.queryBeers(param1, param2, param3, param4, param5, param6);
          callInfo.reply(retValue_);
        }
        break;
      case 2:
        {
          RPCInt param1 = new RPCInt();
          param1.deserialize(deser);
          this.voidReturnTest(param1);
          callInfo.reply();
        }
        break;
      case 3:
        {
          this.exceptionTest();
          callInfo.reply();
        }
        break;
      default:
        throw RPCExceptionFactory.createException(
            RPCExceptionFactory.RPC_MethodNotFound_Exception, " method not found.");
    }
  }
}
