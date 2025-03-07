package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {

    private int num;
    private int amount;
    private Hash prevHash;
    private long nonce;
    private Hash blockHash;

    /**
     * Instantiates Block
     *
     * @param num
     * @param amount
     * @param prevHash
     * @throws java.security.NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        mineBlock();
    }

    /**
     * Initiates block given nonce
     *
     * @param num
     * @param amount
     * @param prevHash
     * @param nonce
     * @throws NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {

        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.blockHash = computeHash(num, amount, prevHash, nonce, getBufferSize());
    }

    /**
     * Computes the hash of a block provided the information.
     *
     * @param num
     * @param amount
     * @param prevHash
     * @param nonce
     * @param bufferSize
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Hash computeHash(int num, int amount, Hash prevHash, long nonce, int bufferSize) throws NoSuchAlgorithmException {
        //ternary return hash based on if it is first block or not
        //credit: https://stackoverflow.com/a/7693344

        return (num != 0
                ? new Hash(ByteBuffer.allocate(bufferSize)
                        .putInt(num)
                        .putInt(amount)
                        .putLong(Long.parseLong(prevHash.toString()))
                        .putLong(nonce)
                        .array())
                : new Hash(ByteBuffer.allocate(bufferSize)
                        .putInt(num)
                        .putInt(amount)
                        .putLong(nonce)
                        .array()));
    }

    /**
     * Calculates the needed buffer size for byte array based on block number
     *
     * @return
     */
    private int getBufferSize() {
       // return 250;
       return (getNum() != 0 ? (2 * (Integer.SIZE /8)) + (2 * (Long.SIZE/8)) :(2 * (Integer.SIZE /8)) + (Long.SIZE/8));
    }

    /**
     * Mines the nonce value and assigns the new nonce value and associated hash
     *
     * @throws NoSuchAlgorithmException
     */
    private void mineBlock() throws NoSuchAlgorithmException {
        int bufferSize;

        //credit: https://stackoverflow.com/a/51718622
        // it is not the first block allocate space for two intergers and two longs else allocate for two integers and one long
        bufferSize = getBufferSize();

        //noce begins at -1 but increments to 0 due to do while loop
        long nonce = -1;

        //temporary hash var for calculation
        Hash newHash;
        do {
            nonce++;

            newHash = computeHash(getNum(), getAmount(), getPrevHash(), nonce, bufferSize);

        } while (!newHash.isValid());

        
        //perform assignments
        this.nonce = nonce;
        this.blockHash = newHash;
    }

    /**
     * Returns the number of the block
     *
     * @return
     */
    public int getNum() {
        return num;
    }

    /**
     * Returns the amount transferred that is recorded in the current block
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the nonce value of the current block
     *
     * @return
     */
    public long getNonce() {
        return nonce;
    }

    /**
     * Returns the hash of the previous block
     *
     * @return
     */
    public Hash getPrevHash() {
        return prevHash;
    }

    /**
     * Returns the hash of the current block
     *
     * @return
     */
    public Hash getHash() {
        return blockHash;
    }

    /**
     * Returns a string representation of the block of the form: Block <num>
     * (Amount: <amt>, Nonce: <nonce>, prevHash: <prevHash>, hash: <hash>)
     *
     * @return
     */
    @Override
    public String toString() {
        byte[] temp = getHash().getDataReplace();
        System.out.println(String.format("%d %d %d %d %d %d", temp[0] , temp[1], temp[2] , temp[3] , temp[4] , temp[5]));
        return String.format("Amount: %d, None: %d, prevHash: %s, hash: %s", getAmount(), getNonce(), getPrevHash() == null? "null": getPrevHash().toString(), getHash().toString());
    }

}
