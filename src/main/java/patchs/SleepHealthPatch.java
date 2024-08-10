package patchs;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import relics.FuriousRing;

@SpirePatch(clz = CampfireSleepEffect.class, method = "update")
public class SleepHealthPatch {
    @SpirePostfixPatch
    public static void SleepHealthPatch(CampfireSleepEffect __instance) {
            if (AbstractDungeon.player.hasRelic(FuriousRing.ID) && __instance.duration < __instance.startingDuration / 2.0F) {
                AbstractDungeon.player.getRelic(FuriousRing.ID).flash();
                AbstractDungeon.player.decreaseMaxHealth(4);
            }
        }
}

