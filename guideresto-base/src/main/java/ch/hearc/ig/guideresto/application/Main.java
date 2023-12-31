package ch.hearc.ig.guideresto.application;

import ch.hearc.ig.guideresto.persistence.*;
import ch.hearc.ig.guideresto.presentation.CLI;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    var scanner = new Scanner(System.in);
    var printStream = System.out;

    var cli = new CLI(scanner, printStream);

    cli.start();
  }
}
