package com.girlkun.models.boss;

import lombok.Builder;
import lombok.Data;

@Data
public class BossData {

    public static final int DEFAULT_APPEAR = 0;
    public static final int APPEAR_WITH_ANOTHER = 1;
    public static final int ANOTHER_LEVEL = 2;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public short[] getOutfit() {
        return outfit;
    }

    public void setOutfit(short[] outfit) {
        this.outfit = outfit;
    }

    public long getDame() {
        return dame;
    }

    public void setDame(long dame) {
        this.dame = dame;
    }

    public long[] getHp() {
        return hp;
    }

    public void setHp(long[] hp) {
        this.hp = hp;
    }

    public int[] getMapJoin() {
        return mapJoin;
    }

    public void setMapJoin(int[] mapJoin) {
        this.mapJoin = mapJoin;
    }

    public int[][] getSkillTemp() {
        return skillTemp;
    }

    public void setSkillTemp(int[][] skillTemp) {
        this.skillTemp = skillTemp;
    }

    public String[] getTextS() {
        return textS;
    }

    public void setTextS(String[] textS) {
        this.textS = textS;
    }

    public String[] getTextM() {
        return textM;
    }

    public void setTextM(String[] textM) {
        this.textM = textM;
    }

    public String[] getTextE() {
        return textE;
    }

    public void setTextE(String[] textE) {
        this.textE = textE;
    }

    public int getSecondsRest() {
        return secondsRest;
    }

    public void setSecondsRest(int secondsRest) {
        this.secondsRest = secondsRest;
    }

    public TypeAppear getTypeAppear() {
        return typeAppear;
    }

    public void setTypeAppear(TypeAppear typeAppear) {
        this.typeAppear = typeAppear;
    }

    public int[] getBossesAppearTogether() {
        return bossesAppearTogether;
    }

    public void setBossesAppearTogether(int[] bossesAppearTogether) {
        this.bossesAppearTogether = bossesAppearTogether;
    }

    private byte gender;

    private short[] outfit;

    private long dame;

    private long[] hp;

    private int[] mapJoin;

    private int[][] skillTemp;

    private String[] textS;

    private String[] textM;

    private String[] textE;

    private int secondsRest;

    private TypeAppear typeAppear;

    private int[] bossesAppearTogether;

    private BossData(String name, byte gender, short[] outfit, int dame, long[] hp,
            int[] mapJoin, int[][] skillTemp, String[] textS, String[] textM,
            String[] textE) {
        this.name = name;
        this.gender = gender;
        this.outfit = outfit;
        this.dame = dame;
        this.hp = hp;
        this.mapJoin = mapJoin;
        this.skillTemp = skillTemp;
        this.textS = textS;
        this.textM = textM;
        this.textE = textE;
        this.secondsRest = 0;
        this.typeAppear = TypeAppear.DEFAULT_APPEAR;
    }

    private BossData(String name, byte gender, short[] outfit, long dame, long[] hp,
            int[] mapJoin, int[][] skillTemp, String[] textS, String[] textM,
            String[] textE) {
        this.name = name;
        this.gender = gender;
        this.outfit = outfit;
        this.dame = dame;
        this.hp = hp;
        this.mapJoin = mapJoin;
        this.skillTemp = skillTemp;
        this.textS = textS;
        this.textM = textM;
        this.textE = textE;
        this.secondsRest = 0;
        this.typeAppear = TypeAppear.DEFAULT_APPEAR;
    }

    @Builder
    public BossData(String name, byte gender, long dame, long[] hp,
            short[] outfit, int[] mapJoin, int[][] skillTemp,
            int secondsRest, String[] textS, String[] textM,
            String[] textE) {
        this.name = name;
        this.gender = gender;
        this.dame = dame;
        this.hp = hp;
        this.outfit = outfit;
        this.mapJoin = mapJoin;
        this.skillTemp = skillTemp;
        this.secondsRest = secondsRest;
        this.textS = new String[] {};
        this.textM = new String[] {};
        this.textE = new String[] {};
    }

    public BossData(String name, byte gender, short[] outfit, long dame, long[] hp,
            int[] mapJoin, int[][] skillTemp, String[] textS, String[] textM,
            String[] textE, int secondsRest) {
        this(name, gender, outfit, dame, hp, mapJoin, skillTemp, textS, textM, textE);
        this.secondsRest = secondsRest;
    }

    public BossData(String name, byte gender, short[] outfit, long dame, long[] hp,
            int[] mapJoin, int[][] skillTemp, String[] textS, String[] textM,
            String[] textE, int secondsRest, int[] bossesAppearTogether) {
        this(name, gender, outfit, dame, hp, mapJoin, skillTemp, textS, textM, textE, secondsRest);
        this.bossesAppearTogether = bossesAppearTogether;
    }

    public BossData(String name, byte gender, short[] outfit, long dame, long[] hp,
            int[] mapJoin, int[][] skillTemp, String[] textS, String[] textM,
            String[] textE, TypeAppear typeAppear) {
        this(name, gender, outfit, dame, hp, mapJoin, skillTemp, textS, textM, textE);
        this.typeAppear = typeAppear;
    }

    public BossData(String name, byte gender, short[] outfit, long dame, long[] hp,
            int[] mapJoin, int[][] skillTemp, String[] textS, String[] textM,
            String[] textE, int secondsRest, TypeAppear typeAppear) {
        this(name, gender, outfit, dame, hp, mapJoin, skillTemp, textS, textM, textE, secondsRest);
        this.typeAppear = typeAppear;
    }

    public BossData(String name, byte gender, short[] outfit, int dame, long[] hp,
            int[] mapJoin, int[][] skillTemp, String[] textS, String[] textM,
            String[] textE, int secondsRest) {
        this(name, gender, outfit, dame, hp, mapJoin, skillTemp, textS, textM, textE);
        this.secondsRest = secondsRest;
    }
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
