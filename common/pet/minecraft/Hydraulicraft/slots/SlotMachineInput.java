package pet.minecraft.Hydraulicraft.slots;

import pet.minecraft.Hydraulicraft.TileEntities.TileHydraulicCrusher;
import pet.minecraft.Hydraulicraft.TileEntities.TileHydraulicFrictionIncinerator;
import pet.minecraft.Hydraulicraft.TileEntities.TileHydraulicMixer;
import pet.minecraft.Hydraulicraft.TileEntities.TileHydraulicPump;
import pet.minecraft.Hydraulicraft.TileEntities.TileHydraulicWasher;
import pet.minecraft.Hydraulicraft.baseClasses.MachineEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotMachineInput extends Slot {
	private MachineEntity ent;
	
	public SlotMachineInput(IInventory inv, MachineEntity machine, int par2, int par3,
			int par4) {
		super(inv, par2, par3, par4);
		ent = machine;
	}
	
    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
	@Override
    public boolean isItemValid(ItemStack itemStack){
		if(ent instanceof TileHydraulicFrictionIncinerator){
			return ((TileHydraulicFrictionIncinerator)ent).isItemValidForSlot(this.getSlotIndex(), itemStack);
		}else if(ent instanceof TileHydraulicPump){
			return ((TileHydraulicPump) ent).isItemValidForSlot(this.getSlotIndex(), itemStack);
		}else if(ent instanceof TileHydraulicCrusher){
			return ((TileHydraulicCrusher) ent).isItemValidForSlot(this.getSlotIndex(), itemStack);
		}else if(ent instanceof TileHydraulicMixer){
			return ((TileHydraulicMixer) ent).isItemValidForSlot(this.getSlotIndex(), itemStack);
		}else if(ent instanceof TileHydraulicWasher){
			return ((TileHydraulicWasher) ent).isItemValidForSlot(this.getSlotIndex(), itemStack);
		}
		return true;
    }
}
