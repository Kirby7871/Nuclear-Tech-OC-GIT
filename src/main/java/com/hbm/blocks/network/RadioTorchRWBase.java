package com.hbm.blocks.network;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.gui.GUIScreenRadioTorch;
import com.hbm.tileentity.network.TileEntityRadioTorchBase;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for the basic sender and receiver RTTY torch
 * @author hbm
 */
public abstract class RadioTorchRWBase extends RadioTorchBase {

	@SideOnly(Side.CLIENT) protected IIcon iconOn;

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? this.blockIcon : this.iconOn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityRadioTorchBase) {
			TileEntityRadioTorchBase radio = (TileEntityRadioTorchBase) te;
			List<String> text = new ArrayList();
			if(radio.channel != null && !radio.channel.isEmpty()) text.add(EnumChatFormatting.AQUA + "Freq: " + radio.channel);
			text.add(EnumChatFormatting.RED + "Signal: " + radio.lastState);
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityRadioTorchBase) return new GUIScreenRadioTorch((TileEntityRadioTorchBase) te);
		return null;
	}
}
