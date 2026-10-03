package com.lorcomp.mod.energy;

/**
 * Три технологических уровня кабеля LorComp.
 *
 * <p>transferPerTick — максимальный объём FE, который один
 * кабель может принять/передать за игровой тик.</p>
 */
public enum CableTier {

    /** Медный провод — базовый уровень. */
    COPPER(256),

    /** Золотой провод — повышенная пропускная способность. */
    GOLD(1024),

    /** Розовое золото — высокопроизводительный провод. */
    ROSE_GOLD(4096);

    /** Максимальный transfer rate одного кабеля, FE/t. */
    private final int transferPerTick;

    CableTier(int transferPerTick) {
        this.transferPerTick = transferPerTick;
    }

    /** @return лимит FE/t */
    public int getTransferPerTick() {
        return transferPerTick;
    }
}
