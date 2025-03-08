package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    private final Node first;
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

        size = 1;
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

    /**
     * returns the size of the blockchain.
     *
     * @return
     */
    public int getSize() {
        return this.size;
    }

    /**
     * returns the hash of the last block in the chain.
     *
     * @return
     */
    public Hash getHash() {
        return last.data.getHash();
    }

    /**
     * adds this block to the list, throwing an IllegalArgumentException if this
     * block cannot be added to the list (because it is invalid with the rest of
     * the blocks).
     *
     * @param blk
     */
    public void append(Block blk) {
        //do and ask for forgiveness

        if (blk.getHash().isValid() && blk.getPrevHash().equals(last.data.getHash())) {
            Node newLastNode = new Node(blk);
            last.next = newLastNode;
            last = newLastNode;
            this.size++;
        } else {
            throw new IllegalArgumentException("Block is not valid");
        }

    }

    /**
     * walks the blockchain and ensures that its blocks are consistent and
     * valid.
     *
     * @return
     */
    public boolean isValidBlockChain() {
        //begin chain from swecond node
        Node curr = first.next;

        //begin balance from first node
        int balance = first.data.getAmount();
        
        Hash prevHash = first.data.getHash();

        while (curr != null) {
            //validate hash data
            if (!curr.data.getHash().isValid()) {
                return false;
            }

            balance += curr.data.getAmount();
            System.out.println(balance);
            
            //validate transaction amount
            if (balance < 0) {
                return false;

            }
            
            //validate hash and previous hash
            if(!curr.data.getPrevHash().equals(prevHash)){
                return false;
            }
            
            prevHash = curr.data.getHash();
            curr=curr.next;
            
            
        }
        return true;
    }

    /**
     * removes the last block from the chain, returning true. If the chain only
     * contains a single block, then removeLast does nothing and returns false.
     *
     * @return
     */
    public boolean removeLast() {
        if (getSize() == 1) {
            return false;
        }

        Node prev = first;
        Node curr = first.next;

        while (!curr.equals(last)) {
            prev = curr;
            curr = curr.next;
        }

        this.last = prev;
        prev.next = null;

        return true;
    }

    /**
     * prints Alice's and Bob's respective balances in the form Alice: <amt>,
     * Bob: <amt> on a single line, e.g., Alice: 300, Bob: 0.
     */
    public void printBalances() {
        Node curr = first;
        int aliceBalance = 0;
        int bobBalance = 0;

        while (curr != null) {
            int temp = curr.data.getAmount();

            if (temp >= 0) {
                aliceBalance += temp;
            } else {
                bobBalance -= temp;
            }
            curr = curr.next;
        }

        System.out.println(String.format("Alice: %d, Bob: %d", aliceBalance, bobBalance));
    }

    /**
     * Returns a string representation of the BlockChain which is simply the
     * string representation of each of its blocks, earliest to latest, one per
     * line.
     *
     * @return
     */
    @Override
    public String toString() {

        Node curr = first;
        final StringBuilder sb = new StringBuilder();
        while (curr != null) {
            sb.append(String.format("Block: %d (%s)\n", curr.data.getNum(), curr.data.toString()));
            curr = curr.next;
        }
        return sb.toString();
    }

}
