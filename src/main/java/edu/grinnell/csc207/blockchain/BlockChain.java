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
     * Initialize first block in chain.
     *
     * @param initial the initial amount
     * @throws NoSuchAlgorithmException if SHA-256 is not available
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
     * @param amount the amount to transfer
     * @return the mined block
     * @throws NoSuchAlgorithmException if SHA-256 is not available
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        return new Block(getSize(), amount, getHash());
    }

    /**
     * Returns the size of the blockchain.
     *
     * @return number of blocks in the chain
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the hash of the last block in the chain.
     *
     * @return hash of the last block
     */
    public Hash getHash() {
        return last.data.getHash();
    }

    /**
     * Adds this block to the list, throwing an IllegalArgumentException if this
     * block cannot be added (invalid hash or previous hash mismatch).
     *
     * @param blk the block to append
     */
    public void append(Block blk) {
        if (blk.getHash().isValid()
                && blk.getPrevHash().equals(last.data.getHash())) {

            Node newLastNode = new Node(blk);
            last.next = newLastNode;
            last = newLastNode;
            this.size++;
        } else {
            throw new IllegalArgumentException("Block is not valid");
        }
    }

    /**
     * Walks the blockchain and ensures that its blocks are consistent and
     * valid.
     *
     * @return true if valid, false otherwise
     */
    public boolean isValidBlockChain() {
        Node curr = first.next;
        int balance = first.data.getAmount();
        int startingbalance = balance;
        Hash prevHash = first.data.getHash();

        while (curr != null) {
            if (!curr.data.getHash().isValid()) {
                return false;
            }

            balance += curr.data.getAmount();

            if (balance < 0) {
                return false;
            }
            if (balance > startingbalance) {
                return false;
            }

            if (!curr.data.getPrevHash().equals(prevHash)) {
                return false;
            }

            prevHash = curr.data.getHash();
            curr = curr.next;
        }

        return true;
    }

    /**
     * Removes the last block from the chain, returning true. If the chain only
     * contains a single block, then removeLast does nothing and returns false.
     *
     * @return true if a block was removed, false otherwise
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
     * Prints Alice's and Bob's respective balances in the form:
     * Alice: &lt;amt&gt;, Bob: &lt;amt&gt;
     */
    public void printBalances() {
        Node curr = first;
        int aliceBalance = curr.data.getAmount();
        int bobBalance = 0;

        curr = curr.next;

        while (curr != null) {
            int temp = curr.data.getAmount();
            aliceBalance += temp;
            bobBalance -= temp;
            curr = curr.next;
        }

        System.out.println(
                String.format("Alice: %d, Bob: %d", aliceBalance, bobBalance)
        );
    }

    /**
     * Returns a string representation of the BlockChain.
     *
     * @return formatted string of each block, one per line
     */
    @Override
    public String toString() {
        Node curr = first;
        final StringBuilder sb = new StringBuilder();
        while (curr != null) {
            sb.append(
                    String.format(
                            "Block: %d (%s)\n",
                            curr.data.getNum(),
                            curr.data.toString()
                    )
            );
            curr = curr.next;
        }
        return sb.toString();
    }
}
