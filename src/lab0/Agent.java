package lab0;

import java.io.FileNotFoundException;

// The agent is the command line interface
public class Agent {
    public static void main(String[] argv) throws FileNotFoundException {
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
                .println("\tTo send message, you can type: send bob MESSAGE_KIND CONTENT<RETURN>");
        System.out
                .println("\tTo receive messages, you can type: receive<RETURN>");
        System.out
        .println("\tTo receive messages, you can type: quit<RETURN>");
        //
        // while (true) {
        //
        // }
    }
}
