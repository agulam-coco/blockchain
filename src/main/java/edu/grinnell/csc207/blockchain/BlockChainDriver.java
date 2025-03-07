package edu.grinnell.csc207.blockchain;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {
   
    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     */
    public static void main(int argc, String[] args) {
        
       if(argc!= 1){
           printUsage();
           System.exit(1);
       }
        System.out.println("Hello world!");
        // TODO: fill me in!
    } 
    
    /**
     * Outputs the usage of the function to the terminal
     */
    private static void printUsage(){
        System.out.println("Usage: java BlockChainDriver <int amount>");
    }
    
    /**
     * Outputs the help menu to the terminal for the user.
     */
    private static void printHelp(){
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
