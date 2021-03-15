package com.dfsek.nmsinject.nms;

import com.dfsek.terra.api.math.vector.Location;
import com.dfsek.terra.api.platform.block.Block;
import com.dfsek.terra.api.platform.block.BlockData;
import com.dfsek.terra.api.platform.block.BlockFace;
import com.dfsek.terra.api.platform.block.BlockType;
import com.dfsek.terra.api.platform.block.state.BlockState;
import com.dfsek.terra.bukkit.world.BukkitAdapter;
import com.dfsek.terra.bukkit.world.block.BukkitBlock;
import com.dfsek.terra.bukkit.world.block.state.BukkitBlockState;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.RegionLimitedWorldAccess;
import net.minecraft.server.v1_16_R3.WorldAccess;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;

public class NMSBlock implements Block {
    private final RegionLimitedWorldAccess delegate;
    private final BlockPosition position;

    public NMSBlock(RegionLimitedWorldAccess delegate, BlockPosition position) {
        this.delegate = delegate;
        this.position = position;
    }

    @Override
    public void setBlockData(BlockData blockData, boolean b) {
        delegate.setTypeAndData(position, ((CraftBlockData) blockData.getHandle()).getState(), 0);
    }

    @Override
    public BlockData getBlockData() {
        return BukkitAdapter.adapt(CraftBlockData.fromData(delegate.getType(position)));
    }

    @Override
    public BlockState getState() {
        return new BlockState() {
            @Override
            public Block getBlock() {
                return NMSBlock.this;
            }

            @Override
            public int getX() {
                return NMSBlock.this.getX();
            }

            @Override
            public int getY() {
                return NMSBlock.this.getY();
            }

            @Override
            public int getZ() {
                return NMSBlock.this.getZ();
            }

            @Override
            public BlockData getBlockData() {
                return NMSBlock.this.getBlockData();
            }

            @Override
            public boolean update(boolean b) {
                return false;
            }

            @Override
            public Object getHandle() {
                return NMSBlock.this;
            }
        };
    }

    @Override
    public Block getRelative(BlockFace blockFace) {
        return getRelative(blockFace, 1);
    }

    @Override
    public Block getRelative(BlockFace blockFace, int i) {
        return new NMSBlock(delegate, new BlockPosition(position.getX() + blockFace.getModX()*i, position.getY() + blockFace.getModY()*i, position.getZ() + blockFace.getModZ()*i));
    }

    @Override
    public boolean isEmpty() {
        return getBlockData().isAir();
    }

    @Override
    public Location getLocation() {
        return new Location(new NMSWorld(delegate), position.getX(), position.getY(), position.getZ());
    }

    @Override
    public BlockType getType() {
        return getBlockData().getBlockType();
    }

    @Override
    public int getX() {
        return position.getX();
    }

    @Override
    public int getZ() {
        return position.getZ();
    }

    @Override
    public int getY() {
        return position.getY();
    }

    @Override
    public boolean isPassable() {
        return isEmpty();
    }

    @Override
    public Object getHandle() {
        return delegate;
    }
}
