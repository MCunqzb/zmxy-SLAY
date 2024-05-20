package patchs;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import monsters.act1.boss.BullDemonKing;
import monsters.act1.boss.RocDemonKing;
import relics.FuriousRing;

//@SpirePatch(clz = AbstractDungeon.class, method = "nextRoomTransition")
//public class BullDemonKingMovePlayerPatch {
//    @SpirePostfixPatch
//    public static void  BullDemonKingMovePlayerPatch(AbstractDungeon __instance) {
//        boolean judge =false;
//        MonsterGroup monsters = __instance.getMonsters();
//        BaseMod.logger.info(monsters);
//        if (monsters != null) {
//            for (AbstractMonster monster : monsters.monsters) {
//                if (monster != null && monster.id.equals(BullDemonKing.ID)) {
//                    judge =true;
//                }else {
//                    judge =false;
//                }
//            }
//        }
//        if (__instance.getCurrRoom() instanceof MonsterRoom && (__instance.lastCombatMetricKey.equals("Shield and Spear")
//        || judge)) {
//            __instance.player.movePosition((float) Settings.WIDTH / 2.0F, 340.0F * Settings.yScale);
//        }
//    }
//}
