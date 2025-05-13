package edu.grinnell.csc207.blockchain;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
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
     * Instantiates Block.
     *
     * @param num       the block number
     * @param amount    the amount transferred
     * @param prevHash  the hash of the previous block
     * @throws java.security.NoSuchAlgorithmException if SHA-256 is not available
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        mineBlock();
    }

    /**
     * Instantiates Block given a nonce.
     *
     * @param num        the block number
     * @param amount     the amount transferred
     * @param prevHash   the hash of the previous block
     * @param nonce      the nonce to be used
     * @throws NoSuchAlgorithmException if SHA-256 is not available
     */
    public Block(int num, int amount, Hash prevHash, long nonce)
            throws NoSuchAlgorithmException {

        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.blockHash =
            computeHash(num, amount,new BigInteger(prevHash.toString(), 16), nonce, getBufferSize());
    }

    /**
     * Computes the hash of a block provided the information.
     *
     * @param num         the block number
     * @param amount      the amount transferred
     * @param prevHash    the hash of the previous block
     * @param nonce       the nonce value
     * @param bufferSize  the buffer size for byte array
     * @return the hash of the block
     * @throws NoSuchAlgorithmException if SHA-256 is not available
     */
    private static Hash computeHash(
        int num, int amount, BigInteger prevLong, long nonce, int bufferSize
    ) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");

        if (num != 0) {
            md.update(
                ByteBuffer.allocate(bufferSize)
                    .putInt(num)
                    .putInt(amount)
                    .putLong(prevLong.longValue())
                    .putLong(nonce)
                    .array()
            );
        } else {
            md.update(
                ByteBuffer.allocate(bufferSize)
                    .putInt(num)
                    .putInt(amount)
                    .putLong(nonce)
                    .array()
            );
        }

        return new Hash(md.digest());
    }

    /**
     * Calculates the needed buffer size for byte array based on block number.
     *
     * @return the required buffer size
     */
    private int getBufferSize() {
        return (getNum() != 0)
            ? (2 * (Integer.SIZE / 8)) + (2 * (Long.SIZE / 8))
            : (2 * (Integer.SIZE / 8)) + (Long.SIZE / 8);
    }

    /**
     * Mines the nonce value and assigns the new nonce value and associated hash.
     *
     * @throws NoSuchAlgorithmException if SHA-256 is not available
     */
    private void mineBlock() throws NoSuchAlgorithmException {
        int bufferSize = getBufferSize();
        long nonce = -1;
        Hash newHash;

        BigInteger prevLong = null;
    if (getNum() != 0 && getPrevHash() != null) {
        prevLong = new BigInteger(getPrevHash().toString(), 16);
    }
        do {
            nonce++;
            newHash = computeHash(
                getNum(), getAmount(), prevLong, nonce, bufferSize
            );
        } while (!newHash.isValid());

        this.nonce = nonce;
        this.blockHash = newHash;
    }

    /**
     * Returns the number of the block.
     *
     * @return the block number
     */
    public int getNum() {
        return num;
    }

    /**
     * Returns the amount transferred that is recorded in the current block.
     *
     * @return the transferred amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the nonce value of the current block.
     *
     * @return the nonce value
     */
    public long getNonce() {
        return nonce;
    }

    /**
     * Returns the hash of the previous block.
     *
     * @return the previous block's hash
     */
    public Hash getPrevHash() {
        return prevHash;
    }

    /**
     * Returns the hash of the current block.
     *
     * @return the current block's hash
     */
    public Hash getHash() {
        return blockHash;
    }

    /**
     * Returns a string representation of the block.
     *
     * @return formatted string with block data
     */
    @Override
    public String toString() {
        return String.format(
            "Amount: %d, Nonce: %d, prevHash: %s, hash: %s",
            getAmount(),
            getNonce(),
            getPrevHash() == null ? "null" : getPrevHash().toString(),
            getHash().toString()
        );
    }
}
