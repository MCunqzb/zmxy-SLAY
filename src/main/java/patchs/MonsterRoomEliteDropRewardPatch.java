package patchs;


import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import monsters.act1.DrakeDemonKing;
import relics.BloodSirenShell;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import patchs.RelicDropLogic;
import relics.BloodSirenShell;

@SpirePatch(
        clz = MonsterRoomElite.class,
        method = "dropReward"
)
public class MonsterRoomEliteDropRewardPatch {
    @SpireInstrumentPatch
    public static ExprEditor instrument() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("addRelicToRewards")) {
                    m.replace("{ if (patchs.RelicDropLogic.shouldDropFixedRelic()) { com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom().addRelicToRewards(patchs.RelicDropItem.getRelicForElite()); } else { $_ = $proceed($$); } }");
                }
            }
        };
    }
}
