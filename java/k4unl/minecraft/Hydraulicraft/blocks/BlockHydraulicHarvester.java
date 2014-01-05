package k4unl.minecraft.Hydraulicraft.blocks;

import java.util.List;

import k4unl.minecraft.Hydraulicraft.TileEntities.harvester.TileHarvesterFrame;
import k4unl.minecraft.Hydraulicraft.TileEntities.harvester.TileHarvesterTrolley;
import k4unl.minecraft.Hydraulicraft.TileEntities.harvester.TileHydraulicHarvester;
import k4unl.minecraft.Hydraulicraft.baseClasses.MachineBlockContainer;
import k4unl.minecraft.Hydraulicraft.lib.config.Ids;
import k4unl.minecraft.Hydraulicraft.lib.config.ModInfo;
import k4unl.minecraft.Hydraulicraft.lib.config.Names;
import k4unl.minecraft.Hydraulicraft.lib.helperClasses.Name;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHydraulicHarvester extends MachineBlockContainer {
	private Icon[] icons;
	private Icon[] topIcons;
	private Icon[] bottomIcons;
	
	private Name[] mName;
	
	protected boolean hasTopIcon[] = {
			false, false, false
	};
	protected boolean hasBottomIcon[] = {
			false, false, false
	};
	
	
	protected BlockHydraulicHarvester() {
		super(Ids.blockHydraulicHarvester, Names.blockHydraulicHarvester[0]);
		
		mName = Names.blockHydraulicHarvester;
		
		icons = new Icon[mName.length];
		topIcons = new Icon[mName.length];
		bottomIcons = new Icon[mName.length];
	}

    @Override
    public int getRenderType(){
        return -1;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata){
		switch(metadata){
		case 0:
			return new TileHydraulicHarvester();
		case 1:
			return new TileHarvesterFrame();
		case 2:
			return new TileHarvesterTrolley();
		}
        return null;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list){
		for(int i = 0; i < mName.length; i++){
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int par6, float par7, float par8, float par9) {
		return false;
	}
	
	private String getTextureName(String side, int subId){
		if(side != ""){
			return ModInfo.LID + ":" + mName[subId].unlocalized + "_" + side;
		}else{
			return ModInfo.LID + ":" + mName[subId].unlocalized;
		}
	}
	
	@Override
	public void registerIcons(IconRegister iconRegistry){
		for(int i = 0; i < mName.length; i++){
			icons[i] = iconRegistry.registerIcon(getTextureName("sides",i));
			if(hasTopIcon[i]){
				topIcons[i] = iconRegistry.registerIcon(getTextureName("top",i));
			}else{
				topIcons[i] = icons[i];
			}
			if(hasBottomIcon[i]){
				bottomIcons[i] = iconRegistry.registerIcon(getTextureName("bottom",i));
			}else{
				bottomIcons[i] = icons[i];
			}
		}
	}
	
	
	
	@Override
	public Icon getIcon(int side, int metadata){
		ForgeDirection s = ForgeDirection.getOrientation(side);
		if(s.equals(ForgeDirection.UP)){
			return topIcons[metadata];
		}else if(s.equals(ForgeDirection.DOWN)){
			return bottomIcons[metadata];
		}

		return icons[metadata];
		
	}
	
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileHarvesterFrame){
			//Check if we should rotate.
			if(world.getBlockId(x, y, z+1) == Ids.blockHydraulicHarvester.act 
					|| world.getBlockId(x, y, z-1) == Ids.blockHydraulicHarvester.act
					|| world.getBlockId(x, y, z+1) == Ids.blockHydraulicHarvester.act
					|| world.getBlockId(x, y, z-1) == Ids.blockHydraulicHarvester.act){
				//Frame block placed.
				//Rotate.
				((TileHarvesterFrame)tile).setRotated(true);
			}else{
				((TileHarvesterFrame)tile).setRotated(false);
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y,
				int z, int blockId) {
		super.onNeighborBlockChange(world, x, y, z, blockId);
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileHarvesterFrame){
			//Check if we should rotate.
			if(world.getBlockId(x, y, z+1) == Ids.blockHydraulicHarvester.act 
					|| world.getBlockId(x, y, z-1) == Ids.blockHydraulicHarvester.act
					|| world.getBlockId(x, y, z+1) == Ids.blockHydraulicHarvester.act
					|| world.getBlockId(x, y, z-1) == Ids.blockHydraulicHarvester.act){
				//Frame block placed.
				//Rotate.
				((TileHarvesterFrame)tile).setRotated(true);
			}else{
				((TileHarvesterFrame)tile).setRotated(false);
			}
		}
	}
	

}
