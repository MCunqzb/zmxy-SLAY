package patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import monsters.act4.TheSovereignofTheTongtianSect;
import pathes.ThmodClassEnum;

@SpirePatch(clz = TheEnding.class, method = "initializeBoss")
public class TheEndingBossPatch {
    @SpirePostfixPatch
    public static void TheEndingBossPatch(TheEnding __instance) {
        if (AbstractDungeon.player.chosenClass.equals(ThmodClassEnum.MonkeyKing_CLASS)) {
            AbstractDungeon.bossList.clear();
            AbstractDungeon.bossList.add(TheSovereignofTheTongtianSect.ID);
            AbstractDungeon.bossList.add(TheSovereignofTheTongtianSect.ID);
            AbstractDungeon.bossList.add(TheSovereignofTheTongtianSect.ID);
        } else {
            AbstractDungeon.bossList.clear();
            AbstractDungeon.bossList.add("The Heart");
            AbstractDungeon.bossList.add("The Heart");
            AbstractDungeon.bossList.add("The Heart");
            AbstractDungeon.bossList.add(TheSovereignofTheTongtianSect.ID);
            AbstractDungeon.bossList.add(TheSovereignofTheTongtianSect.ID);
            AbstractDungeon.bossList.add(TheSovereignofTheTongtianSect.ID);
        }

    }
}