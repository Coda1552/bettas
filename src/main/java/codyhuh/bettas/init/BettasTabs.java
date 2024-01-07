package codyhuh.bettas.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import codyhuh.bettas.Bettas;

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