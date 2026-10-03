package com.lorcomp.mod;

import com.lorcomp.mod.init.ModWorldGen;
import com.lorcomp.mod.init.ModTileEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Главная точка входа мода LorComp.
 *
 * <p>Класс намеренно содержит только orchestration-код:
 * регистрация подсистем и передача жизненного цикла прокси.</p>
 */
@Mod(
        modid = LorComp.MOD_ID,
        name = LorComp.MOD_NAME,
        version = LorComp.VERSION,
        acceptedMinecraftVersions = "[1.12.2]"
)
public class LorComp {

    /** Уникальный ModID, используемый Forge и системой ресурсов. */
    public static final String MOD_ID = "lorcomp";

    /** Отображаемое имя мода. */
    public static final String MOD_NAME = "LorComp";

    /** Версия текущей сборки. */
    public static final String VERSION = "1.0.0";

    /** Ссылка на экземпляр мода, создаваемый Forge. */
    @Mod.Instance(MOD_ID)
    public static LorComp INSTANCE;

    /**
     * SidedProxy не даёт серверу загрузить клиентские классы рендера.
     */
    @SidedProxy(
            clientSide = "com.lorcomp.mod.ClientProxy",
            serverSide = "com.lorcomp.mod.CommonProxy"
    )
    public static CommonProxy PROXY;

    /**
     * PreInit: регистрируем world generator и запускаем общий preInit.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModTileEntities.register();
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        PROXY.preInit(event);
    }

    /** Init: общий этап регистрации игровой логики. */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init(event);
    }

    /** PostInit: поздние интеграции. */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit(event);
    }
}
