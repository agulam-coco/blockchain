package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     *
     * @param args the command-line arguments
     * @throws java.security.NoSuchAlgorithmException if SHA-256 is not
     * available
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (args.length != 1) {
            printUsage();
            System.exit(1);
        }

        BlockChain blockChain = null;

        try {
            int number = Integer.parseInt(args[0]);
            if (number < 0) {
                throw new IllegalArgumentException(
                        "Initial balance cannot be negative"
                );
            }
            blockChain = new BlockChain(number);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: Please enter a valid integer.");
            printUsage();
            System.exit(2);
        }

        boolean programRunning = true;
        Scanner scanner = new Scanner(System.in);
        Block minedBlock = null;

        while (programRunning) {
            System.out.print(blockChain.toString());
            String command = getValidString(scanner, "Command? ");

            switch (command) {
                case "quit" ->
                    programRunning = false;

                case "help" ->
                    printHelp();

                case "mine" -> {
                    int amount = getValidInt(scanner, "Amount transferred? ");
                    minedBlock = blockChain.mine(amount);
                    System.out.println(
                            String.format(
                                    "amount = %d, nonce = %d\n",
                                    amount,
                                    minedBlock.getNonce()
                            )
                    );
                }

                case "append" -> {
                    if (minedBlock == null) {
                        System.out.println(
                                "No blocks have been mined. Mine one first!\n"
                        );
                    } else {
                        blockChain.append(minedBlock);
                        System.out.println(
                                String.format(
                                        "Amount Transferred? %d \nNonce? %d\n",
                                        minedBlock.getAmount(),
                                        minedBlock.getNonce()
                                )
                        );
                        minedBlock = null;
                    }
                }

                case "report" ->
                    blockChain.printBalances();

                case "check" -> {
                    String output = blockChain.isValidBlockChain()
                            ? "Chain is valid!\n"
                            : "Chain is not valid\n";
                    System.out.println(output);
                }

                case "remove" -> {
                    blockChain.removeLast();
                    System.out.println();
                }

                default ->
                    System.out.println("Invalid command.\n");
            }
        }

        scanner.close();
    }

    /**
     * Prompts the user for a valid integer. Reprompts if input is invalid.
     *
     * @param scanner the scanner to read from
     * @param prompt the prompt to show the user
     * @return a valid integer input
     */
    public static int getValidInt(Scanner scanner, String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // discard non-integer input
            }
        }
    }

    /**
     * Prompts the user for a valid string. Reprompts if input is invalid.
     *
     * @param scanner the scanner to read from
     * @param prompt the prompt to show the user
     * @return a valid string input
     */
    public static String getValidString(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.matches("[a-zA-Z\\s]+")) {
                return input;
            } else {
                System.out.println(
                        "Invalid input. Please enter a valid name "
                        + "(letters and spaces only)."
                );
            }
        }
    }

    /**
     * Outputs the usage instructions to the terminal.
     */
    private static void printUsage() {
        System.out.println("Usage: java BlockChainDriver <int amount>");
    }

    /**
     * Outputs the help menu to the terminal for the user.
     */
    private static void printHelp() {
        System.out.println("""
            Valid commands:
                mine: discovers the nonce for a given transaction
                append: appends a new block onto the end of the chain
                remove: removes the last block from the end of the chain
                check: checks that the block chain is valid
                report: reports the balances of Alice and Bob
                help: prints this list of commands
                quit: quits the program
            """);
    }
}
