package patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import demoMod.MonkeyKingMod;
import monsters.act2.boss.WheelTurningKing;
import pathes.ThmodClassEnum;

@SpirePatch(clz = TheCity.class, method = "initializeBoss")
public class TheCityBossPatch {
    @SpirePostfixPatch
    public static void TheBottomBossPatch(TheCity __instance) {
        if (AbstractDungeon.player.chosenClass.equals(ThmodClassEnum.MonkeyKing_CLASS) && MonkeyKingMod.MonkeyKingSpireConfig.getBool("TheCityBoss100%fromMod")) {
            AbstractDungeon.bossList.clear();
            AbstractDungeon.bossList.add(WheelTurningKing.ID);

        }

    }
}