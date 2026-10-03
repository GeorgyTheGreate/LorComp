package com.lorcomp.mod.init;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Генератор трёх металлических руд LorComp.
 *
 * <p>Генерация выполняется только в Overworld (dimension 0).</p>
 */
public class ModWorldGen implements IWorldGenerator {

    private static final int LEAD_SIZE = 8;
    private static final int ALUMINUM_SIZE = 8;
    private static final int COPPER_SIZE = 9;

    private static final int LEAD_ATTEMPTS = 8;
    private static final int ALUMINUM_ATTEMPTS = 10;
    private static final int COPPER_ATTEMPTS = 12;

    private static final int LEAD_MAX_Y = 32;
    private static final int ALUMINUM_MAX_Y = 64;
    private static final int COPPER_MAX_Y = 72;

    @Override
    public void generate(
            Random random,
            int chunkX,
            int chunkZ,
            World world,
            IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {

        if (world.provider.getDimension() != 0) {
            return;
        }

        int x = chunkX << 4;
        int z = chunkZ << 4;

        generateOre(world, random, x, z,
                ModBlocks.LEAD_ORE.getDefaultState(),
                LEAD_SIZE, LEAD_ATTEMPTS, LEAD_MAX_Y);

        generateOre(world, random, x, z,
                ModBlocks.ALUMINUM_ORE.getDefaultState(),
                ALUMINUM_SIZE, ALUMINUM_ATTEMPTS, ALUMINUM_MAX_Y);

        generateOre(world, random, x, z,
                ModBlocks.COPPER_ORE.getDefaultState(),
                COPPER_SIZE, COPPER_ATTEMPTS, COPPER_MAX_Y);
    }

    /**
     * Размещает указанное количество случайных жил внутри чанка.
     */
    private void generateOre(
            World world,
            Random random,
            int chunkX,
            int chunkZ,
            IBlockState state,
            int veinSize,
            int attempts,
            int maxY) {

        WorldGenMinable generator =
                new WorldGenMinable(state, veinSize);

        for (int i = 0; i < attempts; i++) {
            int x = chunkX + random.nextInt(16);
            int y = random.nextInt(maxY);
            int z = chunkZ + random.nextInt(16);

            generator.generate(
                    world,
                    random,
                    new BlockPos(x, y, z)
            );
        }
    }
}
