package com.lorcomp.mod.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class ModEnergyStorage extends EnergyStorage {

    public ModEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public void writeToNBT(NBTTagCompound nbt, String key) {
        nbt.setInteger(key, getEnergyStored());
    }

    public void readFromNBT(NBTTagCompound nbt, String key) {
        this.energy = Math.max(
                0,
                Math.min(
                        getMaxEnergyStored(),
                        nbt.getInteger(key)
                )
        );
    }

    public int getRawEnergy() {
        return energy;
    }

    public void setRawEnergy(int value) {
        energy = Math.max(0, Math.min(capacity, value));
    }
}
