## 生成protobuf

```
D:\java-eclipse-workspace\serializationPerformance> protoc --proto_path=. --java_out=.\src\main\java .\BeerArray.proto
```


## 参考资料


### Java序列化库

- [Using Google's Protocol Buffers With Java](https://dzone.com/articles/using-googles-protocol-buffers-with-java)

- [Protocol Buffer Basics: Java](https://developers.google.com/protocol-buffers/docs/javatutorial)

- [https://dzone.com/articles/using-jaxb-for-xml-with-java](https://dzone.com/articles/using-jaxb-for-xml-with-java)

- [Jackson Custom Serializer](https://www.concretepage.com/jackson-api/jackson-custom-serializer)

- [Understanding sun.misc.Unsafe](https://dzone.com/articles/understanding-sunmiscunsafe)
- [Guide to sun.misc.Unsafe](https://www.baeldung.com/java-unsafe)

- [msgpack/msgpack-java](https://github.com/msgpack/msgpack-java)

- [michel-kraemer/bson4jackson](https://github.com/michel-kraemer/bson4jackson)

### 序列化优化性能

- [Increase Java Serialization Performance](http://www.drdobbs.com/jvm/increase-java-serialization-performance/240159166?pgno=1)
- [High performance serialization: Java vs Google Protocol Buffers vs …?](https://stackoverflow.com/questions/647779/high-performance-serialization-java-vs-google-protocol-buffers-vs)
- [Serialization benchmarks and charts](https://dzone.com/articles/serialization-benchmarks-and)
- [Java Persistence Performance](http://java-persistence-performance.blogspot.com/2013/08/optimizing-java-serialization-java-vs.html)

- [Comparing Serialization Performance](https://www.robert-franz.com/2013/12/09/comparing-serialization-performance/)
- [Efficient Java I/O: byte[], ByteBuffers, and OutputStreams](http://www.evanjones.ca/software/java-bytebuffers.html)
- [https://www.baeldung.com/java-unsafe](https://mechanical-sympathy.blogspot.com/2012/07/native-cc-like-performance-for-java.html)
- [maximn/SerializationPerformanceTest_CSharp](https://github.com/maximn/SerializationPerformanceTest_CSharp)