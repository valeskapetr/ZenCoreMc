package cz.valenty.zenCore.enums;

public enum MinecraftAdvancement{
    //Adventure
    ADVENTURE_ROOT("adventure/root","Dobrodruh"),
    ADVENTURE_KILL_A_MOB("adventure/kill_a_mob","Lovec"),
    ADVENTURE_ADVENTURING_TIME("adventure/adventuring_time","Čas na dobrodružství");

    private final String key;
    private final String displayName;

    MinecraftAdvancement(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    public static String getDisplayNameByKey(String key){
        for(MinecraftAdvancement adv:values()) if(adv.key.equalsIgnoreCase(key)) return adv.displayName;

        return null;
    }
}