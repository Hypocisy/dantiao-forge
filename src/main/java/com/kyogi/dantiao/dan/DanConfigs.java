package com.kyogi.dantiao.dan;

import net.minecraftforge.common.ForgeConfigSpec;

public class DanConfigs {
    private final String editName;

    private final ForgeConfigSpec.ConfigValue<String> prefix;
    private final ForgeConfigSpec.IntValue exp;

    public DanConfigs(String editName, String prefix, int exp, ForgeConfigSpec.Builder builder){
        this.editName = editName;
        this.prefix = builder.comment("Experience required for " + editName +"Default exp is " + exp).define(editName, prefix);;
        this.exp = builder.comment("Experience required for " + editName +"Default exp is " + exp).defineInRange("exp"+editName, exp, 1,Integer.MAX_VALUE);
    }

    public String getEditName() {
        return prefix.getPath().toString();
    }

    public String getDisplayName() {
        return prefix.get();
    }

    public int getExp() {
        return exp.get();
    }
    public void setExp(int i) {
        this.exp.set(i);
    }
    public void setDisplayName(String sPrefix){
        this.prefix.set(sPrefix);
    }

    public int getDefaultExp() {
        return this.exp.getDefault();
    }
    public DanConfigs getInstance(){
        return this;
    }
}
