package edu.grinnell.csc207.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    byte[] hashData;

    /**
     * Constructor which initializes data into object
     *
     * @param data
     * @throws NoSuchAlgorithmException
     */
    public Hash(byte[] data) throws NoSuchAlgorithmException {

        hashData = data;
   
    }

    /**
     * Method returns the hash contained in the object as an array of bytes
     *
     * @return true or false
     */
    public byte[] getData() {
        return hashData;
    }

    /**
     * Method return true if hash meets criteria for validity: HAsh starts with
     * three zeroes
     *
     * @return
     */
    public boolean isValid() {
        byte[] hashStore = getData();
        boolean returnValue = true;

        for (int i = 0; i < 3; i++) {
            if (hashStore[i] != 0) {
                returnValue = false;
                break;
            }
        }

        return returnValue;
    }

    /**
     * Helper function to get hash data from object but replaces it after.
     *
     * @return
//mi
    /**
     * This returns a string representation of the hash in hexadecimal
     *
     * @return
     */
    @Override
    public String toString() {
        byte[] hashStore = getData();

        //Credit:https://stackoverflow.com/a/2817883
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < hashStore.length; i++) {
            sb.append(String.format("%02x", Byte.toUnsignedInt(hashStore[i])));
        }

        return sb.toString();
    }

    /**
     * Returns true if this hash is structurally equal to the argument
     *
     * @param other
     */
    @Override
    public boolean equals(Object other) {
        //cherck if it is a hash object
        if (!(other instanceof Hash)) {
            return false;
        }

        //Cast and validate
        Hash o = (Hash) other;

        //concise check and return
        return Arrays.equals(o.getData(), getData());
    }
}
