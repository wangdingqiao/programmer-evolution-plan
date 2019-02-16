/**
 * * This file is generated from src/test/example.idl by RPC message translator. Recompile the
 * source file if modification is needed. Do not manually modify this file. The RPC Message
 * Translator is a MIT License software authored by Wangdingqiao(393524628@qq.com).
 */
package com.my.rpc.testMessage;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.runner.RPCCallBackBase;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;
import com.my.rpc.testMessage.MessageBeerArray.BeerArray;

public interface IBeerQueryC {
  public BeerArray queryBeers(
      RPCFloat param1,
      RPCString param2,
      RPCCommonMessage.SeqString param3,
      RPCCommonMessage.MapStrInt param4,
      RPCBinary param5,
      RPCCommonMessage.SeqInt param6,
      RPCCallBackBase callBack)
      throws RPCExceptionBase;

  public void voidReturnTest(RPCInt param1, RPCCallBackBase callBack) throws RPCExceptionBase;

  public void exceptionTest(RPCCallBackBase callBack) throws RPCExceptionBase;
}
