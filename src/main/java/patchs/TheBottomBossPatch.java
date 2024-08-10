package patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import demoMod.MonkeyKingMod;
import monsters.act1.boss.BullDemonKing;
import monsters.act1.boss.RocDemonKing;
import monsters.act4.TheSovereignofTheTongtianSect;
import pathes.ThmodClassEnum;

@SpirePatch(clz = Exordium.class, method = "initializeBoss")
public class TheBottomBossPatch {
    @SpirePostfixPatch
    public static void TheBottomBossPatch(Exordium __instance) {
        if (AbstractDungeon.player.chosenClass.equals(ThmodClassEnum.MonkeyKing_CLASS) && MonkeyKingMod.MonkeyKingSpireConfig.getBool("TheBottomBoss100%fromMod")) {
            AbstractDungeon.bossList.clear();
            AbstractDungeon.bossList.add(BullDemonKing.ID);
            AbstractDungeon.bossList.add(RocDemonKing.ID);
        }

    }
}