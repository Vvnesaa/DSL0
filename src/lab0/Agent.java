package lab0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// The agent is the command line interface
public class Agent {
    public static void main(String[] argv) throws IOException {
        if (argv.length < 2) {
            System.out
                    .println("Please provide at least two arguments: <Config File> <Name>");
            return;
        }

        String configPath = argv[0];
        String name = argv[1];
        MessagePasser proxy = new MessagePasser(configPath, name);

        System.out.println("INSTRUCTIONS\n========================");
        System.out
                .println("\tTo send message, type: send RECEIVER MESSAGE_KIND CONTENT<RETURN>");
        System.out.println("\tTo receive messages, type: receive<RETURN>");

        System.out.println("\tTo receive all message, type: all<RETURN>");
        System.out.println("\tTo quit, type: quit<RETURN>");

        boolean shouldContinue = true;
        while (shouldContinue) {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    System.in));
            String line = buffer.readLine();

            if (normalize(line).startsWith("quit")) {
                System.out.print("quit");
                shouldContinue = false;
            } else if (normalize(line).startsWith("receive")) {
                Message message = proxy.nonblockReceive();
                int counter = 0;
                while (message != null) {
                    System.out.println("" + (++counter) + ": " + message);
                    message = proxy.nonblockReceive();
                }
                System.out.println("<end-of-new-message>");
            } else if (normalize(line).startsWith("all")) {
                Message message = proxy.nonblockReceive();
                if (message != null) {
                    System.out.println(message);
                }
                System.out.println("<end-of-new-message>");
            } else if (normalize(line).startsWith("send")) {
                String[] parts = line.split("\\s+", 4);
                Message message = new Message(name, parts[1], parts[2],
                        parts[3]);
                proxy.send(message);
            } else {
                System.out.println("Unsupport action");
            }
        }

    }

    private static String normalize(String s) {
        return s;
    }
}
