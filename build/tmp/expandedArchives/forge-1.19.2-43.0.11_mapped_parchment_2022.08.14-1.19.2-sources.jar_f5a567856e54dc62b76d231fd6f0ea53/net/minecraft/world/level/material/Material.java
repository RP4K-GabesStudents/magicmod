package net.minecraft.world.level.material;

public final class Material {
   public static final Material AIR = (new Material.Builder(MaterialColor.NONE)).noCollider().notSolidBlocking().nonSolid().replaceable().build();
   public static final Material STRUCTURAL_AIR = (new Material.Builder(MaterialColor.NONE)).noCollider().notSolidBlocking().nonSolid().replaceable().build();
   public static final Material PORTAL = (new Material.Builder(MaterialColor.NONE)).noCollider().notSolidBlocking().nonSolid().notPushable().build();
   public static final Material CLOTH_DECORATION = (new Material.Builder(MaterialColor.WOOL)).noCollider().notSolidBlocking().nonSolid().flammable().build();
   public static final Material PLANT = (new Material.Builder(MaterialColor.PLANT)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().build();
   public static final Material WATER_PLANT = (new Material.Builder(MaterialColor.WATER)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().build();
   public static final Material REPLACEABLE_PLANT = (new Material.Builder(MaterialColor.PLANT)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().flammable().build();
   public static final Material REPLACEABLE_FIREPROOF_PLANT = (new Material.Builder(MaterialColor.PLANT)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().build();
   public static final Material REPLACEABLE_WATER_PLANT = (new Material.Builder(MaterialColor.WATER)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().build();
   public static final Material WATER = (new Material.Builder(MaterialColor.WATER)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
   public static final Material BUBBLE_COLUMN = (new Material.Builder(MaterialColor.WATER)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
   public static final Material LAVA = (new Material.Builder(MaterialColor.FIRE)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
   public static final Material TOP_SNOW = (new Material.Builder(MaterialColor.SNOW)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().build();
   public static final Material FIRE = (new Material.Builder(MaterialColor.NONE)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().build();
   public static final Material DECORATION = (new Material.Builder(MaterialColor.NONE)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().build();
   public static final Material WEB = (new Material.Builder(MaterialColor.WOOL)).noCollider().notSolidBlocking().destroyOnPush().build();
   public static final Material SCULK = (new Material.Builder(MaterialColor.COLOR_BLACK)).build();
   public static final Material BUILDABLE_GLASS = (new Material.Builder(MaterialColor.NONE)).build();
   public static final Material CLAY = (new Material.Builder(MaterialColor.CLAY)).build();
   public static final Material DIRT = (new Material.Builder(MaterialColor.DIRT)).build();
   public static final Material GRASS = (new Material.Builder(MaterialColor.GRASS)).build();
   public static final Material ICE_SOLID = (new Material.Builder(MaterialColor.ICE)).build();
   public static final Material SAND = (new Material.Builder(MaterialColor.SAND)).build();
   public static final Material SPONGE = (new Material.Builder(MaterialColor.COLOR_YELLOW)).build();
   public static final Material SHULKER_SHELL = (new Material.Builder(MaterialColor.COLOR_PURPLE)).build();
   public static final Material WOOD = (new Material.Builder(MaterialColor.WOOD)).flammable().build();
   public static final Material NETHER_WOOD = (new Material.Builder(MaterialColor.WOOD)).build();
   public static final Material BAMBOO_SAPLING = (new Material.Builder(MaterialColor.WOOD)).flammable().destroyOnPush().noCollider().build();
   public static final Material BAMBOO = (new Material.Builder(MaterialColor.WOOD)).flammable().destroyOnPush().build();
   public static final Material WOOL = (new Material.Builder(MaterialColor.WOOL)).flammable().build();
   public static final Material EXPLOSIVE = (new Material.Builder(MaterialColor.FIRE)).flammable().notSolidBlocking().build();
   public static final Material LEAVES = (new Material.Builder(MaterialColor.PLANT)).flammable().notSolidBlocking().destroyOnPush().build();
   public static final Material GLASS = (new Material.Builder(MaterialColor.NONE)).notSolidBlocking().build();
   public static final Material ICE = (new Material.Builder(MaterialColor.ICE)).notSolidBlocking().build();
   public static final Material CACTUS = (new Material.Builder(MaterialColor.PLANT)).notSolidBlocking().destroyOnPush().build();
   public static final Material STONE = (new Material.Builder(MaterialColor.STONE)).build();
   public static final Material METAL = (new Material.Builder(MaterialColor.METAL)).build();
   /** The material for crafted snow. */
   public static final Material SNOW = (new Material.Builder(MaterialColor.SNOW)).build();
   public static final Material HEAVY_METAL = (new Material.Builder(MaterialColor.METAL)).notPushable().build();
   public static final Material BARRIER = (new Material.Builder(MaterialColor.NONE)).notPushable().build();
   public static final Material PISTON = (new Material.Builder(MaterialColor.STONE)).notPushable().build();
   public static final Material MOSS = (new Material.Builder(MaterialColor.PLANT)).destroyOnPush().build();
   public static final Material VEGETABLE = (new Material.Builder(MaterialColor.PLANT)).destroyOnPush().build();
   public static final Material EGG = (new Material.Builder(MaterialColor.PLANT)).destroyOnPush().build();
   public static final Material CAKE = (new Material.Builder(MaterialColor.NONE)).destroyOnPush().build();
   public static final Material AMETHYST = (new Material.Builder(MaterialColor.COLOR_PURPLE)).build();
   public static final Material POWDER_SNOW = (new Material.Builder(MaterialColor.SNOW)).nonSolid().noCollider().build();
   public static final Material FROGSPAWN = (new Material.Builder(MaterialColor.WATER)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().build();
   public static final Material FROGLIGHT = (new Material.Builder(MaterialColor.NONE)).build();
   /** The color index used to draw the blocks of this material on maps. */
   private final MaterialColor color;
   /**
    * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
    * indicates that it can't be pushed.
    */
   private final PushReaction pushReaction;
   private final boolean blocksMotion;
   private final boolean flammable;
   private final boolean liquid;
   private final boolean solidBlocking;
   private final boolean replaceable;
   private final boolean solid;

   public Material(MaterialColor pColor, boolean pLiquid, boolean pSolid, boolean pBlocksMotion, boolean pSolidBlocking, boolean pFlammable, boolean pReplaceable, PushReaction pPushReaction) {
      this.color = pColor;
      this.liquid = pLiquid;
      this.solid = pSolid;
      this.blocksMotion = pBlocksMotion;
      this.solidBlocking = pSolidBlocking;
      this.flammable = pFlammable;
      this.replaceable = pReplaceable;
      this.pushReaction = pPushReaction;
   }

   /**
    * Returns if blocks of these materials are liquids.
    */
   public boolean isLiquid() {
      return this.liquid;
   }

   /**
    * Returns true if the block is a considered solid. This is true by default.
    */
   public boolean isSolid() {
      return this.solid;
   }

   /**
    * Returns if this material is considered solid or not
    */
   public boolean blocksMotion() {
      return this.blocksMotion;
   }

   /**
    * Returns if the block can burn or not.
    */
   public boolean isFlammable() {
      return this.flammable;
   }

   /**
    * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
    */
   public boolean isReplaceable() {
      return this.replaceable;
   }

   /**
    * Indicate if the material is opaque
    */
   public boolean isSolidBlocking() {
      return this.solidBlocking;
   }

   public PushReaction getPushReaction() {
      return this.pushReaction;
   }

   /**
    * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
    */
   public MaterialColor getColor() {
      return this.color;
   }

   public static class Builder {
      private PushReaction pushReaction = PushReaction.NORMAL;
      private boolean blocksMotion = true;
      private boolean flammable;
      private boolean liquid;
      private boolean replaceable;
      private boolean solid = true;
      private final MaterialColor color;
      private boolean solidBlocking = true;

      public Builder(MaterialColor pColor) {
         this.color = pColor;
      }

      public Material.Builder liquid() {
         this.liquid = true;
         return this;
      }

      public Material.Builder nonSolid() {
         this.solid = false;
         return this;
      }

      public Material.Builder noCollider() {
         this.blocksMotion = false;
         return this;
      }

      Material.Builder notSolidBlocking() {
         this.solidBlocking = false;
         return this;
      }

      protected Material.Builder flammable() {
         this.flammable = true;
         return this;
      }

      public Material.Builder replaceable() {
         this.replaceable = true;
         return this;
      }

      protected Material.Builder destroyOnPush() {
         this.pushReaction = PushReaction.DESTROY;
         return this;
      }

      protected Material.Builder notPushable() {
         this.pushReaction = PushReaction.BLOCK;
         return this;
      }

      public Material build() {
         return new Material(this.color, this.liquid, this.solid, this.blocksMotion, this.solidBlocking, this.flammable, this.replaceable, this.pushReaction);
      }
   }
}