package erogenousbeef.bigreactors.client;

import welfare93.bigreactors.handlers.TickHandler;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import erogenousbeef.bigreactors.client.renderer.RotorSimpleRenderer;
import erogenousbeef.bigreactors.client.renderer.RotorSpecialRenderer;
import erogenousbeef.bigreactors.client.renderer.SimpleRendererFuelRod;
import erogenousbeef.bigreactors.common.BigReactors;
import erogenousbeef.bigreactors.common.CommonProxy;
import erogenousbeef.bigreactors.common.multiblock.block.BlockFuelRod;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbineRotorPart;
import erogenousbeef.bigreactors.common.multiblock.tileentity.TileEntityTurbineRotorBearing;
import erogenousbeef.bigreactors.gui.BeefGuiIconManager;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static BeefGuiIconManager GuiIcons;

	public ClientProxy() {
		GuiIcons = new BeefGuiIconManager();
	}
	
	@Override
	public void preInit()
	{

	}

	@Override
	public void init()
	{
		super.init();
		FMLCommonHandler.instance().bus().register(new TickHandler());
		
		BlockFuelRod.renderId = RenderingRegistry.getNextAvailableRenderId();
		ISimpleBlockRenderingHandler fuelRodISBRH = new SimpleRendererFuelRod();
		RenderingRegistry.registerBlockHandler(BlockFuelRod.renderId, fuelRodISBRH);
		
		BlockTurbineRotorPart.renderId = RenderingRegistry.getNextAvailableRenderId();
		ISimpleBlockRenderingHandler rotorISBRH = new RotorSimpleRenderer();
		RenderingRegistry.registerBlockHandler(BlockTurbineRotorPart.renderId, rotorISBRH);	
		
		if(BigReactors.blockTurbinePart != null) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurbineRotorBearing.class, new RotorSpecialRenderer());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SubscribeEvent 
	public void registerBlockIcons(TextureStitchEvent.Pre event) {
		if(event.map.getTextureType() == BeefIconManager.TERRAIN_TEXTURE) {
			BigReactors.registerNonBlockFluidIcons(event.map);
			GuiIcons.registerBlockIcons(event.map);
		}
		// else if(event.map.textureType == BeefIconManager.ITEM_TEXTURE) { }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SubscribeEvent 
	public void setIcons(TextureStitchEvent.Post event) {
		BigReactors.setNonBlockFluidIcons();
	}
}
