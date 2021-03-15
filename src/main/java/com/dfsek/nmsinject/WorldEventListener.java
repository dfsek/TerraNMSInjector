package com.dfsek.nmsinject;

import com.dfsek.nmsinject.nms.NMSChunkGenerator;
import com.dfsek.nmsinject.nms.NMSTree;
import com.dfsek.terra.api.TerraPlugin;
import com.dfsek.terra.api.event.EventListener;
import com.dfsek.terra.api.event.annotations.Global;
import com.dfsek.terra.api.event.annotations.Priority;
import com.dfsek.terra.api.event.events.config.ConfigPackPreLoadEvent;
import com.dfsek.terra.api.event.events.world.TerraWorldLoadEvent;
import com.dfsek.terra.api.math.vector.Location;
import com.dfsek.terra.api.registry.CheckedRegistry;
import com.dfsek.terra.api.util.collections.MaterialSet;
import com.dfsek.terra.api.world.tree.Tree;
import com.dfsek.terra.bukkit.world.BukkitAdapter;
import com.dfsek.terra.bukkit.world.BukkitTree;
import com.dfsek.terra.config.dummy.DummyWorld;
import com.dfsek.terra.registry.config.TreeRegistry;
import com.dfsek.terra.registry.exception.DuplicateEntryException;
import net.minecraft.server.v1_16_R3.ChunkGenerator;
import net.minecraft.server.v1_16_R3.ChunkProviderServer;
import net.minecraft.server.v1_16_R3.PlayerChunkMap;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.TreeType;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.generator.CustomChunkGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class WorldEventListener implements EventListener {
    private final Logger logger;
    private final TerraPlugin main;

    public WorldEventListener(Logger logger, TerraPlugin main) {
        this.logger = logger;
        this.main = main;
    }

    @Global
    public void onWorldLoad(TerraWorldLoadEvent event) throws NoSuchFieldException, IllegalAccessException {
        if(event.getWorld().getWorld() instanceof DummyWorld) return;
        CraftWorld world = ((CraftWorld) event.getWorld().getWorld().getHandle());

        logger.info("Injecting into world \"" + world.getName() + "\".");

        Field populators = CraftWorld.class.getDeclaredField("populators");
        populators.setAccessible(true);
        ((List<?>) populators.get(world)).clear(); // populators bad

        logger.info("Removed populators.");

        WorldServer worldServer = world.getHandle();
        CustomChunkGenerator chunkGenerator = (CustomChunkGenerator) worldServer.getChunkProvider().getChunkGenerator();

        Field customDelegate = CustomChunkGenerator.class.getDeclaredField("delegate");
        customDelegate.setAccessible(true);

        CustomChunkGenerator newGenerator = new NMSChunkGenerator(worldServer, (ChunkGenerator) customDelegate.get(chunkGenerator), world.getGenerator(), main); // this is evil but it should(tm) work

        Field evilFinalModifier = Field.class.getDeclaredField("modifiers");
        evilFinalModifier.setAccessible(true);

        Field finalGenerator = ChunkProviderServer.class.getDeclaredField("chunkGenerator");
        finalGenerator.setAccessible(true);

        evilFinalModifier.setInt(finalGenerator, finalGenerator.getModifiers() & ~Modifier.FINAL);

        finalGenerator.set(worldServer.getChunkProvider(), newGenerator); // evil, evil I say!

        Field pcmGen = PlayerChunkMap.class.getDeclaredField("chunkGenerator");
        pcmGen.setAccessible(true);
        evilFinalModifier.set(pcmGen, pcmGen.getModifiers() & ~Modifier.FINAL);

        pcmGen.set(worldServer.getChunkProvider().playerChunkMap, newGenerator);

        logger.info("Evil reflection garbage complete B)");
    }

    @Global
    @Priority(Priority.HIGHEST)
    public void injectTrees(ConfigPackPreLoadEvent event) {
        for(TreeType value : TreeType.values()) {
            event.getPack().getTreeRegistry().addUnchecked(BukkitAdapter.TREE_TRANSFORMER.translate(value), new NMSTree(main, value)); // overwrite trees with evil trees
        }
    }
}
