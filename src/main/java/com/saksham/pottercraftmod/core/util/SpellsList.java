package com.saksham.pottercraftmod.core.util;

public enum SpellsList {
    STUPEFY("Stupefy"),
    AVADA_KEDAVRA("Avada Kedavra"),
    CRUCIO("Crucio"),
    IMPERIOUS("Imperio"),
    INCENDIO("Incendio"),
    CONFRINGO("Confringo"),
    WINGARDIUM_LEVIOSA("Wingardium Leviosa"),
    ACCIO("Accio"),
    AGUAMENTI("Aguamenti"),
    ASCENDIO("Ascendio"),
    CONJUNCTIVITIS("Conjunctivitis"),
    PETRIFICUS("Petrificus Totalus"),
    EXPELLIARMUS("Expelliarmus");

    private final String name;
    private static final SpellsList[] spells = values();

    SpellsList(String name){
        this.name = name;
    }

    public static boolean isValidSpell(String spellName){
        for(SpellsList spell : spells){
            if(spell.name.equalsIgnoreCase(spellName))return true;
        }
        return false;
    }

    public String getDisplayName(){
        return this.name;
    }

    public SpellsList next(){
        return spells[(this.ordinal()+1) % spells.length];
    }
}
