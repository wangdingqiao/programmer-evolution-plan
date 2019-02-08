/**
 * * This file is generated from src/main/java/rpcCommon.idl by RPC message translator. Recompile
 * the source file if modification is needed. Do not manually modify this file. The RPC Message
 * Translator is a MIT License software authored by Wangdingqiao(393524628@qq.com).
 */
package com.my.rpc.runtime.builtInMessage;

import java.util.HashMap;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.protocol.IRPCMessage;
import com.my.rpc.runtime.serializer.IDeserializer;
import java.util.ArrayList;
import java.util.Map;
import com.my.rpc.runtime.serializer.ISerializer;

public final class RPCCommonMessage {

  public static class SeqString implements IRPCMessage {
    public ArrayList<RPCString> elements;

    public SeqString() {
      this.elements = new ArrayList<RPCString>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCString value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCString value = new RPCString();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCString value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqString" + "[" + builder.toString() + "]";
    }
  }

  public static class SeqInt implements IRPCMessage {
    public ArrayList<RPCInt> elements;

    public SeqInt() {
      this.elements = new ArrayList<RPCInt>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCInt value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCInt value = new RPCInt();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCInt value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqInt" + "[" + builder.toString() + "]";
    }
  }

  public static class SeqShort implements IRPCMessage {
    public ArrayList<RPCShort> elements;

    public SeqShort() {
      this.elements = new ArrayList<RPCShort>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCShort value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCShort value = new RPCShort();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCShort value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqShort" + "[" + builder.toString() + "]";
    }
  }

  public static class SeqLong implements IRPCMessage {
    public ArrayList<RPCLong> elements;

    public SeqLong() {
      this.elements = new ArrayList<RPCLong>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCLong value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCLong value = new RPCLong();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCLong value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqLong" + "[" + builder.toString() + "]";
    }
  }

  public static class SeqFloat implements IRPCMessage {
    public ArrayList<RPCFloat> elements;

    public SeqFloat() {
      this.elements = new ArrayList<RPCFloat>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCFloat value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCFloat value = new RPCFloat();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCFloat value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqFloat" + "[" + builder.toString() + "]";
    }
  }

  public static class SeqDouble implements IRPCMessage {
    public ArrayList<RPCDouble> elements;

    public SeqDouble() {
      this.elements = new ArrayList<RPCDouble>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCDouble value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCDouble value = new RPCDouble();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCDouble value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqDouble" + "[" + builder.toString() + "]";
    }
  }

  public static class SeqDate implements IRPCMessage {
    public ArrayList<RPCDate> elements;

    public SeqDate() {
      this.elements = new ArrayList<RPCDate>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCDate value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCDate value = new RPCDate();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCDate value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqDate" + "[" + builder.toString() + "]";
    }
  }

  public static class SeqBool implements IRPCMessage {
    public ArrayList<RPCBool> elements;

    public SeqBool() {
      this.elements = new ArrayList<RPCBool>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = elements.size();
      ser.putInt(size);
      for (RPCBool value : elements) {
        value.serialize(ser);
      }
    }

    @Override
    public void deserialize(IDeserializer deser) throws RPCExceptionBase {
      int size = deser.readInt();
      for (int i = 0; i < size; ++i) {
        RPCBool value = new RPCBool();
        value.deserialize(deser);
        this.elements.add(value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String elementsText = new String("[");
      for (RPCBool value : elements) {
        elementsText += value.toString();
      }
      elementsText += "]";
      builder.append(elementsText);
      return "SeqBool" + "[" + builder.toString() + "]";
    }
  }

  public static class MapStrInt implements IRPCMessage {
    public HashMap<RPCString, RPCInt> str2Int;

    public MapStrInt() {
      this.str2Int = new HashMap<RPCString, RPCInt>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = str2Int.size();
      ser.putInt(size);
      for (Map.Entry<RPCString, RPCInt> entry : str2Int.entrySet()) {
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
        RPCInt value = new RPCInt();
        value.deserialize(deser);
        this.str2Int.put(key, value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String str2IntText = new String("{");
      for (Map.Entry<RPCString, RPCInt> entry : str2Int.entrySet()) {
        str2IntText += entry.getKey().toString() + ":" + entry.getValue().toString() + ",";
      }
      str2IntText += "}";
      builder.append(str2IntText);
      return "MapStrInt" + "[" + builder.toString() + "]";
    }
  }

  public static class MapStrStr implements IRPCMessage {
    public HashMap<RPCString, RPCString> str2Str;

    public MapStrStr() {
      this.str2Str = new HashMap<RPCString, RPCString>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = str2Str.size();
      ser.putInt(size);
      for (Map.Entry<RPCString, RPCString> entry : str2Str.entrySet()) {
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
        RPCString value = new RPCString();
        value.deserialize(deser);
        this.str2Str.put(key, value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String str2StrText = new String("{");
      for (Map.Entry<RPCString, RPCString> entry : str2Str.entrySet()) {
        str2StrText += entry.getKey().toString() + ":" + entry.getValue().toString() + ",";
      }
      str2StrText += "}";
      builder.append(str2StrText);
      return "MapStrStr" + "[" + builder.toString() + "]";
    }
  }

  public static class MapStrFloat implements IRPCMessage {
    public HashMap<RPCString, RPCFloat> str2Float;

    public MapStrFloat() {
      this.str2Float = new HashMap<RPCString, RPCFloat>();
    }

    @Override
    public void serialize(ISerializer ser) throws RPCExceptionBase {
      int size = str2Float.size();
      ser.putInt(size);
      for (Map.Entry<RPCString, RPCFloat> entry : str2Float.entrySet()) {
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
        RPCFloat value = new RPCFloat();
        value.deserialize(deser);
        this.str2Float.put(key, value);
      }
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      String str2FloatText = new String("{");
      for (Map.Entry<RPCString, RPCFloat> entry : str2Float.entrySet()) {
        str2FloatText += entry.getKey().toString() + ":" + entry.getValue().toString() + ",";
      }
      str2FloatText += "}";
      builder.append(str2FloatText);
      return "MapStrFloat" + "[" + builder.toString() + "]";
    }
  }
}
