# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopFor
# Parameters: (N = 10000)

# Run progress: 0.00% complete, ETA 00:14:40
# Fork: 1 of 1
# Warmup Iteration   1: 0.040 ms/op
# Warmup Iteration   2: 0.042 ms/op
# Warmup Iteration   3: 0.042 ms/op
Iteration   1: 0.042 ms/op
Iteration   2: 0.042 ms/op
Iteration   3: 0.041 ms/op
Iteration   4: 0.042 ms/op
Iteration   5: 0.042 ms/op
Iteration   6: 0.041 ms/op
Iteration   7: 0.041 ms/op
Iteration   8: 0.041 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopFor":
  0.042 ±(99.9%) 0.001 ms/op [Average]
  (min, avg, max) = (0.041, 0.042, 0.042), stdev = 0.001
  CI (99.9%): [0.041, 0.042] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopFor
# Parameters: (N = 10000000)

# Run progress: 12.50% complete, ETA 00:12:52
# Fork: 1 of 1
# Warmup Iteration   1: 54.831 ms/op
# Warmup Iteration   2: 55.013 ms/op
# Warmup Iteration   3: 54.674 ms/op
Iteration   1: 54.676 ms/op
Iteration   2: 55.033 ms/op
Iteration   3: 54.707 ms/op
Iteration   4: 54.755 ms/op
Iteration   5: 55.402 ms/op
Iteration   6: 54.706 ms/op
Iteration   7: 56.229 ms/op
Iteration   8: 55.525 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopFor":
  55.129 ±(99.9%) 1.060 ms/op [Average]
  (min, avg, max) = (54.676, 55.129, 56.229), stdev = 0.555
  CI (99.9%): [54.069, 56.190] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopForEach
# Parameters: (N = 10000)

# Run progress: 25.00% complete, ETA 00:11:05
# Fork: 1 of 1
# Warmup Iteration   1: 0.042 ms/op
# Warmup Iteration   2: 0.043 ms/op
# Warmup Iteration   3: 0.047 ms/op
Iteration   1: 0.047 ms/op
Iteration   2: 0.048 ms/op
Iteration   3: 0.047 ms/op
Iteration   4: 0.047 ms/op
Iteration   5: 0.047 ms/op
Iteration   6: 0.047 ms/op
Iteration   7: 0.047 ms/op
Iteration   8: 0.047 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopForEach":
  0.047 ±(99.9%) 0.001 ms/op [Average]
  (min, avg, max) = (0.047, 0.047, 0.048), stdev = 0.001
  CI (99.9%): [0.047, 0.048] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopForEach
# Parameters: (N = 10000000)

# Run progress: 37.50% complete, ETA 00:09:13
# Fork: 1 of 1
# Warmup Iteration   1: 59.189 ms/op
# Warmup Iteration   2: 60.173 ms/op
# Warmup Iteration   3: 58.432 ms/op
Iteration   1: 59.192 ms/op
Iteration   2: 59.160 ms/op
Iteration   3: 58.393 ms/op
Iteration   4: 58.766 ms/op
Iteration   5: 59.807 ms/op
Iteration   6: 60.293 ms/op
Iteration   7: 59.331 ms/op
Iteration   8: 58.738 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopForEach":
  59.210 ±(99.9%) 1.173 ms/op [Average]
  (min, avg, max) = (58.393, 59.210, 60.293), stdev = 0.614
  CI (99.9%): [58.037, 60.383] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopIterator
# Parameters: (N = 10000)

# Run progress: 50.00% complete, ETA 00:07:23
# Fork: 1 of 1
# Warmup Iteration   1: 0.042 ms/op
# Warmup Iteration   2: 0.043 ms/op
# Warmup Iteration   3: 0.047 ms/op
Iteration   1: 0.047 ms/op
Iteration   2: 0.047 ms/op
Iteration   3: 0.047 ms/op
Iteration   4: 0.047 ms/op
Iteration   5: 0.047 ms/op
Iteration   6: 0.047 ms/op
Iteration   7: 0.047 ms/op
Iteration   8: 0.047 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopIterator":
  0.047 ±(99.9%) 0.001 ms/op [Average]
  (min, avg, max) = (0.047, 0.047, 0.047), stdev = 0.001
  CI (99.9%): [0.047, 0.047] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopIterator
# Parameters: (N = 10000000)

# Run progress: 62.50% complete, ETA 00:05:32
# Fork: 1 of 1
# Warmup Iteration   1: 59.183 ms/op
# Warmup Iteration   2: 59.000 ms/op
# Warmup Iteration   3: 62.701 ms/op
Iteration   1: 64.162 ms/op
Iteration   2: 67.308 ms/op
Iteration   3: 61.938 ms/op
Iteration   4: 60.025 ms/op
Iteration   5: 59.985 ms/op
Iteration   6: 65.629 ms/op
Iteration   7: 61.392 ms/op
Iteration   8: 62.549 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopIterator":
  62.873 ±(99.9%) 5.035 ms/op [Average]
  (min, avg, max) = (59.985, 62.873, 67.308), stdev = 2.633
  CI (99.9%): [57.839, 67.908] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopWhile
# Parameters: (N = 10000)

# Run progress: 75.00% complete, ETA 00:03:41
# Fork: 1 of 1
# Warmup Iteration   1: 0.041 ms/op
# Warmup Iteration   2: 0.042 ms/op
# Warmup Iteration   3: 0.042 ms/op
Iteration   1: 0.042 ms/op
Iteration   2: 0.042 ms/op
Iteration   3: 0.042 ms/op
Iteration   4: 0.042 ms/op
Iteration   5: 0.042 ms/op
Iteration   6: 0.042 ms/op
Iteration   7: 0.042 ms/op
Iteration   8: 0.042 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopWhile":
  0.042 ±(99.9%) 0.001 ms/op [Average]
  (min, avg, max) = (0.042, 0.042, 0.042), stdev = 0.001
  CI (99.9%): [0.042, 0.042] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_201, Java HotSpot(TM) 64-Bit Server VM, 25.201-b09
# VM invoker: C:\Program Files\Java\jre1.8.0_201\bin\java.exe
# VM options: -Xms2G -Xmx2G
# Warmup: 3 iterations, 10 s each
# Measurement: 8 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.learningjava.JMHDemo.BenchmarkLoop.loopWhile
# Parameters: (N = 10000000)

# Run progress: 87.50% complete, ETA 00:01:50
# Fork: 1 of 1
# Warmup Iteration   1: 57.523 ms/op
# Warmup Iteration   2: 57.889 ms/op
# Warmup Iteration   3: 58.503 ms/op
Iteration   1: 57.968 ms/op
Iteration   2: 58.066 ms/op
Iteration   3: 56.682 ms/op
Iteration   4: 56.437 ms/op
Iteration   5: 56.805 ms/op
Iteration   6: 56.564 ms/op
Iteration   7: 56.720 ms/op
Iteration   8: 56.869 ms/op


Result "com.learningjava.JMHDemo.BenchmarkLoop.loopWhile":
  57.014 ±(99.9%) 1.212 ms/op [Average]
  (min, avg, max) = (56.437, 57.014, 58.066), stdev = 0.634
  CI (99.9%): [55.802, 58.226] (assumes normal distribution)


# Run complete. Total time: 00:14:47

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                (N)  Mode  Cnt   Score    Error  Units
JMHDemo.BenchmarkLoop.loopFor          10000  avgt    8   0.042 ±  0.001  ms/op
JMHDemo.BenchmarkLoop.loopFor       10000000  avgt    8  55.129 ±  1.060  ms/op
JMHDemo.BenchmarkLoop.loopForEach      10000  avgt    8   0.047 ±  0.001  ms/op
JMHDemo.BenchmarkLoop.loopForEach   10000000  avgt    8  59.210 ±  1.173  ms/op
JMHDemo.BenchmarkLoop.loopIterator     10000  avgt    8   0.047 ±  0.001  ms/op
JMHDemo.BenchmarkLoop.loopIterator  10000000  avgt    8  62.873 ±  5.035  ms/op
JMHDemo.BenchmarkLoop.loopWhile        10000  avgt    8   0.042 ±  0.001  ms/op
JMHDemo.BenchmarkLoop.loopWhile     10000000  avgt    8  57.014 ±  1.212  ms/op
