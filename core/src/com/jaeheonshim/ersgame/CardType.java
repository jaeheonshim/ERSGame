package com.jaeheonshim.ersgame;

public enum CardType {
    CLOVER_A("Clovers_A_white"),
    CLOVER_2("Clovers_2_white"),
    CLOVER_3("Clovers_3_white"),
    CLOVER_4("Clovers_4_white"),
    CLOVER_5("Clovers_5_white"),
    CLOVER_6("Clovers_6_white"),
    CLOVER_7("Clovers_7_white"),
    CLOVER_8("Clovers_8_white"),
    CLOVER_9("Clovers_9_white"),
    CLOVER_10("Clovers_10_white"),
    CLOVER_J("Clovers_Jack_white"),
    CLOVER_Q("Clovers_Queen_white"),
    CLOVER_K("Clovers_King_white"),
    HEART_A("Hearts_A_white"),
    HEART_2("Hearts_2_white"),
    HEART_3("Hearts_3_white"),
    HEART_4("Hearts_4_white"),
    HEART_5("Hearts_5_white"),
    HEART_6("Hearts_6_white"),
    HEART_7("Hearts_7_white"),
    HEART_8("Hearts_8_white"),
    HEART_9("Hearts_9_white"),
    HEART_10("Hearts_10_white"),
    HEART_J("Hearts_Jack_white"),
    HEART_Q("Hearts_Queen_white"),
    HEART_K("Hearts_King_white"),
    SPADE_A("Pikes_A_white"),
    SPADE_2("Pikes_2_white"),
    SPADE_3("Pikes_3_white"),
    SPADE_4("Pikes_4_white"),
    SPADE_5("Pikes_5_white"),
    SPADE_6("Pikes_6_white"),
    SPADE_7("Pikes_7_white"),
    SPADE_8("Pikes_8_white"),
    SPADE_9("Pikes_9_white"),
    SPADE_10("Pikes_10_white"),
    SPADE_J("Pikes_Jack_white"),
    SPADE_Q("Pikes_Queen_white"),
    SPADE_K("Pikes_King_white"),
    DIAMOND_A("Tiles_A_white"),
    DIAMOND_2("Tiles_2_white"),
    DIAMOND_3("Tiles_3_white"),
    DIAMOND_4("Tiles_4_white"),
    DIAMOND_5("Tiles_5_white"),
    DIAMOND_6("Tiles_6_white"),
    DIAMOND_7("Tiles_7_white"),
    DIAMOND_8("Tiles_8_white"),
    DIAMOND_9("Tiles_9_white"),
    DIAMOND_10("Tiles_10_white"),
    DIAMOND_J("Tiles_Jack_white"),
    DIAMOND_Q("Tiles_Queen_white"),
    DIAMOND_K("Tiles_King_white");


    public String filename;

    private CardType(String filename) {
        this.filename = filename;
    }
}
