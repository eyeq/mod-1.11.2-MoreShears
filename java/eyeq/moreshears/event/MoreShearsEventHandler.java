package eyeq.moreshears.event;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import eyeq.moreshears.MoreShears;

public class MoreShearsEventHandler {
    @SubscribeEvent
    public void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Entity entity = event.getTarget();
        World world = entity.world;
        if(world.isRemote || !(entity instanceof EntitySheep)) {
            return;
        }
        EntitySheep sheep = (EntitySheep) entity;
        if(sheep.isChild()) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        EnumHand hand = event.getHand();
        ItemStack itemStack = player.getHeldItem(hand);
        Item item = itemStack.getItem();

        if(sheep.getSheared()) {
            if(item == MoreShears.shearsIronAndDiamond) {
                sheep.setSheared(false);
                itemStack.damageItem(10, player);
                event.setCanceled(true);
            }
            return;
        }
        if(item == MoreShears.shearsGoldAndIron) {
            shearsGoldAndIron(player, world);
            return;
        }
        if(item == MoreShears.shearsDiamond) {
            shearsDiamond(player, world, itemStack);
            return;
        }

        if(item == MoreShears.shearsDiamondAndGold) {
            dropItem(sheep, new ItemStack(Blocks.WOOL, 1, sheep.getFleeceColor().getMetadata()));
        } else if(item == MoreShears.shearsWood) {
            sheep.setSheared(true);
        } else if(item == MoreShears.shearsStone) {
            sheep.setSheared(true);
            dropItem(sheep, new ItemStack(Items.STRING));
        } else if(item == MoreShears.shearsGold) {
            sheep.setSheared(true);
            dropItem(sheep, new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, EnumDyeColor.YELLOW.getMetadata()));
        } else {
            return;
        }
        itemStack.damageItem(1, player);
        sheep.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
    }

    private void shearsGoldAndIron(EntityPlayer player, World world) {
        for(EntitySheep sheep : world.getEntitiesWithinAABB(EntitySheep.class, player.getEntityBoundingBox().expand(16.0, 2.0, 16.0))) {
            if(sheep.world.isRemote) {
                continue;
            }
            if(sheep.isChild() || sheep.getSheared()) {
                sheep.move(MoverType.SELF, -4.0 / (player.posX - sheep.posX), 0, -4.0D / (player.posZ - sheep.posZ));
            } else if(player.getDistanceToEntity(sheep) > 5.0) {
                sheep.move(MoverType.SELF, (player.posX - sheep.posX) * 0.8, 0, (player.posZ - sheep.posZ) * 0.8);
            }
        }
    }

    private void shearsDiamond(EntityPlayer player, World world, ItemStack itemStack) {
        for(EntitySheep sheep : world.getEntitiesWithinAABB(EntitySheep.class, player.getEntityBoundingBox().expand(8.0, 2.0, 8.0))) {
            if(sheep.world.isRemote) {
                continue;
            }
            if(sheep.isChild() || sheep.getSheared()) {
            } else {
                sheep.setSheared(true);
                dropItem(sheep, new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, sheep.getFleeceColor().getMetadata()));
                itemStack.damageItem(1, player);
                sheep.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
            }
        }
    }

    private void dropItem(EntitySheep sheep, ItemStack itemStack) {
        Random rand = sheep.getRNG();
        int n = 1 + rand.nextInt(3);
        for(int i = 0; i < n; i++) {
            EntityItem entityItem = sheep.entityDropItem(itemStack, 1.0F);
            entityItem.motionY += rand.nextFloat() * 0.05F;
            entityItem.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
            entityItem.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
        }
    }
}
