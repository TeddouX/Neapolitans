package teddyx.neapolitans.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import teddyx.neapolitans.Neapolitans;

import java.util.function.Function;

public class ModItems {

    public static final Item PESTLE = register("pestle", new Item.Settings().maxCount(1).maxDamage(150));
    public static final Item COCOA_POWDER = register("cocoa_powder", new Item.Settings());
    public static final Item FLOUR = register("flour", FlourItem::new, new Item.Settings());
    public static final Item RAW_DOUGH = register("raw_dough", new Item.Settings());
    public static final Item NEAPOLITAN_DOUGH = register("neapolitan_dough", new Item.Settings());
    public static final Item CHOCOLATE = register("chocolate", new Item.Settings());

    private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Identifier id = Neapolitans.id(name);
        RegistryKey<Item> itemResourceKey = RegistryKey.of(RegistryKeys.ITEM, id);

        return Registry.register(Registries.ITEM, id, factory.apply(settings.registryKey(itemResourceKey)));
    }

    private static Item register(String name, Item.Settings settings) {
        Identifier id = Neapolitans.id(name);
        RegistryKey<Item> itemResourceKey = RegistryKey.of(RegistryKeys.ITEM, id);

        return Registry.register(Registries.ITEM, id, new Item(settings.registryKey(itemResourceKey)));
    }

    public static void registerModItems() {
        Neapolitans.LOGGER.info("Registering items...");
    }
}
