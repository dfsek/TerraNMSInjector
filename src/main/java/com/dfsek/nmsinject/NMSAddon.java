package com.dfsek.nmsinject;

import com.dfsek.terra.api.TerraPlugin;
import com.dfsek.terra.api.addons.TerraAddon;
import com.dfsek.terra.api.addons.annotations.Addon;
import com.dfsek.terra.api.addons.annotations.Author;
import com.dfsek.terra.api.addons.annotations.Version;
import com.dfsek.terra.api.injection.annotations.Inject;

import java.util.logging.Logger;

@Addon("NMS-Injector")
@Author("dfsek")
@Version("0.1.0")
public class NMSAddon extends TerraAddon {
    @Inject
    private TerraPlugin main;

    @Inject
    private Logger logger;

    @Override
    public void initialize() {
        main.getEventManager().registerListener(this, new WorldEventListener(logger, main));
    }
}
