package com.sanjuthomas.reactive.examples;

import java.util.function.Consumer;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ConnectableFlowableExample {

  public static void main(String args[]) throws InterruptedException {
    final ConnectableFlux<Integer> connectableFlux = Flux.fromArray(new Integer[] {1, 2, 3, 4}).publish();
    connectableFlux.subscribe(new Consumer45th());
    connectableFlux.subscribe(new Consumer47th());
    printSleep(2000);
    connectableFlux.connect();
    printSleep(2000);
  }

  static void printSleep(int duration) throws InterruptedException {
    int counter = 0;
    while(counter < duration) {
      System.out.print(".");
      Thread.sleep(100);
      counter += 100;
    }
    System.out.print(".\n");
  }

  static final class Consumer47th implements Consumer<Integer> {
    private static final String who = "47th";
    public void accept(Integer data) {
      System.out.println(who +": "+ data);
    }
  }

  static final class Consumer45th implements Consumer<Integer> {
    private static final String who = "45th";
    public void accept(Integer data) {
      System.out.println(who +": " + data);
    }
  }
}
