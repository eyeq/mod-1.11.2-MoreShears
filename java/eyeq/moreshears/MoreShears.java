package eyeq.moreshears;

import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import eyeq.moreshears.event.MoreShearsEventHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

import static eyeq.moreshears.MoreShears.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class MoreShears {
    public static final String MOD_ID = "eyeq_moreshears";

    @Mod.Instance(MOD_ID)
    public static MoreShears instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item shearsWood;
    public static Item shearsStone;
    public static Item shearsGold;
    public static Item shearsDiamond;

    public static Item shearsIronAndDiamond;
    public static Item shearsGoldAndIron;
    public static Item shearsDiamondAndGold;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new MoreShearsEventHandler());
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        shearsWood = new ItemShears().setMaxDamage(14).setUnlocalizedName("shearsWood");
        shearsStone = new ItemShears().setMaxDamage(119).setUnlocalizedName("shearsStone");
        shearsGold = new ItemShears().setMaxDamage(34).setUnlocalizedName("shearsGold");
        shearsDiamond = new ItemShears().setMaxDamage(762).setUnlocalizedName("shearsDiamond");

        shearsIronAndDiamond = new ItemShears().setMaxDamage(119).setUnlocalizedName("shearsRegen");
        shearsGoldAndIron = new ItemShears().setMaxDamage(340).setUnlocalizedName("shearsLovely");
        shearsDiamondAndGold = new ItemShears().setMaxDamage(762).setUnlocalizedName("shearsLucky");

        GameRegistry.register(shearsWood, resource.createResourceLocation("wooden_shears"));
        GameRegistry.register(shearsStone, resource.createResourceLocation("stone_shears"));
        GameRegistry.register(shearsGold, resource.createResourceLocation("golden_shears"));
        GameRegistry.register(shearsDiamond, resource.createResourceLocation("diamond_shears"));

        GameRegistry.register(shearsIronAndDiamond, resource.createResourceLocation("regen_shears"));
        GameRegistry.register(shearsGoldAndIron, resource.createResourceLocation("lovely_shears"));
        GameRegistry.register(shearsDiamondAndGold, resource.createResourceLocation("lucky_shears"));
    }

    public static void addRecipeShears(Item output, Object input) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), "X ", " X",
                'X', input));
    }

    public static void addRecipeShears(Item output, Object input1, Object input2) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), "X ", " Y",
                'X', input1, 'Y', input2));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), "Y ", " X",
                'X', input1, 'Y', input2));
    }

    public static void addRecipes() {
        addRecipeShears(shearsWood, UOreDictionary.OREDICT_STICK);
        addRecipeShears(shearsStone, UOreDictionary.OREDICT_COBBLESTONE);
        addRecipeShears(shearsGold, UOreDictionary.OREDICT_GOLD_INGOT);
        addRecipeShears(shearsDiamond, UOreDictionary.OREDICT_DIAMOND);

        addRecipeShears(shearsIronAndDiamond, UOreDictionary.OREDICT_IRON_INGOT, UOreDictionary.OREDICT_DIAMOND);
        addRecipeShears(shearsGoldAndIron, UOreDictionary.OREDICT_GOLD_INGOT, UOreDictionary.OREDICT_IRON_INGOT);
        addRecipeShears(shearsDiamondAndGold, UOreDictionary.OREDICT_DIAMOND, UOreDictionary.OREDICT_GOLD_INGOT);
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(shearsWood);
        UModelLoader.setCustomModelResourceLocation(shearsStone);
        UModelLoader.setCustomModelResourceLocation(shearsGold);
        UModelLoader.setCustomModelResourceLocation(shearsDiamond);

        UModelLoader.setCustomModelResourceLocation(shearsIronAndDiamond);
        UModelLoader.setCustomModelResourceLocation(shearsGoldAndIron);
        UModelLoader.setCustomModelResourceLocation(shearsDiamondAndGold);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-MoreShears");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, shearsWood, "Wooden Shears");
        language.register(LanguageResourceManager.JA_JP, shearsWood, "木のハサミ");
        language.register(LanguageResourceManager.EN_US, shearsStone, "Stone Shears");
        language.register(LanguageResourceManager.JA_JP, shearsStone, "石のハサミ");
        language.register(LanguageResourceManager.EN_US, shearsGold, "Golden Shears");
        language.register(LanguageResourceManager.JA_JP, shearsGold, "金のハサミ");
        language.register(LanguageResourceManager.EN_US, shearsDiamond, "Diamond Shears");
        language.register(LanguageResourceManager.JA_JP, shearsDiamond, "ダイヤのハサミ");

        language.register(LanguageResourceManager.EN_US, shearsIronAndDiamond, "Regenerate Shears");
        language.register(LanguageResourceManager.JA_JP, shearsIronAndDiamond, "再生のハサミ");
        language.register(LanguageResourceManager.EN_US, shearsGoldAndIron, "Lovely Shears");
        language.register(LanguageResourceManager.JA_JP, shearsGoldAndIron, "吸引のハサミ");
        language.register(LanguageResourceManager.EN_US, shearsDiamondAndGold, "Lucky Shears");
        language.register(LanguageResourceManager.JA_JP, shearsDiamondAndGold, "倍化のハサミ");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, shearsWood, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, shearsStone, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, shearsGold, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, shearsDiamond, ItemmodelJsonFactory.ItemmodelParent.GENERATED);

        UModelCreator.createItemJson(project, shearsIronAndDiamond, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, shearsGoldAndIron, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, shearsDiamondAndGold, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
