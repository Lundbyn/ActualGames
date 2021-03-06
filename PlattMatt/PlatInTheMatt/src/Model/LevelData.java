package Model;


/**
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class LevelData {


    /**
     * The different blocks create different types of block
     * 0 = Empty space
     * 1 = Platform
     * 2 = Spikes
     * 3 = End block
     * 4 = Red enemy (Horizontal movement)
     * 5 = Red enemy (Vertical movement)
     * 6 = Blue enemy (Horizontal movement)
     * 7 = Blue enemy (Vertical movement)
     * 9 = Boss level
     */
    public static final String[][] LEVELS = new String[][] {
            {       //LEVEL 1 (Tutorial level)
                    "000000000000000000000000000",
                    "000000000000000000000000000",
                    "000000000000000000000000000",
                    "000000000000000000000000000",
                    "000000000000000000000000000",
                    "000000000000000000000000000",
                    "000000000000000000050000003",
                    "000000000004000000000000003",
                    "000000000011111100000600003",
                    "111001122111111111000111111",
                    "000000000000000000000000000"
            },

            {       //LEVEL 2
                    "000000000000000000000000001",
                    "000000000000000000000000001",
                    "000000000000000000000000001",
                    "111000000000000000000000001",
                    "000000000000000020000000071",
                    "000000000006000020000000003",
                    "111000000111111000000000003",
                    "000000000000000000000000001",
                    "000000000000000000000000001",
                    "111000000000000000011111111",
                    "000000000000000000000000000"
            },

            {       //LEVEL 3
                    "000000000000000000000001",
                    "000000000000000000000001",
                    "000000000000000005050001",
                    "000000000000000050505001",
                    "000000000000000005050001",
                    "000000000000000000000001",
                    "000040400001111100000001",
                    "000404040001000000000001",
                    "000040400001000000001331",
                    "111111111111000000001331",
                    "000000000000000000001111"
            },

            {       //LEVEL 4
                    "0000000000000000000000000000000000000000001",
                    "0000000000000000000000000000000000000000001",
                    "1333310000000200000000000000000000011100001",
                    "1111111110001010011221122112211000010070001",
                    "0000000000000000000000000000000000010000001",
                    "0000000000000000000000000000000000010001111",
                    "0000000000000000040000006000000000010000000",
                    "0000050000000000011111111111111000000000000",
                    "0000000000011000000000000000000000011100000",
                    "1100001100000000000000000000000000000000000",
                    "0000000000000000000000000000000000000000000"
            },

            {       //LEVEL 5 (Boss level)
                    "000000000000000000000000001",
                    "000000000000000000000000001",
                    "000000000000000000000000001",
                    "000011000000000000001100001",
                    "000000000000000000000000001",
                    "000000000000000000000000001",
                    "000011000000000000001100001",
                    "000000000000000000000000001",
                    "000000000000000000000000001",
                    "111111111111111111111111111",
                    "9"
            },
    };
}