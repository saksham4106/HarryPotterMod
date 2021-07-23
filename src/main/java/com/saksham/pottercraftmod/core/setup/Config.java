package com.saksham.pottercraftmod.core.setup;

import com.saksham.pottercraftmod.core.util.SpellsList;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_WAND = "wand";

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> SPELLS_LIST;

    static {

        System.out.println("sup");
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("General Settins").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();

        SERVER_BUILDER.comment("Wand Settings").push(CATEGORY_WAND);
        setupWandConfig(SERVER_BUILDER, CLIENT_BUILDER);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupWandConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER){
            List<String> spellList = new ArrayList<>();
            for(SpellsList spell: SpellsList.values()){
                spellList.add(spell.name());
            }

           SPELLS_LIST = SERVER_BUILDER.comment("List of Spells that will be allowed")
                   .defineList("spellList", spellList, (s) -> SpellsList.isValidSpell((String)s));

            SERVER_BUILDER.pop();
    }

    public static void onLoad(final ModConfig.Loading configEvent){
    }

    public static void onReload(final ModConfig.Reloading configEvent){

    }
}
