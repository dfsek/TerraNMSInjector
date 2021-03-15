package com.dfsek.nmsinject.nms;

import com.dfsek.terra.api.TerraPlugin;
import com.dfsek.terra.api.math.vector.Location;
import com.dfsek.terra.api.util.collections.MaterialSet;
import com.dfsek.terra.api.world.tree.Tree;
import net.minecraft.server.v1_16_R3.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R3.BlockChorusFlower;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.RegionLimitedWorldAccess;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
import org.bukkit.TreeType;
import org.bukkit.World;

import java.util.Random;

public class NMSTree implements Tree {
    private final TerraPlugin main;
    private final TreeType type;

    public NMSTree(TerraPlugin main, TreeType type) {
        this.main = main;
        this.type = type;
    }

    @Override
    public boolean plant(Location loc, Random random) {
        BlockPosition pos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        RegionLimitedWorldAccess world = ((NMSWorld) loc.getWorld()).getDelegate();

        WorldGenFeatureConfigured gen;
        switch (type) {
            case BIG_TREE:
                gen = BiomeDecoratorGroups.FANCY_OAK;
                break;
            case BIRCH:
                gen = BiomeDecoratorGroups.BIRCH;
                break;
            case REDWOOD:
                gen = BiomeDecoratorGroups.SPRUCE;
                break;
            case TALL_REDWOOD:
                gen = BiomeDecoratorGroups.PINE;
                break;
            case JUNGLE:
                gen = BiomeDecoratorGroups.MEGA_JUNGLE_TREE;
                break;
            case SMALL_JUNGLE:
                gen = BiomeDecoratorGroups.JUNGLE_TREE_NO_VINE;
                break;
            case COCOA_TREE:
                gen = BiomeDecoratorGroups.JUNGLE_TREE;
                break;
            case JUNGLE_BUSH:
                gen = BiomeDecoratorGroups.JUNGLE_BUSH;
                break;
            case RED_MUSHROOM:
                gen = BiomeDecoratorGroups.HUGE_RED_MUSHROOM;
                break;
            case BROWN_MUSHROOM:
                gen = BiomeDecoratorGroups.HUGE_BROWN_MUSHROOM;
                break;
            case SWAMP:
                gen = BiomeDecoratorGroups.SWAMP_TREE;
                break;
            case ACACIA:
                gen = BiomeDecoratorGroups.ACACIA;
                break;
            case DARK_OAK:
                gen = BiomeDecoratorGroups.DARK_OAK;
                break;
            case MEGA_REDWOOD:
                gen = BiomeDecoratorGroups.MEGA_PINE;
                break;
            case TALL_BIRCH:
                gen = BiomeDecoratorGroups.SUPER_BIRCH_BEES_0002;
                break;
            case CHORUS_PLANT:
                ((BlockChorusFlower) Blocks.CHORUS_FLOWER).a(world, pos, random, 8);
                return true;
            case CRIMSON_FUNGUS:
                gen = BiomeDecoratorGroups.CRIMSON_FUNGI;
                break;
            case WARPED_FUNGUS:
                gen = BiomeDecoratorGroups.WARPED_FUNGI;
                break;
            case TREE:
            default:
                gen = BiomeDecoratorGroups.OAK;
                break;
        }

        return gen.e.generate(world, world.getMinecraftWorld().getChunkProvider().getChunkGenerator(), random, pos, gen.f);
    }

    @Override
    public MaterialSet getSpawnable() {
        return MaterialSet.get(main.getWorldHandle().createBlockData("minecraft:grass_block"),
                main.getWorldHandle().createBlockData("minecraft:podzol"),
                main.getWorldHandle().createBlockData("minecraft:mycelium"));
    }
}
