package com.worldbattles.quests.registry;

import com.worldbattles.quests.WorldBattlesQuests;
import com.worldbattles.quests.entity.BlacksmithNpc;
import com.worldbattles.quests.entity.ExplorerNpc;
import com.worldbattles.quests.entity.HunterNpc;
import com.worldbattles.quests.entity.MayllineNpc;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WorldBattlesQuests.MODID);

    public static final RegistryObject<EntityType<BlacksmithNpc>> BLACKSMITH =
        TYPES.register("blacksmith", () -> EntityType.Builder.of(BlacksmithNpc::new, MobCategory.MISC)
            .sized(1.0f, 2.0f).clientTrackingRange(10).build("blacksmith"));

    public static final RegistryObject<EntityType<ExplorerNpc>> EXPLORER =
        TYPES.register("explorer", () -> EntityType.Builder.of(ExplorerNpc::new, MobCategory.MISC)
            .sized(0.7f, 1.95f).clientTrackingRange(10).build("explorer"));

    public static final RegistryObject<EntityType<HunterNpc>> HUNTER =
        TYPES.register("hunter", () -> EntityType.Builder.of(HunterNpc::new, MobCategory.MISC)
            .sized(0.7f, 1.95f).clientTrackingRange(10).build("hunter"));

    // Maylline is seated; this collision box follows the chair and the scaled model.
    public static final RegistryObject<EntityType<MayllineNpc>> MAYLLINE =
        TYPES.register("maylline", () -> EntityType.Builder.of(MayllineNpc::new, MobCategory.MISC)
            .sized(1.4f, 2.2f).clientTrackingRange(10).build("maylline"));

    private ModEntities() {}
}
