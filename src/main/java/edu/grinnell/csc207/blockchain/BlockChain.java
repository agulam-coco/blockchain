package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    private Node first;
    private Node last;
    private int size;

    private class Node {

        private Block data;
        private Node next;

        public Node(Block data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Initialize first block in chain
     *
     * @param initial
     * @throws NoSuchAlgorithmException
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, initial, null);

        first = new Node(firstBlock);
        last = first;

        size = 0;
    }

    /**
     * Mines a new candidate block to be added to the list.
     *
     * @param amount
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        return new Block(getSize(), amount, getHash());
    }

    public int getSize() {
        return this.size;
    }

    public Hash getHash() {
        return last.data.getHash();
    }

    /**
     * adds this block to the list,
     * throwing an IllegalArgumentException if this block cannot be added to the list 
     * (because it is invalid wrt the rest of the blocks).
     * @param blk 
     */
    public void append(Block blk) {
        //do and ask for forgiveness
        
        Node newLastNode = new Node(blk);
        last.next = newLastNode;
        last = newLastNode;

        if (!isValidBlockChain()) {
            removeLast();
            throw new IllegalArgumentException("Block is not valid");
        }
    }

    /**
     * walks the blockchain and ensures that its blocks are consistent and valid.
     * @return 
     */
    public boolean isValidBlockChain() {
        //TODO 
        return false;
    }

    /**
     * removes the last block from the chain, returning true. 
     * If the chain only contains a single block, 
     * then removeLast does nothing and returns false.
     * @return 
     */
    public boolean removeLast() {
        //TO DO 
        return true;
    }

    /**
     * prints Alice's and Bob's respective balances in the form 
     * Alice: <amt>, Bob: <amt> on a single line, 
     * e.g., Alice: 300, Bob: 0.
     */
    public void printBalances(){
        //TO DO
    }
    
    /**
     * Returns a string representation of the BlockChain which is simply
     * the string representation of each of its blocks, earliest to latest, one per line.
     * @return 
     */
    @Override
    public String toString(){
        return "TO DO";
    }
}
