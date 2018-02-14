import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {

    private static HashMap<Integer, Double> accountStore = new HashMap<>();
    private static Double buffer = 0.0;
    private static boolean cond = true;
    private static String input;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (cond) {
            try {
                input = reader.readLine();
                String[] command = input.split(" ");
                if (validation(command)) continue;

                switch (command[0]) {
                    case "NEWACCOUNT" :
                        newAccount(command);
                        break;
                    case "DEPOSIT" :
                        deposit(command);
                        break;
                    case "WITHDRAW" :
                        withdraw(command);
                        break;
                    case "BALANCE" :
                        balance(command);
                        break;
                    default:
                        System.out.println("ERROR");
                }
            }

            catch (Exception e) {
                System.out.println("ERROR");;
                }
        }
    }

    private static boolean validation(String[] command) {
        try {
            if ( ((command.length == 2)&&(command[0].equals("NEWACCOUNT"))&&(command[1].length() == 5)) || ((command.length == 2)&&(command[0].equals("BALANCE"))&&(command[1].length() == 5)) ) {
                Integer.parseInt(command[1]);
            }
            if ( ((command.length == 3)&&(command[0].equals("DEPOSIT"))&&(command[1].length() == 5)) || ((command.length == 3)&&(command[0].equals("WITHDRAW"))&&(command[1].length() == 5)) ) {
                Integer.parseInt(command[1]);
                Double.parseDouble(command[2]);
            }

        } catch (Exception e) {
            System.out.println("ERROR");
            return true; //команды должны валидировать входные параметры
        }
        return false; //программа не должна "упасть" на некорректных данных
    }


    private static void balance(String[] command) {
        if (!accountStore.containsKey(Integer.parseInt(command[1]))) {
            System.out.println("ERROR");
            return; //перед тем как проводить операции со счетом, его надо зарегистрировать
        }

        System.out.println(accountStore.get(Integer.parseInt(command[1])));
    }

    private static void withdraw(String[] command) {
        if (!accountStore.containsKey(Integer.parseInt(command[1]))) {
            System.out.println("ERROR");
            return; //перед тем как проводить операции со счетом, его надо зарегистрировать
        }

        if (Double.parseDouble(command[2]) < 0 ) {
            System.out.println("ERROR");
            return; //нельзя класть на счёт и снимать отрицательные суммы
        }

        buffer = Double.parseDouble(command[2]);
        double value = accountStore.get(Integer.parseInt(command[1])) - buffer;
        if (value < 0) {
            System.out.println("ERROR");
            return; // значение счета не может опускаться ниже 0
        }

        accountStore.put(Integer.parseInt(command[1]), value);
        System.out.println("OK");
    }

    private static void deposit(String[] command) {
        if (!accountStore.containsKey(Integer.parseInt(command[1]))) {
            System.out.println("ERROR");
            return; //перед тем как проводить операции со счетом, его надо зарегистрировать
        }

        if (Double.parseDouble(command[2]) < 0 ) {
            System.out.println("ERROR");
            return; //нельзя класть на счёт и снимать отрицательные суммы
        }

        double value = accountStore.get(Integer.parseInt(command[1]));
        accountStore.put(Integer.parseInt(command[1]), value + Double.parseDouble(command[2]));
        System.out.println("OK");
    }

    private static void newAccount(String[] command) {
        if (accountStore.containsKey(Integer.parseInt(command[1]))) {
            System.out.println("ERROR");
            return; //нельзя завести 2 раза номер с одним номером
        }

        accountStore.put(Integer.parseInt(command[1]), 0.0);
        System.out.println("OK");
    }



}
