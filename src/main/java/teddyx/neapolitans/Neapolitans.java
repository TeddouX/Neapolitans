package teddyx.neapolitans;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.util.GeckoLibUtil;
import teddyx.neapolitans.blocks.ModBlocks;
import teddyx.neapolitans.blocks.entity.ModBlockEntities;
import teddyx.neapolitans.items.ModItems;
import teddyx.neapolitans.recipes.ModRecipeBookCategories;
import teddyx.neapolitans.recipes.ModRecipes;


public class Neapolitans implements ModInitializer {
	public static final String MOD_ID = "neapolitans";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		ModRecipeBookCategories.registerModRecipyBookCategories();
		ModRecipes.registerModRecipes();
	}

	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}
}