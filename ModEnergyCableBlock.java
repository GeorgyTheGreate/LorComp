package com.lorcomp.mod.blocks;

import com.lorcomp.mod.energy.CableTier;
import com.lorcomp.mod.tile.TileEnergyCable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.chunk.Chunk;

/** Визуальный и функциональный блок энергетического кабеля. */
public class ModEnergyCableBlock
        extends Block
        implements ITileEntityProvider {

    /** Северное соединение. */
    public static final PropertyBool NORTH =
            PropertyBool.create("north");

    /** Южное соединение. */
    public static final PropertyBool SOUTH =
            PropertyBool.create("south");

    /** Восточное соединение. */
    public static final PropertyBool EAST =
            PropertyBool.create("east");

    /** Западное соединение. */
    public static final PropertyBool WEST =
            PropertyBool.create("west");

    /** Верхнее соединение. */
    public static final PropertyBool UP =
            PropertyBool.create("up");

    /** Нижнее соединение. */
    public static final PropertyBool DOWN =
            PropertyBool.create("down");

    /** Технологический уровень кабеля. */
    private final CableTier tier;

    /**
     * Создаёт кабель.
     *
     * @param tier уровень кабеля
     */
    public ModEnergyCableBlock(CableTier tier) {
        super(Material.IRON);

        this.tier = tier;

        setHardness(1.0F);
        setResistance(30.0F);
        setHarvestLevel("pickaxe", 0);
        setDefaultState(
                blockState.getBaseState()
                        .withProperty(NORTH, false)
                        .withProperty(SOUTH, false)
                        .withProperty(EAST, false)
                        .withProperty(WEST, false)
                        .withProperty(UP, false)
                        .withProperty(DOWN, false)
        );
    }

    /** @return tier кабеля */
    public CableTier getTier() {
        return tier;
    }

    /**
     * Создаёт шесть Boolean-состояний для multipart blockstate.
     */
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                NORTH,
                SOUTH,
                EAST,
                WEST,
                UP,
                DOWN
        );
    }

    /**
     * Для кабеля всегда существует TileEntity.
     */
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    /**
     * Создаёт TE соответствующего tier.
     */
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEnergyCable(tier);
    }

    /**
     * Вычисляет визуальные соединения непосредственно перед рендерингом.
     *
     * <p>Эти свойства не обязаны сохраняться в metadata. Они являются
     * derived state, полученным из соседних блоков.</p>
     */
    @Override
    public IBlockState getActualState(
            IBlockState state,
            IBlockAccess world,
            BlockPos pos) {

        return state
                .withProperty(NORTH, canConnect(world, pos, EnumFacing.NORTH))
                .withProperty(SOUTH, canConnect(world, pos, EnumFacing.SOUTH))
                .withProperty(EAST, canConnect(world, pos, EnumFacing.EAST))
                .withProperty(WEST, canConnect(world, pos, EnumFacing.WEST))
                .withProperty(UP, canConnect(world, pos, EnumFacing.UP))
                .withProperty(DOWN, canConnect(world, pos, EnumFacing.DOWN));
    }

    /**
     * Проверяет, существует ли энергетически корректное соединение.
     *
     * @param world доступ к миру
     * @param pos позиция кабеля
     * @param side направление к соседу
     */
    private boolean canConnect(
            IBlockAccess world,
            BlockPos pos,
            EnumFacing side) {

        BlockPos neighbourPos = pos.offset(side);
        IBlockState neighbourState =
                world.getBlockState(neighbourPos);

        if (neighbourState.getBlock()
                instanceof ModEnergyCableBlock) {
            return true;
        }

        TileEntity tile;
        if (world instanceof ChunkCache) {
            tile = ((ChunkCache) world).getTileEntity(
                    neighbourPos,
                    Chunk.EnumCreateEntityType.CHECK
            );
        } else {
            tile = world.getTileEntity(neighbourPos);
        }

        if (tile == null) {
            return false;
        }

        net.minecraftforge.energy.IEnergyStorage energy =
                tile.getCapability(
                        net.minecraftforge.energy.CapabilityEnergy.ENERGY,
                        side.getOpposite()
                );

        return energy != null
                && (energy.canReceive() || energy.canExtract());
    }

    /**
     * Кабель — прозрачная техническая геометрия.
     */
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Не является полным кубом.
     */
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /**
     * Не является opaque cube.
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Маленький центральный bounding box.
     *
     * <p>Фактическая визуальная геометрия задаётся model JSON,
     * поэтому этот box в первую очередь нужен для collision/selection.</p>
     */
    @Override
    public AxisAlignedBB getBoundingBox(
            IBlockState state,
            IBlockAccess source,
            BlockPos pos) {

        return new AxisAlignedBB(
                0.25D, 0.25D, 0.25D,
                0.75D, 0.75D, 0.75D
        );
    }

    /**
     * При изменении соседнего блока заставляем chunk пересчитать
     * actual state и визуальное соединение.
     */
    @Override
    public void neighborChanged(
            IBlockState state,
            World world,
            BlockPos pos,
            Block blockIn,
            BlockPos fromPos) {

        super.neighborChanged(
                state,
                world,
                pos,
                blockIn,
                fromPos
        );

        if (!world.isRemote) {
            world.notifyBlockUpdate(
                    pos,
                    state,
                    state,
                    3
            );
        }
    }

    /**
     * При установке сразу обновляем соседние кабели.
     */
    @Override
    public void onBlockAdded(
            World world,
            BlockPos pos,
            IBlockState state) {

        super.onBlockAdded(world, pos, state);

        if (!world.isRemote) {
            notifyNeighbours(world, pos);
        }
    }

    /**
     * При удалении также обновляем соседей.
     */
    @Override
    public void breakBlock(
            World world,
            BlockPos pos,
            IBlockState state) {

        if (!world.isRemote) {
            notifyNeighbours(world, pos);
        }

        super.breakBlock(world, pos, state);
    }

    /**
     * Просит шесть соседних позиций пересчитать свой визуальный state.
     */
    private void notifyNeighbours(World world, BlockPos pos) {
        for (EnumFacing side : EnumFacing.values()) {
            world.notifyNeighborsOfStateChange(
                    pos.offset(side),
                    this,
                    false
            );
        }
    }

    /**
     * Metadata не используется для направлений.
     * Все шесть свойств являются actual-state.
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    /**
     * Восстанавливает единственный базовый state из metadata.
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }
}
