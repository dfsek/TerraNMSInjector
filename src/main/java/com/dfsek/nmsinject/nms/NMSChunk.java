package com.dfsek.nmsinject.nms;

import com.dfsek.terra.api.platform.block.Block;
import com.dfsek.terra.api.platform.block.BlockData;
import com.dfsek.terra.api.platform.world.Chunk;
import com.dfsek.terra.api.platform.world.World;
import com.dfsek.terra.bukkit.world.BukkitAdapter;
import com.dfsek.terra.bukkit.world.block.BukkitBlock;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.RegionLimitedWorldAccess;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.jetbrains.annotations.NotNull;

public class NMSChunk implements Chunk {
    private final RegionLimitedWorldAccess delegate;

    public NMSChunk(RegionLimitedWorldAccess delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object getHandle() {
        return delegate;
    }

    @Override
    public int getX() {
        return delegate.a();
    }

    @Override
    public int getZ() {
        return delegate.b();
    }

    @Override
    public World getWorld() {
        return new NMSWorld(delegate);
    }

    @Override
    public Block getBlock(int i, int i1, int i2) {
        return new NMSBlock(delegate, new BlockPosition((getX() << 4) + i, i1, (getZ() << 4)+ i2));
    }

    @Override
    public void setBlock(int i, int i1, int i2, @NotNull BlockData blockData) {
        delegate.setTypeAndData(new BlockPosition((getX() << 4) + i, i1, (getZ() << 4) + i2), ((CraftBlockData) blockData.getHandle()).getState(), 0);
    }

    @Override
    public @NotNull BlockData getBlockData(int i, int i1, int i2) {
        return getBlock(i, i1, i2).getBlockData();
    }
}
