package com.dfsek.nmsinject.nms;

import com.dfsek.terra.api.TerraPlugin;
import com.dfsek.terra.api.platform.world.World;
import com.dfsek.terra.bukkit.world.BukkitAdapter;
import com.dfsek.terra.world.population.CavePopulator;
import com.dfsek.terra.world.population.FloraPopulator;
import com.dfsek.terra.world.population.OrePopulator;
import com.dfsek.terra.world.population.StructurePopulator;
import com.dfsek.terra.world.population.TreePopulator;
import net.minecraft.server.v1_16_R3.Chunk;
import net.minecraft.server.v1_16_R3.ChunkGenerator;
import net.minecraft.server.v1_16_R3.RegionLimitedWorldAccess;
import net.minecraft.server.v1_16_R3.StructureManager;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_16_R3.generator.CustomChunkGenerator;

public class NMSChunkGenerator extends CustomChunkGenerator {
    private final CavePopulator cavePopulator;
    private final StructurePopulator structurePopulator;
    private final OrePopulator orePopulator;
    private final TreePopulator treePopulator;
    private final FloraPopulator floraPopulator;

    public NMSChunkGenerator(WorldServer world, ChunkGenerator delegate, org.bukkit.generator.ChunkGenerator generator, TerraPlugin main) {
        super(world, delegate, generator);
        System.out.println("Instantiated janky generator.");
        this.cavePopulator = new CavePopulator(main);
        this.structurePopulator = new StructurePopulator(main);
        this.orePopulator = new OrePopulator(main);
        this.treePopulator = new TreePopulator(main);
        this.floraPopulator = new FloraPopulator(main);
    }

    @Override
    public void addDecorations(RegionLimitedWorldAccess regionlimitedworldaccess, StructureManager structuremanager) {
        super.addDecorations(regionlimitedworldaccess, structuremanager);
        World world = new NMSWorld(regionlimitedworldaccess);
        com.dfsek.terra.api.platform.world.Chunk chunk = new NMSChunk(regionlimitedworldaccess);
        cavePopulator.populate(world, chunk);
        structurePopulator.populate(world, chunk);
        orePopulator.populate(world, chunk);
        treePopulator.populate(world, chunk);
        floraPopulator.populate(world, chunk);
    }
}
