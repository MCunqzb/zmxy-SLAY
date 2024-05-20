package actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.Iterator;

public class FaceTheDifficultyAction extends AbstractGameAction {
    private AbstractCreature target;
    private boolean upgraded=false;
    private int totalBuffAmount = 0;
    public FaceTheDifficultyAction(AbstractCreature target,boolean upgraded) {
        this.target = target;
        this.upgraded=upgraded;
    }

    @Override
    public void update() {
        if (!this.upgraded) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var3.hasNext()) {
                    AbstractMonster monster = (AbstractMonster) var3.next();
                    if (!monster.isDead && !monster.isDying) {
                        for (AbstractPower power : monster.powers) {
                            if (power.amount > totalBuffAmount) {
                                totalBuffAmount = power.amount;
                                //BaseMod.logger.info("UNUp"+totalBuffAmount);
                            }
                        }
                    }
                }
            }
            this.addToTop(new ApplyPowerAction(target, target, new VigorPower(target, totalBuffAmount), totalBuffAmount));
        }else{
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var3.hasNext()) {
                    AbstractMonster monster = (AbstractMonster) var3.next();
                    if (!monster.isDead && !monster.isDying) {
                        if (monster != null) {
                            for (AbstractPower power : monster.powers) {
                                if (power.amount > 0) {
                                    totalBuffAmount += power.amount;
                                    //BaseMod.logger.info("Up"+totalBuffAmount);
                                }
                            }
                        }
                    }
                }
            }
            this.addToTop(new ApplyPowerAction(target, target, new VigorPower(target, totalBuffAmount), totalBuffAmount));
        }
        this.isDone = true;
    }
}
