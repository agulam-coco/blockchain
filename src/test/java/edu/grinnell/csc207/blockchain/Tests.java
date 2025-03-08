package edu.grinnell.csc207.blockchain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

public class Tests{

    private Block block1;
    private Block block2;
    private Hash prevHash;
    private Hash newPrevHash;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        prevHash = new Hash(new byte[32]); 
        block1 = new Block(1, 100, prevHash);
        newPrevHash = new Hash(new byte[32]); 
        block2 = new Block(2, 200, newPrevHash);
    }

    @Test
    void testBlockInitializationWithValidParams() {
        assertNotNull(block1, "Block should be initialized correctly.");
        assertEquals(1, block1.getNum(), "Block number should be 1.");
        assertEquals(100, block1.getAmount(), "Block amount should be 100.");
        assertEquals(prevHash, block1.getPrevHash(), "Previous hash should be the same.");
        assertTrue(block1.getNonce() >= 0, "Nonce should be non-negative.");
        assertNotNull(block1.getHash(), "Block hash should be computed.");
    }

    @Test
    void testBlockInitializationWithNonce() throws NoSuchAlgorithmException {
        long nonce = 1000;
        Block blockWithNonce = new Block(3, 300, prevHash, nonce);
        
        assertEquals(nonce, blockWithNonce.getNonce(), "Nonce should match the one passed during initialization.");
        assertNotNull(blockWithNonce.getHash(), "Block hash should be computed with the provided nonce.");
    }

//    @Test
//    void testComputeHash() throws NoSuchAlgorithmException {
//        Hash computedHash = Block.computeHash(1, 100, prevHash, 0, 128);
//        
//        assertNotNull(computedHash, "Computed hash should not be null.");
//        assertTrue(computedHash.getData().length > 0, "Computed hash data should have a length greater than zero.");
//    }

    @Test
    void testMineBlock() throws NoSuchAlgorithmException {
        Block minedBlock = new Block(4, 400, prevHash);
        
        assertTrue(minedBlock.getNonce() >= 0, "Nonce should be non-negative.");
        assertNotNull(minedBlock.getHash(), "Block hash should be computed after mining.");
        assertTrue(minedBlock.getHash().isValid(), "The mined block hash should be valid.");
    }

    @Test
    void testGetters() {
        assertEquals(1, block1.getNum(), "Block number getter should return correct value.");
        assertEquals(100, block1.getAmount(), "Amount getter should return correct value.");
        assertEquals(prevHash, block1.getPrevHash(), "Previous hash getter should return correct value.");
        assertNotNull(block1.getHash(), "Hash getter should return computed hash.");
    }

    @Test
    void testToString() {
        String blockString = block1.toString();
        assertTrue(blockString.contains("Amount: 100"), "String representation should contain the correct amount.");
        assertTrue(blockString.contains("Nonce: "), "String representation should contain nonce.");
        assertTrue(blockString.contains("prevHash: " + prevHash.toString()), "String representation should contain the previous hash.");
        assertTrue(blockString.contains("hash: "), "String representation should contain the hash.");
    }

    @Test
    void testEdgeCaseForFirstBlock() throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, 0, null); 
        assertEquals(0, firstBlock.getNum(), "First block number should be 0.");
        assertEquals(0, firstBlock.getAmount(), "Amount in the first block should be 0.");
        assertNull(firstBlock.getPrevHash(), "First block should have no previous hash.");
    }
    
    @Test
    void testInvalidHash() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hash(new byte[0]);  
        });
    }
}
