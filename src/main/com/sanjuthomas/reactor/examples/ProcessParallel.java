package com.sanjuthomas.reactor.examples;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ProcessParallel {

  public static void main(String[] args) {
    final Flux<Result>  factorials = Flux.range(10000, 64)
      .parallel()
      .runOn(Schedulers.boundedElastic())
      .flatMap(new FactorialFunction()).sequential();
    final List<Result> results = factorials.buffer().blockLast();
    results.forEach(result -> System.out.println(result));
  }


  static class Result {
    private final String thread;
    private final BigInteger result;

    public Result(String thread, BigInteger result) {
      this.thread = thread;
      this.result = result;
    }

    @Override
    public String toString() {
      return this.thread + ":" + this.result;
    }
  }
  static class FactorialFunction implements Function<Integer, Flux<Result>> {
    @Override
    public Flux<Result> apply(Integer number) {
      BigInteger result = BigInteger.ONE;
      for (int i = 2; i <= number; i++) {
        result = result.multiply(BigInteger.valueOf(i));
      }
      return Flux.just(new Result(Thread.currentThread().getName(), result));
    }
  }

}
