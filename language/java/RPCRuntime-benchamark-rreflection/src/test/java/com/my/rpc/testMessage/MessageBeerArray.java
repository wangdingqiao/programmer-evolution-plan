/**
 * * This file is generated from src/test/example.idl by RPC message translator. Recompile the
 * source file if modification is needed. Do not manually modify this file. The RPC Message
 * Translator is a MIT License software authored by Wangdingqiao(393524628@qq.com).
 */
package com.my.rpc.testMessage;

import java.util.HashMap;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.protocol.IRPCMessage;
import com.my.rpc.runtime.serializer.IDeserializer;
import java.util.ArrayList;
import java.util.Map;
import com.my.rpc.runtime.serializer.ISerializer;

public final class MessageBeerArray {

  public static class BeerSort implements IRPCMessage {
    public ArrayList<RPCString> names;

    public BeerSort() {
      this.names = new ArrayList<RPCString>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = names.size();
      ser.putInt(size);
      for (RPCString value : names) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCString value = new RPCString();
        value.deserialize(deser);
        this.names.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String namesText = new String("[");
      for (RPCString value : names) {
        namesText += value.toString() + ",";
      }
      namesText += "]";
      builder.append(namesText);
      return "BeerSort" + "[" + builder.toString() + "]";
    }
  }

  public static class Beer implements IRPCMessage {
    public RPCString Brand;
    public BeerSort Sort;
    public RPCFloat Alcohol;
    public RPCString Brewery;

    public Beer() {
      this.Brand = new RPCString();
      this.Sort = new BeerSort();
      this.Alcohol = new RPCFloat();
      this.Brewery = new RPCString();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      Brand.serialize(ser);
      Sort.serialize(ser);
      Alcohol.serialize(ser);
      Brewery.serialize(ser);
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      this.Brand.deserialize(deser);
      this.Sort.deserialize(deser);
      this.Alcohol.deserialize(deser);
      this.Brewery.deserialize(deser);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Brand=" + Brand.toString() + ",");
      builder.append("Sort=" + Sort.toString() + ",");
      builder.append("Alcohol=" + Alcohol.toString() + ",");
      builder.append("Brewery=" + Brewery.toString() + ",");
      return "Beer" + "[" + builder.toString() + "]";
    }
  }

  public static class BeerArray implements IRPCMessage {
    public ArrayList<Beer> beers;

    public BeerArray() {
      this.beers = new ArrayList<Beer>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = beers.size();
      ser.putInt(size);
      for (Beer value : beers) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        Beer value = new Beer();
        value.deserialize(deser);
        this.beers.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String beersText = new String("[");
      for (Beer value : beers) {
        beersText += value.toString() + ",";
      }
      beersText += "]";
      builder.append(beersText);
      return "BeerArray" + "[" + builder.toString() + "]";
    }
  }

  public static class BeerMap implements IRPCMessage {
    public HashMap<RPCString, BeerArray> beers;

    public BeerMap() {
      this.beers = new HashMap<RPCString, BeerArray>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = beers.size();
      ser.putInt(size);
      for (Map.Entry<RPCString, BeerArray> entry : beers.entrySet()) {
        entry.getKey().serialize(ser);
        entry.getValue().serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCString key = new RPCString();
        key.deserialize(deser);
        BeerArray value = new BeerArray();
        value.deserialize(deser);
        this.beers.put(key, value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String beersText = new String("{");
      for (Map.Entry<RPCString, BeerArray> entry : beers.entrySet()) {
        beersText += entry.getKey().toString() + ":" + entry.getValue().toString() + ",";
      }
      beersText += "}";
      builder.append(beersText);
      return "BeerMap" + "[" + builder.toString() + "]";
    }
  }
}
