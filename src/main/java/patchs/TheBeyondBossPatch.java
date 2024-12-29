package patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import demoMod.MonkeyKingMod;
import monsters.act3.YangJian;
import pathes.ThmodClassEnum;

@SpirePatch(clz = TheBeyond.class, method = "initializeBoss")
public class TheBeyondBossPatch {
    @SpirePostfixPatch
    public static void TheBottomBossPatch(TheBeyond __instance) {
        if (AbstractDungeon.player.chosenClass.equals(ThmodClassEnum.MonkeyKing_CLASS) && MonkeyKingMod.MonkeyKingSpireConfig.getBool("TheBeyondBoss100%fromMod")) {
            AbstractDungeon.bossList.clear();
            AbstractDungeon.bossList.add(YangJian.ID);

        }

    }
}