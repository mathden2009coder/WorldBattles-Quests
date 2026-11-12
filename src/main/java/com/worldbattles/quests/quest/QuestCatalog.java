package com.worldbattles.quests.quest;

import net.minecraft.resources.ResourceLocation;
import java.util.*;

public final class QuestCatalog {
    private static final Map<String, List<QuestDefinition>> CHAINS = new LinkedHashMap<>();
    // Keep the original reward progression unchanged.
    private static final long[] REWARDS = {
        100, 150, 250, 350, 500, 700, 900, 1200, 1600, 2200,
        3000, 4200, 6000, 8500, 12000, 18000, 27000, 40000, 65000, 100000
    };

    static {
        add("blacksmith", "Commandes de forge", QuestDefinition.Type.DELIVER_ITEM,
            new String[]{"coal","raw_iron","iron_ingot","iron_pickaxe","iron_sword","raw_copper",
                "copper_ingot","gold_ingot","iron_block","diamond","diamond_pickaxe","diamond_sword",
                "obsidian","anvil","gold_block","emerald","diamond_block","netherite_scrap",
                "netherite_ingot","netherite_block"},
            new int[]{8,12,16,1,1,20,16,12,3,4,1,1,24,1,4,12,2,8,2,1});

        // The old Explorer/Mira chain is intentionally retired.
        add("hunter", "Contrats de chasse", QuestDefinition.Type.KILL_ENTITY,
            new String[]{"zombie","skeleton","spider","creeper","drowned","enderman","witch","pillager",
                "phantom","slime","blaze","ghast","hoglin","piglin_brute","cave_spider","guardian",
                "ravager","evoker","wither_skeleton","magma_cube"},
            new int[]{5,6,8,8,10,4,4,8,6,12,8,3,10,4,5,8,3,3,10,1});

        // Twenty archive deliveries, all obtainable without travelling to the End.
        add("maylline", "Les pages égarées", QuestDefinition.Type.DELIVER_ITEM,
            new String[]{"paper","book","feather","ink_sac","map","compass","writable_book","bookshelf",
                "lapis_lazuli","glow_ink_sac","amethyst_shard","spyglass","ender_pearl","written_book",
                "enchanted_book","amethyst_block","clock","quartz_block","crying_obsidian","lectern"},
            new int[]{32,8,16,12,4,1,2,6,24,8,16,1,8,1,2,4,1,6,1,1});
    }

    private static void add(String npc, String theme, QuestDefinition.Type type,
                            String[] targets, int[] counts) {
        List<QuestDefinition> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String label = "maylline".equals(npc) ? mayllineLabel(targets[i]) : targets[i].replace('_', ' ');
            String description = type == QuestDefinition.Type.KILL_ENTITY
                ? "Élimine " + counts[i] + " " + label + "."
                : "Rapporte " + counts[i] + " " + label + ".";
            String title = "maylline".equals(npc)
                ? theme + " — chapitre " + (i + 1)
                : theme + " " + (i + 1);
            list.add(new QuestDefinition(npc, i + 1, title, description, type,
                new ResourceLocation("minecraft:" + targets[i]), counts[i], REWARDS[i]));
        }
        CHAINS.put(npc, List.copyOf(list));
    }

    private static String mayllineLabel(String item) {
        return switch (item) {
            case "paper" -> "feuilles de papier";
            case "book" -> "livres";
            case "feather" -> "plumes";
            case "ink_sac" -> "sacs d'encre";
            case "map" -> "cartes";
            case "compass" -> "boussoles";
            case "writable_book" -> "livres vierges";
            case "bookshelf" -> "bibliothèques";
            case "lapis_lazuli" -> "lapis-lazulis";
            case "glow_ink_sac" -> "sacs d'encre lumineuse";
            case "amethyst_shard" -> "éclats d'améthyste";
            case "spyglass" -> "longues-vues";
            case "ender_pearl" -> "perles de l'Ender (Endermen de l'Overworld)";
            case "written_book" -> "livres écrits";
            case "enchanted_book" -> "livres enchantés";
            case "amethyst_block" -> "blocs d'améthyste";
            case "clock" -> "horloges";
            case "quartz_block" -> "blocs de quartz";
            case "crying_obsidian" -> "obsidiennes pleureuses";
            case "lectern" -> "lutrins";
            default -> item.replace('_', ' ');
        };
    }

    public static List<QuestDefinition> forNpc(String id) {
        return CHAINS.getOrDefault(id, List.of());
    }

    public static QuestDefinition current(String id, int completed) {
        List<QuestDefinition> list = forNpc(id);
        return completed >= list.size() ? null : list.get(completed);
    }

    private QuestCatalog() {}
}
