package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    byte[] hashData;

    /**
     * Constructor which initializes data into object.
     *
     * @param data the byte array representing the hash
     * @throws NoSuchAlgorithmException if SHA-256 is not available
     */
    public Hash(byte[] data) throws NoSuchAlgorithmException {
        hashData = data;
    }

    /**
     * Returns the hash contained in the object as an array of bytes.
     *
     * @return the raw byte array of the hash
     */
    public byte[] getData() {
        return hashData;
    }

    /**
     * Returns true if the hash is valid. A hash is valid if its first three
     * bytes are all zero.
     *
     * @return true if hash is valid, false otherwise
     */
    public boolean isValid() {
        byte[] hashStore = getData();

        //length is not at least 3
        if (hashStore.length < 3) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (hashStore[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the hash in hexadecimal.
     *
     * @return a hex string representation of the hash
     */
    @Override
    public String toString() {
        byte[] hashStore = getData();

        // Credit: https://stackoverflow.com/a/2817883
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashStore.length; i++) {
            sb.append(String.format("%x", Byte.toUnsignedInt(hashStore[i])));
        }

        return sb.toString();
    }

    /**
     * Returns true if this hash is structurally equal to the argument.
     *
     * @param other the object to compare against
     * @return true if the hashes are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Hash)) {
            return false;
        }

        Hash o = (Hash) other;
        return Arrays.equals(o.getData(), getData());
    }
}
