package dungeons;

public class ZMXY1 {}
    //extends CustomDungeon {
//    public static String ID = ModHelper.MakePath("ZMXY1");
//    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
//    public static final String[] TEXT = uiStrings.TEXT;
//    public static final String NAME = TEXT[0];
//    public ZMXY1() {
//        super(NAME, ID, "images/ui/event/panel.png", false, 3, 12, 10);
//    }
//
//    public ZMXY1(CustomDungeon cd, AbstractPlayer p, ArrayList<String> emptyList) {
//        super(cd, p, emptyList);
//    }
//
//    public ZMXY1(CustomDungeon cd, AbstractPlayer p, SaveFile saveFile) {
//        super(cd, p, saveFile);
//    }
//
//    @Override
//    public AbstractScene DungeonScene() {
//        return (AbstractScene)new ModScene();
//    }
//
//    @Override
//    protected void initializeLevelSpecificChances() {
//        shopRoomChance = 0.05F;
//        restRoomChance = 0.12F;
//        treasureRoomChance = 0.0F;
//        eventRoomChance = 0.22F;
//        eliteRoomChance = 0.08F;
//        smallChestChance = 50;
//        mediumChestChance = 33;
//        largeChestChance = 17;
//        commonRelicChance = 50;
//        uncommonRelicChance = 33;
//        rareRelicChance = 17;
//        colorlessRareChance = 0.3F;
//        if (AbstractDungeon.ascensionLevel >= 12) {
//            cardUpgradedChance = 0.25F;
//        } else {
//            cardUpgradedChance = 0.5F;
//        }
//
//    }
//
//    public String getBodyText() {
//        return TEXT[2];
//    }
//
//    public String getOptionText() {
//        return TEXT[3];
//    }
//
//   //protected void generateElites(int count) {
//   //    ArrayList<MonsterInfo> monsters = new ArrayList<>();
//   //    monsters.add(new MonsterInfo("dreaming_journey_to_the_west:KingYurong", 1.0F));
//   //    monsters.add(new MonsterInfo("dreaming_journey_to_the_west:KingMonkey", 1.0F));
//   //    monsters.add(new MonsterInfo("dreaming_journey_to_the_west:KingLionCamel", 1.0F));
//   //    MonsterInfo.normalizeWeights(monsters);
//   //    populateMonsterList(monsters, count, true);
//   //}
//
//}
