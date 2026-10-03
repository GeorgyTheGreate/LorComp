package com.lorcomp.mod.tile;

import com.lorcomp.mod.energy.CableTier;
import com.lorcomp.mod.energy.ModEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEnergyCable extends TileEntity {

    private CableTier tier;
    private ModEnergyStorage storage;

    public TileEnergyCable() {
        this(CableTier.COPPER);
    }

    public TileEnergyCable(CableTier tier) {
        this.tier = tier == null ? CableTier.COPPER : tier;
        this.storage = createStorage(this.tier, 0);
    }

    public void setTier(CableTier tier) {
        CableTier newTier = tier == null ? CableTier.COPPER : tier;
        if (this.tier == newTier) {
            return;
        }

        int energy = storage == null ? 0 : storage.getEnergyStored();
        this.tier = newTier;
        this.storage = createStorage(newTier, energy);
        markDirty();
    }

    public CableTier getTier() {
        return tier;
    }

    public ModEnergyStorage getStorage() {
        return storage;
    }

    private ModEnergyStorage createStorage(CableTier cableTier, int energy) {
        int transfer = cableTier.getTransferPerTick();
        ModEnergyStorage result = new ModEnergyStorage(transfer * 2, transfer, transfer);
        result.setRawEnergy(Math.min(energy, result.getMaxEnergyStored()));
        return result;
    }

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }

        int limit = tier.getTransferPerTick();

        for (EnumFacing side : EnumFacing.values()) {
            if (storage.getEnergyStored() >= storage.getMaxEnergyStored()) {
                break;
            }

            TileEntity neighbour = world.getTileEntity(pos.offset(side));
            if (neighbour == null || neighbour instanceof TileEnergyCable) {
                continue;
            }

            IEnergyStorage source = neighbour.getCapability(
                    CapabilityEnergy.ENERGY,
                    side.getOpposite()
            );
            if (source == null || !source.canExtract()) {
                continue;
            }

            int amount = Math.min(
                    limit,
                    storage.getMaxEnergyStored() - storage.getEnergyStored()
            );

            int extracted = source.extractEnergy(amount, true);
            if (extracted <= 0) {
                continue;
            }

            int accepted = storage.receiveEnergy(extracted, false);
            int remainder = extracted - accepted;
            if (remainder > 0) {
                source.extractEnergy(remainder, false);
            }
        }

        for (EnumFacing side : EnumFacing.values()) {
            if (storage.getEnergyStored() <= 0) {
                break;
            }

            TileEntity neighbour = world.getTileEntity(pos.offset(side));
            if (neighbour == null || neighbour instanceof TileEnergyCable) {
                continue;
            }

            IEnergyStorage target = neighbour.getCapability(
                    CapabilityEnergy.ENERGY,
                    side.getOpposite()
            );
            if (target == null || !target.canReceive()) {
                continue;
            }

            int amount = Math.min(limit, storage.getEnergyStored());
            int accepted = target.receiveEnergy(amount, false);
            if (accepted > 0) {
                storage.extractEnergy(accepted, false);
            }
        }

        for (EnumFacing side : EnumFacing.values()) {
            if (storage.getEnergyStored() <= 0) {
                break;
            }

            TileEntity neighbour = world.getTileEntity(pos.offset(side));
            if (!(neighbour instanceof TileEnergyCable)) {
                continue;
            }

            TileEnergyCable other = (TileEnergyCable) neighbour;
            if (other.storage.getEnergyStored() >= storage.getEnergyStored()) {
                continue;
            }

            int difference = storage.getEnergyStored() - other.storage.getEnergyStored();
            int amount = Math.min(limit, Math.max(1, difference / 2));
            int accepted = other.storage.receiveEnergy(amount, false);
            if (accepted > 0) {
                storage.extractEnergy(accepted, false);
            }
        }

        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt, "Energy");
        nbt.setString("Tier", tier.name());
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        CableTier loadedTier = CableTier.COPPER;
        if (nbt.hasKey("Tier")) {
            try {
                loadedTier = CableTier.valueOf(nbt.getString("Tier"));
            } catch (IllegalArgumentException ignored) {
                loadedTier = CableTier.COPPER;
            }
        }

        this.tier = loadedTier;
        this.storage = createStorage(loadedTier, 0);
        this.storage.readFromNBT(nbt, "Energy");
    }

    @Override
    public boolean hasCapability(
            net.minecraftforge.common.capabilities.Capability<?> capability,
            EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY
                || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(
            net.minecraftforge.common.capabilities.Capability<T> capability,
            EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(storage);
        }
        return super.getCapability(capability, facing);
    }
}
