package de.katzenpapst.amunra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import _net.rom.stellar.IMod;
import _net.rom.stellar.network.NetworkHandler;
import de.katzenpapst.amunra.command.CommandMothershipForceArrive;
import de.katzenpapst.amunra.command.CommandMothershipInfo;
import de.katzenpapst.amunra.command.CommandMoveMothership;
import de.katzenpapst.amunra.command.CommandShuttleTeleport;
import de.katzenpapst.amunra.config.ARConfig;
import de.katzenpapst.amunra.crafting.RecipeHelper;
import de.katzenpapst.amunra.entity.EntityCryoArrow;
import de.katzenpapst.amunra.entity.EntityLaserArrow;
import de.katzenpapst.amunra.entity.EntityOsirisBossFireball;
import de.katzenpapst.amunra.entity.spaceship.EntityShuttle;
import de.katzenpapst.amunra.entity.spaceship.EntityShuttleFake;
import de.katzenpapst.amunra.event.CraftingHandler;
import de.katzenpapst.amunra.event.EventHandlerAR;
import de.katzenpapst.amunra.event.FurnaceHandler;
import de.katzenpapst.amunra.helper.InteroperabilityHelper;
import de.katzenpapst.amunra.mob.RobotVillagerProfession;
import de.katzenpapst.amunra.mob.entity.EntityARVillager;
import de.katzenpapst.amunra.mob.entity.EntityAlienBug;
import de.katzenpapst.amunra.mob.entity.EntityMummyBoss;
import de.katzenpapst.amunra.mob.entity.EntityPorcodon;
import de.katzenpapst.amunra.mob.entity.EntityRobotVillager;
import de.katzenpapst.amunra.mob.entity.EntitySentry;
import de.katzenpapst.amunra.mothership.MothershipWorldProvider;
import de.katzenpapst.amunra.network.ARChannelHandler;
import de.katzenpapst.amunra.network.packet.ConnectionPacketAR;
import de.katzenpapst.amunra.old.block.ARBlocks;
import de.katzenpapst.amunra.old.item.ARItems;
import de.katzenpapst.amunra.old.tile.TileEntityARChest;
import de.katzenpapst.amunra.old.tile.TileEntityARChestLarge;
import de.katzenpapst.amunra.old.tile.TileEntityBlockScale;
import de.katzenpapst.amunra.old.tile.TileEntityBossDungeonSpawner;
import de.katzenpapst.amunra.old.tile.TileEntityGravitation;
import de.katzenpapst.amunra.old.tile.TileEntityHydroponics;
import de.katzenpapst.amunra.old.tile.TileEntityIsotopeGenerator;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipController;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineBooster;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineBoosterIon;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineIon;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineJet;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipSettings;
import de.katzenpapst.amunra.old.tile.TileEntityShuttleDock;
import de.katzenpapst.amunra.old.tile.TileEntityShuttleDockFake;
import de.katzenpapst.amunra.proxy.ARSidedProxy;
import de.katzenpapst.amunra.tick.ConnectionEvents;
import de.katzenpapst.amunra.tick.TickHandlerServer;
import de.katzenpapst.amunra.world.anubis.AnubisWorldProvider;
import de.katzenpapst.amunra.world.horus.HorusWorldProvider;
import de.katzenpapst.amunra.world.maahes.MaahesWorldProvider;
import de.katzenpapst.amunra.world.mehen.MehenWorldProvider;
import de.katzenpapst.amunra.world.neper.NeperWorldProvider;
import de.katzenpapst.amunra.world.seth.SethWorldProvider;
import mcp.MethodsReturnNonnullByDefault;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody.ScalableDistance;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.dimension.TeleportTypeMoon;
import micdoodle8.mods.galacticraft.core.dimension.TeleportTypeOverworld;
import micdoodle8.mods.galacticraft.core.dimension.TeleportTypeSpaceStation;
import micdoodle8.mods.galacticraft.core.util.CreativeTabGC;
import micdoodle8.mods.galacticraft.planets.asteroids.dimension.TeleportTypeAsteroids;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = AmunRa.MODID, version = AmunRa.VERSION, dependencies = AmunRa.DEPENDENCIES, name = AmunRa.MODNAME, acceptedMinecraftVersions = "1.12")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AmunRa implements IMod {
	
	public static final String MODID = "amunra";
	public static final String MODNAME = "Amun-Ra";
	public static final String VERSION = "0.1.0";
    public static final int BUILD_NUM = 0;
    public static final String DEPENDENCIES = "required-after:galacticraftcore@[4.0.2.261,]; required-after:galacticraftplanets;";
    public static NetworkHandler network;
    
    public static final Logger LOGGER = LogManager.getLogger(MODNAME);


	public static ARChannelHandler packetPipeline;

	@Instance(AmunRa.MODID)
	public static AmunRa instance;

	public static final String ASSETPREFIX = "amunra";
	public static final String TEXTUREPREFIX = ASSETPREFIX + ":";

	public static CreativeTabGC arTab;
	public static int chestRenderId;
	public static int msBoosterRendererId;

	public static int multiOreRendererId;
	public static int dummyRendererId;
	public static final ARConfig config = new ARConfig();
	@SidedProxy(clientSide = "de.katzenpapst.amunra.proxy.ClientProxy", serverSide = "de.katzenpapst.amunra.proxy.ServerProxy")
	public static ARSidedProxy proxy;
	public static DimensionType mothershipDimensionType;
	public Star starRa = null;

	public Planet starAmun = null;
	public SolarSystem systemAmunRa = null;
	public Planet planetOsiris = null;
	public Planet planetHorus = null;
	public Planet planetBaal = null;
	public Planet planetAnubis = null;
	public Planet asteroidBeltMehen = null;
	public Planet planetSekhmet = null;

	public Moon moonBaalRings = null;

	public Moon moonKhonsu;

	public Moon moonNeper;
	public Moon moonIah;
	public Moon moonBastet;
	public Moon moonMaahes;

	public Moon moonThoth;

	public Moon moonSeth;
	public Moon moonKebe;

	protected ArrayList<ResourceLocation> possibleMothershipTextures = new ArrayList<ResourceLocation>();

	// STARTING HERE FOR ANYTHING ADDED FOR READABILITY

	protected ArrayList<ResourceLocation> possibleAsteroidTextures = new ArrayList<ResourceLocation>();

	/**
	 * Automatically incrementing ID number for registering entities.
	 */
	private int lastEntityId = -1;

	ModContainer mod = Objects.requireNonNull(Loader.instance().activeModContainer());

	public void addPossibleMothershipTexture(ResourceLocation loc) {
		possibleMothershipTextures.add(loc);
	}

	protected Moon createMoon(String name, String texture, double phaseShift, double distance, double orbitTime) {
		Moon pl = new Moon(name);
		setCelestialBodyStuff(pl, texture, phaseShift, distance, orbitTime);
		return pl;
	}

	protected Planet createPlanet(String name, String texture, double phaseShift, double distance, double orbitTime) {
		Planet pl = new Planet(name);
		setCelestialBodyStuff(pl, texture, phaseShift, distance, orbitTime);
		return pl;
	}

	private void doCompatibilityChecks() {
		// sanity checks go here
		// verify crafting
		RecipeHelper.verifyNasaWorkbenchCrafting();

		// verify mothership provider ID
		config.verifyMothershipProviderId();

	}

	public List<ResourceLocation> getPossibleAsteroidTextures() {
		return (List<ResourceLocation>) possibleAsteroidTextures.clone();
	}

	public List<ResourceLocation> getPossibleMothershipTextures() {
		return (List<ResourceLocation>) possibleMothershipTextures.clone();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		AmunRa.arTab = new CreativeTabGC(CreativeTabs.getNextID(), "AmunRaTab", null, null);
		AmunRa.arTab.setItemForTab(new ItemStack(ARItems.shuttleItem));

		packetPipeline = ARChannelHandler.init();

		initCelestialBodies();
		initCreatures();
		registerTileEntities();
		initOtherEntities();
		RecipeHelper.initRecipes();

		proxy.init(event);
	}

	protected void initCelestialBodies() {

		systemAmunRa = new SolarSystem("systemAmunRa", "milkyWay");
		starRa = new Star("starRa");
		systemAmunRa.setMainStar(starRa).setMapPosition(new Vector3(1.5F, -1.15F, 0.0F));
		GalaxyRegistry.registerSolarSystem(systemAmunRa);

		starRa.setBodyIcon(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/celestialbodies/sun-red2.png"));
		starRa.setParentSolarSystem(systemAmunRa);

		starAmun = createPlanet("starAmun", "sun-blue.png", Math.PI * 0.1, 0.7, 0.9);
		starAmun.setRelativeSize(3.0F);
		starAmun.setParentSolarSystem(systemAmunRa);
		GalaxyRegistry.registerPlanet(starAmun);

		// two inner planets
		planetOsiris = createPlanet("osiris", "planet-mercury.png", Math.PI * 0.8, 0.34, 0.4);
		planetOsiris.setParentSolarSystem(systemAmunRa);
		planetOsiris.setRelativeSize(0.8F);
		GalaxyRegistry.registerPlanet(planetOsiris);

		planetHorus = createPlanet("horus", "planet-horus.png", Math.PI * 1.3, 0.55, 0.458);
		planetHorus.setRelativeSize(1.05F);
		planetHorus.setParentSolarSystem(systemAmunRa);
		planetHorus.setDimensionInfo(config.dimHorus, HorusWorldProvider.class);
		GalacticraftRegistry.registerTeleportType(HorusWorldProvider.class, new TeleportTypeMoon());
		planetHorus.setTierRequired(config.planetDefaultTier);
		GalaxyRegistry.registerPlanet(planetHorus);

		// gas giant
		planetBaal = createPlanet("baal", "planet-gas03.png", Math.PI * 1.9, 1.2, 1.4);
		planetBaal.setParentSolarSystem(systemAmunRa);
		planetBaal.setRelativeSize(2.2F);
		GalaxyRegistry.registerPlanet(planetBaal);

		// .. and its moons
		// ring, aka innermost moon
		// the regular moon has a distance of 13
		moonBaalRings = createMoon("baalRings", "micromoon.png", 1.58, 9, 100);
		moonBaalRings.setParentPlanet(planetBaal);
		GalaxyRegistry.registerMoon(moonBaalRings);

		// moon god, but something to do with the creation of life? so maybe stuff here as well
		moonKhonsu = createMoon("khonsu", "moon.png", 1.9 * Math.PI, 12.45, 110);
		moonKhonsu.setParentPlanet(planetBaal);
		moonKhonsu.setRelativeSize(0.45F);
		GalaxyRegistry.registerMoon(moonKhonsu);

		// this will have an oxygen atmosphere. neper was some kind of a grain god, so
		moonNeper = createMoon("neper", "planet-life-o2.png", 1.58, 14.9, 140);
		moonNeper.atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		moonNeper.atmosphereComponent(EnumAtmosphericGas.OXYGEN);
		moonNeper.atmosphereComponent(EnumAtmosphericGas.ARGON);
		moonNeper.atmosphereComponent(EnumAtmosphericGas.HELIUM);
		moonNeper.setDimensionInfo(config.dimNeper, NeperWorldProvider.class);
		moonNeper.setParentPlanet(planetBaal);
		moonNeper.setTierRequired(config.planetDefaultTier);
		moonNeper.setRelativeSize(0.89F);
		GalacticraftRegistry.registerTeleportType(NeperWorldProvider.class, new TeleportTypeOverworld());
		// GalacticraftRegistry.registerTeleportType(WorldProviderMoon.class, new TeleportTypeMoon());
		// GalacticraftRegistry.registerTeleportType(WorldProviderSurface.class, new TeleportTypeOverworld());
		GalaxyRegistry.registerMoon(moonNeper);

		// just some dead rock. iah was a moon god
		moonIah = createMoon("iah", "moon.png", 3.1, 18.5, 162);
		moonIah.setParentPlanet(planetBaal);
		moonIah.setRelativeSize(0.21F);
		GalaxyRegistry.registerMoon(moonIah);

		// an asteroid belt. todo figure the other stuff out later
		asteroidBeltMehen = createPlanet("asteroidBeltMehen", "micromoon.png", Math.PI * 0.19, 1.4, 1.6);
		asteroidBeltMehen.setParentSolarSystem(systemAmunRa);
		asteroidBeltMehen.setDimensionInfo(config.dimMehen, MehenWorldProvider.class);
		asteroidBeltMehen.setTierRequired(config.planetDefaultTier);
		GalacticraftRegistry.registerTeleportType(MehenWorldProvider.class, new TeleportTypeAsteroids());
		GalaxyRegistry.registerPlanet(asteroidBeltMehen);

		// another gas giant?
		planetSekhmet = createPlanet("sekhmet", "planet-gas02.png", Math.PI * 0.6, 1.6, 1.8);
		planetSekhmet.setParentSolarSystem(systemAmunRa);
		planetSekhmet.setRelativeSize(2.42F);
		GalaxyRegistry.registerPlanet(planetSekhmet);

		// ... and it's moons
		// cat goddess, of course it's a moon of sekhmet
		moonBastet = createMoon("bast", "moon.png", 3.1, 9.8, 122);
		moonBastet.setParentPlanet(planetSekhmet);
		moonBastet.setRelativeSize(0.758F);
		GalaxyRegistry.registerMoon(moonBastet);

		// lion goddess, dito
		moonMaahes = createMoon("maahes", "planet-life-ch4.png", 4.514, 11.4, 136);
		moonMaahes.setRelativeSize(0.912F);
		moonMaahes.setParentPlanet(planetSekhmet);
		moonMaahes.atmosphereComponent(EnumAtmosphericGas.CO2);
		moonMaahes.atmosphereComponent(EnumAtmosphericGas.METHANE);
		moonMaahes.atmosphereComponent(EnumAtmosphericGas.HYDROGEN);
		moonMaahes.atmosphereComponent(EnumAtmosphericGas.ARGON);
		moonMaahes.setDimensionInfo(config.dimMaahes, MaahesWorldProvider.class);
		moonMaahes.setTierRequired(config.planetDefaultTier);
		GalacticraftRegistry.registerTeleportType(MaahesWorldProvider.class, new TeleportTypeOverworld());

		GalaxyRegistry.registerMoon(moonMaahes);

		moonThoth = createMoon("thoth", "moon.png", 1.9, 15.5, 145);
		moonThoth.setRelativeSize(0.68F);
		moonThoth.setParentPlanet(planetSekhmet);
		GalaxyRegistry.registerMoon(moonThoth);

		// this will be the ice ocean moon now
		moonSeth = createMoon("seth", "planet-ice2.png", 6, 17.98, 198);
		moonSeth.setRelativeSize(0.457F);
		moonSeth.setParentPlanet(planetSekhmet);
		// moonSeth.atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		moonSeth.setDimensionInfo(config.dimSeth, SethWorldProvider.class);
		moonSeth.setTierRequired(config.planetDefaultTier);
		GalacticraftRegistry.registerTeleportType(SethWorldProvider.class, new TeleportTypeMoon());
		GalaxyRegistry.registerMoon(moonSeth);

		// a small rocky planet
		planetAnubis = createPlanet("anubis", "moon.png", Math.PI * 0.36, 1.9, 2.2);
		planetAnubis.setParentSolarSystem(systemAmunRa);
		planetAnubis.setDimensionInfo(config.dimAnubis, AnubisWorldProvider.class);
		planetAnubis.setRelativeSize(0.65F);
		GalacticraftRegistry.registerTeleportType(AnubisWorldProvider.class, new TeleportTypeMoon());
		planetAnubis.setTierRequired(config.planetDefaultTier);
		GalaxyRegistry.registerPlanet(planetAnubis);

		// ..with a moon nonetheless
		moonKebe = createMoon("kebe", "moon.png", 5.1, 19, 253);
		moonKebe.setRelativeSize(0.32F);
		moonKebe.setParentPlanet(planetAnubis);
		GalaxyRegistry.registerMoon(moonKebe);

		// For motherships:
		// TODO Redo this but keep the catching exemption for now
		try {
			mothershipDimensionType = DimensionType.register("mothership", "_mothership", config.mothershipProviderID, MothershipWorldProvider.class, false);
		} catch (RuntimeException e) {
			throw new RuntimeException("Could not register provider mothership provider ID. Please change I:mothershipProviderID in the config.");
		}
		GalacticraftRegistry.registerTeleportType(MothershipWorldProvider.class, new TeleportTypeSpaceStation());

		// default stuff
		config.setStaticConfigValues();
	}

	protected void initCreatures() {
		registerEntity(EntityPorcodon.class, "porcodon", 60, 60, false);

		registerEntity(EntityARVillager.class, "alienVillagerAR", 60, 60, false);
		registerEntity(EntityRobotVillager.class, "robotVillager", 60, 60, false);

		registerEntity(EntitySentry.class, "sentryRobot", 60, 60, false);

		registerEntity(EntityAlienBug.class, "alienBug", 60, 60, false);

		registerEntity(EntityMummyBoss.class, "osirisBoss", 60, 60, false);

		// register trading stuff
		registerTrading();

	}

	protected void initOtherEntities() {
		registerEntity(EntityLaserArrow.class, "laserArrow", 150, 5, true);
		registerEntity(EntityCryoArrow.class, "cryoArrow", 150, 5, true);
		registerEntity(EntityShuttle.class, "Shuttle", 150, 1, false);
		registerEntity(EntityShuttleFake.class, "ShuttleFake", 150, 5, false);
		registerEntity(EntityOsirisBossFireball.class, "cryoArrow", 150, 5, true);
	}

	protected void loadJsonConfig() {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);

		MinecraftForge.EVENT_BUS.register(new GuiHandler());
		FMLCommonHandler.instance().bus().register(new TickHandlerServer());

		// failsafes
		doCompatibilityChecks();

		// mod compatibility
		InteroperabilityHelper.initCompatibility();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration configFile = new Configuration(event.getSuggestedConfigurationFile());

		config.processConfig(configFile);

		ARBlocks.initBlocks();
		ARItems.initItems();
		MinecraftForge.EVENT_BUS.register(new EventHandlerAR());
		MinecraftForge.EVENT_BUS.register(new CraftingHandler());

		GameRegistry.registerFuelHandler(new FurnaceHandler());

		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/0.png"));
		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/1.png"));
		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/2.png"));
		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/3.png"));
		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/4.png"));
		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/5.png"));
		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/6.png"));
		possibleMothershipTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/mothership_icons/7.png"));

		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/0.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/1.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/2.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/3.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/4.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/5.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/6.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/7.png"));
		possibleAsteroidTextures.add(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/asteroid_icons/8.png"));

		ConnectionPacketAR.bus = NetworkRegistry.INSTANCE.newEventDrivenChannel(ConnectionPacketAR.CHANNEL);
		ConnectionPacketAR.bus.register(new ConnectionPacketAR());

		FMLCommonHandler.instance().bus().register(new ConnectionEvents());

		proxy.preInit(event);
	}

	// HOLDER FOR NOW TO ALLOW ENTITY REGISTRY
	public void registerEntity(Class<? extends Entity> entityClass, String key, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		registerEntity(entityClass, key, ++this.lastEntityId, this.mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public void registerEntity(Class<? extends Entity> entityClass, String key, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		ResourceLocation resource = new ResourceLocation(AmunRa.MODID, key);
		EntityRegistry.registerModEntity(resource, entityClass, key, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityIsotopeGenerator.class, "AmunRa Atomic Battery");
		GameRegistry.registerTileEntity(TileEntityMothershipController.class, "AmunRa Mothership Controller");
		GameRegistry.registerTileEntity(TileEntityMothershipEngineJet.class, "AmunRa Mothership Engine");
		GameRegistry.registerTileEntity(TileEntityMothershipSettings.class, "AmunRa Mothership Settings");
		GameRegistry.registerTileEntity(TileEntityMothershipEngineBooster.class, "AmunRa Mothership Engine Booster");

		GameRegistry.registerTileEntity(TileEntityMothershipEngineIon.class, "AmunRa Mothership Ion Engine");
		GameRegistry.registerTileEntity(TileEntityMothershipEngineBoosterIon.class, "AmunRa Mothership Ion Engine Booster");

		GameRegistry.registerTileEntity(TileEntityBlockScale.class, "AmunRa Block Scale");

		GameRegistry.registerTileEntity(TileEntityShuttleDock.class, "AmunRa Shuttle Dock");
		GameRegistry.registerTileEntity(TileEntityShuttleDockFake.class, "AmunRa Shuttle Dock Fake");

		GameRegistry.registerTileEntity(TileEntityHydroponics.class, "AmunRa Hydroponics");
		GameRegistry.registerTileEntity(TileEntityGravitation.class, "AmunRa Gravity Engine");
		GameRegistry.registerTileEntity(TileEntityBossDungeonSpawner.class, "AmunRa Dungeon Spawner Osiris");

		GameRegistry.registerTileEntity(TileEntityARChest.class, "AmunRa Chest");
		GameRegistry.registerTileEntity(TileEntityARChestLarge.class, "AmunRa Chest Large");
	}

	protected void registerTrading() {
		RobotVillagerProfession.addProfession(new RobotVillagerProfession(new ResourceLocation(Constants.ASSET_PREFIX, "textures/blocks/electricFurnace.png"), "furnace").addRecipe(Items.BEEF, 4, Items.COOKED_BEEF).addRecipe(new ItemStack(Items.IRON_AXE, 1), new ItemStack(Items.EMERALD, 6), new ItemStack(Items.IRON_INGOT, 3)).addRecipe(new ItemStack(Items.IRON_DOOR, 1), new ItemStack(Items.EMERALD, 12), new ItemStack(Items.IRON_INGOT, 6))
				.addRecipe(new ItemStack(Items.IRON_HOE, 1), new ItemStack(Items.EMERALD, 4), new ItemStack(Items.IRON_INGOT, 2)).addRecipe(new ItemStack(Items.IRON_PICKAXE, 1), new ItemStack(Items.EMERALD, 6), new ItemStack(Items.IRON_INGOT, 3)).addRecipe(new ItemStack(Items.IRON_SHOVEL, 1), new ItemStack(Items.EMERALD, 2), new ItemStack(Items.IRON_INGOT, 1)));

		ItemStack emptyCan = new ItemStack(GCItems.oilCanister, 1, GCItems.oilCanister.getMaxDamage());

		// offers oxygen refill, and maybe other stuff, TBD
		RobotVillagerProfession.addProfession(new RobotVillagerProfession(new ResourceLocation(Constants.ASSET_PREFIX, "textures/blocks/machine_compressor_1.png"), "compressor").addRecipe(emptyCan, new ItemStack(Items.EMERALD, 24), new ItemStack(AsteroidsItems.canisterLOX, 1, 1)).addRecipe(emptyCan, new ItemStack(Items.EMERALD, 4), new ItemStack(AsteroidsItems.canisterLN2, 1, 1)).addRecipe(new ItemStack(Items.EMERALD, 2), emptyCan)
				.addRecipe(new ItemStack(GCItems.oxTankLight, 1, GCItems.oxTankLight.getMaxDamage()), new ItemStack(Items.EMERALD, 4), new ItemStack(GCItems.oxTankLight, 1)).addRecipe(new ItemStack(GCItems.oxTankMedium, 1, GCItems.oxTankMedium.getMaxDamage()), new ItemStack(Items.EMERALD, 8), new ItemStack(GCItems.oxTankMedium, 1)).addRecipe(new ItemStack(GCItems.oxTankHeavy, 1, GCItems.oxTankHeavy.getMaxDamage()), new ItemStack(Items.EMERALD, 16), new ItemStack(GCItems.oxTankHeavy, 1)));

		/*
		 * can't make the battery work, because it resets on being crafted // register battery refill RobotVillagerProfession.addProfession(new RobotVillagerProfession( new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/blocks/coalGenerator.png"), "generator") .addRecipe(new ItemStack(GCItems.battery, 1, GCItems.battery.getMaxDamage()), new ItemStack(Items.emerald, 8) , new ItemStack(GCItems.battery, 1, 50)) );
		 */
		RobotVillagerProfession.addProfession(new RobotVillagerProfession(new ResourceLocation(Constants.ASSET_PREFIX, "textures/blocks/refinery_front.png"), "refinery").addRecipe(new ItemStack(GCItems.oilCanister, 1, 1), new ItemStack(Items.EMERALD, 16), new ItemStack(GCItems.fuelCanister, 1, 1)).addRecipe(emptyCan, new ItemStack(Items.EMERALD, 26), new ItemStack(GCItems.fuelCanister, 1, 1)));
		RobotVillagerProfession.addProfession(new RobotVillagerProfession(new ResourceLocation(Constants.ASSET_PREFIX, "textures/blocks/electric_compressor.png"), "ingotcompressor").addRecipe(new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.EMERALD, 4), new ItemStack(GCItems.basicItem, 1, 11))// 11 = iron

				.addRecipe(new ItemStack(GCItems.basicItem, 2, 5), new ItemStack(Items.EMERALD, 4), new ItemStack(GCItems.basicItem, 1, 8))// 8 = alu
				.addRecipe(new ItemStack(GCItems.basicItem, 2, 4), new ItemStack(Items.EMERALD, 4), new ItemStack(GCItems.basicItem, 1, 7))// 7 = tin
				.addRecipe(new ItemStack(GCItems.basicItem, 2, 3), new ItemStack(Items.EMERALD, 4), new ItemStack(GCItems.basicItem, 1, 6))// 6 = copper
		);

		RobotVillagerProfession.addProfession(new RobotVillagerProfession(new ResourceLocation(Constants.ASSET_PREFIX, "textures/blocks/circuit_fabricator.png"), "circuitfabricator").addRecipe(new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.EMERALD, 4), new ItemStack(GCItems.basicItem, 9, 12))// solar thingys
				.addRecipe(new ItemStack(Blocks.REDSTONE_TORCH), new ItemStack(Items.EMERALD, 6), new ItemStack(GCItems.basicItem, 3, 13))// basic wafer
				.addRecipe(new ItemStack(Items.REPEATER), new ItemStack(Items.EMERALD, 8), new ItemStack(GCItems.basicItem, 2, 14))// advanced wafer
				.addRecipe(new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.EMERALD, 10), ARItems.baseItem.getItemStack("waferEnder", 1))// ender wafer

		);

		/*
		 * RobotVillagerProfession.addProfession(new RobotVillagerProfession( new ResourceLocation(AmunRa.ASSETPREFIX, "textures/blocks/crafter.png"), "crafter") .addRecipe(new ItemStack(Items.dye, 1, 4), new ItemStack(Items.emerald, 4), new ItemStack(GCItems.basicItem, 9, 12))
		 * 
		 * 
		 * 
		 * );
		 */
	}

	@EventHandler
	public void serverInit(FMLServerStartedEvent event) {
		TickHandlerServer.restart();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandShuttleTeleport());
		event.registerServerCommand(new CommandMoveMothership());
		event.registerServerCommand(new CommandMothershipInfo());
		event.registerServerCommand(new CommandMothershipForceArrive());
	}

	protected void setCelestialBodyStuff(CelestialBody body, String texture, double phaseShift, double distance, double orbitTime) {
		body.setBodyIcon(new ResourceLocation(AmunRa.ASSETPREFIX, "textures/gui/celestialbodies/" + texture)).setPhaseShift((float) phaseShift).setRelativeDistanceFromCenter(new ScalableDistance((float) distance, (float) distance)).setRelativeOrbitTime((float) orbitTime);
	}
	/*
	 * @SideOnly(Side.CLIENT) public void setClientMothershipData(MothershipWorldData data) { mothershipDataClient = data; }
	 * 
	 * public MothershipWorldData getMothershipData() { if(FMLCommonHandler.instance().getSide() == Side.CLIENT) { return this.mothershipDataClient; } return TickHandlerServer.mothershipData;
	 * 
	 * }
	 * 
	 */

	@Override
	public String getModId() {
		return MODID;
	}

	@Override
	public String getModName() {
		return MODNAME;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	public int getBuildNum() {
		return BUILD_NUM;
	}
}
