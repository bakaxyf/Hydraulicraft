package k4unl.minecraft.Hydraulicraft.world;

import cpw.mods.fml.common.IWorldGenerator;
import k4unl.minecraft.Hydraulicraft.lib.Log;
import k4unl.minecraft.Hydraulicraft.lib.config.HCConfig;
import k4unl.minecraft.Hydraulicraft.ores.Ores;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class HCWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
		case -1:
			generateNether(world,random,chunkX * 16,chunkZ * 16);
			break;
		case 0:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		default:
			generateOverworld(world,random, chunkX * 16, chunkZ * 16);
			break;
		}
	}
	
	private void generateEnd(World world, Random random, int chunkX, int chunkZ){
		//Do nothing here, we don't want ores in the end!
	}
	
	private void generateNether(World world, Random random, int chunkX, int chunkZ){
		//Do nothing here, we don't want ores in the nether!
	}
	
	private void generateOre(Block ore, World world, int veinSize, int veinCount, int minY, int maxY, Random random, int chunkX, int chunkZ){
		for(int i = 0; i < veinCount; i++){
			int firstBlockXCoord = chunkX + random.nextInt(16);
			int firstBlockYCoord = minY + random.nextInt(maxY - minY); // From +18 to 70
			int firstBlockZCoord = chunkZ + random.nextInt(16);
			
			(new WorldGenMinable(ore, veinSize)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
	}

    private void generateFluids(Block fluidBlock, World world, int minY, int maxY, Random random, int chunkX, int chunkZ){
        int firstBlockXCoord = chunkX + random.nextInt(16);
        int firstBlockYCoord = minY + random.nextInt(maxY - minY); // From +18 to 70
        int firstBlockZCoord = chunkZ + random.nextInt(16);
        Log.info(firstBlockXCoord + " " + firstBlockYCoord + " " + firstBlockZCoord);
        (new WorldGenLakes(fluidBlock)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
    }
	
	private void generateOverworld(World world, Random random, int chunkX, int chunkZ){
		if(HCConfig.INSTANCE.getBool("shouldGenCopperOre", "worldgen")){
			generateOre(Ores.oreCopper, world, HCConfig.INSTANCE.getInt("copperVeinSize"), HCConfig.INSTANCE.getInt("copperVeinCount"),HCConfig.INSTANCE.getInt("copperMinY"),HCConfig.INSTANCE.getInt("copperMaxY"), random, chunkX, chunkZ);
		}
		if(HCConfig.INSTANCE.getBool("shouldGenLeadOre", "worldgen")){
            generateOre(Ores.oreLead, world, HCConfig.INSTANCE.getInt("leadVeinSize"), HCConfig.INSTANCE.getInt("leadVeinCount"),HCConfig.INSTANCE.getInt("leadMinY"),HCConfig.INSTANCE.getInt("leadMaxY"), random, chunkX, chunkZ);
		}
		if(HCConfig.INSTANCE.getBool("shouldGenLonezium", "worldgen")){
			generateOre(Ores.oreLonezium, world, HCConfig.INSTANCE.getInt("loneziumVeinSize"), HCConfig.INSTANCE.getInt("loneziumVeinCount"), HCConfig.INSTANCE.getInt("loneziumMinY"), HCConfig.INSTANCE.getInt("loneziumMaxY"), random, chunkX, chunkZ);
		}
        if(HCConfig.INSTANCE.getBool("shouldGenNadsiumBicarbinate", "worldgen")){
            generateOre(Ores.oreNadsiumBicarbinate, world, HCConfig.INSTANCE.getInt("nadsiumBicarbinateVeinSize"), HCConfig.INSTANCE.getInt("nadsiumBicarbinateVeinCount"), HCConfig.INSTANCE.getInt("nadsiumBicarbinateMinY"), HCConfig.INSTANCE.getInt("nadsiumBicarbinateMaxY"), random, chunkX, chunkZ);
        }
        if(HCConfig.INSTANCE.getBool("shouldGenBeachium", "worldgen")){
            generateOre(Ores.oreBeachium, world, HCConfig.INSTANCE.getInt("beachiumVeinSize"), HCConfig.INSTANCE.getInt("beachiumVeinCount"), HCConfig.INSTANCE.getInt("beachiumMinY"), HCConfig.INSTANCE.getInt("beachiumMaxY"), random, chunkX, chunkZ);
        }
        if(HCConfig.INSTANCE.getBool("shouldGenOil", "worldgen")){
			//Log.info("Now genning " + (chunkX * 16) + " " + (chunkZ * 16));
            if (random.nextDouble() < HCConfig.INSTANCE.getDouble("oilChance", "worldgen")) {
                int x = chunkX + 8;
                int z = chunkZ + 8;
                int y = 30 + random.nextInt(40);

                (new WorldGenOil()).generate(world, random, x, z, random.nextInt(20), y);
            }
        }
	}

}
