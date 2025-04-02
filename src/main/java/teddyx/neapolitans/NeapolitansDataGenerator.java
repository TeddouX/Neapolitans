package teddyx.neapolitans;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import teddyx.neapolitans.datagen.ModEnglishLanguageProvider;
import teddyx.neapolitans.datagen.ModLootTablesGenerator;
import teddyx.neapolitans.datagen.ModModelsProvider;
import teddyx.neapolitans.datagen.ModRecipesProvider;

public class NeapolitansDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModEnglishLanguageProvider::new);
		pack.addProvider(ModModelsProvider::new);
		pack.addProvider(ModLootTablesGenerator::new);
		pack.addProvider(ModRecipesProvider::new);
	}
}
