package com.jaeheonshim.ersgame.game.model;

public enum CardType {
    BACK("back", -1),
    CLOVER_A("Clovers_A_white", 1),
    CLOVER_2("Clovers_2_white", 2),
    CLOVER_3("Clovers_3_white", 3),
    CLOVER_4("Clovers_4_white", 4),
    CLOVER_5("Clovers_5_white", 5),
    CLOVER_6("Clovers_6_white", 6),
    CLOVER_7("Clovers_7_white", 7),
    CLOVER_8("Clovers_8_white", 8),
    CLOVER_9("Clovers_9_white", 9),
    CLOVER_10("Clovers_10_white", 10),
    CLOVER_J("Clovers_Jack_white", 11),
    CLOVER_Q("Clovers_Queen_white", 12),
    CLOVER_K("Clovers_King_white", 13),
    HEART_A("Hearts_A_white", 1),
    HEART_2("Hearts_2_white", 2),
    HEART_3("Hearts_3_white", 3),
    HEART_4("Hearts_4_white", 4),
    HEART_5("Hearts_5_white", 5),
    HEART_6("Hearts_6_white", 6),
    HEART_7("Hearts_7_white", 7),
    HEART_8("Hearts_8_white", 8),
    HEART_9("Hearts_9_white", 9),
    HEART_10("Hearts_10_white", 10),
    HEART_J("Hearts_Jack_white", 11),
    HEART_Q("Hearts_Queen_white", 12),
    HEART_K("Hearts_King_white", 13),
    SPADE_A("Pikes_A_white", 1),
    SPADE_2("Pikes_2_white", 2),
    SPADE_3("Pikes_3_white", 3),
    SPADE_4("Pikes_4_white", 4),
    SPADE_5("Pikes_5_white", 5),
    SPADE_6("Pikes_6_white", 6),
    SPADE_7("Pikes_7_white", 7),
    SPADE_8("Pikes_8_white", 8),
    SPADE_9("Pikes_9_white", 9),
    SPADE_10("Pikes_10_white", 10),
    SPADE_J("Pikes_Jack_white", 11),
    SPADE_Q("Pikes_Queen_white", 12),
    SPADE_K("Pikes_King_white", 13),
    DIAMOND_A("Tiles_A_white", 1),
    DIAMOND_2("Tiles_2_white", 2),
    DIAMOND_3("Tiles_3_white", 3),
    DIAMOND_4("Tiles_4_white", 4),
    DIAMOND_5("Tiles_5_white", 5),
    DIAMOND_6("Tiles_6_white", 6),
    DIAMOND_7("Tiles_7_white", 7),
    DIAMOND_8("Tiles_8_white", 8),
    DIAMOND_9("Tiles_9_white", 9),
    DIAMOND_10("Tiles_10_white", 10),
    DIAMOND_J("Tiles_Jack_white", 11),
    DIAMOND_Q("Tiles_Queen_white", 12),
    DIAMOND_K("Tiles_King_white", 13);


    public String filename;
    public int number;

    CardType(String filename, int number) {
        this.filename = filename;
        this.number = number;
    }
}
