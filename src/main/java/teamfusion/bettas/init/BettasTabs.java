package teamfusion.bettas.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamfusion.bettas.Bettas;
import teamfusion.bettas.item.BettasBucketItem;

public class BettasTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Bettas.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BETTAS_TAB = TABS.register("bettas_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + Bettas.MOD_ID))
                    .icon(BettasItems.BETTA_FISH.get()::getDefaultInstance)
                    .displayItems((displayParams, output) -> {
                        for (var item : BettasItems.ITEMS.getEntries()) {
                            output.accept(item.get());
                        }
                    })
                    .build()
    );
}