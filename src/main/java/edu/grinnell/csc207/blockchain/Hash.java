package edu.grinnell.csc207.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    MessageDigest md;

    /**
     * Constructor which initializes data into object
     *
     * @param data
     * @throws NoSuchAlgorithmException
     */
    public Hash(byte[] data) throws NoSuchAlgorithmException {

        //fill data into digest
        md = MessageDigest.getInstance("sha-256");
        md.update(data);
    }

    /**
     * Method returns the hash contained in the object as an array of bytes
     *
     * @return true or false
     */
    public byte[] getData() {
        return md.digest();
    }

    /**
     * Method return true if hash meets criteria for validity: HAsh starts with
     * three zeroes
     *
     * @return
     */
    public boolean isValid() {
        boolean returnValue = true;
        
        //get hash data in store. md now empty.
        byte[] hashStore = md.digest();
        
        for (int i = 0; i < 3; i++) {
            if (hashStore[i] != 0 ) {
                returnValue = false;
                break;
            }
        }
        
        //put hash abck into the array
        md.update(hashStore);
        return returnValue;
    }
}
