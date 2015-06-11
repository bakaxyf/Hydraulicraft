package k4unl.minecraft.Hydraulicraft.tileEntities.consumers;


import k4unl.minecraft.Hydraulicraft.api.IHydraulicConsumer;
import k4unl.minecraft.Hydraulicraft.lib.recipes.*;
import k4unl.minecraft.Hydraulicraft.tileEntities.TileHydraulicBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTank;

public class TileAssembler extends TileHydraulicBase implements IHydraulicConsumer, IInventory, IFluidCraftingMachine {
    InventoryFluidCrafting    inventoryCrafting;
    InventoryFluidCraftResult inventoryResult;
    IFluidRecipe              recipe;
    int workProgress = 0;

    public TileAssembler() {
        super(10);
        super.init(this);
        FluidTank[] tanks = new FluidTank[1];

        // TODO size of assembler's crafting tank
        tanks[0] = new FluidTank(4000);
        inventoryCrafting = new InventoryFluidCrafting(this, 3, tanks, null);
        inventoryResult = new InventoryFluidCraftResult(this);
    }

    @Override
    public float workFunction(boolean simulate, ForgeDirection from) {
        if (recipe != null) {
            if (inventoryResult.getStackInSlot(0) != null &&
                    !Helpers.canMergeStacks(inventoryResult.getStackInSlot(0), recipe.getRecipeOutput()))
                return 0;

            if (simulate) {
                return 0.1f;
            }

            if (workProgress == 0) {
                inventoryCrafting.eatItems();
            }

            workProgress++;
            inventoryCrafting.eatFluids(recipe, 1f / 20);

            // TODO get assembly time
            if (workProgress == 20) {
                inventoryResult.setInventorySlotContents(0,
                        Helpers.mergeStacks(recipe.getCraftingResult(inventoryCrafting.getInventoryCrafting()),
                                inventoryResult.getStackInSlot(0)));
                markDirty();
                onCraftingMatrixChanged();
            }

            return 0.1f;
        }

        // TODO get fluid used in assembler
        return 0;
    }

    @Override
    public boolean canWork(ForgeDirection dir) {
        return true;
    }

    @Override
    public void onFluidLevelChanged(int old) {

    }

    @Override
    public boolean canConnectTo(ForgeDirection side) {
        return true;
    }

    public int getScaledAssembleTime() {
        return 0;
    }

    @Override
    public int getSizeInventory() {
        return inventoryCrafting.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventoryCrafting.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return inventoryCrafting.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventoryCrafting.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        inventoryCrafting.setInventorySlotContents(slot, itemStack);
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventoryCrafting.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return inventoryCrafting.isItemValidForSlot(slot, itemStack);
    }

    public InventoryFluidCrafting getFluidInventory() {
        return inventoryCrafting;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        inventoryCrafting.save(tagCompound);

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        inventoryCrafting.load(tagCompound);
    }

    public InventoryCraftResult getInventoryResult() {
        return inventoryResult;
    }

    @Override
    public void onCraftingMatrixChanged() {
        recipe = HydraulicRecipes.getAssemblerRecipe(inventoryCrafting);
        if (recipe != null) {
            workProgress = 0;
        }
    }

    @Override
    public void spawnOverflowItemStack(ItemStack stack) {
        worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, stack));
    }
}
